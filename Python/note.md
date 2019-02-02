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
