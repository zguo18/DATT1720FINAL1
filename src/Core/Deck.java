package Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        // Generate 108 cards: for each suit ("Wan", "Tiao", "Tong"), numbers 1-9, 4 copies each.
        String[] suits = {"Wan", "Tiao", "Tong"};
        for (String suit : suits) {
            for (int number = 1; number <= 9; number++) {
                for (int i = 0; i < 4; i++) {
                    // Construct image path, e.g., "/Assets/mahjong/Wan1.png"
                    String imagePath = "/Assets/mahjong/" + suit + number + ".png";
                    cards.add(new Card(suit, number, imagePath));
                }
            }
        }
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the top of the deck.
     * @return the drawn card, or null if the deck is empty.
     */
    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    /**
     * Draws a card from the bottom of the deck.
     * This is used for gang operations.
     * @return the drawn card, or null if the deck is empty.
     */
    public Card drawLastCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

    /**
     * Checks whether the deck is empty.
     * @return true if the deck has no cards left.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Returns the number of cards remaining in the deck.
     * @return the count of remaining cards.
     */
    public int remainingCards() {
        return cards.size();
    }
}