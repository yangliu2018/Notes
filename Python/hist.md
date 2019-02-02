matplotlib.pyplot.hist
绘制直方图

matplotlib.pyplot.hist(
    x,                          # 输入的数据，array类型
    bins=None,                  # 整数或序列或'auto'
    range=None,                 # 数据范围，默认值为(x.min(), x.max())
    density=None, 
    weights=None, 
    cumulative=False, 
    bottom=None, 
    histtype='bar', 
    align='mid', 
    orientation='vertical', 
    rwidth=None, 
    log=False, 
    color=None, 
    label=None, 
    stacked=False, 
    normed=None, 
    hold=None, 
    data=None, 
    **kwargs
)



(419L, 544L)
float64


找坏点：
1 window还是整体
2 几倍std
