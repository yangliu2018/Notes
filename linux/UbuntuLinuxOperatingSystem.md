# Ubuntu Linux操作系统
### 张金石 钟小平 吴宁

# 用户与组管理

## 用户与组概述
- linux用户账户
    - 超级用户(super user): root
    - 系统用户(system user)
    - 普通用户(regular user)
- UID
    - 用户账户的唯一标识
    - root账户的UID为0
    - 系统账户的UID为1~499及65534
    - 常规用户的UID默认从1000开始编号(Ubuntu)
- 获取root权限  
    - su命令: 普通用户临时提升为root权限(需要root密码)
    - sudo命令: 临时使用root身份运行程序，执行完毕后返回普通用户状态
    - sudo -i: 切换至root身份登录(exit退出)
- Ubuntu默认禁用root用户，不设置root密码
    - 安装时创建的第一个用户成为管理员
    - 不能使用su升至root权限，必须使用sudo获取root权限
    - sudo su (root)
    - Ubuntu可以启用root用户，设置密码
- Ubuntu的普通用户分为两类
    - 标准账户
    - 管理员
        - 具有管理权限的普通用户，可以使用sudo命令获取root权限
        - /etc/sudoers中指定sudo用户及其可执行的特权命令
- 组账户/用户组
    - 用户与组是多对多的关系
    - 主要组/初始组：用户的默认组，Ubuntu自动创建与用户同名的组
    - 分类
        - 超级组(superuser group): 组名root，GID = 0
        - 系统组(system group): GID 0~499
        - 自定义组: Ubuntu默认从GID 1000开始
    - GID是组账户的唯一标识
- 用户与组配置文件
    - 用户账户配置文件 /etc/passwd
        - 账户名:密码:UID:GID:注释:主目录:Shell
        - 密码用x表示，passwd文件不保存密码信息
    - 用户密码配置文件 /etc/shadow
        - MD5加密算法
        - shadow组成员可读，其他用户禁止访问
        - 帐户名:密码:最近一次修改:最短有效期:最长有效期:过期前警告期:过期日期:禁用:保留用于未来扩展
    - 组账户配置文件 /etc/group
        - 组名:组密码:GID:组成员列表
        - 只列出次要组成员，不列出主要组成员
    - 组账户密码配置文件 /etc/gshadow
        - 组名:加密后的密码:组管理员:组成员列表

### 管理用户和组的命令行工具
- 管理用户的命令行工具
    - 查看用户账户 cat /etc/passwd
    - 添加用户账户 
        - useradd [选项] <用户名>, Linux通用命令
        - adduser, Ubuntu命令，提供交互会话
    - 管理用户账户密码
        - passwd [选项] [用户名], 设置用户密码
        - 设置账户密码 passwd
        - 账户密码锁定和解锁 passwd -l, passwd -u
        - 查询账户密码 passwd -S
        - 删除账目密码 passwd -d
    - 修改用户账户
        - usermod命令：修改账户属性，包括用户名、主目录、用户组、登录Shell等
        - chfn命令：更改用户个人信息
    - 删除用户账户
        - userdel [-r] 用户名
            - -r选项：删除该账户的主目录和邮件目录
            - 不允许删除正在使用/已经登录的用户账户
        - deluser: Ubuntu中使用较多
- 管理组的命令行工具
    - 查看组账户 cat /etc/group
    - 创建组账户
        - groupadd [选项] 组名
        - addgroup Ubuntu提供，交互会话，选项长格式
    - 修改组账户
        - groudmod [-g GIC] [-n 新组名] 组名: 修改组名和GID
    - 删除组账户
        - groupdel 组名
        - 不可删除某用户账户的主组，除非先删除成员账户，再删除组
        - delgroup Ubuntu使用较多
    - 管理组成员
        - groups 显示某用户所属的全部组
        - gpasswd 添加或删除组内用户
        - adduser/deluser 同上
- 其他用户管理命令
    - 查看用户信息: id [选项] [用户名]
    - 查看登录用户: who
    - 查看历史登录信息: last
    - 查看用户执行的进程: w


