import javax.swing.*;
public class Card{

    private int suit;
    private int value;
    private ImageIcon image;

    public Card(int value, int suit, ImageIcon image){
        this.suit = suit;
        this.value = value;
        this.image = image;
    }
    public ImageIcon getImage(){
        return image;
    }
    public int getSuit(){
        return suit;
    }
    public String getSuitName(){
        switch(suit){
            case 0:
                return "Clubs";
            case 1:
                return "Hearts";
            case 2:
                return "Spades";
            case 3:
                return "Diamonds";
            default:
                return "";
        }
    }
    public int getValue(){
        return value;
    }
    public String getValueName(){
        switch(value){
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            case 14:
                return "Ace";
            default:
                return String.valueOf(value);
        }

    }
}