# Machine Learning Basic Concepts
Notes from: [Machine Learning Basic Concepts PDF](https://courses.edx.org/asset-v1:ColumbiaX+CSMM.101x+1T2017+type@asset+block@AI_edx_ml_5.1intro.pdf)

## Terminology
- Machine Learning
- Data Science
- Data Mining
- Data Analysis
- Statistical Learning
- Knowledge Discovery in Databases
- Pattern Discovery

## The Data Science process
1. data collection
2. data preparation: data cleaning, feature/variable engineering
3. EDA: descriptive statistics, clustering research questions
4. machine learning: classification, socring, predictive, models, clustering, density estimation, etc.
5. visualization: application deployment, data-driven decisions, dashboard

## ML vs Statistics
- Statistics
    - hypothesis testing
    - experimental designr
    - anova
    - linear regression
    - logistic regression
    - GLM(generialized linear model)
    - PCA(principal component analysis)
- Machine Learning
    - decision trees
    - rule induction
    - neural networks
    - SVMs(support vector machine)
    - clusting method
    - association rules
    - feature selection
    - visualization
    - graphical models
    - genetic algorithm

## Machine Learning definition
"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E."
<div style="text-align: right">Tom Mitchell. Machine Learning 1997.</div>

## Supervised vs. Unsupervised
- unsupervised learning: learning a model from unlabeled data
- supervised learning: learning a model from labeled data
- unsupervased learning
    - training data: x1, x2, ..., xn
    - clustering/segmentation
        - k-means
        - gaussian mixtures
        - hierarchical clustering
        - spectral clustering, etc.
- supervised learning
    - training data: (x1, y1), ..., (xn, yn), labels y
    - classification: y is discrete
        - binary classifier: y = +1 or -1
        - non linear classification
        - support vector machines
        - neural networks
        - decision trees
        - K-nearset neighbors
        - naive Bayes, etc.
    - regression: y is a real value
        - regressor

## Training and Testing
- Training set => ML Algorithm => Model(f)
- minimize E_train and hope E_test small too
    - in-sample/training/empirical error
    - out-sample/test/true error


## KNN(K-nearest neighbors)
- Not every ML method builds a model
- Main idea: Used the similarity between examples
- Assumption: Two similar examples should have same labels
- Assumption all examples(instances) are points in the d dimensional space R^d
- Standard Euclidian distance

## Overfitting/Underfitting
- complexity of the model vs. prediction error
- bias vs. variance
- overfitting: high complexity, low bias, high variance
- underfitting: low complexity, high bias, low variance
- avoid overfitting: use simple models
    - reduce the number of features manually or do feature selection
    - model selection
    - regularization(keep the features but reduce their importance by setting small parameter values)
    - cross-validation(estimate test error)

## Train, Validation and Test
- split data randomly
- training set: used for learning a model
- validation set: not used for learning but help tune model parameters and control overfitting
- test set: used to assess the performance of the final model and provide an estimation of the test error
- never use test set in any way to further tune the parameter or revise the model

## K-fold Cross Validation

## Confusion matrix

## Evaluation metrics


## Machine Learning Books
1. Tom Mitchell, Machine Learning.
2. Abu-Mostafa, Yaser S. and Magdon-Ismail, Malik and Lin, Hsuan-Tien, Learning From Data, AMLBook.
3. The elements of statistical learning. Data mining, inference, and prediction T. Hastie, R. Tibshirani, J. Friedman.
4. Christopher Bishop. Pattern Recognition and Machine Learning.
5. Richard O. Duda, Peter E. Hart, David G. Stork. Pattern Classification. Wiley.