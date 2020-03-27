public class AnimateCardThread implements Runnable{
    Frame frame;
    int handNum;
    int xDest;
    boolean isRunning;
    
    public AnimateCardThread(Frame frame, int handNum, int xDest){
        this.frame = frame;
        this.xDest = xDest;
        this.handNum = handNum;
        isRunning = true;
    }
    //translates jlabel to xDest
    public void run(){
        frame.isAnimating = true;
        int xStart= frame.handLabels.get(handNum).getX();
        while(isRunning) {
            for(int i = xStart;i>=xDest;i-=1){
                frame.handLabels.get(handNum).setBounds(i,frame.handLabels.get(handNum).getY(),130,200);
                frame.repaint();
                if(i==xDest){
                    isRunning = false;
                    frame.isAnimating = false;
                }
                try{
                    Thread.currentThread().sleep(5);
                }
                catch(InterruptedException e){}
            }
        }
    }
}