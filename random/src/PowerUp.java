import java.awt.*;

public class PowerUp {
    public int x, y, size = 20;
    public int type; // 0 = enlarge, 1 = shoot

    public PowerUp(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = Math.random() < 0.5 ? 0 : 1;
    }

    public void fall() {
        y += 3;
    }

    public void draw(Graphics g) {
        if (type == 0) g.setColor(Color.GREEN);     // Enlarge
        else g.setColor(Color.ORANGE);              // Shoot

        g.fillOval(x, y, size, size);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, size, size);
    }
}
