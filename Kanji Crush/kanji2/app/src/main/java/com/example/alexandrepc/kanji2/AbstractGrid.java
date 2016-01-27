package com.example.alexandrepc.kanji2;

/**
 * Classe AbstractGrid
 */

/**
 * \file      AbstractGrid.java
 * \version   1.0
 * \date      29/03/2015
 * \brief     Classe abstraite de grid
 *
 * \details   Cette classe regroupe les methodes communes aux Grid
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



abstract public class AbstractGrid extends Activity{

    protected static final float ALPHA_SELECTED = 0.1F;
    protected static final float ALPHA_UNSELECTED = 1F;

    protected int modeSelected;  //Mode de jeu actuel
    protected Plateau mainPlateau; //Plateau princioal

    protected boolean isViewPhonSelected = false; //True si un élément de la listView Phonétique est sélectionné
    protected boolean isViewSensSelected = false; //True si un élément de la listView Sens est sélectionné

    protected View currentViewPhon; //Phonétique sélectionné dans la listView Phonétique
    protected View currentViewSens; //Sens sélectionné dans la listView Sens


    protected Kanji currentKanji; //Kanji sélectionné
    protected int positionKanji; //Position du kanji selectionné dans la grille

    protected Button tabButton[];    //Tableau de boutons correspondant aux boutons de la grille
    protected Button tabButtonJoker[]; //Tableau de boutons correspondant aux boutons jokers
    protected Button tabButtonNbJoker[]; //Tableau de boutons correspondant aux nombres de jokers disponibles

    protected boolean boolPosition;  // Sert a corriger le "problème du bouton 0"

    protected int score; // Score asociation

    protected TextView textViewScore; //TextView affichant le score association
    protected TextView textViewInfo; //TextView affichant diverses informations
    protected Score classScore; // Variable de type Score, contenant notamment le score actuel de la partie
    protected Chronometer chrono; //Chronometre

    protected String currentTime; // ??

    protected long time; // Permet le calcul du temps final
    protected double second; // Permet le calcul du temps final

    protected int nbKanjiAdd;      // Nombre de kanji a ajouter en cas de mauvaise association

    protected GridView mListCaractere = null;  //GridView contenant la grille de boutons

    protected Joker joker; // Variable de type Joker permettant d'invoquer les jokers
    protected Profile profile; // Varible de type Profile contenant diverses informations sur le joueur

    protected boolean gameLost = false; // Vrai si la partie est perdue, faux sinon
    protected ShareDialog shareDialog; // Permet le partage sur facebook



    /* PRIVATE METHODS */

    /**
     * \brief     Ajoute une liste de kanji à la base de donnée
     * \return    Une liste de kanji
     */
    private void parseKanji(ArrayList al,DatabaseHelper db){
        String kanji = "";
        String phonetique = "";
        String sens = "";
        int j;

        for (int i=0; i<al.size();i++) {
            String[] string = (String[]) al.get(i); // Affiche la ligne i du fichier "Hiragana1.csv"
            String line = string[0];
            j = 0;
            while (j<line.length() && line.charAt(j) != ';'){
                kanji += line.charAt(j);
                j++;
            }
            j++;
            while (j<line.length() && line.charAt(j) != ';'){
                phonetique += line.charAt(j);
                j++;
            }
            j++;
            while (j<line.length()){
                sens += line.charAt(j);
                j++;
            }

            db.addKanji(new Kanji(kanji,phonetique,sens));
            kanji="";
            phonetique="";
            sens="";
        }

    }


    /**
     * \brief     Initialise les attributs communs aux trois AbstractGrid
     * \return    Pas de retour
     */
    private void initAttribute (){
        currentKanji = new Kanji();
        positionKanji = -1;
        score = 0;
        tabButton = new Button [25];
        tabButtonJoker = new Button[5];
        tabButtonNbJoker = new Button[5];
        boolPosition = true;
        modeSelected = ChoiceActivity.getMode();
        nbKanjiAdd = 2;
        currentTime="";
        time = 0;
        second = 0;

        textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewScore.setText(Integer.toString(score));
        textViewInfo = (TextView) findViewById(R.id.textViewInfo);
        tabButtonNbJoker[0] = (Button) findViewById(R.id.GridNbJokTime);
        tabButtonNbJoker[1] = (Button) findViewById(R.id.NbJokBomb);
        tabButtonNbJoker[2] = (Button) findViewById(R.id.NbJokIndication);
        tabButtonNbJoker[3] = (Button) findViewById(R.id.NbJokLineBomb);
        tabButtonNbJoker[4] = (Button) findViewById(R.id.NbJokColumnBomb);
        chrono=(Chronometer)findViewById(R.id.chronometer);
        mListCaractere = (GridView) findViewById(R.id.Grid);
        joker = new Joker (getBaseContext(), chrono, textViewInfo);
        classScore  = new Score(this, getBaseContext(), textViewScore, textViewInfo);

        tabButtonNbJoker[0].setText(String.valueOf(profile.getJoker_time()));
        tabButtonNbJoker[1].setText(String.valueOf(profile.getJoker_bomb()));
        tabButtonNbJoker[2].setText(String.valueOf(profile.getJoker_indication()));
        tabButtonNbJoker[3].setText(String.valueOf(profile.getJoker_linebomb()));
        tabButtonNbJoker[4].setText(String.valueOf(profile.getJoker_colbomb()));


    }



    /* PROTECTED METHODS */

    /**
     * \brief     Instancie une Hash map à partir d'une clé et d'un nom
     * \return    une HashMap
     */
    protected HashMap<String, String> createKanji(String key, String name) {
        HashMap<String, String> kanji = new HashMap<>();
        kanji.put(key, name);
        return kanji;
    }

    /**
     * \brief     Réinitialise la position du kanji courant
     * \return    Pas de retour
     */
    protected void resetCurrentPosition (){
        positionKanji = -1;
        currentKanji.setNull();
    }


    /**
     * \brief     Réinitialise le fond de tous les boutons de la grille
     * \return    Pas de retour
     */
    protected void resetBackgroundColorGrid (Button [] tab){
        for (int i = 0; i< tab.length; i++){
            tab[i].setBackgroundColor(Color.BLACK);
        }
    }

    /**
     * \brief     Réinitialise le fond de tous les boutons jokers
     * \return    Pas de retour
     */
    protected void resetBackgroundColorJoker (Button [] tab){
        for (int i = 0; i< tab.length; i++){
            tab[i].setAlpha(ALPHA_UNSELECTED);
        }
    }


    /* PUBLIC METHODS */

    /**
     * \brief     Converti des secondes en minutes
     * \return    Chaine de caractère contenant le temps au format xx min xx sec
     */
    public static String convertSecondToMinute (int sec){
        String res;
        if (sec >= 60)
            res = String.valueOf(sec / 60)+" min "+ String.valueOf(sec % 60)+" seconds";
        else
            res = String.valueOf(sec)+" seconds";


        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    /* ONCREATE */

    /**
     * \brief     Methode principale
     * \return    Pas de retour
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);


        setContentView(R.layout.activity_grid);

        profile = new Profile (this);
        initAttribute();

        chrono.start();

        //!On parse un fichier csv puis on remplit la base de donnée
        String csvFile = ChoiceActivity.getLevel();
        ArrayList al = new ArrayList();
        try {
            CSVFile cs = new CSVFile(getAssets().open(csvFile));
            al = cs.read();
 /*       } catch (FileNotFoundException e) {
            e.printStackTrace();*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseHelper db = new DatabaseHelper(this);
        db.onDelete();
        parseKanji(al,db);
        db.close();

        mainPlateau = new Plateau(db.getAllKanjis(), 25);

        //!Animation dans le cas ou un joker est indisponible
        final Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(8);


        Button buttonTime = (Button) findViewById(R.id.buttonTime);
        tabButtonJoker[0] = buttonTime;
        buttonTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (isViewPhonSelected)
                    currentViewPhon.setBackgroundColor(Color.TRANSPARENT);

                if (isViewSensSelected)
                    currentViewSens.setBackgroundColor(Color.TRANSPARENT);

                resetBackgroundColorJoker(tabButtonJoker);
                resetBackgroundColorGrid(tabButton);
                resetCurrentPosition();
                joker.resetAllJokerBomb();

                if (profile.getJoker_time() > 0) {
                    joker.freezeTime(10);
                    profile.setJoker_time(-1);
                    tabButtonNbJoker[0].setText(Integer.toString(profile.getJoker_time()));
                } else {
                    textViewInfo.setText("Joker temps non disponible");
                    tabButtonJoker[0].startAnimation(anim);
                }
            }


        });


        Button buttonBomb = (Button) findViewById(R.id.buttonDelete);
        tabButtonJoker[1] = buttonBomb;
        buttonBomb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (isViewPhonSelected)
                    currentViewPhon.setBackgroundColor(Color.TRANSPARENT);

                if (isViewSensSelected)
                    currentViewSens.setBackgroundColor(Color.TRANSPARENT);

                resetBackgroundColorJoker(tabButtonJoker);
                resetBackgroundColorGrid(tabButton);
                resetCurrentPosition();

                if (joker.isJokerBomb()) {
                    joker.resetAllJokerBomb();
                } else {
                    joker.resetAllJokerBomb();
                    if (profile.getJoker_bomb() > 0) {
                        tabButtonJoker[1].setAlpha(ALPHA_SELECTED);
                        joker.setJokerBomb(true);

                    } else {
                        tabButtonJoker[1].startAnimation(anim);
                        textViewInfo.setText("Joker Bomb non disponible");
                    }
                }
            }


        });


        Button buttonIndication = (Button) findViewById(R.id.buttonIndication);
        tabButtonJoker[2] = buttonIndication;
        buttonIndication.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (isViewPhonSelected)
                    currentViewPhon.setBackgroundColor(Color.TRANSPARENT);

                if (isViewSensSelected)
                    currentViewSens.setBackgroundColor(Color.TRANSPARENT);

                resetBackgroundColorJoker(tabButtonJoker);
                resetBackgroundColorGrid(tabButton);
                resetCurrentPosition();
                joker.resetAllJokerBomb();

                if (profile.getJoker_indication() > 0) {

                    Kanji k = new Kanji();
                    String fakePhonetique;
                    String fakeSens;
                    double i;

                    while (k.isNull()) {  //Dangereux
                        i = Math.random() * (double) mainPlateau.getListKanji().size();
                        k = mainPlateau.getListKanji().get((int) i);
                    }


                    fakePhonetique = k.getPhonetique();
                    while (fakePhonetique.compareTo(k.getPhonetique()) == 0) {
                        i = Math.random() * (double) mainPlateau.getListAllPhonetique().size();
                        fakePhonetique = mainPlateau.getListAllPhonetique().get((int) i);
                    }

                    fakeSens = k.getSens();
                    while (fakeSens.compareTo(k.getSens()) == 0) {
                        i = Math.random() * (double) mainPlateau.getListAllSens().size();
                        fakeSens = mainPlateau.getListAllSens().get((int) i);
                    }

                    if (modeSelected == 1)
                        joker.printIndication(k, fakePhonetique, modeSelected);
                    else
                        joker.printIndication(k, fakeSens, modeSelected);

                    profile.setJoker_indication(-1);
                    tabButtonNbJoker[2].setText(Integer.toString(profile.getJoker_indication()));
                } else {
                    textViewInfo.setText("Joker Indication non disponible");
                    tabButtonJoker[2].startAnimation(anim);
                }
            }


        });

        Button buttonColumnBomb = (Button) findViewById(R.id.buttonMix);
        tabButtonJoker[3] = buttonColumnBomb;
        buttonColumnBomb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (isViewPhonSelected)
                    currentViewPhon.setBackgroundColor(Color.TRANSPARENT);

                if (isViewSensSelected)
                    currentViewSens.setBackgroundColor(Color.TRANSPARENT);

                resetBackgroundColorJoker(tabButtonJoker);
                resetBackgroundColorGrid(tabButton);
                resetCurrentPosition();

                if (joker.isJokerColumnBomb()) {
                    joker.resetAllJokerBomb();
                } else {
                    joker.resetAllJokerBomb();
                    if (profile.getJoker_colbomb() > 0) {
                        tabButtonJoker[3].setAlpha(ALPHA_SELECTED);
                        joker.setJokerColumnBomb(true);

                    } else {
                        tabButtonJoker[3].startAnimation(anim);
                        textViewInfo.setText("Joker Colonne Bomb non disponible");
                    }
                }
            }


        });


        Button buttonLineBomb = (Button) findViewById(R.id.buttonBomb);
        tabButtonJoker[4] = buttonLineBomb;
        buttonLineBomb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (isViewPhonSelected)
                    currentViewPhon.setBackgroundColor(Color.TRANSPARENT);

                if (isViewSensSelected)
                    currentViewSens.setBackgroundColor(Color.TRANSPARENT);

                resetBackgroundColorJoker(tabButtonJoker);
                resetBackgroundColorGrid(tabButton);
                resetCurrentPosition();

                if (joker.isJokerLineBomb()) {
                    joker.resetAllJokerBomb();
                } else {
                    joker.resetAllJokerBomb();

                    if (profile.getJoker_linebomb() > 0) {
                        tabButtonJoker[4].setAlpha(ALPHA_SELECTED);
                        joker.setJokerLineBomb(true);

                    } else {
                        tabButtonJoker[4].startAnimation(anim);
                        textViewInfo.setText("Joker Bomb ligne non disponible");
                    }
                }
            }

        });

    }

    protected void endGame (Context c, final Context className, final Class cls) {
        Dialog dialog = new Dialog(className);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox);


        time = SystemClock.elapsedRealtime() - chrono.getBase();
        second = (double) ((time) / 1000) - 1;

        second++;
        chrono.stop();
        classScore.computeScoreFinal(second);
        dialog.setCancelable(false);


        final Profile p = new Profile(c);

        final int scoreFinal = classScore.getScoreFinal();

        p.setExperience(scoreFinal);
        p.setBestScore(scoreFinal);

        TextView t  = (TextView) dialog.findViewById(R.id.ScoreAffiche);
        t.setText(Integer.toString(scoreFinal));

        ImageView s1 = (ImageView) dialog.findViewById(R.id.Star1);
        ImageView s2 = (ImageView) dialog.findViewById(R.id.Star2);
        ImageView s3 = (ImageView) dialog.findViewById(R.id.Star3);

        if (gameLost){
            s1.setVisibility(View.GONE);
            s2.setVisibility(View.GONE);
            s3.setVisibility(View.GONE);

        } else {
            if (scoreFinal > 400){

            }

            if (scoreFinal > 200) {
                s3.setVisibility(View.GONE);
            } else if (scoreFinal > 100) {
                s2.setVisibility(View.GONE);
                s3.setVisibility(View.GONE);
            } else {
                s1.setVisibility(View.GONE);
                s2.setVisibility(View.GONE);
                s3.setVisibility(View.GONE);
            }
        }
        ProgressBar pb = (ProgressBar) dialog.findViewById(R.id.PBar);

        TextView textViewProgress = (TextView) dialog.findViewById(R.id.Pgression);
        TextView textViewLevel = (TextView) dialog.findViewById(R.id.valuelvl);
        //!Mettre à jour la barre d'expérience
        pb.setMax(p.experienceToReach());
        if (p.getLevel()== 1){
            pb.setProgress(p.getExperience());
        }
        else{
            pb.setProgress(p.getExperience()- p.previousExperience());
        }
        textViewProgress.setText(pb.getProgress()+"/"+pb.getMax());
        textViewLevel.setText(String.valueOf(p.getLevel()));

        final TextView textViewDropJoker = (TextView) dialog.findViewById(R.id.jokerdrop);


        if (gameLost){
            textViewDropJoker.setText("");

        } else {

            if (p.dropJoker()) {
                textViewDropJoker.setText("Vous avez gagné un " + p.getStringJokerDrop());
            }

        }


        Button bHome = (Button) dialog.findViewById(R.id.buttonHome);
        bHome.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                finish();
                Intent intent = new Intent(className, MainActivity.class);
                startActivity(intent);
            }
        });

        Button bDetail = (Button) dialog.findViewById(R.id.buttonDetail);
        bDetail.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String endGameMessage ="";
                final AlertDialog alertDialog = new AlertDialog.Builder(className).create();
                endGameMessage += "Nombre de bonnes associations : "+Integer.toString(classScore.getGoodAssociation())+"\n";
                endGameMessage += "Nombre de mauvaises associations : "+Integer.toString(classScore.getBadAssociation())+"\n";
                endGameMessage += "Score association : "+Integer.toString(classScore.getScoreActuel())+"\n";
                if (gameLost) {
                    endGameMessage += "Temps : " + convertSecondToMinute((int) second);
                    endGameMessage += "\nScore final = "+classScore.getScoreFinal()+"\n";
                }
                else {
                    endGameMessage += "Temps : " + convertSecondToMinute((int) second) + " --->  Bonus Temps : " + Integer.toString(classScore.getBonusTime()) + "\n";
                    endGameMessage += "\nScore final = "+Integer.toString(classScore.getScoreActuel())+" + "+Integer.toString(classScore.getBonusTime())+"  = "+classScore.getScoreFinal()+"\n";
                }


                alertDialog.setTitle("Détail de la partie");
                alertDialog.setMessage(endGameMessage);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });

        Button bPLayAgain = (Button) dialog.findViewById(R.id.buttonPlayAgain);
        bPLayAgain.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                finish();
                Intent intent = new Intent(className, cls);
                startActivity(intent);
            }
        });

        final Button bFacebook= (Button) dialog.findViewById(R.id.buttonFacebook);
        bFacebook.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Kanji crush")
                            .setContentDescription(
                                    "Score final :"+scoreFinal+" !!")
                            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                            .build();

                    shareDialog.show(linkContent);
                }

                while (!p.dropJoker()){}
                textViewDropJoker.setText("Grâce au partage facebook vous avez gagné un " + p.getStringJokerDrop());
                bFacebook.setEnabled(false);
            }
        });

        dialog.show();

    }

}
