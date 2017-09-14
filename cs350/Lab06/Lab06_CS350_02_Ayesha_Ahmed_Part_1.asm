; Lab06 CS_350_02 Ayesha Ahmed Part 1
; This is an LC3 program that left-shifts
; 	a value from one word to another.
; Variables and their meanings:
; R0 - R
; R1 - L
; R2 - I

	.ORIG 	x3000 		; Start program at line x3000
	LD 	R0, X				; R0 is R 
	LD	R1, L				; R1 is L (0)
	LD 	R2, N				;Store I into R2 to keep track 							;	of shifts left to do
	LD	R5, P				;Store 1 into R5
	NOT	R5, R5			;Store the NOT of 1 into R5
	
  Loop	BRZ	Done 			;While I>0
	ADD 	R1, R1, R1			;Left-shift L by multiplying
	ADD	R0, R0, 0			;inspect the left-most bit of R
	BRZP	NOTneg			;If leftt-most bit of R is 1
	NOT 	R3, R1			;Take the not of L to begin OR
	AND	R4, R3, R5		;AND L with the not of 1 and store it 
	NOT 	R1, R4		;Finish OR by using Demorgan setting 						; 	right most bit of L to 1 
			
NOTneg	ADD 	R0, R0, R0		;Left-shift R one bit
	ADD	R2, R2, -1			;decrement the count
	BR	Loop				;end while loop

	
  Done	ST	R1, L			;store L into memory
	ST	R0, R  			;store R into memory
	HALT					;end program

  X 	 .Fill 	  xFFFF	;This is the number to be shifted
  N	 .Fill 	  5		;number of shifts to be done
  P	 .Fill	  x0001	;setting P = 1 for compare purposes
  R	 .Blkw	  1		;initializing R to 0
  L	 .Blkw	  1		;initilizing L to 0

	 .END				;Tell assembler this ends file.