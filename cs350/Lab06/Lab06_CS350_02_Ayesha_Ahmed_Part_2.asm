; Lab06 CS_350_02 Ayesha Ahmed Part2
; This is an LC3 program that searches for
; the occurences of a character within a string.
; Register Assignments
; R0 - strings to get or print out (for GETC or OUT)
; R1 - Temporary register
; R2 - char to search for (variable s in Lab handout)
; R3 - counter for found chars
; R4 - pointer to Array of size 100
; R5 - value of zero
; R6 - negation of newline character (ASCII x10)

	.ORIG 	x3000 		; Start program at line x3000
	LEA 	R0, pSTR	; R0 has char prompt
	PUTS			; prints out string in R0 - to prompt for char
	GETC			; read char into R1
	OUT			; print out char in R0

	ADD	R2, R0, 0	; R1 = char to search for
	NOT 	R2, R2		; taking 1'sC nagative of R1
	ADD	R2, R2, 1	; add 1 to get 2'sC

	LEA	R4, Array	; Pointer to Array of size 100	
	AND	R3, R3, 0	; initialize count to zero - store 0 in R3

	LD 	R6, N		; load R6 with newline char
	NOT 	R6, R6		; take 1'sC of newline (negate)
	ADD	R6, R6, 1	; take 2'sC of newline

	LD	R0, N		; put newline char in R0
	OUT			; output newline

	LEA	R0, sSTR	; R0 has string prompt
	PUTS			; prints out string in R0 - to prompt for string

  Loop	GETC			; read string into R4
	OUT			; echos out char in R4
	STR	R0, R4, 0	; puts first char into 0th element of R4
	ADD	R4, R4, 1	; increment R4 array location
	ADD	R1, R0, R2	; compare search char (R2) to current string char (R0) store in temp (R1)
	BRNP	stop		; will skip one line of code if False
	ADD	R3, R3, 1	; increment count of found chars
  stop	ADD 	R0, R0, R6	; check if char in R0 is newline?

	BRNP	Loop		; branch until we get '\0'

	LD	R1, -9		; store value -9 into temp (R1)
	ADD	R1, R3, -9	; check if count is 9
	BRP	else		; branch if count > 9
	LD 	R5, Zero	; store char of 0 (x30) in R5
	ADD	R1, R5, R3	; '0' + count = 'count'
	LEA	R0, count	; load count string
	PUTS			; print count string
	ADD	R0, R1, 0	; copy count number
	OUT			; print 1 char of count number
	BR	Done		; if done skip else branch

  else	LEA	R0, errSTR	; load too many occur string
	PUTS			; print out error string in R0

  Done	HALT			; stop program


  Nine	 .Fill	  -9		; value -9
  Zero	 .Fill 	  x30		; char for zero
  N	 .Fill	  10		; 10 = newline char
  pSTR	 .STRINGZ "Enter a char to search for: "	; char prompt string
  sSTR	 .STRINGZ "Enter string to search through: "	; string prompt 
  count	 .STRINGZ "The count was: "			; count string
  errSTR .STRINGZ "There wer more than 9 occurrences"	; More than 9 string
  Array  .BLKW	  100
	 .END			; Tell assembler this ends the file.