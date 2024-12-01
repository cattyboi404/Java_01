package BlockGame;

import java.awt.*;

public class Score {
    private int score;
    private boolean isAnimating = false;
    private int animationStep = 0;

    public Score() {
        this.score = 0;
    }

    public void increaseScore(int points) {
        score += points;
        if (score % 10 == 0) {
            isAnimating = true;
            animationStep = 0;
        }
    }

    public int getScore() {
        return score;
    }

    public void draw(Graphics g, int panelWidth) {
        g.setFont(new Font("Arial", Font.BOLD, 18));

        if (isAnimating) {
            if (animationStep % 20 < 10) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.ORANGE);
            }
            animationStep++;
            if (animationStep > 40) {
                isAnimating = false;
            }
        } else {
            g.setColor(Color.BLACK);
        }

        g.drawString("Score: " + score, panelWidth - 100, 30);
    }
}
