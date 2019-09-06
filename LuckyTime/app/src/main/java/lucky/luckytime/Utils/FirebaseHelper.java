package lucky.luckytime.Utils;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.os.Handler;

import lucky.luckytime.Model.Competition;
import lucky.luckytime.Model.Prize;
import lucky.luckytime.R;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved;
    ArrayList<Prize> prizes = new ArrayList<>();
    ListView mListView;
    Context c;
    FirebaseMethods mfirebaseMethods;

    /*
   let's receive a reference to our FirebaseDatabase
   */
    public FirebaseHelper(DatabaseReference db, Context context, ListView mListView) {
        this.db = db;
        this.c = context;
        this.mListView = mListView;
        //this.retrieve();
    }

    /*
    let's now write how to save a single Teacher to FirebaseDatabase
     */
    /*public Boolean save(Teacher teacher) {
        //check if they have passed us a valid teacher. If so then return false.
        if (teacher == null) {
            saved = false;
        } else {
            //otherwise try to push data to firebase database.
            try {
                //push data to FirebaseDatabase. Table or Child called Teacher will be created.
                db.child("Teacher").push().setValue(teacher);
                saved = true;

            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        //tell them of status of save.
        return saved;
    }*/

    /*
    Retrieve and Return them clean data in an arraylist so that they just bind it to ListView.
     */
    /*public ArrayList<Prize> retrieve() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prizes.clear();
                Competition competition = mfirebaseMethods.getCompetitionInfo(dataSnapshot);
                    ListViewPrizeHelper adapter = new ListViewPrizeHelper(c,competition);
                    mListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mListView.smoothScrollToPosition(prizes.size());
                        }
                    });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return prizes;
    }*/

}

