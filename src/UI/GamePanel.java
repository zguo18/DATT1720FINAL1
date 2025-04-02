package UI;

import Core.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * GamePanel.java
 * Responsible for drawing the human player's hand and registering interaction events.
 * Supports click-selection of Mahjong tiles (cards), discarding, and canceling.
 */
public class GamePanel extends JPanel {
    private HumanPlayer player;

    public GamePanel(HumanPlayer player) {
        this.player = player;
        this.setLayout(null); // Absolute positioning
        this.setBackground(new Color(230, 230, 230));
    }

    /**
     * Updates the panel with the current hand of the player.
     * Reconstructs card images with event bindings.
     * @param handCards List of cards to display
     */
    public void updateHand(List<Card> handCards) {
        this.removeAll();

        int xOffset = 10; // spacing from the left
        int spacing = 50; // space between cards

        for (Card card : handCards) {
            ImageIcon icon = new ImageIcon(getClass().getResource(card.getImagePath()));
            JLabel cardLabel = new JLabel(icon);
            cardLabel.setBounds(xOffset, 30, 50, 70);
            cardLabel.putClientProperty("card", card); // associate card with label
            cardLabel.addMouseListener(new InputHandler(player));
            this.add(cardLabel);
            xOffset += spacing;
        }

        this.revalidate();
        this.repaint();
    }
}
