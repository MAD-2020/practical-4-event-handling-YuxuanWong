package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG="Whack-A-Mole! 2.0";
    private TextView advancedScore;
    private int count = 0;

    private void readyTimer(){
        CountDownTimer cdt = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished){
                Toast.makeText(getApplicationContext(), "Get ready in " + millisUntilFinished/ 1000 + " seconds",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            }
            public void onFinish(){
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Ready CountDown Complete!");
            }
        };
        cdt.start();
    }

    private void placeMoleTimer(){
        final CountDownTimer cdt = new CountDownTimer(Long.MAX_VALUE,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "New Mole Location!");
                setNewMole();
            }
            @Override
            public void onFinish() {
                cancel();
            }
        };
        cdt.start();
    }

    private static final int[] BUTTON_IDS = {
            R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,
            R.id.button9,R.id.button10,R.id.button11,R.id.button12
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        advancedScore = findViewById(R.id.textView2);
        Intent scored = getIntent();
        String score = scored.getStringExtra("score");
        advancedScore.setText(score);
        count = Integer.parseInt(score);
        Log.d(TAG, "Current User Score: " + score);

        readyTimer();

        for(final int id : BUTTON_IDS){
            findViewById(id).setOnClickListener(clicked);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
    }

    private void doCheck(Button checkButton)
    {
        if (checkButton.getText() == "*"){
            count += 1;
            advancedScore.setText(String.valueOf(count));
            Log.d(TAG, "Hit, score added!");
        }
        else {
            count -= 1;
            advancedScore.setText(String.valueOf(count));
            Log.d(TAG, "Missed, score deducted!");
        }
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        for (final int id : BUTTON_IDS){
            Button button = findViewById(id);
            button.setText("O");
        }
        Button b = findViewById(BUTTON_IDS[randomLocation]);
        b.setText("*");
    }

    private View.OnClickListener clicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            doCheck(button);
            placeMoleTimer();
        }
    };
}