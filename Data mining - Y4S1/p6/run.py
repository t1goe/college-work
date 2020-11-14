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
# Create kmeans with specified parameters and apply the data
kmeans1 = KMeans(n_clusters=3, random_state=0)
kmeans1.fit(df_1)

# Q1.2
# Get the labels as an array and add it to the dataset then output to csv
df_1['cluster'] = kmeans1.labels_
df_1.to_csv('./output/question_1.csv', index=False)

# Q1.3
# Create a scatterplot of the data and apply the labels as color
# Save to pdf
plt.scatter(df_1['x'], df_1['y'], c=kmeans1.labels_, cmap='rainbow')
plt.savefig('./output/question_1.pdf', format='pdf', bbox_inches="tight")

print('*' * 20)
print('Question 2')
print('*' * 20)

df_2 = pd.read_csv('./specs/question_2.csv')

# Q2.1
# Drop the specificed columns
df_2 = df_2.drop(['NAME', 'MANUF', 'TYPE', 'RATING'], axis=1)

# Q2.2
# Create another Kmeans with params, apply to data and output to new column in dataset
kmeans1 = KMeans(n_clusters=5, n_init=5, max_iter=100, random_state=0)
kmeans1.fit(df_2)

df_2['config1'] = kmeans1.labels_

# Q2.3
# kmeans again, apply data, output
kmeans2 = KMeans(n_clusters=5, n_init=100, max_iter=100, random_state=0)
kmeans2.fit(df_2)

df_2['config2'] = kmeans2.labels_

# Q2.4
# Report
# Explains the difference between kmeans1 and 2

# Q2.5
# kmeans again, apply data, output
kmeans3 = KMeans(n_clusters=3, n_init=100, max_iter=100, random_state=0)
kmeans3.fit(df_2)

df_2['config3'] = kmeans3.labels_

# Q2.6
# Report
# Creates several different Kmeans with variable number of clusters
# Graphed against inertia (squared sum of distances to centroid) to find the elbow of the graph
# (A by eye estimation of the best balance between n_clusters and accuracy)
inertiaVals = []
clusterRange = range(1, 40)
for x in clusterRange:
    kmeansX = KMeans(n_clusters=x, n_init=100, max_iter=100, random_state=0)
    kmeansX.fit(df_2)
    print(x)

    inertiaVals.append(kmeansX.inertia_)

plt.xlabel('n_clusters')
plt.ylabel('inertia')
plt.plot(clusterRange, inertiaVals, 'ro')
plt.show()


# Q2.7
# out to csv
df_2.to_csv('./output/question_2.csv', index=False)

print('*' * 20)
print('Question 3')
print('*' * 20)

df_3 = pd.read_csv('./specs/question_3.csv')

# Q3.1
# Drop ID column and do kmeans again
df_3 = df_3.drop('ID', axis=1)
kmeans3 = KMeans(n_clusters=7, n_init=5, max_iter=100, random_state=0)
kmeans3.fit(df_3)
df_3['kmeans'] = kmeans3.labels_

# Q3.2
# Plot data
plt.scatter(df_3['x'], df_3['y'], c=kmeans3.labels_, cmap='rainbow')
plt.savefig('./output/question_3_1.pdf', format='pdf', bbox_inches="tight")

# Q3.3
# Scale the data using minmax scalar (excluding the kmeans from the previous question)
# and reinsert back into dataframe
x = df_3.drop('kmeans', axis=1)
min_max_scaler = preprocessing.MinMaxScaler()
x_scaled = min_max_scaler.fit_transform(x)
df_3_data = pd.DataFrame(x_scaled)
df_3['x'] = df_3_data[0]
df_3['y'] = df_3_data[1]

# Apply DBSCAN to data
dbscan1 = DBSCAN(eps=0.04, min_samples=4)
dbscan1.fit(df_3_data)

# Graph data in the range 0,1
plt.xlim(-0.1, 1.1)
plt.ylim(-0.1, 1.1)

plt.scatter(df_3_data[0], df_3_data[1], c=dbscan1.labels_, cmap='rainbow')
plt.savefig('./output/question_3_2.pdf', format='pdf', bbox_inches="tight")

df_3['dbscan1'] = dbscan1.labels_

# Q3.4
# Apply dbscan with updated params
dbscan2 = DBSCAN(eps=0.08, min_samples=4)
dbscan2.fit(df_3_data)

# Plot data and output to pdf
plt.scatter(df_3_data[0], df_3_data[1], c=dbscan2.labels_, cmap='rainbow')
plt.savefig('./output/question_3_3.pdf', format='pdf', bbox_inches="tight")

df_3['dbscan2'] = dbscan2.labels_

# Q3.5
# csv output
df_3.to_csv('./output/question_3.csv', index=False)

# Q3.6
# Report
