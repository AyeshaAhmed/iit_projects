; CS 350, J. Sasaki, Illinois Institute of Technology
;
; multiply.asm
;
; Set product = X * Y (where Y >= 0), using repeated addition.

; Pseudocode:
;
; Property: product = X*(Y-k) and 0 <= k <= Y
;    (So when k = 0, product = X*Y)
;
;    product = 0               ; Initialize product and k
;    k = Y
;    until k = 0
;       product += X
;       k--
;
; Register usage: R1 = k, R2 = X, R3 = product
;
        .ORIG    x3050
         LD      R2, X         ; R2 = X
         AND     R3, R3, 0     ; R3 = X * (Y-k)
         LD      R1, Y         ; k = Y
Loop     BRZ     Done          ; until k = 0
         ADD     R3, R3, R2    ;    R3 = R3 + X
         ADD     R1, R1, -1    ;    k--
         BR      Loop
Done     ST      R3, product   ; product = X*Y
         HALT

X       .FILL    16
Y       .FILL    6
product .BLKW    1             ; Holds X*Y at end
        .END
