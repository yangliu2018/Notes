### SSH
- secure shell
- a cryptographic network protocol for operating network services securely over an unsecured network

### SHA
- SHA-2: secure hash algorithm 2
    - a set of cryptographic hash functions
    - SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, SHA-512/256
#### SHA-256
- 产生256 bit的消息摘要，即32 byte，常用64个十六进制位表示
- SHA256算法使用8个哈希初值和64个哈希常量
- 8个哈希初值是自然数中的前8个质数(2, 3, 5, 7, 11, 13, 17, 19)的平方根的小数部分的前32个二进制位
- 64个哈希常量是自然数中的前64个质数的立方根的小数部分的前32个二进制位
```c
h0 := 0x6a09e667
h1 := 0xbb67ae85
h2 := 0x3c6ef372
h3 := 0xa54ff53a
h4 := 0x510e527f
h5 := 0x9b05688c
h6 := 0x1f83d9ab
h7 := 0x5be0cd19

428a2f98 71374491 b5c0fbcf e9b5dba5
3956c25b 59f111f1 923f82a4 ab1c5ed5
d807aa98 12835b01 243185be 550c7dc3
72be5d74 80deb1fe 9bdc06a7 c19bf174
e49b69c1 efbe4786 0fc19dc6 240ca1cc
2de92c6f 4a7484aa 5cb0a9dc 76f988da
983e5152 a831c66d b00327c8 bf597fc7
c6e00bf3 d5a79147 06ca6351 14292967
27b70a85 2e1b2138 4d2c6dfc 53380d13
650a7354 766a0abb 81c2c92e 92722c85
a2bfe8a1 a81a664b c24b8b70 c76c51a3
d192e819 d6990624 f40e3585 106aa070
19a4c116 1e376c08 2748774c 34b0bcb5
391c0cb3 4ed8aa4a 5b9cca4f 682e6ff3
748f82ee 78a5636f 84c87814 8cc70208
90befffa a4506ceb bef9a3f7 c67178f2
```
- 信息预处理
    - 附加填充比特
        - 在报文末尾填充，使报文长度对512取模后余数为448
        - 填充规则：先补一个比特位1，然后都补0
        - 填充必须发生，如果初始报文长度满足对512取模后余数448，则补1位1和511位0
    - 附加长度
        - 将原始消息的长度附加到填充后的消息后面，64位
        - 448 + 64 = 512
        - 原始消息长度的上限是$2 ^ {64}$
        - 长度信息的编码方式为64-bit big-endian integer
- 逻辑运算
    - SHA256散列函数中涉及的操作全部是逻辑的位运算
    
```c
Note: all variables are unsigned 32 bits

Initialize variables:
h0 := 0x6a09e667
h1 := 0xbb67ae85
h2 := 0x3c6ef372
h3 := 0xa54ff53a
h4 := 0x510e527f
h5 := 0x9b05688c
h6 := 0x1f83d9ab
h7 := 0x5be0cd19


Initialize table of round constants:
k[0..63] :=
   0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
   0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
   0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
   0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
   0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
   0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
   0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
   0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2

Pre-processing:
append the bit '1' to message
append k bits '0' to message, k is the minimun positive integer meeting (length + k) mod 512 = 448 
append length of message (before pre-processing), 64-bits big-edian integer
==> message length mod 512 == 0

Process the message in successive 512-bit chunks:
break message into 512-bit chunks
for each chunk:
    break chunk into 16 32-bit big-endian words w[0..15]
    expand the 16 32-bit words into 64 32-bit words:
    for i from 16 to 63:
        s0 := (w[i - 15] rightrotate 7) xor (w[i - 15] rightrotate 18) xor (w[i - 15] rightshift 3)
        s1 := (w[i - 2] rightrotate 17) xor (w[i - 2] rightrotate 19) xor (w[i - 2] rightshift 10)
        w[i] := w[i - 16] + s0 + w[i - 7] + s1

    Initialize hash values:
    a := h0
    b := h1
    c := h2
    d := h3
    e := h4
    f := h5
    g := h6
    h := h7

    Main loog:
    for i from 0 to 63:
        s0 := (a rightrotate 2) xor (a rightrotate 13) xor (a rightratate 33)
        maj := (a and b) xor (a and c) xor (b and c)
        t2 := s0 + maj
        s1 := (e rightrotate 6) xor (e rightrotate 11) xor (x rightrotate 25)
        ch := (e and f) xor ((not e) and g)
        t1 := h + s1 + ch + k[i] + w[i]
        h := g
        g := f
        f := e
        e := d + t1
        d := c
        c := b
        b := a
        a := t1 + t2

    Add hash fo this chunk to result:
    h0 := h0 + a
    h1 := h1 + b
    h2 := h2 + c
    h3 := h3 + d
    h4 := h4 + e
    h5 := h5 + f
    h6 := h6 + g
    h7 := h7 + h

Produce the final hash value (256-bit big-endian):
digest = h0 append h1 append h2 append h3 append h4 append h5 append h6 appand h7 append h8
```