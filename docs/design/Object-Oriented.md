> - 面向对象编程是一种编程范式或编程风格。它以类或对象作为组织代码的基本单元，并将封装、抽象、继承、多态四个特性，作为代码设计和实现的基石 。
> - 面向对象编程语言是支持类或对象的语法机制，并有现成的语法机制，能方便地实现面向对象编程四大特性（封装、抽象、继承、多态）的编程语言。

# 封装（Encapsulation）

> 封装也叫作信息隐藏或者数据访问保护。类通过暴露有限的访问接口，授权外部仅能通过类提供的方式（或者叫函数）来访问内部信息或者数据。
>
> 如果对类中属性的访问不做限制，那任何代码都可以访问、修改类中的属性，虽然这样看起来更加灵活，但从另一方面来说，过度灵活也意味着不可控，属性可以随意被以各种奇葩的方式修改，而且修改逻辑可能散落在代码中的各个角落，势必影响代码的可读性、可维护性。
>
> 除此之外，类仅仅通过有限的方法暴露必要的操作，也能提高类的易用性。如果把类属性都暴露给类的调用者，调用者想要正确地操作这些属性，就势必要对业务细节有足够的了解。而这对于调用者来说也是一种负担。相反，如果将属性封装起来，暴露少许的几个必要的方法给调用者使用，调用者就不需要了解太多背后的业务细节，用错的概率就减少很多。
>
> 模拟一个钱包Wallet类，对于钱包余额 balance 这个属性，从业务的角度来说，只能增或者减，不会被重新设置。所以，在 Wallet 类中，只暴露了 increaseBalance() 和 decreaseBalance() 方法，并没有暴露 set 方法。对于上次钱包余额变更的时间 balanceLastModifiedTime 这个属性，它完全是跟 balance 这个属性的修改操作绑定在一起的。只有在 balance 修改的时候，这个属性才会被修改。所以，把 balanceLastModifiedTime 这个属性的修改操作完全封装在了 increaseBalance() 和 decreaseBalance() 两个方法中，不对外暴露任何修改这个属性的方法和业务细节。这样也可以保证 balance 和 balanceLastModifiedTime 两个数据的一致性。

# 抽象（Abstraction）

> 抽象讲的是如何隐藏方法的具体实现，让调用者只需要关心方法提供了哪些功能，并不需要知道这些功能是如何实现的。
>
> 在面向对象编程中，常借助编程语言提供的接口类（比如 Java 中的 interface 关键字语法）或者抽象类（比如 Java 中的 abstract 关键字语法）这两种语法机制，来实现抽象这一特性。
>
> 并不是说一定要为实现类（PictureStorage）抽象出接口类（IPictureStorage），才叫作抽象。类的方法是通过编程语言中的“函数”这一语法机制来实现的。通过函数包裹具体的实现逻辑，这本身就是一种抽象。调用者在使用函数的时候，并不需要去研究函数内部的实现逻辑，只需要通过函数的命名、注释或者文档，了解其提供了什么功能，就可以直接使用了。
>
> 抽象这个概念是一个非常通用的设计思想，并不单单用在面向对象编程中，也可以用来指导架构设计等。而且这个特性也并不需要编程语言提供特殊的语法机制来支持，只需要提供“函数”这一非常基础的语法机制，就可以实现抽象特性、所以，它没有很强的“特异性”，有时候并不被看作面向对象编程的特性之一。
>
> 如果上升一个思考层面的话，抽象及其前面讲到的封装都是人类处理复杂性的有效手段。在面对复杂系统的时候，人脑能承受的信息复杂程度是有限的，所以必须忽略掉一些非关键性的实现细节。而抽象作为一种只关注功能点不关注实现的设计思路，正好帮大脑过滤掉许多非必要的信息。
>
> 除此之外，抽象作为一个非常宽泛的设计思想，在代码设计中，起到非常重要的指导作用。很多设计原则都体现了抽象这种设计思想，比如**基于接口而非实现编程、开闭原则（对扩展开放、对修改关闭）、代码解耦（降低代码的耦合性）**等。
>
> 换一个角度来考虑，在定义（或者叫命名）类的方法的时候，也要有抽象思维，不要在方法定义中，暴露太多的实现细节，以保证在某个时间点需要改变方法的实现逻辑的时候，不用去修改其定义。举个简单例子，比如 getAliyunPictureUrl() 就不是一个具有抽象思维的命名，因为某一天如果不再把图片存储在阿里云上，而是存储在私有云上，那这个命名也要随之被修改。相反，如果定义一个比较抽象的函数，比如叫作 getPictureUrl()，那即便内部存储方式修改了，也不需要修改命名。

