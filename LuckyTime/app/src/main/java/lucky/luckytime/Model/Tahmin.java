package lucky.luckytime.Model;

public class Tahmin {

    private String tahmin;
    private String tahmin_cevap1;
    private String tahmin_cevap2;
    private String tahmin_cevap3;
    private String tahmin_dogrucevap;
    private String tahmin_odulu;
    private String kapanma_date;
    private String kapanma_saat;
    private long tahmin_ucreti;
    private long sonuc_bilen;
    private long sonuc_toplam;
    private boolean situation_oynanma;
    private boolean situation_yayin;
    private boolean situation_cevap;

    public Tahmin(String tahmin, String tahmin_cevap1, String tahmin_cevap2, String tahmin_cevap3, String tahmin_dogrucevap,
                  String tahmin_odulu, String kapanma_date, String kapanma_saat, long tahmin_ucreti, long sonuc_bilen,
                  long sonuc_toplam, boolean situation_oynanma, boolean situation_yayin, boolean situation_cevap) {
        this.tahmin = tahmin;
        this.tahmin_cevap1 = tahmin_cevap1;
        this.tahmin_cevap2 = tahmin_cevap2;
        this.tahmin_cevap3 = tahmin_cevap3;
        this.tahmin_dogrucevap = tahmin_dogrucevap;
        this.tahmin_odulu = tahmin_odulu;
        this.kapanma_date = kapanma_date;
        this.kapanma_saat = kapanma_saat;
        this.tahmin_ucreti = tahmin_ucreti;
        this.sonuc_bilen = sonuc_bilen;
        this.sonuc_toplam = sonuc_toplam;
        this.situation_oynanma = situation_oynanma;
        this.situation_yayin = situation_yayin;
        this.situation_cevap = situation_cevap;
    }

    public Tahmin(){

    }
    public String getTahmin() {
        return tahmin;
    }

    public void setTahmin(String tahmin) {
        this.tahmin = tahmin;
    }

    public String getTahmin_cevap1() {
        return tahmin_cevap1;
    }

    public void setTahmin_cevap1(String tahmin_cevap1) {
        this.tahmin_cevap1 = tahmin_cevap1;
    }

    public String getTahmin_cevap2() {
        return tahmin_cevap2;
    }

    public void setTahmin_cevap2(String tahmin_cevap2) {
        this.tahmin_cevap2 = tahmin_cevap2;
    }

    public String getTahmin_cevap3() {
        return tahmin_cevap3;
    }

    public void setTahmin_cevap3(String tahmin_cevap3) {
        this.tahmin_cevap3 = tahmin_cevap3;
    }

    public String getTahmin_dogrucevap() {
        return tahmin_dogrucevap;
    }

    public void setTahmin_dogrucevap(String tahmin_dogrucevap) {
        this.tahmin_dogrucevap = tahmin_dogrucevap;
    }

    public String getTahmin_odulu() {
        return tahmin_odulu;
    }

    public void setTahmin_odulu(String tahmin_odulu) {
        this.tahmin_odulu = tahmin_odulu;
    }

    @Override
    public String toString() {
        return "Tahmin{" +
                "tahmin='" + tahmin + '\'' +
                ", tahmin_cevap1='" + tahmin_cevap1 + '\'' +
                ", tahmin_cevap2='" + tahmin_cevap2 + '\'' +
                ", tahmin_cevap3='" + tahmin_cevap3 + '\'' +
                ", tahmin_dogrucevap='" + tahmin_dogrucevap + '\'' +
                ", tahmin_odulu='" + tahmin_odulu + '\'' +
                ", kapanma_date='" + kapanma_date + '\'' +
                ", kapanma_saat='" + kapanma_saat + '\'' +
                ", tahmin_ucreti=" + tahmin_ucreti +
                ", sonuc_bilen=" + sonuc_bilen +
                ", sonuc_toplam=" + sonuc_toplam +
                ", situation_oynanma=" + situation_oynanma +
                ", situation_yayin=" + situation_yayin +
                ", situation_cevap=" + situation_cevap +
                '}';
    }

    public String getKapanma_date() {
        return kapanma_date;
    }

    public void setKapanma_date(String kapanma_date) {
        this.kapanma_date = kapanma_date;
    }

    public String getKapanma_saat() {
        return kapanma_saat;
    }

    public void setKapanma_saat(String kapanma_saat) {
        this.kapanma_saat = kapanma_saat;
    }

    public long getTahmin_ucreti() {
        return tahmin_ucreti;
    }

    public void setTahmin_ucreti(long tahmin_ucreti) {
        this.tahmin_ucreti = tahmin_ucreti;
    }

    public long getSonuc_bilen() {
        return sonuc_bilen;
    }

    public void setSonuc_bilen(long sonuc_bilen) {
        this.sonuc_bilen = sonuc_bilen;
    }

    public long getSonuc_toplam() {
        return sonuc_toplam;
    }

    public void setSonuc_toplam(long sonuc_toplam) {
        this.sonuc_toplam = sonuc_toplam;
    }

    public boolean isSituation_oynanma() throws NullPointerException {
        return situation_oynanma;
    }

    public void setSituation_oynanma(boolean situation_oynanma) throws NullPointerException{
        this.situation_oynanma = situation_oynanma;
    }

    public boolean isSituation_yayin()throws NullPointerException {
        return situation_yayin;
    }

    public void setSituation_yayin(boolean situation_yayin)throws NullPointerException {
        this.situation_yayin = situation_yayin;
    }

    public boolean isSituation_cevap()throws NullPointerException {
        return situation_cevap;
    }

    public void setSituation_cevap(boolean situation_cevap) throws NullPointerException{
        this.situation_cevap = situation_cevap;
    }

}
