#include "structures.h"
#include "fill_grid.h"
#include "grid.h"
#include "recursive.h"
#include "random.h"	
	
int main(int argc, char ** argv)
{
	//ncurses initialisation
	initscr();			/* Start curses mode 		  */
	raw();				/* Line buffering disabled	*/
	keypad(stdscr, TRUE);		/* We get F1, F2 etc..		*/
	srand(time(NULL));
	
	if(argc > 2){
		char file[100];	
		if(strcmp(argv[1],"-f") == 0){
			struct grid Grid= createGrid(argv[2]);
			fill_grid_by_black_cases(&Grid);
			Grid = resolvGrid(Grid);
			if(Grid.line != -1){
				displayGrid(Grid);
				printw("Appuyer sur s pour sauvegarder, sur une autre touche pour quitter\n");
				int j=getch();
				printw("\n");
				if(j == 's'){
					printw("tapez le nom du fichier dans lequel sauvegarder la grille:\n");
					getstr(file);
					saveGrid(file,Grid);
				}
			}
			else{
				printw("Grille non résolvable");
				getch();
			}
		}
		else if(strcmp(argv[1],"-r") == 0){
			if(atoi(argv[2]) ==1 || atoi(argv[2]) ==2 || atoi(argv[2]) ==3){
				struct grid Grid = random_grid(atoi(argv[2]));
				struct grid g = copy(Grid);
				for(int i=0;i<Grid.line;i++)
					for(int j=0;j<Grid.column;j++)
						g.cases[i][j].value = -1;
				displayGrid(g);
				printw("Appuyer sur r pour afficher la solution, sur s pour sauvegarder, sur une autre touche pour quitter\n");
				int i = getch();
				printw("\n");
				if(i=='r'){
					clear();
					displayGrid(Grid);
					printw("Appuyer sur s pour sauvegarder, sur une autre touche pour quitter\n");
					int j=getch();
					printw("\n");
					if(j == 's'){
						printw("tapez le nom du fichier dans lequel sauvegarder la grille:\n");
						getstr(file);
						saveGrid(file,Grid);
					}
				}
				else if(i=='s'){
					printw("tapez le nom du fichier dans lequel sauvegarder la grille:\n");
					getstr(file);
					saveGrid(file,g);
				}
			}
			else{
				printw("La syntaxe de l'option random est la suivante:\n ./picture -r {1,2,3}\nAppuyer sur une touche pour quitter");
				getch();
			}
		}
		else{
			printw("Mauvaise commande\n, les syntaxes à utiliser pour démarrer le programme correctement sont les suivantes:");
			printw("Utiliser le résolveur:\n./picture -f {CheminDuFichier}\n");
			printw("Utiliser la création de grille aléatoire:\n./picture -r {1,2,3}\n");
			printw("1 niveau facile, 2 niveau moyen, 3 niveau difficile\n");
			printw("Appuyer sur une touche pour quitter\n");
			getch();
		}
	}
	else{
		printw("Mauvaise commande\n, les syntaxes à utiliser pour démarrer le programme correctement sont les suivantes:");
		printw("Utiliser le résolveur:\n./picture -f {CheminDuFichier}\n");
		printw("Utiliser la création de grille aléatoire:\n./picture -r {1,2,3}\n");
		printw("1 niveau facile, 2 niveau moyen, 3 niveau difficile\n");
		printw("Appuyer sur une touche pour quitter\n");
		getch();
	}
		
	endwin();			/* End curses mode		  */
	
	return 0;
}
