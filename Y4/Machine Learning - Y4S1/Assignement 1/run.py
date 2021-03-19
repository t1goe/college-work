import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
from sklearn import metrics
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier
from mlxtend.feature_selection import SequentialFeatureSelector as SFS
from mlxtend.plotting import plot_sequential_feature_selection as plot_sfs
from sklearn.metrics import classification_report, roc_curve, auc
from sklearn.metrics import accuracy_score
from sklearn.metrics import balanced_accuracy_score

tesco_df = pd.read_csv('tesco_17372013.csv')

# Map the ordinal data to numerical
tesco_df["'Diabetes category'"] = tesco_df["'Diabetes category'"].map(
    {'low': 0, 'mid': 1, 'high': 2}).copy()

# filter techniques
tesco_df = tesco_df.filter(regex="_std|_norm|f_|Diabetes").copy()

tesco_df_train = tesco_df.iloc[0:500, ]
tesco_df_test = tesco_df.iloc[400:500, ]

# creating set to hold the correlated features
corr_features = set()

# create the correlation matrix (default to pearson)
corr_matrix = tesco_df_train.corr()

# display a heatmap of the correlation matrix
# plt.figure(figsize=(11, 11))
# sns.heatmap(corr_matrix)  # , annot=True
# plt.show()

# ID highly corrilated attributes and add to set
for i in range(len(corr_matrix.columns)):
    for j in range(i):
        if abs(corr_matrix.iloc[i, j]) > 0.8:
            print(corr_matrix.columns[i] + "\t\t|\t\t" + corr_matrix.columns[j])
            colname = corr_matrix.columns[i]
            corr_features.add(colname)

tesco_df_train.drop(labels=corr_features, axis=1, inplace=True)
tesco_df_test.drop(labels=corr_features, axis=1, inplace=True)

# print(corr_features)
# pd.set_option("display.max_rows", 3, "display.max_columns", None)
# print(filtered_tesco_df)

knn = KNeighborsClassifier(n_neighbors=5)
dtc = DecisionTreeClassifier(random_state=0, criterion="entropy")
gnb = GaussianNB()

X = tesco_df_train.drop(labels="'Diabetes category'", axis=1)
Y = tesco_df_train.filter(regex='Diabetes category').values.ravel()

sfs_forward_knn = SFS(knn, k_features=20, forward=True, floating=False, verbose=0, scoring='accuracy', cv=50, n_jobs=-1)
sfs_forward_clf = SFS(dtc, k_features=20, forward=True, floating=False, verbose=0, scoring='accuracy', cv=50, n_jobs=-1)
sfs_forward_gnb = SFS(gnb, k_features=20, forward=True, floating=False, verbose=0, scoring='accuracy', cv=50, n_jobs=-1)

sfs_forward_knn = sfs_forward_knn.fit(X, Y)
sfs_forward_clf = sfs_forward_clf.fit(X, Y)
sfs_forward_gnb = sfs_forward_gnb.fit(X, Y)

knn_filtered = pd.DataFrame()
dtc_filtered = pd.DataFrame()
gnb_filtered = pd.DataFrame()

for feature in sfs_forward_knn.k_feature_names_:
    knn_filtered[feature] = tesco_df_train[feature]

# knn_filtered[feature] = tesco_df_train["'Diabetes category'"]

for feature in sfs_forward_clf.k_feature_names_:
    dtc_filtered[feature] = tesco_df_train[feature]

# clf_filtered[feature] = tesco_df_train["'Diabetes category'"]

for feature in sfs_forward_gnb.k_feature_names_:
    gnb_filtered[feature] = tesco_df_train[feature]

# gnb_filtered[feature] = tesco_df_train["'Diabetes category'"]

# X_train, X_test, y_train, y_test = train_test_split(original_df, original_df_y, test_size=0.20)
X_train, X_test, y_train, y_test = train_test_split(knn_filtered, tesco_df_train.filter(regex='Diabetes category'), test_size=0.20)

# try running from k=1 to k=5 and k=20
k_range = [1, 5, 20, 50]
scores = {}
scores_list = []
print("\n\n\nKNN EVAL SELF:")
for k in k_range:
    knn = KNeighborsClassifier(n_neighbors=k)
    knn.fit(X_train, y_train.values.ravel())
    y_pred = knn.predict(X_test)
    scores[k] = metrics.accuracy_score(y_test.values.ravel(), y_pred)
    scores_list.append(metrics.accuracy_score(y_test, y_pred))
    print(k)
    print(metrics.classification_report(y_true=y_test, y_pred=y_pred))

print(scores_list)

print("\n\n\nDTC EVAL SELF:")
X_train, X_test, y_train, y_test = train_test_split(dtc_filtered, tesco_df_train.filter(regex='Diabetes category'), test_size=0.20)
clf = DecisionTreeClassifier(random_state=0, criterion="entropy")
clf.fit(X_train, y_train.values.ravel())
y_pred = clf.predict(X_test)
print(metrics.classification_report(y_true=y_test, y_pred=y_pred))

