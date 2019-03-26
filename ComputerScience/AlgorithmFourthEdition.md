# Algorithm Fourth Edition
### Robert Sedgewich and Kevin Wayne

# 5 Strings

- computational challenges about processing strings
    - information processing
    - genomics
    - communications systems
    - programming systems
- alphabets
    - character-indexed arrays
    - numbers
    - string ==> R = 256, externded ASCII

## 5.1 String Sorts
- more efficient than the general-purpose sorts
- two fundamentally string sorts
    - least-significant-digit(LSD): right-to-left order
    - most-significant-digit(MSD): left-to-right order

### Key-indexed counting
- effective and stable whever the keys are small integers
- steps
    - compute frequency counts
    - transform counts to indices
    - distribute the data
    - copy back

### LSD string sort

## 5.3 Substring Search
- given a text string of length N and a pattern string of length M, find an occurence of the pattern within the text

### A short history
- brute-force algorithm
- Knuth-Morris-Pratt(KMP) algorithm
- Boyer-Moore algorithm
- M.O.Rabin and R.M.Karp hashing algorithm

### Brute-force substring search

### Knuth-Morris-Pratt substring search

### DFA simulation

### Boyer-Moore substring search

### Rabin-Karp fingerprint search

## 5.4 Regular Expression
- partial-substring-searching problems
- pattern-matching problems/algorithms/machines: construct and simulate
- nondeterminism

### Describing patterns with regular expressions
- language: a set of strings(posibly infinite)
- pattern: a language specification
- three fundamental operation
    - concatenation: AB: {AB}, cross product
    - or: A|B: {A|B}, {A, B}, specify alternatives, union
    - closure: A*: any number of repeated times including zero
    - precedence: parentheses > closure > or > concatenarion

### Shortcuts
- set-of-characters descriptors: shorts for a sequence of or operations
    - wildcard: dot character(.)
    - specified set: enclosed in []
    - range: enclosed in [] and separated by -
    - complement: enclosed in [] and preceded by ^
- closure shortcuts
    - plus sign(+): at least one copy
    - question mark(?): zero or one copy
    - count or range within braces({}): a given number of copies
- escape sequence
    - metacharacters: \, ., |, *, (, )
    - backslach \ + metacharacter
    - \t: a tab character
    - \n: a newline character
    - \s: any whitespace character

### REs in applications
- substring search: to search for a substring pat in a text string txt is to check whether txt matches the pattern .\*pat.* or not
- validity checking: an RE that describes the set of all legal inputs
- programmer's toolbox: Unix command grep
- genomics: gene sequence
- search: web search engines
- possibilities: (0|1(01*0)\*1)\*
- limitations
    - no RE can describe the set of all strings that specify legal REs
    - cannot use REs to check whether parentheses are balanced
    - cannot use REs to check whether a string has an equal number of As and Bs

### Nondeterministic finite-state automata(NFA)
- RE pattern matching algorithm
    - build the NFA corresponding to the given RE
    - simulate the operation of that NFA on given text
- Kleene'a Theorem: there is an NFA corresponding to any given RE, and vice versa
- two ways for moving from one state to another
    - match transition
        - the current state corresponds to a character in alphabet
        - the current character in the text string matches the character
        - the automaton can scan past the character in the text string and take the black transiton to the next state
    - ε-transition
        - the automaton can follow any red edge to another state without scanning any text character
        - matching the empty string ε
- the text matches the pattern => the NFA recognizes the text
- the key difference between NFAs and DFAs
    - multiple edges leaving a given state => transition not deterministic
    - guess which transition(if any) will lead to the accept state
- an NFA recognizes a text string if and only if thers is some sequence of match transitions and ε-transitions that scans all the text characters and ends in the accept state when started at the beginning of text in state 0


### Simulating an NFA
- representation
    - states
        - the integers between 0 and M, where M is the number of characters in the RE
    - match transitions
        - an array re[] of char values
        - if re[i] is in the alphabet, then there is a match transition from i to i+1
    - ε-transitions
        - digraph G
        - directed edges connecting vertices between 0 and M
- NFA simulation and rechability
    - keep track of the set of states that could possibly be encountered while the automaton is examining the current input character
- Proposition: determining whether an N-character text string is recognized by the NFA corresponding to an M-character RE takes time proportional to NM in the worst case

### Building an NFA corrresponding to an RE
- Dijkstra's two-stack algorithm: evaluate an arithmetic expression
- evaluate a RE: one stack to keep track of the positions of left parentheses and or operators
- concatenation: match transition
- parentheses
    - push the RE index of each left parenthesis on the stack
    - pop when encountering a right parenthesis
- closure
    - occur after a single character: ε-transition to and from the character
    - occur after a right parentnesis: ε-transition to and from the corresponding left parenthesis(top of stack)
- or expression
    - (A|B), where A and B are both REs => add two ε-transition
    - from the state corresponding to the left parenthesis to the state corresponding to the first character of B
    - from the state corresponding to the | operator to the state corresponding to the right parenthesis
    - push the RE index corresponding the | operator onto the stack
    - do not add an ε-transition from the state corresponding to the | operator to the state with the next higher index
- Proposition: building the NFA corresponding to an M-character RE takes time and space proportional to M in the worst time
- grep: Generalized Regular Expression Pattern-matching