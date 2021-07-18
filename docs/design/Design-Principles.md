# 单一职责原则（SRP）

> **单一职责原则是 Single Responsibility Principle，缩写为 SRP。**
>
> A class or module should have a single responsibility.
> 一个类或者模块只负责完成一个职责（或者功能）。
>
> 这个原则描述的对象包含两个，一个是类（class），一个是模块（module）。关于这两个概念，有两种理解方式。一种理解是：把模块看作比类更加抽象的概念，类也可以看作模块。另一种理解是：把模块看作比类更加粗粒度的代码块，模块中包含多个类，多个类组成一个模块。
>
> 单一职责原则的定义描述非常简单，即一个类只负责完成一个职责或者功能。也就是说，不要设计大而全的类，要设计粒度小、功能单一的类。换个角度来讲就是，一个类包含了两个或者两个以上业务不相干的功能，那就说明职责不够单一，应该将其拆分成多个功能更加单一、粒度更细的类。

## 如何判断类的职责是否足够单一？

> 不同的应用场景、不同阶段的需求背景下，对同一个类的职责是否单一的判定，可能都是不一样的。在某种应用场景或者当下的需求背景下，一个类的设计可能已经满足单一职责原则了，但如果换个应用场景或着在未来的某个需求背景下，可能就不满足了，需要继续拆分成粒度更细的类。
>
> 评价一个类的职责是否足够单一，并没有一个非常明确的、可以量化的标准，可以说，这是件非常主观、仁者见仁智者见智的事情。实际上，在真正的软件开发中，也没必要过于未雨绸缪，过度设计。**可以先写一个粗粒度的类，满足业务需求。随着业务的发展，如果粗粒度的类越来越庞大，代码越来越多，这个时候，就可以将这个粗粒度的类，拆分成几个更细粒度的类，保持持续重构。**
>
> 下面几条判断原则，比起很主观地去思考类是否职责单一，要更有指导意义、更具有可执行性：
>
> - 类中的代码行数、函数或属性过多，会影响代码的可读性和可维护性，我们就需要考虑对类进行拆分
> - 类依赖的其他类过多，或者依赖类的其他类过多，不符合高内聚、低耦合的设计思想，我们就需要考虑对类进行拆分
> - 私有方法过多，我们就要考虑能否将私有方法独立到新的类中，设置为 public 方法，供更多的类使用，从而提高代码的复用性
> - 比较难给类起一个合适名字，很难用一个业务名词概括，或者只能用一些笼统的 Manager、Context 之类的词语来命名，这就说明类的职责定义得可能不够清晰
> - 类中大量的方法都是集中操作类中的某几个属性，比如，在 UserInfo 例子中，如果一半的方法都是在操作 address 信息，那就可以考虑将这几个属性和对应的方法拆分出来

## 类的职责是否设计得越单一越好？

> 为了满足单一职责原则，是不是把类拆得越细就越好呢？答案是否定的。
>
> 可以通过Serialization 类实现了一个简单协议的序列化和反序列功能来证明。如果想让类的职责更加单一，可以对 Serialization 类进一步拆分，拆分成一个只负责序列化工作的 Serializer 类和另一个只负责反序列化工作的 Deserializer 类。
>
> 虽然经过拆分之后，Serializer 类和 Deserializer 类的职责更加单一了，但也随之带来了新的问题。如果我们修改了协议的格式，或者序列化方式从 JSON 改为了 XML，那 Serializer 类和 Deserializer 类都需要做相应的修改，代码的内聚性显然没有原来 Serialization 高了。而且，如果我们仅仅对 Serializer 类做了协议修改，而忘记了修改 Deserializer 类的代码，那就会导致序列化、反序列化不匹配，程序运行出错，代码的可维护性变差了。
>
> **实际上，不管是应用设计原则还是设计模式，最终的目的还是提高代码的可读性、可扩展性、复用性、可维护性等。在考虑应用某一个设计原则是否合理的时候，也可以以此作为最终的考量标准。**

# 开闭原则（OCP）

> **开闭原则的英文全称是 Open Closed Principle，简写为 OCP。**
>
> software entities (modules, classes, functions, etc.) should be open for extension , but closed for modification.
> 软件实体（模块、类、方法等）应该“对扩展开放、对修改关闭”。
>
> 即添加一个新的功能应该是，在已有代码基础上扩展代码（新增模块、类、方法等），而非修改已有代码（修改模块、类、方法等）。

## 修改代码就意味着违背开闭原则吗？

> 开闭原则可以应用在不同粒度的代码中，可以是模块，也可以类，还可以是方法（及其属性）。同样一个代码改动，在粗代码粒度下，被认定为“修改”，在细代码粒度下，又可以被认定为“扩展”。
>
> 添加属性和方法相当于修改类，在类这个层面，这个代码改动可以被认定为“修改”；但这个代码改动并没有修改已有的属性和方法，在方法（及其属性）这一层面，它又可以被认定为“扩展”。
>
> 实际上，也没必要纠结某个代码改动是“修改”还是“扩展”，更没必要太纠结是否违反“开闭原则”。回到这条原则的设计初衷：只要没有破坏原有的代码的正常运行，没有破坏原有的单元测试，就可以说，这是一个合格的代码改动。
>
> 而且，添加一个新功能，不可能任何模块、类、方法的代码都不“修改”，这个是做不到的。类需要创建、组装、并且做一些初始化操作，才能构建成可运行的的程序，这部分代码的修改是在所难免的。要做到尽量让修改操作更集中、更少、更上层，尽量让最核心、最复杂的那部分逻辑代码满足开闭原则。

## 如何做到“对扩展开放、修改关闭”？

