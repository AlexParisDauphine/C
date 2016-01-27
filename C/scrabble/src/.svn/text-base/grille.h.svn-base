#ifndef GRILLE_H
#define GRILLE_H

#include "structures.h"
#include "letters.h"
#include "algorithm.h"
#include "grille.c"

void createGrille(struct box Grille[15][15]);
void displayGrille(struct box Grille[15][15]);
int addWordGrille(struct box Grille[15][15], char motaAjouter[15],int longueurMot, int position, int positionInitialColonne, int positionInitialLigne);
int searchFirstWord(char hand[7], char motsPotentiel[20000][15],int sizeWord[20000]);
void findBestFirstWord(struct box Grille[15][15], char hand[7],struct wordScrabble Word[10], int n,int scores[10]);

#endif