# 继承（Inheritance）

> 继承是用来表示类之间的 is-a 关系，比如猫是一种哺乳动物。从继承关系上来讲，继承可以分为两种模式，单继承和多继承。单继承表示一个子类只继承一个父类，多继承表示一个子类可以继承多个父类，比如猫既是哺乳动物，又是爬行动物。
>
> 为了实现继承这个特性，编程语言需要提供特殊的语法机制来支持，比如 Java 使用 extends 关键字来实现继承，C++ 使用冒号（class B : public A），Python 使用 parentheses ()，Ruby 使用 <。不过，有些编程语言只支持单继承，不支持多重继承**（多继承会出现菱形问题）**，比如 Java、PHP、C#、Ruby 等，而有些编程语言既支持单重继承，也支持多重继承，比如 C++、Python、Perl 等。
>
> 继承最大的一个好处就是代码复用。假如两个类有一些相同的属性和方法，就可以将这些相同的部分，抽取到父类中，让两个子类继承父类。这样，两个子类就可以重用父类中的代码，避免代码重复写多遍。不过，这一点也并不是继承所独有的，也可以通过其他方式来解决这个代码复用的问题，比如利用组合关系而不是继承关系。
>
> 继承的概念很好理解，也很容易使用。不过，**过度使用继承，继承层次过深过复杂，就会导致代码可读性、可维护性变差**。为了了解一个类的功能，不仅需要查看这个类的代码，还需要按照继承关系一层一层地往上查看“父类、父类的父类……”的代码。还有，子类和父类高度耦合，修改父类的代码，会直接影响到子类。

# 多态（Polymorphism）

> 多态是指，子类可以替换父类，在实际的代码运行过程中，调用子类的方法实现。
>
> 多态这种特性需要编程语言提供特殊的语法机制来实现：
>
> - 编程语言要支持父类对象可以引用子类对象
> - 编程语言要支持继承
> - 编程语言要支持子类可以重写（override）父类中的方法
>
> 对于多态特性的实现方式，除了利用“继承加方法重写”这种实现方式之外，还有其他两种比较常见的的实现方式，一个是利用接口类语法，另一个是利用 duck-typing 语法（鸭子类型：对于对象的使用，不是由对象继承类或者实现的接口特性来决定的，而是由对象定义的属性和方法来决定的）。不过，并不是每种编程语言都支持接口类或者 duck-typing 这两种语法机制，比如 C++ 就不支持接口类语法，而 duck-typing 只有一些动态语言才支持，比如 Python、JavaScript 等。
>
> 多态也是很多设计模式、设计原则、编程技巧的代码实现基础，比如策略模式、基于接口而非实现编程、依赖倒置原则、里式替换原则、利用多态去掉冗长的 if-else 语句等等。

# 违反面向对象编程的案例

## 滥用 getter、setter 方法

> 定义完类的属性之后，用 IDE 或者 Lombok 插件把这些属性的 getter、setter 方法都定义上。
>
> 它违反了面向对象编程的封装特性，相当于将面向对象编程风格退化成了面向过程编程风格。如果属性是一个容器，getter方法返回的是一个集合容器。外部调用者在拿到这个容器之后，是可以操作容器内部数据的，也就是说，外部代码还是能修改 items 中的数据。容易导致类中的属性数据不一致。
>
> 我们可以通过 Java 提供的 Collections.unmodifiableList() 类似的方法，让 getter 方法返回一个不可被修改的 UnmodifiableList 集合容器，一旦调用这些修改数据的方法，代码就会抛出 UnsupportedOperationException 异常，这样就避免了容器中的数据被修改。不过还是可以修改容器中对象的值。（使用原型模式解决）

## 滥用全局变量和全局方法

