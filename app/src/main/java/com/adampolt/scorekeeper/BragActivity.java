package com.adampolt.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BragActivity extends AppCompatActivity {
    //These are keys we'll use to get
    public static final String PLAYER_ONE_SCORE_EXTRA = "extra_player_one_score";
    public static final String PLAYER_TWO_SCORE_EXTRA = "extra_player_two_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Always call super.onCreate first
        super.onCreate(savedInstanceState);

        //Set the Activity's layout to the activity_brag.xml layout file:
        setContentView(R.layout.activity_brag);

        //Get the score strings we passed as extras on the intent in MainActivity:
        String player1Score = getIntent().getStringExtra(PLAYER_ONE_SCORE_EXTRA);
        String player2Score = getIntent().getStringExtra(PLAYER_TWO_SCORE_EXTRA);

        //Get references to our views:
        final EditText bragText = findViewById(R.id.bragText);
        Button bragButton = findViewById(R.id.bragButton);

        //Set some suggested Share text as a default (The user can modify it before sending)
        bragText.setText("Player 1 score: " + player1Score + ", Player 2 score: " + player2Score);

        //The Brag button sends the text as an implicit intent, which means any activity that
        //implements the specified action and type can be the intent's target
        bragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create the implicit intent with "ACTION_SEND" -- notice that we're not specifying
                //a specific activity in the Intent's constructor
                Intent bragImplicitIntent = new Intent(Intent.ACTION_SEND);

                //Add the text as an Extra on the Intent (similar to how we passed the scores into
                //this Activity)
                bragImplicitIntent.putExtra(Intent.EXTRA_TEXT, bragText.getText().toString());

                //Set the type as text/plain so activities know what we actually want to send
                bragImplicitIntent.setType("text/plain");

                //Call StartActivity on the Intent. Android will query the apps that are installed
                //and figure out which apps can perform ACTION_SEND on something of type "text/plain"
                startActivity(bragImplicitIntent);
            }
        });
    }
}
