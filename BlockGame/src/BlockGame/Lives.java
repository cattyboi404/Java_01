package BlockGame;

import java.awt.*;

public class Lives {
    private int lives;

    public Lives(int initialLives) { this.lives = initialLives; }

    public void loseLife() {
        if (lives > 0)
            lives--;
    }

    public int getLives() { return lives; }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Lives: " + lives, 10, 20);
    }
}
