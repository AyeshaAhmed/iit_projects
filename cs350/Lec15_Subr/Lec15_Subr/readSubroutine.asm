; readSubroutine.asm
;
; The main program exercises the readline subroutine.
;
          .ORIG     x3000
           LEA      R0, string1   ; read first message
           JSR      readstring

           LEA      R0, string2   ; read second message
           JSR      readstring

           HALT

string1   .BLKW     100
string2   .BLKW     100


; readstring: Reads a return-terminated string into a
; buffer pointed to by R0.  Uses the same pseudocde as in
; readstring.asm
;
; This routine assumes R0 points to the buffer into which
; to read the string.  It saves and restores all registers. 

; Save internally used registers:
;    R0 = GETC/OUT char, R1 = buffer_posn,
;    R2 = -(return char), R3 = temporary
readstring ST       R0, RSsave0   ; Save R0
           ST       R1, RSsave1   ; Save R1
           ST       R2, RSsave2   ; Save R2
           ST       R3, RSsave3   ; Save R3
           ST       R7, RSsave7   ; Save R7

           ADD      R1, R0, 0     ; buffer_posn = &buffer
           LEA      R0, RSmsg     ; get prompt message
           PUTS                   ; prompt for input
           GETC                   ; read char into R0
           LD       R2, RS_rc     ; R2 = return char
           NOT      R2, R2        ; R2 = -(return char) - 1
           ADD      R2, R2, 1     ; R2 = -(return char)
           ADD      R3, R0, R2    ; calculate R0 - return char
RSLoop     BRZ      RSDone        ; until char read = return
           OUT                    ; print char read in
           STR      R0, R1, 0     ; *buffer_posn = char read in
           ADD      R1, R1, 1     ; buffer_posn++
           GETC                   ; read next char
           ADD      R3, R0, R2    ; calc char - return char
           BR       RSLoop        ; continue loop
RSDone     OUT                    ; print the return char in R0
           AND      R3, R3, 0     ; get a null char ('\0')
           STR      R3, R1, 0     ; terminate buffer string
           LD       R0, RSsave0   ; point to bufer
           PUTS                   ; print the string we read in
           LD       R0, RS_rc     ; get a newline
           OUT                    ; end this line of output

; Restore registers and return
           LD       R7, RSsave7   ; Restore R7
           LD       R3, RSsave3   ; Restore R3
           LD       R2, RSsave2   ; Restore R2
           LD       R1, RSsave1   ; Restore R1
           LD       R0, RSsave0   ; Restore R0
           JMP      R7

RS_rc     .FILL     x0A           ; ASCII newline char
RSmsg     .STRINGZ  "Enter chars (then return): "

; Save area for registers
RSsave0   .BLKW     1             ; Save area for R0
RSsave1   .BLKW     1             ; Save area for R1
RSsave2   .BLKW     1             ; Save area for R2
RSsave3   .BLKW     1             ; Save area for R3
RSsave7   .BLKW     1             ; Save area for R7
          .END
