package com.example.alexandrepc.kanji2;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Classe Joker
 */

/**
 * \file      Joker.java
 * \version   1.0
 * \date      29/03/2015
 * \brief     Classe gérant l'utilisation des Jokers
 *
 * \details   Cette classe contient toutes les métodes qui intéragissent avec les jokers. Elle ne contient pas d'attributs permettant de savoir le nombre de Jokers
 */




//Cette classe gère le joker qui arrete le temps pendant x secondes
public class Joker {

    private Context c; //!Context courant correspondant au context de l'activité ayant crée le joker
    private Chronometer chrono; //! Chronometre
    private boolean jokerBomb = false; //! Vrai si le joker bomb est activé, faux sinon
    private boolean jokerLineBomb = false; //! Vrai si le joker linebomb est activé, faux sinon
    private boolean jokerColumnBomb = false; //! Vrai si le joker columnbomb est activé, faux sinon
    private TextView tInfo; //! TextView info de l'activité

    private LinkedList<Integer> listCurrentKanjiGravity; //! Liste des positions des kanjis qui vont disparaitre

    /**
     * \brief      Constructeur du joker
     * \details    Initialise les attributs c (représentant le contexte), chrono
     */
    

    public Joker (Context context, Chronometer chronom, TextView t){
        c = context;
        chrono = chronom;
        tInfo = t;
        this.listCurrentKanjiGravity = new LinkedList<>();
    }


