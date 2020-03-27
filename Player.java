public class Player{
    private int chipCount;
    public Player(){
        chipCount = 20;
    }
    public int getChipCount(){
        return chipCount;
    }
    public void setChipCount(int amount){
        chipCount = amount;
    }
    public void addChipCount(int amount){
        chipCount+=amount;
    }
    public void subtractChipCount(int amount){
        chipCount-=amount;
    }
}