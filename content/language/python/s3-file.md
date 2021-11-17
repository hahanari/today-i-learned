# Python으로 S3 다루기
## S3 접근하여 파일 읽어오기
```python
import boto3
import pandas as pd

s3_client = boto3.client("s3")

BUCKET_NAME = "iris-bucket"
path = "path/sample/iris.csv"

object = s3_client.get_object(Bucket=BUCKET_NAME, Key=path)

# csv file을 읽어 dataframe으로 변환
df = pd.read_csv(object["Body"], encoding="utf8", quotechar='"')
```