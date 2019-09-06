package lucky.luckytime.Model;

public class TahminCevap {

    private String usercevap;
    private Tahmin tahmin;
    private long userRip;

    public TahminCevap(String usercevap, Tahmin tahmin,long userRip) {
        this.usercevap = usercevap;
        this.tahmin = tahmin;
        this.userRip = userRip;
    }

    public String getUsercevap() {
        return usercevap;
    }

    public void setUsercevap(String usercevap) {
        this.usercevap = usercevap;
    }

    public Tahmin getTahmin() {
        return tahmin;
    }

    public void setTahmin(Tahmin tahmin) {
        this.tahmin = tahmin;
    }

    public long getUserRip() {
        return userRip;
    }

    public void setUserRip(long userRip) {
        this.userRip = userRip;
    }
}