> **为了尽量写出扩展性好的代码，我们要时刻具备扩展意识、抽象意识、封装意识。这些“潜意识”可能比任何开发技巧都重要。**
>
> 在写代码的时候后，要多花点时间往前多思考一下，这段代码未来可能有哪些需求变更、如何设计代码结构，事先留好扩展点，以便在未来需求变更的时候，不需要改动代码整体结构、做到最小代码改动的情况下，新的代码能够很灵活地插入到扩展点上，做到“对扩展开放、对修改关闭”。
>
> 还有，在识别出代码可变部分和不可变部分之后，要将可变部分封装起来，隔离变化，提供抽象化的不可变接口，给上层系统使用。当具体的实现发生变化的时候，只需要基于相同的抽象接口，扩展一个新的实现，替换掉老的实现即可，上游系统的代码几乎不需要修改。
>
> 对于一些比较确定的、短期内可能就会扩展，或者需求改动对代码结构影响比较大的情况，或者实现成本不高的扩展点，在编写代码的时候之后，就可以事先做些扩展性设计。但对于一些不确定未来是否要支持的需求，或者实现起来比较复杂的扩展点，可以等到有需求驱动的时候，再通过重构代码的方式来支持扩展的需求。
>
> 当然，开闭原则也并不是免费的。有些情况下，代码的扩展性会跟可读性相冲突。为了更好地支持扩展性，可以对代码进行了重构，重构之后的代码可能要比之前的代码复杂很多，理解起来也更加有难度。很多时候，都需要在扩展性和可读性之间做权衡。在某些场景下，代码的扩展性很重要，我们就可以适当地牺牲一些代码的可读性；在另一些场景下，代码的可读性更加重要，就适当地牺牲一些代码的可扩展性。

# 里式替换（LSP）

> **里式替换原则的英文翻译是：Liskov Substitution Principle，缩写为 LSP。**
>
> If S is a subtype of T, then objects of type T may be replaced with objects of type S, without breaking the program.
> 																																										——1986, Barbara Liskov
>
> Functions that use pointers of references to base classes must be able to use objects of derived classes without knowing it.
> 																																										——1996, Robert Martin
> 子类对象（object of subtype/derived class）能够替换程序（program）中父类对象（object of base/parent class）出现的任何地方，并且保证原来程序的逻辑行为（behavior）不变及正确性不被破坏。
>
> 虽然从定义描述和代码实现上来看，多态和里式替换有点类似，但它们关注的角度是不一样的。多态是面向对象编程的一大特性，也是面向对象编程语言的一种语法。它是一种代码实现的思路。而里式替换是一种设计原则，是用来指导继承关系中子类该如何设计的，子类的设计要保证在替换父类的时候，不改变原有程序的逻辑以及不破坏原有程序的正确性。
>
> **实际上，里式替换原则还有另外一个更加能落地、更有指导意义的描述，那就是“Design By Contract”，中文翻译就是“按照协议来设计”。**
>
> 子类在设计的时候，要遵守父类的行为约定（或者叫协议）。父类定义了函数的行为约定，那子类可以改变函数的内部实现逻辑，但不能改变函数原有的行为约定。这里的行为约定包括：函数声明要实现的功能；对输入、输出、异常的约定；甚至包括注释中所罗列的任何特殊说明。实际上，定义中父类和子类之间的关系，也可以替换成接口和实现类之间的关系。

## 哪些代码明显违背了 LSP？

> - **子类违背父类声明要实现的功能**
    >
    >   > 父类中提供的 sortOrdersByAmount() 订单排序函数，是按照金额从小到大来给订单排序的，而子类重写这个 sortOrdersByAmount() 订单排序函数之后，是按照创建日期来给订单排序的。那子类的设计就违背里式替换原则。
>
> - **子类违背父类对输入、输出、异常的约定**
    >
    >   > 在父类中，某个函数约定：运行出错的时候返回 null；获取数据为空的时候返回空集合（empty collection）。而子类重载函数之后，实现变了，运行出错返回异常（exception），获取不到数据返回 null。那子类的设计就违背里式替换原则。
    >   >
    >   > 在父类中，某个函数约定，输入数据可以是任意整数，但子类实现的时候，只允许输入数据是正整数，负数就抛出，也就是说，子类对输入的数据的校验比父类更加严格，那子类的设计就违背了里式替换原则。
    >   >
    >   > 在父类中，某个函数约定，只会抛出 ArgumentNullException 异常，那子类的设计实现中只允许抛出 ArgumentNullException 异常，任何其他异常的抛出，都会导致子类违背里式替换原则。
>
> - **子类违背父类注释中所罗列的任何特殊说明**
    >
    >   > 父类中定义的 withdraw() 提现函数的注释是这么写的：“用户的提现金额不得超过账户余额……”，而子类重写 withdraw() 函数之后，针对 VIP 账号实现了透支提现的功能，也就是提现金额可以大于账户余额，那这个子类的设计也是不符合里式替换原则的。
>
> 判断子类的设计实现是否违背里式替换原则，有一个小窍门，那就是拿父类的单元测试去验证子类的代码。如果某些单元测试运行失败，就有可能说明，子类的设计实现没有完全地遵守父类的约定，子类有可能违背了里式替换原则。

# 接口隔离原则（ISP）

> **接口隔离原则的英文翻译是“ Interface Segregation Principle”，缩写为 ISP。**
>
> Clients should not be forced to depend upon interfaces that they do not use.
> 客户端不应该被强迫依赖它不需要的接口（“客户端”，可以理解为接口的调用者或者使用者）。
>
> 在软件开发中，可以把接口看作一组抽象的约定，也可以具体指系统与系统之间的 API 接口，还可以特指面向对象编程语言中的接口等。可以把“接口”理解为下面三种东西：
>
> 1. 一组 API 接口集合
> 2. 单个 API 接口或函数
> 3. OOP 中的接口概念

## 把“接口”理解为一组 API 接口集合

> 微服务用户系统提供了一组跟用户相关的 API 给其他系统使用，比如：注册、登录、获取用户信息等。当后台管理系统要实现删除用户的功能，希望用户系统提供一个删除用户的接口。通常在 UserService 中新添加一个 deleteUserByCellphone() 或 deleteUserById() 接口就可以了。**这个方法可以解决问题，但是也隐藏了一些安全隐患。**
>
> 删除用户是一个非常慎重的操作，一般只希望通过后台管理系统来执行，所以这个接口只限于给后台管理系统使用。如果把它放到 UserService 中，那所有使用到 UserService 的系统，都可以调用这个接口。不加限制地被其他业务系统调用，就有可能导致误删用户。
>
> 当然，最好的解决方案是从架构设计的层面，通过接口鉴权的方式来限制接口的调用。不过，如果暂时没有鉴权框架来支持，还可以从代码设计的层面，尽量避免接口被误用。参照接口隔离原则，**调用者不应该强迫依赖它不需要的接口**，将删除接口单独放到另外一个接口 RestrictedUserService 中，然后将 RestrictedUserService 只打包提供给后台管理系统来使用。
>
> **在设计微服务或者类库接口的时候，如果部分接口只被部分调用者使用，那就需要将这部分接口隔离出来，单独给对应的调用者使用，而不是强迫其他调用者也依赖这部分不会被用到的接口。**

