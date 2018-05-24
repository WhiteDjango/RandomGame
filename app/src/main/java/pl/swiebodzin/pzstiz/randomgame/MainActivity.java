package pl.swiebodzin.pzstiz.randomgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView player1;
    TextView player2;
    TextView pointsPlayer1;
    TextView pointsPlayer2;
    TextView currentNumber;
    EditText editText;
    int currentPlayer = 1;
    int counter = 5;
    int collectPoints1;
    int collectPoints2;
    int playerNum = 0;
    int number;
    int globalCounter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        currentNumber = findViewById(R.id.currentNumber);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        pointsPlayer1 = findViewById(R.id.pointsPlayer1);
        pointsPlayer2 = findViewById(R.id.pointsPlayer2);
        editText = findViewById(R.id.editText);

        button.setText("START");
        currentNumber.setText("");
        player1.setText("your turn");
        player2.setText("");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = getRandomNumber();
                playerNum = getNumberFromEditText();
                if(playerNum != 0){
                    gameLogic();
                }
            }
        });


    }

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(10);
    }

    //przelacza pomiedzy graczami
    public int shiftPlayer(int p) {
        switch (p) {
            case 1:
                currentPlayer = 2;
                player2.setText("your turn");
                player1.setText("");
                counter = 5;
                break;
            case 2:
                currentPlayer = 1;
                player1.setText("your turn");
                player2.setText("");
                counter = 5;
                break;
        }
        return currentPlayer;
    }


    public void gameLogic() {


        try{
            currentNumber.setText(String.valueOf(number));
            counter--;
            button.setText(String.valueOf(counter));
            if (counter == 0) {
                checkCounter();
                shiftPlayer(currentPlayer);
            }
            int number = Integer.parseInt(currentNumber.getText().toString());
            playerNum = Integer.parseInt(editText.getText().toString());

            //liczy punkty

            if (number == playerNum && playerNum != 0) {

                switch (currentPlayer) {
                    case 1:
                        collectPoints1++;
                        pointsPlayer1.setText(String.valueOf(collectPoints1));
                        break;

                    case 2:
                        collectPoints2++;
                        pointsPlayer2.setText(String.valueOf(collectPoints2));
                        break;
                }
            }
        }catch (NumberFormatException e){
            Toast.makeText(getBaseContext(), "Podaj liczbę", Toast.LENGTH_SHORT).show();
        }
    }
    public int getNumberFromEditText(){

        try {
            return  Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("errors", "number not found!!!");
        }
        return  0;

    }
    public void checkCounter(){
        if(counter == 0 ){
            globalCounter--;
            if (globalCounter == 0){
                getGameOverActivity();
            }
        }

    }
    public void getGameOverActivity(){
        Intent intent = new Intent(this,activity_game_over.class);
        startActivity(intent);
    }
}
