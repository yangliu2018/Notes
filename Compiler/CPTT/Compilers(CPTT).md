# 编译原理
# Compilers: Principles, Techniques and Tools
### Alfred V. Aho, , 2nd Edition



---

# 1 Introduction

## Language Processors
- compiler: a program that can read a program in one language and translate it into an equivalent program in another language (from source language to target language)
- interpreter: directly execute the operations specified in the source program on inputs and generate outputs
- Java language processors combine compilation and interpretation
- preprocessor: from source code to modified source code
- compiler: from modified source code to target assembly code
- assembler: process assembly language and produce relocatable machine code
- linker: resolve external memory addresses
- loader: put together all the executable objects files into memory for execution

## The Structure of a Compiler
- analysis: front end
  - grammatical structure
  - create an intermediate representation of the source program
  - symbol table: information about source program
- synthesis: back end
  - construct target program from the intermediate representation and symbol table
- phases: (symbol table throughout)
  - (character stream) Lexical Analyzer (token stream)
  - Syntax Analyzer (syntax tree)
  - Semantic Analyzer (syntax tree)
  - Intermediate Code Generator (intermediate representation)
  - Machine-Independent Code Optimizer (intermediate representation)
  - Code Generator (target-machine code)
  - Machine-Dependent Code Optimizer (target-machine code)

### Lexical Analysis
- lexical analysis / scanning
- lexeme => token: <token-name, attribute-value>
  - token-name: an abstract symbol used during syntax analysis
  - attribute-value: points to an entry in the symbol table

### Syntax Analysis
- syntax analysis / parsing
- syntax analyzer: uses the first components of tokens produced by lexical analyzer to create a tree-like intermediate representation that depicts the grammatical structure of the token stream

### Semantic Analysis
- semantic analyzer: uses the syntax tree and the information in symbol table to check source program for semantic consistency with the language definition; gathers type information
- type checking: check that each operator has matching operands
- coercion: type conversion

### Intermediate Code Generation
- low-level or machine-like intermediate representation
- a program for an abstract machine
  - easy to produce
  - easy to translate to target machine
- three-address code: three operands per instruction

### Code Optimization
- improve the intermediate code to result at better target code
- perform during compile time

### Code Generation
- the judicious assignment of registers to hold variables
- storage-allocation decisions are made ether during intermediate code generation or during code generation

### Symbol-Table Management
- the name and attributes of variables
  - name
  - type
  - scope
  - procedure: name, arguments, return type
- data structure to find recode quickly and store or retrieve data from record quickly

### The Grouping of Phases into Passes
- pass: group several phases together, read an input file and write an output file

### Compiler-Construction Tools
- scanner generator: produce analyzers from a regular-expression description of tokens
- parser generator: produce syntax analyzers from a grammatical description
- syntax-directed translation engine: produce collections of routines for walking a parse tree and generating intermediate code
- code-generator generator: produce a code generator from a collection of rules
- data-flow analysis engine: information about value transmission
- compiler-construction toolkit: provide an integrated set of routines for constructing various phases of a compiler

## The Science of Building a Compiler
- formulate a mathematical abstraction from a problem
- design the right mathematical models and choosing the right algorithms
- models: finite-state machines and regular expressions
- balance maintenance, performance, and correctness
- prioritize infinite optimizations and implement those tha lead to the greatest benefits

## Applications of Compiler Technology
- implementation of high-level programming languages
  - optimizing compilers offset the inefficiency introduced by high-level abstractions
- Optimizations for Computer Architectures
  - parallelism
    - instruction-level parallelism: order, instruction set: VLIW (Very Long Instruction Word)
    - process-level parallelism: automatically generate parallel code from sequential code
  - memory hierarchies
    - use registers effectively
    - change the layout of the data
    - change the order of instructions accessing the data
    - change the layout of the code
- Design of New Computer Architectures
  - RISC (Reduced Instruction-Set Computer)
  - Specialized Architectures
- Program Translations
  - Binary Translation: translate binary code for one machine to that of another; backward compatibility
  - Hardware Synthesis: high-level hardware description languages
  - Database Query Interpreters: query languages
  - Compiled Simulation: compile the simulation design to native machine code
  - Software Productivity Tools: error detector (data-flow analysis)
  - Type Checking: errors; security holes
  - Bounds Checking
  - Memory-Management Tools

## Programming Language Basics
- static/dynamic distinction
  - static policy: decide at compile time
  - dynamic policy: decide at run time
  - static/dynamic scope
- environments and states
  - environment: mapping from names to locations in store
  - state: mapping from locations in store to values
  - static/dynamic binding from names to locations
  - static/dynamic binding from locations to values
