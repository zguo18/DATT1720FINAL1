package Core;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected String name;
    protected List<Card> handCards;
    protected boolean isHuman;

    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
        this.handCards = new ArrayList<>();
    }

    /**
     * Draw a card from the given deck.
     * If a card is drawn, add it to the hand and print a message.
     */
    public void drawCard(Deck deck) {
        Card card = deck.drawCard();
        if (card != null) {
            handCards.add(card);
            System.out.println(name + " draws card: " + card);
        } else {
            System.out.println("Deck is empty. " + name + " cannot draw a card.");
        }
    }

    /**
     * Add a specific card to the player's hand.
     */
    public void drawCard(Card card) {
        if (card != null) {
            handCards.add(card);
            System.out.println(name + " draws card: " + card);
        }
    }

    /**
     * Discard the specified card from the player's hand.
     */
    public void discardCard(Card card) {
        if (handCards.contains(card)) {
            handCards.remove(card);
            System.out.println(name + " discards card: " + card);
        } else {
            System.out.println("Card " + card + " not found in " + name + "'s hand.");
        }
    }

    /**
     * Return the player's current hand.
     */
    public List<Card> getHandCards() {
        return handCards;
    }

    /**
     * Return the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns true if this is a human player.
     */
    public boolean isHuman() {
        return isHuman;
    }

    /**
     * Clear the player's hand (useful for game reset).
     */
    public void clearHand() {
        handCards.clear();
    }

    /**
     * Returns a string representation of the player's hand.
     */
    public String handToString() {
        StringBuilder sb = new StringBuilder();
        for (Card c : handCards) {
            sb.append(c.toString()).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return name + "'s hand: " + handToString();
    }

    /**
     * Abstract method for executing a player's turn.
     * Subclasses (HumanPlayer and AIPlayer) must implement this.
     */
    public abstract void playTurn();
}