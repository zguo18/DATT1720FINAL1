package UI;

import Core.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * MahjongGUI.java
 * Main GUI class combining Game, GameUI, and GamePanel to coordinate game logic and interface display.
 */
public class MahjongGUI extends JFrame {
    private Game game;
    private GameUI gameUI;
    private GamePanel gamePanel;

    public MahjongGUI() {
        // Initialize game logic core
        game = new Game();

        // Initialize UI controller
        gameUI = new GameUI();
        gameUI.initializeUI();

        // Initialize graphical panel and bind to human player
        HumanPlayer human = (HumanPlayer) game.getPlayers().get(0);
        gamePanel = new GamePanel(human);
        gamePanel.setBounds(100, 400, 600, 150);
        gameUI.getMainPanel().add(gamePanel);

        // Add main panel to this JFrame
        this.setTitle("Sichuan Mahjong");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(null);
        this.add(gameUI.getMainPanel());
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Add keyboard event: R key to restart the game
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    System.out.println("Game restarted!");
                    game.resetGame();
                    gamePanel.updateHand(game.getPlayers().get(0).getHandCards());
                    gameUI.updateRemainingCards(game.getDeck().remainingCards());
                    gameUI.updateTurnIndicator(game.getCurrentTurn());
                }
            }
        });

        // Start the initial game
        game.startGame();
        gamePanel.updateHand(game.getPlayers().get(0).getHandCards());
        gameUI.updateRemainingCards(game.getDeck().remainingCards());
        gameUI.updateTurnIndicator(game.getCurrentTurn());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MahjongGUI::new);
    }
}
