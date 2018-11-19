接单 http://nightwing.top:8080/shop/order/{order_id}/accept 

完成 order/{order_id}/complete  

拿菜品信息 /order/details/{order_id}  

![1542182546396](C:\Users\Wenjor\Desktop\1542182546396.png)![1542182559902](C:\Users\Wenjor\Desktop\1542182559902.png)

```java
  st = "http://nightwing.top:8080/order/1/status=20";
            HttpClientClass httpclient12 =null;
            try {
                httpclient12 = new HttpClientClass(st,"GET","JSON",
                        null,handle3,headers);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HttpClientClass finalHttpclient12 =httpclient12;
            
```



```json
{"msg":"success","code":0,"data":[{"id":11,"tel":"15725365670","password":"B0001FGAO4","role":1,"createdAt":"2018-10-24T08:58:33.000+0000","updatedAt":null},{"id":12,"tel":"14581906515","password":"B00066USKU","role":1,"createdAt":"2018-10-24T08:58:33.000+0000","updatedAt":null},{"id":13,"tel":"17720429416","password":"B000AA5SMU","role":1,"createdAt":"2018-10-24T08:58:33.000+0000","updatedAt":null},{"id":14,"tel":"13205586387","password":"867149","role":0,"createdAt":"2018-11-03T00:50:42.000+0000","updatedAt":null},{"id":15,"tel":"3911482","password":"867149076","role":0,"createdAt":"2018-11-03T00:52:51.000+0000","updatedAt":null},{"id":16,"tel":"867149076","password":"867149076","role":0,"createdAt":"2018-11-03T00:55:44.000+0000","updatedAt":null}]}
```



```xml
<com.wenjor.topbar.Goods
    android:id="@+id/goods1"
    android:layout_width="match_parent"
    android:layout_height="151dp"
    android:layout_weight="0"

    custom:amount="X1"
    custom:amountTextColor="#000000"
    custom:amountTextSize="13sp"
    custom:bt1="编辑"
    custom:bt1Background="#66b2e1"
    custom:bt1TextColor="#FFFFFF"
    custom:bt1TextSize="8sp"
    custom:image="@mipmap/jin"
    custom:name="Jin"
    custom:nameTextColor="#000000"
    custom:nameTextSize="12sp"
    custom:price="$2 biliion"
    custom:priceTextColor="#FF4848"
    custom:priceTextSize="11sp"

    >

</com.wenjor.topbar.Goods>
```