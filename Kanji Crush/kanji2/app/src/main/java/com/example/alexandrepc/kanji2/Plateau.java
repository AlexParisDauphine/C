package com.example.alexandrepc.kanji2;

import android.util.Log;
import android.widget.Button;

import java.util.Collections;
import java.util.LinkedList;




/**
 * Classe Plateau
 */

/**
 * \file      Plateau.java
 * \version   1.0
 * \date      29/03/2015
 * \brief     Classe représentant un Plateau
 *
 * \details   Cette classe représente un Plateau c'est à dire essentiellement une liste de Kanji représentant la grille de Kanji,
 *            une liste de sens, et une liste de phonetique.
 */


class Plateau {

    private final LinkedList<Kanji> listKanjiConstante; //!Pas modifiée après qu'elle soit remplie
    private LinkedList<Kanji> listKanjiRestant; //! Vidée au fur et à mesure qu'on utilise les kanji.
    private LinkedList<Kanji> listKanji; //!Correspond à la grille (truc affiché).

    private LinkedList<String> listCarac; //!Pour tout i, listCarac(i) = listKanji(i).getCaractere()
    private LinkedList<String> listAllPhonetique;
    private LinkedList<String> listAllSens;

    private LinkedList<Integer> listCurrentKanjiTransfered; //! Liste contenant les positions des kanji ajoutés lors d'une mauvaise association

    private boolean isLost; //!Booléen attestant de la défaite du joueur (grille pleine)

    /**
     * \brief       Constructeur de Plateau
     * \details    Reçoit une liste contenant tous les kanjis de la base, ainsi qu'un nombre d'élément correspondant à la taille de la grille de Kanji.
     *             Elle utilise plusieurs méthodes pour remplir aléatoirement la grille de Kanji, remplir la liste de sens (resp. phonétique) avec tous les sens (resp. phonétque) de tous les kanjis 
     */


    public Plateau(LinkedList<Kanji> listAllKanji, int nbElements) {
        this.listKanjiConstante = new LinkedList<>(listAllKanji);
        this.listKanjiRestant = new LinkedList<>(listAllKanji);
        this.listCurrentKanjiTransfered = new LinkedList<>();

        reduceList(nbElements);//!Liste de kanjis aléatoire pour remplir à 1/3 plus les kanjis vides pour remplir
        initListCarac();

        initListAllPhonetique();
        initListAllSens();

        isLost = false;
    }



 

    /**
     * \brief     Rempli la listKanji et vide la listKanjiRestant
     * \param     nbElements        entier correspondant à la taille de la grille
     * \return    void
     */
    public void reduceList(int nbElements) {
        //!Prend la liste totale des kanji et en sort une liste de hauteur*largeur/3 selectionné au hasard plus des kanjis vide pour remplir la grille (taille nbElements)
        listKanji = new LinkedList<>();
        int n = nbElements / 2;
        double i;
        int m = listKanjiConstante.size()/2;

        int min = minVal(n,m);

        while (min > 0) {
            i = Math.random() * (double) listKanjiRestant.size();
            listKanji.add(listKanjiRestant.get((int) i));
            listKanjiRestant.remove((int) i);
            min--;
        }

        int currentLength = listKanji.size();

        while (currentLength < nbElements ) {
            listKanji.addFirst(new Kanji());
            currentLength++;
        }
    }

    /**
     * \brief     Initialise la liste des caractères
     * \return    void
     */
    public void initListCarac(){
        listCarac = new  LinkedList<>();

        for (int i = 0; i < listKanji.size(); i++) {
            listCarac.add(listKanji.get(i).getCaractere());
        }
    }


    /**
     * \brief     Initialise la liste des phonétiques
     * \return    void
     */
    public void initListAllPhonetique(){
        listAllPhonetique = new LinkedList<>();
        int n = listKanjiConstante.size();

        for (int i = 0; i < n; i++){
            listAllPhonetique.add(listKanjiConstante.get(i).getPhonetique());
        }

        //!Trie
        Collections.sort(listAllPhonetique);


    }

