package UI;

import Core.Card;
import Core.HumanPlayer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 * InputHandler is responsible for handling mouse events on card components.
 * It translates mouse clicks into actions for the human player, including:
 * - First left-click: select a card (card moves upward).
 * - Second left-click (when already selected): confirm and discard the card.
 * - Right-click: cancel the card selection (card moves back to original position).
 */
public class InputHandler extends MouseAdapter {
    private HumanPlayer humanPlayer;

    public InputHandler(HumanPlayer player) {
        this.humanPlayer = player;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if (source instanceof JLabel) {
            JLabel cardLabel = (JLabel) source;
            // Retrieve the associated Card object stored as a client property.
            Card card = (Card) cardLabel.getClientProperty("card");
            if (card == null) {
                return;
            }

            // Left mouse button handling
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (!card.isSelected()) {
                    // First left-click: select the card
                    humanPlayer.selectCard(card);
                    // Animate the card upward by half its height
                    cardLabel.setLocation(cardLabel.getX(), cardLabel.getY() - cardLabel.getHeight() / 2);
                } else {
                    // Second left-click when already selected: confirm discard
                    humanPlayer.confirmDiscard(card);
                    // Remove the card label from its parent container after discarding
                    if (cardLabel.getParent() != null) {
                        cardLabel.getParent().remove(cardLabel);
                        cardLabel.getParent().revalidate();
                        cardLabel.getParent().repaint();
                    }
                }
            }
            // Right mouse button handling
            else if (e.getButton() == MouseEvent.BUTTON3) {
                if (card.isSelected()) {
                    // Right-click cancels the selection
                    humanPlayer.cancelSelection(card);
                    // Reset the card label's position by moving it down half its height
                    cardLabel.setLocation(cardLabel.getX(), cardLabel.getY() + cardLabel.getHeight() / 2);
                }
            }
        }
    }
}