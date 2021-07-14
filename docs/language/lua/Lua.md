# 注释

- 单行注释 -- 单行注释
- 多行注释 --[[ 多行注释 --]]

# 标示符

以一个字母 A 到 Z 或 a 到 z 或下划线 **_** 开头后加上 0 个或多个字母，下划线，数字（0 到 9）。

**最好不要使用下划线加大写字母的标示符，因为Lua的保留字也是这样的。**

# 数据类型

**Lua 是动态类型语言，变量不要类型定义,只需要为变量赋值。 值可以存储在变量中，作为参数传递或结果返回。**

Lua 中有 8 个基本类型分别为：nil、boolean、number、string、userdata、function、thread 和 table。

## nil（空）

> 这个最简单，只有值nil属于该类，表示一个无效值**（在条件表达式中相当于false）**
>
> 对于全局变量和 table，nil 还有一个"删除"作用，给全局变量或者 table 表里的变量赋一个 nil 值，等同于把它们删掉
>
> nil 作比较时应该加上双引号`"nil"`
>
> ``type(X)==nil`结果为`false`的原因是`type(X)`实质是返回的`"nil"`字符串，是一个string类型

## boolean（布尔）

> boolean 类型只有两个可选值：`true`（真）和`false`（假），**Lua 把 false 和 nil 看作是 false，其他的都为 true，数字 0 也是 true**

## number（数字）

> Lua 默认只有一种 number 类型 -- double（双精度）类型（默认类型可以修改 luaconf.h 里的定义）

## string（字符串）

> 字符串由一对双引号或单引号来表示
>
> 也可以用 2 个方括号 "[[]]" 来表示字符块
>
> 在对一个数字字符串上进行算术操作时，Lua 会尝试将这个数字字符串转成一个数字
>
> 字符串连接使用的是`..`
>
> 使用`#`来计算字符串的长度，放在字符串前面

## table（表）

> Lua 中的表（table）其实是一个"关联数组"（associative arrays），数组的索引可以是数字、字符串或表类型
>
> 在 Lua 里，table 的创建是通过"构造表达式"来完成，最简单构造表达式是{}，用来创建一个空表
>
> 不同于其他语言的数组把 0 作为数组的初始索引，**在 Lua 里表的默认初始索引一般以 1 开始**
>
> table 不会固定长度大小，有新数据添加时 table 长度会自动增长，没初始的 table 都是 nil

## function（函数）

> 在 Lua 中，函数是被看作是"第一类值（First-Class Value）"，函数可以存在变量里
>
> function 可以以匿名函数（anonymous function）的方式通过参数传递

## thread（线程）

> 在 Lua 里，最主要的线程是协同程序（coroutine）。它跟线程（thread）差不多，拥有自己独立的栈、局部变量和指令指针，可以跟其他协同程序共享全局变量和其他大部分东西
>
> 线程跟协程的区别：线程可以同时多个运行，而协程任意时刻只能运行一个，并且处于运行状态的协程只有被挂起（suspend）时才会暂停

## userdata（自定义类型）

> userdata 是一种用户自定义数据，用于表示一种由应用程序或 C/C++ 语言库所创建的类型，可以将任意 C/C++ 的任意数据类型的数据（通常是 struct 和 指针）存储到 Lua 变量中调用

# 变量

> Lua 变量有三种类型：全局变量、局部变量、表中的域
>
> Lua 中的变量全是全局变量，那怕是语句块或是函数里，除非用 local 显式声明为局部变量
>
> 局部变量的作用域为从声明位置开始到所在语句块结束
>
> 变量的默认值均为 nil

## 赋值

> Lua 可以对多个变量同时赋值，变量列表和值列表的各个元素用逗号分开，赋值语句右边的值会依次赋给左边的变量
>
> ```lua
> a, b = 10, 2 -- a=10; b=2
> a, b = f() -- f()第一个返回值赋值给a，第二个返回值赋值给b
> ```
>
> 遇到赋值语句Lua会先计算右边所有的值然后再执行赋值操作，所以我们可以这样进行交换变量的值
>
> ```lua
> x, y = y, x -- swap 'x' for 'y'
> ```
>
> 当变量个数和值的个数不一致时，变量少按变量个数补足nil，变量多多余的值会被忽略，如果要对多个变量赋值必须依次对每个变量赋值
>
> ```lua
> a, b, c = 0
> print(a,b,c) -- 0   nil   nil
> ```
>
> Lua 对多个变量同时赋值，不会进行变量传递，仅做值传递
>
> ```lua
> a, b = 0, 1
> a, b = a+1, a+1
> print(a,b) -- 1   1
> 
> a, b = 0, 1
> a, b = b+1, b+1
> print(a,b) -- 2   2
> 
> a, b = 0, 1
> a = a+1
> b = a+1
> print(a,b) -- 1   2
> ```

