package lucky.luckytime.Model;

public class Odul {

    private long sira1;
    private double sira1odul;
    private long sira1tane;
    private long sira2;
    private String sira2odul;
    private long sira2tane;
    private long sira3;
    private String sira3odul;
    private long sira3tane;
    private long sira4;
    private long sira4odul;
    private long sira5;
    private long sira5odul;
    private long sira6;
    private long sira6odul;
    private boolean dunonay;
    private String wonname;
    private String date;

    public Odul(long sira1, double sira1odul, long sira1tane, long sira2, String sira2odul, long sira2tane, long sira3,
                String sira3odul, long sira3tane, long sira4, long sira4odul, long sira5, long sira5odul, long sira6,
                long sira6odul, boolean dunonay, String wonname, String date) {
        this.sira1 = sira1;
        this.sira1odul = sira1odul;
        this.sira1tane = sira1tane;
        this.sira2 = sira2;
        this.sira2odul = sira2odul;
        this.sira2tane = sira2tane;
        this.sira3 = sira3;
        this.sira3odul = sira3odul;
        this.sira3tane = sira3tane;
        this.sira4 = sira4;
        this.sira4odul = sira4odul;
        this.sira5 = sira5;
        this.sira5odul = sira5odul;
        this.sira6 = sira6;
        this.sira6odul = sira6odul;
        this.dunonay = dunonay;
        this.wonname = wonname;
        this.date = date;
    }

    public Odul(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getSira1() {
        return sira1;
    }

    public void setSira1(long sira1) {
        this.sira1 = sira1;
    }

    public double getSira1odul() {
        return sira1odul;
    }

    public void setSira1odul(double sira1odul) {
        this.sira1odul = sira1odul;
    }

    public long getSira2() {
        return sira2;
    }

    public void setSira2(long sira2) {
        this.sira2 = sira2;
    }

    public String getSira2odul() {
        return sira2odul;
    }

    public void setSira2odul(String sira2odul) {
        this.sira2odul = sira2odul;
    }

    public long getSira3() {
        return sira3;
    }

    public void setSira3(long sira3) {
        this.sira3 = sira3;
    }

    public String getSira3odul() {
        return sira3odul;
    }

    public void setSira3odul(String sira3odul) {
        this.sira3odul = sira3odul;
    }

    public long getSira4() {
        return sira4;
    }

    public void setSira4(long sira4) {
        this.sira4 = sira4;
    }

    public long getSira4odul() {
        return sira4odul;
    }

    public void setSira4odul(long sira4odul) {
        this.sira4odul = sira4odul;
    }

    public long getSira5() {
        return sira5;
    }

    public void setSira5(long sira5) {
        this.sira5 = sira5;
    }

    public long getSira5odul() {
        return sira5odul;
    }

    public void setSira5odul(long sira5odul) {
        this.sira5odul = sira5odul;
    }

    public long getSira6() {
        return sira6;
    }

    public void setSira6(long sira6) {
        this.sira6 = sira6;
    }

    public long getSira6odul() {
        return sira6odul;
    }

    public void setSira6odul(long sira6odul) {
        this.sira6odul = sira6odul;
    }

    public boolean isDunonay()throws NullPointerException {
        return dunonay;
    }

    public void setDunonay(boolean dunonay)throws NullPointerException {
        this.dunonay = dunonay;
    }

    public String getWonname() {
        return wonname;
    }

    public void setWonname(String wonname) {
        this.wonname = wonname;
    }

    public long getSira1tane() {
        return sira1tane;
    }

    public void setSira1tane(long sira1tane) {
        this.sira1tane = sira1tane;
    }

    public long getSira2tane() {
        return sira2tane;
    }

    public void setSira2tane(long sira2tane) {
        this.sira2tane = sira2tane;
    }

    public long getSira3tane() {
        return sira3tane;
    }

    public void setSira3tane(long sira3tane) {
        this.sira3tane = sira3tane;
    }

    @Override
    public String toString() {
        return "Odul{" +
                "sira1=" + sira1 +
                ", sira1odul='" + sira1odul + '\'' +
                ", sira2=" + sira2 +
                ", sira2odul='" + sira2odul + '\'' +
                ", sira3=" + sira3 +
                ", sira3odul='" + sira3odul + '\'' +
                ", sira4=" + sira4 +
                ", sira4odul=" + sira4odul +
                ", sira5=" + sira5 +
                ", sira5odul=" + sira5odul +
                ", sira6=" + sira6 +
                ", sira6odul=" + sira6odul +
                ", dunonay=" + dunonay +
                '}';
    }
}
