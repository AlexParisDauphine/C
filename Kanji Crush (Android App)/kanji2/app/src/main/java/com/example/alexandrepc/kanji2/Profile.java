package com.example.alexandrepc.kanji2;

/**
 * Classe Profil
 */

/**
 * \file      Profile.java
 * \version   1.0
 * \date      29/03/2015
 * \brief     Classe représentant un profil
 *
 * \details   Cette classe permet de manipuler et créer un Profil
 */

import android.content.Context;
import android.content.SharedPreferences;

public class Profile {
    private int bestScore;
    private int experience;
    private int level;
    private String rank = "";
    private int joker_time;
    private int joker_bomb;
    private int joker_indication;
    private int joker_linebomb;
    private int joker_colbomb;
    private Context myContext;
    private String stringJokerDrop = "";

    private static final String MyPREFERENCES = "MyPrefs";
    private static final String Experience = "storedExperienceKey";
    private static final String BestScore = "storedBestScoreKey";


    private static final String Jtime = "storedJokerTime";
    private static final String Jbomb = "storedJokerBomb";
    private static final String Jindic = "storedJokerIndic";
    private static final String JLbomb = "storedJokerLbomb";
    private static final String JCbomb = "storedJokerCbomb";

/**
     * \brief       Constructeur avec paramètre
     * \param    context         version de l'application
     */

    public Profile(Context C){

        myContext = C;

        //! Charge la table qui stock le meilleur score de l'utilisateur ainsi que son expérience
        SharedPreferences sharedpreferences = myContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //! L'éditeur permet de mettre à jour les valeurs stockées dans la table SharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();

        if (sharedpreferences.contains(Experience)){
            experience = sharedpreferences.getInt(Experience,0);
        }
        else{
            editor.putInt(Experience, 0);
            editor.apply();
        }

        if (sharedpreferences.contains(BestScore)){
            bestScore = sharedpreferences.getInt(BestScore,0);
        }
        else{
            editor.putInt(BestScore, 0);
            editor.apply();
        }

        if (sharedpreferences.contains(Jtime)){
            joker_time = sharedpreferences.getInt(Jtime,0);
        }
        else{
            joker_time = 20;
            editor.putInt(Jtime, joker_time);
            editor.apply();
        }

        if (sharedpreferences.contains(Jbomb)){
            joker_bomb = sharedpreferences.getInt(Jbomb,0);
        }
        else{
            joker_bomb = 20;
            editor.putInt(Jbomb, joker_bomb);
            editor.apply();
        }


        if (sharedpreferences.contains(Jindic)){
            joker_indication = sharedpreferences.getInt(Jindic,0);
        }
        else{
            joker_indication = 20 ;
            editor.putInt(Jindic, joker_indication);
            editor.apply();
        }


        if (sharedpreferences.contains(JLbomb)){
            joker_linebomb = sharedpreferences.getInt(JLbomb,0);
        }
        else{
            joker_linebomb = 50;
            editor.putInt(JLbomb,joker_linebomb);
            editor.apply();
        }


        if (sharedpreferences.contains(JCbomb)){
            joker_colbomb = sharedpreferences.getInt(JCbomb,0);
        }
        else{
            joker_colbomb = 20;
            editor.putInt(JCbomb,joker_colbomb);
            editor.apply();
        }

        updateLevel(experience);
        updateRank();
    }

    /**
     * \brief       getter d'expérience
     * \details     retourne l'expérience
     * \return      int
     */

    public int getExperience(){
        return experience;
    }

    /**
     * \brief       Met a jour la valeur d'expérience
     * \details     Remplace la valeur d'expérience par la nouvelle expérience
     * \param       newExperience            entier valeur de la nouvelle expérience
     * \return      void
     */

    public void setExperience(int newExperience){
        experience += newExperience;

        SharedPreferences sharedpreferences = myContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putInt(Experience, experience);
        editor.apply();
        updateLevel(experience);
        updateRank();

    }

    /**
     * \brief       getter de niveau
     * \details     retourne le niveau
     * \return      int
     */

    public int getLevel(){
        return level;
    }

    /**
     * \brief       Met a jour le niveau
     * \details     Remplace l'ancien niveau par le niveau si on a atteint le palier
     * \param       newExperience            entier valeur de la nouvelle expérience
     * \return      void
     */

    public void updateLevel(int newExperience){
        int[] levels = new int[20];
        for (int i = 0; i < 20 ; i++) {
            if (i == 0)
                levels[i] = 15*(i)*(i)+50*(i)+400;
            else
                levels[i] = 15*(i)*(i)+50*(i)+400 + levels [i-1];
        }
        int newTMPLevel = 1;
        int j = 0;
        while (j<levels.length && newExperience >= levels[j]){
            j++;
            newTMPLevel ++;
        }
        if(newTMPLevel>level){
            level = newTMPLevel;
        }
    }

