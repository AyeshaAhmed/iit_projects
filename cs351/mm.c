//DMA lab - Ayesha Ahmed CS351 Summer2015

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <unistd.h>

#include "mm.h"
#include "memlib.h"

/* single word (4) or double word (8) alignment */
#define ALIGNMENT 8

/* rounds up to the nearest multiple of ALIGNMENT */
#define ALIGN(size) (((size_t)(size) + (ALIGNMENT-1)) & ~0x7)
#define SIZE_T_SIZE (ALIGN(sizeof(size_t)))

// MACROS -- IN ALL CAPS
// constants in byte size
#define WORDS    4	  
#define DOUBLES  8   // double word
#define IHEAPS   16  // initial heap
#define MINBLKS  24  // minimum block size

// read/write a word in address
#define READ(a)       (*(int *)(a))
#define WRITE(a, val)  (*(int *)(a) = (val))

// Read the size and allocated bit from address
#define READ_SIZE(a)  (READ(a) & ~0x7)
#define READ_ALLOC(a) (READ(a) & 0x1)

// using block ptr find address of its header and footer
#define HEAD(ptr)       ((void *)(ptr) - WORDS)
#define FOOT(ptr)       ((void *)(ptr) + READ_SIZE(HEAD(ptr)) - DOUBLES)

// find maximum of x or y
#define MAXIMUM(x, y) ((x) > (y) ? (x) : (y))

// fill a word with size and allocated bit
#define FILL(size, bit)  ((size) | (bit))

// using block ptr find address of next and previous blks
#define NEXT_BLK(ptr)  ((void *)(ptr) + READ_SIZE(HEAD(ptr)))
#define PREV_BLK(ptr)  ((void *)(ptr) - READ_SIZE(HEAD(ptr) - WORDS))

// using block ptr find address of next and previous free blks
#define NEXT_FREE(ptr)(*(void **)(ptr + DOUBLES))
#define PREV_FREE(ptr)(*(void **)(ptr))

// global array of pointers to doubly linked lists
  static char *heap_l = 0; // Pointer to the first block
  static char *free_l = 0; // Pointer to the first free block
  static char *search_l = 0;
  int free_lists = 0;

static void *add_heap(size_t size);
static void *find_fit(size_t size);
static void place_block(void *ptr, size_t size);
static void *coalesce(void *ptr);
static void front_heap(void *ptr); // linked list
static void delete_block(void *ptr); // linked list

/*
* mm_init - Initialize the malloc package.
*/
int mm_init(void){
  //if heap alloc fails return -1 
  if ((heap_l = mem_sbrk(2 * MINBLKS)) == NULL)
    return -1;
  
  WRITE(heap_l, 0); //padding
  WRITE(heap_l + WORDS, FILL(MINBLKS, 1));//empty header
  WRITE(heap_l + DOUBLES, 0);
  WRITE(heap_l + DOUBLES + WORDS, 0);
  WRITE(heap_l + MINBLKS, FILL(MINBLKS, 1));//empty footer
  WRITE(heap_l + WORDS + MINBLKS, FILL(0, 1));//empty tail

  //free list pointer to the tail
  free_l = heap_l + DOUBLES;
  search_l = free_l;//points to addr of first block

  //extend empty heap with free block of init heap
  if (add_heap(IHEAPS / WORDS) == NULL)
    return -1;
  return 0;
}

static void *add_heap(size_t size){
  char *ptr;
  size_t mysize;

  //even number to maintain alignment
  mysize = (size % 2) ? (size + 1) * WORDS : size * WORDS;
  
  if (mysize < MINBLKS)
    mysize = MINBLKS;
  if ((long)(ptr = mem_sbrk(mysize)) == -1)
    return NULL;

  // init new empty block
  WRITE(HEAD(ptr), FILL(mysize, 0));
  WRITE(FOOT(ptr), FILL(mysize, 0));
  WRITE(HEAD(NEXT_BLK(ptr)), FILL(0, 1));

  // coalesce if the previous block was free
  return coalesce(ptr);
}

/*
* mm_malloc - Allocate a block by incrementing the brk pointer.
*     Always allocate a block whose size is a multiple of the alignment.
*/
void *mm_malloc(size_t size){
                     // find the bigger size for allignment
  size_t newsize = MAXIMUM(ALIGN(size) + DOUBLES, MINBLKS);
  size_t newfit;
  char *blkp;

  // bad size
  if (size <= 0)
    return NULL;

  // quick search
  // search free linked list for a fit
  if ((blkp = find_fit(newsize))){
    place_block(blkp, newsize);
     return blkp;
  }

  // if no fit, then alloc memory
  // find the bigger size for allignment
  newfit = MAXIMUM(newsize, IHEAPS);

  //if heap alloc failed return NULL 
  if ((blkp = add_heap(newfit / WORDS)) == NULL)
    return NULL;

  place_block(blkp, newsize);
  return blkp;
}