## 把“接口”理解为单个 API 接口或函数

> 接口隔离原则就可以理解为：函数的设计要功能单一，不要将多个不同的功能逻辑在一个函数中实现。
>
> ```java
> public class Statistics {
> private Long max;
> private Long min;
> private Long average;
> private Long sum;
> private Long percentile99;
> private Long percentile999;
> //...constructor/getter/setter等方法...
> }
> 
> public Statistics count(Collection<Long> dataSet) {
> Statistics statistics = new Statistics();
> //...计算逻辑...
> return statistics;
> }
> ```
>
> 在上面的代码中，count() 函数的功能不够单一，包含很多不同的统计功能，比如，求最大值、最小值、平均值等等。按照接口隔离原则，应该把 count() 函数拆成几个更小粒度的函数，每个函数负责一个独立的统计功能。拆分之后的代码如下所示：
>
> ```java
> public Long max(Collection<Long> dataSet) { //... }
> public Long min(Collection<Long> dataSet) { //... } 
> public Long average(Colletion<Long> dataSet) { //... }
> // ...其他统计函数...
> ```
>
> 接口隔离原则跟单一职责原则有点类似，不过稍微还是有点区别。**单一职责原则针对的是模块、类、接口的设计。而接口隔离原则相对于单一职责原则，一方面它更侧重于接口的设计，另一方面它的思考的角度不同。**它提供了一种判断接口是否职责单一的标准：通过调用者如何使用接口来间接地判定。如果调用者只使用部分接口或接口的部分功能，那接口的设计就不够职责单一。

## 把“接口”理解为 OOP 中的接口概念

> 假设我们的项目中用到了三个外部系统：Redis、MySQL、Kafka。每个系统都对应一系列配置信息，比如地址、端口、访问超时时间等。为了在内存中存储这些配置信息，供项目中的其他模块来使用，我们分别设计实现了三个 Configuration 类：RedisConfig、MysqlConfig、KafkaConfig。
>
> 现在，有一个新的功能需求，希望支持 Redis 和 Kafka 配置信息的热更新。但是，因为某些原因，并不希望对 MySQL 的配置信息进行热更新。为了实现这样一个功能需求，设计实现了一个 ScheduledUpdater 类，以固定时间频率（periodInSeconds）来调用 RedisConfig、KafkaConfig 的 update() 方法更新配置信息。
>
> ```java
> public interface Updater {
> void update();
> }
> 
> public class RedisConfig implemets Updater {
> //...省略其他属性和方法...
> @Override
> public void update() { //... }
> }
> 
> public class KafkaConfig implements Updater {
> //...省略其他属性和方法...
> @Override
> public void update() { //... }
> }
> 
> public class MysqlConfig { //...省略其他属性和方法... }
> 
> public class ScheduledUpdater {
>  private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();;
>  private long initialDelayInSeconds;
>  private long periodInSeconds;
>  private Updater updater;
> 
>  public ScheduleUpdater(Updater updater, long initialDelayInSeconds, long periodInSeconds) {
>      this.updater = updater;
>      this.initialDelayInSeconds = initialDelayInSeconds;
>      this.periodInSeconds = periodInSeconds;
>  }
> 
>  public void run() {
>      executor.scheduleAtFixedRate(new Runnable() {
>          @Override
>          public void run() {
>              updater.update();
>          }
>      }, this.initialDelayInSeconds, this.periodInSeconds, TimeUnit.SECONDS);
>  }
> }
> 
> public class Application {
> ConfigSource configSource = new ZookeeperConfigSource(/*省略参数*/);
> public static final RedisConfig redisConfig = new RedisConfig(configSource);
> public static final KafkaConfig kafkaConfig = new KakfaConfig(configSource);
> public static final MySqlConfig mysqlConfig = new MysqlConfig(configSource);
> 
> public static void main(String[] args) {
>  ScheduledUpdater redisConfigUpdater = new ScheduledUpdater(redisConfig, 300, 300);
>  redisConfigUpdater.run();
> 
>  ScheduledUpdater kafkaConfigUpdater = new ScheduledUpdater(kafkaConfig, 60, 60);
>  kafkaConfigUpdater.run();
> }
> }
> ```
>
> 又有了一个新的监控功能需求。通过命令行来查看 Zookeeper 中的配置信息是比较麻烦的。希望能有一种更加方便的配置信息查看方式。
>
> ```java
> public interface Updater {
> void update();
> }
> 
> public interface Viewer {
> String outputInPlainText();
> Map<String, String> output();
> }
> 
> public class RedisConfig implemets Updater, Viewer {
> //...省略其他属性和方法...
> @Override
> public void update() { //... }
> @Override
> public String outputInPlainText() { //... }
> @Override
> public Map<String, String> output() { //...}
> }
> 
> public class KafkaConfig implements Updater {
> //...省略其他属性和方法...
> @Override
> public void update() { //... }
> }
> 
> public class MysqlConfig implements Viewer {
> //...省略其他属性和方法...
> @Override
> public String outputInPlainText() { //... }
> @Override
> public Map<String, String> output() { //...}
> }
> 
> public class SimpleHttpServer {
> private String host;
> private int port;
> private Map<String, List<Viewer>> viewers = new HashMap<>();
> 
> public SimpleHttpServer(String host, int port) {//...}
> 
> public void addViewers(String urlDirectory, Viewer viewer) {
>  if (!viewers.containsKey(urlDirectory)) {
>    viewers.put(urlDirectory, new ArrayList<Viewer>());
>  }
>  this.viewers.get(urlDirectory).add(viewer);
> }
> 
> public void run() { //... }
> }
> 
> public class Application {
>  ConfigSource configSource = new ZookeeperConfigSource();
>  public static final RedisConfig redisConfig = new RedisConfig(configSource);
>  public static final KafkaConfig kafkaConfig = new KakfaConfig(configSource);
>  public static final MySqlConfig mysqlConfig = new MySqlConfig(configSource);
> 
>  public static void main(String[] args) {
>      ScheduledUpdater redisConfigUpdater =
>          new ScheduledUpdater(redisConfig, 300, 300);
>      redisConfigUpdater.run();
> 
>      ScheduledUpdater kafkaConfigUpdater =
>          new ScheduledUpdater(kafkaConfig, 60, 60);
>      redisConfigUpdater.run();
> 
>      SimpleHttpServer simpleHttpServer = new SimpleHttpServer(“127.0.0.1”, 2389);
>      simpleHttpServer.addViewer("/config", redisConfig);
>      simpleHttpServer.addViewer("/config", mysqlConfig);
>      simpleHttpServer.run();
>  }
> }
> ```
>
> 新设计了两个功能非常单一的接口：Updater 和 Viewer。ScheduledUpdater 只依赖 Updater 这个跟热更新相关的接口，不需要被强迫去依赖不需要的 Viewer 接口，满足接口隔离原则。同理，SimpleHttpServer 只依赖跟查看信息相关的 Viewer 接口，不依赖不需要的 Updater 接口，也满足接口隔离原则。
>
> **这样设计思路更加灵活、易扩展、易复用。因为 Updater、Viewer 职责更加单一，单一就意味了通用、复用性好。**

