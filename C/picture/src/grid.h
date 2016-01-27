#ifndef GRID_H
#define GRID_H

#include "structures.h"
#include "grid.c"

struct grid copy(struct grid Grid);
void displayGrid(struct grid Grid);
struct grid createGrid( char file[]);
void saveGrid( char file[], struct grid Grid);

#endif