# 循环

## while

> ```lua
> while(condition)
> do
>    -- Do Something
> end
> ```

## for

> ```lua
> for var=exp1,exp2,exp3 do  
>     -- Do Something 
> end 
> ```
>
> var 从 exp1 变化到 exp2，每次变化以 exp3 为步长递增 var，并执行一次 "执行体"。exp3 是可选的，如果不指定，默认为1
>
> for的三个表达式在循环开始前一次性求值，以后不再进行求值
>
> ```lua
> a = {"one", "two", "three"}
> for i, v in ipairs(a) do
>     print(i, v)
> end 
> ```
>
> i是数组索引值，v是对应索引的数组元素值。ipairs是Lua提供的一个迭代器函数，用来迭代数组

## repeat...until

> ```lua
> repeat
>    statements
> until( condition )
> ```
>
> for 和 while 循环的条件语句在当前循环执行开始时判断，而 repeat...until 循环的条件语句在当前循环结束后判断，所以在条件进行判断前循环体都会执行一次
>
> 如果条件判断语句（condition）为 false，循环会重新开始执行，直到条件判断语句（condition）为 true 才会停止执行

## 循环控制语句

### break

> 退出当前循环或语句，并开始脚本执行紧接着的语句
>
> 如果使用循环嵌套，break语句将停止最内层循环的执行，并开始执行的外层的循环语句

### goto

> goto 语句允许将控制流程无条件地转到被标记的语句处，可以实现 continue 的功能
>
> ```lua
> local a = 1
> ::label:: print("--- goto label ---")
> 
> a = a+1
> if a < 3 then
>     goto label -- a 小于 3 的时候跳转到标签 label
> end
> ```

# 流程控制

> **Lua认为false和nil为假，true和非nil为真(0 为 true)**

## if

> ```lua
> if(condition)
> then
>    -- Do Something 
> end
> ```

## if...else

> ```lua
> if(condition)
> then
>    --Do Something 
> else
>    --Do Something 
> end
> ```

## if...elseif...else

> ```lua
> if(condition1)
> then
>    --Do Something 
> elseif(condition2)
> then
>    --Do Something 
> elseif(condition3)
> then
>    --Do Something 
> else 
>    --Do Something 
> end
> ```

# 函数

## 函数定义

> ```lua
> optional_function_scope function function_name( argument1, argument2, argument3..., argumentn)
>     function_body
>     return result_params_comma_separated
> end
> ```
>
> - **optional_function_scope:** 该参数是可选的制定函数是全局函数还是局部函数，未设置该参数默认为全局函数，如果你需要设置函数为局部函数需要使用关键字`local`
> - **function_name:** 指定函数名称
> - **argument1, argument2, argument3..., argumentn:** 函数参数，多个参数以逗号隔开，函数也可以不带参数
> - **function_body:** 函数体，函数中需要执行的代码语句块
> - **result_params_comma_separated:** 函数返回值，Lua语言函数可以返回多个值，每个值以逗号隔开

## 多返回值

> Lua函数可以返回多个结果值，如果不存则返回nil
>
> 在return后列出要返回的值的列表即可返回多值

## 可变参数

> Lua 函数可以接受可变数目的参数，和 C 语言类似，在函数参数列表中使用三点 **...** 表示函数有可变的参数
>
> ```lua
> function add(...)  
> local s = 0  
>   for i, v in ipairs{...} do -- {...} 表示一个由所有变长参数构成的数组  
>     s = s + v  
>   end  
>   return s  
> end  
> print(add(3,4,5,6,7)) -- 25
> ```
>
> 有时候可能需要几个固定参数加上可变参数，固定参数必须放在变长参数之前
>
> ```lua
> function fwrite(fmt, ...) -- 固定的参数fmt
>     return io.write(string.format(fmt, ...))    
> end
> ```
>
> 通常在遍历变长参数的时候只需要使用`{…}`，然而变长参数可能会包含一些`nil`，那么就可以用`select`函数来访问变长参数了`select('#', …)`或者`select(n, …)`
>
> ```lua
> function f(...)
>     a = select(3,...) -- 2
>     print (a)
>     print (select(3,...)) -- 2 3 4 5
> end
> 
> f(0,1,2,3,4,5)
> ```

# 运算符

## 算术运算符

