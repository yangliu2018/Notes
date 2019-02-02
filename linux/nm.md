Usage: nm [option(s)] [file(s)]
 List symbols in [file(s)] (a.out by default).
 The options are:
  -a, --debug-syms       Display debugger-only symbols
  -A, --print-file-name  Print name of the input file before every symbol
  -B                     Same as --format=bsd
  -C, --demangle[=STYLE] Decode low-level symbol names into user-level names
                          The STYLE, if specified, can be `auto' (the default),
                          `gnu', `lucid', `arm', `hp', `edg', `gnu-v3', `java'
                          or `gnat'
      --no-demangle      Do not demangle low-level symbol names
  -D, --dynamic          Display dynamic symbols instead of normal symbols
      --defined-only     Display only defined symbols
  -e                     (ignored)
  -f, --format=FORMAT    Use the output format FORMAT.  FORMAT can be `bsd',
                           `sysv' or `posix'.  The default is `bsd'
  -g, --extern-only      Display only external symbols
  -l, --line-numbers     Use debugging information to find a filename and
                           line number for each symbol
  -n, --numeric-sort     Sort symbols numerically by address
  -o                     Same as -A
  -p, --no-sort          Do not sort the symbols
  -P, --portability      Same as --format=posix
  -r, --reverse-sort     Reverse the sense of the sort
      --plugin NAME      Load the specified plugin
  -S, --print-size       Print size of defined symbols
  -s, --print-armap      Include index for symbols from archive members
      --size-sort        Sort symbols by size
      --special-syms     Include special symbols in the output
      --synthetic        Display synthetic symbols as well
  -t, --radix=RADIX      Use RADIX for printing symbol values
      --target=BFDNAME   Specify the target object format as BFDNAME
  -u, --undefined-only   Display only undefined symbols
  -X 32_64               (ignored)
  @FILE                  Read options from FILE
  -h, --help             Display this information
  -V, --version          Display this program's version number


nm是names的缩写，列出文件中的符号（函数和全局变量）
value type name
type:
    小写：local
    大写：global(external)
    
静态库是对象文件的打包，只经过编译过程，没有链接过程，即使只有函数声明也可以成功编译静态库
动态库有其他动态库的链接，有依赖关系，不需要在工程中显式依赖动态库依赖的其他动态库
编译库时，可以只include头文件，不链接其他库，编译可执行文件时才链接动态库，但不推荐这么做

-Wl,-export-dynamic


静态函数库（static libraries）：链接时加载
共享函数库（shared libraries）：程序启动时加载
动态加载函数库（dynamically loaded libraries）：程序运行时加载，使用api

.a中可以增加.o
静态库可以编译生成动态库吗？

Windows
运行时库(.dll)
静态库(.lib)
动态库 = 导入库(.lib) + 动态库(.dll) 

Windows下加载动态库的方法
1. 隐式加载/静态加载/加载时动态链接
在程序中包含.h和.lib文件
#include "somedll.h"
#pragma comment(lib, "somedll.h")
若加载失败将中止进程
2. 显式加载/动态加载/运行时动态链接
不需要.h和.lib，使用api加载
若加载失败可以尝试恢复


编译boost
    link
    runtime-link

只有动态库没有头文件：创建函数指针，动态加载

动态库和静态库可以互相编译吗？

用动态库做动态库
用动态库做静态库
用静态库做静态库
用静态库做动态库


静态库是.o文件的集合，动态库是可执行文件的形式

编译器标志
链接器标志
