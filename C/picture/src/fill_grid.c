void fill_grid_by_black_cases (struct grid * grille){
	int i;
	int l;
	for( i = 0; i < grille->line; i++){
      //on remplit un premier tableau de cases noires à partir de la gauche
		int a;
		int j = 0;
        int aa = 0;
        int bb = 0;
		struct box tab_left[100];
		for(  aa= 0; aa<100; aa++){
			tab_left[aa].value = -1; 
			tab_left[aa].nbSequences_line = -1;}
		struct box tab_right[100]; 
		for( bb= 0; bb<100; bb++){
			tab_right[bb].value = -1; 
			tab_right[bb].nbSequences_line = -1;
			}
		l =0;  
		int k; 
		while(grille->sequences[i][j] != -1){
			a = grille->sequences[i][j];
			for( k= l; k<l+a ;k++){	
				tab_left[k].value = 1;
				tab_left[k].nbSequences_line = j;
				}
			l = l+a+1;
			j++; 
			}
		int s; 
		int b;
		int o = j-1;
		int x = 0; 
		while(o >= 0){
			b = grille->sequences[i][o];
			for( s = grille->column -x -1; s > grille->column -x -1 -b; s--){
				tab_right[s].value = 1;
                tab_right[s].nbSequences_line = o;
			}
		x = x+b+1;
		o--;   
			}
	   for(int h =0; h<grille->column; h++){
			if(tab_left[h].value == 1 && tab_right[h].value == 1 && tab_left[h].nbSequences_line == tab_right[h].nbSequences_line  &&  tab_left[h].nbSequences_line != -1 &&  tab_right[h].nbSequences_line != -1  )
			{	   
				(grille->cases[i][h]).value = 1 ;
				grille->cases[i][h].nbSequences_line =  tab_right[h].nbSequences_line;
			}
		} 
	}
    int m;
    int n;
	for( m = 0; m < grille->column; m++){
        //on remplit un premier tableau de cases noires à partir du haut
		int z;
		int p = 0;
		int qq = 0;
		int pp = 0;
		struct box tab_left1[100]   ;
		for(  qq = 0; qq<100; qq++){
			tab_left1[qq].value = -1; 
			tab_left1[qq].nbSequences_column = -1;
			}
		struct box tab_right1[100]  ;
		for( pp = 0; pp<100; pp++){
			tab_right1[pp].value = -1; 
			tab_right1[pp].nbSequences_column = -1;
			}
		n =0;  
		while(grille->sequences[m + grille->line][p] != -1){
			int d = grille->sequences[m + grille->line][p];
			for(z= n; z<n+d ;z++){
				tab_left1[z].value = 1;
				tab_left1[z].nbSequences_column = p; 
				}
			n = n+d+1;
			p++; 
			}
		int f;
		int t;
		int w = 0;	
		int y = p-1;
		while(y >= 0){  
			int g = grille->sequences[m+grille->line][y];
			for( t = grille->line -w -1 ; t > grille->line -g -w -1; t--){
			tab_right1[t].value = 1;
			tab_right1[t].nbSequences_column = y; 
				}
		w = w +g +1;
		y--;   
		}
		for(f =0; f<grille->line; f++){
			if(tab_left1[f].value == 1 && tab_right1[f].value == 1 && tab_left1[f].nbSequences_column == tab_right1[f].nbSequences_column &&  tab_left1[f].nbSequences_column != -1 &&  tab_right1[f].nbSequences_column != -1  )
   			grille->cases[f][m].value = 1 ;
			grille->cases[f][m].nbSequences_column = tab_left1[f].nbSequences_column;
		}
	} 
}

int nb_sequences(struct grid grille, int i ){
	int compteur =0;
	int j =0;
	while(grille.sequences[i][j] != -1){
		compteur ++;
		j++;
}
	return compteur;
}

int line_is_complete(struct grid grille, int i){
	int k = nb_sequences(grille,i);
	int sum =0;
	int compteur = 0;
	for (int j = 0; j <k; j++){
		sum = sum + grille.sequences[i][j];
	}
	for (int c =0; c<grille.column;c++){
		if (grille.cases[i][c].value == 1)
			compteur ++;
	}	
	return ( compteur == sum);
}

