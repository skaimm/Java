package lucky.luckytime.Model;

import java.util.Random;

public class RafflePoint {

    private long chance;
    private long chance1;
    private long chance2;
    private long chance3;
    private long chance4;
    private long chance5;
    private long max;
    private long max1;
    private long max2;
    private long max3;
    private long max4;
    private long max5;

    public RafflePoint(long chance, long chance1, long chance2, long chance3, long chance4, long chance5,
                       long max, long max1, long max2, long max3, long max4, long max5) {
        this.chance = chance;
        this.chance1 = chance1;
        this.chance2 = chance2;
        this.chance3 = chance3;
        this.chance4 = chance4;
        this.chance5 = chance5;
        this.max = max;
        this.max1 = max1;
        this.max2 = max2;
        this.max3 = max3;
        this.max4 = max4;
        this.max5 = max5;
    }

    public RafflePoint(){

    }

    public long getChance() {
        return chance;
    }

    public void setChance(long chance) {
        this.chance = chance;
    }

    public long getChance1() {
        return chance1;
    }

    public void setChance1(long chance1) {
        this.chance1 = chance1;
    }

    public long getChance2() {
        return chance2;
    }

    public void setChance2(long chance2) {
        this.chance2 = chance2;
    }

    public long getChance3() {
        return chance3;
    }

    public void setChance3(long chance3) {
        this.chance3 = chance3;
    }

    public long getChance4() {
        return chance4;
    }

    public void setChance4(long chance4) {
        this.chance4 = chance4;
    }

    public long getChance5() {
        return chance5;
    }

    public void setChance5(long chance5) {
        this.chance5 = chance5;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getMax1() {
        return max1;
    }

    public void setMax1(long max1) {
        this.max1 = max1;
    }

    public long getMax2() {
        return max2;
    }

    public void setMax2(long max2) {
        this.max2 = max2;
    }

    public long getMax3() {
        return max3;
    }

    public void setMax3(long max3) {
        this.max3 = max3;
    }

    public long getMax4() {
        return max4;
    }

    public void setMax4(long max4) {
        this.max4 = max4;
    }

    public long getMax5() {
        return max5;
    }

    public void setMax5(long max5) {
        this.max5 = max5;
    }

    public long getPointForRaffle() throws Error{
        Random rand = new Random();
        long point;
        double ch = (Math.random() * chance);
        if(ch < chance1){
            point = rand.nextInt((int) max1);
        }
        else if(ch <chance2){
            point = rand.nextInt((int) max2);
        }
        else if(ch <chance3){
            point = rand.nextInt((int) max3);
        }
        else if(ch<chance4){
            point = rand.nextInt((int) max3);
        }
        else if(ch<chance5){
            point = rand.nextInt((int) max4);
        }
        else{
            point = rand.nextInt((int) max);
        }

        return point;
    }

    @Override
    public String toString() {
        return "RafflePoint{" +
                "chance=" + chance +
                ", chance1=" + chance1 +
                ", chance2=" + chance2 +
                ", chance3=" + chance3 +
                ", chance4=" + chance4 +
                ", chance5=" + chance5 +
                ", max=" + max +
                ", max1=" + max1 +
                ", max2=" + max2 +
                ", max3=" + max3 +
                ", max4=" + max4 +
                ", max5=" + max5 +
                '}';
    }
}
