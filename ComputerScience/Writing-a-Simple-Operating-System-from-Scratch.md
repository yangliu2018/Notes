# Writing a Simple Operating System from Scratch
### Nick Blundell

# Chapter 1 Intruduction

# Chapter 2 Computer Architecture and the Boot Process
- machine viutualisation: VMware or VirtualBox
- CPU emulator: Bochs or QEmu
- disk image file: the raw data (i.e. machine code and data) that would otherwise have written to medium of a hard disk, a floppy disk, a CDROM, USB stick, etc

# Chapter 3 Boot Sector Programming (in 16-bit Read Mode)
- interrupt: a mechanism that allow CPU temporarily to halt what it is doing and run some other, higher-priority instructions before returning to the original task
- soft interrupt: software instruction (e.g. int 0x10)
- hard interrupt: hardware device
- interrupt vector table
    - every index is a interrupt
    - a table initailly set up by BIOS
    - interrupt service routines (ISRs)
    - 0x10: screen-related ISR
    - 0x13: disk-related I.O ISR
    - multiplex: switch (ax) to choose BISO routine for interrupt
- 4 general purpose registers: ax, bx, cx, dx
- BIOS loads the boot sector to the address 0x7c00
- CPU treats the offset as though it was from the start from memory, rather than the start address of out loaded code
- [org 0x7coo]
    - at the top of code
    - assemblers correct label reference during assemblege
- ip: instruction pointer, call/ret, current instruction being executed
    - call: push return address to stack, then jmp
    - ret: pop return address from stack, then jmp
- pusha/popa: push and pop all registers to and from the stack respectively
- %include "file.asm"

# Chapter 4 Entering 32-bit Protected Mode
- switch the CPU from 16-bit read mode to 32-bit protected mode
    - define global descriptor table (GDT)
    - load GTB into the CPU
    - set a single bit in a special CPU control register to make the actual switch
- os kernel written C is not compiled to 16-bit instructions
- BIOS can no longer be used once switched into 32-bit protected mode
- BIOS routines can only be used in 16-bit read mode
- the 32-bit protected mode operating system can switch temporarily back into 16-bit read mode whereupon it may utilise BIOS
- display device
    - text mode
    - graphica mode
    - what diaplayed on the screen is a visual representation of a specific range of memory
    - manipulate memory -> manipulate screen
    - memory-mapped hardware
- segment descriptor (SG)
    - base address (32 bits): begin address of segment
    - segment limit (20 bits): size fo segment
    - various flags: privilige level, write/read, ...
- basic flat model: simplest workable configuration fo segment registers
    - code segment
    - data segment
    - two overlapping segments cover the full 4 GB of adressable memory
- null descriptor: exception machanism
- cli: clear interrupt, disable all interrupts
```asm
cli
lgdt [gdt_descriptor]
mov eax, cr0    ; control register
or eax, 0x1
mov cr0 eax     ; switch CPU from 16-bit mode to 32-bit mode
jmp CODE_SEG:start_protected_mode   ; flush the pipeline and start 32-bit code
```

# Chapter 5 Writing, Building, and Loading Your Kernel
- higher-level language compilers: map higher level constructs, such as control structures and function calls onto generic assembly template code
- downside: the generic assembly template may not always be optimal for specific functionality
- bp: base pointer of stack
- sp: top pointer of stack
- mov ebp, esp  ; create a local, initialy empty stack frame
- gcc
    - -E: preprocess, not complile or assemble
    - -S: compile, not assemble or link
    - -c: compile and assemble, not link
- objdump
    - -d, --disassemble: display assembler contents of executable sections
    - -D, --disassemble-all: display assembler contents of all sections
- Section contains static for the linker, segment dynamic data for the OS

