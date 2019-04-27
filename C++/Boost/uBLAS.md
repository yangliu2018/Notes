# Boost::uBLAS
- BLAS (Basic Linear Algebra Subprograms)
    - routines that privide standard building blocks for performing basic vector and matrix operations
    - Level 1 BLAS: scaler, vector and vector-vector operations
    - Level 2 BLAS: matrix-vector operations
    - Level 3 BLAS: matrix-matrix operations
- LAPACK (Linear Algebra PACKage): based on BLAS
- Boost::uBLAS
    - unify mathematical notation via operator overloading
    - efficient code generation via expression templates

## uBLAS Overview

### Mathematical Notation
- overloading operators
    - indexing: ()
    - assignment: =, +=, -=, *=
    - unary operations: - 
    - binary operations: +, -
    - multiplication: *
- non-overloading operators
    - left multiplication of vectors with a matrix: prod
    - right multiplication of vectors with a matrix: prod
    - multiplication of matrices: prod
    - inner product of vectors: inner_prod
    - outer product of vectors: outer_prod
    - transpose of a matrix: trans
### efficiency
- eliminating temporaries: lazy evaluation
    - evaluate a complex expression element wise and to assign it directly to the target
    - alias
    - complexity
- eliminating virtual function calls
    - expression templates
    - contain lazy evaluation and replace dynamic polymorphism with static
    - compile time polymorphism

## Vector
- templated class vector<T, A>: base container adaptor for dense vectors
- templated class unit_vector<T, ALLOC>: canonical unit vectors
- templated class zero_vector<T, ALLOC>: zero vectors
- templated class scalar_vector<T, ALLOC>: scalar vectors

## Sparse Vector
- templated class mapped_vector<T, A>: base container adapter for sparse vectors using element maps
- templated class compressed_vector<T, IB, IA, TA>: base container adapter for compressed vectors
- templated class coordinate_vector<T, IB, IA, TA>: base container for compressed vectors
