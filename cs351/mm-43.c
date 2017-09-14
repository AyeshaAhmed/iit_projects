//DMA lab - Ayesha Ahmed CS351 Summer2015

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <unistd.h>

#include "mm.h"
#include "memlib.h"

typedef struct header{
  size_t size;
  struct header *next_h;
  struct header *prior_h;
}blockH;

//global array of pointers to doubly linked free lists
blockH *free_lists;

/* single word (4) or double word (8) alignment */
#define ALIGNMENT 8

/* rounds up to the nearest multiple of ALIGNMENT */
#define ALIGN(size) (((size_t)(size) + (ALIGNMENT-1)) & ~0x7)
#define SIZE_T_SIZE (ALIGN(sizeof(size_t)))

#define BLK_HDR_SIZE ALIGN(sizeof(blockH))
//#define BLK_FTR_SIZE ALIGN(sizeof(blockF))


#define NUM_SIZE_CLASSES 5
size_t min_class_size[] = { BLK_HDR_SIZE, 64, 128, 256, 1024 };


/* typedef struct footer{ */
/*   size_t size; */
/*   struct footer *next_f; */
/*   struct footer *prior_f; */
/* }blockF; */

void *find_fit(size_t size);
void try_split(blockH *blockp, size_t needed);
void print_heap();
//void *coalesce(size_t *blockp);

/* 
 * mm_init - initialize the malloc package.
 */
int mm_init(void){
  blockH *bhp = mem_sbrk(BLK_HDR_SIZE);
  bhp->size = BLK_HDR_SIZE;
  bhp->next_h = bhp;
  bhp->prior_h = bhp;
  
  int i;
  free_lists = mem_sbrk(NUM_SIZE_CLASSES * sizeof(blockH));
  for(i=0; i<NUM_SIZE_CLASSES; i++){
    free_lists[i].size = 0;
    free_lists[i].next_h = free_lists[i].prior_h = &free_lists[i];
  }

  /* blockF *bfp = mem_sbrk(BLK_FTR_SIZE); */
  /* bfp->size = BLK_FTR_SIZE; */
  /* bfp->next_f = bfp; */
  /* bfp->prior_f = bfp; */
  return 0;
}

/* 
 * mm_malloc - Allocate a block by incrementing the brk pointer.
 *     Always allocate a block whose size is a multiple of the alignment.
 
 */

void *mm_malloc(size_t size){
  int newsize = ALIGN(BLK_HDR_SIZE + size);
  blockH *bhp = find_fit(newsize);
  
  if(bhp == NULL){
    bhp = mem_sbrk(newsize);
    if((long)bhp == -1)
      return NULL;
    else
      bhp->size = newsize | 1;
  }
  //trying to implement split
  /* if(bhp && newsize < bhp->size){ */
  /*     bhp->size = bhp->size - newsize; */
  /* } */
  else {
    bhp->size |= 1;
    bhp->prior_h->next_h = bhp->next_h;//linked list
    bhp->next_h->prior_h = bhp->prior_h; //removal
  }
  return (char *)bhp + BLK_HDR_SIZE;
}

void *find_fit (size_t size){
  blockH *bhp;
  int i, found = 0;

  //find first
  for(i=0; i<NUM_SIZE_CLASSES; i++){
    if(min_class_size[i] >= size && free_lists[i].next_h != &free_lists[i]){
      found = 1;
      //take the first block
      bhp = free_lists[i].next_h;
      //remove it from the free list
      free_lists [i].next_h = bhp->next_h;
      bhp->next_h->prior_h = &free_lists[i];
      //and try to split it
      try_split(bhp, size);
      return bhp;
    }
  }
  if (!found){
    for (bhp = ((blockH *)mem_heap_lo())->next_h;
	 bhp != mem_heap_lo() && bhp->size < size;
	 bhp = bhp->next_h);

    if(bhp != mem_heap_lo())
      //deferred coalescing
      // merge with next block if free
      /* if(bhp->next_h < mem_heap_lo() && !(bhp->next_h->size & 1)){ */
      /*   bhp->size += next->size; */
      /*   continue; */
      /* } */
      return bhp;
  }
    return NULL;
}

void try_split(blockH *blockp, size_t needed){
  int i, remaining = blockp->size - needed;
  blockH *bhp;
  
  if(remaining < BLK_HDR_SIZE)
    return;
  
  //split the block
  blockp->size = needed;
  bhp = (blockH *)((char *)blockp + needed);
  bhp->size = remaining;

  //and putt the leftover free block in the correct list
  for(i=NUM_SIZE_CLASSES-1; i>0; i--){
    if(min_class_size[i] <= remaining){
      bhp->prior_h = &free_lists[i];
      bhp->next_h = free_lists[i].next_h;
      free_lists[i].next_h = free_lists[i].next_h->prior_h = bhp;
      break;
    }
  }
}

/* void *coalesce(blockH *freeb){ */
/*   blockH bhp; */
/*   int next_alloc = , prev_alloc; */

/*   //edge cases */
/*   if(mem_heap_hi < freeb){ */
/*     bhp->prior_h = freeb - ((freeb - BLK_HDR_SIZE) & ~1L); */
/*     prev_alloc = bhp->prior_h & 1; */
/*   } else { */
/*     prev_alloc = 1; */
/*   } */

/*   if(mem_heap_lo > freeb){ */
/*     bhp->next_h = freeb - ((freeb - BLK_HDR_SIZE) & ~1L); */
/*     next_alloc = bhp->next_h & 1; */
/*   } else { */
/*     next_alloc = 1; */
/*   } */
    
/* } */

/*
 * mm_free - Freeing a block does nothing.
 */
void mm_free(void *ptr)
{
  blockH *bhp = ptr - BLK_HDR_SIZE, 
         *head = mem_heap_lo();
  //add freed block to free list after head
  bhp->size &= ~1;
  bhp->next_h = head->next_h; //immediate coalescing
  bhp->prior_h = head;
  head->next_h = bhp;
  bhp->next_h->prior_h = bhp;
  
  // coalesce : requires adding footers.
}

/*
 * mm_realloc - Implemented simply in terms of mm_malloc and mm_free
 */
void *mm_realloc(void *ptr, size_t size)
{
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

void print_heap(){
  blockH *bhp = mem_heap_lo();
  while(bhp < (blockH *)mem_heap_hi()){
    printf("%s block ar %p, size %d\n", (bhp->size&1)?"allocated":"free", bhp, (int)(bhp->size & ~1));
  }
}
