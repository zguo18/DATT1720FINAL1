package UI;

import Core.Card;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameUI {
    private JFrame mainFrame;
    private JPanel mainPanel;

    // UI Components
    private JLabel remainingCardsLabel; // Left-top: displays remaining deck count
    private JLabel turnIndicatorLabel;  // Top-center: indicates current turn
    private JPanel playedCardsPanel;    // Center: displays played cards in order
    private JLabel initialGangHintLabel; // Bottom-left: displays Gang hint (until chosen)

    // Prompt Icons from Assets
    private ImageIcon pengIcon;
    private ImageIcon gangIcon;
    private ImageIcon huIcon;
    private ImageIcon passIcon;

    /**
     * Initializes the UI components and layout.
     */
    public void initializeUI() {
        // Create main frame
        mainFrame = new JFrame("Sichuan Mahjong");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(null);

        // Create main panel with absolute layout
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 800, 600);
        mainFrame.add(mainPanel);

        // Remaining cards label at top-left
        remainingCardsLabel = new JLabel("Remaining Cards: 108");
        remainingCardsLabel.setBounds(10, 10, 150, 20);
        mainPanel.add(remainingCardsLabel);

        // Turn indicator label at top-center
        turnIndicatorLabel = new JLabel("Turn: ");
        turnIndicatorLabel.setBounds(350, 10, 100, 20);
        mainPanel.add(turnIndicatorLabel);

        // Played cards panel in the center (using FlowLayout)
        playedCardsPanel = new JPanel(new FlowLayout());
        playedCardsPanel.setBounds(150, 250, 500, 100);
        playedCardsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.add(playedCardsPanel);

        // Initial Gang hint label (hidden by default) at bottom-left
        initialGangHintLabel = new JLabel();
        initialGangHintLabel.setBounds(10, 500, 250, 30);
        initialGangHintLabel.setVisible(false);
        mainPanel.add(initialGangHintLabel);

        // Load prompt icons from Assets folder
        pengIcon = new ImageIcon(getClass().getResource("/Assets/mahjong/Peng.png"));
        gangIcon = new ImageIcon(getClass().getResource("/Assets/mahjong/Gang.png"));
        huIcon   = new ImageIcon(getClass().getResource("/Assets/mahjong/Hu.png"));
        passIcon = new ImageIcon(getClass().getResource("/Assets/mahjong/Pass.png"));

        mainFrame.setVisible(true);
    }

    /**
     * Refreshes the entire UI.
     */
    public void updateUI() {
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Updates the label displaying the remaining card count.
     *
     * @param count The current number of remaining cards.
     */
    public void updateRemainingCards(int count) {
        remainingCardsLabel.setText("Remaining Cards: " + count);
    }

    /**
     * Displays played cards in the center panel in the order they were played.
     * Adjustments for rotation based on player position can be added here.
     *
     * @param playedCards List of played cards.
     */
    public void displayPlayedCards(List<Card> playedCards) {
        playedCardsPanel.removeAll();
        for (Card card : playedCards) {
            // Load card image from its imagePath; no rotation is applied in this basic example.
            ImageIcon cardIcon = new ImageIcon(getClass().getResource(card.getImagePath()));
            JLabel cardLabel = new JLabel(cardIcon);
            playedCardsPanel.add(cardLabel);
        }
        playedCardsPanel.revalidate();
        playedCardsPanel.repaint();
    }

    /**
     * Displays a prompt icon for Peng, Gang, Hu, or Pass on the given card.
     * Here, we simulate this by showing a dialog. In a complete UI, you'd overlay
     * the icon on the card component.
     *
     * @param type The type of prompt ("Peng", "Gang", "Hu", or "Pass").
     * @param card The card for which the prompt is applicable.
     */
    public void showPengGangHuIcon(String type, Card card) {
        ImageIcon iconToShow = null;
        if ("Peng".equalsIgnoreCase(type)) {
            iconToShow = pengIcon;
        } else if ("Gang".equalsIgnoreCase(type)) {
            iconToShow = gangIcon;
        } else if ("Hu".equalsIgnoreCase(type)) {
            iconToShow = huIcon;
        } else if ("Pass".equalsIgnoreCase(type)) {
            iconToShow = passIcon;
        }
        if (iconToShow != null) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Showing " + type + " icon for card: " + card.toString(),
                    "Prompt", JOptionPane.INFORMATION_MESSAGE, iconToShow);
        }
    }

    /**
     * Displays the initial Gang hint, prompting the human player to choose Gang or Pass.
     */
    public void showInitialGangHint() {
        initialGangHintLabel.setText("Gang available! (Click Gang or Pass)");
        initialGangHintLabel.setIcon(gangIcon);
        initialGangHintLabel.setVisible(true);
    }

    /**
     * Hides the initial Gang hint.
     */
    public void hideInitialGangHint() {
        initialGangHintLabel.setVisible(false);
    }

    /**
     * Updates the turn indicator label to reflect the current player's turn.
     *
     * @param currentTurn The current player's index or turn information.
     */
    public void updateTurnIndicator(int currentTurn) {
        turnIndicatorLabel.setText("Turn: " + currentTurn);
    }

    /**
     * Displays a winner dialog with the provided winner information.
     *
     * @param winnerInfo Information about the winning order and scores.
     */
    public void showWinner(String winnerInfo) {
        JOptionPane.showMessageDialog(mainFrame, winnerInfo, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays a draw dialog when the game ends in a draw.
     */
    public void showDraw() {
        JOptionPane.showMessageDialog(mainFrame, "Draw! No winner.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}