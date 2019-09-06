package lucky.luckytime.Model;

import java.util.ArrayList;

public class Promotion {

    private ArrayList<String> codes;
    private String odul;
    private boolean usedcode;
    private boolean durum;

    public Promotion(ArrayList<String> codes, String odul, boolean usedcode, boolean durum) {
        this.codes = codes;
        this.odul = odul;
        this.usedcode = usedcode;
        this.durum = durum;
    }

    public Promotion(){

    }
    public ArrayList<String> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<String> codes) {
        this.codes = codes;
    }

    public String getOdul() {
        return odul;
    }

    public void setOdul(String odul) {
        this.odul = odul;
    }

    public boolean isUsedcode() {
        return usedcode;
    }

    public void setUsedcode(boolean usedcode) {
        this.usedcode = usedcode;
    }

    public boolean isDurum() {
        return durum;
    }

    public void setDurum(boolean durum) {
        this.durum = durum;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "codes=" + codes +
                ", odul='" + odul + '\'' +
                ", usedcode=" + usedcode +
                ", durum=" + durum +
                '}';
    }

}
