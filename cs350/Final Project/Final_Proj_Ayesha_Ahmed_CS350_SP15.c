// 
// *** Ayesha Ahmed CS350_02 FINAL PROJECT ***
// 

// CS 350, Spring 2015
// LC3 Simulator
//
// Illinois Institute of Technology, (c) 2014, James Sasaki

#include <stdio.h>
#include <stdlib.h>       // For error exit()

// CPU Declarations -- a CPU is a structure with fields for the
// different parts of the CPU.
//
	typedef short int Word;          // type that represents a word of SDC memory
        typedef unsigned short Address;  // type that represents an SDC address

        #define MEMLEN 65536
	#define NREG 8

	typedef struct {
		Word mem[MEMLEN];
		Word reg[NREG];      // Note: "register" is a reserved word
		Address pc;          // Program Counter
	        int running;         // running = 1 iff CPU is executing instructions
		Word ir;             // Instruction Register
	        int opcode;          //   opcode field
	        int cc;              //   Condition Code
	} CPU;

        FILE *fileName;
// Prototypes [note the functions are also declared in this order]
//
	int main(int argc, char *argv[]);
	void initialize_control_unit(CPU *cpu);
	void initialize_memory(int argc, char *argv[], CPU *cpu);
        void exec_HLT(CPU *cpu);
        void execute(CPU *cpu);
	FILE *get_datafile(int argc, char *argv[]);

        char cond_char(CPU *cpu);
	void dump_control_unit(CPU *cpu);
	void dump_memory(CPU *cpu);
	void dump_registers(CPU *cpu);

	int read_execute_command(CPU *cpu);
        int execute_command(char cmd_char, CPU *cpu, char *cmd_str);
	void help_message(void);
        int twos_comp (Word instr, int pos);
        int get_bits(Word instr, int left, int right);
	void many_instruction_cycles(int nbr_cycles, CPU *cpu);
	void one_instruction_cycle(CPU *cpu);

	
// Main program: Initialize the cpu, read initial memory values,
// and execute the read-in program starting at location 00.
//
int main(int argc, char *argv[]) {
        CPU cpu_value, *cpu = &cpu_value;
		
	printf("LC3 Simulator: CS 350 Final Project Ayesha Ahmed\n");
	get_datafile(argc, argv);
	initialize_control_unit(cpu);
	initialize_memory(argc, argv, cpu);

	char *prompt = "> ";
	printf("\nBeginning execution; type h for help\n%s", prompt);

	int done = read_execute_command(cpu);
	while (!done) {
		printf("%s", prompt);
		done = read_execute_command(cpu);
	}
	return 0;
}


// Initialize the control registers (pc, ir, running flag, condition code) and
// the general-purpose registers
//
void initialize_control_unit(CPU *cpu) {
	
  int num, i;
  for(num = 0; num < NREG; num++){
    cpu->reg[num] = 0;
  }
  for(i = 0; i < MEMLEN; i++){
    cpu->mem[i] = 0;
  }
  
  cpu->pc = 0;
  cpu->ir = 0;
  cpu->running = 1;
  cpu->cc = 0;

}

// Read and dump initial values for memory
//
void initialize_memory(int argc, char *argv[], CPU *cpu) {
	FILE *datafile = fileName;

	// Will read the next line (words_read = 1 if it started
	// with a memory value). Will set memory location loc to
	// value_read
	//
	int words_read, done = 0;
	Word value_read;
	Address loc = 0;
	//
	// Each getline automatically reallocates buffer and
	// updates buffer_len so that we can read in the whole line
	// of input. bytes_read is 0 at end-of-file.  Note we must
	// free the buffer once we're done with this file.
	//
	char *buffer = NULL;
	size_t buffer_len = 0, bytes_read = 0;

	// Read in first and succeeding memory values. 
	//Skip any zero values
	bytes_read = getline(&buffer, &buffer_len, datafile);
	//
	words_read = sscanf(buffer, "%hx", &value_read);
	if (words_read == 1){
	  loc = value_read;
	  cpu->pc = value_read;
	}
	bytes_read = getline(&buffer, &buffer_len, datafile);
	
	while (bytes_read != -1 && !done) {
		// If the line of input begins with an integer, treat
		// it as the memory value to read in.  Ignore junk
		// after the number and ignore blank lines and lines
		// that don't begin with a number.
		//
	        words_read = sscanf(buffer, "%hx", &value_read);
		if (words_read == 1){
		  if (value_read >=  -32768 && value_read <= 32767 && loc < MEMLEN){
		    cpu->mem[loc] = value_read;
		    loc++;
		  }
		}
		// set memory value at current location to
		// value_read and increment location.  Exceptions: If
		// loc is out of range, complain and quit the loop. If
		// value_read is outside -9999...9999, then it's a
		// sentinel and we should say so and quit the loop.
		
		// Get next line and continue the loop
		//
		bytes_read = getline(&buffer, &buffer_len, datafile);
			}
	free(buffer);  // return buffer to OS
	// initialize and dump control unit
	printf("\nCONTROL UNIT:\n");
	dump_control_unit(cpu);
	printf("\n");
	// Initialize rest of memory
	//
	printf("MEMORY (addresses x0000 - xFFFF):\n");
	dump_memory(cpu);
}

