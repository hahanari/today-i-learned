# Python 기본 함수

## 리스트: list
### append
```python
a_li = []
b_li = ["b"]

a_li.append("a")
b_li.append(a_li)
```
### extend
```python
a_li = [["a"], "b"]
b_li = []
for a in a_li:
    b_li.extend(a)
```

## 집합: set
### add
```python
a_set = set()
b_set = {"b"}

a_set.add(b_set)
```
### union
```python
a_set = {"a"}
b_set = {"b"}
a_set.union(b_set)
```


