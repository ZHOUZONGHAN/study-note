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