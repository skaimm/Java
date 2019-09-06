package lucky.luckytime.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import lucky.luckytime.Model.Competition;
import lucky.luckytime.Model.Prize;
import lucky.luckytime.R;

public class ListViewPrizeHelper extends BaseAdapter {

    private Context mContext;
    //private List<Prize> mPrizes;
    //private User user;
    private FirebaseMethods firebaseMethods;
    //private RafflePoint rafflePoint;
    private Competition competition;
    UniversalImageLoader universalImageLoader;
    private ListView lvPrize;

    public ListViewPrizeHelper(Context mContext,Competition competition) {
        this.mContext = mContext;
        this.lvPrize=lvPrize;
        this.competition = competition;

        /*this.mPrizes = competition().getPrizes();
        this.user = competition().getUser();
        this.rafflePoint = competition().getRafflePoint();*/
        this.firebaseMethods = new FirebaseMethods(mContext);
        this.universalImageLoader = new UniversalImageLoader(mContext);
    }
    @Override
    public int getCount() {
        if(competition.getPrizes() !=null)
            return competition.getPrizes().size();

        return 0;
    }
    public void updateCompetition(Competition comp) {
        competition = null;
        competition = comp;
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int i) {
        return competition.getPrizes().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {

        public TextView prizename;
        public TextView prizekatilim;
       // public TextView prizekatilims;
        public TextView prizeprice;
        public Button prizebutton;
        public ImageView prizeimage;
        public TextView prizesponsor;

        public ViewHolder(View view){
            prizeimage=(ImageView) view.findViewById(R.id.iv_prizepic);
            prizekatilim = (TextView) view.findViewById(R.id.tv_prizekatilim);
            //prizekatilims = (TextView) view.findViewById(R.id.tv_sabitkatilim);
            prizename = (TextView) view.findViewById(R.id.tv_prizename);
            prizebutton = (Button) view.findViewById(R.id.btn_prizekatil);
            prizesponsor = (TextView) view.findViewById(R.id.tv_prizesponsor);
            prizeprice = (TextView) view.findViewById(R.id.tv_prizeprice);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) throws NullPointerException,Error {

        ViewHolder holder;
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customlistprize,null);
            holder = new ViewHolder(view);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        universalImageLoader.ImageLoadFromUrl(competition.getPrizes().get(i).getPicture(),holder.prizeimage);
        holder.prizename.setText(competition.getPrizes().get(i).getName());
        holder.prizekatilim.setText(new DecimalFormat("###,###,###").format(competition.getPrizes().get(i).getKatilim()));
       // holder.prizekatilims.setText(new DecimalFormat("###,###,###").format(competition.getPrizes().get(i).getKatilims()));
        holder.prizeprice.setText(String.valueOf(competition.getPrizes().get(i).getPrice()));
        holder.prizesponsor.setText(String.valueOf(competition.getPrizes().get(i).getSponsor()));
        holder.prizebutton.setBackgroundResource(R.drawable.background_signup);
        holder.prizebutton.setPadding(10,0,10,0);

        boolean oynanma = competition.getPrizes().get(i).isSituation_oynanma();
        final String prizekey = competition.getPrizes().get(i).getId();
        String kazuserid = competition.getPrizes().get(i).getKazananuserid();
        final String prizename = competition.getPrizes().get(i).getName();
        long katilim = competition.getPrizes().get(i).getKatilim();
        final long katilimucreti = competition.getPrizes().get(i).getPrice();
        String kazananad = competition.getPrizes().get(i).getKazananuser();

        holder.prizebutton.setClickable(false);
        if(katilim>0){
            if(oynanma){
                if(competition.getUser().getRip()>=katilimucreti){
                    holder.prizebutton.setClickable(true);
                    holder.prizebutton.setText(mContext.getString(R.string.buttonjoin));
                    holder.prizebutton.setBackgroundResource(R.drawable.background_signup);
                    holder.prizebutton.setPadding(10,0,10,0);
                    holder.prizebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(competition.getUser().isCanjoin()){
                                final Dialog customDialog = new Dialog(mContext);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.customdialog);
                                TextView question = (TextView) customDialog.findViewById(R.id.tv_dialogque);
                                Button yes = (Button) customDialog.findViewById(R.id.btn_diayes);

                                question.setText( prizename +" "+mContext.getString(R.string.wanttry));
                                yes.setText(mContext.getString(R.string.yes));
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Runnable backGroundRunnable = new Runnable() {
                                            public void run(){
                                                if(competition.getUser().getRip()>=katilimucreti){
                                                    long price = katilimucreti;
                                                    long newRip = competition.getUser().getRip()-price;
                                                    firebaseMethods.changeRipCount(newRip);
                                                    firebaseMethods.changeCanWatch(false);
                                                    Query query = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_prize)).child(prizekey);
                                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            long tempkatilim= dataSnapshot.getValue(Prize.class).getKatilim();
                                                            tempkatilim--;
                                                            firebaseMethods.changePrizeKatilim(prizekey,tempkatilim);
                                                            long point = competition.getRafflePoint().getPointForRaffle();
                                                            long kazanpuan = dataSnapshot.getValue(Prize.class).getKazananpuan();
                                                            if(kazanpuan<=point){
                                                                firebaseMethods.prizeTempWinner(prizekey,point,competition.getUser().getName(),competition.getUser().getEmail());
                                                                if(point ==competition.getRafflePoint().getMax()){
                                                                    firebaseMethods.millionUsers(prizekey,competition.getUser().getName(),competition.getUser().getUser_id());
                                                                    Toast.makeText(mContext,mContext.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
                                                                }
                                                            }else{
                                                                Toast.makeText(mContext,mContext.getString(R.string.tryagain),Toast.LENGTH_LONG).show();
                                                            }
                                                            customDialog.cancel();
                                                        }
                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                            }};
                                        Thread sampleThread = new Thread(backGroundRunnable);
                                        sampleThread.start();
                                        customDialog.cancel();
                                    }
                                });


                                customDialog.show();
                            }else {
                                Toast.makeText(mContext,mContext.getString(R.string.infojoin),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    holder.prizebutton.setText(mContext.getString(R.string.buttonnorip));
                    holder.prizebutton.setBackgroundResource(R.drawable.btnlost);
                    holder.prizebutton.setPadding(10,0,10,0);
                }
            }else{
                holder.prizebutton.setText(mContext.getString(R.string.buttonnojoin));
                holder.prizebutton.setBackgroundResource(R.drawable.btnlost);
                holder.prizebutton.setPadding(10,0,10,0);
            }
            if(competition.getUser().getEmail().equals(kazuserid)){
                holder.prizebutton.setText(mContext.getString(R.string.buttonfirst));
                holder.prizebutton.setBackgroundResource(R.drawable.btnwait);
                holder.prizebutton.setPadding(10,0,10,0);
            }

        }else{
            if(oynanma){
                holder.prizebutton.setText(mContext.getString(R.string.buttonfinishedwait));
                holder.prizebutton.setBackgroundResource(R.drawable.btnwait);
                holder.prizebutton.setPadding(10,0,10,0);
                if(competition.getUser().getUser_id().equals(mContext.getString(R.string.admin))){
                    firebaseMethods.findwinner(kazuserid,prizekey);
                }
            }else{
                holder.prizebutton.setText(mContext.getString(R.string.buttonwin) + " " + kazananad);
                holder.prizebutton.setBackgroundResource(R.drawable.btnlost);
                holder.prizebutton.setPadding(10,0,10,0);
                if(competition.getUser().getEmail().equals(kazuserid)){
                    holder.prizebutton.setText(mContext.getString(R.string.buttonwon));
                    holder.prizebutton.setBackgroundResource(R.drawable.btnwon);
                    holder.prizebutton.setPadding(10,0,10,0);
                }
            }
        }
        return view;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private interface FirebaseCallback {
        void userInfo(Competition competition) throws Exception;
    }
}

