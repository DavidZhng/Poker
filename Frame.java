import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.plaf.ColorUIResource;
public class Frame extends JFrame implements ActionListener, MouseListener{
    public static final int F_WIDTH = 1000;
    public static final int F_LENGTH = 900;

    ArrayList<Card> cards = new ArrayList<Card>();
    ArrayList<Card> deck = new ArrayList<Card>();
    ArrayList<Card> hand = new ArrayList<Card>();

    ArrayList<JLabel> handLabels = new ArrayList<JLabel>();

    JButton btnStart, btnDeal;
    JLabel lblDealer, lblSpeechBubble, lblChipCount, lblSpeech;
    TableComponent tableComponent;

    Player player;

    Boolean isAnimating;

    public Frame(){
        super("Poker");
        //main menu
        JLabel lblTitle = new JLabel(new ImageIcon(new ImageIcon("Images/PokerTitle.png").getImage().getScaledInstance(800,200,Image.SCALE_DEFAULT)));
        lblTitle.setBounds(100, 100, 800, 200);
        JLabel lblPicture = new JLabel(new ImageIcon(new ImageIcon("Images/honor_clubs.png").getImage().getScaledInstance(400,200,Image.SCALE_DEFAULT)));
        lblPicture.setBounds(F_WIDTH/2-200, 175, 400, 200);

        btnStart= new JButton("PLAY");
        btnStart.setBounds(F_WIDTH/2-75,400,150,75);
        btnStart.addActionListener(this);

        lblSpeech = new JLabel("", SwingConstants.HORIZONTAL);
        lblSpeech.setBounds(120, -25, 220, 300);
        lblSpeech.setFont(new Font("Courier", Font.BOLD,14));

        add(btnStart);
        add(lblTitle);
        add(lblPicture);
        add(new TableComponent());
        addMouseListener(this);

        setSize(F_WIDTH,F_LENGTH);
        getContentPane().setBackground(new ColorUIResource(0, 170, 0));
        setLayout(null);
        setResizable(false);
        setVisible(true);
    }
    //for if jbuttons are clicked
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==btnStart){
            getContentPane().removeAll();
            getContentPane().repaint();
            getContentPane().revalidate();
            loadGame();
        }
        else if(e.getSource()==btnDeal){
            lblSpeechBubble.setVisible(false);
            //makes sure player has adequate funds
            if(player.getChipCount()>=5){
                player.subtractChipCount(5);
                updateChips();
                resetDeck();
                resetHand();
                btnDeal.setVisible(false);
                new Thread(new DealThread(this,true)).start();   
            }
            else{
                say("You don't have enough chips. Restart the game.");
            }
        }
    }
    //for if jlabels are clicked
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==lblDealer&&lblSpeech.getText().equals("<html>Click on the cards you want to discard. When you are done, click my beard.</html>")){
            lblSpeechBubble.setVisible(false);
            removeSelectedCards();
        }
        //for selecting cards to discard
        else if(handLabels.size()>0&&e.getSource()==handLabels.get(0)&&lblSpeech.getText().equals("<html>Click on the cards you want to discard. When you are done, click my beard.</html>")){
            handLabels.get(0).getComponent(0).setVisible(!handLabels.get(0).getComponent(0).isVisible());
        }
        else if(handLabels.size()>1&&e.getSource()==handLabels.get(1)&&lblSpeech.getText().equals("<html>Click on the cards you want to discard. When you are done, click my beard.</html>")){
            handLabels.get(1).getComponent(0).setVisible(!handLabels.get(1).getComponent(0).isVisible());
        }
        else if(handLabels.size()>2&&e.getSource()==handLabels.get(2)&&lblSpeech.getText().equals("<html>Click on the cards you want to discard. When you are done, click my beard.</html>")){
            handLabels.get(2).getComponent(0).setVisible(!handLabels.get(2).getComponent(0).isVisible());
        }
        else if(handLabels.size()>3&&e.getSource()==handLabels.get(3)&&lblSpeech.getText().equals("<html>Click on the cards you want to discard. When you are done, click my beard.</html>")){
            handLabels.get(3).getComponent(0).setVisible(!handLabels.get(3).getComponent(0).isVisible());
        }
        else if(handLabels.size()>4&&e.getSource()==handLabels.get(4)&&lblSpeech.getText().equals("<html>Click on the cards you want to discard. When you are done, click my beard.</html>")){
            handLabels.get(4).getComponent(0).setVisible(!handLabels.get(4).getComponent(0).isVisible());
        }
    }
    //unused methods that need to be here anyway
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    public void loadGame(){

        player = new Player();
        isAnimating = false;
        //graphics stuff
        getContentPane().setBackground(Color.BLACK);
        tableComponent = new TableComponent();
        tableComponent.setBounds(-150, 350, 1500, 1500);

        lblDealer = new JLabel(new ImageIcon(new ImageIcon("Images/JiWangGrandpa.png").getImage().getScaledInstance(400,400,Image.SCALE_DEFAULT)));
        lblDealer.setBounds(F_WIDTH/2-200, 0, 400, 400);
        lblDealer.addMouseListener(this);

        lblSpeechBubble = new JLabel(new ImageIcon(new ImageIcon("Images/SpeechBubble.png").getImage().getScaledInstance(450,300,Image.SCALE_DEFAULT)));
        lblSpeechBubble.setBounds(15, -50, 450, 300);
        say("Hello, my name is JI WANG and I will be your dealer today.");

        JLabel lblChips = new JLabel(new ImageIcon(new ImageIcon("Images/poker-chip-stacks.png").getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT)));
        lblChips.setBounds(800, 25, 200, 200);
        lblChipCount = new JLabel(String.valueOf(player.getChipCount()), SwingConstants.CENTER);
        lblChipCount.setFont(new Font("Courier", Font.BOLD,18));
        lblChipCount.setForeground(Color.WHITE);
        lblChipCount.setBounds(800, 115, 200, 200);

        btnDeal = new JButton("DEAL");
        btnDeal.setBounds(250,100,200,100);
        btnDeal.setFont(new Font("Courier", Font.BOLD,30));
        btnDeal.addActionListener(this);

        add(tableComponent);
        add(lblDealer);
        add(lblSpeechBubble);
        tableComponent.add(lblChips);
        tableComponent.add(lblChipCount);
        tableComponent.add(btnDeal);
        lblSpeechBubble.add(lblSpeech);

        lblSpeechBubble.setVisible(false);
        lblChips.setVisible(false);
        lblChipCount.setVisible(false);
        btnDeal.setVisible(false);
        //for the sequence of things that the dealer says
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                lblSpeechBubble.setVisible(true);
                Timer timer2 = new Timer(4000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        say("To your right are the number of chips you currently have. You will need 5 chips to play. Click DEAL to play.");
                        lblChips.setVisible(true);
                        lblChipCount.setVisible(true);
                        btnDeal.setVisible(true);
                        Timer timer3 = new Timer(7000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent arg0) {
                                lblSpeechBubble.setVisible(false);
                            }
                        });
                        timer3.setRepeats(false);
                        timer3.start();
                    }
                });
                timer2.setRepeats(false);
                timer2.start();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    //updates the jlabel text of lblChipCount
    public void updateChips(){
        lblChipCount.setText(String.valueOf(player.getChipCount()));
    }
    //displays speech bubble along with something
    public void say(String something){
        lblSpeechBubble.setVisible(true);
        lblSpeech.setText("<html>" + something + "</html>");
    }
    //resets all arrayLists involving the hand
    public void resetHand(){
        hand.clear();
        if(handLabels.size()>0){
            for(int i = 0;i<5;i++){
                tableComponent.remove(handLabels.get(i));
            }
            repaint();
        }
        handLabels.clear();
    }
    //resets the deck and adds all 52 cards
    public void resetDeck(){
        cards.clear();
        cards.add(new Card(2,0,new ImageIcon(new ImageIcon("Images/2C.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(2,1,new ImageIcon(new ImageIcon("Images/2H.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(2,2,new ImageIcon(new ImageIcon("Images/2S.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(2,3,new ImageIcon(new ImageIcon("Images/2D.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(3,0,new ImageIcon(new ImageIcon("Images/3C.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(3,1,new ImageIcon(new ImageIcon("Images/3H.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(3,2,new ImageIcon(new ImageIcon("Images/3S.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(3,3,new ImageIcon(new ImageIcon("Images/3D.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(4,0,new ImageIcon(new ImageIcon("Images/4C.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(4,1,new ImageIcon(new ImageIcon("Images/4H.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(4,2,new ImageIcon(new ImageIcon("Images/4S.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(4,3,new ImageIcon(new ImageIcon("Images/4D.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(5,0,new ImageIcon(new ImageIcon("Images/5C.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(5,1,new ImageIcon(new ImageIcon("Images/5H.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(5,2,new ImageIcon(new ImageIcon("Images/5S.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(5,3,new ImageIcon(new ImageIcon("Images/5D.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(6,0,new ImageIcon(new ImageIcon("Images/6C.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(6,1,new ImageIcon(new ImageIcon("Images/6H.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(6,2,new ImageIcon(new ImageIcon("Images/6S.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(6,3,new ImageIcon(new ImageIcon("Images/6D.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(7,0,new ImageIcon(new ImageIcon("Images/7C.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(7,1,new ImageIcon(new ImageIcon("Images/7H.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(7,2,new ImageIcon(new ImageIcon("Images/7S.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(7,3,new ImageIcon(new ImageIcon("Images/7D.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(8,0,new ImageIcon(new ImageIcon("Images/8C.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(8,1,new ImageIcon(new ImageIcon("Images/8H.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(8,2,new ImageIcon(new ImageIcon("Images/8S.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(8,3,new ImageIcon(new ImageIcon("Images/8D.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(9,0,new ImageIcon(new ImageIcon("Images/9C.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(9,1,new ImageIcon(new ImageIcon("Images/9H.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(9,2,new ImageIcon(new ImageIcon("Images/9S.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(9,3,new ImageIcon(new ImageIcon("Images/9D.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(10,0,new ImageIcon(new ImageIcon("Images/10C.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(10,1,new ImageIcon(new ImageIcon("Images/10H.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(10,2,new ImageIcon(new ImageIcon("Images/10S.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(10,3,new ImageIcon(new ImageIcon("Images/10D.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(11,0,new ImageIcon(new ImageIcon("Images/JC.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(11,1,new ImageIcon(new ImageIcon("Images/JH.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(11,2,new ImageIcon(new ImageIcon("Images/JS.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(11,3,new ImageIcon(new ImageIcon("Images/JD.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(12,0,new ImageIcon(new ImageIcon("Images/QC.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(12,1,new ImageIcon(new ImageIcon("Images/QH.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(12,2,new ImageIcon(new ImageIcon("Images/QS.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(12,3,new ImageIcon(new ImageIcon("Images/QD.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(13,0,new ImageIcon(new ImageIcon("Images/KC.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(13,1,new ImageIcon(new ImageIcon("Images/KH.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(13,2,new ImageIcon(new ImageIcon("Images/KS.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(13,3,new ImageIcon(new ImageIcon("Images/KD.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(14,0,new ImageIcon(new ImageIcon("Images/AC.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(14,1,new ImageIcon(new ImageIcon("Images/AH.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(14,2,new ImageIcon(new ImageIcon("Images/AS.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));
        cards.add(new Card(14,3,new ImageIcon(new ImageIcon("Images/AD.png").getImage().getScaledInstance(130, 200,Image.SCALE_DEFAULT))));

        deck.clear();
        //shuffles deck
        for(int i = 0;i<52;i++){
            int cardIndex = (int)(Math.random()*cards.size());
            deck.add(cards.get(cardIndex));
            cards.remove(cardIndex);
        }
    }
    //removes selected cards, animates cards that need to move to the left, and deals new cards
    public void removeSelectedCards(){
        for(int i = 0;i<hand.size();i++){
            if(handLabels.get(i).getComponent(0).isVisible()){
                tableComponent.remove(handLabels.get(i));
                repaint();
                handLabels.remove(i);
                hand.remove(i);
                i--;
            }
        }
        for(int i = 0;i<hand.size();i++){
            if(handLabels.get(i).getX()!=(275+i*150)){
                new Thread(new AnimateCardThread(this,i,275+i*150)).start();   
            }
        }
        new Thread(new DealThread(this, false)).start(); 
    }
    //calculates how many chips were won with the hand
    public void calculateChipsWon(){
        int countOfSameValue = 0;
        int countOfSameSuit = 0;
        int countOfPairs = 0;
        boolean hasStraight = false;
        boolean hasOnePair = false;
        boolean hasThreeOfAKind = false;
        boolean hasFourOfAKind = false;
        String suitOfFirstCard = hand.get(0).getSuitName();
        ArrayList<String> valuesForRoyalFlush = new ArrayList<String>();
        valuesForRoyalFlush.add("Jack");valuesForRoyalFlush.add("Queen");valuesForRoyalFlush.add("King");valuesForRoyalFlush.add("Ace");valuesForRoyalFlush.add("10");
        int[][] possibleStraightCombos = new int[5][5];
        int valueOfFirstCard = hand.get(0).getValue();
        //tests for a straight
        for(int i = 0; i<5;i++){
            int straightCount = 0;
            for(int j = 0;j<5;j++){
                int temp = valueOfFirstCard-(i-j);
                if(temp > 14){
                    temp-= 13;
                }
                else if(temp<2){
                    temp+= 13;
                }
                possibleStraightCombos[i][j] = temp;
                for(int k = 0;k<5;k++){
                    if(hand.get(k).getValue()==possibleStraightCombos[i][j]){
                        straightCount++;
                        break;
                    }
                }
            }
            if(straightCount==5){
                hasStraight = true;
            }
            
        }
        for(int j = 0;j<hand.size();j++){
            if(hand.get(j).getSuitName().equals(suitOfFirstCard)){
                countOfSameSuit++;
                //tests for royal flush
                for(int i = 0;i<valuesForRoyalFlush.size();i++){
                    if(hand.get(j).getValueName().equals(valuesForRoyalFlush.get(i))){
                        valuesForRoyalFlush.remove(i);
                    }
                }
            }
            for(int i = 0;i<hand.size();i++){
                if(i!=j){
                    if(hand.get(j).getValue()==hand.get(i).getValue()){
                        countOfSameValue++;
                        if(countOfSameValue==1){
                            countOfPairs++;
                        }
                    }
                }
            }
            if(countOfSameValue==3)hasFourOfAKind = true;
            else if(countOfSameValue==2)hasThreeOfAKind = true;
            else if(countOfSameValue==1)hasOnePair = true;
            countOfSameValue = 0; 
        }
        if(valuesForRoyalFlush.size()==0){
            player.addChipCount(250);
            say("Royal Flush!You won 250 tokens. Click DEAL to play again.");
            updateChips();
        }
        else if(countOfSameSuit==5&&hasStraight){
            say("Straight Flush!You won 50 tokens. Click DEAL to play again.");
            player.addChipCount(50);
            updateChips();
        }
        else if(hasFourOfAKind){
            say("Four of a Kind!You won 25 tokens. Click DEAL to play again.");
            player.addChipCount(25);
            updateChips();
        }
        else if(hasThreeOfAKind&&countOfPairs>=5){
            say("Full house!You won 6 tokens. Click DEAL to play again.");
            player.addChipCount(6);
            updateChips();
        }
        else if(countOfSameSuit==5){
            say("Flush!You won 5 tokens. Click DEAL to play again.");
            player.addChipCount(5);
            updateChips();
        }
        else if(hasStraight){
            say("Straight!You won 4 tokens. Click DEAL to play again.");
            player.addChipCount(4);
            updateChips();
        }
        else if(hasThreeOfAKind){
            say("Three of a Kind!You won 3 tokens. Click DEAL to play again.");
            player.addChipCount(3);
            updateChips();
        }
        else if(countOfPairs>=4){
            say("Two pairs!You won 2 tokens. Click DEAL to play again.");
            player.addChipCount(2);
            updateChips();
        }
        else if(hasOnePair){
            say("One pair!You won 1 token. Click DEAL to play again.");
            player.addChipCount(1);
            updateChips();
        }
        else{
            say("You didn't win anything. Click DEAL to play again.");
        }
        btnDeal.setVisible(true);
    }
}