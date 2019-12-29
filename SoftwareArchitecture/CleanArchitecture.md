# PART I Introduction
## Chapter 1 What is Design and Architecture?
- There is no difference between design and architecture: low-level details and high-level structure
- The goal of software architecture is to minimize the human resources required to build and maintain the required system
- The measure of design quality is simply the measure of the effort required to meet the needs of the customer
- making messes is always slower than staying clean
- TDD: test-driven development
- The only way to go fast, is to go well
- recognize and avoid overconfidence, take software architecture seriously


## Chapter 2 A Tale of Two Values
- every software system provides two different values to the stakeholders: behavior and structure
- soft: easy to change
- President Dwight D.Eisenhower's matrix of importance versus urgency
    - I have two kinds of problems, the urgent and the important. The urgent are not important, and the important are never urgent.
    - functionality: urgent but not always particularly important
    - architecture: import but never particularly urgent
- It is the responsibility of software development team to assert the importance of architecture over the urgency of features
- Software architects are more focused on the architecture of the system than on its features and functions
- Architects create an architecture than allows features and functions to be easily developed, easily modified, and easily extended


# PART II Programming Paradigms 
- programming languages: tools of programming
- programming paradigms: ways of programming, relatively unrelated to languages
- paradigms are all about /*what not to do*/
- Software is composed of sequence, selection, iteration, and indirection. Nothing more. Nothing less.

## Chapter 3 Paradigm Overview
- structured programming
    - unrestrained jumps (goto statements) is harmful to program structure
    - replace with if/then/else and do/while/until constructs
    - structured programming imposed discipline on direct transfer of control
- objected-oriented programming
    - move the function call stack frame to a heap => class, expand lifetime
        - function => class constructor
        - local variables => fields
        - nested functions => methods
    - objected-oriented programming imposed discipline on indirect transfer of control
- functional programming
    - immutability: a foundational notion than the values of symbols do not change
    - no assignment statement
    - functional programming imposed discipline upon assignment
- the three paradigms together removes goto statements, function pointers, and assignment
- three big concerns of architecture: function, separation of components, and data management

## Chapter 4 Structured Programming
- bad goto statements: prevent modules from being decomposed recursively into smaller and smaller units
- good goto statements: correspond to simple selection and iteration control structures
- all programs can be constructed from just three structures: sequence, selection, and iteration
- functional decomposition: modules can be recursively decomposed into provable units
- the nature of scientific theories and laws: falsifiable but not provable
- Testing show the presence, not the absence, of bugs
- programming is like science rather than math: falsifiability


## Chapter 5 Object-Oriented Programming
- OO: encapsulation, inheritance, and polymorphism
    - easy and effective encapsulation of data and function
    - inheritance: the redeclaration of a group of variables and functions within an enclosing scope
- non-OO encapsulation: data structures and functions declared in header files and implemented in implementation files
- OO encapsulation: class and access control (public, protected and private)
- non-OO polymorphism: explicitly using pointers to functions (dangerous and complex) 
    - example: device independent, FILE* (no need to recompile when replacing device, plugin architecture)
- OO polymorphism: all calls to virtual functions go through vtable

### dependency inversion
- no polymorphism: source code dependencies inexorably follow the flow of control
    - high-level code depends on low-level code
    - constrained to align source code dependencies with the flow of control
- polymorphism: any source code dependency, no matter where it is, can be inverted
    - invoker and implement both depend on interface
    - absolute control over the direction of all source code dependencies in the system
    - make low-level modules to be plugins of high-level modules
- independent developability and independent deployability: develop, compile, deploy, change and redeploy independently for both low-level and high-level modules

## Chapter 6 Functional Programming
- based on l-calculus (lambda calculus)
- mutable variable: loop control variable that changes state during the execution of program
### immutability
- no mutable variables in functional language, initialized but never modified
- all race conditions, deadlock conditions, and concurrent update problems are due to mutable variables
- all problems in concurrency programming ...
- immutability is practicable if having infinite storage and infinite processor speed

### segregation of mutability
- segregate the application, or the services within the application, into mutable and immutable components
    - immutable components: purely functional way without any mutable variables
    - mutable components: communicate with other components and transactional memory
    - push as much processing as possible into the immutable components
- transactional memory: protect mutable variables from concurrent updates and race conditions
- compare and swap algorithm

