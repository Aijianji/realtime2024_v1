#### 第二天

架构

![image-20241211135547638](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20241211135547638.png)

模拟数据

日志

![image-20241211212449602](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20241211212449602.png)

业务

![image-20241211212508742](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20241211212508742.png)



数据倾斜写法

```
with t1 as (
    select user_id,
           order_no,
           concat(region,'-',rand()) as  region,
           product_no,
           color_no,
           sale_amount,
           ts
    from data_incline_t
)
select substr(region,1,2) as region,
       count(*) as cnt
from (
    select region,
           count(*) as cnt
    from t1
    group by region
     ) as t2
group by substr(region,1,2);
```

```
说明
根据地区id分组就会数据倾斜 然后呢，你在地区后面拼接个随机数，再count()，由于随机数的原因，那么就都是1个。然后切割后再分组   最终结果依然是8千万对8百万 但是速度从19秒降到了12秒。
这个叫先两阶段聚合然后全局聚合
注:
map得都走完 才能reduce。所以map阶段，让a,b两个人去两个教室数桌子(map)，必须要等他俩都数完，才能汇总(reduce)。那么a的那个教室3张桌子，b的教室300张桌子，a一定是比b快的，但是b数不完无法汇总(reduce)，就是数据倾斜
```

