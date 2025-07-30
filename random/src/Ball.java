import java.awt.*;

public class Ball {
    public int x, y, size = 15;
    private int dx = 3, dy = -3;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += dx;
        y += dy;

        if (x < 0 || x + size > 800) dx = -dx;
        if (y < 0) dy = -dy;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, size, size);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, size, size);
    }

    public void reverseY() {
        dy = -dy;
    }

    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        dx = 3;
        dy = -3;
    }
}
