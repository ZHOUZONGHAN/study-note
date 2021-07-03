# Mybatis-plus入门

## Mybatis-plus配置文件信息

详情参考官方文档配置：https://mp.baomidou.com/config/

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mp?useSSL=false&serverTimezone=GMT%2B8
    username: root
    password: root

logging:
  level:
    root: warn
    com.zhou.mybatisplus.dao: trace
  pattern:
    console: '%p%m%n'

# 配置mp的mapper文件位置
mybatis-plus:
  mapper-locations: classpath*:mapper/*.xml
  # 主键全局策略，局部策略优于全局策略
  global-config:
    db-config:
      id-type: id_worker
```

### 表对应的实体类：

```java
@Data
// 指定表明  可以让类名和表名不一致
@TableName("user")
public class User extends Model<User> {

    @TableId// 通过TableId来指定Id，不设置默认只能查找名字为id的属性，如果不为id将会报错
    private Long id;// 主键  主键是通过雪花算法获取的

    @TableField("name")// 指定该属性在数据库中的名字
    private String name;// 姓名
    private Integer age;// 年龄
    private String email;// 邮件
    private Long managerId;// 直属上级
    private LocalDateTime createTime;// 创建时间

    // 需要使用一个临时字段remark，使remark属性只存在于User实体中，
    // 不需要在数据库中有相应的字段，如果不做任何处理，MP将会在数据库中找不到相应的字段

    // 方法一：用transient修饰的成员变量不参与序列化过程
    private transient String remark1;// 备注信息
    // 方法二：使用static修饰remark   注意！！！：lombok不会为静态变量生成get/set，需要自己手动生成
    private static String remark2;
    public static String getRemark2() {
        return remark2;
    }
    public static void setRemark2(String remark2) {
        User.remark2 = remark2;
    }
    // 方法三：使用@TableField(exist = false)
    @TableField(exist = false)
    private String remark3;

}
```

### Mapper中的select操作：

```java
@Test
public void select() {
    List<User> users = userMapper.selectList(null);
    // Assert.assertEquals()
    // 1. 如果两者一致, 程序继续往下运行
    // 2. 如果两者不一致, 中断测试方法, 抛出异常信息 AssertionFailedError
    Assert.assertEquals(7, users.size());
    users.forEach(System.out::println);
}

@Test
public void selectById() {
    User user = userMapper.selectById(1088248166370832385L);
    System.out.println(user);
}

@Test
public void selectIds() {
    List<Long> ids = Arrays.asList(1088248166370832385L, 1088250446457389058L, 1094592041087729666L);
    List<User> users = userMapper.selectBatchIds(ids);
    users.forEach(System.out::println);
}

@Test
public void selectByMap() {
    // 注意！！！  Map中的Key是数据库中的列，不是实体中的属性名
    Map<String, Object> columnMap = new HashMap<>();
    columnMap.put("name","王天风");
    columnMap.put("age",27);
    List<User> users = userMapper.selectByMap(columnMap);
    users.forEach(System.out::println);
}

@Test
public void selectByWrapperSupper3() {
    User user = new User();
    user.setName("刘红雨");
    user.setAge(32);
    // 以实体为参数，与条件互不干扰
    // 如果使用实体传参需要模糊查询，则需要在实体的属性上加上注解 @TableField(condition = SqlCondition.LIKE)
    // 则查询语句将会是 name like '%刘红雨%'
    QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * 名字中包含雨并且年龄小于40
     * name like '%雨%' and age<40
     */
@Test
public void selectByWrapper1() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    //        QueryWrapper<User> query = Wrappers.<User>query();
    queryWrapper.like("name","雨").lt("age",40);
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * 名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
     * name like '%雨%' and age between 20 and 40 and email is not null
     */
@Test
public void selectByWrapper2() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("name","雨").between("age",20,40).isNotNull("email");
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     * name like '王%' or age>=25 order by age desc,id asc
     */
@Test
public void selectByWrapper3() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.likeRight("name","王").or().ge("age",25).orderByDesc("age").orderByAsc("id");
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * 创建日期为2019年2月14日并且直属上级为名字为王姓
     * date_format(create_time,'%Y-%m-%d')='2019-02-14'
     * and manager_id in (select id from user where name like '王%')
     */
@Test
public void selectByWrapper4() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    // 此处不写{0}也可以获取同样的结果，但是会有sql注入的风险
    // 使用{0}会将后面的值最为一个参数，不会有sql注入的风险
    queryWrapper.apply("date_format(create_time,'%Y-%m-%d') = {0}","2019-02-14")
        .inSql("manager_id","select id from user where name like '王%'");
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     * name like '王%' and (age<40 or email is not null)
     */
@Test
public void selectByWrapper5() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.likeRight("name","王").and(wq -> wq.lt("age",40).or().isNotNull("email"));
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     * name like '王%' or (age<40 and age>20 and email is not null)
     */
@Test
public void selectByWrapper6() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.likeRight("name","王")
        .or(wq -> wq.lt("age",40).gt("age",20));
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * （年龄小于40或邮箱不为空）并且名字为王姓
     *  (age<40 or email is not null) and name like '王%'
     */
