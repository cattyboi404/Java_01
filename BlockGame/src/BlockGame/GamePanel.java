package BlockGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements MouseMotionListener, ActionListener {
    private Paddle paddle;
    private Ball ball;
    private Block[][] blocks;
    private Timer timer;

    public GamePanel() {
        setBackground(Color.WHITE);
        paddle = new Paddle(350, 550);
        ball = new Ball(400, 300);

        blocks = new Block[5][8];
        int blockWidth = 80, blockHeight = 30;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j] = new Block(j * blockWidth + 10, i * blockHeight + 10, blockWidth - 5, blockHeight - 5);
            }
        }

        addMouseMotionListener(this);
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paddle.draw(g);
        ball.draw(g);

        for (Block[] row : blocks) {
            for (Block block : row) {
                block.draw(g);
            }
        }
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

        if (ball.getBounds().y > 600) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over!");
            System.exit(0);
        }

        repaint();
    }
}