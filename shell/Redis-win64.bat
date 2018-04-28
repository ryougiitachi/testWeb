rem redis-server用于在服务端直接操作配置redis服务器
rem 启动redis
rem windows版本有两个配置文件redis.windows.conf和redis.windows-service.conf，默认情况下两者只在log设置上有区别。
#redis-server redis.windows-service.conf
rem 指定启动时最大内存
#redis-server redis.windows.conf --maxmemory 200m
rem 安装redis服务，默认名称为Redis
#redis-server --service-install redis.windows.conf
rem 卸载redis服务
#redis-server --service-uninstall
rem 启动默认的windows服务Redis
#redis-server --service-start
rem 停止默认的windows服务Redis
#redis-server --service-stop
rem 指定参数安装redis服务，默认端口是6379
rem 这样写应该是错的
rem redis-server --service-install --service-name RedisService01 --port 10001 redis.windows-service.conf
rem redis-server --service-install --service-name RedisService02 --port 10002 redis.windows-service.conf
rem 这样应该是对的
rem 在windows系统中，如果没有相应的service-name，再执行一下命令后会自动创建一个 
#redis-server --service-install redis.windows-service.conf --service-name RedisService03 --port 10003
rem 启动指定的windows服务
#redis-server --service-start --service-name RedisService01
rem 停止指定的windows服务
#redis-server --service-stop --service-name RedisService01
rem 卸载指定的windows服务
#redis-server --service-uninstall --service-name RedisService01

rem redis配置文件中日志的配置logfile是个很神奇的设定
rem 

rem 具体可参考http://www.runoob.com/redis/redis-keys.html
rem redis-cli主要用于登陆redis服务器，默认没有密码
rem 客户端命令下可以执行加值减值查值等命令
#redis-cli -h 127.0.0.1 -p 10001 -a mypass
rem 登陆之后可以执行以下命令
rem 注意：redis里面的值是可以互相覆盖的，一个键值a原来为hash的话是可以被覆盖成string的，反过来却不行，到时候再访问就要用相对应的命令访问了，否则会报错，不知道是不是windows版本实现的问题
rem 查看当前所有的键，可以指定键值
#keys *
rem 检查键值是否存在
#exists key kst
rem 删除给定键值
#del typ dekyj
rem 返回键值剩余生存时间，-1为永久
#ttl key 
rem 查看某键值的类型，不能用星号
rem redis的五种类型：字符串、列表、哈希、集合、有序集合，定义的变量应该是区分大小写的
rem 除了zset的分数可为double，其他类型里面的元素应该都是字符串
#type archer

rem 字符串string，单键最大限制为512m
rem 设置字符串
#set keystr valuestr
rem 获取字符串
#get string

rem 哈希表hash，每个哈希最大数量为无符号整型最大值
rem 新建哈希键，若有重复键值按照后面的为准
#hmset mapServant archer emiya saber Arthur archer gilgamesh assassin sasaki Rider Alexander
rem 查看哈希映射
#hgetall mapServant
rem 获取哈希的键值
#hget map key
rem 设置哈希键值
#hset map key value
rem 列出所有哈希键
#hkeys *
rem 哈希的大小
#hlen map
rem 获取多字段值
#hmget map key1 key2
rem 获取所有值
#hvals map 

rem 列表list，每个哈希最大数量为无符号整型最大值
rem 定义list
#lpush list saber archer 
rem 查看list，0开始，索引超过不报错
#lrange list 0 10

rem 集合set，每个哈希最大数量为无符号整型最大值
rem 定义并添加元素，返回成功添加数
#sadd set 1 2
rem 列出集合中的元素
#smembers set 

rem 有序集zset
rem 向有序集添加元素，分数可以重复，成员不可重复，有序集通过分数排序，同一分数的成员无序
#zadd zset 1 saber 2 archer 
rem 同一条命令加相同的成员随后面的
#zadd zset 0 rider 2 rider 
rem 不同命令加相同的成员不成功
#zadd zset 3 rider
rem 查看有序集成员
#zrangebyscore zset 0 100
