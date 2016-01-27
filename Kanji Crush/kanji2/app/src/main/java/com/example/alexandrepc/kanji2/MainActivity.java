package com.example.alexandrepc.kanji2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String credits = "Université Bordeaux-Montaigne\n"+
            "UFR Langues et Civilisations 3\n"+
            "en collaboration avec l'ENSEIRB-MATMECA\n\n"+
            "Développeurs :\n" +
            "\tAlexandre PATRY\n" +
            "\tAlice DE CHATILLON\n" +
            "\tCyprien SALOMEZ\n" +
            "\tJibril MIRAOUI\n" +
            "\tLaurent CIDERE\n" +
            "\tOmar LAHLOU\n" +
            "\tValentin DARRICAU\n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        findViewById(R.id.imageButton).startAnimation(uptodown);
        findViewById(R.id.imageButton2).startAnimation(uptodown);
        findViewById(R.id.imageButton3).startAnimation(uptodown);

        TextView myTextView=(TextView)findViewById(R.id.textViewChoiceTitle);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/harakiri.ttf");
        myTextView.setTypeface(typeFace);


        Button buttonsuiv = (Button) findViewById(R.id.imageButton);
        buttonsuiv.setOnClickListener(new View.OnClickListener() {

            @Override
            //Intent permettent d’envoyer et recevoir des messages
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
                startActivity(intent);
            }
        });

        Button buttonprofile = (Button) findViewById(R.id.imageButton2);
        buttonprofile.setOnClickListener(new View.OnClickListener() {

            @Override
            //Intent permettent d’envoyer et recevoir des messages
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        Button buttoncredits = (Button) findViewById(R.id.imageButton3);
        buttoncredits.setOnClickListener(new View.OnClickListener() {

            @Override
            //Intent permettent d’envoyer et recevoir des messages
            public void onClick(View v) {
                showDialogCredits();
            }
        });
    }

    private void showDialogCredits() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Crédits");
        alertDialog.setMessage(credits);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                //...

            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}

