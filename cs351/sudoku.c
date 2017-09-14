#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sudoku.h"

#define NUM_DIGITS 9
#define NUM_ROWS   NUM_DIGITS
#define NUM_COLS   NUM_DIGITS
#define NUM_PEERS  20
#define NUM_UNITS  3
#define DIGITS     "123456789"
#define ROW_NAMES  "ABCDEFGHI"
#define COL_NAMES  DIGITS

typedef struct square {
  char vals[NUM_DIGITS+1]; // string of possible values
  unsigned char row;
  unsigned char col;
  struct square *peers[NUM_PEERS];
  struct square *units[NUM_UNITS][NUM_DIGITS];
} square_t;

typedef struct puzzle {
  square_t squares[NUM_ROWS][NUM_COLS];
} puzzle_t;

void solve(unsigned char grid[9][9]);

// following are static ("private") function declarations --- add as needed

static puzzle_t *create_puzzle(unsigned char grid[9][9]);
static void init_peers(puzzle_t *puz, int row, int col);
static puzzle_t *copy_puzzle(puzzle_t *puz);
static void free_puzzle(puzzle_t *puz);
static void print_puzzle(puzzle_t *);

static puzzle_t *search(puzzle_t *puz);
static void del_char(char *str, char c);
static puzzle_t *assign(puzzle_t *puz, int row, int col, char val);
static puzzle_t *eliminate(puzzle_t *puz, int row, int col, char val);

/*************************/
/* Public solve function */
/*************************/

void solve(unsigned char grid[9][9]) {
  puzzle_t *puz = create_puzzle(grid);
  puzzle_t *solved;
  if ((solved = search(puz)) != NULL) {
    print_puzzle(solved);
  }
  free_puzzle(solved);
}

/*******************************************/
/* Puzzle data structure related functions */
/*******************************************/

//uses assign which uses eliminate
static puzzle_t *create_puzzle(unsigned char vals[9][9]) {
  puzzle_t *puz = malloc(sizeof(puzzle_t));
  square_t *sq;
  int i, j;
  for (i=0; i<NUM_ROWS; i++){
    for (j=0; j<NUM_COLS; j++){
      sq = &(puz->squares[i][j]);
      strcpy(sq->vals, DIGITS);
      sq->row = i;
      sq->col = j;
      init_peers(puz, sq->row, sq->col);      // call init_peers
    }
  }
  
  if (vals) { // if we pass in a NULL for vals, we don't do C.P.
    for (i = 0; i<NUM_ROWS; i++){
      for (j=0; j<NUM_COLS; j++){
        (assign(puz, i, j, vals[i][j]));// initial constraint propagation
      }
    }
  }
  
  return puz;
}

//init units in here as well!
static void init_peers(puzzle_t *puz, int row, int col) {
  int i, j, peer_idx=0;
  square_t *sq = &(puz->squares[row][col]);

  // initialize the row-wise unit
  for (i=0; i<NUM_COLS; i++){
    sq->units[0][i] = &(puz->squares[row][i]); // points to all the cols in the row
    if (i!=col) {
      sq->peers[peer_idx++] = &(puz->squares[row][i]);
    }    
  }

  // init the col-wise unit
  for (i=0; i<NUM_ROWS; i++){
    sq->units[1][i] = &(puz->squares[i][col]); // points to all the rows in the col
    if (i!=row) {
      sq->peers[peer_idx++] = &(puz->squares[i][col]);
    }
  }
  //init the box unit that the square belongs to 
  // Need to map:
  // 0-2 -> 0
  // 3-5 -> 3
  // 6-8 -> 6
  // working formula: (x / 3) * 3 <-- gives us "lower bound" of the box
  int r = (row/3)*3, c = (col/3)*3, unit_idx = 0;
  for (i=0; i<3; i++){// for rows
    for (j=0; j<3; j++){// for cols
      sq->units[2][unit_idx] = &(puz->squares[r+i][c+j]);
      unit_idx++;
      if ((r+i!=row) && (c+j!=col)) {
	sq->peers[peer_idx++] = &(puz->squares[r+i][c+j]);
      }
    }
  }
}

static void free_puzzle(puzzle_t *puz) {
  free(puz);
}

