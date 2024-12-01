package BlockGame;

import java.awt.*;

public class Ball {
    private int x, y, diameter = 20;
    private double xSpeed = 5;
    private double ySpeed = 5;
    private double speedMagnitude = 5;

    public Ball(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        adjustSpeedToMagnitude();
    }

    public boolean move(Rectangle paddle, Block[][] blocks) {
        x += xSpeed;
        y += ySpeed;

        if (x < 0 || x > 800 - diameter) {
            xSpeed = -xSpeed;
        }
        if (y < 0) {
            ySpeed = -ySpeed;
        }

        if (paddle.intersects(getBounds())) {
            ySpeed = -Math.abs(ySpeed);
        }

        for (Block[] row : blocks) {
            for (Block block : row) {
                if (block != null && block.isVisible() && getBounds().intersects(block.getBounds())) {
                    Rectangle blockBounds = block.getBounds();
                    if (x + diameter - xSpeed <= blockBounds.x || x - xSpeed >= blockBounds.x + blockBounds.width) {
                        reverseXSpeed();
                    } else {
                        reverseYSpeed();
                    }
                    return block.hit();
                }
            }
        }

        return false;
    }

    public void reverseYSpeed() {
        ySpeed = -ySpeed;
    }

    public void reverseXSpeed() {
        xSpeed = -xSpeed;
    }

    public void increaseSpeed(double increment) {
        speedMagnitude += increment;
        adjustSpeedToMagnitude();
    }

    private void adjustSpeedToMagnitude() {
        double currentSpeed = Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);
        xSpeed = xSpeed / currentSpeed * speedMagnitude;
        ySpeed = ySpeed / currentSpeed * speedMagnitude;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, diameter, diameter);
    }
}
