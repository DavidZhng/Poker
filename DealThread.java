import javax.swing.*;
import java.awt.*;

public class DealThread implements Runnable {
    Frame frame;
    boolean isRunning;
    int handSize;
    boolean isInitialDeal;
    public DealThread(Frame frame, boolean isInitialDeal){
        this.frame = frame;
        isRunning = true;
        handSize = frame.hand.size();
        this.isInitialDeal = isInitialDeal;
    }
    //adds and displays enough cards for a full hand
    public void run() {
        while(isRunning){
            try
            {
                Thread.currentThread().sleep(100);
            }
            catch(InterruptedException ex){}
            //waits until all AnimateCardThreads are complete
            if(!frame.isAnimating){
                for(int i = 0;i<5-handSize;i++){
                    frame.hand.add(frame.deck.remove(i));
                    frame.handLabels.add(new JLabel(frame.hand.get(handSize+i).getImage()));
                    frame.handLabels.get(handSize+i).setBounds(275+(handSize+i)*150,250,130,200);
                    frame.handLabels.get(handSize+i).addMouseListener(frame);
                    frame.tableComponent.add(frame.handLabels.get(handSize+i));
                    JLabel lblCross = new JLabel(new ImageIcon(new ImageIcon("Images/x.png").getImage().getScaledInstance(130,200,Image.SCALE_DEFAULT)));
                    lblCross.setBounds(0,0,130,200);
                    frame.handLabels.get(handSize+i).add(lblCross);
                    lblCross.setVisible(false);
                    frame.repaint();
                    try
                    {
                        Thread.currentThread().sleep(500);
                    }
                    catch(InterruptedException ex){}
                }
                if(isInitialDeal){
                    frame.say("Click on the cards you want to discard. When you are done, click my beard.");
                }
                else{
                    frame.calculateChipsWon();
                }
                isRunning = false;
            }
        }
    }
}