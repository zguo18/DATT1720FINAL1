package Core;

public class AIPlayer extends Player {

    public AIPlayer(String name) {
        super(name, false);
    }

    @Override
    public void playTurn() {
        System.out.println("It's " + name + "'s turn.");
        // Simulate a 2-second delay for AI thinking
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        autoDiscard();
    }

    /**
     * Automatically discards the rightmost card in the hand.
     * @return The card that was discarded.
     */
    public Card autoDiscard() {
        if (!handCards.isEmpty()) {
            // Discard the last card in the hand (rightmost card)
            Card card = handCards.get(handCards.size() - 1);
            discardCard(card);
            System.out.println(name + " auto-discarded: " + card);
            return card;
        }
        System.out.println(name + " has no card to discard.");
        return null;
    }
}