### even sourcing
- even sourcing: store the transactions, but not the state
- when requiring state, simply apply all the transactions
- applications are not CRUD, but just CR (no updates or deletions, no concurrent update issues)


# PART III Design Principles
- SOLID: SRP, OCP, LSP, ISP, and DIP
    - how to arrange functions and data structures into groupings, and how to interconnect groupings
    - goal: the creation of mid-level soft structures that:
        - tolerate change
        - easy to understand
        - the basis of components that can be used in many software systems
    - mid-level: these principles are applied above the code level and at the module level
- SRP: each software module has one, and only one, reason to change
- OCP: allow the behavior of software systems to be changed by adding new code, rather than changing existing code
- LSP: to build software systems from interchangeable parts, those parts must adhere to a contract that allows those parts to be substituted one for another
- ISP: avoid useless dependencies
- DIP: the code that implements high-level policy should not depend on the code that implements low-level details. Rather, details should depend on policies.

## Chapter 7 SRP: The Single Responsibility Principle
- a function should do one and only one thing: lowest level, not SRP
- SRP descriptions
    - a module should have one, and only one, reason to change
    - a module should be responsible to one, and only one, user or stakeholder
    - a module should be responsible to one, and only one, actor
- users and stakeholders: the reason to change
- actor: the group (one or more people) who require the change
- a module is just a source file or a cohesive set of functions and data structures ("cohesive" implies the SRP)
- cohesion is the force that binds together the code responsible to a single actor
- accidental duplication and merges
    - separate the code that different actors depend on/supports different actors
    - solution 1: separate data from the functions and use Facade pattern (facade class -> function class -> data class)
    - solution 2: the original class keep the most import methods and is used as a Facade for the lesser functions (facade class including data and core functions -> function class)
- the Single Responsibility Principle is about functions and classes
    - component level: Common Closure Principle
    - architectural level: the Axis of Change responsible for the creation of Architectural Boundaries


## Chapter 8 OCP: The Open-Closed Principle
- a software artifact should be open for extension but closed for modification
- goal: make the system easy to extend without incurring a high impact of changes
- how OCP works
    - separate functionality based on how, why, and when it changes
    - then organize that separated functionality into a dependency hierarchy of components
    - higher-level components are protected from the changed made to lower-level components
- interface
    - directional control
    - information hiding: transitive dependencies are a violation of the general principle that software entities should not depend on things that they don't directly use


## Chapter 9 LSP: The Liskov Substitution Principle
- the LSP pertains to interfaces and implementations
    - interface
        - Java-style interface implemented by serval classes
        - Ruby classes that share the same method signatures
        - a set of services that all respond to the same REST interface
    - well-defined interfaces and the substitutability of the implementations
    

## Chapter 10 ISP: The Interface Segregation Principle
- ISP and language
    - statically typed languages: import, use, or include declarations that create source code dependencies to force recompilation and redeployment
    - dynamically typed languages: no such included declarations and no source code dependencies. flexible
- ISP and architecture: it is harmful to depend on modules that contain more than you need
- depending on something that carries baggage that you didn't need can cause you troubles that you didn't expect
- Common Reuse Principle: Component Cohesion


## Chapter 11 DIP: The Dependency Inversion Principle
- the most flexible systems are those in which source code dependencies refer only to abstractions, not to concretions
    - use, import, and include statements should refer only to source modules containing interfaces, abstract classes, or other kind of abstract declaration
    - a concrete module is any module in which the functions being called are implemented
    - tolerate concrete but stable dependencies
    - avoid depending on volatile concrete elements of system (those are actively developing modules)
- stable abstractions: abstract interfaces should be less volatile than concrete implementations
    - don't refer to volatile concrete classes (refer to abstract interfaces instead)
    - don't derive from volatile concrete classes
    - don't override concrete functions (when overriding concrete functions, not eliminate but inherit dependencies)
    - never mention the name of anything concrete and volatile
- factories: special handle the creation of volatile concrete objects
    - in virtually all languages, the creation of an object requires a source code dependency on the concrete definition of that object
- architecture boundary: separate the abstract from the concrete
    - the abstract component contains all the high-level business rules of the application
    - the concrete component contains all the implementation details that business rules manipulate
    - DIP: the flow of control crosses the architecture boundary in the opposite direction of the source code dependencies
- concrete components
    - dependencies in concrete components violate the DIP
    - DIP violations cannot be entirely removed, but can be gathered into a small number of concrete components and kept separate from the rest fo the system
    - most systems contains at least one such concrete component: often called main because containing main function
