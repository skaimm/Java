package lucky.luckytime.Model;

public class Duello {

    private String user_id;
    private String isim;
    private long izleme;
    private long izlemedun;
    private long siradun;
    private long sira;

    public Duello(String user_id, String isim, long izleme, long izlemedun, long siradun, long sira) {
        this.user_id = user_id;
        this.isim = isim;
        this.izleme = izleme;
        this.izlemedun = izlemedun;
        this.siradun = siradun;
        this.sira = sira;
    }

    public Duello(){

    }

    public long getSira() {
        return sira;
    }

    public void setSira(long sira) {
        this.sira = sira;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String id) {
        this.user_id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public long getIzleme() {
        return izleme;
    }

    public void setIzleme(long izleme) {
        this.izleme = izleme;
    }

    public long getIzlemedun() {
        return izlemedun;
    }

    public void setIzlemedun(long izlemedun) {
        this.izlemedun = izlemedun;
    }

    public long getSiradun() {
        return siradun;
    }

    public void setSiradun(long siradun) {
        this.siradun = siradun;
    }

    @Override
    public String toString() {
        return "Duello{" +
                "id='" + user_id + '\'' +
                ", isim='" + isim + '\'' +
                ", izleme=" + izleme +
                ", izlemedun=" + izlemedun +
                ", siradun=" + siradun +
                '}';
    }
}
