int rand_a_b(int a, int b){
    return rand()%(b-a) +a;
}


struct grid random_grid(int lvl){
	struct grid Grid;
	int nbBlack;
	if(lvl == 1){
		Grid.line =4;
		Grid.column=4;
		nbBlack = rand_a_b(6,10);		
	}
	else if(lvl == 2){
		Grid.line = rand_a_b(5,7);
		Grid.column = rand_a_b(5,7);
		nbBlack = rand_a_b((Grid.line*Grid.column)/3,(Grid.line*Grid.column)/2);
	}
        else{
	  Grid.line = rand_a_b(7,9);
	  Grid.column = rand_a_b(7,9);
	  nbBlack = rand_a_b((Grid.line*Grid.column)/3, (Grid.line*Grid.column)/2.5);
	}
	int k=0;
	for(int i=0;i<Grid.line;i++){
		for(int j=0;j<Grid.column;j++){
			if(k< nbBlack){
				Grid.cases[i][j].value=1;
				k++;
			}
			else
			Grid.cases[i][j].value=0;
		}
	}
	
	
	//mélanger
	
	for(int i=0;i<5000;i++){
		int x1=rand_a_b(0,Grid.line);
		int x2=rand_a_b(0,Grid.line);
		int y1=rand_a_b(0,Grid.column);
		int y2=rand_a_b(0,Grid.column);
		int tmp = Grid.cases[x1][y1].value;
		Grid.cases[x1][y1].value = Grid.cases[x2][y2].value;
		Grid.cases[x2][y2].value = tmp;
	}
	//compter les séquences
	for(int i=0;i<Grid.line;i++){
		int nbSequence=0;
		int seq=0;
		for(int j=0;j<Grid.column;j++){
			if(Grid.cases[i][j].value == 1){
				seq++;
			}
			if(Grid.cases[i][j].value != 1 || j==Grid.column-1){
				if(seq!=0){
					Grid.sequences[i][nbSequence] = seq;
					nbSequence++;
				}
				seq=0;
			}
		}
		Grid.sequences[i][nbSequence]=-1;
	}
	
	for(int j=0;j<Grid.column;j++){
		int nbSequence=0;
		int seq=0;
		for(int i=0;i<Grid.line;i++){
			if(Grid.cases[i][j].value == 1){
				seq++;
			}
			if(Grid.cases[i][j].value != 1 || i==Grid.line-1){
				if(seq!=0){
					Grid.sequences[Grid.line+j][nbSequence] = seq;
					nbSequence++;
				}
				seq=0;
			}
		}
		Grid.sequences[Grid.line+j][nbSequence]=-1;
	}
	return Grid;
}
