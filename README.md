# 安裝與啟動

1. 安裝 jdk1.8
2. clone專案
3. 使用IDE啟動 `WebappApplication.java`

------

1. 如果沒 IDE ，到專案根目錄輸入以下命令

   ```sh
   java -jar webapp-0.0.1-SNAPSHOT.jar
   ```

# 測試

使用瀏覽器或其他插件發GET請求

```sh
localhost:8080/api/v1/exchangeRate/?source=TWD&target=USD&amount=$70
```

