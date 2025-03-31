package Core;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name, true);
    }

    /**
     * Called when it's the human player's turn.
     * In a real UI application, this turn would be driven by user events.
     */
    @Override
    public void playTurn() {
        System.out.println("It's " + name + "'s turn.");
        System.out.println("Current hand: " + handToString());
        // In a UI environment, wait for user input (handled by InputHandler).
    }

    /**
     * Called when the user selects a card (first left-click).
     * The card is marked as selected.
     *
     * @param card The card that is selected.
     */
    public void selectCard(Card card) {
        if (!card.isSelected()) {
            card.setSelected(true);
            System.out.println(name + " selected " + card);
            // The card's picture is moved upward in InputHandler.
        }
    }

    /**
     * Called when the user confirms the selected card to discard it (second left-click).
     * The card is removed from the hand.
     *
     * @param card The card to discard.
     */
    public void confirmDiscard(Card card) {
        if (card.isSelected()) {
            discardCard(card);
            System.out.println(name + " confirmed discard of " + card);
            // The game is notified that the card has been played in InputHandler.
        }
    }

    /**
     * Called when the user cancels the card selection (right-click).
     * The card is deselected.
     *
     * @param card The card to cancel selection.
     */
    public void cancelSelection(Card card) {
        if (card.isSelected()) {
            card.setSelected(false);
            System.out.println(name + " cancelled selection of " + card);
            // The card's picture is moved back to its original position in InputHandler.
        }
    }
}