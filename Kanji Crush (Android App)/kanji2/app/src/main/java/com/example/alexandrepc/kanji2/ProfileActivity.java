package com.example.alexandrepc.kanji2;

/**
 * Classe ProfileActivity
 */

/**
 * \file      ProfileActivity.java
 * \version   1.0
 * \date      29/03/2015
 * \brief     Classe gérant l'activité profil
  */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.w3c.dom.Text;

public class ProfileActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/harakiri.ttf");
        TextView TextViewTitle=(TextView)findViewById(R.id.textViewProfileTitle);
        TextViewTitle.setTypeface(typeFace);
        TextView TextViewExp=(TextView)findViewById(R.id.TextViewProfileExp);
        TextViewExp.setTypeface(typeFace);
        TextView TextViewJokers=(TextView)findViewById(R.id.TextViewProfileJokers);
        TextViewJokers.setTypeface(typeFace);

        //!Creation du profil
        Profile myProfile = new Profile(getBaseContext());
        //find progress bar, textprogress, level ...views.
        ProgressBar progress = (ProgressBar)findViewById(R.id.ProgressBar);
        TextView textViewProgress = (TextView)findViewById(R.id.Progression);
        TextView textViewLevel = (TextView)findViewById(R.id.Level);
        TextView textViewBestScore = (TextView)findViewById(R.id.BestScore);
        //!Mise à jour de la barre d'expérience
        progress.setMax(myProfile.experienceToReach());


        if (myProfile.getLevel()== 1){
            progress.setProgress(myProfile.getExperience());
        }
        else{
            progress.setProgress(myProfile.getExperience()- myProfile.previousExperience());
        }
        textViewProgress.setText(progress.getProgress()+"/"+progress.getMax());
        textViewLevel.setText("Niveau : " + String.valueOf(myProfile.getLevel()));
        textViewBestScore.setText("Meilleur score : " + String.valueOf(myProfile.getBestScore()));

        TextView textXp = (TextView)findViewById(R.id.totalXp);
        textXp.setText("Expérience cumulée : " + String.valueOf(myProfile.getExperience()));

        TextView textRank = (TextView)findViewById(R.id.rank);
        textRank.setText("Rang : " + myProfile.getRank());

        Button tabButtonNbJoker []  = new Button[5];
        tabButtonNbJoker[0] = (Button) findViewById(R.id.NbJokTime);
        tabButtonNbJoker[1] = (Button) findViewById(R.id.NbJokBomb);
        tabButtonNbJoker[2] = (Button) findViewById(R.id.NbJokIndication);
        tabButtonNbJoker[3] = (Button) findViewById(R.id.NbJokLineBomb);
        tabButtonNbJoker[4] = (Button) findViewById(R.id.NbJokColumnBomb);

        tabButtonNbJoker[0].setText(String.valueOf(myProfile.getJoker_time()));
        tabButtonNbJoker[1].setText(String.valueOf(myProfile.getJoker_bomb()));
        tabButtonNbJoker[2].setText(String.valueOf(myProfile.getJoker_indication()));
        tabButtonNbJoker[3].setText(String.valueOf(myProfile.getJoker_linebomb()));
        tabButtonNbJoker[4].setText(String.valueOf(myProfile.getJoker_colbomb()));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //!Rajoute a la barre d'action un onglet si présent
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