@Test
public void selectByWrapper7() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.nested(wq -> wq.lt("age",40).or().isNotNull("email")).likeRight("name","王");
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * 年龄为30、31、34、35
     * age in (30、31、34、35)
     */
@Test
public void selectByWrapper8() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.in("age",Arrays.asList(30,31,34,35));
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * 只返回满足条件的其中一条语句即可
     * limit 1
     */
@Test
public void selectByWrapper9() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.in("age",Arrays.asList(30,31,34,35)).last("limit 1");
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * 名字中包含雨并且年龄小于40(需求1加强版)
     * 第一种情况：select id,name
     * 	           from user
     * 	           where name like '%雨%' and age<40
     * 第二种情况：select id,name,age,email
     * 	           from user
     * 	           where name like '%雨%' and age<40
     */
@Test
public void selectByWrapperSupper1() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    // 第一种情况
    //        queryWrapper.select("id","name").like("name","雨").lt("age",40);
    // 第二种情况
    queryWrapper.like("name","雨").lt("age",40)
        .select(User.class,info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"));
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}


@Test
public void selectByWrapperAllEq() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    Map<String, Object> params = new HashMap<>();
    params.put("name","王天风");
    params.put("age",null);
    // 在allEq中加上false可以过滤所有值为null的属性，否则语句将会变成 age is null
    //        queryWrapper.allEq(params, false);
    queryWrapper.allEq((k, v) -> !k.equals("name"),params);
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

// 当实体中有很多字段时，但是又不要查询所有字段，只需要部分字段，便选用map，如果使用list则会有很多值为null
// 或者当返回的结果是统计查询
@Test
public void selectMaps1() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.select("id", "name").like("name","雨").lt("age",40);
    List<Map<String, Object>> users = userMapper.selectMaps(queryWrapper);
    users.forEach(System.out::println);
}

/**
     * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。
     * 并且只取年龄总和小于500的组。
     * select avg(age) avg_age,min(age) min_age,max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) <500
     */
@Test
public void selectMaps2() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.select("avg(age) avg_age","min(age) min_age","max(age) max_age")
        .groupBy("manager_id").having("sum(age) < {0}", 500);
    List<Map<String, Object>> users = userMapper.selectMaps(queryWrapper);
    users.forEach(System.out::println);
}

// 获取第一列的值，其他的列将会被舍弃，可以用来获取一列的值
@Test
public void selectByWrapperObjs() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.select("id", "name").like("name","雨").lt("age",40);
    List<Object> users = userMapper.selectObjs(queryWrapper);
    users.forEach(System.out::println);
}

// 获取第一列的值，其他的列将会被舍弃，可以用来获取一列的值
@Test
public void selectByWrapperCount() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("name","雨").lt("age",40);
    Integer count = userMapper.selectCount(queryWrapper);
    System.out.println(count);
}

// ！！！注意！！！ 查询结果必须要是一条或者没有，如果多于一条就会报错
@Test
public void selectByWrapperOne() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("name","刘红雨").lt("age",40);
    User user = userMapper.selectOne(queryWrapper);
    System.out.println(user);
}

// 有防误写的作用
@Test
public void selectLambda1() {
    //        LambdaQueryWrapper<User> lambda3 = new QueryWrapper<User>().lambda();
    //        LambdaQueryWrapper<User> lambda2 = new LambdaQueryWrapper<>();
    LambdaQueryWrapper<User> lambda = Wrappers.<User>lambdaQuery();
    lambda.like(User::getName, "雨").lt(User::getAge, 40);
    List<User> users = userMapper.selectList(lambda);
    users.forEach(System.out::println);
}

@Test
public void selectLambda2() {
    LambdaQueryWrapper<User> lambda = Wrappers.<User>lambdaQuery();
    lambda.likeRight(User::getName, "王").and(lqw -> lqw.lt(User::getAge,40).or().isNotNull(User::getEmail));
    List<User> users = userMapper.selectList(lambda);
    users.forEach(System.out::println);
}

@Test
public void selectLambda3() {
    List<User> users = new LambdaQueryChainWrapper<User>(userMapper)
        .like(User::getName, "雨").ge(User::getAge, 20).list();
    users.forEach(System.out::println);
}

// 使用自定义的SQL语句进行查询
@Test
public void selectMyConfig() {
    LambdaQueryWrapper<User> lambda = Wrappers.<User>lambdaQuery();
    lambda.likeRight(User::getName, "王").and(lqw -> lqw.lt(User::getAge,40).or().isNotNull(User::getEmail));
    List<User> users = userMapper.selectAll(lambda);
    users.forEach(System.out::println);
}

// 分页查询
@Test
public void selectPage() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.ge("age",26);

    // 第三个参数为是否需要总记录数
    Page<User> page = new Page<>(1,2,false);

    //        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
    //        System.out.println("总页数：" + iPage.getPages());
    //        System.out.println("总记录数：" + iPage.getTotal());
    //        List<User> users = iPage.getRecords();

    IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, queryWrapper);
    System.out.println("总页数：" + iPage.getPages());
    System.out.println("总记录数：" + iPage.getTotal());
    List<Map<String, Object>> users = iPage.getRecords();

    users.forEach(System.out::println);
}

