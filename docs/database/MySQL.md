# 存储引擎：

### **InnoDB**

> InnoDB**支持事务**，其设计目标主要面向OLTP(Online Transaction Processing, 在线事务处理)的应用。**其特点是行锁设计、支持外键，并默认读取操作不会产生锁。**

### MyISAM

> MyISAM**不支持事务、表锁设计，支持全文索引**，主要面向OLAP(Online Analytical Processing, 联机分析处理)数据库应用。MyISAM的**缓冲池中只缓存索引文件，而不缓存数据文件**。

### NDB

> NDB的数据全部放在内存中(从MySQL5.1开始，可以将非索引数据放在磁盘上)，因此通过主键查找非常快速，并且添加NDB节点可以线性地提高数据库性能，是高可用、高性能的集群系统。
>
> NDB的连接操作(join)是在MySQL数据库层完成的，而不是在存储引擎完成的，会导致复杂的连接操作需要巨大的网络开销。

### Memory

> Memory将表中的数据存放在内存中，非常适合存储临时数据的临时表。Memory默认使用哈希索引，而不是我们熟悉的B+树索引。Memory只支持表锁，并发性能较差，并且不能存储`TEXT(文本字符串)`和`BLOB(binary large object, 二进制大对象)`，`存储变长字段(varchar)`时是按照`定长字段(char)`的方式进行的，浪费内存。
>
> Memory作为临时表来存储查询的中间结果集，如果中间结果集大于Memory的容量设置，又或者中间结果集包含TEXT和BLOB类型字段，则MySQL会将其转换为MyISAM，MyISAM不缓存数据文件，因此这时产生的临时表的性能对于查询会有损失。

### Archive

> Archive只支持INSERT和SELECT操作，使用zlib算法将数据行进行压缩后存储，压缩比一般可以有1：10，非常适合存储归档数据。Archive使用行锁来实现高并发的插入操作，但本身不是事务安全的，其设计的主要目的是提供高速的插入和压缩功能。

### Federated

> Federated不存放数据，它只指向一台远程的MySQL服务器上的表。

### Maria

> 支持缓存数据和索引文件，应用了行锁设计，提供了MVCC功能，支持事务和非事务安全的选项，以及更好的BLOB字符类型的处理性能。

# InnoDB

## InnoDB版本

|     版本     |               功能               |
| :----------: | :------------------------------: |
| 老版本InnoDB |     支持ACID、行锁设计、MVCC     |
| InnoDB 1.0.x |  增加了compress和dynamic页格式   |
| InnoDB 1.1.x |    增加了Linux AIO、多回滚段     |
| InnoDB 1.2.x | 增加了全文索引支持、在线索引添加 |

## InnoDB体系架构

### 后台线程

1. **Master Thread**

	> Master Thread是一个非常核心的后台线程，主要负责将缓冲池中的数据异步刷新到磁盘，保证数据的一致性。

2. **IO Thread**

	> 在InnoDB中大量**使用了AIO来处理写IO请求**，这样可以极大的提高数据库的性能。而IO Thread的工作主要是负责这些IO请求的回调处理。InnoDB 1.0版本之前共有4个IO Thread，分别是write、read、insert buffer和log IO thread。从InnoDB 1.0开始，read thread和write thread分别增大到了4个，使用innodb_read_io_threads和innodb_write_io_threads参数进行设置。

3. **Purge Thread**

	> 事务被提交后，其所使用的undo log可能不再需要，因此需要**PurgeThread来回收已经使用并分配的undo页**。InnoDb 1.1版本之前，purge操作仅在InnoDB的Master Thread中完成；从InnoDB 1.1版本开始，purge操作可以独立到单独的线程中进行，以此来减轻Master Thread的工作，从而提高CPU的使用率以及提高存储引擎的性能。
	>
	> 从InnoDB 1.2版本开始，为了加快undo页的回收，InnoDB支持多个Purge Thread。

4. **Page Cleaner Thread**

	> Page Cleaner Thread是在InnoDB 1.2.x版本中引入的。其作用是将之前版本中的脏页的刷新操作都放入到单独的线程中来完成，是为了减轻原Master Thread的工作和对于用户查询线程的阻塞。

### **内存：**

1. 缓冲池

	> InnoDB是基于磁盘存储的，并将其中的记录**按照页的方式进行管理**。因此可将其视为基于磁盘的数据库系统(Disk-base Database)。因为CPU和磁盘性能相差巨大，所以需要使用缓冲池来弥补性能差。
	>
	> 在数据库中进行读取页的操作，首先将从磁盘读到的页存放在缓冲池中，这个过程称为将页"FIX"在缓冲池中。对页进行修改操作，首先修改缓冲池中的页，然后再以一定频率刷新到磁盘上。缓冲池刷新回磁盘的操作并不是在每次页发生更新时触发，而是通过一种称为"Checkpoint"的机制刷新回磁盘。
	>
	> 缓冲池中缓存的数据类型有：索引页、数据页、undo页、插入缓存(insert buffer)、自适应哈希索引(adaptive hash index)、InnoDB存储的锁信息(lock info)、数据字典信息(data dicitionary)等。不能简单地认为缓冲池只是缓存索引页和数据页，它们只是占缓冲池很大一部分而已。
	>
	> InnoDB 1.0.x开始，允许有多个缓冲池实例。每个页根据哈希值平均分配到不同缓冲池实例中。

2. LRU List、Free List和Flush List

	>  

### InnoDB关键特性

- 插入缓冲

	1. Insert Buffer

		> 这个名字可能会让人认为插入缓存是缓冲池中的一个组成部分。其实不然，InnoDB缓冲池是有Insert Buffer信息，但是Insert Buffer和数据也一样，是物理页的一个组成部分。
		>
		> Insert Buffer对于非聚集索引的插入或者更新操作，不是每一次都直接插入到索引页，而是先判断插入的非聚集索引页是否在缓冲池中，若是在，则直接插入；不在则放入一个Insert Buffer中。然后再以一定的频率和情况进行Insert Buffer和辅助索引页子节点的merge操作。
		>
		> 使用Insert Buffer的两个条件：
		>
		> 1. 索引是辅助索引
		> 2. 索引不是唯一的

	2. Change Buffer

		> Change Buffer可以视为Insert Buffer的升级。可以对DML操作——INSERT、DELETE、UPDATE都进行缓冲。

	3. Merge Insert Buffer

		> 

- DoubleWrite

	> 

- 自适应哈希索引

	> 

- 异步IO

	> 

- 刷新邻接表

	>  

## **索引**

- 聚集索引
- 辅助索引
- 联合索引
- 覆盖索引

## **MVCC**

