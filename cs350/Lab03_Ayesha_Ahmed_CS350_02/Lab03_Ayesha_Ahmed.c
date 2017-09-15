// CS 350, Spring 2015
// Lab 3 -- Program for argc/argv and file I/O
// Ayesha Ahmed CS_350_02
// IIT
//
#include <stdio.h>
#define ARRAYLEN 32

int main (int argc, char *argv[])
{
  printf("Lab03 Ayesha Ahmed CS_350_02\n\n");
  printf("The command line had %d part(s)\n", argc);
  int i;
  for (i = 0; i < argc; i++)
    printf("argv[%d] = %s\n", i, argv[i]);

  char *filename;          // file to read from
  FILE *in_file;
  if (filename = argv[1]){
    in_file = fopen(filename, "r"); // NULL if the open failed
  }else filename = "default.txt";
        in_file = fopen(filename, "r");
	printf("No file specified, reading from: %s\n", filename);
  if (in_file == NULL) {
    printf("Couldn't open file %s\n", filename);
    return 1;
  }
  
  int nbr_values_read;    // # items read by call of fscanf
  int value_read;
  int right, left;
  int Ones, column = 0;
  int mask = 0x00000000;
  int bitset, cleared, flipped;
  unsigned int selectedBits, rightAligned;
  
  nbr_values_read = fscanf(in_file, "%x" "%d" "%d", &value_read, &right, &left);
  while (nbr_values_read > 0) {
    printf("\n");
    printf("vlaue \t\t0x%x = %d\n", value_read, value_read);
    //create mask
    Ones = left - right + 1;
    mask = ((1<<Ones) - 1)<<right;
    selectedBits = mask & value_read;
    rightAligned = selectedBits>>right;
    printf("mask \t\t0x%08x, bits %d:%d\n", mask, right, left);
    printf("selected bits \t0x%08x, right-aligned 0x%x\n", selectedBits, rightAligned);
    //calculate bitset, cleared, and flipped
    bitset = mask | value_read;
    cleared = ~mask & value_read;
    flipped = mask ^= value_read;
    printf("bits set \t0x%08x\n", bitset);
    printf("bits cleared \t0x%08x\n", cleared);
    printf("bits flipped \t0x%08x\n", flipped);
    //grab next and reset
    nbr_values_read = fscanf(in_file, "%x" "%d" "%d", &value_read, &right, &left);
    mask = 0x00000000;
  }
  int close_err = fclose(in_file);
  if(close_err){
    printf("File failed to close!\n");
  }
  return 0;
}
