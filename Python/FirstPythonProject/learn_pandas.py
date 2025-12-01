import pandas as pd

df = pd.read_csv('addresses.csv')
df['Added'] = [i for i in range(13)]
df.drop("Address", axis=1, inplace=True)
df = df.iloc[0:5]
df.to_csv('output.csv', index=False)
