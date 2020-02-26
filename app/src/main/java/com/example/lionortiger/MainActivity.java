package com.example.lionortiger;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.app.DownloadManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player{
        ONE, TWO, NONE
    }

    Player currentPlayer = Player.ONE;

    Player[] playerChoices =new Player[9];// which array index is chosen by which player

    int[][] winnerArrays = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private boolean gameOver= false;
    private int countTag =0;

    private Button buttonReset;

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int arrCount=0; arrCount < playerChoices.length; arrCount++){
            playerChoices[arrCount] = Player.NONE;
        }
        gridLayout =findViewById(R.id.gridLayout);
        buttonReset =findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTheGame();
            }
        });
    }
    public void imageViewIsTapped(View imageView){    //onClick attribute function is defined here
        View tappedImageView = (ImageView) imageView; //typecasting from View -> ImageView

        int tappedImageTag = Integer.parseInt(tappedImageView.getTag().toString());
        countTag += tappedImageTag;

        if(playerChoices[tappedImageTag] == Player.NONE && gameOver == false) { //change image if and only if none of the players has tapped on the imageView previously
                  //also if the game is not over
            playerChoices[tappedImageTag] = currentPlayer;

            if (currentPlayer == Player.ONE) {
                tappedImageView.setTranslationX(-2000);
                tappedImageView.setBackgroundResource(R.drawable.lion);
                tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1200);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setTranslationX(2000);
                tappedImageView.setBackgroundResource(R.drawable.tiger);
                tappedImageView.animate().translationXBy(-2000).alpha(1).rotation(3600).setDuration(1200);

                currentPlayer = Player.ONE;
            }
           // Toast.makeText(this, tappedImageView.getTag().toString(), Toast.LENGTH_SHORT).show();//to get the tag which image view is tapped

            for (int[] winnerCoulmns : winnerArrays) { //the reason for using int array is that inside 2D array we have rows and
                //inside each row we have columns of type integer
                //also we refer to only the coulmns of the winner array i.e, {0,1,2} or {3,4,5}....
                if (playerChoices[winnerCoulmns[0]] ==
                        playerChoices[winnerCoulmns[1]]
                        && playerChoices[winnerCoulmns[1]] ==
                        playerChoices[winnerCoulmns[2]] &&
                        playerChoices[winnerCoulmns[0]] != Player.NONE) {

                    buttonReset.setVisibility(View.VISIBLE); //set the visibility of the reset button to visible if the game has finished
                    gameOver =true;

                    String winnerOfGame = "";
                    if (currentPlayer == Player.ONE) {
                        //Toast.makeText(this,"Player 2 is winner",Toast.LENGTH_LONG).show();
                        winnerOfGame = "Prakash";
                    } else if (currentPlayer == Player.TWO) {
                        //Toast.makeText(this, "Player 1 is winner", Toast.LENGTH_LONG).show();
                        winnerOfGame = "Srishti";
                    }

                    Toast.makeText(this, winnerOfGame + " is the winner", Toast.LENGTH_LONG).show();
                }
            }
        }
        if(countTag ==36){
            Toast.makeText(this,"This is a tie", Toast.LENGTH_LONG).show();
            gameOver = true;
            buttonReset.setVisibility(View.VISIBLE);
        }
    }

    //Reset game function
    private void resetTheGame(){
        for(int index =0; index < gridLayout.getChildCount(); index++){
            ImageView imageView = (ImageView) gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.color.colorBack);
            imageView.setAlpha(1.0f);

        }
        currentPlayer = Player.ONE;

        for(int arrCount=0; arrCount < playerChoices.length; arrCount++){
            playerChoices[arrCount] = Player.NONE;
        }
        gameOver = false;

        buttonReset.setVisibility(View.INVISIBLE);
        countTag =0;
    }

}
