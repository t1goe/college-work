import pathlib

import pandas as pd
from sklearn import preprocessing
from sklearn.cluster import KMeans
from sklearn.cluster import DBSCAN

import matplotlib.pyplot as plt

# create the output directory if it does not exist
pathlib.Path('./output').mkdir(exist_ok=True)

print('*' * 20)
print('Question 1')
print('*' * 20)

df_1 = pd.read_csv('./specs/question_1.csv')

# Q1.1
kmeans1 = KMeans(init="random", n_clusters=3, random_state=0)
kmeans1.fit(df_1)

# Q1.2
df_1['cluster'] = kmeans1.labels_
df_1.to_csv('./output/question_1.csv', index=False)

# Q1.3
plt.scatter(df_1['x'], df_1['y'], c=kmeans1.labels_, cmap='rainbow')
plt.savefig('./output/question_1.pdf', format='pdf', bbox_inches="tight")

print('*' * 20)
print('Question 2')
print('*' * 20)

df_2 = pd.read_csv('./specs/question_2.csv')

# Q2.1
df_2 = df_2.drop(['NAME', 'MANUF', 'TYPE', 'RATING'], axis=1)

# Q2.2
kmeans2 = KMeans(init="random", n_clusters=5, n_init=5, max_iter=100, random_state=0)
kmeans2.fit(df_2)

df_2['config1'] = kmeans2.labels_

# Q2.3
kmeans2 = KMeans(init="random", n_clusters=5, n_init=100, max_iter=100, random_state=0)
kmeans2.fit(df_2)

df_2['config2'] = kmeans2.labels_

# Q2.4
# Report

# Q2.5
kmeans2 = KMeans(init="random", n_clusters=3, n_init=100, max_iter=100, random_state=0)
kmeans2.fit(df_2)

df_2['config3'] = kmeans2.labels_

# Q2.6
# Report

# Q2.7
df_2.to_csv('./output/question_2.csv', index=False)

print('*' * 20)
print('Question 3')
print('*' * 20)

df_3 = pd.read_csv('./specs/question_3.csv')

# Q3.1
df_3 = df_3.drop('ID', axis=1)
kmeans3 = KMeans(init="random", n_clusters=7, n_init=5, max_iter=100, random_state=0)
kmeans3.fit(df_3)
df_3['kmeans'] = kmeans3.labels_

# Q3.2
plt.scatter(df_3['x'], df_3['y'], c=kmeans3.labels_, cmap='rainbow')
plt.savefig('./output/question_3_1.pdf', format='pdf', bbox_inches="tight")

# Q3.3
x = df_3.drop('kmeans', axis=1)
min_max_scaler = preprocessing.MinMaxScaler()
x_scaled = min_max_scaler.fit_transform(x)
df_3_data = pd.DataFrame(x_scaled)
df_3['x'] = df_3_data[0]
df_3['y'] = df_3_data[1]

print(df_3_data)

dbscan1 = DBSCAN(eps=0.04, min_samples=4)
dbscan1.fit(df_3_data)

plt.ylim(0, 1)
plt.xlim(0, 1)

plt.scatter(df_3_data[0], df_3_data[1], c=dbscan1.labels_, cmap='rainbow')
plt.savefig('./output/question_3_2.pdf', format='pdf', bbox_inches="tight")

df_3['dbscan1'] = dbscan1.labels_

# Q3.4
dbscan2 = DBSCAN(eps=0.08, min_samples=4)
dbscan2.fit(df_3_data)

plt.scatter(df_3_data[0], df_3_data[1], c=dbscan2.labels_, cmap='rainbow')
plt.savefig('./output/question_3_3.pdf', format='pdf', bbox_inches="tight")

df_3['dbscan2'] = dbscan2.labels_

# Q3.5
df_3.to_csv('./output/question_3.csv', index=False)

# print(df_3)

# Q3.6
# Report
