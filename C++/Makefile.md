
```
# list of source files, automatically match all source files
SRCS = $(wildcard *.c)

# list of object files, replace all .c with .o
OBJS = $(SRCS:.c=.o)

$(TAR) : $(OBJS)
    ...
```


```
PROJNAME := MyProject
PROJDIRS := subdir1 subdir2
SRCFILES := $(shell find $(PROJDIRS) -type f -name "\*.c")
OBJFILES := $(patsubst %.c,%.o,$(SRCFILES))
DEPFILES := $(patsubst %.c,%.d,$(SRCFILES))

WARNINGS := -Wall -Wextra -pedantic -Wshadow -Wpointer-arith -Wcast-align \
            -Wwrite-strings -Wmissing-prototypes -Wmissing-declarations \
            -Wredundant-decls -Wnested-externs -Winline \
            -Wuninitialized -Wconversion -Wstrict-prototypes
CFLAGS := -g -std=c99 $(WARNINGS)

.PHONY: all clean

all: $(PROJNAME)

clean:
    -@$(RM) $(wildcard $(OBJFILES) $(DEPFILES) $(PROJNAME))

-include $(DEPFILES)

$(PROJNAME): $(OBJFILES)
    @$(CC) $(LDFLAGS) $^

%.o: %.c Makefile
    @$(CC) $(CFLAGS) -MMD -MP -c $< -o $@
```