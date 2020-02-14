package com.example.gameconnect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0: yellow, 1:red, 2:emptyState
    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winners = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive = true;

    public void gridBoard(View view){
       /** ImageView imageView = findViewById(R.id.imageView0);
        imageView.setTranslationY(-300);
        imageView.setImageResource(R.drawable.yellow);
        imageView.animate().translationYBy(300).setDuration(100); **/

        ImageView counter = (ImageView) view; //this considers the entire view instead of requiring to enter each individual Coin's ID

        Log.i("Tag Info: ", counter.getTag().toString());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive) {

            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-300);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(300).setDuration(100);
            for (int[] win : winners) {
                if (gameState[win[0]] == gameState[win[1]] && gameState[win[1]] == gameState[win[2]] && gameState[win[0]] != 2) {
                    gameActive = false;
                    String winner = "";
                    if(activePlayer == 0){
                        winner = "Red"; //because the active player always changes to the next color when the other color wins
                    }
                    winner = "Yellow";

                    displayWinner(view, winner);
                }
            }
        }
    }

    public void displayWinner(View view, String winner){
        TextView textView = findViewById(R.id.textView);
        Button playAgain = findViewById(R.id.button2);
        textView.setText( winner + " has won!");
        textView.animate().alpha(1).translationY(-50).setDuration(500);
        playAgain.animate().alpha(1).translationY(-50).setDuration(500);
    }

    public void clickPlayAgain(View view){
        TextView textView = findViewById(R.id.textView);
        Button playAgain = findViewById(R.id.button2);
        textView.animate().alpha(0).translationY(-50).setDuration(500);
        playAgain.animate().alpha(0).translationY(-50).setDuration(500);

        //clear the grid
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        //In order to clear every element in the grid layout, we have to loop
        //through the items and set image drawable to null
        for(int i=0; i<gridLayout.getChildCount(); i++){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        //resetting  the state of the game
        for(int i =0; i<gameState.length; i++){
            gameState[i] = 2;
        }
        activePlayer = 0; //resetting active player back to 0 (yellow)
        gameActive = true; //reactivating the game
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
