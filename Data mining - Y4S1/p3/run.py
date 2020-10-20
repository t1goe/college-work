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

df_1 = pd.read_csv('./specs/gpa_question1.csv')
df_1 = df_1.round(3)
# Q1.1
df_1.drop('count', axis=1, inplace=True)

# Q1.2

