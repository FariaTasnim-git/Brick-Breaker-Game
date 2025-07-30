import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Block> blocks;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Bullet> bullets;

    private int lives = 3;
    private int level = 1;
    private int score = 0;
    private boolean gameOver = false;
    private boolean gameStarted = false;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initGame();
        timer = new Timer(10, this);
        timer.start();
    }

    private void initGame() {
        paddle = new Paddle(350, 550);
        ball = new Ball(390, 300);
        blocks = new ArrayList<>();
        powerUps = new ArrayList<>();
        bullets = new ArrayList<>();
        generateBlocks(level);
    }

    private void generateBlocks(int level) {
        blocks.clear();
        int rows = 2 + level;
        int cols = 10;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * 75 + 20;
                int y = row * 30 + 50;
                Color color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
                boolean hasPowerUp = Math.random() < 0.15;
                blocks.add(new Block(x, y, color, hasPowerUp));
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameStarted) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Press Enter to Start", 250, 300);
        } else {
            paddle.draw(g);
            ball.draw(g);

            for (Block block : blocks) block.draw(g);
            for (PowerUp p : powerUps) p.draw(g);
            for (Bullet b : bullets) b.draw(g);

            g.setColor(Color.WHITE);
            g.drawString("Lives: " + lives, 10, 20);
            g.drawString("Score: " + score, 100, 20);
            g.drawString("Level: " + level, 200, 20);

            if (gameOver) {
                g.setFont(new Font("Arial", Font.BOLD, 36));
                g.drawString("GAME OVER", 300, 300);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Press Enter to Restart", 300, 350);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            ball.move();
            paddle.move();
            handleCollisions();

            for (PowerUp p : powerUps) p.fall();
            powerUps.removeIf(p -> p.y > getHeight());

            for (Bullet b : bullets) b.move();
            bullets.removeIf(b -> b.y < 0);

            repaint();
        }
    }

    private void handleCollisions() {
        if (ball.getRect().intersects(paddle.getRect())) ball.reverseY();

        Iterator<Block> it = blocks.iterator();
        while (it.hasNext()) {
            Block b = it.next();
            if (ball.getRect().intersects(b.getRect())) {
                ball.reverseY();
                score += 10;
                if (b.hasPowerUp) {
                    powerUps.add(new PowerUp(b.x + 15, b.y));
                }
                it.remove();
                break;
            }
        }

        // Power-up catch
        Iterator<PowerUp> pit = powerUps.iterator();
        while (pit.hasNext()) {
            PowerUp p = pit.next();
            if (p.getRect().intersects(paddle.getRect())) {
                if (p.type == 0) paddle.expand();
                else if (p.type == 1) paddle.enableShooting();
                pit.remove();
            }
        }

        // Bullet hitting blocks
        Iterator<Bullet> bit = bullets.iterator();
        while (bit.hasNext()) {
            Bullet bullet = bit.next();
            Iterator<Block> blockIt = blocks.iterator();
            while (blockIt.hasNext()) {
                Block block = blockIt.next();
                if (bullet.getRect().intersects(block.getRect())) {
                    blockIt.remove();
                    bit.remove();
                    score += 10;
                    break;
                }
            }
        }

        // Ball out of bounds
        if (ball.y > getHeight()) {
            lives--;
            if (lives == 0) gameOver = true;
            ball.reset(390, 300);
        }

        // Level up
        if (blocks.isEmpty()) {
            level++;
            generateBlocks(level);
            ball.reset(390, 300);
        }
    }

    private void restartGame() {
        lives = 3;
        score = 0;
        level = 1;
        gameOver = false;
        gameStarted = true;
        initGame();
    }

    public void keyPressed(KeyEvent e) {
        if (!gameStarted && e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameStarted = true;
        }

        if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            restartGame();
        }

        paddle.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_SPACE && paddle.canShoot) {
            bullets.add(new Bullet(paddle.x + paddle.width / 2 - 2, paddle.y));
        }
    }

    public void keyReleased(KeyEvent e) {
        paddle.keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {}
}
