//structure servant à savoir la place valable pour écrire un mot
struct valableSize{
	int min;
	int max;
};

void filldico(char file[], char dico[400000][15], int sizeDico[400000]){
	int nbWords=0;
	char letter;
	FILE * File = NULL;
	File = fopen(file, "r");
	if(File != NULL){
		while(letter != EOF){
			letter = fgetc(File);
			int j=0;
			while(letter != '\n' && letter != EOF && j<15){
				dico[nbWords][j] = letter;
				j++;
				letter = fgetc(File);
			}
			if(j!=15)
				sizeDico[nbWords] = j-1;
			else
				sizeDico[nbWords] = 15;
				while(letter != '\n' && letter != EOF)
					letter=fgetc(File);
			nbWords++;
		}
		fclose(File);
	}
}

int wordExist(char letters[15],int l){
	for(int i=0;i<nbWords;i++){
		if(sizeWords[i] == l){
			int found=1;
			for(int	j=0;j<l;j++){
				if(dictionary[i][j] != toupper(letters[j])){
					found=0;
					break;
				}
			}
			if (found)
				return 1;
		}
	}
	return 0;
}

int valueOfWord(struct wordScrabble Word, struct box Grille[15][15]){
	int score=0;
	int multi=1;
	int posX=0;
	int posY=0;
	int valueLetter;
	int bonusPoints=0;
	int nbPosed=0;
	for(int i=0;i<Word.l;i++){
		valueLetter = findValue(Word.letters[i]);
		if(valueLetter==-1)
			valueLetter=0;
		if(Word.position){
			posY=i;
		}
		else{
			posX=i;
		}
		//calcul du mot lui-même
		if(Grille[Word.x + posX][Word.y + posY].tok.value == 0){
			nbPosed++;
			switch (Grille[Word.x + posX][Word.y + posY].bonus)
			{
				case 1:
					multi*=2;
					break;
				case 2:
					multi*=3;
					break;
				case 3:
					valueLetter*=2;
					break;
				case 4:
					valueLetter*=3;
					break;
			}
		}
		score +=valueLetter;
		if(Grille[Word.x+ posX][Word.y + posY].tok.value == 0){
			//calcul des mots autour
			int j=0,k=0;
			char w[15];
			int l=0;
			int s=0;
			int mul=1;
			//horizontale
			if(Word.position){
				while(Word.x -(j+1) >= 0 && Grille[Word.x - (j+1)][Word.y + posY].tok.value != 0){
					valueLetter = findValue(Grille[Word.x - (j+1)][Word.y + posY].tok.letter);
					if(valueLetter==-1)
					valueLetter=0;
					if(Grille[Word.x - (j+1)][Word.y + posY].tok.value == 0)
					{
						switch (Grille[Word.x - (j+1)][Word.y + posY].bonus)
						{
							case 1:
								mul*=2;
								break;
							case 2:
								mul*=3;
								break;
							case 3:
								valueLetter*=2;
								break;
							case 4:
								valueLetter*=3;
								break;
						}
					}
					s +=valueLetter;
					j++;
				}
				while(Word.x + (k+1) < 15 && Grille[Word.x + k+1][Word.y + posY].tok.value != 0){
					valueLetter = findValue(Grille[Word.x + k+1][Word.y + posY].tok.letter);
					if(valueLetter==-1)
					valueLetter=0;
					if(Grille[Word.x + k+1][Word.y + posY].tok.value ==0)
					{
						switch (Grille[Word.x + k+1][Word.y + posY].bonus)
						{
							case 1:
								mul*=2;
								break;
							case 2:
								mul*=3;
								break;
							case 3:
								valueLetter*=2;
								break;
							case 4:
								valueLetter*=3;
								break;
						}
					}
					s +=valueLetter;
					k++;
				}
				int f=j;
				for(f;f>0;f--){
					w[l] = toupper(Grille[Word.x - f][Word.y + posY].tok.letter);
					l++;
				}
				w[l] = toupper(Word.letters[i]);
				l++;
				for(int m=1;m<=k;m++){
					w[l] = toupper(Grille[Word.x + m][Word.y + posY].tok.letter);
					l++;
				}
				if(wordExist(w,l) && l != 1){
					score+=findValue(Word.letters[i]);
						score+=s;
						multi*=mul;
				}
				else if(l != 1)
					return -1;
			}
			
			//vertical
			else{
				while(Word.y -(j+1) >= 0 && Grille[Word.x + posX][Word.y -(j+1)].tok.value != 0){
					valueLetter = findValue(Grille[Word.x + posX][Word.y -(j+1)].tok.letter);
					if(valueLetter==-1)
					valueLetter=0;
					if(Grille[Word.x + posX][Word.y -(j+1)].tok.value == 0){
						switch (Grille[Word.x + posX][Word.y -(j+1)].bonus)
						{
							case 1:
								mul*=2;
								break;
							case 2:
								mul*=3;
								break;
							case 3:
								valueLetter*=2;
								break;
							case 4:
								valueLetter*=3;
								break;
						}
					}
					s +=valueLetter;
					j++;
				}
				while(Word.y + (k+1) < 15 && Grille[Word.x + posX][Word.y + k+1].tok.value != 0){
					valueLetter = findValue(Grille[Word.x + posX][Word.y + k+1].tok.letter);
					if(valueLetter==-1)
						valueLetter=0;
					if(Grille[Word.x + posX][Word.y + k+1].tok.value == 0){
						switch (Grille[Word.x + posX][Word.y + k+1].bonus)
						{
							case 1:
								mul*=2;
								break;
							case 2:
								mul*=3;
								break;
							case 3:
								valueLetter*=2;
								break;
							case 4:
								valueLetter*=3;
								break;
						}
					}
					s+=valueLetter;
					k++;
				}
				for(j;j>0;j--){
					w[l] = Grille[Word.x +posX][Word.y - j].tok.letter;
					l++;
				}
				w[l] = Word.letters[i];
				l++;
				for(int m=1;m<=k;m++){
					w[l] = Grille[Word.x + posX][Word.y + m].tok.letter;
					l++;
				}
				if(wordExist(w,l) && l != 1){
					if(w[0]=='E' && w[1] == 'Z' && w[2] == 'E' && l==3 && wordExist(w,3))
						printf("ok");
					score+=s;
					multi*=mul;
				}
				else if(l != 1)
						return -1;
			}
		}
	}
	if(nbPosed==7)
		bonusPoints=50;
	return score *multi + bonusPoints;
}

