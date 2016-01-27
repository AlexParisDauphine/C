#include <stdio.h>
#include <ncurses.h>

#include "structures.h"
#include "grille.h"
#include "letters.h"
#include "algorithm.h"

int main(int argc, char ** argv)
{	
	initscr();			/* Start curses mode 		  */
	int manual=0;
	int random=1;
	if(argc > 2 && strcmp("-f",argv[1])==0){
		createAlphabetByFile(argv[2]);
		random=0;
		if(argc > 3 && strcmp("-m",argv[3]) ==0)
			manual=1;
	}
	if(argc > 1 && strcmp("-m", argv[1]) == 0){
		manual=1;
		if(argc> 3 && strcmp("-f",argv[2]) == 0){
			if(createAlphabetByFile(argv[3])){
				random=0;
				printf("ok");
			}
		}
	}
	if(random){	
		srand(time(NULL));
		createAlphabetRandom();
	}
	filldico("/net/ens/renault/dicoScrabble.txt",dictionary,sizeWords);
	createLettersValue();
	struct box Grille[15][15];
	createGrille(Grille);
	struct wordScrabble Words[10];
	int scores[10];
	struct player p;
	
	//tirage des lettres
	for(int j=0;j<7;j++){
		p.player_letters[j] = letters[letterUsed];
		letterUsed++;
	}
	p.score=0;
	p.ia=!manual;
	
	//démarrage du jeu
	int letterDispo=1;
	int firstTour=1;
	displayGrille(Grille);
	while(letterDispo){
		printw("____________________________________________\n");
		printw("Score: %d\n",p.score);
		printw("Lettres disponibles: ");
		for(int i=0;i<7;i++){
			printw("%c%c",p.player_letters[i],(i!=6 ? ',':'.'));
		}
		printw("\n");
		printw("____________________________________________\n");
		printw("Appuyez sur une touche pour continuer");
		getch();
		//joueur IA
		if(p.ia){
			if(firstTour){
				findBestFirstWord(Grille,p.player_letters,Words,1,scores);
				letterDispo = takeLetter(&p,Grille,Words[0]);
				addWordGrille(Grille,Words[0].letters,Words[0].l,Words[0].position,Words[0].x,Words[0].y);
				p.score+=scores[0];
				firstTour=0;
			}
			else{
				searchWord(Grille,p.player_letters,1,Words,scores);
				letterDispo = takeLetter(&p,Grille,Words[0]);
				addWordGrille(Grille,Words[0].letters,Words[0].l,Words[0].position,Words[0].x,Words[0].y);
				p.score+=scores[0];
			}
		}
		//joueur non IA
		else{
			if(firstTour){
				firstTour=0;
				findBestFirstWord(Grille,p.player_letters,Words,10,scores);
				for(int i=0;i<10;i++){
					printw("%d: Mot: ",i);
					for(int j=0;j<Words[i].l;j++){
						printw("%c",Words[i].letters[j]);
					}
					char * pos;
					if(Words[i].position)
						pos="HORIZONTAL";
					else
						pos="VERTICAL";
					printw(" x:%d, y:%d, position:%s,points:%d\n",Words[i].x,Words[i].y,pos,scores[i]);
				}
				int ok=0;
				while(!ok)
				{
					printw("Choisissez le mot à placer de 0 a 9 ou tapez u pour poser votre propre mot:\n");
					char c;
					noecho();
					c = getch();
					echo();
					if(c >='0' && c <= '9'){
						int l = c -'0';
						letterDispo = takeLetter(&p,Grille,Words[l]);
						addWordGrille(Grille,Words[l].letters,Words[l].l,Words[l].position,Words[l].x,Words[l].y);
						p.score+=scores[l];
						ok=1;
					}
					else if(c == 'u'){
						printw("Quel mot souhaitez-vous placez?:\n");
						char w[15];
						getstr(w);
						char y;
						printw("A quel ordonnee? (de 0 à 15, le point d'origine est en haut gauche:");
						printw("\n");
						y=getch() - '0';
						if(wordExist(w,strlen(w)) && hasLettersFirstWord(w,p.player_letters)){
							addWordGrille(Grille,w,strlen(w),1,7,y);
							ok=1;
						}
						else{
							printw("Mot non existant ou lettres insuffisantes\n");
						}
					}
				}		
			}
			else{
				searchWord(Grille,p.player_letters,10,Words,scores);
				for(int i=0;i<10;i++){
					printw("%d: Mot: ",i);
					for(int j=0;j<Words[i].l;j++){
						printw("%c",Words[i].letters[j]);
					}
					char * pos;
					if(Words[i].position)
						pos="HORIZONTAL";
					else
						pos="VERTICAL";
					printw(" x:%d, y:%d, position:%s,points:%d\n",Words[i].x,Words[i].y,pos,scores[i]);
				}
				int ok=0;
				while(!ok)
				{
					printw("Choisissez le mot à placer (de 0 à 9) ou tapez u pour placer votre propre mot: \n");
					char c;
					noecho();
					c = getch();
					echo();
					if(c >='0' && c <= '9'){
						int l = c -'0';
						letterDispo = takeLetter(&p,Grille,Words[l]);
						addWordGrille(Grille,Words[l].letters,Words[l].l,Words[l].position,Words[l].x,Words[l].y);
						p.score+=scores[l];
						ok=1;
					}
					else if(c == 'u'){
						printw("Quel mot souhaitez-vous placez?:\n");
						char w[15];
						getstr(w);
						char x,y;
						printw("A quel abscisse? (de 0 à 15, le point d'origine est en haut gauche:");
						printw("\n");
						x=getch() - '0';
						printw("\nA quel ordonnée? (de 0 à 15, le point d'origine est en haut gauche:");
						printw("\n");
						y=getch() - '0';
						printw("\nDans quel sens? (horizontal:1,vertical:0):\n");
						int sens;
						sens = getch() - '0';
						struct wordScrabble tmp;
						for(int i=0;i<strlen(w);i++){
							tmp.letters[i] = toupper(w[i]);
						}
						tmp.x=x;
						tmp.y=y;
						tmp.position=sens;
						tmp.l = strlen(w);
						if(!hasLetters(Grille,p.player_letters,tmp)){
							printw("Lettres insuffisantes, mot rattaché à aucun autre ou pose sur une lettre déjà présente\n");
						}
						else{
							int v = valueOfWord(tmp,Grille);
							if(v == -1){
								printw("Mot invalide sur la grille\n");
							}
							else
							{
								letterDispo = takeLetter(&p,Grille,tmp);
								addWordGrille(Grille,tmp.letters,tmp.l,tmp.position,tmp.x,tmp.y);
								p.score+=v;
								ok=1;
							}
						}
					}
				}
			}
		}
		move(0,0);
		displayGrille(Grille);
		
	}
	if(!letterDispo){
		printw("Plus de lettres dans le jeu, score final: %d",p.score);
	}
	endwin();			/* End curses mode		  */
	return 0;
}
