start
    checkAndLoadingDNB                  定位DNB位置
    readAllCycleImages                  读取信号图片
    getSNRFromSignalDict                计算SNR
    signalAndNoiseCyclesPlot            信号与噪声作图
    pickMaxSignal                       找出最大信号
    intensityExtrator                   光强提取
    normalization                       归一化
    scatterDensity                      作散点图


    checkAndLoadingDNB          定位DNB位置
        traverseImages              读取所有全暗和全亮图片，返回两个列表，分别保存全暗和全亮的数据
            readRawImage                读取原始图片，4096*3072，无符号16位整型，返回64位float型矩阵
        darkImageHist               打印信息
        signalAndBkCalculator       计算信号和背景，从而筛选5张信号最好的图片
        dnbPosExtractor             输入一张全暗和一张全亮的数据，得到DNB位置
            meanAndStdCalculator        以window为单位计算均值和方差
    readAllCycleImages          读取后面的所有图片，返回多重字典signalDict，这里区分SZ和CG的存储差异
        readRawImage
        seperateSignalAndDark   区分信号和全暗图片，分类存入signalDict    
    getSNRFromSignalDict        计算SNR
        meanAndStdCalculator        信号 = sig - dark, 以window为单位计算背景的均值（即噪声）和方差（舍弃），考虑dnb位置，不计算dnb点，得到多个cycle的信号和背景列表
    signalAndNoiseCyclesPlot    信号与噪声作图
    pickMaxSignal               找出最大信号
    intensityExtrator           光强提取
    normalization               归一化
    scatterDensity              作散点图



temp 的数据为int32位
原始图像位uint16位

https://bgitech.sharepoint.com/:p:/s/SWAnalysis/EbyPnUjhxXtJqEPb_uiVw4QBUyxNZEAeyldGnBU4rNRpRw?e=gMcL5v
https://bgitech.sharepoint.com/:p:/r/sites/SWAnalysis/_layouts/15/doc.aspx?sourcedoc=%7B489d8fbc-c5e1-497b-a843-dbfee895c384%7D&action=edit&uid=%7B489D8FBC-C5E1-497B-A843-DBFEE895C384%7D&ListItemId=15&ListId=%7B62C1A9DF-6EBE-4DA4-A0F4-981F8F50CA3B%7D&odsp=1&env=prod
