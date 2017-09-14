; CS 350, J. Sasaki, Illinois Institute of Technology
;
; readstring.asm
; Read and echo characters until we see a return. (Also echo
; the return.)  Store the characters (but not the return) as
; a string.
;
; Pseudocode:
; buffer_posn = &buffer	(the characters we read will
;     go into a buffer; buffer_posn points to our
;     position within the buffer (the location to store
;     the next character into).
;
; Print "Enter chars (return to halt): "
; Read char into R0
; Calculate R0 - return char
; until R0 - return char = 0
;    Print char in R0
;    *buffer_posn = R0
;    buffer_posn++
;    Read char into R0
;    Calculate R0 - return char
; end loop
; Print the return character
; *buffer_posn = null char to end the string
; Print the string
; Halt
;
; Register usage
;    R0 = GETC/OUT char, R1 = buffer_posn,
;    R2 = -(return char), R3 = temp
;
        .ORIG    x3000
         LEA     R1, buffer    ; buffer_posn = &buffer
         LEA     R0, msg
         PUTS                  ; prompt for input
         GETC                  ; get char into R0
         LD      R2, retChar   ; R2 = return char
         NOT     R2, R2        ; R2 = -(return char) - 1
         ADD     R2, R2, 1     ; R2 = -(return char)
         ADD     R3, R0, R2    ; calculate R0 - return char
Loop     BRZ     Done          ; until r0 = return char
         OUT                   ; print char in R0
         STR     R0, R1, 0     ; *buffer_posn = char read in
         ADD     R1, R1, 1     ; buffer_posn++
         GETC                  ; get char into R0
         ADD     R3, R0, R2    ; calc char - return char
         BR      Loop          ; continue loop
Done     OUT                   ; print return char in R0
         AND     R3, R3, 0     ; R3 = null char ('\0')
         STR     R3, R1, 0     ; terminate string in buffer
         LEA     R0, buffer
         PUTS                  ; print the string we read in
         HALT

retChar .FILL    x0A           ; Return character (\n)
msg     .STRINGZ "Enter chars (return to halt): "
buffer  .BLKW    100           ; buffer space for string
        .END