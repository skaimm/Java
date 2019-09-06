package lucky.luckytime.LoginRegister;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import lucky.luckytime.R;

public class SozlesmeActivity extends AppCompatActivity {

    private Context mContext = SozlesmeActivity.this;
    TextView title,context;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sozlesme);

        title = (TextView) findViewById(R.id.tv_title);
        context = (TextView) findViewById(R.id.tv_context);
        button = (Button) findViewById(R.id.btn_okay);

        title.setText(getIntent().getStringExtra("title"));
        context.setText(getIntent().getStringExtra("context"));
    }
}

