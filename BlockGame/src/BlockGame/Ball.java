package BlockGame;

import java.awt.*;

public class Ball {
    private int x, y, diameter = 20;
    private int xSpeed = 3, ySpeed = 3;

    public Ball(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void move(Rectangle paddle, Block[][] blocks) {
        x += xSpeed;
        y += ySpeed;

        if (x < 0 || x > 800 - diameter) xSpeed = -xSpeed;
        if (y < 0) ySpeed = -ySpeed;

        if (paddle.intersects(getBounds())) ySpeed = -ySpeed;

        for (Block[] row : blocks) {
            for (Block block : row) {
                if (block.isVisible() && block.getBounds().intersects(getBounds())) {
                    block.setVisible(false);
                    ySpeed = -ySpeed;
                    return;
                }
            }
        }
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, diameter, diameter);
    }
}
