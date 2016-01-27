

#ifndef FILL_GRID_H
#define FILL_GRID_H

#include "structures.h"
#include "fill_grid.c"

void fill_grid_by_black_cases (struct grid* grille);
int nb_sequences(struct grid grille, int i );
int line_is_complete(struct grid grille, int i);
int column_is_complete( struct grid grille, int i);
void fill_grid_by_spaces(struct grid* grille);
void fill_grid_boarder_line_by_spaces(struct grid* grille);
void fill_grid_boarder_column_by_spaces(struct grid* grille);
void joining(struct grid* grille);
void splitting(struct grid* grille);
int nb_box_have_to_be(struct grid grille, int i) ;
int nb_box_exist(struct grid grille, int i, int column_or_line);
int nb_uncertainly( struct grid grille, int i, int column_or_line);
void simple_box ( struct grid * grille);
int equalGrid( struct grid  grille1, struct grid grille2);

#endif
