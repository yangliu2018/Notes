## 图像锐化
- 目的：使图像的边缘、轮廓线、细节变清晰
- 进行锐化的图像必须有较高性噪比，否则锐化后图像信噪比降低，噪声增加比信号多，故一般先去除或减轻噪声后再锐化
- 图像锐化的方法：高通滤波，空域微分


### 空域微分
- 一阶微分运算：一般指梯度模运算
    - 图像的梯度模值包含边界及细节信息
    - 梯度模算子用于计算梯度模值，边界提取算子，具有极值性、位移不变形、旋转不变性