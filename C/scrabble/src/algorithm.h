#ifndef ALGORITHM_H
#define ALGORITHM_H

#include "structures.h"
#include "letters.h"
#include "algorithm.c"

void filldico(char file[], char dico[][15], int sizeDico[400000]);
int wordExist(char letters[15],int l);
int valueOfWord(struct wordScrabble Word,struct box Grille[15][15]);
int findWordsWithLine(struct box line[15], char letters[7], struct valableSize sizes[100],int nbSizes, struct wordScrabble result[1000],int position, int x);
int searchWordByLine(struct box line[15],char letters[7], int nb, int position,struct wordScrabble Words[10000]);
void searchWord(struct box Grille[15][15],char letters[7],int n, struct wordScrabble Words[10],int scores[10]);
int hasLettersFirstWord(char w[15],char hand[7]);
int hasLetters(struct box Grille[15][15], char letters[7],struct wordScrabble word);


#endif