- Dependency Rule: the way the dependencies cross architecture boundaries in one direction and toward more abstract entities


# PART IV Component Principles
## Chapter 12 Components
### components
- the units of deployment
- can be linked together into a single executable, or aggregated together into a single archive, or independently deployed as separate dynamically loaded plugins
- independently deployable and independently developable

### relocatability
- relocatable binaries: binary code that could be relocated in memory by a smart loader
- external reference and external definition
    - the names of called/defined functions
    - link the external references to the external definitions

### linkers
- linking loader: allow dividing programs up onto separately compilable and loadable segments, but slow
- linker: outputs a linked relocatable that a relocating loader could load very quickly at any time
- component plugin architecture: the linking is so quick that we can link at load time (such as jar and dll)

## Chapter 13 Component Cohesion
- 3 principles of component cohesion
    - REP: Reuse/Release Equivalent Principle
    - CCP: Common Closure Principle
    - CRP: Common Reuse Principle

### reuse/release equivalent principle
- the granule of reuse is the granule of release
- module management tool
- classes and modules that are grouped together into a component should be releasable together, sharing the same version number and the same release tracking, and included under the same release document

### common closure principle
- Gather into components those classes that change for the same reasons and at the same times. Separate into different components those classes that change at different times and for different reasons
- SRP (module/class level) => CCP (component level)
- make changes gathered into one component instead of distributed across many components
- restrict change to a minimal number of components
- SRP & OCP: Gather together those things that change at the same time for the same reasons. Separate those things that change at different times or for different reasons.

### common reuse principle
- Don't force users of a component to depend on things they don't need.
- modules that tend to be reused together belong in the same component
- example: a container class and its associated iterators
- classes that are not tightly bound to each other should not be in the same component
- ISP & CRP: don't depend on things you don't need
    - ISP: don't depend on classes that have methods we don't use
    - CRP: don't depend on components that have classes we don't use

### conclusion
- REP & CCP: inclusive principle, tend to make components larger
- CRP: exclusive principle, drive components to be smaller
- REP: group for reuse
- CCP: group for maintenance
- CRP: split to avoid unneeded releases
- the component structure of a project can vary with time and maturity
- balance between reusability and develop-ability dynamically

## Chapter 14 Component Coupling
- 3 principles dealing with the relationships between components
    - ADP: Acyclic Dependencies Principle
- balance between develop-ability and logical design

### acyclic dependencies principle
- Allow no cycles in the component dependency graph.
- weekly build
    - all the developers ignore each other for the first four days of the week
    - then on Friday, integrate all changes and build the system
    - common in medium-size projects
- eliminating dependency cycles
    - partition the development environment into releasable components
    - the components become units of work that can be the responsibility of a single developer, or a team of developers
    - as new releases of a component are made available, other teams can decide whether immediately adopting the new release
    - change made to one component do not need to have an immediate affect on other teams
    - integration happens in small increments
- 2 mechanisms to break the cycle
    - dependency inversion principle
    - create new component depended on by other components
- top-down design
    - the component structure cannot be designed from the top down
    - component dependency diagrams are a map to the buildability and maintainability of the application, instead of the function of the application
    - component dependency diagrams are not design at the beginning

### stable dependencies principle
- Depend in the direction of stability.
- volatile components should not be depended on by stable components
- stability
    - components depended on: responsible and independent
- stability metrics
    - Fan-in: incoming dependencies (number of classes)
    - Fan-out: outgoing dependencies (number of classes)
    - I: Instability = Fan-out / (Fan-in + Fan-out)
- SDP
    - the I metric of a component should be larger than I metrics of components it depends on
    - I metrics should decrease in the direction of dependency
- not all components should be stable
    - otherwise the system is unchangeable
    - changeable components on the top depend on stable components at the bottom
- abstract component: very stable and an ideal target for less stable components to depend on 

### stable abstract principle
- A component should be as abstract as it is stable.
- some software in the system should not change very often: high-level architecture and policy decisions

# PART V Architecture
## Chapter 15 What Is Architecture?
## Chapter 16 Independence
## Chapter 17 Boundaries: Drawing Lines
## Chapter 18 Boundary Anatomy
## Chapter 19 Policy and Level
## Chapter 20 Business Rules
## Chapter 21 Screaming Architecture
## Chapter 22 The Clean Architecture