# 依赖反转原则（DIP）

> **依赖反转原则的英文翻译是 Dependency Inversion Principle，缩写为 DIP。有时候也叫依赖倒置原则。**
>
> High-level modules shouldn’t depend on low-level modules. Both modules should depend on abstractions. In addition, abstractions shouldn’t depend on details. Details depend on abstractions.
> 高层模块不要依赖低层模块。高层模块和低层模块应该通过抽象来互相依赖。除此之外，抽象不要依赖具体实现细节，具体实现细节依赖抽象。
>
> 所谓高层模块和低层模块的划分，简单来说就是，在调用链上，调用者属于高层，被调用者属于低层。在平时的业务代码开发中，高层模块依赖底层模块是没有任何问题的。实际上，这条原则主要还是用来指导框架层面的设计，跟前面讲到的控制反转类似。
>
> 拿 Tomcat 这个 Servlet 容器作为例子来说明，Tomcat 是运行 Java Web 应用程序的容器。编写的 Web 应用程序代码只需要部署在 Tomcat 容器下，便可以被 Tomcat 容器调用执行。按照划分原则，Tomcat 就是高层模块，编写的 Web 应用程序代码就是低层模块。Tomcat 和应用程序代码之间并没有直接的依赖关系，两者都依赖同一个“抽象”，也就是 Servlet 规范。Servlet 规范不依赖具体的 Tomcat 容器和应用程序的实现细节，而 Tomcat 容器和应用程序依赖 Servlet 规范。

## 控制反转（IOC）

> **控制反转的英文翻译是 Inversion Of Control，缩写为 IOC。**
>
> “控制”指的是对程序执行流程的控制，而“反转”指的是在没有使用框架之前，程序员自己控制整个程序的执行。在使用框架之后，整个程序的执行流程可以通过框架来控制。流程的控制权从程序员“反转”到了框架。
>
> 控制反转并不是一种具体的实现技巧，而是一个比较笼统的设计思想，一般用来指导框架层面的设计。

## 依赖注入（DI）

> **依赖注入的英文翻译是 Dependency Injection，缩写为 DI。**
>
> 依赖注入是不通过 new() 的方式在类内部创建依赖类对象，而是将依赖的类对象在外部创建好之后，通过构造函数、函数参数等方式传递（或注入）给类使用。
>
> 通过依赖注入的方式来将依赖的类对象传递进来，这样就提高了代码的扩展性，可以灵活地替换依赖的类。
>
> ```java
> public class Notification {
> private MessageSender messageSender;
> 
> public Notification(MessageSender messageSender) {
>  this.messageSender = messageSender;
> }
> 
> public void sendMessage(String cellphone, String message) {
>  this.messageSender.send(cellphone, message);
> }
> }
> 
> public interface MessageSender {
> void send(String cellphone, String message);
> }
> 
> // 短信发送类
> public class SmsSender implements MessageSender {
> @Override
> public void send(String cellphone, String message) {
>  //....
> }
> }
> 
> // 站内信发送类
> public class InboxSender implements MessageSender {
> @Override
> public void send(String cellphone, String message) {
>  //....
> }
> }
> 
> //使用Notification
> MessageSender messageSender = new SmsSender();
> Notification notification = new Notification(messageSender);
> ```

## 依赖注入框架（DI Framework）

> 在实际的软件开发中，一些项目可能会涉及几十、上百、甚至几百个类，类对象的创建和依赖注入会变得非常复杂。如果这部分工作都是靠程序员自己写代码来完成，容易出错且开发成本也比较高。而对象创建和依赖注入的工作，本身跟具体的业务无关，完全可以抽象成框架来自动完成。
>
> 只需要通过依赖注入框架提供的扩展点，简单配置一下所有需要创建的类对象、类与类之间的依赖关系，就可以实现由框架来自动创建对象、管理对象的生命周期、依赖注入等原本需要程序员来做的事情。
>
> 现成的依赖注入框架有很多，比如 Google Guice、Java Spring、Pico Container、Butterfly Container 等。

# KISS 原则

> KISS 原则的英文描述有好几个版本
>
> Keep It Simple and Stupid.
> Keep It Short and Simple.
> Keep It Simple and Straightforward.
> 尽量保持简单。
>
> KISS 原则就是保持代码可读和可维护的重要手段。代码足够简单，也就意味着很容易读懂，bug 比较难隐藏。即便出现 bug，修复起来也比较简单。

## 代码行数越少就越“简单”吗？

