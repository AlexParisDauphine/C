int areEquals(struct grid Grid1, struct grid Grid2){
	for(int i=0;i<Grid1.line;i++){
		for(int j=0;j<Grid1.column;j++){
			if(Grid1.cases[i][j].value != Grid2.cases[i][j].value){
				return 0;
			}
		}
	}
	return 1;
}

void applyRules(struct grid * Grid){
	struct grid g;
	do{
		g=copy(*Grid);
		fill_grid_boarder_line_by_spaces(Grid);
		fill_grid_boarder_column_by_spaces(Grid);
		fill_grid_by_spaces(Grid);
		joining(Grid);
	
		splitting(Grid);
	
	
		simple_box(Grid);
		fill_grid_by_spaces(Grid);
	}
	while(!areEquals(g,*Grid));	
}

int isComplete(struct grid Grid)
{
	//vérification des lignes
	for(int i=0;i<Grid.line;i++){
		int nbSequence=0;
		int seq=0;
		for(int j=0;j<Grid.column;j++){
			if(Grid.cases[i][j].value == 1){
				seq++;
			}
			if(Grid.cases[i][j].value != 1 || j == Grid.column-1){
				if(seq!=0){
					if(Grid.sequences[i][nbSequence] != seq){
						return 0;
					}
					nbSequence++;
				}
				seq=0;
			}
		}
		int t=0;
		while(Grid.sequences[i][t] != -1)
			t++;
		if(t != nbSequence){
			return (int)0;
		}
	}
	
	//vérification des colonnes
	for(int i=0;i<Grid.column;i++){
		int nbSequence=0;
		int seq=0;
		for(int j=0;j<Grid.line;j++){
			if(Grid.cases[j][i].value == 1){
				seq++;
			}
			if(Grid.cases[j][i].value != 1 || j==Grid.line-1){
				if(seq!=0){
					if(Grid.sequences[Grid.line+i][nbSequence] != seq){
						return 0;
					}
					nbSequence++;
				}
				seq=0;
			}
		}
		
		int t=0;
		while(Grid.sequences[Grid.line+i][t] != -1)
			t++;
		if(t != nbSequence){
			return 0;
		}
	}
	return 1;
}
	
	
int isValid(struct grid Grid){
	int sequences[100];
		int blancs[100];
		int nbSequence=0;
		int seq=0;
		int blancOK=1;
		int blanc=0;
		int nbBlanc=0;
		int t=0;
	//calcul des séquences par ligne
	for(int i=0;i<Grid.line;i++){
		while(Grid.cases[i][t].value != 1 && t < Grid.column)
			t++;
		for(int j=t;j<Grid.column;j++){
			if(Grid.cases[i][j].value == 1){
				seq++;
				if(seq==0){
					blancs[nbBlanc] = blanc;
					nbBlanc++;
					blanc=0;
				}
				blancOK=1;
			}
			if(Grid.cases[i][j].value == 0 || j==Grid.column-1){
				if(seq!=0){
					sequences[nbSequence]=seq;
					nbSequence++;
				}
				seq=0;
				blancOK=0;
				blanc=0;
			}
			if(Grid.cases[i][j].value == -1 || j==Grid.column-1)
			{
				if(seq!=0){
					sequences[nbSequence]=seq;
					nbSequence++;
				}
				seq=0;
				if(blancOK)
					blanc++;
			}
		}
		
		//vérification des séquences par ligne
		int it=0;
		for(int k=0;k<nbSequence;k++){
			while(Grid.sequences[i][it] != -1 && sequences[k] > Grid.sequences[i][it]){
				it++;
			}
			Grid.sequences[i][it] -= (sequences[k] + blancs[k]);
			
			if(Grid.sequences[i][it] == -1)
				return 0;
		}
	}
	
	for(int j=0;j<Grid.column;j++){
		//calcul des séquences par colonnes
		while(Grid.cases[t][j].value != 1 && t < Grid.column)
			t++;
		for(int i=0;i<Grid.line;i++){
			if(Grid.cases[i][j].value == 1){
				seq++;
				if(seq==0){
					blancs[nbBlanc] = blanc;
					nbBlanc++;
					blanc=0;
				}
				blancOK=1;
			}
			if(Grid.cases[i][j].value == 0 || j==Grid.column-1){
				if(seq!=0){
					sequences[nbSequence]=seq;
					nbSequence++;
				}
				seq=0;
				blancOK=0;
				blanc=0;
			}
			if(Grid.cases[i][j].value == -1 || j==Grid.column-1)
			{
				if(seq!=0){
					sequences[nbSequence]=seq;
					nbSequence++;
				}
				seq=0;
				if(blancOK)
					blanc++;
			}
		}
		
		//vérification si valide par colonne
		int it=0;
		for(int k=0;k<nbSequence;k++){
			while(Grid.sequences[Grid.line+j][it] != -1 && sequences[k] > Grid.sequences[Grid.line+j][it]){
				it++;
			}
			Grid.sequences[Grid.line+j][it] -= (sequences[k] + blancs[k]);
			if(Grid.sequences[Grid.line+j][it] == -1)
				return 0;
		}
	}
	return 1;
}

int fixUncertainty(struct grid * Grid, int val){
	for(int i=0;i<Grid->line;i++){
		for(int j=0;j<Grid->column;j++){
			if(Grid->cases[i][j].value == -1){
				Grid->cases[i][j].value = val;
				return 1;
			}
		}
	}
	return 0;
}

struct grid resolvGrid(struct grid Grid){
	applyRules(&Grid);
	if(isComplete(Grid)){
		return Grid;
	}
	else if(!isValid(Grid)){	
		Grid.line = -1;
		return Grid;
	}
	else{
		struct grid g=copy(Grid);
		if(fixUncertainty(&g, 1)){
			g = resolvGrid(g);
			if(g.line != -1){
				return g;
			}
			else{
				g=copy(Grid);
				fixUncertainty(&g, 0);
				g = resolvGrid(g);
				return g;
			}
		}
		else{
			Grid.line = -1;
			return Grid;
		}
	}
}