static puzzle_t *copy_puzzle(puzzle_t *src) {
  puzzle_t *dest = create_puzzle(NULL); // make a new puzzle to store copy
  int i, j;
  for(i=0; i<NUM_ROWS; i++){
    for(j=0; j<NUM_COLS; j++){
      // copy the vals from square in src puzzle to the dest
      strcpy(dest->squares[i][j].vals, src->squares[i][j].vals);
    }
  }
  return dest;
}

void print_puzzle(puzzle_t *p) {
  int i, j;
  for (i=0; i<NUM_ROWS; i++) {
    for (j=0; j<NUM_COLS; j++) {
      //printf(" %9s", p->squares[i][j].vals); // may be useful while debugging
      printf(" %2s", p->squares[i][j].vals);
    }
    printf("\n");
  }
}

/**********/
/* Search */
/**********/

static puzzle_t *search(puzzle_t *puz) {
  puzzle_t  *cpzl, *rval;
  square_t *sq, *found = NULL;
  int i, j, k, min = 9;
  char val;
  for(i=0; i<NUM_ROWS; i++){
    for(j=0; j<NUM_COLS; j++){
      sq = &(puz->squares[i][j]);
      if(strlen(sq->vals)==0){
  	return NULL;
      }
    }
  }
  //find the square with min (len(vals)>1)
  for(i=0; i<NUM_ROWS; i++){
    for(j=0; j<NUM_COLS; j++){
      sq = &(puz->squares[i][j]);
      if(strlen(sq->vals)>1){
  	if(min>strlen(sq->vals)){
  	  min = strlen(sq->vals);
  	  found = sq;
  	}
      }
    }
  }
  if(found==NULL){
    return puz;
  }else if(found!=NULL){
    for(k=0; k<strlen(sq->vals); k++){
      val = found->vals[k];
      cpzl = copy_puzzle(puz);
      rval = search(assign(cpzl, found->row, found->col, val));
      if(rval!=NULL){
      /* 	if (rval != cpzl){ */
      /* 	free_puzzle(cpzl); */
      /* } */
	return rval;
      }else free_puzzle(cpzl);
    }
  }
  //  free_puzzle(puz);
  return cpzl;
}

/**************************/
/* Constraint propagation */
/**************************/

static puzzle_t *assign(puzzle_t *puz, int row, int col, char val) {
  square_t *sq = &(puz->squares[row][col]); // val is == to something like '5'
  char rest[NUM_DIGITS+1];
  int i;
  if(strlen(sq->vals)==1){
    for(i=0; i<NUM_PEERS; i++){
      if(strchr(sq->peers[i]->vals, val)){  //use init_peers function
	if(strlen(sq->peers[i]->vals)==1){
	  return NULL;
	}else eliminate(puz, sq->peers[i]->row, sq->peers[i]->col, val);
      }
    }
  }
  if(strchr(sq->vals, val)){
    strcpy(rest, sq->vals); // we have a copy of the vals
    del_char(rest, val); // now it only has the remaining values
    for(i=0; i<(strlen(rest)); i++){
      eliminate(puz, row, col, rest[i]);
    }
  }
  return puz;
}

static puzzle_t *eliminate(puzzle_t *puz, int row, int col, char val) {
  square_t *sq = &(puz->squares[row][col]), *places;
  int i, j, count=0, placesRow, placesCol;
  if(!strchr(sq->vals, val)){
    return puz;
  }else {
    del_char(sq->vals, val);
  }

  if(strlen(sq->vals)==0){
    return NULL;
  }else if(strlen(sq->vals)==1){
    assign(puz, row, col, sq->vals[0]);
  }

  for(i=0; i<NUM_UNITS; i++){
    for(j=0; j<NUM_DIGITS; j++){
      places = sq->units[i][j];
      if(strchr(places->vals, val)){ 
	count++;
	placesRow = places->row;
	placesCol = places->col;
      }
    }
  }
  if(count == 1){            //if there is only one occurrence
    assign(puz, placesRow, placesCol, val); //assign it to that square
  }
  return puz;
}

/*****************************************/
/* Misc (e.g., utility) functions follow */
/*****************************************/

static void del_char(char *str, char c) {  // "delete" the character c from the str
  char *src, *dst;                         // --- this entails shifting all
  for(src=dst=str; *src!='\0'; src++){     // the characters (including the null 
    *dst = *src;                           // terminator) over the character to
    if(*dst!=c){                           // delete; e.g. del_char("12345\0", '3')
      dst++;                               // => "1245\0"
    }
  }
  *dst = '\0';
}