> 通过三个方法可以实现同样一个功能：检查输入的字符串 ipAddress 是否是合法的 IP 地址。
>
> ```java
> // 第一种实现方式: 使用正则表达式
> public boolean isValidIpAddressV1(String ipAddress) {
> if (StringUtils.isBlank(ipAddress)) return false;
> String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
>           + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
>           + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
>           + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
>   return ipAddress.matches(regex);
> }
> 
> // 第二种实现方式: 使用现成的工具类
> public boolean isValidIpAddressV2(String ipAddress) {
>   if (StringUtils.isBlank(ipAddress)) return false;
>   String[] ipUnits = StringUtils.split(ipAddress, '.');
>   if (ipUnits.length != 4) {
>     return false;
>   }
>   for (int i = 0; i < 4; ++i) {
>     int ipUnitIntValue;
>     try {
>       ipUnitIntValue = Integer.parseInt(ipUnits[i]);
>     } catch (NumberFormatException e) {
>       return false;
>     }
>     if (ipUnitIntValue < 0 || ipUnitIntValue > 255) {
>       return false;
>     }
>     if (i == 0 && ipUnitIntValue == 0) {
>       return false;
>     }
>   }
>   return true;
> }
> 
> // 第三种实现方式: 不使用任何工具类
> public boolean isValidIpAddressV3(String ipAddress) {
>   char[] ipChars = ipAddress.toCharArray();
>   int length = ipChars.length;
>   int ipUnitIntValue = -1;
>   boolean isFirstUnit = true;
>   int unitsCount = 0;
>   for (int i = 0; i < length; ++i) {
>     char c = ipChars[i];
>     if (c == '.') {
>       if (ipUnitIntValue < 0 || ipUnitIntValue > 255) return false;
>       if (isFirstUnit && ipUnitIntValue == 0) return false;
>       if (isFirstUnit) isFirstUnit = false;
>       ipUnitIntValue = -1;
>       unitsCount++;
>       continue;
>     }
>     if (c < '0' || c > '9') {
>       return false;
>     }
>     if (ipUnitIntValue == -1) ipUnitIntValue = 0;
>     ipUnitIntValue = ipUnitIntValue * 10 + (c - '0');
>   }
>   if (ipUnitIntValue < 0 || ipUnitIntValue > 255) return false;
>   if (unitsCount != 3) return false;
>   return true;
> }
> ```
>
> 第一种实现方式利用的是正则表达式，只用三行代码就把这个问题搞定了。它的代码行数最少，那是不是就最符合 KISS 原则呢？答案是否定的。虽然代码行数最少，看似最简单，实际上却很复杂。这正是因为它使用了正则表达式。
>
> 一方面，正则表达式本身是比较复杂的，写出完全没有 bug 的正则表达本身就比较有挑战；另一方面，并不是每个程序员都精通正则表达式。对于不怎么懂正则表达式的同事来说，看懂并且维护这段正则表达式是比较困难的。这种实现方式会导致代码的可读性和可维护性变差，所以，从 KISS 原则的设计初衷上来讲，这种实现方式并不符合 KISS 原则。
>
> 第二种实现方式使用了 StringUtils 类、Integer 类提供的一些现成的工具函数，来处理 IP 地址字符串。第三种实现方式，不使用任何工具函数，而是通过逐一处理 IP 地址中的字符，来判断是否合法。从代码行数上来说，这两种方式差不多。
>
> 虽然第三种实现方式性能会更高一些。一般来说，工具类的功能都比较通用和全面，所以，在代码实现上，需要考虑和处理更多的细节，执行效率就会有所影响。而第三种实现方式，完全是自己操作底层字符，只针对 IP 地址这一种格式的数据输入来做处理，没有太多多余的函数调用和其他不必要的处理逻辑，所以，在执行效率上，这种类似定制化的处理代码方式肯定比通用的工具类要高些。
>
> 但是，第三种要比第二种更加有难度，更容易写出 bug。从可读性上来说，第二种实现方式的代码逻辑更清晰、更好理解。所以，在这两种实现方式中，第二种实现方式更加“简单”，更加符合 KISS 原则。除非 isValidIpAddress() 函数是影响系统性能的瓶颈代码，否则，这样优化的投入产出比并不高，增加了代码实现的难度、牺牲了代码的可读性，性能上的提升却并不明显。

## 代码逻辑复杂就违背 KISS 原则吗？

> **KMP算法**
>
> ```java
> // KMP algorithm: a, b分别是主串和模式串；n, m分别是主串和模式串的长度。
> public static int kmp(char[] a, int n, char[] b, int m) {
> int[] next = getNexts(b, m);
> int j = 0;
> for (int i = 0; i < n; ++i) {
>  while (j > 0 && a[i] != b[j]) { // 一直找到a[i]和b[j]
>    j = next[j - 1] + 1;
>  }
>  if (a[i] == b[j]) {
>    ++j;
>  }
>  if (j == m) { // 找到匹配模式串的了
>    return i - m + 1;
>  }
> }
> return -1;
> }
> 
> // b表示模式串，m表示模式串的长度
> private static int[] getNexts(char[] b, int m) {
> int[] next = new int[m];
> next[0] = -1;
> int k = -1;
> for (int i = 1; i < m; ++i) {
>  while (k != -1 && b[k + 1] != b[i]) {
>    k = next[k];
>  }
>  if (b[k + 1] == b[i]) {
>    ++k;
>  }
>  next[i] = k;
> }
> return next;
> }
> ```
>
> 这段代码完全符合逻辑复杂、实现难度大、可读性差的特点，但它并不违反 KISS 原则。
>
> KMP 算法以快速高效著称。当需要处理长文本字符串匹配问题（几百 MB 大小文本内容的匹配），或者字符串匹配是某个产品的核心功能（比如 Vim、Word 等文本编辑器），又或者字符串匹配算法是系统性能瓶颈的时候，就应该选择尽可能高效的 KMP 算法。而 KMP 算法本身具有逻辑复杂、实现难度大、可读性差的特点。**本身就复杂的问题，用复杂的方法解决，并不违背 KISS 原则。**
>
> 不过，平时的项目开发中涉及的字符串匹配问题，大部分都是针对比较小的文本。在这种情况下，直接调用编程语言提供的现成的字符串匹配函数就足够了。如果非得用 KMP 算法、BM 算法来实现字符串匹配，那就真的违背 KISS 原则了。**也就是说，同样的代码，在某个业务场景下满足 KISS 原则，换一个应用场景可能就不满足了。**