    /**
     * \brief     Initialise la liste des sens
     * \return    void
     */
    public void initListAllSens(){
        listAllSens = new LinkedList<>();
        int n = listKanjiConstante.size();
        LinkedList<Kanji> listTemp = new LinkedList<>(listKanjiConstante);
        double i;

        while (n > 0) {
            i = Math.random() * (double) listTemp.size();
            listAllSens.add(listTemp.get((int) i).getSens());
            listTemp.remove((int) i);
            n--;
        }
    }


    /**
     * \brief     Vérifie si le plateau est vide
     * \return    True si le Plateau est vide
     */
    public boolean isEmpty() {
        int i = 0;
        while(i<listKanji.size() && listKanji.get(i).isNull()){
            i++;
        }
        return (i== listKanji.size());
    }

    /**
     * \brief     Vérifie si le plateau est rempli
     * \return    True si le Plateau est rempli
     */
    public boolean isFull() {
        return (isLost);
    }

    /**
     * \brief     Trouve un kanji dans la kanjilist à partir de son caractère
     * \param     caractere       string correspondant au caractère
     * \return    Un Kanji qui a pour caractère "caractere"
     */
    public Kanji findKanji (String caractere) {
        int i = 0;
        int j = 0;
        int n = listKanji.size();

        while (i < n) {
            if (caractere.compareTo(listKanji.get(i).getCaractere()) == 0) {
                j = i;
            }
            i++;
        }

        if (caractere.compareTo(listKanji.get(j).getCaractere()) == 0)
            return new Kanji(listKanji.get(j));
        else {
            Log.d("Erreur", caractere + " non trouvé");
            return new Kanji();
        }


    }
    

    /**
     * \brief     Quand la liste de Kanji restant à mettre dans la grille est vide, on appelle cette méthoe qui la re-rempli
     * \return    void
     */

    public void resetListKanjiRestante(){
        for(int i = 0;i<listKanjiConstante.size();i++) {
            if (!containsKanji(listKanji, listKanjiConstante.get(i))) {
                listKanjiRestant.add(listKanjiConstante.get(i));
            }
        }
        if(listKanjiRestant.isEmpty()){ //!Ce cas ne se produit que si la bdd de kanji est plus petite que la grille
            listKanjiRestant.addAll(listKanjiConstante);
        }
    }

    /**
     * \brief     Transfert "nbKanji" kanji de la listKanji vers la listCurrentKanjiTransfered
     * \detail    Cette métode sert quand on fait une mauvaise association, et permet de rajouter un nombre de Kanji égal à
     *            nbKanji dans la grille. (2 par défaut)
     * \param     nbKanji        Entier correspondant au nombre de Kanji à transférer
     * \return    Pas de retour
     */
    public void transfertKanji (int nbKanji){
        int n = listKanji.size() - 1;
        double i;
        int j = 0;
        Kanji kanjiTransfered;

        while (nbKanji > 0){
            if(listKanjiRestant.isEmpty()){
                resetListKanjiRestante();
            }


            while(!listKanji.get(n-j).isNull()) {
                if(j>=n){
                    isLost = true;
                    return;
                }
                j++;
            }

            i = Math.random() * (double) listKanjiRestant.size();

            Integer current = tetrisKanji(5);
            kanjiTransfered = new Kanji(listKanjiRestant.get((int) i));
            listKanjiRestant.remove((int)i);

            while (kanjiTransfered.isNull()){
                if(listKanjiRestant.isEmpty()){
                    resetListKanjiRestante();
                }
                i = Math.random() * (double) listKanjiRestant.size();
                current = tetrisKanji(5);
                kanjiTransfered = new Kanji(listKanjiRestant.get((int) i));
                listKanjiRestant.remove((int)i);
            }

            listKanji.get(current).copyValue(kanjiTransfered);
            listCurrentKanjiTransfered.add(current);

            nbKanji--;
        }

    }