> 将常量全部放入一个Constants类中，将方法与数据分离，破坏了封装特性，是典型的面向过程风格。
>
> 首先，这样的设计会影响代码的可维护性。如果参与开发同一个项目的工程师有很多，在开发过程中，可能都要涉及修改这个类，比如往这个类里添加常量，那这个类就会变得越来越大，成百上千行都有可能，查找修改某个常量也会变得比较费时，而且还会增加提交代码冲突的概率。
>
> 其次，这样的设计还会增加代码的编译时间。当 Constants 类中包含很多常量定义的时候，依赖这个类的代码就会很多。那每次修改 Constants 类，都会导致依赖它的类文件重新编译，因此会浪费很多不必要的编译时间。
>
> 最后，这样的设计还会影响代码的复用性。如果要在另一个项目中，复用本项目开发的某个类，而这个类又依赖 Constants 类。即便这个类只依赖 Constants 类中的一小部分常量，仍然需要把整个 Constants 类也一并引入，也就引入了很多无关的常量到新的项目中。
>
> 推荐将 Constants 类拆解为功能更加单一的多个类，比如跟 MySQL 配置相关的常量，放到 MysqlConstants 类中；跟 Redis 配置相关的常量，放到 RedisConstants 类中。
>
> 还有就是并不单独地设计 Constants 常量类，而是哪个类用到了某个常量，就把这个常量定义到这个类中。比如，RedisConfig 类用到了 Redis 配置相关的常量，就直接将这些常量定义在 RedisConfig 中，这样也提高了类设计的内聚性和代码的复用性。

## 定义数据和方法分离的类

> MVC 结构分为 Controller 层、Service 层、Repository 层。Controller 层负责暴露接口给前端调用，Service 层负责核心业务逻辑，Repository 层负责数据读写。而在每一层中，我们又会定义相应的 VO（View Object）、BO（Business Object）、Entity。一般情况下，VO、BO、Entity 中只会定义数据，不会定义方法，所有操作这些数据的业务逻辑都定义在对应的 Controller 类、Service 类、Repository 类中。**数据定义在一个类中，方法定义在另一个类中。**
>
> 这种开发模式叫作基于贫血模型的开发模式，也是现在非常常用的一种 Web 项目的开发模式。
>
> 在生活中，去完成一个任务，一般都会思考，应该先做什么、后做什么，如何一步一步地顺序执行一系列操作，最后完成整个任务。面向过程编程风格恰恰符合人的这种流程化思维方式。而面向对象编程风格正好相反。它是一种自底向上的思考方式。它不是先去按照执行流程来分解任务，而是将任务翻译成一个一个的小的模块（也就是类），设计类之间的交互，最后按照流程将类组装起来，完成整个任务。这样的思考路径比较适合复杂程序的开发，但并不是特别符合人类的思考习惯。

# 接口VS抽象类

> **抽象类特性：**
>
> - 抽象类不允许被实例化，只能被继承。也就是说，你不能 new 一个抽象类的对象出来（Logger logger = new Logger(...); 会报编译错误）。
> - 抽象类可以包含属性和方法。方法既可以包含代码实现（比如 Logger 中的 log() 方法），也可以不包含代码实现（比如 Logger 中的 doLog() 方法）。不包含代码实现的方法叫作抽象方法。
> - 子类继承抽象类，必须实现抽象类中的所有抽象方法。对应到例子代码中就是，所有继承 Logger 抽象类的子类，都必须重写 doLog() 方法。
>
> **接口特性：**
>
> - 接口不能包含属性（也就是成员变量）。
> - 接口只能声明方法，方法不能包含代码实现。
> - 类实现接口的时候，必须实现接口中声明的所有方法。
>
> **区别：**
>
> - 抽象类中可以定义属性、方法的实现，而接口中不能定义属性，方法也不能包含代码实现等等。
> - 抽象类实际上就是类，只不过是一种特殊的类，这种类不能被实例化为对象，只能被子类继承。
> - **继承关系是一种 is-a 的关系，那抽象类既然属于类，也表示一种 is-a 的关系。相对于抽象类的 is-a 关系来说，接口表示一种 has-a 关系，表示具有某些功能。对于接口，有一个更加形象的叫法，那就是协议（contract）。**
>
> **从类的继承层次上来看，抽象类是一种自下而上的设计思路，先有子类的代码重复，然后再抽象成上层的父类（也就是抽象类）。而接口正好相反，它是一种自上而下的设计思路。我们在编程的时候，一般都是先设计接口，再去考虑具体的实现。**