int findWordsWithLine(struct box line[15], char letters[7], struct valableSize sizes[100],int nbSizes, struct wordScrabble result[1000],int position, int x){
	int nbResult=0;
	int nbJoker=0;
	for(int letter=0;letter<7;letter++){
		if(letters[letter] == '?'){
			nbJoker++;
		}
	}
	for(int i = 0;i<nbSizes;i++){
		for(int j=0;j<nbWords;j++){
			int tabJok[15]={0};
			int jok=nbJoker;
			if(sizes[i].max - sizes[i].min >= sizeWords[j]){
				//calcul correspondance des lettres
				int matchword=1;
				int used[7]={0};
				int used2[15]={0};
				for(int k=0;k<sizeWords[j];k++){
					int found=0;
					for(int letter=0;letter<7;letter++){
						if(letters[letter] == dictionary[j][k] && !used[letter] && !found){
							used[letter]=1;
							found=1;
						}
					}
					if(!found){
						for(int indice=sizes[i].min; indice < sizes[i].max+1;indice++){
							if(line[indice].tok.value != 0 && line[indice].tok.letter == dictionary[j][k] && !used2[indice]){
								found=1;
								used2[indice]=1;
							}
						}
					}
					if(!found && jok>0)
					{
						found=1;
						tabJok[k]=1;
						jok--;
					}
					if(!found){
						matchword=0;
						break;
					}
				}	
				if(matchword){
					int firstIndice=-1;
					int lastIndice;
					for(int t=sizes[i].min;t <= sizes[i].max;t++){
						if(line[t].tok.value != 0){
							if(firstIndice==-1)
								firstIndice=t;
							lastIndice = t;
						}
					}
					int maskCorrespond=0;
					if(sizeWords[j] >= lastIndice - firstIndice +1){
						int correspond = 1;
						for(int diff=0;diff <= (sizes[i].max - sizes[i].min ) - sizeWords[j]+1;diff++){
							int everPosed=0;
							for(int indiceMask=0;indiceMask < sizeWords[j];indiceMask++){
								if(line[sizes[i].min+indiceMask+diff].tok.value != 0)
									everPosed++;
								if(line[sizes[i].min+indiceMask+diff].tok.value != 0 && toupper(line[sizes[i].min+indiceMask+diff].tok.letter) != dictionary[j][indiceMask]){
									correspond =0;
								}
							}
							if(correspond && sizes[i].min +sizeWords[j]+diff -1>= lastIndice && everPosed != sizeWords[j] && everPosed>0){
								struct wordScrabble tmp;
								for(int t=0;t<sizeWords[j];t++){
									if(tabJok[t]){
										tmp.letters[t] = tolower(dictionary[j][t]);
									}
									else
										tmp.letters[t] = dictionary[j][t];
								}
								if(position){
									tmp.x = x;
									tmp.y= diff+sizes[i].min;
								}
								else{
									tmp.y = x;
									tmp.x=diff+sizes[i].min;
								}
								tmp.l=sizeWords[j];
								tmp.position = position;
								result[nbResult]=tmp;
								nbResult++;
							}
						}
					}	
				}	
			}
		}
	}
	return nbResult;
}