# 文件与目录管理

## Linux文件与目录概述
- 文件是Linux操作系统处理信息的基本单位，所有软件都以文件形式组织
- 目录是包含许多文件的一类特殊文件，每个文件都登记在一个或多个目录中
- Linux目录结构
    - 没有盘符，所有文件和目录都挂载在一棵目录树上
    - 目录树：分级、分层组织管理文件的树形目录结构
- Linux文件系统层次标准(FHS, Filesystem Hierarchy Standard)
    - 第1层规范：根目录的子目录应该放什么文件
    - 第2层规范：/usr和/var的子目录应该放什么文件
- Linux文件类型
    - 文件的结构分为2部分
        - 索引节点：I节点，有关相应文件信息的记录，包含权限、所有者、文件大小等
        - 数据：文件的实际内容，可以为空，可以很大，可以有自己的结构
    - 文件的4种类型
        - 普通文件：非结构化，有序的字符序列
        - 目录文件：特殊文件，结构化，索引节点号-文件名对的列表，每个目录文件中至少有"."和".."两个条目
        - 设备文件：特殊文件，只有索引节点，没有数据，标识设备驱动器，用于内核与设备通信，字符设备文件/块设备文件/伪设备文件
        - 链接文件
            - 特殊文件，存放的数据是指向文件的路径
            - 使用链接文件时，内核自动访问指向的文件路径
            - 符号链接(Symbolic Link)，硬链接(Hard Link)
- 软链接和硬链接
    - 软链接类似快捷方式，删除原文件后链接文件失效，删除链接文件不影响原文件
    - 硬链接是原文件的别名，使用同一个索引节点，是同一个文件，无法区分原文件和硬链接，删除原文件不影响硬链接文件
    - 硬链接可原文件必须在同一个文件系统上，不允许链接至目录，软链接不要求
- ls -l中的文件类型
    - "-" 普通文件
    - d 目录文件
    - c 字符设备文件
    - b 块设备文件
    - l 符号链接文件

## Ubuntu目录操作
- 创建目录 mkdir
- 删除目录 rmdir
- 工作目录 
    - ch 改变工作目录
    - pwd 以绝对路径显示工作目录
- 显示目录内容 ls


## Ubuntu文件操作
- 文件内容显示 
    - cat 查看全部文件内容
    - more 逐页显示，百分比，回车显示下一行
    - less 逐页显示，前后翻页
    - head 显示文件开头的若干行或若干字节，默认10行
    - tail 显示文件末尾的若干行或若干字节，默认10行
    - od 按照特殊格式查看文件内容，默认8进制
- 文件内容查找
    - grep 模式查找，显示包含字符串模式的所有行
- 文件内容比较
    - comm 逐行比较两个排好序的文件，显示共有的行
    - diff 逐行比较两个文件，列出不同，提示使两文件一致需要修改那些行，若完全一致则无输出
- 文件内容排序
    - sort 排序
- 文件内容统计
    - wc 统计指定文件的字节数、字数、行数
- 文件查找
    - find 在目录结构中搜索满足查询条件的文件并执行指定操作，权利受限
    - locate 查找文件，比find快，数据库，权利不受限
- 文件/目录复制、删除和移动
    - cp 复制
    - rm 删除
    - mv 移动或重命名
- 链接文件创建
    - ln -s 符号链接
    - ln 硬链接
- 文件压缩与解压缩
    - gzip: .gz
    - zip: .zip
    - tar: 打包

## 管理文件和目录权限
- 文件权限：对文件的访问控制，用户和组对文件和目录的访问权限
- 3种访问者身份
    - 所有者(owner)：默认为文件的创建者
    - 所属组(group)：默认为文件创建者的主要组
    - 其他用户(others)：除root、所有者及所属组外的所有用户
- 3种访问权限
    - 读(read)：读取文件内容或查看目录
    - 写(write)：修改文件内容或创建、删除文件
    - 执行(execute)：执行文件或允许使用cd命令进入目录
