package BlockGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements MouseMotionListener, ActionListener {
    private Paddle paddle;
    private Ball ball;
    private Block[][] blocks;
    private Timer timer;
    private Lives lives;
    private Score score;

    public GamePanel() {
        setBackground(Color.WHITE);

        initializeGame();

        addMouseMotionListener(this);
        timer = new Timer(10, this);
        timer.start();
    }

    private void initializeGame() {
        paddle = new Paddle(350, 550);
        ball = new Ball(400, 300);

        blocks = new Block[5][10];
        int blockWidth = 75, blockHeight = 30;
        int horizontalPadding = 10;
        int verticalPadding = 50;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j] = new Block(
                    horizontalPadding + j * blockWidth,
                    verticalPadding + i * blockHeight,
                    blockWidth - 5,
                    blockHeight - 5
                );
            }
        }

        lives = new Lives(3);
        score = new Score();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Block[] row : blocks) {
            for (Block block : row)
                block.draw(g);
        }

        paddle.draw(g);
        ball.draw(g);

        lives.draw(g);
        score.draw(g, getWidth());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        paddle.move(e.getX());
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        ball.move(paddle.getBounds(), blocks);

        for (Block[] row : blocks) {
            for (Block block : row) {
                if (block.isVisible() && block.getBounds().intersects(ball.getBounds())) {
                    block.setVisible(false);
                    score.increaseScore(1);
                    ball.reverseYSpeed();
                    break;
                }
            }
        }

        if (ball.getBounds().y > 600) {
            lives.loseLife();
            if (lives.getLives() <= 0) {
                timer.stop();
                showGameOverDialog();
            }
            else
                resetBallAndPaddle();
        }

        repaint();
    }

    private void resetBallAndPaddle() {
        ball = new Ball(400, 300);
        paddle = new Paddle(350, 550);
    }

    private void showGameOverDialog() {
        int choice = JOptionPane.showOptionDialog(
                this,
                "게임 오버! 다시 도전하시겠습니까?\n점수: " + score.getScore(),
                "게임 오버!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"재시작", "종료"},
                "재시작"
        );

        if (choice == JOptionPane.YES_OPTION) {
            initializeGame();
            timer.start();
            repaint();
            
        }
        
        else
            System.exit(0);
    }
}