    /**
     * \brief     Retire un certain nombre de secondes au chronomètre
     * \param     temps       le temps courant
     * \return    void
     */
    public void freezeTime(int temps){

        long timeWhenStopped;
        tInfo.setText("- 10 secondes !!");
        timeWhenStopped = chrono.getBase() - SystemClock.elapsedRealtime();
        chrono.stop();
        chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped + temps*1000);
        chrono.start();

    }


    /**
     * \brief     Affiche une indication à l'écran quant à la signification ou la phonétique d'un kanji
     * \param     k       sur lequel on  donne l'indication
     * \param     fake    correspond à une mauvaise phonetique ou un mauvais sens suivant le mode
     * \param     mode    entier correspondant au mode dans lequel on se trouve (Phonetique/Kanji , Sens/Kanji, Phonetique et Sens / Kanji
     * \return    void
     */
    public void printIndication (Kanji k, String fake, int mode){
        final Animation animIndication = AnimationUtils.loadAnimation(c, R.anim.scale);
        int i = (int) (Math.random()*2);

        switch (mode) {
            case 1: //Donne la bonne phonetique et une mauvaise phonetique
                if (i == 1) {
                    tInfo.setText("Indication : " + k.getCaractere() + " se prononce " + k.getPhonetique() + " ou " + fake);
                }
                else{
                    tInfo.setText("Indication : " + k.getCaractere() + " se prononce " + fake+" ou "+k.getPhonetique());
                }
                break;
            case 2: //!Donne le bon sens et un mauvais sens
                if (i == 1){
                    tInfo.setText("Indication : " + k.getCaractere() + " signifie " + k.getSens()+" ou "+fake);
                }
                else{
                    tInfo.setText("Indication : " + k.getCaractere() + " signifie "+fake+" ou " + k.getSens());
                }
                break;
            case 3: //!Donne le bon sens ou la bonne phonétique
                if (i == 1){
                    tInfo.setText("Indication : " + k.getCaractere() + " se prononce " + k.getPhonetique());
                }
                else{
                    tInfo.setText("Indication : " + k.getCaractere() + " signifie " + k.getSens());
                }
                break;
        }
        tInfo.startAnimation(animIndication);
    }


    /**
     * \brief     Affiche une la signification d'un kanji, sa phonétique ou les 2, selon le mode choisi
     * \param     k       Kanji sur lequel on donne les informations
     * \return    void
     */
    public void printAnswer(Kanji k, int mode){
        switch (mode) {
            case 1:
                tInfo.setText("Joker utilisé : " + k.getCaractere() + " se prononce " + k.getPhonetique());
                break;
            case 2:
                tInfo.setText("Joker utilisé : " + k.getCaractere() + " signifie " + k.getSens());
                break;
            case 3:
                tInfo.setText("Joker utilisé : " + k.getCaractere() + " se prononce " + k.getPhonetique()+ " signifie " + k.getSens());
                break;
        }

    }


    /**
     * \brief     Supprime une ligne de la grille du plateau
     * \param     p          le plateau courant
     * \param     position   la position d'où on va supprimer la ligne
     * \param     modulo     correspond au nombre de colonnes dans la grille de Kanji
     * \param     tab[]      Tableau contenant les boutons de le grille
     * \return    Pas de retour
     */
    public void jokerLineBomb(Plateau p, int position, int modulo, Button tab[]){
        int ligne = -1; //!départ de la ligne
        int j = 1;

        while(ligne == -1){
            if(0<=position && position < j*modulo){
                ligne = (j-1)*modulo;
            }
            j++;
        }

        for(int k = ligne; k < ligne + modulo; k++) {
            if(p.getListKanji().get(k).isNull()) {
                //!On ne fait rien
            }
            else {
                listCurrentKanjiGravity.add(new Integer(k));
            }
            p.gravity(tab, k, modulo);
        }

    }

    /**
     * \brief     Supprime une colonne de la grille du plateau
     * \param     position   la position d'où on va supprimer la colonne
     * \param     modulo     correspond au nombre de colonnes dans la grille de Kanji
     * \param     tab[]      Tableau contenant les boutons de le grill
     * \return    Pas de retour
     */
    public void jokerColumnBomb(Plateau p, int position, int modulo, Button tab[]){
        int k = position;
        if (position >= 0 && position < 5) {

        }
        else {
            while (k - modulo >= 0) {
                k -= modulo;
            }
        }
        int i = k;

        while (i+modulo < 25){
            if (p.getListKanji().get(i).isNull()) {
                //!On ne fait rien
            } else {
                listCurrentKanjiGravity.add(i);
            }
            p.gravity(tab, i, modulo);
            i += modulo;
        }
        p.gravity(tab, i, modulo);
    }


    /**
     * \brief     Réinitialise la liste des positions des kanjis qui vont disparaitre
     * \return    void
     */
    public void resetListCurrentKanjiGravity(){
        listCurrentKanjiGravity.clear();

        if (listCurrentKanjiGravity.size() != 0){
            Log.d("erreur", "listCurrentKanjiGravity non réinitialisé");
        }
    }

    /**
     * \brief     Retourne la liste des positions des kanjis qui vont disparaitre
     * \return    Liste des positions des kanjis qui vont disparaitre
     */
    public LinkedList<Integer> getListCurrentKanjiGravity(){
        return listCurrentKanjiGravity;
    }



    /**
     * \brief     Active le joker line bomb
     * \param     b         booléen qui indique si on a activé le joker Line bomb ou pas
     * \return    void
     */
    public void setJokerLineBomb (boolean b){
        jokerLineBomb = b;
    }

    /**
     * \brief    renvoie la valeur de l'attribut jokerLineBomb
     * \return   booléen correspondant à jokerLineBomb
     */
    public boolean isJokerLineBomb() {
        return jokerLineBomb;
    }

    /**
     * \brief     Active le joker column bomb
     * \param     b         booléen qui indique si on a activé le joker Column bomb ou pas
     * \return    void
     */
    public void setJokerColumnBomb (boolean b){
        jokerColumnBomb = b;
    }

    /**
     * \brief    renvoie la valeur de l'attribut jokerColumnBomb
     * \return   booléen correspondant à jokerColumnBomb
     */

    public boolean isJokerColumnBomb() {
        return jokerColumnBomb;
    }


    /**
     * \brief     Active le joker bomb
     * \param     b         booléen qui indique si on a activé le joker Bomb ou pas
     * \return    void
     */
    public void setJokerBomb (boolean b){
        jokerBomb = b;
    }

    /**
     * \brief    renvoie la valeur de l'attribut jokerBomb
     * \return   booléen correspondant à jokerBomb
     */
    public boolean isJokerBomb() {
        return jokerBomb;
    }

    /**
     * \brief    Mets tous les booléens correspondant au joker en cours à false
     * \return   void
     */
    public void resetAllJokerBomb (){
        jokerBomb = false;
        jokerLineBomb = false;
        jokerColumnBomb = false;

    }

}

