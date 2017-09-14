// 
// *** Ayesha Ahmed CS350_02 Lab08 ***
// 

// CS 350, Spring 2015
// Lab 8: LC3 Simulator
//
// Illinois Institute of Technology, (c) 2014, James Sasaki

#include <stdio.h>
#include <stdlib.h>       // For error exit()

// CPU Declarations -- a CPU is a structure with fields for the
// different parts of the CPU.
//
	typedef short int Word;          // type that represents a word of SDC memory
typedef unsigned short Address;          // type that represents an SDC address

#define MEMLEN 65536
	#define NREG 8

	typedef struct {
		Word mem[MEMLEN];
		Word reg[NREG];      // Note: "register" is a reserved word
		Address pc;          // Program Counter
	        int running;         // running = 1 iff CPU is executing instructions
		Word ir;             // Instruction Register
		int instr_sign;      //   sign of instruction
	        int opcode;          //   opcode field
		int reg_R;           //   register field
		int addr_MM;         //   memory field
	        int cc;              // Condition Code
	} CPU;

        int breakLoc, Sentinelval;
        FILE *fileName;        
// Prototypes [note the functions are also declared in this order]
//
	int main(int argc, char *argv[]);
	void initialize_control_unit(CPU *cpu);
	void initialize_memory(int argc, char *argv[], CPU *cpu);
        void exec_HLT(CPU *cpu);
	FILE *get_datafile(int argc, char *argv[]);

	void dump_control_unit(CPU *cpu);
	void dump_memory(CPU *cpu);
	void dump_registers(CPU *cpu);

	int read_execute_command(CPU *cpu);
	int execute_command(char cmd_char, CPU *cpu);
	void help_message(void);
	void many_instruction_cycles(int nbr_cycles, CPU *cpu);
	void one_instruction_cycle(CPU *cpu);

	
// Main program: Initialize the cpu, read initial memory values,
// and execute the read-in program starting at location 00.
//
int main(int argc, char *argv[]) {
        CPU cpu_value, *cpu = &cpu_value;
		
	printf("LC3 Simulator: CS 350 Lab 8 Ayesha Ahmed\n");
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


// Initialize the control registers (pc, ir, running flag) and
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
	printf("\nCONTROL UNIT:\n");
	dump_control_unit(cpu);
	printf("\n");
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
	// See linux command man 3 getline for details.
	//
	char *buffer = NULL;
	size_t buffer_len = 0, bytes_read = 0;

	// Read in first and succeeding memory values. Stop when we
	// hit a sentinel value, fill up memory, or hit end-of-file.
	//
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
		    //printf("%d \t %x\n", loc, value_read);
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
	
	// Initialize rest of memory
	//
	printf("MEMORY (addresses x0000 - xFFFF):\n");
	//printf("Sentinel %d found at location %d\n", Sentinelval, breakLoc);
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

// dump_control_unit(CPU *cpu): Print out the control and
// general-purpose registers
// 
void dump_control_unit(CPU *cpu) {
  char CC_val;
  if (cpu->cc == 0){
    CC_val = 'Z';
  }else
    if (cpu->cc > 0){
      CC_val = 'P';
      }else
	 if (cpu->cc < 0){
	   CC_val = 'N';
	 }
	 
  printf("  PC: x%4.4x  IR: x%4.4x  CC: %c  RUNNING:%2d\n", cpu->pc, cpu->ir, CC_val, cpu->running);
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
	    printf("x%4.4x \tx%4.4X \t%8d \n", loc, (unsigned short) cpu->mem[loc], cpu->mem[loc]);
	  }
	}
	
}

// dump_registers(CPU *cpu): Print register values in two rows of
// five.
//
void dump_registers(CPU *cpu) {
	int i;
	for(i = 0; i < NREG; i++){
	  if(i == 3){
	    printf("  R%d: x%4.4x %2d\n", i, cpu->reg[i], cpu->reg[i]);
	  }else{
	    printf("  R%d: x%4.4x %2d", i, cpu->reg[i], cpu->reg[i]);
	  }
	}
}

