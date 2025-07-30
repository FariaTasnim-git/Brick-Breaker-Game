import java.awt.*;

public class Block {
    public int x, y, width = 70, height = 20;
    public boolean hasPowerUp;
    private Color color;

    public Block(int x, int y, Color color, boolean hasPowerUp) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.hasPowerUp = hasPowerUp;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
}
