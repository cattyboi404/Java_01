package BlockGame;

import java.awt.*;

public class Paddle {
    private int x, y, width = 100, height = 10;

    public Paddle(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void move(int mouseX) {
        x = mouseX - width / 2;
        if (x < 0) x = 0;
        if (x > 800 - width) x = 800 - width;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}