// Read a simulator command from the keyboard ("h", "?", "d", number,
// or empty line) and execute it.  Return true if we hit end-of-input
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
	  // many_instruction_cycles(nbr_cycles, cpu);
	  printf("This command is recognized but not implemented in Lab 8.\n");
	}else{
	  words_read = sscanf(cmd_buffer, "%c", &cmd_char);
	  if (cmd_char == '\n') {
	    //one_instruction_cycle(cpu);
	    printf("This command is recognized but not implemented in Lab 8.\n");
	  } else {
	    execute_command(cmd_char, cpu);
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

// Execute a nonnumeric command; complain if it's not 'h', '?', 'd', 'q' or '\n'
// Return true for the q command, false otherwise
//
int execute_command(char cmd_char, CPU *cpu) {
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
	}else
	if (cmd_char == 'g' || cmd_char == 'G'){
	    printf("This command is recognized but not implemented in Lab 8.\n");
	}else
	if (cmd_char == 's' || cmd_char == 'S'){
	      printf("This command is recognized but not implemented in Lab 8.\n");
	}else printf("Unknown command; ignoring it.\n");
	// 
	return 0;
}

// Print standard message for simulator help command ('h' or '?')
//
void help_message(void) {
  printf("Simulator commands:\n"
"h or H or ? for help (prints this message)\n"
"q or Q to quit\n"
"d or D to dump the control unit and memory\n"
"-------------------- Not suppored in Lab 8 --------------------\n"
"g address to go to to new location\n"
"s address value to set value of a memory location\n"
"s register value to set register r0, r1, ..., to a value\n"
"An integer > 0 to execute that many instruction cycles\n"
"Or just return, which executes one instruction cycle\n"
"Note: Addresses and values should be in hex xNNNN format\n");
}

/* //------~~~~ Lab05 ~~~~------// */

/* // Execute a number of instruction cycles.  Exceptions: If the */
/* // number of cycles is <= 0, complain and return; if the CPU is */
/* // not running, say so and return; if the number of cycles is */
/* // insanely large, complain and substitute a saner limit. */
/* // */
/* // If, as we execute the many cycles, the CPU stops running, */
/* // then return. */
/* // */

/* void many_instruction_cycles(int nbr_cycles, CPU *cpu) { */
/*   if (cpu->running == 0){ */
/*     printf("Halted\n"); */
/*     return; */
/*   } */
/*   if(nbr_cycles < 1){ */
/*     printf("Your number is too small! Try a bigger number.\n"); */
/*     return; */
/*   } */
/*   if(nbr_cycles > MEMLEN){ */
/*     printf("Your number is too large! Try a smaller number.\n"); */
/*     return; */
/*   } */
/*   while(nbr_cycles >= 1){ */
/*     one_instruction_cycle(cpu); */
/*     nbr_cycles--; */
/*   } */
/* } */

/* // Execute one instruction cycle */
/* // */

/* void one_instruction_cycle(CPU *cpu) { */
/*   // If the CPU isn't running, say so and return. */
/*   // If the pc is out of range, complain and stop running the CPU. */
/*   // */
/*   // */
/*   if (cpu->running == 0){ */
/*     printf("Halted\n"); */
/*     return; */
/*   } */
/*   if (cpu->pc > MEMLEN){ */
/*     printf("The PC is too large! Try a smaller number.\n"); */
/*     return; */
/*   } */

/*        // Get instruction and increment pc */
/*        // */
/*        int instr_loc = cpu -> pc;  // Instruction's location (pc before increment) */
/*        cpu -> ir = cpu -> mem[cpu -> pc++]; */

/*        // Decode instruction into opcode, reg_R, addr_MM, and instruction sign */
/*        // */
/*        // */
/*        cpu->opcode = cpu->ir/1000; */
/*        cpu->reg_R = (cpu->ir%1000) / 100; */
/*        cpu->addr_MM = (cpu->ir%100); */

/*        if (cpu->ir < 0){ */
/* 	 cpu->instr_sign = -1; */
/* 	 cpu->reg_R = cpu->reg_R * -1; */
/* 	 cpu->addr_MM = cpu->addr_MM * -1; */
/*        }else cpu->instr_sign = 1; */
/*        // Echo instruction */
/*        // */
/*        printf("At %02d instr %2d %d %02d: ", instr_loc, cpu -> opcode, cpu -> reg_R, cpu -> addr_MM); */
/* } */

/* // Execute the halt instruction (make CPU stop running) */
/* // */
/* void exec_HLT(CPU *cpu) { */
/*   printf("HALT\nHalting\n"); */
/*   cpu -> running = 0; */

/*   char *prompt = "> "; */
/*   printf("%s", prompt); */

/*   int done = read_execute_command(cpu); */
/*   while (!done) { */
/*     printf("%s", prompt); */
/*     done = read_execute_command(cpu); */
/*   } */
/* } */