| 操作符 | 描述 | 实例               |
| :----- | :--- | :----------------- |
| +      | 加法 | A + B 输出结果 30  |
| -      | 减法 | A - B 输出结果 -10 |
| *      | 乘法 | A * B 输出结果 200 |
| /      | 除法 | B / A 输出结果 2   |
| %      | 取余 | B % A 输出结果 0   |
| ^      | 乘幂 | A^2 输出结果 100   |
| -      | 负号 | -A 输出结果 -10    |

## 关系运算符

| 操作符 | 描述                                                         | 实例                  |
| :----- | :----------------------------------------------------------- | :-------------------- |
| ==     | 等于，检测两个值是否相等，相等返回 true，否则返回 false      | (A == B) 为 false。   |
| ~=     | 不等于，检测两个值是否相等，不相等返回 true，否则返回 false  | (A ~= B) 为 true。    |
| >      | 大于，如果左边的值大于右边的值，返回 true，否则返回 false    | (A > B) 为 false。    |
| <      | 小于，如果左边的值大于右边的值，返回 false，否则返回 true    | (A < B) 为 true。     |
| >=     | 大于等于，如果左边的值大于等于右边的值，返回 true，否则返回 false | (A >= B) 返回 false。 |
| <=     | 小于等于， 如果左边的值小于等于右边的值，返回 true，否则返回 false | (A <= B) 返回 true。  |

## 逻辑运算符

| 操作符 | 描述                                                         | 实例                   |
| :----- | :----------------------------------------------------------- | :--------------------- |
| and    | 逻辑与操作符。 若 A 为 false，则返回 A，否则返回 B。         | (A and B) 为 false。   |
| or     | 逻辑或操作符。 若 A 为 true，则返回 A，否则返回 B。          | (A or B) 为 true。     |
| not    | 逻辑非操作符。与逻辑运算结果相反，如果条件为 true，逻辑非为 false。 | not(A and B) 为 true。 |

## 其他运算符

| 操作符 | 描述                               | 实例                                                         |
| :----- | :--------------------------------- | :----------------------------------------------------------- |
| ..     | 连接两个字符串                     | a..b ，其中 a 为 "Hello " ， b 为 "World", 输出结果为 "Hello World"。 |
| #      | 一元运算符，返回字符串或表的长度。 | #"Hello" 返回 5                                              |

## 运算符优先级

> 从高到低的顺序：
>
> ```lua
> ^
> not    - (unary)
> *      /       %
> +      -
> ..
> <      >      <=     >=     ~=     ==
> and
> or
> ```

# 字符串

> Lua 语言中字符串可以使用以下三种方式来表示：
>
> - 单引号间的一串字符
> - 双引号间的一串字符
> - `[[`与`]]`间的一串字符
>
> ```lua
> string1 = "Lua"
> print("\"字符串 1 是\"",string1) -- "字符串 1 是"Lua
> 
> string2 = 'runoob.com'
> print("字符串 2 是",string2) -- 字符串 2 是runoob.com
> 
> string3 = [["Lua 教程"]]
> print("字符串 3 是",string3) -- 字符串 3 是"Lua 教程"
> ```

## 字符串操作

| 方法                                                    | 用途                                                         |
| ------------------------------------------------------- | ------------------------------------------------------------ |
| string.upper(argument)                                  | 字符串全部转为大写字母                                       |
| string.lower(argument)                                  | 字符串全部转为小写字母                                       |
| string.sub(s, i [, j])                                  | s：要截取的字符串<br />i：截取开始位置<br />j：截取结束位置，默认为 -1，最后一个字符 |
| string.gsub(mainString, findString, replaceString, num) | 在字符串中替换，`mainString`为要操作的字符串，`findString`为被替换的字符，`replaceString`要替换的字符，`num`替换次数（可以忽略，则全部替换） |
| string.find (str, substr, [init, [end]])                | 在一个指定的目标字符串中搜索指定的内容(第三个参数为索引)，返回其具体位置。不存在则返回`nil` |
| string.reverse(arg)                                     | 字符串反转                                                   |
| string.format(...)                                      | 返回一个类似`printf`的格式化字符串                           |
| string.char(arg)                                        | char 将整型数字转成字符并连接                                |
| string.byte(arg[,int])                                  | byte 转换字符为整数值(可以指定某个字符，默认第一个字符)      |
| string.len(arg)                                         | 计算字符串长度                                               |
| string.rep(string, n)                                   | 返回字符串string的n个拷贝                                    |
| string.gmatch(str, pattern)                             | 回一个迭代器函数，每一次调用这个函数，返回一个在字符串`str`找到的下一个符合`pattern`描述的子串。如果参数`pattern`描述的字符串没有找到，迭代函数返回`nil` |
| string.match(str, pattern, init)                        | 只寻找源字串str中的第一个配对<br />参数init可选，指定搜寻过程的起点，默认为1<br/>在成功配对时，函数将返回配对表达式中的所有捕获结果；如果没有设置捕获标记，则返回整个配对字符串，当没有成功的配对时，返回nil |