int searchWordByLine(struct box line[15],char letters[7], int nb, int position,struct wordScrabble Words[10000]){
	int i=0;
	int newleft=0;
	int lastleft=0;
	struct valableSize sizes[100];
	int l=0;
	while(i < 15){
		if(line[i].tok.value != 0)
		{
			do
			{
				i++;
			}
			while(line[i].tok.value != 0 && i < 15);
			struct valableSize tmp;
			tmp.min = lastleft;
			newleft=i+1;
			
			int j = i;
			int nbBlank = 0;
			while(j < 15 && nbBlank <= 7){
				int letterExist=0;
				while(line[j].tok.value == 0 && j < 15 && nbBlank <= 7)
				{
					nbBlank++;
					j++;
				}
				if(line[j].tok.value !=0){
					tmp.max = j-2;
				}
				else{
					tmp.max = j-1;
				}
				sizes[l]=tmp;
				l++;
				
				while(line[j].tok.value != 0 && j < 15)
				{
					j++;
					letterExist=1;
				}
			}
			lastleft=newleft;
		}
		i++;
	}
	return findWordsWithLine(line,letters,sizes,l,Words,nb,position);
}


void searchWord(struct box Grille[15][15],char letters[7],int n, struct wordScrabble Words[10], int scores[10]){
	int m;
	int v;
	for(int i=0;i<n;i++)
		scores[i] = -1;
	for(int i = 0; i < 15; i++){
		struct box line[15];
		struct box column[15];
		for(int j = 0;j < 15;j++){
			line[j] = Grille[i][j];
			column[j] = Grille[j][i];
		}
		struct wordScrabble WordsTmp[10000];
		m =searchWordByLine(line,letters, 1, i,WordsTmp);
		for(int k=0;k<m;k++){
			v=valueOfWord(WordsTmp[k],Grille);
			if(v != -1){
				for(int j=0;j<n;j++){
					if(scores[j] == -1){
						Words[j] = WordsTmp[k];
						scores[j] = v;
						break;
					}
					else if(scores[j] < v){
						struct wordScrabble wordNow;
						struct wordScrabble wordNext;
						int scoreNow;
						int scoreNext;
						wordNow = WordsTmp[k];
						scoreNow = v;
						for(int h=j; h<n;h++){
							wordNext = Words[h];
							scoreNext = scores[h];
							Words[h] = wordNow;
							scores[h] = scoreNow;
							wordNow = wordNext;
							scoreNow = scoreNext;
						}
						break;
					}
				}
			}
		}
		m=searchWordByLine(column,letters, 0, i,WordsTmp);
		for(int k=0;k<m;k++){
			v=valueOfWord(WordsTmp[k],Grille);
			if(v != -1){
				for(int j=0;j<n;j++){
					if(scores[j] == -1){
						Words[j] = WordsTmp[k];
						scores[j] = v;
						break;
					}
					else if(scores[j] < v){	
						struct wordScrabble wordNow;
						struct wordScrabble wordNext;
						int scoreNow;
						int scoreNext;
						wordNow = WordsTmp[k];
						scoreNow = v;
						for(int h=j; h<n;h++){
							wordNext = Words[h];
							scoreNext = scores[h];
							Words[h] = wordNow;
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
}

int hasLettersFirstWord(char w[15],char hand[7]){
	int used[7]={0};
	int usedJok[7]={0};
	for(int i=0;i<strlen(w);i++){
		int ok=0;
		for(int j=0;j<7;j++){
			if(hand[j] == w[i] && !used[j]){
				used[j]=1;
				ok=1;
				break;
			}
		}
		if(!ok){
			for(int j=0;j<7;j++){
				if(letters[j] == '?' && !used[j] && !ok){
					usedJok[j]=1;
					ok=1;
				}
			}
		}
		if(!ok){
			return 0;
		}
	}
	return 1;
}
		
int hasLetters(struct box Grille[15][15], char letters[7],struct wordScrabble word){
	int posX=0;
	int posY=0;
	int used[7]={0};
	int usedJok[7]={0};
	int everPosed=0;
	for(int i=0;i<word.l;i++){
		if(word.position){
			posY=i;
		}
		else{
			posX=i;
		}
		if(Grille[word.x + posX][word.y + posY].tok.value == 0){
			int ok=0;
			for(int j=0;j<7;j++){
				if(letters[j] == word.letters[i] && !used[j]){
					used[j]=1;
					ok=1;
					break;
				}
			}
			if(!ok){
				for(int j=0;j<7;j++){
					if(letters[j] == '?' && !used[j] && !ok){
						usedJok[j]=1;
						ok=1;
					}
				}
			}
			if(!ok){
				return 0;
			}
		}
		else if(Grille[word.x + posX][word.y + posY].tok.letter != word.letters[i]){
			return 0;
		}
		else{
			everPosed=1;
		}
	}
	return 1 && everPosed;
}
