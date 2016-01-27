void createAlphabetRandom(){
	char *tmp = "AAAAAAAAABBCCDDDEEEEEEEEEEEEEEEFFGGHHIIIIIIIIJKLLLLLMMMNNNNNNOOOOOOPPQRRRRRRSSSSSSTTTTTTUUUUUUVVWXYZ??";
	
	for(int i=0;i<102;i++)
		letters[i] = *(tmp+i);
	for(int i=0; i<2000; i++){
	  int j = rand()%102;
	  int k =  rand()%102;
	  int tmp = letters[j];
	  letters[j] = letters[k];
	  letters[k] = tmp;
	}
}

int takeLetter(struct player *p, struct box Grille[15][15],struct wordScrabble Word){
	int used[7]={0};
	int posX=0;
	int posY=0;
	for(int i=0;i<Word.l;i++){
		if(Word.position){
			posY=i;
		}
		else{
			posX=i;
		}
		if(Grille[Word.x + posX][Word.y + posY].tok.value == 0){
			int ok=0;
			for(int j=0;j<7;j++){
				if(p->player_letters[j] == Word.letters[i] && !used[j]){
					used[j]=1;
					ok=1;
					break;
				}
			}
			if(!ok){
				for(int j=0;j<7;j++){
					if(p->player_letters[j] == '?'){
						used[j] = 1;
					}
				}
			}
		}
	}
	char newLetters[7];
	int notUsed=0;
	for(int i=0;i<7;i++){
		if(!used[i]){
			newLetters[notUsed] = p->player_letters[i];
			notUsed++;
		}
	}
	for(int i=notUsed;i<7;i++){
		newLetters[i] = letters[letterUsed];
		letterUsed++;
		if(letterUsed==102)
			return 0;
	}
	
	for(int i=0;i<7;i++){
		p->player_letters[i] = newLetters[i];
	}
	return 1;
}
	
int createAlphabetByFile(char *file){  /* Creer le tableau d'alphabet a partir d'un fichier*/
  
  FILE* File = NULL;
  char currentChar = 0;
  File = fopen(file, "r");
  int i = 0;
  if (File != NULL)
  {
	for(int i=0;i<102;i++){
		currentChar = fgetc(File);
		letters[i] = currentChar;
	}
    fclose(File);
    return 1;
  }
  return 0;
}

int draw (){ /* s'occupe du tirage de lettrer */
  int j = letters[numberDraw];
  numberDraw++;
  return j;
}
  





void createLettersValue(){
	struct letterValue tmp[7];
	tmp[0].value=0;
	tmp[0].letters ="?";

	tmp[1].value=1;
	tmp[1].letters ="EAINORSTUL";
	
	tmp[2].value=2;
	tmp[2].letters="DMG";
	
	tmp[3].value=3;
	tmp[3].letters="BCP";
	
	tmp[4].value=4;
	tmp[4].letters="FHV";
	
	tmp[5].value=8;
	tmp[5].letters="JQ";
	
	tmp[6].value=10;
	tmp[6].letters="KWXZ";
	
	for(int i=0;i<7;i++){
		valuesLetter[i]=tmp[i];
	}
}

int isIn(char *w,char l){
	int i=0;
	int find=0;
	while(w[i] != '\0' && !find){
		if(w[i] == l)
			find=1;
		i++;
	}
	return find;
}
		
int findValue(char l){
	if(toupper(l) == l){
		for(int i=0;i<nbValueLetter;i++){
			if(isIn(valuesLetter[i].letters,l))
				return valuesLetter[i].value;
		}
	}
	return -1;
}
