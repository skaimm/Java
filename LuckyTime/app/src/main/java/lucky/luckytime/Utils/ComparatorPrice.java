package lucky.luckytime.Utils;

import lucky.luckytime.Model.Prize;

public class ComparatorPrice implements java.util.Comparator<Prize> {

    @Override
    public int compare(Prize r1, Prize r2) {


        if(r1.getPrice()>r2.getPrice())
            return 1;
        else if(r1.getPrice()<r2.getPrice())
            return -1;
        else{
            if(r1.getKatilim()>r2.getKatilim())
                return 1;
            if(r1.getKatilim()<r2.getKatilim())
                return -1;
            else
                return 0;
        }
    }
}
