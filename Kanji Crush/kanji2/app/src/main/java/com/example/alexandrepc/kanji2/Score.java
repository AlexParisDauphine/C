package com.example.alexandrepc.kanji2;
/**
 * Classe Score
 */

/**
 * \file      Score.java
 * \version   1.0
 * \date      29/03/2015
 * \brief     Classe qui calcule le score lors d'une partie
 *
 * \details   Cette classe permet d'effectuer les calculs concernant les scores en prenant en compte le temps et les associations
 */

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


class Score {


    private TextView tScore;
    private TextView tInfo;
    private int scoreActuel;
    private int scoreFinal;
    private int bonusTime;
    private int rowAssociation = 0;
    private int goodAssociation = 0;
    private int badAssociation = 0;
    private Context c;
    private Activity a;
    private static final int nbAssocToCombo = 3;

 /**
     * \brief       Constructeur avec paramètres
     * \param    activity           activité
     * \param    context            version de l'application
     * \param    textview           reference xml
     * \param    textview           reference xml
     */
    public Score(Activity activity, Context context, TextView t, TextView info) {
        this.tScore = t;
        this.tInfo = info;
        this.scoreActuel = 0;
        this.scoreFinal = 0;
        this.c = context;
        this.a = activity;
    }
    
    /**
     * \brief       Fonction temps
     * \details     Fonction qui calcule le bonus en fonction du temps
     * \param       temps       valeur du chronomètre en secondes
     * \return      double
     */

    public double functionTime(double temps) {
        return 20 + (10 * Math.exp(1 / (0.005 * (temps + 45) + 0.1)));
    }

   /**
     * \brief       Fonction  bonne association
     * \details     Fonction qui rajoute des points, qui incrémente le nombre de combos et qui affiche à l'écran une animation sous forme de texte
     * \param       kanji        kanji qu'on vient d'associer a son sens et/ou phonétique
     * \param       mode         entier correspondant au mode de jeu
     * \return      void
     */


    public void goodAssociation(Kanji k, int mode) {
        rowAssociation++;
        goodAssociation++;
        scoreActuel += 10;

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(8);
        tScore.startAnimation(anim);


        switch (mode) {
            case 1:
                //Toast.makeText(c, "Bonne association : " + k.getCaractere() + " se prononce " + k.getPhonetique(), Toast.LENGTH_SHORT).show();
                tInfo.setText("Bonne association : " + k.getCaractere() + " se prononce " + k.getPhonetique());
                break;
            case 2:
                //Toast.makeText(c, "Bonne association : " + k.getCaractere() + " signifie " + k.getSens(), Toast.LENGTH_SHORT).show();
                tInfo.setText("Bonne association : " + k.getCaractere() + " signifie " + k.getSens());
                break;
            case 3:
                //Toast.makeText(c, "Bonne association : " + k.getCaractere() + " se prononce " + k.getPhonetique() + " et signifie " + k.getSens(), Toast.LENGTH_SHORT).show();
                tInfo.setText("Bonne association : " + k.getCaractere() + " se prononce " + k.getPhonetique() + " et signifie " + k.getSens());
                break;

        }
    }


   /**
     * \brief       Fonction  mauvaise association
     * \details     Fonction qui retire des points, qui remet à zéro le nombre de combos et qui affiche à l'écran une animation sous forme de texte
     * \return      void
     */


    public void badAssociation() {
        //Toast.makeText(c, "Mauvaise association !", Toast.LENGTH_SHORT).show();
        badAssociation++;
        tInfo.setText("Mauvaise association !");
        rowAssociation = 0;
        scoreActuel -= 3;
    }


 /**
     * \brief       Fonction  calculer score final
     * \details     Fonction qui calcule le score final en appelant fonction temps et l'additionne en cas de victoire au score d'associations. Si le score est inférieur à 0, on affiche un score nul par défaut
     * \param       temps         double correspondant au temps
     * \return      void
     */

    public void computeScoreFinal(double temps) {
        if (scoreActuel <= 0) {
            scoreFinal = 0;

        } else if (scoreActuel > 0) {
            bonusTime = (int)functionTime(temps);
            scoreFinal = (int) (scoreActuel + bonusTime);

        }
    }

 /**
     * \brief       Fonction  affichage du score actuel
     * \return      void
     */


    public void printScoreActuel() {
        tScore.setText(Integer.toString(scoreActuel));
    }

 /**
     * \brief       Fonction  combo
     * \details     Fonction qui vérifie si on est en combo et rajoute un bonus en fonction du niveau de combo et en plus affiche à l'écran un message adapté
     * \return      void
     */


    public void combo(){
        if (rowAssociation == 3 || rowAssociation == 5 || rowAssociation == 8) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.toast_combo, (ViewGroup) a.findViewById(R.id.custom_toast_layout_id));
            ImageView image = (ImageView) layout.findViewById(R.id.image);

            switch (rowAssociation) {
                case (nbAssocToCombo):
                    scoreActuel += 5;
                    image.setImageResource(R.drawable.combonice);
                    break;
                case (nbAssocToCombo+2):
                    scoreActuel += 40;
                    image.setImageResource(R.drawable.combogreat);
                    break;
                case (nbAssocToCombo+5):
                    scoreActuel += 100;
                    image.setImageResource(R.drawable.comboamazing);
                    break;
            }
            Toast toast = new Toast(c.getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }



    public int getGoodAssociation(){return goodAssociation;}
    public int getBadAssociation(){return badAssociation;}
    public int getScoreActuel(){return scoreActuel;}
    public int getBonusTime(){return bonusTime;}
    public int getScoreFinal(){return scoreFinal;}


}