// Get the data file to initialize memory with.  If it was
// specified on the command line as argv[1], use that file
// otherwise use default.sdc.  If file opening fails, complain
// and terminate program execution with an error.
// See linux command man 3 exit for details.
//
FILE *get_datafile(int argc, char *argv[]) {
	char *default_datafile_name = "default.hex";
	char *datafile_name;
	
	if(argv[1]==NULL){
	  datafile_name = default_datafile_name;
	}
	else{
	  datafile_name = argv[1];
	}
	// print out file name
	printf("Loading %s\n", datafile_name);

	//set datafile name to argv[1] or default
        FILE *datafile = fopen(datafile_name, "r");
	fileName = datafile;
	//if the open failed, complain and call
	// exit(EXIT_FAILURE); to quit the entire program
	if(datafile==NULL){
	  printf("Couldn't open file!\n"
		 "This file does not exist: %s \nQuitting\n", datafile_name);
	  exit(EXIT_FAILURE);
	}
	
	return datafile;
}

char cond_char(CPU *cpu){
  char CC_char;
  if (cpu->cc == 0){
    CC_char = 'Z';
  }else
    if (cpu->cc > 0){
      CC_char = 'P';
      }else
	 if (cpu->cc < 0){
	   CC_char = 'N';
	 }
  return CC_char;
}

// dump_control_unit(CPU *cpu): Print out the control and
// general-purpose registers
// 
void dump_control_unit(CPU *cpu) {
  char CC_val = cond_char(cpu);
  printf("  PC: x%4.4X  IR: x%4.4x  CC: %c  RUNNING:%2d\n", cpu->pc, cpu->ir, CC_val, cpu->running);
	dump_registers(cpu);
	printf("\n");
}

// dump_memory(CPU *cpu): Print memory values in a table, ten per
// row, with a space between each group of five columns and with
// a header column of memory locations.
//
void dump_memory(CPU *cpu) {
        int loc;	
        // print memory array
	for (loc = 0; loc < MEMLEN; loc++){
	  if (cpu->mem[loc] != 0){
	    printf("x%4.4X \tx%4.4X \t%8d \n", loc, (unsigned short) cpu->mem[loc], cpu->mem[loc]);
	  }
	}
	
}

// dump_registers(CPU *cpu): Print register values in two rows of
// four.
//
void dump_registers(CPU *cpu) {
	int i;
	for(i = 0; i < NREG; i++){
	  if(i == 3){
	    printf("  R%d: x%4.4hX %2d\n", i, cpu->reg[i], cpu->reg[i]);
	  }else{
	    printf("  R%d: x%4.4hX %2d", i, cpu->reg[i], cpu->reg[i]);
	  }
	}
}

// Read a simulator command from the keyboard ("h", "?", "d", "q"
// "g", "s", number, or empty line) and execute it.
//Return true if we hit end-of-input
// or execute_command told us to quit.  Otherwise return false.
//
int read_execute_command(CPU *cpu) {
	// Buffer for the command line from the keyboard, plus its size
	//
	char *cmd_buffer = NULL;
	size_t cmd_buffer_len = 0, bytes_read = 0;

	// Values read using sscanf of command line
	//
	int nbr_cycles;
	char cmd_char;
	size_t words_read;	// number of items read by sscanf call

	int done = 0;	// Should simulator stop?

	bytes_read = getline(&cmd_buffer, &cmd_buffer_len, stdin);

	if (bytes_read == -1) {
		done = 1;   // Hit end of file
	}

	words_read = sscanf(cmd_buffer, "%d", &nbr_cycles);

	if (words_read == 1){
	  many_instruction_cycles(nbr_cycles, cpu);
	}else{
	  words_read = sscanf(cmd_buffer, "%c", &cmd_char);
	  if (cmd_char == '\n') {
	    one_instruction_cycle(cpu);  
	  } else {
	    execute_command(cmd_char, cpu, cmd_buffer);
	  }
	}
	
	//
	//If we found a number, do that many
	// instruction cycles.  Otherwise sscanf for a character
	// and call execute_command with it.  (Note the character
	// might be '\n'.)

	free(cmd_buffer);
	return done;
}

