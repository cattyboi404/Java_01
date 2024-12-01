package BlockGame;

import java.awt.*;

public class Block {
    private int x, y, width, height;
    private boolean isVisible;
    private int durability;
    private boolean isMoving;
    private int xDirection = 1;

    public Block(int x, int y, int width, int height, int durability) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.durability = durability;
        this.isVisible = true;
        this.isMoving = false;
    }

    public void draw(Graphics g) {
        if (isVisible) {
            if (durability == 3) {
                g.setColor(Color.RED);
            } else if (durability == 2) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.ORANGE);
            }
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }
    }

    public boolean hit() {
        if (durability > 1) {
            durability--;
        } else {
            isVisible = false;
        }
        return !isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void enableMoving() {
        isMoving = true;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void move() {
        if (isMoving) {
            x += xDirection * 2;
            if (x <= 0 || x >= 800 - width) {
                xDirection *= -1;
                x += xDirection * 2;
            }
        }
    }
}
