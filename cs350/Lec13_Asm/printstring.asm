; CS 350, J. Sasaki, Illinois Institute of Technology
;
; printstring.asm
;
; Given: R0 points to first word of string.
; At end: We’ve printed the string.
; Temporary register: R2
;
       .ORIG    x3000       ; (Start program at x3000)
        LEA     R0, string  ; Pt R0 to string to print

        ADD     R2, R0, 0   ; R2 = &current char to print
Loop    LDR     R0, R2, 0   ; R0 = curr char to print
        BRZ     Done        ; (BRZ 3) Loop until we see '\0'
        OUT                 ; (TRAP x21) print char in R0
        ADD     R2, R2, 1   ; Pt R2 to next char
        BR      Loop        ; (BR -5) Continue loop
Done    HALT                ; (TRAP x25) Halt execution

string .STRINGZ "Hello, world!"
       .END               ; Tell assembler this ends the file