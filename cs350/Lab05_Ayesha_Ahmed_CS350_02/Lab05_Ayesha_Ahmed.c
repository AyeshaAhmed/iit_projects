// 
// *** Ayesha Ahmed CS350_02 Lab05 ***
// 

// CS 350, Spring 2015
// Lab 5: SDC Simulator
//
// Illinois Institute of Technology, (c) 2014, James Sasaki

#include <stdio.h>
#include <stdlib.h>       // For error exit()

// CPU Declarations -- a CPU is a structure with fields for the
// different parts of the CPU.
//
	typedef short int Word;          // type that represents a word of SDC memory
	typedef unsigned char Address;   // type that represents an SDC address

	#define MEMLEN 100
	#define NREG 10

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
	} CPU;

        int breakLoc, Sentinelval;
        char *fileName;        
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
	printf("SDC Simulator: CS_350 Lab_05 Ayesha_Ahmed\n");
	CPU cpu_value, *cpu = &cpu_value;
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
	printf("\nInitial control unit:\n");
	dump_control_unit(cpu);
	printf("\n");
}

// Read and dump initial values for memory
//
void initialize_memory(int argc, char *argv[], CPU *cpu) {
	FILE *datafile = get_datafile(argc, argv);

	// Will read the next line (words_read = 1 if it started
	// with a memory value). Will set memory location loc to
	// value_read
	//
	int value_read, words_read, loc = 0, done = 0;
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
	while (bytes_read != -1 && !done) {
		// If the line of input begins with an integer, treat
		// it as the memory value to read in.  Ignore junk
		// after the number and ignore blank lines and lines
		// that don't begin with a number.
		//
	        words_read = sscanf(buffer, "%d", &value_read);
		if (words_read == 1){
		  if (value_read >=  -9999 && value_read <= 9999 && loc < MEMLEN){
		    cpu->mem[loc] = value_read;
		    loc++;
		  }
		  else {
		  	breakLoc = loc;
			Sentinelval = value_read;
		  	break;
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
	while (loc < MEMLEN) {
		cpu -> mem[loc++] = 0;
	}
	printf("Initialize memory from %s\n", fileName);
	printf("Sentinel %d found at location %d\n", Sentinelval, breakLoc);
	dump_memory(cpu);
}

// Get the data file to initialize memory with.  If it was
// specified on the command line as argv[1], use that file
// otherwise use default.sdc.  If file opening fails, complain
// and terminate program execution with an error.
// See linux command man 3 exit for details.
//
FILE *get_datafile(int argc, char *argv[]) {
	char *default_datafile_name = "default.sdc";
	char *datafile_name;
	char *justName;
	if(argv[1]==NULL){
	  datafile_name = "default.sdc";
	  justName = "default.sdc";
	}
	else{
	  datafile_name = argv[1];
	  justName = argv[1];
	}

	//set datafile name to argv[1] or default
        fileName = justName;
	FILE *datafile = fopen(datafile_name, "r");

	if(datafile==NULL){
	  printf("this file does not exist: %s", datafile_name);
	  exit(EXIT_FAILURE);
	}
	
	//if the open failed, complain and call
	// exit(EXIT_FAILURE); to quit the entire program
	return datafile;
}

// dump_control_unit(CPU *cpu): Print out the control and
// general-purpose registers
// 
void dump_control_unit(CPU *cpu) {
        
  printf("  PC:%6.2d  IR:%6.4d  RUNNING:%2d\n", cpu->pc, cpu->ir, cpu->running);
	dump_registers(cpu);
	printf("\n");
}

// dump_memory(CPU *cpu): Print memory values in a table, ten per
// row, with a space between each group of five columns and with
// a header column of memory locations.
//
void dump_memory(CPU *cpu) {
        int loc = 0;
	int row = 0, col = 0;
        // print memory array
	while (col < 10){
	  printf("%3d0:", col);
	while (row < 10){
	  printf("%6d", cpu->mem[loc]);
	  if (row == 4){
	    printf("   ");
	  }
	  loc++;
	  row++;
	}
	row = 0;
	col++;
	printf("\n");
	}
	
}

// dump_registers(CPU *cpu): Print register values in two rows of
// five.
//
void dump_registers(CPU *cpu) {
	int i;
	for(i = 0; i < NREG; i++){
	  if(i == 4){
	    printf("  R%d:%6d\n", i, cpu->reg[i]);
	  }else{
	    printf("  R%d:%6d", i, cpu->reg[i]);
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
	  many_instruction_cycles(nbr_cycles, cpu);
	}else{
	  words_read = sscanf(cmd_buffer, "%c", &cmd_char);
	  if (cmd_char == '\n') {
	    one_instruction_cycle(cpu);
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
	if (cmd_char == '?' || cmd_char == 'h') {
		help_message();
	}else
	if (cmd_char == 'q'){
	  printf("Quitting.\n");
	  exit(EXIT_FAILURE);
	}else
	if (cmd_char == 'd'){
	  dump_control_unit(cpu);
	  printf("\n");
	  dump_memory(cpu);
	}else printf("Unknown command; ignoring it.\n");
	// 
	return 0;
}

// Print standard message for simulator help command ('h' or '?')
//
void help_message(void) {
  printf("Simulator commands:\n"
"h or ? for help (prints this message)\n"
"d to dump the control unit\n"
"An integer > 0 to execute that many instruction cycles\n"
"Or just return, which executes one instruction cycle\n\n"
"SDC Instruction set:\n"
" 0xxx: HALT\n"
" 1RMM: Load Reg[R] <- M[MM]\n"
" 2RMM: Store M[MM] <- Reg[R]\n"
" 3RMM: Add M[MM] to Reg[R]\n"
" 4Rxx: Negate Reg[R]\n"
" 5RMM: Load Immediate Reg[R] <- MM\n"
"-5RMM: Load Immediate Reg[R] <- -MM\n"
" 6RMM: Add Immediate Reg[R] <- Reg[R] + MM\n"
"-6RMM: Subtract Immediate Reg[R] <- Reg[R] - MM\n"
" 7xMM: Branch to MM\n"
" 8RMM: Branch Conditional to MM\n"
" 90xx: Read char into R0\n"
" 91xx: Print char in R0\n"
" 92MM: Print string starting at MM\n"
" 93MM: Dump control unit\n"
" 94MM: Dump memory\n\n");
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
  if (cpu->running == 0){
    printf("Halted\n");
    return;
  }
  if(nbr_cycles < 1){
    printf("Your number is too small! Try a bigger number.\n");
    return;
  }
  if(nbr_cycles > MEMLEN){
    printf("Your number is too large! Try a smaller number.\n");
    return;
  }
  while(nbr_cycles >= 1){
    one_instruction_cycle(cpu);
    nbr_cycles--;
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

	// Get instruction and increment pc
	//
	int instr_loc = cpu -> pc;  // Instruction's location (pc before increment)
	cpu -> ir = cpu -> mem[cpu -> pc++];

	// Decode instruction into opcode, reg_R, addr_MM, and instruction sign
	//
	//
	cpu->opcode = cpu->ir/1000;
	cpu->reg_R = (cpu->ir%1000) / 100;
	cpu->addr_MM = (cpu->ir%100);
	
	if (cpu->ir < 0){
	  cpu->instr_sign = -1;
	  cpu->reg_R = cpu->reg_R * -1;
	  cpu->addr_MM = cpu->addr_MM * -1;
	    }else cpu->instr_sign = 1;
	// Echo instruction
	//
	printf("At %02d instr %2d %d %02d: ", instr_loc, cpu -> opcode, cpu -> reg_R, cpu -> addr_MM);
	

	switch (cpu -> opcode) {
	case 0:
	  exec_HLT(cpu);
	  break;
	  
	case 1:
	  printf("LD   R%d <- M[%d] = %d\n", cpu->reg_R, cpu->addr_MM, cpu->mem[cpu->addr_MM]);
	  cpu->reg[cpu->reg_R] = cpu->mem[cpu->addr_MM];
	  break;
	  
	case 2:
	  printf("ST   M[%d] <- R%d = %d\n", cpu->addr_MM, cpu->reg_R, cpu->reg[cpu->reg_R]);
	  cpu->mem[cpu->addr_MM] = cpu->reg[cpu->reg_R];
	  break;
	  
	case 3:
	  printf("ADD  R%d <- R%d + M[%d] = %d + %d = %d \n", cpu->reg_R, cpu->reg_R, cpu->addr_MM, cpu->reg[cpu->reg_R], cpu->mem[cpu->addr_MM], (cpu->reg[cpu->reg_R] + cpu->mem[cpu->addr_MM]));
	  cpu->reg[cpu->reg_R] = cpu->reg[cpu->reg_R] + cpu->mem[cpu->addr_MM];
	  break;
	  
	case 4:
	  printf("NEG  R%d <- -R%d = -%d\n", cpu->reg_R, cpu->reg_R, cpu->reg[cpu->reg_R]);
	  cpu->reg[cpu->reg_R] = -(cpu->reg[cpu->reg_R]);
	  break;
	  
	case 5:
	case -5:
	  printf("LDM  R%d <- %d\n", cpu->reg_R, (cpu->instr_sign * cpu->addr_MM));
	  cpu->reg[cpu->reg_R] = cpu->instr_sign * cpu->addr_MM;
	  break;

	case 6:
	case -6:
	  printf("ADDM R%d <- R%d + %d = %d + %d = %d\n", cpu->reg_R, cpu->reg_R, cpu->addr_MM, cpu->reg[cpu->reg_R], (cpu->instr_sign * cpu->addr_MM), (cpu->reg[cpu->reg_R] + (cpu->instr_sign * cpu->addr_MM)));
	  cpu->reg[cpu->reg_R] = cpu->reg[cpu->reg_R] + (cpu->instr_sign * cpu->addr_MM);
	  break;

	case 7: printf("BR   %d\n", cpu->addr_MM);
	  cpu->pc = cpu->addr_MM;
	  break;
	case 8: printf("BRC  %d if R%d = %d > 0: ", cpu->addr_MM, cpu->reg_R, cpu->reg[cpu->reg_R]);
	  if(cpu->reg[cpu->reg_R] > 0){
	    printf("Yes\n");
	    cpu->pc = cpu->addr_MM;
	  }else
	  printf("No\n");
	  break;
	  
	case -8: printf("BRC  %d if R%d = %d < 0: ", cpu->addr_MM, cpu->reg_R, cpu->reg[cpu->reg_R]);
	  if(cpu->reg[cpu->reg_R] < 0){
	    printf("Yes\n");
	    cpu->pc = cpu->addr_MM;
	  }else
	  printf("No\n");
	  break;
	  
	case 9:
	case -9:
	  if(cpu->reg_R >= 5){
	    printf("Unknown I/O; skipped\n");
	  }else {
	    printf("I/O  ");
	    switch(cpu->reg_R){
	    case 0:
	      printf("0: Read char\n");
	      printf("Enter a char (and/or press return): ");
	      int toNum;
	      char Achar;
	      scanf("%c", &Achar);
	      toNum = (int)Achar;
	      printf("R%d <- %d\n", cpu->reg_R, toNum);
	      cpu->reg[cpu->reg_R] = toNum;
	      break;
	    case 1:
              printf("1: print char in R0 (= %d): %c\n", cpu->reg[0], cpu->reg[0]);
	      break;
	    case 2:
	      printf("2: Print string: ");
	      int temp;
	      temp = cpu->addr_MM;
	      while (cpu->mem[temp] != 0){
		printf("%c", cpu->mem[temp]);
		temp++;
	      }printf("\n");
	      break;
	    case 3: printf("3: Dump Control Unit:\n");
	      dump_control_unit(cpu);
	      break;
	    case 4: printf("4: Dump Memory:\n");
	      dump_memory(cpu);
	      break;
	    default: printf("Bad opcode!? %d\n", cpu -> opcode);
	    }
	    break;
	  }
	  break;
	default: printf("Bad opcode!? %d\n", cpu -> opcode);
	  break;
	}
}

// Execute the halt instruction (make CPU stop running)
//
void exec_HLT(CPU *cpu) {
	printf("HALT\nHalting\n");
	cpu -> running = 0;

	char *prompt = "> ";
	printf("%s", prompt);

	int done = read_execute_command(cpu);
	while (!done) {
		printf("%s", prompt);
		done = read_execute_command(cpu);
	}
}
