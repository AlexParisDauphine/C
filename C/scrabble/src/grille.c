#include <ncurses.h>
void createGrille(struct box Grille[15][15]){
  struct token tmpTok;
  tmpTok.letter = 'a';
  tmpTok.value=0;
  struct box tmpBox;
  tmpBox.bonus=0;
  tmpBox.tok = tmpTok;
  for(int i=0; i<15;i++){
    for(int j=0; j<15; j++){
      Grille[i][j] = tmpBox;
    }
  }
  
  //création des bonus
  
  //RED
  for(int i=0;i<15;i+=7){
	  for(int j=0;j<15;j+=7){
		  Grille[i][j].bonus=2;
	  }
  }
  
  //MAG
  for(int i=1;i<5;i++){
	  Grille[i][i].bonus=1;
	  Grille[14-i][14-i].bonus=1;
	  Grille[i][14-i].bonus=1;
	  Grille[14-i][i].bonus=1;
  }
  Grille[7][7].bonus=1;
  
  //CYN
  for(int i=0;i<nbCyans;i++){
	  Grille[cynBoxes[i][0]][cynBoxes[i][1]].bonus=3;
  }
  
  //BLUE
  for(int i=0;i<nbBlues;i++){
	  Grille[blueBoxes[i][0]][blueBoxes[i][1]].bonus=4;
  }
  
  
		
}

void displayGrille(struct box Grille[15][15]){
	start_color();
	init_pair(1, COLOR_WHITE, COLOR_WHITE);
	init_pair(2, COLOR_BLACK, COLOR_WHITE);
	init_pair(3, COLOR_BLACK, COLOR_RED);
	init_pair(4, COLOR_BLACK, COLOR_MAGENTA);
	init_pair(5, COLOR_BLACK, COLOR_BLUE);
	init_pair(6, COLOR_BLACK, COLOR_CYAN);
	bkgd(COLOR_PAIR(1));
	attron(COLOR_PAIR(2));
	printw("____________________________________________________________\n");
	for(int i=0; i<15; i++){
		for(int j=0; j<15; j++){
			char tmp = '.';
			if(Grille[i][j].tok.value !=0 && (toupper(Grille[i][j].tok.letter) >= 'A' && toupper(Grille[i][j].tok.letter) <='Z'))
				tmp = Grille[i][j].tok.letter;
				attron(COLOR_PAIR(2));
				printw("|");
			switch(Grille[i][j].bonus)
			{
				case 0:
					attron(COLOR_PAIR(2));
					break;
				case 1:
					attron(COLOR_PAIR(4));
					break;
				case 2:
					attron(COLOR_PAIR(3));
					break;
				case 3:
					attron(COLOR_PAIR(6));
					break;
				case 4:
					attron(COLOR_PAIR(5));
					break;
				default:
					attron(COLOR_PAIR(3));
					break;
			}
			printw(" %c ",tmp);
		}
		printw("\n");
	}
	attron(COLOR_PAIR(2));
	printw("------------------------------------------------------------\n");
}
	
/* position indique si le mot devra etre posé horizentalement(0) ou verticalement(O) */
/* addWord ajoute un mot passé en parametre dans la grille*/

int addWordGrille(struct box Grille[15][15], char motaAjouter[15],int longueurMot, int position, int positionInitialLigne, int positionInitialColonne){

  int iTmp;
  int i = 0;
 if (position == 1){  
   
   for (iTmp = positionInitialColonne; iTmp<positionInitialColonne+longueurMot; iTmp++){
   Grille[positionInitialLigne][iTmp].tok.letter= motaAjouter[i];
   Grille[positionInitialLigne][iTmp].tok.value= findValue(motaAjouter[i]);
 i = i+1;
   }
 }
   else {
     for (iTmp = positionInitialLigne; iTmp<positionInitialLigne+longueurMot; iTmp++){
     Grille[iTmp][positionInitialColonne].tok.letter= motaAjouter[i];
     Grille[iTmp][positionInitialColonne].tok.value=findValue(motaAjouter[i]);     
       i = i+1;
     }
   }    
 return 0;
}

int searchFirstWord(char hand[7], char motsPotentiel[20000][15],int sizeWord[20000]){
	int nb= 0;
	int matchword=0;
	for(int i=0; i<nbWords; i++){
		int used[7] = {0};
		matchword=1;
		for (int j=0;j<sizeWords[i];j++){
			int ok=0;
			for(int letter=0;letter<7;letter++){
				if(hand[letter] == dictionary[i][j] && !used[letter] && !ok){
					used[letter]=1;
					ok=1;
				}
			}
			if(!ok){
				matchword=0;
				break;
			}
		}
		if(matchword){
			for(int j=0;j<sizeWords[i];j++){
				motsPotentiel[nb][j] = dictionary[i][j];
			}
			sizeWord[nb]= sizeWords[i];
			nb++;
		}
	}
	return nb;
}


void findBestFirstWord(struct box Grille[15][15], char hand[7],struct wordScrabble Word[10], int n,int scores[10]){
	for(int i=0;i<n;i++)
		scores[i]=0;
	char Words[20000][15];
	int sizes[20000];
	int u = searchFirstWord(hand,Words,sizes);
	for(int i=0;i<u;i++){
		struct wordScrabble tmp1;
		for(int j=0;j<sizes[i];j++){
			tmp1.letters[j] = Words[i][j];
		}
		tmp1.position=1;
		tmp1.l = sizes[i];
		tmp1.x=7;
		for(int j=0;j<sizes[i];j++){
			tmp1.y=7-j;
			int v = valueOfWord(tmp1,Grille);
			for(int k=0;k<n;k++){
				if(scores[k] < v){
					struct wordScrabble wordNow;
					struct wordScrabble wordNext;
					int scoreNow;
					int scoreNext;
					wordNow = tmp1;
					scoreNow = v;
					for(int h=k; h<n;h++){
						wordNext = Word[h];
						scoreNext = scores[h];
						Word[h] = wordNow;
						scores[h] = scoreNow;
						wordNow = wordNext;
						scoreNow = scoreNext;
					}
					break;
				}
			}
		}
	}
}
