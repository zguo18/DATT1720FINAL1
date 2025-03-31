package Core;

import Assets.AssetsManager;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Deck deck;
    private List<Player> players;
    private int currentTurn;
    private RuleChecker ruleChecker;
    private AssetsManager assetsManager;

    public Game() {
        ruleChecker = new RuleChecker();
        assetsManager = new AssetsManager();
        players = new ArrayList<>();
        currentTurn = 0;
    }

    public void startGame() {
        // Load tile and icon resources
        assetsManager.loadAssets();

        // Initialize and shuffle the deck
        deck = new Deck();
        deck.shuffle();

        // Initialize players: one human (dealer with 14 cards) and 3 AI players (each 13 cards)
        players.add(new HumanPlayer("Human"));
        players.add(new AIPlayer("AI 1"));
        players.add(new AIPlayer("AI 2"));
        players.add(new AIPlayer("AI 3"));

        // Deal initial cards
        dealInitialCards();

        // Check if the human player's initial hand meets the Gang condition
        HumanPlayer human = (HumanPlayer) players.get(0);
        if (ruleChecker.validateGang(human, null)) {
            System.out.println("Initial hand meets Gang condition; " + human.getName() + " can choose Gang or Pass.");
            // Here you can prompt the player to choose; currently it only prints a message.
        }

        // Simulate game flow until the deck is empty
        while (!deck.isEmpty()) {
            processPlayerTurn();
            nextTurn();
        }

        // End game once deck is empty
        endGame();
    }

    private void dealInitialCards() {
        // Deal 13 cards to each player
        for (int i = 0; i < 13; i++) {
            for (Player p : players) {
                p.drawCard(deck);
            }
        }
        // Dealer gets one extra card (14 cards total)
        players.get(0).drawCard(deck);
    }

    public void processPlayerTurn() {
        Player currentPlayer = players.get(currentTurn);
        System.out.println("Current turn: " + currentPlayer.getName());

        // Each turn, draw a card if the deck is not empty
        if (!deck.isEmpty()) {
            currentPlayer.drawCard(deck);
        }

        // Simulate discarding a card:
        // Human player discards the first card in hand; AI player discards the rightmost card.
        Card discarded = null;
        if (currentPlayer.isHuman()) {
            discarded = currentPlayer.getHandCards().get(0);
            ((HumanPlayer) currentPlayer).confirmDiscard(discarded);
        } else {
            discarded = ((AIPlayer) currentPlayer).autoDiscard();
        }
        System.out.println(currentPlayer.getName() + " discards: " + discarded);
    }

    public void nextTurn() {
        currentTurn = (currentTurn + 1) % players.size();
    }

    public void endGame() {
        System.out.println("Game over.");
        // Here you could add logic to display winning order, scores, etc.
    }

    public void resetGame() {
        // Reset game state and restart the game
        currentTurn = 0;
        players.clear();
        deck = new Deck();
        deck.shuffle();
        startGame();
    }
}