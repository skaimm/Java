package lucky.luckytime.Utils;

import lucky.luckytime.Model.Duello;

public class Comparator implements java.util.Comparator<Duello> {

    @Override
    public int compare(Duello r1, Duello r2) {
        if(r1.getIzleme()>r2.getIzleme())
            return -1;
        else if(r1.getIzleme()<r2.getIzleme())
            return 1;
        else
            return 0;
    }
}
