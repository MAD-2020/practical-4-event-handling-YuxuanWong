package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="Whack-A-Mole! 1.0";
    private TextView score;
    private int count = 0;
    private static final int[] BUTTON_IDS = {
            R.id.button,R.id.button2,R.id.button3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = findViewById(R.id.textView);
        for(final int id : BUTTON_IDS){
            findViewById(id).setOnClickListener(clicked);
        }
        Log.d(TAG, "Finished Pre-Initialisation!");
    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.d(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        if (checkButton.getText() == "*"){
            count += 1;
            score.setText(String.valueOf(count));
            Log.d(TAG, "Hit, score added!");
        }
        else {
            count -= 1;
            score.setText(String.valueOf(count));
            Log.d(TAG, "Missed, score deducted!");
        }

        if (count % 10 == 0 && count > 0){
            nextLevelQuery();
        }
    }

    private void nextLevelQuery(){
        AlertDialog.Builder nextLvl = new AlertDialog.Builder(MainActivity.this);
        nextLvl.setTitle("Warning! Insane Whack-A-Mole incoming!");
        nextLvl.setMessage("Would you like to advance to advance mode?");
        nextLvl.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "User accepts!");
                        nextLevel();
                    }
                });
        nextLvl.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "User decline!");
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = nextLvl.create();
        dialog.show();
        Log.d(TAG,"Advance option given to user!");
    }

    private void nextLevel(){
        Intent advanceLevel = new Intent(MainActivity.this, Main2Activity.class);
        advanceLevel.putExtra("score", score.getText());
        startActivity(advanceLevel);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        for (final int id : BUTTON_IDS){
            Button button = findViewById(id);
            button.setText("O");
        }
        Button b = findViewById(BUTTON_IDS[randomLocation]);
        b.setText("*");
    }

    private View.OnClickListener clicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button){
                Log.d(TAG,"Button Right Clicked!");
            }
            else if (v.getId() == R.id.button2){
                Log.d(TAG,"Button Centre Clicked!");
            }
            else{
                Log.d(TAG,"Button Left Clicked!");
            }
            Button view = (Button) v;
            doCheck(view);
            setNewMole();
        }
    };
}