    /**
     * \brief     Créer une liste où sont placés toutes les cases vides puis appelle tetrisPositionKanji
     * \param     modulo      entier correspondant au nombre de colonnes  de la grille (5 par défaut)
     * \return    Une position aléatoire parmi les positions où le kanji est nul et où il y a un kanji en dessous (ou sur la dernière ligne)
     */
    public Integer tetrisKanji(int modulo){ //!Prends toute les cases nulles
        LinkedList<Integer> listTemp = new LinkedList<>();
        int n = listKanji.size() ;
        int j=0;
        while(j<n){
            if(listKanji.get(j).isNull()) {
                listTemp.add(j);
            }
            j++;
        }
        return (tetrisPositionKanji(listTemp,modulo));
    }

    /**
     * \brief     Pour chaque élément de la listTemp vérifie si il à un élement "en dessous" ( un élément modulo plus loin dans la liste)
     * \param     listTemp    liste contenant tous les kanjis vides de la grille
     * \param     modulo      entier correspondant au nombre de colonnes  de la grille (5 par défaut)
     * \detail    Cette métode sert à choisir aléatoirement l'endroit où l'on rajoute le kanji lors d'une mauvaise association
     * \return    Une position aléatoire parmi de la listTemp où il y a un kanji en dessous
     */
    public Integer tetrisPositionKanji(LinkedList<Integer> listTemp,int modulo){
        LinkedList<Integer> listFinal = new LinkedList<>();
        for (int j = 0; j < listTemp.size();j++) {
            Integer current = listTemp.get(j);

            if (current > listKanji.size()- modulo -1) {
                listFinal.add(current);
            }
            else{
                if(!listKanji.get(current+modulo).isNull()){
                    listFinal.add(current);
                }
            }
        }

        double i = Math.random() * (double) listFinal.size();
        return listFinal.get((int) i);
    }


    /**
     * \brief     Vérifie si le Kanji kj appartient à la liste list
     * \param     list       une liste de Kanji
     * \param     kj         Kanji à chercher
     * \return    Vrai si kj est dans list
     */
    public static boolean containsKanji(LinkedList<Kanji> list, Kanji kj){
        int i =0;
        while(i<list.size() && list.get(i).isEqual(kj)){
            i++;
        }
        return i==list.size();
    }

    /**
     * \brief     Retourne l'entier le plus petit entre a et b
     * \param     a       une entier
     * \param     b       une entier
     * \return    Entier le plus petit
     */
    public static int minVal (int a, int b){
        if (a<b)
            return a;
        else
            return b;
    }


    /**
     * \brief     Supprime le kanji à la position "position" et tous les kanji modulo en arrière dans la liste
     * \param     tab[]       tableau contenant les boutons de la grille
     * \param     position    postion du kanji qu'il faut supprimer
     * \param     modulo      nombre de ligne de la grille
     * \return    void
     */
    public void gravity(Button tab[],int position, int modulo) {
        Kanji tmp;
        if (position < modulo) {
            listKanji.get(position).setNull();
            for (int i = 0; i < tab.length; i++){
                tab[i].setText(listKanji.get(i).getCaractere());
            }
        } else {
            tmp = findKanji(tab[position - modulo].getText().toString());
            listKanji.get(position).copyValue(tmp);
            gravity(tab,position-modulo,modulo);
        }
    }


    /**
     * \brief     Appelle transfertKanji puis update la kanjilist
     * \detail    cette métode ajoute un nombre de kanji nbKanji dans le cas d'une mauvaise association
     * \return    Pas de retour
     */
    public void addRandomKanji (Button tab[], int nbKanji){
        transfertKanji(nbKanji);

        for (int i = 0; i < tab.length; i++){
            tab[i].setText(listKanji.get(i).getCaractere());
        }

    }

    public LinkedList<Integer> getListCurrentKanjiTransfered(){
        return listCurrentKanjiTransfered;
    }

    public void resetListCurrentKanjiTransfered(){
        listCurrentKanjiTransfered.clear();

        if (listCurrentKanjiTransfered.size() != 0){
            Log.d("erreur", "listCurrentKanjiTransfered non réinitialisé");
        }
    }

    public LinkedList<Kanji> getListKanji(){
        return listKanji;
    }

    public LinkedList<String> getListCarac(){
        return listCarac;
    }

    public LinkedList<String> getListAllSens(){
        return listAllSens;
    }

    public LinkedList<String> getListAllPhonetique(){
        return listAllPhonetique;
    }



}