# 迭代器

> 迭代器（iterator）是一种对象，它能够用来遍历标准模板库容器中的部分或全部元素，每个迭代器对象代表容器中的确定的地址
>
> 泛型 for 在自己内部保存迭代函数，实际上它保存三个值：**迭代函数、状态常量、控制变量**
>
> ```lua
> for k, v in pairs(t) do
>  print(k, v)
> end
> ```
>
> 泛型 for 的执行过程：
>
> 1. 初始化，计算 in 后面表达式的值，表达式应该返回泛型 for 需要的三个值：迭代函数、状态常量、控制变量；与多值赋值一样，如果表达式返回的结果个数不足三个会自动用 nil 补足，多出部分会被忽略
> 2. 将状态常量和控制变量作为参数调用迭代函数（对于 for 结构来说，状态常量没有用处，仅仅在初始化时获取他的值并传递给迭代函数）
> 3. 将迭代函数返回的值赋给变量列表
> 4. 如果返回的第一个值为nil循环结束，否则执行循环体
> 5. 回到第二步再次调用迭代函数

## 无状态的迭代器

> 无状态的迭代器是指不保留任何状态的迭代器，因此在循环中我们可以利用无状态迭代器避免创建闭包花费额外的代价
>
> 每一次迭代，迭代函数都是用两个变量（状态常量和控制变量）的值作为参数被调用，一个无状态的迭代器只利用这两个值可以获取下一个元素

## 多状态的迭代器

> 很多情况下，迭代器需要保存多个状态信息而不是简单的状态常量和控制变量，最简单的方法是使用闭包，还有一种方法就是将所有的状态信息封装到 table 内，将 table 作为迭代器的状态常量，因为这种情况下可以将所有的信息存放在 table 内，所以迭代函数通常不需要第二个参数

# Table(表)

> table 是 Lua 的一种数据结构用来帮助我们创建不同的数据类型，如：数组、字典等
>
> Lua table 使用关联型数组，你可以用任意类型的值来作数组的索引，但这个值不能是 nil
>
> Lua table 是不固定大小的，你可以根据自己需要进行扩容
>
> Lua也是通过table来解决模块（module）、包（package）和对象（Object）的。 例如string.format表示使用"format"来索引table string

## Table的构造

> 构造器是创建和初始化表的表达式。表是Lua特有的功能强大的东西。最简单的构造函数是{}，用来创建一个空表
>
> ```lua
> -- 初始化表
> mytable = {}
> 
> -- 指定值
> mytable[1]= "Lua"
> 
> -- 移除引用
> mytable = nil
> -- lua 垃圾回收会释放内存
> ```
>
> 当我们为 table a 并设置元素，然后将 a 赋值给 b，则 a 与 b 都指向同一个内存。如果 a 设置为 nil ，则 b 同样能访问 table 的元素。如果没有指定的变量指向a，Lua的垃圾回收机制会清理相对应的内存

## Table 操作

| 方法                                           | 用途                                                         |
| ---------------------------------------------- | ------------------------------------------------------------ |
| table.concat (table [, sep [, start [, end]]]) | table.concat()函数列出参数中指定table的数组部分从start位置到end位置的所有元素，元素间以指定的分隔符(sep)隔开 |
| table.insert (table, [pos,] value)             | 在table的数组部分指定位置(pos)插入值为value的一个元素。pos参数可选，默认为数组部分末尾 |
| table.maxn (table)                             | 指定table中所有正数key值中最大的key值。如果不存在key值为正数的元素, 则返回0。**(Lua5.2之后该方法已经不存在了)** |
| table.remove (table [, pos])                   | 返回table数组部分位于pos位置的元素。其后的元素会被前移。pos参数可选，默认为table长度，**即从最后一个元素删起** |
| table.sort (table [, comp])                    | 对给定的table进行升序排序                                    |

**注意：**

当我们获取 table 的长度的时候无论是使用`#`还是`table.getn`其都会在索引中断的地方停止计数，而导致无法正确取得 table 的长度。

*可以使用以下方法来代替：*

```lua
function table_leng(t)
  local leng=0
  for k, v in pairs(t) do
    leng=leng+1
  end
  return leng;
end
```

