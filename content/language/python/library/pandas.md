# pandas
## DataFrame
```python
import pandas as pd

df = pd.DataFrame({
    'A': [1, 2, 3],
    'B': [4.1, 5.2, 6.3],
    'C': ["7", "8", "9"]})

print(df)
# A    B  C
# 0  1  4.1  7
# 1  2  5.2  8
# 2  3  6.3  9

print(df.dtypes)
# A      int64
# B    float64
# C     object
# dtype: object
```
### .apply(str)
특정 행이나 열의 타입을 변경할 때
```python
df = pd.DataFrame({
    'A': [1, 2, 3],
    'B': [4.1, 5.2, 6.3],
    'C': ["7", "8", "9"]})

df["A"].apply(str)
```
