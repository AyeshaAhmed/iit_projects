// ** Skeleton ** <-- remove this line and complete the program
// *** Your name and section (replace this with the correct information ***
// (Also rename this file to Lab04_YourName_Section.c and remove this comment line)

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


// Prototypes [note the functions are also declared in this order]
//
	int main(int argc, char *argv[]);
	void initialize_control_unit(CPU *cpu);
	void initialize_memory(int argc, char *argv[], CPU *cpu);
	FILE *get_datafile(int argc, char *argv[]);

	void dump_control_unit(CPU *cpu);
	void dump_memory(CPU *cpu);
	void dump_registers(CPU *cpu);

	int read_execute_command(CPU *cpu);
	int execute_command(char cmd_char, CPU *cpu);
	void help_message(void);
	void many_instruction_cycles(int nbr_cycles, CPU *cpu);
	void one_instruction_cycle(CPU *cpu);

	// *** STUB *** (stub means you need to replace this comment with code)


// Main program: Initialize the cpu, read initial memory values,
// and execute the read-in program starting at location 00.
//
int main(int argc, char *argv[]) {
	printf("SDC Simulator skeleton: CS 350 Lab 5\n");
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
	
	// *** STUB ***
	
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
	while (bytes_read != -1 && !done) {
		// If the line of input begins with an integer, treat
		// it as the memory value to read in.  Ignore junk
		// after the number and ignore blank lines and lines
		// that don't begin with a number.
		//
		words_read = sscanf(buffer, "%d", &value_read);

	
		// *** STUB *** set memory value at current location to
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

	// *** STUB *** set datafile name to argv[1] or default

	FILE *datafile = fopen(datafile_name, "r");

	// *** STUB *** if the open failed, complain and call
	// exit(EXIT_FAILURE); to quit the entire program

	return datafile;
}

// dump_control_unit(CPU *cpu): Print out the control and
// general-purpose registers
// 
void dump_control_unit(CPU *cpu) {
	// *** STUB ****
	dump_registers(cpu);
}

// dump_memory(CPU *cpu): Print memory values in a table, ten per
// row, with a space between each group of five columns and with
// a header column of memory locations.
//
void dump_memory(CPU *cpu) {
	int loc = 0;
	int row, col;
	// *** STUB ****
}

// dump_registers(CPU *cpu): Print register values in two rows of
// five.
//
void dump_registers(CPU *cpu) {
	// *** STUB ****
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
	// *** STUB ****  If we found a number, do that many
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
	}
	// *** STUB ****
	return 0;
}

// Print standard message for simulator help command ('h' or '?')
//
void help_message(void) {
	// *** STUB ****
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
	// *** STUB ****
}

// Execute one instruction cycle
//
void one_instruction_cycle(CPU *cpu) {
	// If the CPU isn't running, say so and return.
	// If the pc is out of range, complain and stop running the CPU.
	//
	// *** STUB ****

	// Get instruction and increment pc
	//
	int instr_loc = cpu -> pc;  // Instruction's location (pc before increment)
	cpu -> ir = cpu -> mem[cpu -> pc++];

	// Decode instruction into opcode, reg_R, addr_MM, and instruction sign
	//
	// *** STUB ****

	// Echo instruction
	//
	printf("At %02d instr %d %d %02d: ", instr_loc, cpu -> opcode, cpu -> reg_R, cpu -> addr_MM);

	switch (cpu -> opcode) {
	case 0: exec_HLT(cpu); break;
	// *** STUB ****
	default: printf("Bad opcode!? %d\n", cpu -> opcode);
	}
}

// Execute the halt instruction (make CPU stop running)
//
void exec_HLT(CPU *cpu) {
	printf("HALT\nHalting\n");
	cpu -> running = 0;
}
