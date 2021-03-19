import pathlib

import pandas as pd
from sklearn import preprocessing
from sklearn.decomposition import PCA

# create the output directory if it does not exist
#
# documentation here:
# https://docs.python.org/3/library/pathlib.html#pathlib.Path.mkdir
pathlib.Path('./output').mkdir(exist_ok=True)

print('*' * 20)
print('Question 1')
print('*' * 20)

df_1 = pd.read_csv('./specs/SensorData_question1.csv')
df_1 = df_1.round(3)
# Q1.1
df_1['Original Input3'] = df_1['Input3']
df_1['Original Input12'] = df_1['Input12']

# Q1.2
df_1['Input3'] = (df_1['Input3'] - df_1['Input3'].mean()) / df_1['Input3'].std(ddof=1)

# Q1.3
min_max_scalar = preprocessing.MinMaxScaler()
df_1['Input12'] = min_max_scalar.fit_transform(df_1['Input12'].values.reshape(-1, 1))

# Q1.4
stripped_df = df_1.drop('Original Input3', axis=1).drop('Original Input12', axis=1)
df_1['Average Input'] = stripped_df.mean(axis=1)

# Q1.5
df_1.to_csv('./output/question1_out.csv', index=False)

# pd.set_option("display.max_rows", None, "display.max_columns", None)
print(df_1)

print('\n' * 5)
print('*' * 20)
print('Question 2')
print('*' * 20)

df_2 = pd.read_csv('./specs/DNAData_question2.csv')

# Q2.1
pca_model = PCA(n_components=22)
reduced_df = pd.DataFrame(data=pca_model.fit_transform(df_2))
print(sum(pca_model.explained_variance_ratio_))

# Q2.2
bins_df = pd.DataFrame()
for x in range(22):
    bins_df['pca' + str(x) + '_width'] = pd.cut((reduced_df[x]), 10)

# Q2.3
for x in range(22):
    bins_df['pca' + str(x) + '_freq'] = pd.qcut((reduced_df[x]), 10)

# Q2.4
Q2output = pd.concat([df_2, bins_df], axis=1)
print(Q2output)

Q2output.to_csv('./output/question2_out.csv', index=False)