## 如何写出满足 KISS 原则的代码？

> - 不要使用同事可能不懂的技术来实现代码。比如的正则表达式，还有一些编程语言中过于高级的语法等。
> - 不要重复造轮子，要善于使用已经有的工具类库。经验证明，自己去实现这些类库，出 bug 的概率会更高，维护的成本也比较高。
> - 不要过度优化。不要过度使用一些奇技淫巧（比如，位运算代替算术运算、复杂的条件语句代替 if-else、使用一些过于底层的函数等）来优化代码，牺牲代码的可读性。
>
> 评判代码是否简单，还有一个很有效的间接方法，那就是 code review。如果在 code review 的时候，同事对代码有很多疑问，那就说明代码有可能不够“简单”，需要优化啦。
>
> **在做开发的时候，一定不要过度设计，不要觉得简单的东西就没有技术含量。实际上，越是能用简单的方法解决复杂的问题，越能体现一个人的能力。**

#  YAGNI 原则

> You Ain’t Gonna Need It.
> 你不会需要它。
>
> 当用在软件开发中的时候，它的意思是：不要去设计当前用不到的功能；不要去编写当前用不到的代码。实际上，这条原则的核心思想就是：**不要做过度设计**。
>
> 比如，系统暂时只用 Redis 存储配置信息，以后可能会用到 ZooKeeper。根据 YAGNI 原则，在未用到 ZooKeeper 之前，没必要提前编写这部分代码。当然，这并不是说就不需要考虑代码的扩展性。还是要预留好扩展点，等到需要的时候，再去实现 ZooKeeper 存储配置信息这部分代码。
>
> 再比如，不要在项目中提前引入不需要依赖的开发包。对于 Java 程序员来说，经常使用 Maven 或者 Gradle 来管理依赖的类库（library）。有时为了避免开发中 library 包缺失而频繁地修改 Maven 或者 Gradle 配置文件，提前往项目里引入大量常用的 library 包。实际上，这样的做法也是违背 YAGNI 原则的。
>
> **KISS 原则讲的是“如何做”的问题（尽量保持简单），而 YAGNI 原则说的是“要不要做”的问题（当前不需要的就不要做）。**

# DRY 原则

> Don’t Repeat Yourself.
> 不要重复自己。

## 实现逻辑重复

> ```java
> public class UserAuthenticator {
> public void authenticate(String username, String password) {
>  if (!isValidUsername(username)) {
>    // ...throw InvalidUsernameException...
>  }
>  if (!isValidPassword(password)) {
>    // ...throw InvalidPasswordException...
>  }
>  //...省略其他代码...
> }
> 
> private boolean isValidUsername(String username) {
>  // check not null, not empty
>  if (StringUtils.isBlank(username)) {
>    return false;
>  }
>  // check length: 4~64
>  int length = username.length();
>  if (length < 4 || length > 64) {
>    return false;
>  }
>  // contains only lowcase characters
>  if (!StringUtils.isAllLowerCase(username)) {
>    return false;
>  }
>  // contains only a~z,0~9,dot
>  for (int i = 0; i < length; ++i) {
>    char c = username.charAt(i);
>    if (!(c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '.') {
>      return false;
>    }
>  }
>  return true;
> }
> 
> private boolean isValidPassword(String password) {
>  // check not null, not empty
>  if (StringUtils.isBlank(password)) {
>    return false;
>  }
>  // check length: 4~64
>  int length = password.length();
>  if (length < 4 || length > 64) {
>    return false;
>  }
>  // contains only lowcase characters
>  if (!StringUtils.isAllLowerCase(password)) {
>    return false;
>  }
>  // contains only a~z,0~9,dot
>  for (int i = 0; i < length; ++i) {
>    char c = password.charAt(i);
>    if (!(c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '.') {
>      return false;
>    }
>  }
>  return true;
> }
> }
> ```
>
> 在代码中，有两处非常明显的重复的代码片段：isValidUserName() 函数和 isValidPassword() 函数。重复的代码被敲了两遍，或者简单 copy-paste 了一下，看起来明显违反 DRY 原则。为了移除重复的代码，对上面的代码做下重构，将 isValidUserName() 函数和 isValidPassword() 函数，合并为一个更通用的函数 isValidUserNameOrPassword()。
>
> ```java
> public class UserAuthenticatorV2 {public void authenticate(String userName, String password) { if (!isValidUsernameOrPassword(userName)) {   // ...throw InvalidUsernameException... } if (!isValidUsernameOrPassword(password)) {   // ...throw InvalidPasswordException... }}private boolean isValidUsernameOrPassword(String usernameOrPassword) { //省略实现逻辑 //跟原来的isValidUsername()或isValidPassword()的实现逻辑一样... return true;}}
> ```
>
> 经过重构之后，代码行数减少了，也没有重复的代码了，是不是更好了呢？**答案是否定的。**
>
> 合并之后的 isValidUserNameOrPassword() 函数，负责两件事情：验证用户名和验证密码，**违反了“单一职责原则”和“接口隔离原则”**。
>
> isValidUserName() 和 isValidPassword() 两个函数，虽然从代码实现逻辑上看起来是重复的，但是从语义上并不重复。所谓“语义不重复”指的是：从功能上来看，这两个函数干的是完全不重复的两件事情，一个是校验用户名，另一个是校验密码。尽管在目前的设计中，两个校验逻辑是完全一样的，但如果按照第二种写法，将两个函数的合并，那就会存在潜在的问题。在未来的某一天，如果修改了密码的校验逻辑，比如，允许密码包含大写字符，允许密码的长度为 8 到 64 个字符，那这个时候，isValidUserName() 和 isValidPassword() 的实现逻辑就会不相同。我们就要把合并后的函数，重新拆成合并前的那两个函数。
>
> 尽管代码的实现逻辑是相同的，但语义不同，判定它并不违反 DRY 原则。对于包含重复代码的问题，可以通过抽象成更细粒度函数的方式来解决。比如将校验只包含 a\~z、0\~9、dot 的逻辑封装成 boolean onlyContains(String str, String charlist); 函数。

## 功能语义重复