// Execute a nonnumeric command; complain if it's not 'h', '?', 'd', 'q', '\n',
// 'g', or 's', with their respective calls. Also does CAPITAL letters!!!!!
//Return true for the q command, false otherwise
int execute_command(char cmd_char, CPU *cpu, char *cmd_str) {
  Address Gaddr, Saddr;
  Word Sval;
  int regnum;
  char regstr, LOL;
	if (cmd_char == '?' || cmd_char == 'h' || cmd_char == 'H') {
		help_message();
	}else
	if (cmd_char == 'q' || cmd_char == 'Q'){
	  printf("Quitting.\n");
	  exit(EXIT_FAILURE);
	}else
	if (cmd_char == 'd' || cmd_char == 'D'){
	  printf("CONTROL UNIT:\n");
	  dump_control_unit(cpu);
	  printf("\n");
	  printf("MEMORY (addresses x0000 - xFFFF):\n");
	  dump_memory(cpu);
	}else            /// added new stuff here for 'g' and 's' commands
	if (cmd_char == 'g' || cmd_char == 'G'){
	  sscanf(cmd_str, "%c x%hx", &LOL, &Gaddr);
	  cpu->pc = Gaddr;
	  cpu->running = 1;
	  printf("PC went to: x%4.4X", cpu->pc);
	}else
	if (cmd_char == 's' || cmd_char == 'S'){
	  if (cmd_str[2] == 'r' || cmd_str[2] == 'R'){
	    sscanf(cmd_str, "%c %c%d x%hx", &LOL, &regstr, &regnum, &Sval);
	    cpu->reg[regnum] = Sval;
	    printf("Set %c%d = x%hX\n", regstr, regnum, Sval);
	  }else
	  if (cmd_str[2] == 'x'){
	    sscanf(cmd_str, "%c x%hx x%hx", &LOL, &Saddr, &Sval);
	    cpu->mem[Saddr] = Sval;
	    printf("Set mem[x%hX] = x%hX\n", Saddr, Sval);
	  }else printf("Command not written properly!\n"
		       "Set command should be s (address) (value) or s (register) (value)\n");       
	}else printf("Unknown command; ignoring it.\n");
	// 
	return 0;
}

// Print standard message for simulator help command ('h', 'H', or '?')
//
void help_message(void) {
  printf("Simulator commands:\n"
"h or ? for help (prints this message)\n"
"q to quit\n"
"d to dump the control unit and memory\n"
"g address to go to to new location\n"
"s address value to set value of a memory location\n"
"s register value to set register r0, r1, ..., to a value\n"
"An integer > 0 to execute that many instruction cycles\n"
"Or just return, which executes one instruction cycle\n"
"Note: Addresses and values should be in hex xNNNN format\n");
}

// Execute a number of instruction cycles.  Exceptions: If the
// number of cycles is <= 0, complain and return; if the CPU is
// not running, say so and return; if the number of cycles is
// insanely large, complain and substitute a saner limit.
//
// If, as we execute the many cycles, the CPU stops running,
// then return.
//

void many_instruction_cycles(int nbr_cycles, CPU *cpu) {
  int cycles;
  if (cpu->running == 0){
    printf("Halted\n");
    return;
  }else if(nbr_cycles < 1){
    printf("Your number is too small! Try a bigger number.\n");
    return;
  }else if(nbr_cycles > MEMLEN){
    printf("Your number is too large! let's do 100 instructions instead.\n");
    nbr_cycles = 100;
    cycles = nbr_cycles;
    while(cycles >= 1){
      one_instruction_cycle(cpu);
      cycles--;
    }
    return;
  }else cycles = nbr_cycles;
  
  while(cycles >= 1){
    one_instruction_cycle(cpu);
    cycles--;
  }
}


