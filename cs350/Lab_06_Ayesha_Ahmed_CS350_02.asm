; Lab_06 CS_350_02
; Ayesha Ahmed
;
; This program left shifts a number a certain amount of times.

	.ORIG x3000	; Start program at line 3000
	LD R2, L	;L <- 0
		;R <- X
		;I <- N (I = the number of shifts yet to do)
Loop LDR 		;while I > 1
		; Left-shift L one bit with zero fill
		; Inspect the leftmost bit of R
		; if the leftmost bit of R is 1
		; Set the rightmost bit of L to 1
		; Left-shift R one bit with zero fill
		; --I
		;end while
		;Store L into memory
		;Store R into memory
X .Fill xFFFF
N .Fill 5
L .BLKW 1
R .BLKW 1