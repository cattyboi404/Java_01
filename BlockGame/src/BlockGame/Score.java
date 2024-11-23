package BlockGame;

import java.awt.*;

public class Score {
    private int score;

    public Score() { this.score = 0; }

    public void increaseScore(int points) { score += points; }

    public int getScore() { return score; }

    public void draw(Graphics g, int panelWidth) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, panelWidth - 100, 30);
    }
}