- static scope and block structure
- explicit access control
- dynamic scope
- parameter passing mechanisms
  - actual parameters, formal parameters
  - call by value, call by reference, call by name
- aliasing


# 2 A Simple Syntax-Directed Translator
- syntax-directed translator
  - map infix arithmetic expressions into postfix expressions
  - map code fragments into three-address code
- syntax: form of programs; context-free grammars, BNF (Backus-Naur From)
- semantics: mean of programs

## Syntax Definition
- expr: expression
- stmt: statement
- if-else statement production: stmt -> if (expr) stmt else stmt
- 4 components of a context-free grammar
  - a set of terminal symbols: tokens, elementary symbols
  - a set of non-terminals: syntactic variables
  - a set of productions: head, arrow, and body
  - a designation of one of the non-terminals as the start symbol
- derivation: parsing is the problem of taking a string of terminals and figuring out how to derive it from the start symbol of the grammar
- parse tree: how the start symbol of a grammar derives a string in the language
  - root: start symbol
  - lead: terminal or null
  - interior node: non-terminal
- ambiguity: more than one parse tree generating a given string of terminals
  - design unambiguous grammars
  - use ambiguous grammars with additional rules to resolve ambiguities
- associativity of operators
  - left-associative: addition, subtraction, multiplication, division
  - right-associative: exponentiation, assignment
- precedence of operators: convert to list of terms
```
# a grammar for arithmetic expressions
# factor: cannot be torn apart
# term: can be torn apart by * and / (not by the lower-precedence operators)
# expr: can be torn apart by + and - (by any operator)

factor  ->  digit | (expr)
term    ->  term * factor | term / factor | factor
expr    ->  expr + term | expr - term | term
```

```
# a grammar for a subset of Java statements
# ε: empty list

stmt    ->  id = expr;
        |   if (expr) stmt
        |   if (expr) stmt else stmt
        |   while (expr) stmt
        |   do stmt while (expr);
        |   { stmts }
stmts   -> stmts stmt
        |   ε
```

## Syntax-Directed Translation
- 2 concepts related to syntax-directed translation
  - attribute: any quantity associated with a programming construct
  - translation scheme: a notation for attaching program fragments to the productions of a grammar
- postfix notation
  - not need parentheses
- synthesized attribute
  - synthesized: attribute value determined from attribute values at children of N and at N itself
  - can be evaluated during a single bottom-up traversal of a parse tree
- inherited attribute: value determined from attribute values at the node it self, its parent, and its siblings
- the simple syntax-directed definition: the string representing the translation of the non-terminal at the head of each production is the concatenation of the translations of the non-terminals in the production body
- tree traversal
- semantic action: program fragment embedded within production body

## Parsing
- the process of determining how a string of terminals can be generated by a grammar
- 2 parsing methods
  - recursive descent
  - a software tool to generate a translator directly from a translation scheme (Yacc, Bison)
- time
  - context-free grammar parser: at most O(n^3) time to parse a string of n terminals
  - real programming languages: linear-time algorithms
- 2 parsing methods: (parsing tree construction order)
  - top-down: easy to construct by hand
  - bottom-up: can handle a larger class of grammars and translation schemes
- top-down parsing
  - a single left-to-right scan of the input string
  - lookahead symbol: the current terminal being scanned in the input
  - trial-and-error: try a production and backtrack to try another if unsuitable
  - predictive parsing: remove backtracking
- predictive parsing
  - recursive-descent parsing: top-down
  - predictive parsing: the lookahead symbol unambiguously determines the flow of control through procedure body for each nonterminal
- left recursion: A -> Aα | β
  - leftmost symbol of the body is the same as the nonterminal at the head of the production
  - α and β are sequences of terminals and nonterminals not starting with A
  - expr -> expr + term | term
    - α: + term
    - β: term
  - equivalent to right recursion: A -> βR, R -> αR | ε
  - a predictive parser cannot handle a left-recursive grammer

## A Translator for Simple Expression
- translate arithmetic expressions into postfix form
```
// actions for translating into postfix notation

expr    ->  expr + term { print('+) }
        |   expr - term { print('-) }
        |   term
term    ->  0   { print('0') }
        |   1   { print('1') }
            ...
        |   9   { print('9') }
```
- translate vs. parse: begin with the grammar for easy translation and carefully transform it to facilitate parsing
- abstract and concrete syntax
  - abstract syntax tree
    - interior node: operator
    - children of the node: operands of the operator
  - parse tree vs. syntax tree
    - parse tree: interior nodes represent nonterminals => concrete syntax tree
    - syntax tree: interior nodes represent operator
- adapting the translation scheme
  - left-recursion-elimination technique
