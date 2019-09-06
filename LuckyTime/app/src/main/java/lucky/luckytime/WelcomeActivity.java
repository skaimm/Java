package lucky.luckytime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout lin1,lin2,lin3;
    private Animation updown,downup,transition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        lin1 = (LinearLayout) findViewById(R.id.lin1);
        lin2 = (LinearLayout) findViewById(R.id.lin2);
        lin3 = (LinearLayout) findViewById(R.id.lin3);

        updown =AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downup =AnimationUtils.loadAnimation(this,R.anim.downtoup);
        transition = AnimationUtils.loadAnimation(this,R.anim.transition);
        lin1.setAnimation(updown);
        lin2.setAnimation(downup);
        lin3.setAnimation(transition);

        final Intent intent = new Intent (WelcomeActivity.this, GainPrize.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
