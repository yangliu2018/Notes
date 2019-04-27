# Tutorial
[scikit-learn documentation](https://scikit-learn.org/stable/documentation.html)
# A tutorial on statistical-learning for scientific data processing
- statistical learngin: the use of machine learning techniques with the goal of statistical inference: drawing conclusions on the data at hand

## Statistical learning: the setting and the estimator object in scikit-learn
### datasets
```python
from sklearn import datasets
xx = datasets.load_xx()
samples = xx.data
features = xx.target
```

### estimators objects
- an estimator is any object that learns from data
    - classification
    - regression
    - clustering algorithm
    - transformer that extracts/filters useful features from raw data
```python
estimator = Estimator(param1 = 1, param2 = 2)
estimator.fit(data)         # fitting data
estimator.param1            # estimator parameters
estimator.estimated_param_  # estimated parameters
```

## Supervised learning: predicting an output variable from high-dimensional observations
### supervised learning
- learn the link between two datasets: the observed data X (2D) and the label y (1D)
- supervised_estimator.fit(X, y)
- supervised_estimator.predict(X)
- prediction tast
    - classification tast: classify observations in a set of finite labels
    - regression tast: predict a continuous target variable

### nearest neighbor and the curse of dimensionality
- nearest neighbor: given a new observation X_test, find in the training set the observation with the closest feature vector
- training data and testing data
    - not test the prediction of an estimator on the data used to fit the estimator 
    - as this would not be evaluation the performance of the estimator on new data
- curse of dimensionality

### linear model: from regression to sparsity
#### linear regression
- sklearn.linear_model.LinearRegression: the minimum of the sum of squared residuals
#### shrinkage
- noise induces high variance when there are few data points
- ridge regression
    - a solution in high-dimensional statistical learning
    - shrink the regression coefficients to zero: any two randomly chosen set of observations are likely to be uncorrelated
    - bias/variance tradeoff: the lager the ridge alpha parameter, the higher the bias and the lower the variance
- overfitting: capturing in the fitted parameters noise that prevents the model to generalize to new data
- regularization: the bias introducted by the ridge regression

#### sparsity
- select only the informative features ans set non-informative ones to 0
- Ridge regression: decrease some features' contribution but not set to zero
- Lasso (least absolute shrinkage and selection operator): set some coefficients to zero
- sparse method
- Occam's razor: prefer simpler models
- different algorithms can be used to solve the same mathematical problem
    - Lasso: coordinate descent method
    - LassoLar: LARA algorithm

#### classification
- fit a sigmoid or logistic function
- sklearn.linear_model.LogisticRegression
- multiclass classification
    - fit one-versus-all classifiers
    - use a voting heuristic for the final decision

### support vector machines (SVMs)
#### linear SVMs
- discriminant model: try to find a combination of samples to build a plane maximizing the margin between the two classes
- regularization: set by the C parameter
    - less regularization: large C, the margin is calculated on observations close to the separating line
    - more regularization: small C, the margin is calculated using many or all observations around the seperating line
- SVM (supporting vector machine)
    - SVR (supporting vector regression)
    - SVC (supporting vector classification)
- normalization: for many estimators, having datasets with uint standard deviation for each feature is important to get good prediction

#### using kernels
- classes are not always linearly seperate in feature space
- kernel trick: create a decision energy by positioning kernels on observations
- SVM kernels
    - linear kernel
    - polynomial kernel
    - RBF kernel (radial basis function)


## Model selection: choosing estimators and theri parameters
### score, and cross-validated scores
- estimator.score(): judge the quality of the fit (or the prediction) on new data
- KFold cross-validation: successively split data in folds using for training and testing
### cross-validation generators
- split method: generate lists of train/test indices for popular cross-validation strategies
```python
sklearn.model_selection.KFold(n_splits=n).split(data)
sklearn.model_selection.cross_val_score(estimator, data, target, cv=k_folder, n_jobs=-1)
```
- cross-validation generators
    - KFold
    - StratifiedKFold
    - GroupKFold
    - ShuffleSplit
    - StratifiedShuffleSplit
    - GruopShuffleSplit
    - LeaveOneGroupOut
    - LeavePGroupOut
    - LeaveOneOut
    - LeavePOut
    - PredefinedSplit

### grid-search and cross-validated estimators
#### grid-search
- sklearn.model_selection.GridSearchCV
- compute the score during the fit of an estimator on a parameter grid and choose the parameters to maximize the cross-validation score
- nested cross-validation
    - two cross-validation loops
    - cross_val_score(GridSearchCV, X, y)
#### cross-validated estimators
- called similarly to theri counterparts, with 'CV' appended to their name

## Unsupervised learning: seeking representations of the data
### clustering: grouping observations together
- clustering tast: split the observations without labels into well-seperated group called clusters
#### k-means clustering
- the simplest clustering algorithm
- hard to choose the right number of clusters
- sensitive to initialization; fall into local minima
- don't over-interpret clustering results
#### vector quantization
- clustering is a way of choosing a small number of exemplars to compress information
- image posterization
#### hierarchical agglomerative clustering: ward
- hierarchical clustering: a type of cluster analysis that aims to build a hierarchy of clusters
    - agglomerative: bottom-up approaches
    - divisive: top-down approaches
#### connectivety-constrained clustering
- connectivety graph
    - connected regions/components
#### feature agglomeration
- approaches to mitigate the curse of dimensionality
    - sparsity: observations less than feathers
    - feather allogmeration: mearge together similar features

### decompositions: from a signal to components and loadings

#### PCA (principal component analysis)
- select the successive components that explain the maximum variance in the signal
- reduce dimensionality of the data by projecting on a principal subspace

#### ICA (independent component analysis)
- select components so that the distribution of their loadings carries a maximum amount of independent information