int column_is_complete( struct grid grille, int i){	
			int k = nb_sequences(grille, i + grille.line);
			int sum =0;
			int compteur = 0;
			for (int j = 0; j <k; j++){
				sum = sum + grille.sequences[i+grille.line][j];
					}
			for (int c =0; c<grille.line;c++){
				if (grille.cases[c][i].value == 1)
					compteur ++;
					}	
			return ( compteur == sum);
		}

void fill_grid_by_spaces(struct grid* grille){  // reste a mettre a jour le champ nbSequences
	for (int li =0; li<grille->line;li++){  // remplit de blancs les cases restantes des lignes completes
		if (line_is_complete(*grille, li)){
			for (int li_col = 0;li_col<grille->column;li_col++){
					if(grille->cases[li][li_col].value != 1)
						grille->cases[li][li_col].value = 0;
					}}}
	for (int col = 0; col<grille->column;col++){
		if (column_is_complete(*grille,col)){
			for (int col_li = 0; col_li<grille->line; col_li++){
					if (grille->cases[col_li][col].value !=1)
						grille->cases[col_li][col].value = 0;
					}
				}
			}
		}


void fill_grid_boarder_line_by_spaces(struct grid* grille) { 
	for (int li = 0; li< grille->line; li++){
		for (int li_col = 0; li_col<grille->column; li_col++){
			if (grille->cases[li][li_col].value == 1){
				if (li_col == grille->sequences[li][0]){
					grille->cases[li][0].value = 0;
					int j = 0;
					while (grille->cases[li][li_col + j].value ==1 && li_col + j < grille->column){
							grille->cases[li][j].value = 0;
						 	j++;}
						}
					break;
				}
			}		
		}		
	}				
							
void fill_grid_boarder_column_by_spaces(struct grid* grille) {
	for (int col = 0; col< grille->column; col++){
		for (int col_li = 0; col_li < grille->line; col_li++){
			if (grille->cases[col_li][col].value == 1){
				if (col_li == grille->sequences[grille->line + col ][0]){
					grille->cases[0][col].value = 0;
					int j = 0;
					while (grille->cases[col_li +j][col].value ==1 && col_li + j < grille->line){
							grille->cases[j][col].value = 0;
						 	j++;}
						}
					break;
				}
			}
		}
	}
						
void joining(struct grid* grille) {
	for (int li=0;li<grille->line;li++){
		for (int li_col = 1; li_col<grille->column - 1;li_col++){
			if( grille->cases[li][li_col].value == -1){
				if( grille->cases[li][li_col -1].value ==1 && grille->cases[li][li_col +1].value ==1 && grille->cases[li][li_col +1].nbSequences_line == grille->cases[li][li_col -1].nbSequences_line && grille->cases[li][li_col -1].nbSequences_line != -1   ){
					grille->cases[li][li_col].value = 1;
					grille->cases[li][li_col].nbSequences_line = grille->cases[li][li_col+1].nbSequences_line;
				}
			}
		}
	}
	for (int col= 0;col<grille->column;col++){
		for(int col_li = 1; col_li<grille->line -1; col_li++){
			if (grille->cases[col_li][col].value == -1){
				if(grille->cases[col_li +1][col].value ==1 && grille->cases[col_li -1][col].value == 1 && grille->cases[col_li -1][col].nbSequences_column == grille->cases[col_li +1][col].nbSequences_column &&  grille->cases[col_li+1][col ].nbSequences_column != -1 ){
					grille->cases[col_li][col].value = 1;
					grille->cases[col_li][col].nbSequences_column = grille->cases[col_li +1][col].nbSequences_column;
				}
			}
		}
	}
}

