numpy
    histogram2d 绘制二维直方图
    rot90   
    zeros
    mean
    std
    narray.flatten

V6:
start
    checkAndLoadingDNB
        read_raw_image
        SNRProcess_cyc0
    read_dark_signal_images
        read_raw_image
        SNRProcess


self.filepath   用于base的图片路径
self.load_path  全暗和全亮的图片路径


dark：sensor背景
signal - dark ==>所有点的均值（分区域计算）：生化背景
signal - sensor背景 - 生化背景 ==> 真实ints

cyc0_temp1  真实ints小于maxval
cyc0_temp2  真实ints大于threshold*std
tmp1
计算一个chan的singal和backgruond时，不考虑不满足cyc0_temp1的点


每个cycle都有dark，全暗真的什么都没有？


### *args, **kwargs
- normal argument: 实参与形参按照参数的位置匹配
- keyword argument: 关键字为字符串，实参与形参按照关键字匹配，不要求位置一致
- args: positional arguments，普通参数元组
- kwargs: keyword arguments，关键字参数字典
- *args
    - 形参：将多个普通实参打包成一个tuple
    - 实参：将args解开，将args的元素作为多个实参，字典解开后只有键，丢失值
- **kwargs
    - 形参：将多个关键字实参打包成一个dictionary
    - 实参：将字典解开，元素的键作为实参的关键字，元素的值作为实参的值