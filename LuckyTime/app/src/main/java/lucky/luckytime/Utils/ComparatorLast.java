package lucky.luckytime.Utils;

import lucky.luckytime.Model.Duello;

public class ComparatorLast implements java.util.Comparator<Duello> {

    @Override
    public int compare(Duello r1, Duello r2) {
        if(r1.getIzlemedun()>r2.getIzlemedun())
            return -1;
        else if(r1.getIzlemedun()<r2.getIzlemedun())
            return 1;
        else
            return 0;
    }
}
