package lucky.luckytime.Model;

public class Prize {

    private String id;
    private String picture;
    private String name;
    private String sponsor;
    private String kazananuser;
    private String kazananuserid;
    private long kazananpuan;
    private long katilim;
    private long katilims;
    private long price;
    private long maxpoint;
    private boolean situation_oynanma;
    private boolean situation_yayin;

    public Prize(String id, String picture, String name, String sponsor, String kazananuser, String kazananuserid, long kazananpuan, long katilim,
                 long katilims, long price, long maxpoint, boolean situation_oynanma, boolean situation_yayin) {
        this.id = id;
        this.picture = picture;
        this.name = name;
        this.sponsor = sponsor;
        this.kazananuser = kazananuser;
        this.kazananuserid = kazananuserid;
        this.kazananpuan = kazananpuan;
        this.katilim = katilim;
        this.katilims = katilims;
        this.price = price;
        this.maxpoint = maxpoint;
        this.situation_oynanma = situation_oynanma;
        this.situation_yayin = situation_yayin;
    }

    public Prize(){

    }

    public long getMaxpoint() {
        return maxpoint;
    }

    public void setMaxpoint(long maxpoint) {
        this.maxpoint = maxpoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getKazananuser() {
        return kazananuser;
    }

    public void setKazananuser(String kazananuser) {
        this.kazananuser = kazananuser;
    }

    public String getKazananuserid() {
        return kazananuserid;
    }

    public void setKazananuserid(String kazananuserid) {
        this.kazananuserid = kazananuserid;
    }

    public long getKazananpuan() {
        return kazananpuan;
    }

    public void setKazananpuan(long kazananpuan) {
        this.kazananpuan = kazananpuan;
    }

    public long getKatilim() {
        return katilim;
    }

    public void setKatilim(long katilim) {
        this.katilim = katilim;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isSituation_oynanma()throws NullPointerException {
        return situation_oynanma;
    }

    public void setSituation_oynanma(boolean situation_oynanma) throws NullPointerException{
        this.situation_oynanma = situation_oynanma;
    }

    public boolean isSituation_yayin()throws NullPointerException {
        return situation_yayin;
    }

    public void setSituation_yayin(boolean situation_yayin) throws NullPointerException{
        this.situation_yayin = situation_yayin;
    }

    public long getKatilims() {
        return katilims;
    }

    public void setKatilims(long katilims) {
        this.katilims = katilims;
    }

    @Override
    public String toString() {
        return "Prize{" +
                "id=" + id +
                ", picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", kazananuser='" + kazananuser + '\'' +
                ", kazananuserid='" + kazananuserid + '\'' +
                ", kazananpuan=" + kazananpuan +
                ", katilim=" + katilim +
                ", price=" + price +
                ", situation_oynanma=" + situation_oynanma +
                ", situation_yayin=" + situation_yayin +
                '}';
    }
}