- 变更文件访问者身份
    - 变更所有者 chown
    - 变更所属组 chgrp
- 设置文件访问权限
    - chmod
    - 字符表示：u, g, o, a; r, w, x
    - 数字表示：r = 4, w = 2, x = 1
        - rwx = 7
        - rw- = 6
        - r-x = 5
        - r-- = 4
- 默认文件访问权限
    - 普通文件: rw-r--r--, 644
    - 目录文件: rwxr-xr-x, 755
    - 掩码(umask)：文件权限码的补码，022
        - 普通文件: 666 - 022 = 644
        - 目录文件: 777 - 022 = 755

# 磁盘管理系统

## Linux磁盘存储概述
- 磁盘包括硬盘、光盘、闪存等
- 低级格式化
- 高级格式化
- Linux磁盘分区
    - 分区样式：MBR, GPT, 不同类型的分区表
        - MBR
            - 最多支持4个磁盘分区
            - 可通过扩展分区来支持更多逻辑分区
            - 适合4个主分区，或3个主分区+1个扩展分区
            - 扩展分区只能用于划分逻辑分区，不可存储数据，不可格式化为文件系统
            - 又称MSDOS
            - 容量限制2TB
        - GPT
            - 最多支持128个主分区
            - 不需要创建扩展分区和逻辑分区
            - 适合大于2TB的硬盘分区
    - Linux为每个磁盘设备分配1~16的编号
        - 一个磁盘最多有16个分区
        - 1~4 主分区或扩展分区
        - 5~16 逻辑分区
    - 磁盘分区命名: /dev/sdxy, x = a, b, ..., y = 1, 2, ...
- Linux文件系统
    - 目录结构：操作系统管理文件的逻辑方式，用户可见
    - 文件系统：操作系统在磁盘上存储文件的物理方式，用户不可见
    - Ubuntu默认文件系统格式: ext4(fourth extented filesystem, 扩展文件系统)
        - 大型文件系统，最高支持1EB分区，16TB单个文件
        - 向下兼容ext3和ext2，可将ext3和ext2文件系统挂载为ext4分区
        - 支持Extent文件存储方式，取代ext3/ext2的块映射(block mapping)
        - 支持持久预分配
        - allocte-on-flash, 延迟分配磁盘空间
        - 支持无限数量子目录
        - 日志校验
        - 在线磁盘碎片整理
- 磁盘分区规划
    - 分区类型
        - Linux Native
        - Linuc Swap
    - Linux磁盘最基本的2个分区：根分区和Swap分区
    - 磁盘分区工具: fdisk, parted, cfdisk

## 管理磁盘分区和文件系统
- 建立和使用文件系统的3个步骤
    - 磁盘分区
    - 建立文件系统/格式化
    - 建立挂载点目录
- fdisk
    - 查看分区 fdisk -l
    - 创建分区
        - 进入交互操作界面: fdisk 设备文件
        - 显示硬盘分区表信息: p
        - 创建新分区: n
    - 修改分区类型：交互模式下t命令
    - 删除分区：交互模式下d命令
    - 保存分区修改结果：交互模式下w命令
- 建立文件系统/格式化硬盘分区
    - 查看文件系统类型 file -s
    - 创建文件系统 mkfs
    - 使用卷标(label)或UUID代替设备名表示文件系统
        - UUID: universally unique identifier, 全局唯一标识符, 128位
        - e2label, tune2fs, blkid
- 挂载文件系统
    - 作为挂载点的目录通常应是空目录，挂载文件系统后该目录下的内容暂时消失
    - Ubuntu专用挂载点
        - /mnt 
        - /media 外部存储设备
        - /cdrom 光盘
    - mount [-t 文件系统类型] [-L 卷标] [-o 挂载选项] 设备名 挂载点目录
    - 自动挂载文件系统：配置文件 /etc/fstab
    - 当前已挂在文件系统信息：配置文件/etc/mtab
    - 卸载文件系统: umount
