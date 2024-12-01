package BlockGame;

import javax.swing.*;
import java.awt.*;

public class StartGame extends JPanel {
    private Main parentFrame;

    public StartGame(Main parentFrame) {
        this.parentFrame = parentFrame;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Brick Breaker Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        add(titleLabel, gbc);

        JButton startButton = createStyledButton("Game Start");
        startButton.addActionListener(e -> parentFrame.switchToPanel(new GamePanel(parentFrame)));
        gbc.gridy = 1;
        add(startButton, gbc);

        JButton helpButton = createStyledButton("How to play");
        helpButton.addActionListener(e -> showHelpDialog());
        gbc.gridy = 2;
        add(helpButton, gbc);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        return button;
    }

    private void showHelpDialog() {
        String helpMessage = """
                벽돌 깨기 게임 방법:
                
                1. 마우스를 좌우로 움직여 패들로 공을 튕겨내세요.
                2. 공으로 벽돌을 부수면 점수를 얻습니다.
                3. 공이 패들 아래로 떨어지면 남은 목숨이 줄어듭니다.
                4. 남은 목숨이 0이 되면 게임이 종료됩니다.
                """;

        JOptionPane.showMessageDialog(
            this,
            helpMessage,
            "게임 방법",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
