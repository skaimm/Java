package lucky.luckytime.Model;

public class DuelloOdul {

    private Odul odul;
    private Duello duello;

    public DuelloOdul(Odul odul, Duello duello) {
        this.odul = odul;
        this.duello = duello;
    }

    public Odul getOdul() {
        return odul;
    }

    public void setOdul(Odul odul) {
        this.odul = odul;
    }

    public Duello getDuello() {
        return duello;
    }

    public void setDuello(Duello duello) {
        this.duello = duello;
    }

    @Override
    public String toString() {
        return "DuelloOdul{" +
                "odul=" + odul +
                ", duello=" + duello +
                '}';
    }
}
