package Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RuleChecker {

    /**
     * Validates whether a Peng call is available.
     * For Peng, the player must have at least two cards identical to the given card.
     *
     * @param player the player attempting Peng
     * @param card   the card from the discard (or table) to be matched
     * @return true if the player can call Peng, false otherwise
     */
    public boolean validatePeng(Player player, Card card) {
        int count = 0;
        for (Card c : player.getHandCards()) {
            if (c.getSuit().equals(card.getSuit()) && c.getNumber() == card.getNumber()) {
                count++;
            }
        }
        return count >= 2;
    }

    /**
     * Validates whether a Gang call is available.
     * If the card parameter is null, check the player's hand for any card that appears at least 4 times.
     * If a card is provided, check if the player's hand contains at least three copies of that card.
     *
     * @param player the player attempting Gang
     * @param card   the card involved in the Gang (or null to check the entire hand)
     * @return true if the Gang condition is met, false otherwise
     */
    public boolean validateGang(Player player, Card card) {
        if (card == null) {
            // Check entire hand for any four-of-a-kind.
            for (Card c : player.getHandCards()) {
                int count = 0;
                for (Card inner : player.getHandCards()) {
                    if (c.getSuit().equals(inner.getSuit()) && c.getNumber() == inner.getNumber()) {
                        count++;
                    }
                }
                if (count >= 4) {
                    return true;
                }
            }
            return false;
        } else {
            // Check if the player's hand has at least three copies of the given card.
            int count = 0;
            for (Card c : player.getHandCards()) {
                if (c.getSuit().equals(card.getSuit()) && c.getNumber() == card.getNumber()) {
                    count++;
                }
            }
            return count >= 3;
        }
    }

    /**
     * Validates whether the player can declare Hu (win).
     * This method assumes the winning method is a "ping hu" structure: 4 melds (each being a sequence or triplet)
     * plus one pair.
     *
     * @param player the player attempting to win
     * @param card   an additional card to include (e.g., the drawn card); if not already in hand, it is added
     * @return true if the hand satisfies Hu conditions, false otherwise
     */
    public boolean validateHu(Player player, Card card) {
        List<Card> handCopy = new ArrayList<>(player.getHandCards());
        // Add the card if it's not already in the hand
        if (card != null && !containsCard(handCopy, card)) {
            handCopy.add(card);
        }
        // A winning hand should have 14 cards.
        if (handCopy.size() != 14) {
            return false;
        }

        // Sort hand by suit and number.
        Collections.sort(handCopy, new Comparator<Card>() {
            @Override
            public int compare(Card c1, Card c2) {
                int suitCompare = c1.getSuit().compareTo(c2.getSuit());
                if (suitCompare != 0) {
                    return suitCompare;
                }
                return Integer.compare(c1.getNumber(), c2.getNumber());
            }
        });

        // Try to split the hand into one pair and four melds.
        return checkHuRecursive(handCopy);
    }

    private boolean containsCard(List<Card> cards, Card target) {
        for (Card c : cards) {
            if (c.getSuit().equals(target.getSuit()) && c.getNumber() == target.getNumber()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to partition the hand into one pair (the head) and melds (the body).
     *
     * @param cards the sorted list of cards
     * @return true if the hand can be partitioned properly, false otherwise
     */
    private boolean checkHuRecursive(List<Card> cards) {
        if (cards.isEmpty()) {
            // Successfully partitioned all cards.
            return true;
        }

        // The hand should be split into one pair (head) and the rest (which must be divisible by 3).
        for (int i = 0; i < cards.size() - 1; i++) {
            Card first = cards.get(i);
            Card second = cards.get(i + 1);
            if (first.getSuit().equals(second.getSuit()) && first.getNumber() == second.getNumber()) {
                // Found a pair; remove them and check if the rest can form melds.
                List<Card> remaining = new ArrayList<>(cards);
                remaining.remove(i + 1);
                remaining.remove(i);
                if (canPartition(remaining)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Recursively checks if the remaining cards can be partitioned into melds (each of 3 cards).
     *
     * @param cards the list of remaining cards (should be a multiple of 3 in size)
     * @return true if the cards can be partitioned into melds, false otherwise
     */
    private boolean canPartition(List<Card> cards) {
        if (cards.isEmpty()) {
            return true;
        }
        if (cards.size() % 3 != 0) {
            return false;
        }

        Card first = cards.get(0);
        // Try to remove a triplet (pong) first.
        int count = 0;
        for (Card c : cards) {
            if (c.getSuit().equals(first.getSuit()) && c.getNumber() == first.getNumber()) {
                count++;
            }
        }
        if (count >= 3) {
            List<Card> remaining = removeCards(cards, first, 3);
            if (canPartition(remaining)) {
                return true;
            }
        }

        // If triplet removal fails, try to remove a sequence (chow) if applicable.
        if (isSequenceCapable(first)) {
            Card second = findCard(cards, first.getSuit(), first.getNumber() + 1);
            Card third  = findCard(cards, first.getSuit(), first.getNumber() + 2);
            if (second != null && third != null) {
                List<Card> remaining = new ArrayList<>(cards);
                remaining.remove(first);
                remaining.remove(second);
                remaining.remove(third);
                if (canPartition(remaining)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the card's suit can form sequences (only Wan, Tiao, and Tong).
     *
     * @param card the card to check
     * @return true if the card can form a sequence, false otherwise
     */
    private boolean isSequenceCapable(Card card) {
        String suit = card.getSuit();
        return (suit.equals("Wan") || suit.equals("Tiao") || suit.equals("Tong")) && card.getNumber() <= 7;
    }

    /**
     * Finds a card in the list that matches the specified suit and number.
     *
     * @param cards the list to search
     * @param suit  the suit to match
     * @param number the number to match
     * @return the matching card, or null if not found
     */
    private Card findCard(List<Card> cards, String suit, int number) {
        for (Card c : cards) {
            if (c.getSuit().equals(suit) && c.getNumber() == number) {
                return c;
            }
        }
        return null;
    }

    /**
     * Removes the first 'count' occurrences of cards that match the target (by suit and number)
     * from the given list.
     *
     * @param cards  the list of cards
     * @param target the target card to remove
     * @param count  the number of cards to remove
     * @return a new list of cards with the specified cards removed
     */
    private List<Card> removeCards(List<Card> cards, Card target, int count) {
        List<Card> copy = new ArrayList<>(cards);
        int removed = 0;
        for (int i = 0; i < copy.size() && removed < count; ) {
            Card c = copy.get(i);
            if (c.getSuit().equals(target.getSuit()) && c.getNumber() == target.getNumber()) {
                copy.remove(i);
                removed++;
            } else {
                i++;
            }
        }
        return copy;
    }
}