- 检查维护文件系统
    - fsck 检验并修复文件系统
    - df 查看文件系统的磁盘占用情况
    - du 查看文件和目录的磁盘使用情况
    - tune2fs 转换文件系统

## 文件系统的备份
- 备份 = 系统备份 + 用户备份
- 备份策略
    - 完全备份 (full backup)
    - 增量备份 (incremental backup)
    - 差异备份 (differential backup)
- 备份规则
    - 单纯的完全备份
    - 完全备份结合差异备份
- 使用存档工具进行简单备份
    - tar
    - dd
- 使用dump和restore实现备份和恢复
- 光盘备份
    - mkisofs
    - dd

### Storage Category
- storage
    - magnetic storage
        - hard disk (HDD)
        - floppy disk (FDD)
    - solid-state storage
        - SSD
        - flash
        - SD card

# 软件包管理

## Linux软件安装基础
- 主流软件包格式
    - RPM: ReaHat Package Manager, rpm
    - Deb: Debian Packager, dpkg
- 高级软件包管理工具
    - yum: yellow dog updater, modified
    - apt: advanced packaging tools, 底层是dpkg
- Ubuntu软件安装方式
    - Ubuntu软件中心
    - apt
    - ppa: personal package archive
    - dpkg: offline install .deb
    - 二进制
    - 源代码

## APT工具
- 命令行工具apt, 图形界面工具synaptic
- apt-get 执行与软件包安装有关操作
    - update
    - upgrade
    - install 下载、安装软件包并解决依赖关系
    - remove
    - autoremove
    - purge 卸载软件包及其配置文件
    - source 下载软件包的源代码
    - clean 清理下载的软件包，不影响软件的使用
    - autoclean
- apt-cache 查询软件包相关信息
    - pkgnames
    - search
    - show
    - depends
    - rdepends
    - showpkg
    - policy
- PPA安装
    - launchpad.net
    - 管理PPA源
        - pps:user/ppa-name
        - sudo add-apt-repository ppa:user/ppa-name
        - sudo add-apt-repository -r ppa:user/ppa-name

## 安装软件包文件
- Deb软件包管理 dpkg
- RPM软件包管理 rpm
- 二进制包软件安装 .run .bin
- 源代码安装
    1. 解压软件包
        - tar -jxvf *.tar.bz2
        - tar -zxvf *.tar.gz
    2. 执行configure生成编译配置文件Makefile
        - configure文件是bash脚本文件，扫描系统，确认程序需要的库存在，设置文件路径等设置工作，创建makefile文件
    3. 执行make命令编译源代码
    4. 执行make install安装软件
- 动态库依赖
    - ldd 查询程序依赖的共享库
    - ldconfig 在默认搜索目录/lib, /usr/lib配置文件/etc/ld.so.conf所列目录下搜索所有的共享库，创建动态装入程序(ld.so)所需的连接和缓存文件
    - 安装新动态库后需要运行ldconfig
    - 共享库搜索路径配置 /etc/ld.so.conf
    - 共享库路径缓存 /etc/ld.so.cache

# 系统高级管理

## 进程管理
- PID, PCB
- init进程
    - 系统的第一个进程是初始化进程(init)
    - 所有其他进程都是init的子进程，fork()
    - init在系统运行期间始终存在
- 进程的分类
    - 交互进程：Shell下执行程序，前台或后台运行
    - 批处理进程：进程序列
    - 守护进程：监控进程，后台运行，无控制终端，服务，通常随系统启动而运行
- PCB (process control block)
    - PID (process id)
    - PPID (parent process id)
    - USER (UID, GID)
    - STAT
    - PRIPORITY
    - Resource: CPU, memory
- ps command (process status)
    - 最常使用ps aux
        - a: all with tty, including other users
        - u: user-oriented format
        - x: processes without controlling ttys
    - ps aux | less
    - 静态输出进程状态
- top command
    - 动态刷新进程状态
