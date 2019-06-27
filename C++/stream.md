

### stream
- text stream
    - an ordered sequence of characters composed into lines (zero or more characters plusing a terminating '\n')
    - characters may have to be added, altered, or deleted on input and output to conform to the conventions for representing text in the OS
    - C streams on Windows OS convert \n to \r\n on output, and convert \r\n to \n on input
- binary stream
    - an ordered sequence of characters that can transparently record internal data
    - data read in from a binary stream always equels to the data that were earlier written out to that stream
- POSIX implementations do not distinguish between text and binary streams (no special mapping for \n or any other characters)
- when data read in from a text stream is equal to data earlier written to the stream
    - the data consist only of printing characters and the control characters \t and \n (on Windows OS, the character '\0x1A' terminates input)
    - no \n is immediately preceded by a space character (space characters written out immediately before a \n may disappear when read)
    - the last character is \n
- byte stream: 8-bit bytes
- character stream: fixed-length or varying-length
- bit stream
- buffered stream
```cpp
template< 
    class CharT, 
    class Traits = std::char_traits<CharT>
> class basic_iostream;

template< 
    class CharT, 
    class Traits = std::char_traits<CharT>, 
    class Allocator = std::allocator<CharT>
> class basic_string;
```
- streams in c++ std are fixed-length character streams
- strings in c++ std ara fixed-length character strings
    - used to inspect object representations
### character types
- signed char: type for signed character representation
- unsigned char: type for unsigned character representation
- char: can be most efficiently processed on the target system
    - large enough to represent any UTF-8 code unit, 8 bits
    - signedness depends on the compiler and the target platform
        - unsigned on ARM and PowerPC
        - signed on X86 and X64
- wchar_t: type for wide character representation
    - same size, signedness
    - large enough to represent any supported character code point
    - 32 bits on systems that support Unicode
    - exception: 16 bits on Windows and holds UTF-16 code units
- char16_t: type for UTF-16 character representation, 16 bits
- char32_t: type for UTF-32 character representation, 32 bits
- char8_t: type for UTF-8 character representation, 8 bits, since C++ 20

### 字符编码
- Unicode: Universal Coded Character Set: 通用编码字符集
- UTF: Unicode Transformation Format: 统一码转化格式
- ASCII: 8 bits, 7 bits表示字符, 1 bit奇偶校验，127个字符，只能用于表示英文
- Unicode: 常用双字节表示一个字符，保存纯英文有一倍空间冗余，不兼容ASCII
- UTF-8: 可变长字符编码，1~6字节，兼容ASCII，无空间冗余
    - 英文字母占用1字节，中文汉字通常占用2字节
- 计算机内存中统一使用unicode编码，传输或存储至硬盘时转换为UTF-8编码