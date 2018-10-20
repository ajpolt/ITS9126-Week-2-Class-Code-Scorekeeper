package com.adampolt.scorekeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This Activity keeps track of scores for two players in a long-running game.
 *
 * The scores are saved between app opening/closing and even device restarts using SharedPreferences
 */
public class MainActivity extends AppCompatActivity {
    private static final String SCORE_PREFERENCE_NAME = "scores";

    private static final String PLAYER_ONE_PREFS_KEY = "player1score";
    private static final String PLAYER_TWO_PREFS_KEY = "player2score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //The first line of our onCreate method needs to call super.onCreate
        super.onCreate(savedInstanceState);

        //Let our activity know which layout file to display
        setContentView(R.layout.activity_main);

        //Now that we've set up the layout, we can get references to all of our views and save them
        //in variables:

        //Score-incrementing buttons
        Button player1Won = findViewById(R.id.player1Won);
        Button player2Won = findViewById(R.id.player2Won);

        //Buttons for clearing data and going to the Share screen:
        Button clearButton = findViewById(R.id.clear);
        Button bragButton = findViewById(R.id.brag);

        //These views are where we will display the scores
        //They are referenced inside our onClickListeners so we have to declare them as final
        final TextView player1ScoreView = findViewById(R.id.player1Score);
        final TextView player2ScoreView = findViewById(R.id.player2Score);

        //Get the shared preferences named "Scores" -- this must be declared "final" because it's used
        //inside onClickListeners
        final SharedPreferences scorePreferences = getSharedPreferences(SCORE_PREFERENCE_NAME, 0);

        //Fetch the scores from shared preferences and load them into the score textViews:
        player1ScoreView.setText(String.valueOf(scorePreferences.getInt(PLAYER_ONE_PREFS_KEY, 0)));
        player2ScoreView.setText(String.valueOf(scorePreferences.getInt(PLAYER_TWO_PREFS_KEY, 0)));

        //This button should increment player one's score -- we'll set up an onClickListener to do that
        player1Won.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the current value from shared preferences
                int player1Score = scorePreferences.getInt(PLAYER_ONE_PREFS_KEY, 0);
                //Increment it by one
                player1Score += 1;

                //Get a shared preferences editor from the Shared Preferences
                SharedPreferences.Editor editor = scorePreferences.edit();
                //Add the updated score to the editor
                editor.putInt(PLAYER_ONE_PREFS_KEY, player1Score);
                //Remember to call commit() or apply() to actually save the values
                editor.apply();

                //Update the player's view to reflect the new value
                //(you'll have to wrap any integers with String.valueOf or else it won't work)
                player1ScoreView.setText(String.valueOf(player1Score));
            }
        });

        //This button should increment player two's score in its onClickListener
        player2Won.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Same process as above, but with a different Key string:
                int player2Score = scorePreferences.getInt(PLAYER_TWO_PREFS_KEY, 0);

                SharedPreferences.Editor editor = scorePreferences.edit();
                editor.putInt(PLAYER_TWO_PREFS_KEY, ++player2Score);
                editor.apply();

                player2ScoreView.setText(String.valueOf(player2Score));
            }
        });

        //This button resets everything in our Scores shared preferences
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the shared preferences editor, and call "clear()" and then "commit()" or
                //"apply()" on it to clear all values.
                getSharedPreferences(SCORE_PREFERENCE_NAME, 0)
                        .edit()
                        .clear() //Each call here returns an Editor so we can chain the calls like this
                        .apply();

                //Reset the text of the score views to zero
                player1ScoreView.setText("0");
                player2ScoreView.setText("0");
            }
        });

        //This button goes to a Share Activity where the user can share the scores
        bragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create an Explicit Intent that points to the BragActivity class
                Intent bragIntent = new Intent(MainActivity.this, BragActivity.class);

                //Add the players' scores as Extras in the intent--so we can get them in BragActivity
                bragIntent.putExtra(BragActivity.PLAYER_ONE_SCORE_EXTRA, player1ScoreView.getText());
                bragIntent.putExtra(BragActivity.PLAYER_TWO_SCORE_EXTRA, player2ScoreView.getText());

                //Start the activity
                startActivity(bragIntent);
            }
        });
    }
}