- 进程管理
    - 启动进程
        - 手动启动 shell
            - 前台启动
            - 后台启动 &
        - 调度启动：定时系统自动运行
    - 进程的挂起和恢复
        - 作业(jod)：正在执行的一个或多个相关进程
        - \<Ctrl> + \<Z> 挂起前台作业，转至后台，停止运行
        - fg 挂起的作业回到前台执行
        - bg 挂起的作业回到后台执行
    - 结束进程的执行
        - \<Ctrl> + \<C> 中断前台进程
        - kill command
        - killall command
    - 进程管理的优先级
        - nice value: -20~19, -20最高优先级, 19最低优先级
        - 系统进程默认优先级为0
        - nice command 设置进程的优先级
        - renice command 调整进程的优先级

## 系统启动过程
- Ubuntu启动过程的4个阶段
    1. BIOS启动
        - BIOS加电自检(POST)
        - 按照CMOS设置搜索处于活动状态且可以引导的设备
    2. 启动引导加载程序
        - 选择引导设备
        - 读取引导设备的MBR(main boot record)引导扇区
        - 加载MBR至内存后，BIOS将控制权移交MBR
        - MBR启动引导加载程序(boot loader)
        - 引导加载程序引导操作系统
    3. 装载内核
        - GRUB载入Linux Kernel并运行
        - 初始化设备驱动程序
        - 以只读方式挂载根文件系统(root filesystem)
    4. 执行init程序实现系统初始化
        - 内核完成核内引导后加载init程序（进程号1）
        - init相关配置完成系统初始化
        - 进入某个特定运行级别运行相应的程序和服务
        - 提供用户登录界面
- Ubuntu的默认引导加载程序为GRUB(grand unified bootloader)
- BIOS -> GRUB -> Kernel -> init process
- GRUB配置
    - 配置文件 /boot/grub/grub.conf
    - grub-mkconfig command
        - 配置文件 /etc/grub.d, /etc/default/grub
- Ubuntu Runlevel
    - 0~6
    - 默认启动级别2
    - Ubuntu 2~5级无区别，均为用户模式
    - 0: 关机
    - 1: 单用户模式，用于root身份开启虚拟控制台，管理系统
    - 2~5：带GUI的完整多用户模式
    - 6：重启
- Linux系统的两种init方式
    - System V initialization (SysVinit)
        - 使用运行级别和链接文件启动和关闭系统服务
        - 依据服务间的依赖关系，线性、顺序启动
        - /etc/init.d
    - Upstart
        - 使用事件驱动启动和关闭系统服务
        - 并发启动服务，利用多核性能
        - /etc/init
        - Job
            - 任务作业(task)：有确定的生命周期和终止状态 
            - 服务作业(service)：长期运行
            - 抽象作业(abstract job)：无PID，只存在Upstart内部
- Ubuntu系统初始化过程

# C/C++编程

## Makefile
- makefile 三要素
    - 目标 (target)
    - 条件 (prerequisite)
    - 命令 (command)
## Autotools
- Autotools项目
    - configure 配置文件
    - Makefile.in 模板文件
- Autotools commands
    - aclocal
    - autscan
    - autoconf
    - autoheader
    - automake
- 使用步骤
    1. 执行autoscan，生成configure.scan
    2. 重命名并修改configure.scan至configure.ac
        - AC_INIT
        - AM_INIT_AUTOMAKE
        - AC_CONFIG_FILES([Makefile])
    3. 执行aclocal，生成aclocal.m4
    4. 执行autoconf, 生成configure
    5. 执行autoheader, 生成config.h.in
    6. 创建Makefile.am，定义生成Makefile的规则
        - AUTOMAKE_OPTIONS = foreign, gnu or gnits
        - bin_PROGRAMS
        - file_SOURCES
    7. 执行automake --add-missing生成Makefile.in
    8. 执行./configure, 生成Makefile
    9. make
        - make/make all/make clean
        - make install/make uninstall
        - make dist

## 图形界面编程
- GNOME, LXDE采用GTK+开发
- KDE采用Qt开发
- GIMP: GNU Image Manipulatioin Program
- GTK: GIMP Toolkit
- GTK+