> 在同一个项目代码中有下面两个函数：isValidIp() 和 checkIfIpValid()。尽管两个函数的命名不同，实现逻辑不同，但功能是相同的，都是用来判定 IP 地址是否合法的。
>
> ```java
> public boolean isValidIp(String ipAddress) {if (StringUtils.isBlank(ipAddress)) return false;String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";  return ipAddress.matches(regex);}public boolean checkIfIpValid(String ipAddress) {  if (StringUtils.isBlank(ipAddress)) return false;  String[] ipUnits = StringUtils.split(ipAddress, '.');  if (ipUnits.length != 4) {    return false;  }  for (int i = 0; i < 4; ++i) {    int ipUnitIntValue;    try {      ipUnitIntValue = Integer.parseInt(ipUnits[i]);    } catch (NumberFormatException e) {      return false;    }    if (ipUnitIntValue < 0 || ipUnitIntValue > 255) {      return false;    }    if (i == 0 && ipUnitIntValue == 0) {      return false;    }  }  return true;}
> ```
>
> 尽管两段代码的实现逻辑不重复，但语义重复，也就是功能重复，我们认为它违反了 DRY 原则。
>
> 如果哪天项目中 IP 地址是否合法的判定规则改变了，比如：255.255.255.255 不再被判定为合法的了，相应地，对 isValidIp() 的实现逻辑做了相应的修改，但却忘记了修改 checkIfIpValid() 函数。又或者，压根就不知道还存在一个功能相同的 checkIfIpValid() 函数，这样就会导致有些代码仍然使用老的 IP 地址判断逻辑，导致出现一些莫名其妙的 bug。

## 代码执行重复

> UserService 中 login() 函数用来校验用户登录是否成功。如果失败，就返回异常；如果成功，就返回用户信息。
>
> ```java
> public class UserService {private UserRepo userRepo;//通过依赖注入或者IOC框架注入public User login(String email, String password) { boolean existed = userRepo.checkIfUserExisted(email, password); if (!existed) {   // ... throw AuthenticationFailureException... } User user = userRepo.getUserByEmail(email); return user;}}public class UserRepo {public boolean checkIfUserExisted(String email, String password) { if (!EmailValidation.validate(email)) {   // ... throw InvalidEmailException... } if (!PasswordValidation.validate(password)) {   // ... throw InvalidPasswordException... } // ...query db to check if email&password exists...}public User getUserByEmail(String email) { if (!EmailValidation.validate(email)) {   // ... throw InvalidEmailException... } // ...query db to get user by email...}}
> ```
>
> 在 login() 函数中，email 的校验逻辑被执行了两次。一次是在调用 checkIfUserExisted() 函数的时候，另一次是调用 getUserByEmail() 函数的时候。这个问题解决起来比较简单，只需要将校验逻辑从 UserRepo 中移除，统一放到 UserService 中就可以了。
>
> 代码中还有一处比较隐蔽的执行重复，login() 函数并不需要调用 checkIfUserExisted() 函数，只需要调用一次 getUserByEmail() 函数，从数据库中获取到用户的 email、password 等信息，然后跟用户输入的 email、password 信息做对比，依次判断是否登录成功。
>
> 实际上，这样的优化是很有必要的。因为 checkIfUserExisted() 函数和 getUserByEmail() 函数都需要查询数据库，而数据库这类的 I/O 操作是比较耗时的。**在写代码的时候，应当尽量减少这类 I/O 操作。**

## 代码复用性（Code Reusability）

> 代码复用表示一种行为：在开发新功能的时候，尽量复用已经存在的代码。
>
> 代码的可复用性表示一段代码可被复用的特性或能力：在编写代码的时候，让代码尽量可复用。
>
> DRY 原则是一条原则：不要写重复的代码。
>
> 尽管复用、可复用性、DRY 原则这三者从理解上有所区别，但实际上要达到的目的都是类似的，都是为了减少代码量，提高代码的可读性、可维护性。除此之外，复用已经经过测试的老代码，bug 会比从零重新开发要少。
>
> “复用”这个概念不仅可以指导细粒度的模块、类、函数的设计开发，实际上，一些框架、类库、组件等的产生也都是为了达到复用的目的。比如，Spring 框架、Google Guava 类库、UI 组件等等。

### 怎么提高代码复用性？

> - 减少代码耦合
    >
    >   对于高度耦合的代码，当希望复用其中的一个功能，想把这个功能的代码抽取出来成为一个独立的模块、类或者函数的时候，往往会发现牵一发而动全身。移动一点代码，就要牵连到很多其他相关的代码。所以，高度耦合的代码会影响到代码的复用性，要尽量减少代码耦合。
>
> - 满足单一职责原则
    >
    >   如果职责不够单一，模块、类设计得大而全，那依赖它的代码或者它依赖的代码就会比较多，进而增加了代码的耦合。根据上一点，也就会影响到代码的复用性。相反，越细粒度的代码，代码的通用性会越好，越容易被复用。
>
> - 模块化
    >
    >   “模块”不单单指一组类构成的模块，还可以理解为单个类、函数。要善于将功能独立的代码，封装成模块。独立的模块就像一块一块的积木，更加容易复用，可以直接拿来搭建更加复杂的系统。
>
> - 业务与非业务逻辑分离
    >
    >   越是跟业务无关的代码越是容易复用，越是针对特定业务的代码越难复用。为了复用跟业务无关的代码，将业务和非业务逻辑代码分离，抽取成一些通用的框架、类库、组件等。
>
> - 通用代码下沉
    >
    >   从分层的角度来看，越底层的代码越通用、会被越多的模块调用，越应该设计得足够可复用。一般情况下，在代码分层之后，为了避免交叉调用导致调用关系混乱，只允许上层代码调用下层代码及同层代码之间的调用，杜绝下层代码调用上层代码。所以，通用的代码尽量下沉到更下层。
>
> - 继承、多态、抽象、封装
    >
    >   利用继承，可以将公共的代码抽取到父类，子类复用父类的属性和方法。利用多态，可以动态地替换一段代码的部分逻辑，让这段代码可复用。除此之外，抽象和封装，从更加广义的层面、而非狭义的面向对象特性的层面来理解的话，越抽象、越不依赖具体的实现，越容易复用。代码封装成模块，隐藏可变的细节、暴露不变的接口，就越容易复用。
