# Doxygen
### generate documentation from source code
- the de facto standard tool for generating documentation from annotated C++ sources
- generate documentation in multi-formats
- configure doxygen to extract the code structure from undocumented source files

## Getting Started
- create a configuration file `doxygen -g <config-file>`
- run doxygen `doxygen <config-file>`
- document the sources


## Documenting the code
### Comment blocks for C-like languages
- Javadoc style (C-style comment block starting with two *'s)
```c
/**
 * ... text ...
 */

/**
 ... text ...
 */
```
- Qt style (C-style comment block starting with two *!)
```c
/*!
 * ... text ...
 */

/*!
 ... text ...
 */
```
- multi C++ comment lines, where each line starts with an additional slash or exclamation
```c
///
/// ... text ...
///

//!
//! ... text ...
//!
```
- more visible
```c
/*******************//**
 * ... text ...
 **********************/

////////////////////////
/// ... text ...
////////////////////////

/***********************
 * ... text ...
 **********************/

```
### Putting documents after members
```c
int val;    /*!< detailed description */

int val;    /**< detailed description */

int val;    //!< detailed description
            //!<

int val;    ///< detailed description
            ///<

int val;    /*!< detailed description */
```
