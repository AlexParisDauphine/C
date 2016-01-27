#define KNRM  "\033[7;0m"
#define KBLK  "\033[7;1m"

struct grid copy(struct grid Grid){
	struct grid g;
	g.column = Grid.column;
	g.line = Grid.line;
	for(int i=0;i<Grid.line;i++){
		for(int j=0;j<Grid.column;j++){
			g.cases[i][j].value = Grid.cases[i][j].value;
			g.cases[i][j].nbSequences_column = Grid.cases[i][j].nbSequences_column;
			g.cases[i][j].nbSequences_line = Grid.cases[i][j].nbSequences_line;
		}
	}
	for(int i=0;i<Grid.line + Grid.column;i++){
		int j=0;
		while(Grid.sequences[i][j] != -1){
			g.sequences[i][j] = Grid.sequences[i][j];
			j++;
		}
		g.sequences[i][j]=-1;
	}
	return g;
}


void displayGrid(struct grid Grid){
	int maxline=0;
	int maxcolumn=0;
	for(int i=0;i<Grid.line;i++){
		int j=0;
		while(Grid.sequences[i][j] != -1)
			j++;
		maxline = j > maxline ? j : maxline;
	}
	for(int i=Grid.line;i<Grid.column+Grid.line;i++){
		int j=0;
		while(Grid.sequences[i][j] != -1)
			j++;
		maxcolumn = j > maxcolumn ? j : maxcolumn;
	}
	
	
	for(int i=0;i<Grid.line;i++){
		int j = 0;
		while(Grid.sequences[i][j] != -1){
			move(i+maxcolumn,j*2);
			printw("%d ",Grid.sequences[i][j]);
			j++;
		}
	}
	
	for(int i=Grid.line;i<Grid.line+Grid.column;i++){
		int k=i-Grid.line;
		int j=0;
		while(Grid.sequences[i][j] != -1){
			move(j,(k*2+maxline)*2);
			printw(" %d ",Grid.sequences[i][j]);
			j++;
		}
	}
	start_color();
	init_pair(1, COLOR_BLACK, COLOR_WHITE);
	init_pair(2, COLOR_WHITE, COLOR_BLACK);
	bkgd(COLOR_PAIR(1));
	for(int i=0;i<Grid.line;i++){
		for(int j=0;j<Grid.column;j++){
			move(maxcolumn+i,(maxline+j*2)*2);
			if(Grid.cases[i][j].value == 1){
				attron(COLOR_PAIR(2));
				printw("   ");
			}
			else{
				attron(COLOR_PAIR(1));
				printw("   ");
				attroff(COLOR_PAIR(1));
			}
			attron(COLOR_PAIR(1));
			printw("|");
			attroff(COLOR_PAIR(1));
		}
		printw("\n");
	}
}
		
void saveGrid( char file[], struct grid Grid){
	FILE * File = NULL;
	File = fopen(file, "w");
	
	if(File != NULL){
		fprintf(File,"%d",Grid.line);
		fputc(' ', File);
		fprintf(File,"%d",Grid.column);
		
		fputc('\n',File);
		for(int i=0;i< Grid.line;i++){
			int j = 0;
			while(Grid.sequences[i][j] != -1){
				if(j != 0)
					fputc(' ', File);
				fprintf(File,"%d",Grid.sequences[i][j]);
				j++;
			}
			if(i != Grid.line -1){
				fputc(';',File);
			}
		}
		fputc('\n',File);
		
		for(int i=Grid.line; i < Grid.column+Grid.line;i++){
			int j = 0;
			while(Grid.sequences[i][j] != -1){
				if(j != 0)
					fputc(' ', File);
				fprintf(File,"%d",Grid.sequences[i][j]);
				j++;
			}
			if(i != Grid.line+Grid.column -1){
				fputc(';',File);
			}
		}
		fputc('\n',File);
		for(int i=0;i<Grid.line;i++){
			for(int j=0;j<Grid.column;j++){
				if(Grid.cases[i][j].value != -1)
					fprintf(File,"%d",Grid.cases[i][j].value);
				else
					fputc('_',File);
			}
			fputc('\n',File);
		}
		fclose(File);
	}
}

struct grid createGrid( char file[]){
	struct grid Grid;
	FILE * File = NULL;
	File = fopen(file, "r");
	int i=0;
	int nbLines=0;
	char current_char;
	if(File != NULL) {
		current_char = fgetc(File);
		Grid.line = 0;
		Grid.column = 0;
		while( current_char != ' '){
			Grid.line *= 10;
			Grid.line += current_char - '0';
			current_char= fgetc(File);
		}
		current_char=fgetc(File);
		while( current_char != '\n'){
			Grid.column *= 10;
			Grid.column += current_char - '0';
			current_char = fgetc(File);
		}
		while(nbLines != 2 || current_char == ';'){
			if(current_char == '\n'){
				nbLines++;
			}
			int j=0;
			current_char = fgetc(File);
			while(current_char != ';' && current_char != '\n'){
				Grid.sequences[i][j] = 0;
				while(current_char != ' ' && current_char != ';' && current_char != '\n'){
					Grid.sequences[i][j] *=10;
					Grid.sequences[i][j]+= (current_char - '0');
					current_char = fgetc(File);
				}
				j++;
				if(current_char == ' ')
					current_char = fgetc(File);
			}
			Grid.sequences[i][j] = -1;
			i++;
		}
		for(int i=0;i<Grid.line;i++){
			for(int j=0;j<Grid.column;j++){
				int nbSequence=0;
				int nextblack=0;
				current_char = fgetc(File);
				if(current_char != '_'){
					Grid.cases[i][j].value = current_char - '0';
				}
				else{
					Grid.cases[i][j].value = -1;
				}
				Grid.cases[i][j].nbSequences_line = -1;
				Grid.cases[i][j].nbSequences_column = -1;
			}
			current_char = fgetc(File);
		}
		fclose(File);
	}
	return Grid;
}
