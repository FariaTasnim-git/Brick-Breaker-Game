import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle {
    public int x, y, width, height, dx;
    private final int speed = 5;
    public boolean canShoot = false;

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 15;
        this.dx = 0;
    }

    public void move() {
        x += dx;
        if (x < 0) x = 0;
        if (x + width > 800) x = 800 - width;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(100, 200, 255));
        g.fillRoundRect(x, y, width, height, 15, 15);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) dx = -speed;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) dx = speed;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) dx = 0;
    }

    public void expand() {
        width += 40;
        if (width > 200) width = 200;
    }

    public void enableShooting() {
        canShoot = true;
    }
}
