package lucky.luckytime.Model;


import java.util.Random;

public class Box {

    private long max_rip;
    private long min_rip;
    private long sans_kazan;
    private long sans_sira;
    private long store_price;
    private String type;

    public Box(long max_rip, long min_rip, long sans_kazan, long sans_sira, long store_price, String type) {
        this.max_rip = max_rip;
        this.min_rip = min_rip;
        this.sans_kazan = sans_kazan;
        this.sans_sira = sans_sira;
        this.store_price = store_price;
        this.type = type;
    }

    public Box(){

    }

    public long openBox(){
        Random rand = new Random();
        long max =getMax_rip();
        long min =getMin_rip();
        long maxmin = max -min;
        long n=0;
        double chance = (Math.random() * 100);
        if(chance < 30){
            n = rand.nextInt((int) maxmin/5)+min;
        }
        else if(chance <60){
            n = rand.nextInt((int) maxmin/3)+min;
        }
        else if(chance <80){
            n = rand.nextInt((int) maxmin/2)+min;

        }
        else {
            n = rand.nextInt((int) maxmin)+min;
        }

        return n;
    }

    public long getMax_rip() {
        return max_rip;
    }

    public void setMax_rip(long max_rip) {
        this.max_rip = max_rip;
    }

    public long getMin_rip() {
        return min_rip;
    }

    public void setMin_rip(long min_rip) {
        this.min_rip = min_rip;
    }

    public long getSans_kazan() {
        return sans_kazan;
    }

    public void setSans_kazan(long sans_kazan) {
        this.sans_kazan = sans_kazan;
    }

    public long getSans_sira() {
        return sans_sira;
    }

    public void setSans_sira(long sans_sira) {
        this.sans_sira = sans_sira;
    }

    public long getStore_price() {
        return store_price;
    }

    public void setStore_price(long store_price) {
        this.store_price = store_price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Box{" +
                "max_rip=" + max_rip +
                ", min_rip=" + min_rip +
                ", sans_kazan=" + sans_kazan +
                ", sans_sira=" + sans_sira +
                ", store_price=" + store_price +
                ", type='" + type + '\'' +
                '}';
    }
}