//get specified bits from the 16 bit instruction
// enter left and right boundaries of the bits
// that need to be parsed then shift them to the
//right for the correct value.
int get_bits(Word instr, int left, int right){
  int Ones;
  Word mask, selectedBits, rightAligned;
  Ones = left - right + 1;
  mask = ((1<<Ones) - 1)<<right;
  selectedBits = mask & instr;
  rightAligned = selectedBits >> right;
  return rightAligned;
}

// change the value of parsed bits to
// their decimal value using two's
// compliment method.
// Take the bit position of the bit before
//the bits you want to change into two's compliment.
int twos_comp (Word instr, int pos){
  Word twosComp, rightAlign;
  int signflag = get_bits(instr, pos-1, pos-1);
  if (signflag == 1){
    twosComp = ~instr+1;
    rightAlign = get_bits(twosComp, pos-1, 0);
    rightAlign = -1*rightAlign;
      }else {
    return get_bits(instr, pos-1, 0);
  }
}

// Execute one instruction cycle
//
void one_instruction_cycle(CPU *cpu) {
  // If the CPU isn't running, say so and return.
  // If the pc is out of range, complain and stop running the CPU.
  //
  //
  if (cpu->running == 0){
    printf("Halted\n");
    return;
  }
  if (cpu->pc > MEMLEN){
    printf("The PC is too large! Try a smaller number.\n");
    return;
  }

       // Get instruction from the PC before it increments
         int instr_loc = cpu -> pc;  // Instruction's location (pc before increment)
	 cpu -> ir = cpu -> mem[cpu -> pc++]; //store instruction into IR
	 // Decode instruction into opcode
	 cpu->opcode = ((cpu->ir & 0xF000) >> 12);// To get the opcode AND with 0xF000 then shift 12 bits to the right
	 //
	 //Now I define variables storing differenrt bits that are used in different instructions
	 // ***NOTE*** This may be wastefull in regards to run-time but it makes the code easier to understand!
	 Word flag = (cpu->ir & 0x0020),   // flag bit used for ADD and AND
	   flagBit = (cpu->ir & 0x0800),   // used for JSR
	   maskCC,                         // used to identify BRANCH type
	   NZP = get_bits(cpu->ir, 11, 9), // get NZP for BRANCH
	   DST = get_bits(cpu->ir, 11, 9), // To get the destination register AND with 0x0E00 then shift 9 bits to the right	   
	   Base = get_bits(cpu->ir, 8, 6), // used for getting the base register for BASE type intructions
	   SRC1 = get_bits(cpu->ir, 8, 6), // gets register at source 1
	   SRC2 = get_bits(cpu->ir, 2, 0), // gets register at source 1
	   trap = get_bits(cpu->ir, 7, 0), // gets the trap vector to decode the varios types
	   immed5 = twos_comp(cpu->ir, 5), // Two's complement of last 5 bits -- used for flagged ADD and AND.
	   off6 = twos_comp(cpu->ir, 5),   // Two's complement of offset6
	   off9 = twos_comp(cpu->ir, 9),   // Two's complement of offset9
	   off11 = twos_comp(cpu->ir, 11); // Two's complement of offset6

       // Echo instruction
       //so the user knows what is happening!
	 printf("x%4.4X: x%4.4X ", instr_loc, (unsigned short) cpu->ir);
       //Switch using the first four bits of instruction
	 //which is the opcode.
       switch (cpu -> opcode) {
       case 0: // BR & NOP
	 if (NZP == 0){//NOP when NZP = 0x000; this never loops
	   printf("NOP %d, CC = %c, no go to\n", off9, cond_char(cpu));
	   break;
	 }	 // otherwise Branch 
	   printf("BR");
	   if ((NZP&0x4) != 0){ //branch when condition code is negative
	     maskCC = maskCC + 0x4;
	     printf("N");
	   }
	   if ((NZP&0x2) != 0){//branch when cc is zero
	     maskCC = maskCC + 0x2;
	     printf("Z");
	   }
	   if((NZP&0x1) != 0){//branch when cc is positive
	     maskCC = maskCC + 0x1;
	     printf("P");
	   }
	 
	 printf(" %d; CC = %c; ", off9, cond_char(cpu));
	 if((NZP&maskCC) == 0){
	   printf("go to PCx%4.4X+%d = ", cpu->pc, off9);
	   cpu->pc = cpu->pc+off9;
	   printf("x%4.4X\n", cpu->pc);
	 }else printf("no go to\n");
	   
	 break;                         //method to use for ADD
       case 1: //ADD                    //To check 6th bit (0000 0000 00[]0 0000) AND with mask
	 if (flag == 0x0020){           //Then check if bit == x0020 (0000 0000 0010 0000 =?= 0000 0000 0010 0000)
	   cpu->cc = (cpu->reg[SRC1]+immed5); // calculate immediate value and set CC
	   printf("ADD  R%d, R%d, %d;  R%d <- x%X+(%d) = x%X; CC = %c\n", DST, SRC1, immed5, DST, cpu->reg[SRC1], immed5, (unsigned short)(cpu->reg[SRC1] + immed5), cond_char(cpu));
	   cpu->reg[DST] = cpu->reg[SRC1] + immed5;
	 }else {                         //otherwise just add registers and set CC
	   cpu->cc = (cpu->reg[SRC1]+cpu->reg[SRC2]);
	   printf("ADD  R%d, R%d, R%d;  R%d <- x%X+x%X = x%X; CC = %c\n", DST, SRC1, SRC2, DST, cpu->reg[SRC1], cpu->reg[SRC2], (cpu->reg[SRC1] + cpu->reg[SRC2]), cond_char(cpu));
	   cpu->reg[DST] = cpu->reg[SRC1] + cpu->reg[SRC2];
	 }
	 break;
       case 2: //LD 
	 cpu->cc = cpu->mem[(cpu->pc + off9)]; //load PC+offset9 into given register and set CC
	 printf("LD   R%d, %d;  R%d <- M[PC+%d] = M[x%4.4X] = x%4.4X; CC = %c\n", DST, off9, DST, off9, (cpu->pc+off9), cpu->mem[(cpu->pc+off9)], cond_char(cpu));
	 cpu->reg[DST] = cpu->mem[cpu->pc+off9];
	 break;
       case 3: //ST
	 printf("ST   R%d, %d; M[PC+%d] <- R%d = M[x%4.4X] = x%4.4X;\n", DST, off9, off9, DST, (cpu->pc+off9), cpu->mem[(cpu->pc+off9)]);
	 cpu->mem[cpu->pc + off9] = cpu->reg[DST];//store into memory of PC+offset9 the given register and set CC
	 break;
       case 4:                                   //check if the flag indicates JSR or JSRR
	 if (flagBit == 0x0800){//JSR
	   cpu->reg[7] = cpu->pc;                //remeber to store PC in R[7]
	   printf("JSR PCx%4.4X, %d; PC+offset11 = x%4.4X\n", cpu->pc, off11, (cpu->pc+off11));
	   cpu->pc = (cpu->pc+off11);            // then goto subroutine
	 }else if (flagBit == 0){ //JSRR
	   printf("JSRR R%d = x%4.4X\n", Base, cpu->reg[Base]);
	   if (Base == 7){                       // Properly store PC into R[7] without getting 
	     int tempR = cpu->pc;                // mixed up by using a temporary storage for PC
	     cpu->pc = cpu->reg[Base];
	     cpu->reg[7] = tempR;
	   }else {
	     cpu->reg[7] = cpu->pc;
	     cpu->pc = cpu->reg[Base];
	   }
	 }
	 break;
       case 5: //AND
	 if (flag == 0x0020){                  // check the flag for immediate value
	   cpu->reg[DST] = (cpu->reg[SRC1] & immed5);
	   cpu->cc = (cpu->reg[SRC1] & immed5);//set condition code
	   printf("AND  R%d, R%d, %d;  R%d <- x%4.4X & x%4.4X = x%X; CC = %c\n", DST, SRC1, immed5, DST, cpu->reg[SRC1], (unsigned short) immed5, (cpu->reg[SRC1] & immed5), cond_char(cpu));
	 }else {                               // otherwise AND registers
	   cpu->reg[DST] = (cpu->reg[SRC1] & cpu->reg[SRC2]);
	   cpu->cc = (cpu->reg[SRC1] & cpu->reg[SRC2]);//set condition code
	   printf("AND  R%d, R%d, R%d;  R%d <- x%4.4X & x%4.4X = x%X; CC = %c\n", DST, SRC1, SRC2, DST, cpu->reg[SRC1], cpu->reg[SRC2], (cpu->reg[SRC1] & cpu->reg[SRC2]), cond_char(cpu));
	 }
	 break;
       case 6: //LDR                               //load into destination register using a base register
	 cpu->cc = cpu->mem[cpu->reg[Base]+off6];  // and set CC
	 printf("LDR  R%d, R%d, %d; R%d <- M[x%4.4X+%d] = M[x%4.4X] = x%4.4X; CC = %c\n", DST, Base, off6, DST, cpu->reg[Base], off6, (cpu->reg[Base]+off6), cpu->mem[(cpu->reg[Base]+off6)], cond_char(cpu));
	 cpu->reg[DST] = cpu->mem[cpu->reg[Base]+off6];
	 break;
       case 7: //STR                               //store destination register into base register
	 printf("STR  R%d, R%d, %d; M[x%4.4X+%d] = M[x%4.4X] <- x%4.4X\n", DST, Base, off6, cpu->reg[Base], off6, (cpu->reg[Base]+off6), cpu->reg[DST]);
	  cpu->mem[cpu->reg[Base]+off6] = cpu->reg[DST];
	 break;
       case 8: //RTI - print error
	 printf("RTI; ignored\n");
	 break;
       case 9: //NOT                             //take NOT of a source register store in destination
	 cpu->cc = ~(cpu->reg[SRC1]);            // set the CC
	 printf("NOT R%d, R%d; R%d <- NOT x%4.4X = x%4.4X; CC = %c\n", DST, SRC1, DST, cpu->reg[SRC1],(unsigned short) ~(cpu->reg[SRC1]), cond_char(cpu));
	 cpu->reg[DST] = ~(cpu->reg[SRC1]);
	 break;
       case 10: //LDI                                //load immediate value (offset9) + PC into register 
	 cpu->cc = cpu->mem[cpu->mem[cpu->pc+off9]]; //set the CC
	 printf("LDI  R%d, %d; R%d <- M[M[PC+(%d)]] = M[M[x%4.4X]] = M[x%4.4X] = x%4.4X; CC = %c\n", DST, off9, DST, off9, (cpu->pc+off9), cpu->mem[(cpu->pc+off9)], cpu->mem[cpu->mem[(cpu->pc+off9)]], cond_char(cpu));
	 cpu->reg[DST] = cpu->mem[cpu->mem[cpu->pc+off9]];
	 break;
       case 11: //STI                               //store into immediate value (offset9) + PC the destination register
	 printf("STI  R%d, %d; M[M[PC+(%d)]] = M[M[x%4.4X]] = M[x%4.4X] <- x%4.4X\n", DST, off9, off9, (cpu->pc+off9), cpu->mem[(cpu->pc+off9)], (unsigned short) cpu->reg[DST]);
	 cpu->mem[cpu->mem[cpu->pc+off9]] = cpu->reg[DST];
	 break;
       case 12: //JMP & RET
	 if (Base == 7){//RET                       // check the if Base = 7 
	   printf("RET PCx%4.4X <- R7 = x%4.4X\n", cpu->pc, cpu->reg[7]);
	   Word temp = cpu->pc;                     // properly put PC into R[7] and vice versa
	   cpu->pc = cpu->reg[7];                   // by using a temporary storage
	   cpu->reg[7] = temp;
	 }else {//JMP                               // otherwise just jump by putting PC into R[7]
	 printf("JMP PCx%4.4X <- R%d = x%4.4X\n", cpu->pc, Base, cpu->reg[Base]);
	 cpu->pc = cpu->reg[Base];
	 }
	 break;
       case 13: //err                              //this is not used!
	 printf("Reserved opcode; ignored.\n");
	 break;
       case 14: //LEA                              // load an address into register directly
	 cpu->cc = (cpu->pc + off9);               // set the CC
	 printf("LEA   R%d, %d;  R%d <- PC+%d = x%4.4X; CC = %c\n", DST, off9, DST, off9, (cpu->pc+off9), cond_char(cpu));
	 cpu->reg[DST] = cpu->pc+off9;
	 break;
       case 15: //TRAP                             //This has 6 types check for each one by one
	 printf("TRAP x%X", trap);                 // print mneumonic
	 cpu->reg[7] = cpu->pc;                    // store PC into R[7] before doing TRAP command
	 if (trap == 0x20){//GETC                 
	   cpu->cc = cpu->reg[7];                  // set CC to R[7]
	   printf(" (GETC): ");                    // read char without prompt
	   char Achar;
	   int toNum;
	   scanf("%c", &Achar);
	   getchar();
	   printf("Read %c", Achar);
	   cpu->reg[0] = Achar;                    //print as a char and integer value
	   toNum = (int) Achar;
	   printf(" = %d; CC = %c\n", toNum, cond_char(cpu));
	   break;
	 }else if (trap == 0x21){//OUT
	   cpu->cc = cpu->reg[7];                 // set CC to R[7]
	   printf(" (OUT): %d = %c; CC = %c\n", cpu->reg[0], cpu->reg[0], cond_char(cpu)); //print out register zero 
	   execute(cpu);                         //*********NOTE*********** this copies the instructor's code which went back to 
	   break;                                // read_execute_command after the (OUT) trap. So this does exactly what sample solution does
	 }else if (trap == 0x22){//PUTS
	   cpu->cc = cpu->reg[7];                // store PC into R[7] before doing TRAP command    
	   printf(" (PUTS): ");
	   while(cpu->mem[cpu->reg[0]] != 0){    //recursively print out R0[7..0] until you reach null
	     printf("%c ", cpu->mem[cpu->pc]);
	     cpu->pc++;
	       }printf("; CC = %c\n", cond_char(cpu));
	   break;
	 }else if (trap == 0x23){//IN
	   cpu->cc = cpu->reg[7];                // set CC to R[7]
	   printf(" (IN) Input a character> ");  // read char with a prompt
	   char Achar;                       
	   int toNum;
	   scanf("%c", &Achar);
	   getchar();
	   printf("Read %c", Achar);             
	   cpu->reg[0] = Achar;                 //read char into R0
	   toNum = (int) Achar;                 //print out both char and integer value
	   printf(" = %d; CC = %c\n", toNum, cond_char(cpu));
	   break;
	 }else if (trap == 0x24){//PUTSP        // prints out 2 values from R0[15..8]&[7..0]
	   cpu->cc = cpu->reg[7];               // set CC to R[7]
	   printf(" (PUTSP): ");
	   while(cpu->mem[cpu->reg[0]] != 0){   //prints recursively until it hits null
	     printf("%c ", cpu->mem[cpu->pc]);
	     cpu->pc++;
	       }printf("; CC = %c\n", cond_char(cpu));
	   break;
	 }else if (trap == 0x25){//HALT
	   cpu->cc = 0x1111;                    // set CC to positive
	   printf(" HALT; CC = %c; Halting.\n", cond_char(cpu));
	   exec_HLT(cpu);                       //change running to zero and halt program
	   break;
	 }else {
	   printf (" (bad trapvector); Halting;\n", cond_char(cpu));//***NOTE*** again this is what instructor's code does
	   exec_HLT(cpu);                      //halt program for bag Trapvector ;;; don't set CC
	   break;
	 }
	 break;
       default: printf("Bad opcode?! %4.4X\n", cpu->ir); //print this if instruction is WACKY
       }
}

