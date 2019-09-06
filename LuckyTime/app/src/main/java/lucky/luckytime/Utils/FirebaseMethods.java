package lucky.luckytime.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import lucky.luckytime.Model.Box;
import lucky.luckytime.Model.Competition;
import lucky.luckytime.Model.Duello;
import lucky.luckytime.Model.DuelloOdul;
import lucky.luckytime.Model.Odul;
import lucky.luckytime.Model.Prize;
import lucky.luckytime.Model.Promotion;
import lucky.luckytime.Model.RafflePoint;
import lucky.luckytime.Model.Tahmin;
import lucky.luckytime.Model.TahminCevap;
import lucky.luckytime.Model.User;
import lucky.luckytime.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class FirebaseMethods {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private Context mContext;
    private String userID;
    private int control=0;

    public FirebaseMethods(Context context){
        mAuth= FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext= context;

        if(mAuth.getCurrentUser()!=null)
        {
            userID = mAuth.getCurrentUser().getUid();
        }

    }

    public void registerNewEmail(String email,String password,final String username)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            sendVerificationEmail();
                            userID = mAuth.getCurrentUser().getUid();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(mContext,mContext.getString(R.string.errorregister),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(mContext, mContext.getString(R.string.successverify), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext,mContext.getString(R.string.errorverify), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    public void changeDailyWatchAd(String key,long count) {
        myRef.child(mContext.getString(R.string.dbname_duello)).child(key).child(mContext.getString(R.string.field_izleme)).setValue(count);
    }
    public void changeDailyWatchAdandCount(String key,long count,long sira) {
        myRef.child(mContext.getString(R.string.dbname_duello)).child(key).child(mContext.getString(R.string.field_izleme)).setValue(count);
        myRef.child(mContext.getString(R.string.dbname_duello)).child(key).child(mContext.getString(R.string.field_sira)).setValue(sira);
    }

    public void changeRipCount (long newRip) {
        myRef.child(mContext.getString(R.string.dbname_users)).child(userID).child(mContext.getString(R.string.field_rip)).setValue(newRip);
    }
    public void changeWatchAd(long newAdCount){
        myRef.child(mContext.getString(R.string.dbname_users)).child(userID).child(mContext.getString(R.string.field_watchad)).setValue(newAdCount);
    }
    public void changeBoxCount(String type,long newBox){
        if(type.equals(mContext.getString(R.string.field_altin)))
            myRef.child(mContext.getString(R.string.dbname_users)).child(userID).child(mContext.getString(R.string.field_altinbox)).setValue(newBox);
        if(type.equals(mContext.getString(R.string.field_gumus)))
            myRef.child(mContext.getString(R.string.dbname_users)).child(userID).child(mContext.getString(R.string.field_gumusbox)).setValue(newBox);
        if(type.equals(mContext.getString(R.string.field_bronz)))
            myRef.child(mContext.getString(R.string.dbname_users)).child(userID).child(mContext.getString(R.string.field_bronzbox)).setValue(newBox);
    }
    public void promotionUsers(String key) {
        myRef.child(mContext.getString(R.string.dbname_promotions)).child(mContext.getString(R.string.field_kullanan)).child(userID).setValue(key);
    }
    public void saveTheIBANnumber(String iban) {
        myRef.child(mContext.getString(R.string.dbname_users)).child(userID).child(mContext.getString(R.string.field_iban)).setValue(iban);
    }
    public void changeLuck(String key, long sira) {
        myRef.child(mContext.getString(R.string.dbname_box)).child(key).child(mContext.getString(R.string.field_sanssira)).setValue(sira);
    }
    public void changeSituationofPlaying() {
        myRef.child(mContext.getString(R.string.dbname_tahmin)).child(mContext.getString(R.string.field_situationoynanma)).setValue(false);
    }
    public void predictionUsers(String answer,long toplam){

        myRef.child(mContext.getString(R.string.dbname_tahmin)).child(mContext.getString(R.string.field_sonuctoplam)).setValue(toplam);
        myRef.child(mContext.getString(R.string.dbname_tahminoyna)).child(userID).setValue(answer);
    }

    public void addNewUser(String name, String email){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setUser_id(userID);
        user.setRip(0);
        user.setPrize(0);
        user.setWatch_ad(0);
        user.setAltinbox(0);
        user.setIban("");
        user.setGumusbox(0);
        user.setBronzbox(0);
        user.setMoney(0.001);
        user.setCanjoin(false);

        Duello duello = new Duello();
        duello.setUser_id(userID);
        duello.setIsim(name);
        duello.setIzleme(0);
        duello.setSira(0);
        duello.setIzlemedun(0);
        duello.setSiradun(0);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

        myRef.child(mContext.getString(R.string.dbname_duello))
                .child(userID)
                .setValue(duello);
    }

    public User getUsersInfo(DataSnapshot dataSnapshot){

        User user = new User();

        for (DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.getKey().equals(mContext.getString(R.string.dbname_users))){

                try{
                    user.setPrize(ds.child(userID).getValue(User.class).getPrize());
                    user.setRip(ds.child(userID).getValue(User.class).getRip());
                    user.setUser_id(ds.child(userID).getValue(User.class).getUser_id());
                    user.setPicture(ds.child(userID).getValue(User.class).getPicture());
                    user.setEmail(ds.child(userID).getValue(User.class).getEmail());
                    user.setName(ds.child(userID).getValue(User.class).getName());
                    user.setAltinbox(ds.child(userID).getValue(User.class).getAltinbox());
                    user.setGumusbox(ds.child(userID).getValue(User.class).getGumusbox());
                    user.setBronzbox(ds.child(userID).getValue(User.class).getBronzbox());
                    user.setWatch_ad(ds.child(userID).getValue(User.class).getWatch_ad());
                    user.setCanjoin(ds.child(userID).getValue(User.class).isCanjoin());
                    user.setMoney(ds.child(userID).getValue(User.class).getMoney());
                    user.setIban(ds.child(userID).getValue(User.class).getIban());
                }catch (NullPointerException e){

                }
            }
        }

        return user;
    }
    public Promotion getPromotionInfo(DataSnapshot dataSnapshot) {
        Promotion promo = new Promotion();
        ArrayList<String> codes = new ArrayList<>();
        if(dataSnapshot.child(mContext.getString(R.string.dbname_promotions)).child(mContext.getString(R.string.field_durum)).getValue().equals(true)){
            promo.setDurum(true);
            for (DataSnapshot ds : dataSnapshot.child(mContext.getString(R.string.dbname_promotions)).getChildren()){
                if(ds.getKey().equals(mContext.getString(R.string.field_odul))){
                    promo.setOdul(ds.getValue().toString());
                }
                if(ds.getKey().equals(mContext.getString(R.string.field_code))){
                    try{
                        for(DataSnapshot pcodes : ds.getChildren()){
                            codes.add(pcodes.getValue().toString());
                        }
                        promo.setCodes(codes);
                    }catch (NullPointerException e){

                    }
                }
                if(ds.getKey().equals(mContext.getString(R.string.field_kullanan))){
                    try{
                        for(DataSnapshot users : ds.getChildren()){
                            if(users.getKey().toString().equals(userID)){
                                promo.setUsedcode(true);
                                break;
                            }
                        }
                    }catch (NullPointerException e){

                    }
                }
            }
        }else {
            promo.setDurum(false);
            promo.setUsedcode(true);
            promo.setOdul("");
            promo.setCodes(codes);
        }

        return promo;

    }
    public ArrayList<Box> getBoxesInfo(DataSnapshot dataSnapshot) {
        ArrayList<Box> boxes = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.child(mContext.getString(R.string.dbname_box)).getChildren()){
            try{
                boxes.add(ds.getValue(Box.class));
            }catch (NullPointerException e){

            }
        }

        return boxes;
    }
    public DuelloOdul getOdulInfo(DataSnapshot dataSnapshot){

        Odul odul = new Odul();
        Duello duello = new Duello();

        for (DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.getKey().equals(mContext.getString(R.string.dbname_odul))){

                try{
                    odul.setSira1(ds.getValue(Odul.class).getSira1());
                    odul.setSira2(ds.getValue(Odul.class).getSira2());
                    odul.setSira3(ds.getValue(Odul.class).getSira3());
                    odul.setSira4(ds.getValue(Odul.class).getSira4());
                    odul.setSira5(ds.getValue(Odul.class).getSira5());
                    odul.setSira6(ds.getValue(Odul.class).getSira6());
                    odul.setSira1odul(ds.getValue(Odul.class).getSira1odul());
                    odul.setSira2odul(ds.getValue(Odul.class).getSira2odul());
                    odul.setSira3odul(ds.getValue(Odul.class).getSira3odul());
                    odul.setSira4odul(ds.getValue(Odul.class).getSira4odul());
                    odul.setSira5odul(ds.getValue(Odul.class).getSira5odul());
                    odul.setSira6odul(ds.getValue(Odul.class).getSira6odul());
                    odul.setWonname(ds.getValue(Odul.class).getWonname());
                    odul.setDunonay(ds.getValue(Odul.class).isDunonay());
                    odul.setDate(ds.getValue(Odul.class).getDate());
                    odul.setSira1tane(ds.getValue(Odul.class).getSira1tane());
                    odul.setSira2tane(ds.getValue(Odul.class).getSira2tane());
                    odul.setSira3tane(ds.getValue(Odul.class).getSira3tane());

                }catch (NullPointerException e){

                }
            }
        }

        for (DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.getKey().equals(mContext.getString(R.string.dbname_duello))){

                try{
                    duello.setUser_id(ds.child(userID).getValue(Duello.class).getUser_id());
                    duello.setIsim(ds.child(userID).getValue(Duello.class).getIsim());
                    duello.setIzleme(ds.child(userID).getValue(Duello.class).getIzleme());
                    duello.setIzlemedun(ds.child(userID).getValue(Duello.class).getIzlemedun());
                    duello.setSiradun(ds.child(userID).getValue(Duello.class).getSiradun());
                    duello.setSira(ds.child(userID).getValue(Duello.class).getSira());
                }catch (NullPointerException e){

                }
            }
        }

        return new DuelloOdul(odul,duello);
    }

    public TahminCevap getTahminInfo(DataSnapshot dataSnapshot){
        Tahmin tahmin = new Tahmin();
        String cevap = "";
        long userRip = 0;

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            if (ds.getKey().equals(mContext.getString(R.string.dbname_tahmin))) {
                boolean situation_oynanma = false;
                try {
                    situation_oynanma = ds.getValue(Tahmin.class).isSituation_oynanma();
                    tahmin.setTahmin(ds.getValue(Tahmin.class).getTahmin());
                    tahmin.setTahmin_cevap1(ds.getValue(Tahmin.class).getTahmin_cevap1());
                    tahmin.setTahmin_cevap2(ds.getValue(Tahmin.class).getTahmin_cevap2());
                    tahmin.setTahmin_cevap3(ds.getValue(Tahmin.class).getTahmin_cevap3());
                    tahmin.setTahmin_dogrucevap(ds.getValue(Tahmin.class).getTahmin_dogrucevap());
                    tahmin.setTahmin_odulu(ds.getValue(Tahmin.class).getTahmin_odulu());
                    tahmin.setTahmin_ucreti(ds.getValue(Tahmin.class).getTahmin_ucreti());
                    tahmin.setSonuc_toplam(ds.getValue(Tahmin.class).getSonuc_toplam());
                    tahmin.setSonuc_bilen(ds.getValue(Tahmin.class).getSonuc_bilen());
                    tahmin.setKapanma_saat(ds.getValue(Tahmin.class).getKapanma_saat());
                    tahmin.setKapanma_date(ds.getValue(Tahmin.class).getKapanma_date());
                    tahmin.setSituation_oynanma(situation_oynanma);
                    tahmin.setSituation_yayin(ds.getValue(Tahmin.class).isSituation_yayin());
                    tahmin.setSituation_cevap(ds.getValue(Tahmin.class).isSituation_cevap());
                    Calendar calendar ;
                    calendar = Calendar.getInstance();
                    calendar.setTimeZone(TimeZone.getTimeZone(mContext.getString(R.string.timezone)));
                    SimpleDateFormat dateFormat = new SimpleDateFormat(mContext.getString(R.string.timeformat));
                    String currentdate = dateFormat.format(calendar.getTime());
                    SimpleDateFormat clockFormat = new SimpleDateFormat(mContext.getString(R.string.timehourformat));
                    String currentclock = clockFormat.format(calendar.getTime());
                    String closedate = ds.getValue(Tahmin.class).getKapanma_date();
                    String closeclock = ds.getValue(Tahmin.class).getKapanma_saat();

                    try {
                        Date closetime = dateFormat.parse(closedate);
                        Date clocktime = clockFormat.parse(closeclock);
                        Date date = dateFormat.parse(currentdate);
                        Date time = clockFormat.parse(currentclock);
                        if(closetime.compareTo(date) == 0){
                            if(clocktime.compareTo(time) <= 0){
                                changeSituationofPlaying();
                            }
                        }
                        if(closetime.compareTo(date) < 0){
                            changeSituationofPlaying();
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

                } catch (NullPointerException e) {

                }
            }
            if (ds.getKey().equals(mContext.getString(R.string.dbname_tahminoyna))) {
                try {
                    cevap = ds.child(userID).getValue().toString();
                } catch (NullPointerException e) {

                }
            }
            if (ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
                try {
                    userRip = ds.child(userID).getValue(User.class).getRip();
                } catch (NullPointerException e) {

                }
            }
        }
        return new TahminCevap(cevap, tahmin,userRip);
    }

    public Competition getCompetitionInfo(DataSnapshot dataSnapshot){
        ArrayList<Prize> prizes = new ArrayList<>();
        User user = new User();
        RafflePoint rafflePoint = new RafflePoint();

        for(DataSnapshot ds : dataSnapshot.getChildren()){

            if(ds.getKey().equals(mContext.getString(R.string.dbname_prize))){

                try{
                    for(DataSnapshot prizess : ds.getChildren()){
                        if(prizess.getValue(Prize.class).isSituation_yayin()){
                            prizes.add(prizess.getValue(Prize.class));
                        }
                    }
                }catch (NullPointerException e){

                }
            }
            if(ds.getKey().equals(mContext.getString(R.string.dbname_users))){

                try{
                    user.setPrize(ds.child(userID).getValue(User.class).getPrize());
                    user.setRip(ds.child(userID).getValue(User.class).getRip());
                    user.setUser_id(ds.child(userID).getValue(User.class).getUser_id());
                    user.setEmail(ds.child(userID).getValue(User.class).getEmail());
                    user.setName(ds.child(userID).getValue(User.class).getName());
                    user.setAltinbox(ds.child(userID).getValue(User.class).getAltinbox());
                    user.setWatch_ad(ds.child(userID).getValue(User.class).getWatch_ad());
                    user.setGumusbox(ds.child(userID).getValue(User.class).getGumusbox());
                    user.setBronzbox(ds.child(userID).getValue(User.class).getBronzbox());
                    user.setCanjoin(ds.child(userID).getValue(User.class).isCanjoin());
                    user.setMoney(ds.child(userID).getValue(User.class).getMoney());
                    user.setIban(ds.child(userID).getValue(User.class).getIban());
                }catch (NullPointerException e){

                }
            }
            if(ds.getKey().equals(mContext.getString(R.string.dbname_raffle))){

                try{
                    rafflePoint= ds.getValue(RafflePoint.class);
                }catch (NullPointerException e){

                }
            }
        }

        return new Competition(prizes,user,rafflePoint);
    }

    public boolean checkIfemailExists(String email,DataSnapshot dataSnapshot){
        User user = new User();
        for (DataSnapshot ds:dataSnapshot.getChildren()){
            if(ds.getKey().equals(mContext.getString(R.string.dbname_users))){
                user.setEmail(ds.child(userID).getValue(User.class).getEmail());
                if(user.getEmail().equals(email)){
                    return true;
                }
            }
        }
        return false;
    }

    public void changeCevapOnay() {
        myRef.child(mContext.getString(R.string.dbname_tahmin)).child(mContext.getString(R.string.field_situationcevap)).setValue(false);
    }

    public void changePrizeKatilim(String key, long katilim) {
        myRef.child(mContext.getString(R.string.dbname_prize)).child(key).child(mContext.getString(R.string.field_katilim)).setValue(katilim);
    }

    public void prizeTempWinner(String key,long point,String name,String email) {
        myRef.child(mContext.getString(R.string.dbname_prize)).child(key).child(mContext.getString(R.string.field_kazananpuan)).setValue(point);
        myRef.child(mContext.getString(R.string.dbname_prize)).child(key).child(mContext.getString(R.string.field_kazananuser)).setValue(name);
        myRef.child(mContext.getString(R.string.dbname_prize)).child(key).child(mContext.getString(R.string.field_kazananuserid)).setValue(email);
    }

    public void millionUsers(String prizekey, String name, String user_id) {
        myRef.child(mContext.getString(R.string.field_million)).child(prizekey).child(user_id).setValue(name);
    }

    public void changeDailyRanking(String user_id,String name, long izleme, long count) {
        myRef.child(mContext.getString(R.string.dbname_duello)).child(user_id).child(mContext.getString(R.string.field_izledun)).setValue(izleme);
        myRef.child(mContext.getString(R.string.dbname_duello)).child(user_id).child(mContext.getString(R.string.field_siradun)).setValue(count);

        if(count==1){
            myRef.child(mContext.getString(R.string.dbname_odul)).child(mContext.getString(R.string.field_kazanan)).setValue(name);
        }
    }
    public void changeOnay(boolean b) {
        myRef.child(mContext.getString(R.string.dbname_odul)).child(mContext.getString(R.string.field_dunonay)).setValue(b);
    }
    public void savetheMoneYForWhoWin(double money){
        myRef.child(mContext.getString(R.string.dbname_odul)).child(mContext.getString(R.string.field_odulsira1)).setValue(money);
    }
    public void changeOnayDate(String date) {
        myRef.child(mContext.getString(R.string.dbname_odul)).child(mContext.getString(R.string.field_date)).setValue(date);
    }

    public void changeDailyRankinginDay(String user_id, long count) {
        myRef.child(mContext.getString(R.string.dbname_duello)).child(user_id).child(mContext.getString(R.string.field_sira)).setValue(count);
    }

    public void givePrizeRipForRanking(final long siraodul6, final String user_id) {
        Query query = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_users)).child(user_id).child(mContext.getString(R.string.field_rip));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long newRip = dataSnapshot.getValue(User.class).getRip()+siraodul6;
                myRef.child(mContext.getString(R.string.dbname_users)).child(user_id).child(mContext.getString(R.string.field_rip)).setValue(newRip);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void givePrizeMoneyForRanking(final double siraodul1, final String user_id) {
        Query query = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_users)).child(user_id).child(mContext.getString(R.string.field_money));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double newMoney = (double) dataSnapshot.getValue();
                newMoney+=siraodul1;
                myRef.child(mContext.getString(R.string.dbname_users)).child(user_id).child(mContext.getString(R.string.field_money)).setValue(newMoney);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void givePrizeBoxForRanking(final long adet, final String odul, final String user_id) {
        Query query = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_users)).child(user_id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long newBox;
                String boxtype;
                if(odul.equals(mContext.getString(R.string.field_altin))){
                    newBox = dataSnapshot.getValue(User.class).getAltinbox();
                    newBox+=adet;
                    boxtype= mContext.getString(R.string.field_altinbox);
                }
                else if(odul.equals(mContext.getString(R.string.field_gumus))){
                    newBox = dataSnapshot.getValue(User.class).getGumusbox();
                    newBox+=adet;
                    boxtype= mContext.getString(R.string.field_gumusbox);
                }
                else{
                    newBox = dataSnapshot.getValue(User.class).getBronzbox();
                    newBox+=adet;
                    boxtype= mContext.getString(R.string.field_bronzbox);
                }
                myRef.child(mContext.getString(R.string.dbname_users)).child(user_id).child(boxtype).setValue(newBox);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void givePrizeBoxForTahmin(final String odul, final String user_id) {

        Query query = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_users)).child(user_id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long newBox;
                String boxtype;
                if(odul.equals( mContext.getString(R.string.field_altin))){
                    newBox = dataSnapshot.getValue(User.class).getAltinbox();
                    newBox++;
                    boxtype= mContext.getString(R.string.field_altinbox);
                }
                else if(odul.equals( mContext.getString(R.string.field_gumus))){
                    newBox = dataSnapshot.getValue(User.class).getGumusbox();
                    newBox++;
                    boxtype= mContext.getString(R.string.field_gumusbox);
                }
                else{
                    newBox = dataSnapshot.getValue(User.class).getBronzbox();
                    newBox++;
                    boxtype= mContext.getString(R.string.field_bronzbox);
                }
                myRef.child(mContext.getString(R.string.dbname_users)).child(user_id).child(boxtype).setValue(newBox);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void tahminbilensayisi(long bilen) {
        myRef.child(mContext.getString(R.string.dbname_tahmin)).child(mContext.getString(R.string.field_sonucbilen)).setValue(bilen);
    }

    public ArrayList<Duello> getDailyRanking(DataSnapshot dataSnapshot) {
        ArrayList<Duello> rank = new ArrayList<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.getKey().equals(mContext.getString(R.string.dbname_duello))){
                for(DataSnapshot users : ds.getChildren()){
                        rank.add(users.getValue(Duello.class));
                }
            }
        }
        return rank;
    }
    public void getDailyRankingMOney(final DataSnapshot dataSnapshot) {
        Runnable backGroundRunnable = new Runnable() {
            public void run(){
                double findmoney = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    findmoney += ds.getValue(Duello.class).getIzleme();
                }
                savetheMoneYForWhoWin(findmoney/10000);
            }};
        Thread sampleThread = new Thread(backGroundRunnable);
        sampleThread.start();

    }

    public void saveTheWinners(long count, String user_id) {
        myRef.child(mContext.getString(R.string.dbname_duellokazanan)).child(user_id).setValue(count);
    }

    public void removeUserFromDuelloList(String key) {
        myRef.child(mContext.getString(R.string.dbname_duellokazanan)).child(key).removeValue();
    }
    public void removeUserFromTahminList(String key) {
        myRef.child(mContext.getString(R.string.dbname_tahminoyna)).child(key).removeValue();
    }
    public void removefromliveTahmin(boolean bool){
        myRef.child(mContext.getString(R.string.dbname_tahmin)).child(mContext.getString(R.string.field_situationyayin)).setValue(bool);
    }

    public void giveThePrizeForRankingifAdmin(final DuelloOdul mDuelloOdul, final DataSnapshot dataSnapshot, final ArrayList<Duello> ranking) {
        if(mAuth.getCurrentUser().getUid().equals(mContext.getString(R.string.admin))){
            if(dataSnapshot.child(mContext.getString(R.string.dbname_odul)).child(mContext.getString(R.string.field_dunonay)).exists()){
                boolean onay = dataSnapshot.child(mContext.getString(R.string.dbname_odul)).getValue(Odul.class).isDunonay();
                if(onay){
                    Runnable backGroundRunnable = new Runnable() {
                        public void run(){
                            Calendar calendar ;
                            calendar = Calendar.getInstance();
                            calendar.setTimeZone(TimeZone.getTimeZone(mContext.getString(R.string.timezone)));
                            SimpleDateFormat dateFormat = new SimpleDateFormat(mContext.getString(R.string.timeformat));
                            String currentdate = dateFormat.format(calendar.getTime());
                            String date = dataSnapshot.child(mContext.getString(R.string.dbname_odul)).getValue(Odul.class).getDate();

                            if(!currentdate.equals(date)){
                                Collections.sort(ranking,new Comparator());
                                if(control==0){
                                    for(int i=0;i<ranking.size();i++){
                                        if(ranking.get(i).getIzleme()>20){
                                            changeDailyRankinginDay(ranking.get(i).getUser_id(),(i+1));
                                            saveTheWinners(i+1,ranking.get(i).getUser_id());
                                        }else{
                                            changeDailyRankinginDay(ranking.get(i).getUser_id(),0);
                                        }
                                        changeDailyRanking(ranking.get(i).getUser_id(),ranking.get(i).getIsim(),
                                                ranking.get(i).getIzleme(),i+1);
                                        changeDailyWatchAdandCount(ranking.get(i).getUser_id(),0,0);
                                    }
                                }
                                changeOnayDate(currentdate);
                                control++;
                            }

                            long sira1= mDuelloOdul.getOdul().getSira1();
                            long sira2= mDuelloOdul.getOdul().getSira2();
                            long sira3= mDuelloOdul.getOdul().getSira3();
                            long sira4= mDuelloOdul.getOdul().getSira4();
                            long sira5= mDuelloOdul.getOdul().getSira5();
                            long sira6= mDuelloOdul.getOdul().getSira6();
                            double siraodul1 = mDuelloOdul.getOdul().getSira1odul();
                            String siraodul2 = mDuelloOdul.getOdul().getSira2odul();
                            String siraodul3 = mDuelloOdul.getOdul().getSira3odul();
                            long siraodul4 = mDuelloOdul.getOdul().getSira4odul();
                            long siraodul5 = mDuelloOdul.getOdul().getSira5odul();
                            long siraodul6 = mDuelloOdul.getOdul().getSira6odul();
                            long siratane1 = mDuelloOdul.getOdul().getSira1tane();
                            long siratane2 = mDuelloOdul.getOdul().getSira2tane();
                            long siratane3 = mDuelloOdul.getOdul().getSira3tane();

                            if(dataSnapshot.child(mContext.getString(R.string.dbname_duellokazanan)).exists()){
                                for(DataSnapshot ds : dataSnapshot.child(mContext.getString(R.string.dbname_duellokazanan)).getChildren()){
                                    if(sira1>=(long)ds.getValue()){
                                        givePrizeMoneyForRanking(siraodul1,ds.getKey());
                                    }
                                    else if(sira1+sira2>=(long)ds.getValue()){
                                        givePrizeBoxForRanking(siratane2,siraodul2,ds.getKey());
                                    }
                                    else if(sira1+sira2+sira3>=(long)ds.getValue()){
                                        givePrizeBoxForRanking(siratane3,siraodul3,ds.getKey());
                                    }
                                    else if(sira1+sira2+sira3+sira4>=(long)ds.getValue()){
                                        givePrizeRipForRanking(siraodul4,ds.getKey());
                                    }
                                    else if(sira1+sira2+sira3+sira4+sira5>=(long)ds.getValue()){
                                        givePrizeRipForRanking(siraodul5,ds.getKey());
                                    }
                                    else {
                                        givePrizeRipForRanking(siraodul6,ds.getKey());
                                    }
                                    removeUserFromDuelloList(ds.getKey());
                                }
                            }
                            changeOnay(false);
                        }};
                    Thread sampleThread = new Thread(backGroundRunnable);
                    sampleThread.start();
                }
            }
        }
    }
    public void setProfilePhoto(String url ){
        myRef.child(mContext.getString(R.string.dbname_users)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.field_userpicture)).setValue(url);
        myRef.child(mContext.getString(R.string.dbname_duello)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.field_userpicture)).setValue(url);
    }

    public void giveTheRaffleGift(DataSnapshot dataSnapshot) {
        if(mAuth.getCurrentUser().getUid().equals(mContext.getString(R.string.admin))){
            for(DataSnapshot database : dataSnapshot.getChildren()){
                if(database.getKey().equals(mContext.getString(R.string.dbname_rafflegift))){
                    boolean onay = (boolean) database.child(mContext.getString(R.string.field_onay)).getValue();
                    if(onay){
                        ArrayList<String> useremails = new ArrayList<>();
                        ArrayList<String> userkeys = new ArrayList<>();
                        String odul = database.child(mContext.getString(R.string.field_odul)).getValue().toString();
                        for (DataSnapshot users : database.getChildren()){
                            if(!users.getKey().equals(mContext.getString(R.string.field_odul))){
                                if(!users.getKey().equals(mContext.getString(R.string.field_onay))){
                                    useremails.add(users.getValue().toString());
                                    userkeys.add(users.getKey());
                                }
                            }
                        }
                        final ArrayList<String> finaluserkeys = userkeys;
                        final ArrayList<String> finalusers = useremails;
                        final String finalodul = odul;
                        Query query = myRef.child(mContext.getString(R.string.dbname_users));
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot users : dataSnapshot.getChildren()){
                                    for(int i=0;i<finalusers.size();i++){
                                        if(users.getValue(User.class).getEmail().equals(finalusers.get(i).toString())){
                                            String user_id = users.getValue(User.class).getUser_id();
                                            long newBox;
                                            String boxtype;
                                            if(finalodul.equals( mContext.getString(R.string.field_altin))){
                                                newBox = users.getValue(User.class).getAltinbox();
                                                newBox++;
                                                boxtype= mContext.getString(R.string.field_altinbox);
                                            }
                                            else if(finalodul.equals( mContext.getString(R.string.field_gumus))){
                                                newBox = users.getValue(User.class).getGumusbox();
                                                newBox++;
                                                boxtype= mContext.getString(R.string.field_gumusbox);
                                            }
                                            else{
                                                newBox = users.getValue(User.class).getBronzbox();
                                                newBox++;
                                                boxtype= mContext.getString(R.string.field_bronzbox);
                                            }
                                            myRef.child(mContext.getString(R.string.dbname_users)).child(user_id).child(boxtype).setValue(newBox);
                                            removeUserFromGiftList(finaluserkeys.get(i).toString());
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        changegiftonay(false);
                    }
                }
            }
        }
    }

    private void changegiftonay(boolean bool) {
        myRef.child(mContext.getString(R.string.dbname_rafflegift)).child(mContext.getString(R.string.field_onay)).setValue(bool);
    }

    public void removeUserFromGiftList(String key) {
        myRef.child(mContext.getString(R.string.dbname_rafflegift)).child(key).removeValue();
    }

    public void changeCanWatch(boolean bool) {
        myRef.child(mContext.getString(R.string.dbname_users)).child(userID).child(mContext.getString(R.string.field_canjoin)).setValue(bool);
    }

    public void savetheRequestWithDrawel(String miktar, String iban,String email){
        String key = myRef.child(mContext.getString(R.string.dbname_money)).push().getKey();
        myRef.child(mContext.getString(R.string.dbname_money)).child(key).child(mContext.getString(R.string.field_miktar)).setValue(miktar);
        myRef.child(mContext.getString(R.string.dbname_money)).child(key).child(mContext.getString(R.string.field_iban)).setValue(iban);
        myRef.child(mContext.getString(R.string.dbname_money)).child(key).child(mContext.getString(R.string.field_email)).setValue(email);
        myRef.child(mContext.getString(R.string.dbname_users)).child(userID).child(mContext.getString(R.string.field_money)).setValue(0.001);
    }

    public void findwinner(String kazuserid,String prizekey) {
        if(mAuth.getCurrentUser().getUid().equals(mContext.getString(R.string.admin))){
            Query query = myRef.child(mContext.getString(R.string.dbname_users));
            final String kaz = kazuserid;
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot users : dataSnapshot.getChildren()){
                        if(users.getValue(User.class).getEmail().equals(kaz)){
                            long odulsayisi = users.getValue(User.class).getPrize();
                            odulsayisi++;
                            myRef.child(mContext.getString(R.string.dbname_users)).child(users.getKey())
                                    .child(mContext.getString(R.string.field_prize)).setValue(odulsayisi);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            myRef.child(mContext.getString(R.string.dbname_prize)).child(prizekey).child(mContext.getString(R.string.field_situationoynanma)).setValue(false);

        }
    }
}
