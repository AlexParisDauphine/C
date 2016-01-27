package com.example.alexandrepc.kanji2;
/**
 * Classe SplashScreenActivity
 */

/**
 * \file      SplashScreenActivity.java
 * \version   1.0
 * \date      29/03/2015
 * \brief     Interface de lancement
 *
 * \details   Cette classe permet d'afficher l'interface de lancement de l'application
 */


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {

    //!Mise en place du timer splashscreen
    private static final int SPLASH_TIME_OUT = 1500;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_lauched);
	
        //! Mise en place de police de titre de l'application
        TextView myTextView = (TextView) findViewById(R.id.TextViewLaunched2);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/harakiri.ttf");
        myTextView.setTypeface(typeFace);
	
	
        new Handler().postDelayed(new Runnable() {
		
		/**
		 * Afficher le splachscreen avec une temporisation déterminée par le timer
		 
		 
		 /**
		 * \brief       Fonction run qui s'exécute une fois que le timer est dépassé
		 */
		
		
		@Override
		public void run() {
		    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
		    startActivity(i);
		    
		    finish();
		}
	    }, SPLASH_TIME_OUT);
    }
    
}
