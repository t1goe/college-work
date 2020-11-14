import pathlib

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn import tree
from sklearn.linear_model import LinearRegression

from sklearn.tree import DecisionTreeClassifier # Import Decision Tree Classifier

# create the output directory if it does not exist
pathlib.Path('./output').mkdir(exist_ok=True)

print('*' * 20)
print('Question 1')
print('*' * 20)

df_1 = pd.read_csv('./specs/marks_question1.csv')

# Q1.1
midterm = df_1['midterm']
final = df_1['final']

fig = plt.figure()
plt.scatter(midterm, final)
# plt.show()
plt.savefig('./output/marks.png')

# Q1.2
midterm = df_1['midterm'].values.reshape(-1, 1)
final = df_1['final'].values

reg = LinearRegression().fit(midterm, final)
print(reg.predict(midterm))

# Q1.3
testValue = np.array([86]).reshape(-1, 1)
print("\n86 Should result in:")
print(reg.predict(testValue))

print('*' * 20)
print('Question 2')
print('*' * 20)

df_2 = pd.read_csv('./specs/borrower_question2.csv')

# Q2.1
df_2 = df_2.drop('TID', axis=1)

# Q2.2
# Convert data to numeric values
df_2['HomeOwner'] = df_2['HomeOwner'].map({'No': 0, 'Yes': 1})
df_2['MaritalStatus'] = df_2['MaritalStatus'].map({'Single': 0, 'Married': 1, 'Divorced': 2})
df_2['DefaultedBorrower'] = df_2['DefaultedBorrower'].map({'No': 0, 'Yes': 1})

# Get data
trainingData = df_2.drop('DefaultedBorrower', axis=1)
targetData = df_2['DefaultedBorrower']

# Create Decision Tree classifer object
clf1 = DecisionTreeClassifier(criterion="entropy", min_impurity_decrease=0.5)

# Train Decision Tree Classifer
clf1 = clf1.fit(trainingData, targetData)

plt.figure()
tree.plot_tree(clf1, filled=True)
plt.savefig('./output/high_tree.png', format='png', bbox_inches="tight")

# Q2.3
clf2 = DecisionTreeClassifier(min_impurity_decrease=0.1, criterion='entropy')

clf2 = clf2.fit(trainingData, targetData)

plt.figure()
tree.plot_tree(clf2, filled=True)
plt.savefig('./output/low_tree.png', format='png', bbox_inches="tight")