    /**
     * \brief       expérience à atteindre
     * \details     Valeur de l'expérience à atteindre pour monter de niveau en fonction du niveau actuel
     * \return      int
     */

    public int experienceToReach(){
        return 15*(level-1)*(level-1) + (level-1)*50 + 400;
    }

    /**
     * \brief       expérience précédente
     * \details     Valeur de l'expérience qu'il a fallu atteindre pour passer le niveau actuel
     * \return      int
     */

    public int previousExperience(){
        if (level <= 1){
            return 0;
        }
        else{
            int[] levels = new int[20];
            for (int i = 0; i < 20 ; i++) {
                if (i == 0)
                    levels[i] = 15*(i)*(i)+50*(i)+400;
                else
                    levels[i] = 15*(i)*(i)+50*(i)+400 + levels [i-1];
            }

            return levels [level-2];

        }

    }

    /**
     * \brief       Met a jour le rang
     * \details     Remplace l'ancien rang par un nouveau si on atteint le palier
     * \return      void
     */

    public void updateRank(){
        if(level < 4){
            rank = "Apprenti";
        } else if (level < 8){
            rank = "Initié";
        } else if (level < 11){
            rank = "Novice";
        } else if (level < 15){
            rank = "Disciple";
        } else if (level < 20){
            rank = "Maître";
        } else {
            rank = "Grand maître";
        }

    }

    /**
     * \brief       getter de rang
     * \details     retourne le rang
     * \return      string
     */

    public String getRank (){
        return rank;
    }

    /**
     * \brief       getter de meilleur score
     * \details     retourne le meilleur score
     * \return      int
     */

    public int getBestScore(){
        return bestScore;
    }

    /**
     * \brief       Met a jour la valeur du meilleur score
     * \details     Remplace la valeur du meilleur score si le nouveau score est supérieur à celui-ci
     * \param       newScore           valeur du nouveau score réalisé
     * \return      void
     */

    public void setBestScore(int newScore){
        if (newScore>bestScore) {
            bestScore = newScore;
            // load the table which stores user bestexperience and bestscore
            SharedPreferences sharedpreferences = myContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            //editor allows to update values stored in the table
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putInt(BestScore, bestScore);
            editor.apply();
        }
    }



    /* JOKERS */

    public int getJoker_time(){
        return joker_time;
    }
    public int getJoker_bomb(){
        return joker_bomb;
    }
    public int getJoker_indication(){
        return joker_indication;
    }
    public int getJoker_linebomb(){
        return joker_linebomb;
    }
    public int getJoker_colbomb(){
        return joker_colbomb;
    }

    public void setJoker_time(int value){
        joker_time+=value;
        // load the table which stores user bestexperience and bestscore
        SharedPreferences sharedpreferences = myContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //editor allows to update values stored in the table
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putInt(Jtime, joker_time);
        editor.apply();
    }
    public void setJoker_bomb(int value){
        joker_bomb+=value;
        // load the table which stores user bestexperience and bestscore
        SharedPreferences sharedpreferences = myContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //editor allows to update values stored in the table
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putInt(Jbomb, joker_bomb);
        editor.apply();
    }
    public void setJoker_indication(int value){
        joker_indication+=value;
        // load the table which stores user bestexperience and bestscore
        SharedPreferences sharedpreferences = myContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //editor allows to update values stored in the table
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putInt(Jindic, joker_indication);
        editor.apply();
    }
    public void setJoker_linebomb(int value){
        joker_linebomb+=value;
        // load the table which stores user bestexperience and bestscore
        SharedPreferences sharedpreferences = myContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //editor allows to update values stored in the table
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putInt(JLbomb, joker_linebomb);
        editor.apply();
    }
    public void setJoker_colbomb(int value){
        joker_colbomb+=value;
        // load the table which stores user bestexperience and bestscore
        SharedPreferences sharedpreferences = myContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //editor allows to update values stored in the table
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putInt(JCbomb, joker_colbomb);
        editor.apply();
    }

    /**
     * \brief       Fonction gagner joker
     * \details     A la fin d'une partie le joueur a une chance de gagner chaque type de joker ou de n'en gagner aucun
     * \return      booléen
     */

    public boolean dropJoker(){
        double rand=Math.random()*100;
        if (rand < 3){
            setJoker_colbomb(1);
            stringJokerDrop = "Joker bombe colonne";
        }
        else if (rand < 13){
            setJoker_time(1);
            stringJokerDrop = "Joker temps";
        }
        else if (rand < 28){
            setJoker_indication(1);
            stringJokerDrop = "Joker indication";
        }
        else if (rand < 36){
            setJoker_bomb(1);
            stringJokerDrop = "Joker bombe";
        }
        else if (rand < 39){
            setJoker_linebomb(1);
            stringJokerDrop = "Joker bombe ligne";
        }
        else {
            return false;
        }
        return true;
    }


    public String getStringJokerDrop (){
        return stringJokerDrop;
    }


}
