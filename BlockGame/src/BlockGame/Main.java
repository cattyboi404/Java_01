package BlockGame;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Break Out");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        add(new StartGame(this));

        setVisible(true);
    }

    public void switchToPanel(JPanel panel) {
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }

    public void restartToMainMenu() {
        switchToPanel(new StartGame(this));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}