void splitting(struct grid * grille){
	for (int li=0;li<grille->line;li++){
		for (int li_col = 1; li_col<grille->column - 1;li_col++){
					
			if( grille->cases[li][li_col].value == -1){
				if( grille->cases[li][li_col -1].value ==1 && grille->cases[li][li_col +1].value ==1 && grille->cases[li][li_col +1].nbSequences_line != -1 && grille->cases[li][li_col -1].nbSequences_line != -1 && grille->cases[li][li_col +1].nbSequences_line != grille->cases[li][li_col -1].nbSequences_line  ){
					grille->cases[li][li_col].value = 0;
				}
			}
		}
	}
	for (int col= 0;col<grille->column;col++){
		for(int col_li = 1; col_li<grille->line -1; col_li++){
			if (grille->cases[col_li][col].value == -1){
				if(grille->cases[col_li +1][col].value ==1 && grille->cases[col_li -1][col].value == 1 && grille->cases[col_li -1][col].nbSequences_column !=  -1 && grille->cases[col_li +1][col].nbSequences_column != -1 && grille->cases[col_li +1][col].nbSequences_column != grille->cases[col_li -1][col].nbSequences_column ){
					grille->cases[col_li][col].value = 0;
				}
			}
		}
	}
}

int nb_box_have_to_be(struct grid grille, int i) {
	int compteur = 0;
	int j = 0;
	while ( grille.sequences[i][j] != -1 ){
		compteur = compteur + grille.sequences[i][j];
		j++;
	}
	return compteur;
}
	
int nb_box_exist(struct grid grille, int i, int column_or_line){ // si 1, i numero ligne sinon i num colonne
	int compteur = 0;
	int j ;
//	int a ;
	//int compteur2 = 0;
	if (column_or_line == 1){
		for (j =0; j<grille.column; j++){ 
			if ( grille.cases[i][j].value == 1 ){
				compteur ++;
						
			}
		}
	}	
	if ( column_or_line == 0){
		for (int a = 0; a<grille.line; a++){
			if( grille.cases[a][i].value == 1){
				compteur ++;
				
			}
		}
	
	}
	return compteur;
 }


int nb_uncertainly( struct grid grille, int i, int column_or_line){
	int compteur = 0;
	int j ;
	if (column_or_line == 1){
		for ( j= 0; j< grille.column; j++){
			if ( grille.cases[i][j].value == -1 ){
				compteur ++;
			}
		}
	}	
	if (column_or_line == 0){
		for ( int a= 0; a < grille.line; a++){
			if( grille.cases[a][i].value == -1){
				compteur ++;
			}
		}
	}
	return compteur;
 }

void simple_box ( struct grid * grille){
	for ( int li = 0; li<grille->line; li ++){
		if ( (nb_box_have_to_be(*grille, li) - nb_box_exist(*grille, li, 1)) == nb_uncertainly(*grille, li, 1)){
			for (int li_col = 0; li_col <grille->column; li_col ++){
				if ( grille->cases[li][li_col].value == -1){
						grille->cases[li][li_col].value = 1;
						if ( li_col == 0 ){
							grille->cases[li][li_col].nbSequences_line = 0 ;
						}
						if (li_col == grille->column -1 ){
						
							grille->cases[li][li_col].nbSequences_line = nb_sequences(*grille, li) -1;
					}
				}
			}
		}
	}
/*	for (int col = 0; col<grille->column; col ++){
		if ((nb_box_have_to_be(*grille, grille->line + col) - nb_box_exist(*grille, col, 0)) == nb_uncertainly(*grille, col,0)){
			for ( int col_li = 0; col_li < grille->line; col_li++){
				if ( grille->cases[col_li][col].value == -1){
					grille->cases[col_li][col].value = 1;
					if ( col_li == 0){
						grille->cases[col_li][col].nbSequences_column = 0;
					}
					if ( col_li == grille->line -1){
						grille->cases[col_li][col].nbSequences_column = nb_sequences(*grille, grille->line + col) -1;
					}
				}
			}
		}
	} */
}					
	
int equalGrid( struct grid  grille1, struct grid grille2) {
	for (int li=0; li<grille1.line; li++){
		for (int li_col=0; li_col<grille1.column; li_col++){
			if (grille1.cases[li][li_col].value != grille2.cases[li][li_col].value){
				return 0;
			}
		}
	}
	for (int col=0; col<grille1.column; col++){
		for ( int col_li= 0; col_li<grille1.line; col_li++){
			if (grille1.cases[col_li][col].value !=grille2.cases[col_li][col].value){
				return 0;
			}
		}
	}
}