static void *find_fit(size_t size){
  void *ptr;
  if(free_lists>1000){
    for(ptr=search_l; READ_ALLOC(HEAD(ptr)) == 0; ptr = NEXT_FREE(ptr)){
      if(size <= (size_t)READ_SIZE(HEAD(ptr)))
	return ptr;
    }
    for(ptr=free_l; HEAD(ptr) == PREV_FREE(search_l); ptr = NEXT_FREE(ptr)){
      if(size <= (size_t)READ_SIZE(HEAD(ptr))){
	search_l = NEXT_FREE(ptr);
	return ptr;
      }
    }
  } else {
    for(ptr=free_l; READ_ALLOC(HEAD(ptr)) == 0; ptr = NEXT_FREE(ptr)){
      if(size <= (size_t)READ_SIZE(HEAD(ptr)))
	return ptr;
    }
  }
  return NULL; 
}

static void place_block(void *ptr, size_t size){
  //size of free block
  size_t copySize = READ_SIZE(HEAD(ptr));

  if ((copySize - size) >= MINBLKS) {
    WRITE(HEAD(ptr), FILL(size, 1));
    WRITE(FOOT(ptr), FILL(size, 1));
    delete_block(ptr);
    ptr = NEXT_BLK(ptr);
    WRITE(HEAD(ptr), FILL(copySize - size, 0));
    WRITE(FOOT(ptr), FILL(copySize - size, 0));
    coalesce(ptr);
  }
  //don't split the block if there's no room
  else {
    WRITE(HEAD(ptr), FILL(copySize, 1));
    WRITE(FOOT(ptr), FILL(copySize, 1));
    delete_block(ptr);
  }
}

/*
* mm_free - Freeing a block does nothing.
*/
void mm_free(void *ptr){
  size_t size = READ_SIZE(HEAD(ptr));

  //check NULL pointer
  if (ptr == NULL) 
    return; 
  //unallocate header and footer
  WRITE(HEAD(ptr), FILL(size, 0));
  WRITE(FOOT(ptr), FILL(size, 0));

  //coalesce all free blocks and put in list
  coalesce(ptr); 
}

static void *coalesce(void *ptr){
  size_t prev_alloc = READ_ALLOC(FOOT(PREV_BLK(ptr))) || PREV_BLK(ptr) == ptr;
  size_t next_alloc = READ_ALLOC(HEAD(NEXT_BLK(ptr)));
  size_t size = READ_SIZE(HEAD(ptr));

  // Case 1
  if (prev_alloc && !next_alloc){
    size += READ_SIZE(HEAD(NEXT_BLK(ptr)));
    delete_block(NEXT_BLK(ptr));
    WRITE(HEAD(ptr), FILL(size, 0));
    WRITE(FOOT(ptr), FILL(size, 0));
  }

  // Case 2
  else if (!prev_alloc && next_alloc){
    size += READ_SIZE(HEAD(PREV_BLK(ptr)));
    ptr = PREV_BLK(ptr);
    delete_block(ptr);
    WRITE(HEAD(ptr), FILL(size, 0));
    WRITE(FOOT(ptr), FILL(size, 0));
  }

  /* Case 3, extend the block in both directions */
  else if (!prev_alloc && !next_alloc){
    size += READ_SIZE(HEAD(PREV_BLK(ptr))) + READ_SIZE(HEAD(NEXT_BLK(ptr)));
    delete_block(PREV_BLK(ptr));
    delete_block(NEXT_BLK(ptr));
    ptr = PREV_BLK(ptr);
    WRITE(HEAD(ptr), FILL(size, 0));
    WRITE(FOOT(ptr), FILL(size, 0));
  }

  front_heap(ptr);

  return ptr;
}

static void front_heap(void *ptr){
  NEXT_FREE(ptr) = free_l; 
  PREV_FREE(free_l) = ptr;
  PREV_FREE(ptr) = NULL;
  free_l = ptr;
  free_lists += 1;
}

static void delete_block(void *ptr){
  if (PREV_FREE(ptr))
    NEXT_FREE(PREV_FREE(ptr)) = NEXT_FREE(ptr);
  else
    free_l = NEXT_FREE(ptr);
  PREV_FREE(NEXT_FREE(ptr)) = PREV_FREE(ptr);
  free_lists -= 1;
}

/*
* mm_realloc - Implemented simply in terms of mm_malloc and mm_free
*/
void *mm_realloc(void *ptr, size_t size){
  void *oldptr = ptr;
  void *newptr;
  size_t copySize;
  
  newptr = mm_malloc(size);
  if (newptr == NULL)
    return NULL;
  copySize = *(size_t *)((char *)oldptr - SIZE_T_SIZE);
  if (size < copySize)
    copySize = size;
  memcpy(newptr, oldptr, copySize);
  mm_free(oldptr);
  return newptr;
}
