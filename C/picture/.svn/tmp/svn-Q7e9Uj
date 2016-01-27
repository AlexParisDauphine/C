picture:  src/main.c src/structures.h src/grid.h src/fill_grid.h src/recursive.h src/random.h
	cc -std=c99 -lncurses -o picture src/main.c src/structures.h src/grid.h src/fill_grid.h src/recursive.h src/random.h
	
recursive.o: src/recursive.h src/recursive.c src/structures.h src/grid.h src/fill_grid.h
	cc -std=c99 -o recursive.o src/recursive.h src/recursive.c src/structures.h src/grid.h src/fill_grid.h

grid.o: src/grid.h  src/grid.c src/structures.h
	cc -std=c99 -lncurses-o grid.o src/grid.h src/grid.c src/structures.h
	
fill_grid.o: src/fill_grid.h src/fill_grid.c src/structures.h
	cc -std=c99 -o fill_grid.o src/fill_grid.h src/fill_grid.c src/structures.h	

glue.o: src/glue.c src/glue.h src/structures.h
	cc -std=c99 -o  
	
random.o: src.random.c src.random.h src/structures.h
	cc -std=c99 -o random.o src/random.h src.random.c src/structures.h

clean :
	rm picture
