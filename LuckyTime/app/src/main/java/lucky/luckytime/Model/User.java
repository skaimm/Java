package lucky.luckytime.Model;

public class User {

    private String user_id;
    private String name;
    private String iban;
    private String email;
    private String picture;
    private double money;
    private long rip;
    private long prize;
    private long watch_ad;
    private long altinbox;
    private long gumusbox;
    private long bronzbox;
    private boolean canjoin;

    public User(String user_id, String name, String iban, String email, String picture,
                double money, long rip, long prize, long watch_ad, long altinbox,
                long gumusbox, long bronzbox, boolean canjoin) {
        this.user_id = user_id;
        this.name = name;
        this.iban = iban;
        this.email = email;
        this.picture = picture;
        this.money = money;
        this.rip = rip;
        this.prize = prize;
        this.watch_ad = watch_ad;
        this.altinbox = altinbox;
        this.gumusbox = gumusbox;
        this.bronzbox = bronzbox;
        this.canjoin = canjoin;
    }

    public User(){

    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getWatch_ad() {
        return watch_ad;
    }

    public void setWatch_ad(long watch_ad) {
        this.watch_ad = watch_ad;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getRip() {
        return rip;
    }

    public void setRip(long rip) {
        this.rip = rip;
    }

    public long getPrize() {
        return prize;
    }

    public void setPrize(long prize) {
        this.prize = prize;
    }

    public long getAltinbox() {
        return altinbox;
    }

    public void setAltinbox(long altinbox) {
        this.altinbox = altinbox;
    }

    public long getGumusbox() {
        return gumusbox;
    }

    public void setGumusbox(long gumusbox) {
        this.gumusbox = gumusbox;
    }

    public long getBronzbox() {
        return bronzbox;
    }

    public void setBronzbox(long bronzbox) {
        this.bronzbox = bronzbox;
    }

    public boolean isCanjoin() {
        return canjoin;
    }

    public void setCanjoin(boolean canjoin) {
        this.canjoin = canjoin;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", money=" + money +
                '}';
    }
}