>
> - 应用模板等设计模式
    >
    >   一些设计模式，也能提高代码的复用性。比如，模板模式利用了多态来实现，可以灵活地替换其中的部分代码，整个流程模板代码可复用。
>
> **除了上面这些方法之外，复用意识也非常重要。在写代码的时候，要多去思考一下，这个部分代码是否可以抽取出来，作为一个独立的模块、类或者函数供多处使用。在设计每个模块、类、函数的时候，要像设计一个外部 API 那样，去思考它的复用性。**

# 迪米特法则（LOD）

> **迪米特法则的英文翻译是：Law of Demeter，缩写是 LOD。**它还有另外一个更加达意的名字，叫作**最小知识原则**，英文翻译为：**The Least Knowledge Principle.**
>
> Each unit should have only limited knowledge about other units: only units “closely” related to the current unit. Or: Each unit should only talk to its friends; Don’t talk to strangers.
> 每个模块只应该了解那些与它关系密切的模块的有限知识。或者说，每个模块只和自己的朋友“说话”，不和陌生人“说话”。
>
> 不该有直接依赖关系的类之间，不要有依赖；有依赖关系的类之间，尽量只依赖必要的接口（也就是定义中的“有限知识”）。

## 实例一

> **不该有直接依赖关系的类之间，不要有依赖。**
>
> 代码中包含三个主要的类。其中，NetworkTransporter 类负责底层网络通信，根据请求获取数据；HtmlDownloader 类用来通过 URL 获取网页；Document 表示网页文档，后续的网页内容抽取、分词、索引都是以此为处理对象。
>
> ```java
> public class NetworkTransporter { // 省略属性和其他方法... public Byte[] send(HtmlRequest htmlRequest) {   //... }}public class HtmlDownloader {private NetworkTransporter transporter;//通过构造函数或IOC注入public Html downloadHtml(String url) { Byte[] rawHtml = transporter.send(new HtmlRequest(url)); return new Html(rawHtml);}}public class Document {private Html html;private String url;public Document(String url) { this.url = url; HtmlDownloader downloader = new HtmlDownloader(); this.html = downloader.downloadHtml(url);}//...}
> ```
>
> NetworkTransporter 类作为一个底层网络通信类，它的功能应该尽可能通用，而不只是服务于下载 HTML，所以，不应该直接依赖太具体的发送对象 HtmlRequest。从这一点上讲，NetworkTransporter 类的设计违背迪米特法则，依赖了不该有直接依赖关系的 HtmlRequest 类。
>
> Document 类构造函数中的 downloader.downloadHtml() 逻辑复杂，耗时长，不应该放到构造函数中，会影响代码的可测试性；HtmlDownloader 对象在构造函数中通过 new 来创建，违反了基于接口而非实现编程的设计思想，也会影响到代码的可测试性；从业务含义上来讲，Document 网页文档没必要依赖 HtmlDownloader 类，违背了迪米特法则。
>
> ```java
> public class NetworkTransporter { // 省略属性和其他方法... public Byte[] send(String address, Byte[] data) {   //... }}public class HtmlDownloader {private NetworkTransporter transporter;//通过构造函数或IOC注入// HtmlDownloader这里也要有相应的修改public Html downloadHtml(String url) { HtmlRequest htmlRequest = new HtmlRequest(url); Byte[] rawHtml = transporter.send(   htmlRequest.getAddress(), htmlRequest.getContent().getBytes()); return new Html(rawHtml);}}public class Document {private Html html;private String url;public Document(String url, Html html) { this.html = html; this.url = url;}//...}// 通过一个工厂方法来创建Documentpublic class DocumentFactory {private HtmlDownloader downloader;public DocumentFactory(HtmlDownloader downloader) { this.downloader = downloader;}public Document createDocument(String url) { Html html = downloader.downloadHtml(url); return new Document(url, html);}}
> ```

## 实例二

> **有依赖关系的类之间，尽量只依赖必要的接口。**
>
> Serialization 类负责对象的序列化和反序列化。
>
> ```java
> public class Serialization {public String serialize(Object object) { String serializedResult = ...; //... return serializedResult;}public Object deserialize(String str) { Object deserializedResult = ...; //... return deserializedResult;}}
> ```
>
> 假设在项目中，有些类只用到了序列化操作，而另一些类只用到反序列化操作。那基于迪米特法则后半部分“有依赖关系的类之间，尽量只依赖必要的接口”，只用到序列化操作的那部分类不应该依赖反序列化接口。同理，只用到反序列化操作的那部分类不应该依赖序列化接口。
>
> 根据这个思路，应该将 Serialization 类拆分为两个更小粒度的类，一个只负责序列化（Serializer 类），一个只负责反序列化（Deserializer 类）。拆分之后，使用序列化操作的类只需要依赖 Serializer 类，使用反序列化操作的类只需要依赖 Deserializer 类。
>
> 但是拆分之后的代码更能满足迪米特法则，但却违背了高内聚的设计思想。高内聚要求相近的功能要放到同一个类中，这样可以方便功能修改的时候，修改的地方不至于过于分散。实际上，通过引入两个接口就能轻松解决这个问题。
>
> ```java
> public interface Serializable {String serialize(Object object);}public interface Deserializable {Object deserialize(String text);}public class Serialization implements Serializable, Deserializable {@Overridepublic String serialize(Object object) { String serializedResult = ...; ... return serializedResult;}@Overridepublic Object deserialize(String str) { Object deserializedResult = ...; ... return deserializedResult;}}public class DemoClass_1 {private Serializable serializer;public Demo(Serializable serializer) { this.serializer = serializer;}//...}public class DemoClass_2 {private Deserializable deserializer;public Demo(Deserializable deserializer) { this.deserializer = deserializer;}//...}
> ```
>
> 尽管还是要往 DemoClass_1 的构造函数中，传入包含序列化和反序列化的 Serialization 实现类，但是，Serializable 接口只包含序列化操作，DemoClass_1 无法使用 Serialization 类中的反序列化接口，对反序列化操作无感知，这也就符合了迪米特法则后半部分所说的“依赖有限接口”的要求。