print("\n\n\nGNB EVAL SELF:")
X_train, X_test, y_train, y_test = train_test_split(gnb_filtered, tesco_df_train.filter(regex='Diabetes category'), test_size=0.20)
gnb = GaussianNB()
gnb.fit(X_train, y_train.values.ravel())
y_pred = gnb.predict(X_test)
print(metrics.classification_report(y_true=y_test, y_pred=y_pred))

X_train, X_test, y_train, y_test = train_test_split(tesco_df_train, tesco_df_train.filter(regex='Diabetes category'), test_size=0.20)

# try running from k=1 to k=5 and k=20
k_range = [1, 5, 20, 50]
scores = {}
scores_list = []

print("\n\n\nKNN EVAL Filtered:")
for k in k_range:
    knn = KNeighborsClassifier(n_neighbors=k)
    knn.fit(X_train, y_train.values.ravel())
    y_pred = knn.predict(X_test)
    scores[k] = metrics.accuracy_score(y_test.values.ravel(), y_pred)
    scores_list.append(metrics.accuracy_score(y_test, y_pred))
    print(k)
    print(metrics.classification_report(y_true=y_test, y_pred=y_pred))

print(scores_list)


X_train, X_test, y_train, y_test = \
    train_test_split(knn_filtered, tesco_df_train["'Diabetes category'"].map({0: 0, 1: 0, 2: 1}), test_size=0.20)
kNN = KNeighborsClassifier(n_neighbors = 5)
y_score = kNN.fit(X_train, y_train).predict_proba(X_test)
fprN, tprN, t = roc_curve(y_test, y_score[:,1])
roc_aucN = auc(fprN, tprN)

X_train, X_test, y_train, y_test = \
    train_test_split(dtc_filtered, tesco_df_train["'Diabetes category'"].map({0: 0, 1: 0, 2: 1}), test_size=0.20)
dtree = DecisionTreeClassifier(random_state=0, criterion="entropy")
y_score = dtree.fit(X_train, y_train).predict_proba(X_test)
fprD, tprD, t = roc_curve(y_test, y_score[:,1])
roc_aucD = auc(fprD, tprD)

X_train, X_test, y_train, y_test = \
    train_test_split(gnb_filtered, tesco_df_train["'Diabetes category'"].map({0: 0, 1: 0, 2: 1}), test_size=0.20)
gnb = GaussianNB()
y_score = gnb.fit(X_train, y_train).predict_proba(X_test)
fprG, tprG, t = roc_curve(y_test, y_score[:,1])
roc_aucG = auc(fprG, tprG)

plt.figure()
lw = 2
plt.plot(fprG, tprG, color='red',
         lw=lw, label='ROC NB (area = %0.2f)' % roc_aucG)
plt.plot(fprD, tprD, color='green',
         lw=lw, label='ROC DTree (area = %0.2f)' % roc_aucD)
plt.plot(fprN, tprN, color='blue',
         lw=lw, label='ROC kNN (area = %0.2f)' % roc_aucN)


plt.plot([0, 1], [0, 1], color='black', lw=lw, linestyle='--')
plt.xlim([0.0, 1.0])
plt.ylim([0.0, 1.0])
plt.xlabel('False Positive Rate')
plt.ylabel('True Positive Rate')
plt.title('ROC Analysis for Diabetes data')
plt.legend(loc="lower right")
plt.show()


# print("Best Features: \n")
# for i in range(20):
#     print(sorted(sfs_forward_knn.k_feature_names_)[i] + " | " + sorted(sfs_forward_clf.k_feature_names_)[i] +
#           " | " + sorted(sfs_forward_gnb.k_feature_names_)[i])
#
# fig2 = plot_sfs(sfs_forward_knn.get_metric_dict(),
#                 ylabel='Accuracy',
#                 kind='std_dev')
#
# plt.ylim([0.5, 1])
# plt.title('Sequential Forward Selection KNN (n=5) (w. StdDev)')
# plt.grid()
# # plt.show()
#
# fig2 = plot_sfs(sfs_forward_clf.get_metric_dict(),
#                 ylabel='Accuracy',
#                 kind='std_dev')
#
# plt.ylim([0.5, 1])
# plt.title('Sequential Forward Selection Decision Tree Classifier (w. StdDev)')
# plt.grid()
# # plt.show()
#
# fig2 = plot_sfs(sfs_forward_gnb.get_metric_dict(),
#                 ylabel='Accuracy',
#                 kind='std_dev')
#
# plt.ylim([0.5, 1])
# plt.title('Sequential Forward Selection Naive Bayes (w. StdDev)')
# plt.grid()
# # plt.show()