```
// left-recursion-elimination technique

A -> Aα | Aβ | γ

---

A -> γR
R -> αR | βR | ε
```
```
expr -> term rest
rest -> + term { print('+') } rest
    |   - term { print('-') } rest
    |   ε
term -> 0   { print('0') }
    |   1   { print('1') }
            ...
    |   9   { print('9') }
```
- simplifying the translator
  - tail recursive calls can be replaced by iterations

```cpp
#include <iostream>
#include <cctype>
using namespace std;

template <typename T>
void print(T t) {
    cout << t;
}

class Parser {
public:
    void expr() {
        lookahead = getchar();
        term();
        while (true) {  // rest
            switch (lookahead) {
            case '+': match('+'); term(); print('+'); break;
            case '-': match('-'); term(); print('-'); break;
            default: return;
            }
        }
    }

private:
    void term() {
        if (isdigit(lookahead)) {
            print(lookahead); match(lookahead);
        }
        else throw exception("syntax error");
    }

    void match(int t) {
        if (lookahead == t) lookahead = getchar();
        else throw exception("syntax error");
    }

    char lookahead = 0;
};

int main() {
    Parser parser;
    try {
        while (true) {
            parser.expr();
            cout << endl;
        }
    }
    catch (const exception& e){
        cout << e.what() << endl;
    }
    return 0;
}
```

## Lexical Analysis
- lexical analyzer
  - group characters into token objects
  - insulate a parser from the lexeme representation of tokens
- lexeme: a sequence of input characters that comprises a single token
- removal of white space and comments
- reading ahead
  - maintain an input buffer from which the lexical analyzer can read and push back characters
  - input buffering techniques
- constants: integer constants
- recognize keywords and identifiers: using a table to hold strings
  - single representations: insulate the rest of compiler from the representation of strings
  - reserved words: initialize the string table with reserved strings and their tokens
```
// actions for translating into postfix notation

expr    ->  expr + term     { print('+) }
        |   expr - term     { print('-) }
        |   term
term    ->  term * factor   { print('*') }
        |   term / factor   { print('/') }
        |   factor
factor  ->  (expr)
        |   num             { print(num.value) }
        |   id              { print(id.lexeme) }
```
```pseudocode
// removal of white space and comments
for (;; peek = next input character) {
    if (peek is a blank or a tab) do nothing;
    else if (peek is a newline) line = line + 1;
    else break;
}
```
```pseudocode
// grouping digits into integers
if (peek holds a digit) {
    v = 0;
    do {
        v = v * 10 + integer value of digit peek;
        peek = next input character;
    } while (peek holds a digit);
    return token <num, v>;
}
```
```pseudocode
// distinguish keywords from identifiers
// Hashtable words = new HashTable();

if (peek holds a letter) {
    collect letters or digits into buffer b;
    s = string formed from the characters in b;
    w = token returned by words.get(s);
    if (w is not null) return w;
    else {
        enter the key-value pair (s, <id, s>) into words
        return token <id, s>;
    }
}
```
```pseudocode
// a lexical analyzer
Token scan() {
    skip white space
    handle numbers
    handle reserved words and identifiers
    Token t = new Token(peek);
    peek = blank;
    return t;
}
```

## Symbol Tables
- symbol table: data structure used by compiler to hold information about source-program constructs
- implement scopes by setting up a separate symbol table for each scope
  - a class has its own symbol table, with an entry for each field and method
- symbol table per scope
  - most-closely nested rule: the stack of chained symbol tables
  - a hash table and an auxiliary stack to keep track of changes to the hash table
  - pass information from declarations to uses
```
// translation scheme with symbol tables for blocks
// top: the current symbol table
// saved: the previous symbol table

program ->  block   { top = null; }

block   ->  '{'     { saved = top; top = new Env(top); print("{"); }
            decls stmts '}' { top = saved; print("} "); }

decls   ->  decls decl | ε

decl    ->  type id; { sym = new Symbol(); sym.type = type.lexeme; top.put(id.lexeme, sym)}

stmts   ->  stmts stmt | ε

stmt    ->  block
        |   factor  { print("; "); }

factor  ->  id  { sym = top.get(id.lexeme); print(id.lexeme); print(":"); print(sym.type); }

```

## Intermediate Code Generation



# 3 Lexical Analysis
# 4 Syntax Analysis
# 5 Syntax-Directed Translation
# 6 Intermediate-Code Generation
# 7 Run-Time Environments
# 8 Code Generation
# 9 Machine-Independent Optimizations
# 10 Instruction-Level Parallelism
# 11 Optimizing for Parallelism and Locality
# 12 Inter-procedural Analysis
