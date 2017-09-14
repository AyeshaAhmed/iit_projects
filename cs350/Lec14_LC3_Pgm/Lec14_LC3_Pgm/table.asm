; CS 350, J. Sasaki, Illinois Institute of Technology
;
; table.asm
; Set table[k] = x where k and k are variables.
; We assume table entries take up one word each

; Register usage: R0 = &table[0], R1 = &table[k]. R2: temp
;
         .ORIG  x8000

; To make R0 = &table[0], we can always use the LD
; command below. If the table is close by, then the LEA
; will also work
          LD    R0, tablePtr    ; pt R0 to table[0]
; or      LEA   R0, table       ; pt R0 to table[0]

; Make R2 = &table[k]
          LD    R2, k           ; R2 = k
          ADD   R1, R0, R2      ; pt R1 to table[k]

; Set table[k] = x
          LD    R2, x           ; R2 = value
          STR   R2, R1, 0       ; table[k] = value

          HALT

k        .FILL  4               ; index into table
x        .FILL  -1              ; value to copy into table

; tablePtr is a constant that contains the address of
; table[0]. If we are using the LEA of table, then
; tablePtr isn't necessary [but it must be declared
; close to the LD of tablePtr]
;
tablePtr .FILL  table           ; &table[0]

; We can make table be far away from the LEA by
; uncommenting the BLKW below
;
;        .BLKW  256

table    .BLKW  100             ; space for table[0..99]
         .END
