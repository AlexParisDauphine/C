scrabble:  src/main.c src/structures.h src/grille.h src/letters.h src/algorithm.h
	cc -g -std=c99 -o scrabble src/main.c src/structures.h src/grille.h src/algorithm.h -lncurses

grille.o: src/grille.h src/grille.c src/structures.h src/letters.h src/algorithm.h
	cc -g -std=c99 -o grille.o src/grille.h src/grille.c src/structures.h src/letters.h src/algorithm.h -lncurses
	
letters.o: src/letters.h src/letters.c src/structures.h
	cc -g -std=c99 -o letters.o src/letters.h src/letters.c src/structures.h
	
algorithm.o: src/algorithm.h src/algorithm.c src/structures.h src/letters.h
	cc -g -std=c99 -o src/algorithm.o src/algorithm.h src/algorithm.c src/structures.h src/letters.h

clean :
	rm scrabble
