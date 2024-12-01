package BlockGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements MouseMotionListener, ActionListener {
    private Main parentFrame;
    private Paddle paddle;
    private Ball ball;
    private Block[][] blocks;
    private Timer timer;
    private Lives lives;
    private Score score;
    private int currentStage = 1; // 현재 단계
    private int lastScoreCheckpoint = 0; // 공의 속도가 증가한 마지막 점수 체크포인트
    private boolean isMovingStage = false; // 움직이는 블록 여부
    private int blockMoveDirection = 1; // 움직이는 블록 방향 (1: 오른쪽, -1: 왼쪽)

    public GamePanel(Main parentFrame) {
        this.parentFrame = parentFrame;
        setBackground(Color.WHITE);

        initializeGame();

        addMouseMotionListener(this);
        timer = new Timer(10, this);
        timer.start();
    }

    private void initializeGame() {
        paddle = new Paddle(350, 550);
        ball = new Ball(400, 300);
        lives = new Lives(3);
        score = new Score();
        generateBlocks(currentStage);
    }

    private void generateBlocks(int stage) {
        int blockColumns = 10;
        int blockRows = 5;
        int panelWidth = 800;
        int horizontalPadding = 10;
        int verticalPadding = 70;
        int blockSpacing = 5;

        int blockWidth = (panelWidth - 2 * horizontalPadding - (blockColumns - 1) * blockSpacing) / blockColumns;
        int blockHeight = 30;

        blocks = new Block[blockRows][blockColumns];
        Random random = new Random();

        for (int i = 0; i < blockRows; i++) {
            for (int j = 0; j < blockColumns; j++) {
                int x = horizontalPadding + j * (blockWidth + blockSpacing);
                int y = verticalPadding + i * (blockHeight + blockSpacing);

                if (stage == 2 || stage == 3) {
                    if (random.nextInt(100) < 20) { // 20% 확률로 강한 블록 생성
                        blocks[i][j] = new Block(x, y, blockWidth, blockHeight, 3); // 강한 블록 (내구도 3)
                    } else {
                        blocks[i][j] = new Block(x, y, blockWidth, blockHeight, 1);
                    }

                    if (stage == 3 && random.nextInt(10) < 2) { // 20% 확률로 움직이는 블록 생성
                        blocks[i][j].enableMoving();
                    }
                } else {
                    blocks[i][j] = new Block(x, y, blockWidth, blockHeight, 1);
                }
            }
        }

        isMovingStage = (stage == 3); // 3단계에서 움직이는 블록 활성화
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Block[] row : blocks) {
            if (row != null) {
                for (Block block : row) {
                    if (block != null) block.draw(g);
                }
            }
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
        boolean blockDestroyed = ball.move(paddle.getBounds(), blocks);

        if (blockDestroyed) {
            score.increaseScore(1);

            if (score.getScore() >= lastScoreCheckpoint + 10) {
                ball.increaseSpeed(1.0); // 10점 단위로 공 속도 증가
                lastScoreCheckpoint = score.getScore();
            }
        }

        if (isMovingStage) {
            moveBlocks();
        }

        if (isStageCleared()) {
            showNextStageDialog();
        }

        if (ball.getBounds().y > 600) {
            lives.loseLife();
            if (lives.getLives() <= 0) {
                timer.stop();
                showGameOverDialog();
            } else {
                resetBallAndPaddle();
            }
        }

        repaint();
    }

    private void moveBlocks() {
        for (Block[] row : blocks) {
            for (Block block : row) {
                if (block != null && block.isMoving()) {
                    block.move();
                }
            }
        }

        if (blocks[0][0].getBounds().x <= 0 || blocks[0][blocks[0].length - 1].getBounds().x >= 800 - blocks[0][0].getBounds().width) {
            blockMoveDirection *= -1; // 방향 반전
        }
    }

    private boolean isStageCleared() {
        for (Block[] row : blocks) {
            for (Block block : row) {
                if (block != null && block.isVisible()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showNextStageDialog() {
        if (currentStage == 3 && isStageCleared()) {
            showCongratulations();
            return;
        }

        int choice = JOptionPane.showOptionDialog(
                this,
                "Next?",
                "Stage Clear!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Yes", "No"},
                "Yes"
        );

        if (choice == JOptionPane.YES_OPTION) {
            currentStage++;
            generateBlocks(currentStage);
            resetBallAndPaddle();
        } else {
            parentFrame.restartToMainMenu();
        }
    }

    private void showCongratulations() {
        timer.stop();
        new Thread(() -> {
            try {
                Graphics g = getGraphics();
                for (int i = 0; i < 10; i++) {
                    if (g != null) {
                        g.setFont(new Font("Arial", Font.BOLD, 50));
                        g.setColor(i % 2 == 0 ? Color.MAGENTA : Color.YELLOW);
                        g.drawString("Congratulations!", getWidth() / 2 - 200, getHeight() / 2);
                    }
                    Thread.sleep(500);
                }
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            } finally {
                parentFrame.restartToMainMenu();
            }
        }).start();
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
            currentStage = 1;
            initializeGame();
            timer.start();
            repaint();
        } else {
            System.exit(0);
        }
    }
}
