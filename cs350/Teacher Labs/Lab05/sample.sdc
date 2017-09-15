Sample SDC program
Input we ignore: Blank lines, leading whitespace, the rest of
the line after the instruction, and lines that don't start with
a number.

We stop reading the file if we hit a sentinel (value too big for memory)

 5178	00	LDM R1, 78
-5278	01	LDM R2, -78
 6189	02	ADM R1, 89
-6289	03	ADM R2, -89
 2145	04	ST R1, 45
 1345	05	LD R3, 45
 3345	06	ADD R3, 45
 4367	07	NEG R3
 7810	08	BR 10 		skip over infinite loop
 7009	09	BR 09 		infinite loop; should be skipped
 8112	10	BRC R1, 12 	skip over infinite loop
 7011	11	BR 11		infinite loop
-8214	12	BRC -R2, 14	skip over infinite loop
 7013	13	BR 13		infinite loop
 9011	14	GETC
 9199	15	PUTC
 9221	16	PUTS 21
 9345	17	DCU
-9455	18	DMEM		should ignore sign here
 9500	19	NOP
 0000	20	HALT
 97		21	a	String to print
 65		22	A
 48		23	0
 0		24	\0

10000	If a value is > 9999 or < -9999 we stop reading
1234	so this should be ignored