// Execute the halt instruction (make CPU stop running)
//used for HALT and bad trapvectors 
void exec_HLT(CPU *cpu) {
  cpu -> running = 0;

  char *prompt = "> ";
  printf("%s", prompt);

  int done = read_execute_command(cpu);
  while (!done) {
    printf("%s", prompt);
    done = read_execute_command(cpu);
  }
}

//Return to read_execute_command to read a new command in
//***NOTE*** this is what instructor's code does for TRAP x21 (OUT)
void execute(CPU *cpu) {
  char *prompt = "> ";
  printf("%s", prompt);

  int done = read_execute_command(cpu);
  while (!done) {
    printf("%s", prompt);
    done = read_execute_command(cpu);
  }
}

//END OF PROGRAM!!!!!!!!!!!!!
//WOOOOO   HOOOOOOO!!!!!!!!!!!
//END OF SEMESTER!!!!!!!!!!!! YAAAAAAAAAAAAAAAAAAAAAAAAY!!!!!!!!!!!!!
//HAVE A GR8888 SUMMER!!!!!!!!!!!!!!!!!!!
//WOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO!!!!!!!!!!!!!!!!!!!!!!!
// BOOOYAAA!!!!!!!!!!
//YYYYYYYYYYYYYYAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//Have fun! and as always;
//Have a nice day! :-)!
