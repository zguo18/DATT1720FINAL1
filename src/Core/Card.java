package Core;

import javax.swing.ImageIcon;

public class Card {
    private String suit;      // "Wan", "Tiao", "Tong"
    private int number;       // 1-9
    private String imagePath; // image path
    private boolean isSelected;

    public Card(String suit, int number, String imagePath) {
        this.suit = suit;
        this.number = number;
        this.imagePath = imagePath;
        this.isSelected = false;
    }

    public String getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    @Override
    public String toString() {
        return suit + number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        return number == other.number && suit.equals(other.suit);
    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + number;
        return result;
    }
}