@Test
public void selectMyPage() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.ge("age",26);

    Page<User> page = new Page<>(1,2);

    IPage<User> iPage = userMapper.selectUserPage(page, queryWrapper);
    System.out.println("总页数：" + iPage.getPages());
    System.out.println("总记录数：" + iPage.getTotal());
    List<User> users = iPage.getRecords();

    users.forEach(System.out::println);
}
```

### Mapper中的insert操作：

```java
@Test
public void insert() {
    User user = new User();
    user.setName("zhou");
    user.setAge(21);
    user.setManagerId(1088248166370832385L);
    user.setCreateTime(LocalDateTime.now());
    int rows = userMapper.insert(user);
    System.out.println("=========" + rows);
}
```

### Mapper中的update操作：

```java
@Test
public void updateById() {
    User user = new User();
    user.setId(1088248166370832385L);
    user.setAge(26);
    int i = userMapper.updateById(user);
    System.out.println(i);
}

@Test
public void updateByWrapper1() {
    UpdateWrapper<User> wrapper = new UpdateWrapper<>();
    wrapper.eq("name","李艺伟").eq("age",28);
    User user = new User();
    user.setAge(29);
    int i = userMapper.update(user, wrapper);
    System.out.println(i);
}

@Test
public void updateByWrapper2() {
    // 可以在构造器中传入user实例，user对象将会作为参数拼接到where语句中
    User user1 = new User();
    user1.setName("李艺伟");
    user1.setAge(29);
    UpdateWrapper<User> wrapper = new UpdateWrapper<>(user1);
    User user2 = new User();
    user2.setAge(29);
    int i = userMapper.update(user2, wrapper);
    System.out.println(i);
}

@Test
public void updateByWrapper3() {
    UpdateWrapper<User> wrapper = new UpdateWrapper<>();
    // 直接在wrapper中set修改的值
    wrapper.eq("name","李艺伟").eq("age",28).set("age",30);
    int i = userMapper.update(null, wrapper);
    System.out.println(i);
}

@Test
public void updateByWrapperLambda() {
    LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
    updateWrapper.eq(User::getName,"李艺伟").eq(User::getAge,30).set(User::getAge,31);
    int i = userMapper.update(null, updateWrapper);
    System.out.println(i);
}

@Test
public void updateByWrapperLambdaChain() {
    boolean res = new LambdaUpdateChainWrapper<User>(userMapper)
            .eq(User::getName, "李艺伟").eq(User::getAge, 29).set(User::getAge, 30).update();
    System.out.println(res);
}
```

### Mapper中的delete操作：

```java
@Test
public void deleteById() {
    int i = userMapper.deleteById(1275747204769808385L);
    System.out.println(i);
}

@Test
public void deleteByMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("name","zhou");
    int i = userMapper.deleteByMap(map);
    System.out.println(i);
}

@Test
public void deleteByBatchIds() {
    int res = userMapper.deleteBatchIds(Arrays.asList(1275747204769808385L, 1275747204769808386L, 1275747204769808387L));
    System.out.println(res);
}

@Test
public void deleteByWrapper() {
    LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
    queryWrapper.eq(User::getAge,27).or().ge(User::getAge,41);
    int res = userMapper.delete(queryWrapper);
    System.out.println(res);
}
```

### Mapper中的AR操作：

> Active Record(活动记录)，简称AR，是一种领域模型模式，特点就是一个模型类对应关系型数据库中的一个表，而模型类的一个实例对应表中的一条记录

```java
@Test
public void insertAR() {
    User user = new User();
    user.setName("zong");
    user.setAge(21);
    user.setManagerId(1088248166370832385L);
    user.setCreateTime(LocalDateTime.now());
    boolean insert = user.insert();
    System.out.println(insert);
}

@Test
public void selectById1AR() {
    User user = new User();
    User selectById = user.selectById(1088248166370832385L);
    System.out.println(selectById);
}

@Test
public void selectById2AR() {
    // 可以在实例中设置id，则不需要传参
    User user = new User();
    user.setId(1088248166370832385L);
    User selectById = user.selectById();
    System.out.println(selectById);
}

@Test
public void updateByIdAR() {
    User user = new User();
    user.setId(1088248166370832385L);
    user.setName("han");
    boolean update = user.updateById();
    System.out.println(update);
}

@Test
public void deleteByIdAR() {
    User user = new User();
    user.setId(1088248166370832385L);
    // 删除不存在的返回true
    boolean delete = user.deleteById();
    System.out.println(delete);
}

@Test
public void insertOrUpdateAR() {
    // 当实例中的id没有值时，会执行insert语句
    // 有值时会先判断数据库中是否存在该id，如果存在则执行update，不存在则执行insert
    User user = new User();
    user.setId(1088248166370832385L);
    user.setName("zzh");
    boolean insertOrUpdate = user.insertOrUpdate();
    System.out.println(insertOrUpdate);
}
```

