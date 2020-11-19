今天更改了android.jar，[如何更改看这里](https://blog.csdn.net/qq_26413249/article/details/109749465)，之后提示了如下错误:

```
Could not resolve all files for configuration ':mdm:androidApis'.
Failed to transform android.jar to match attributes {artifactType=android-mockable-jar, org.gradle.libraryelements=jar, org.gradle.usage=java-runtime, returnDefaultValues=false}.
Execution failed for MockableJarTransform: D:\Program\Android\Sdk\platforms\android-30\android.jar.
```
来看看原因，提示MockableJarTransform错误，处理肯定在`com.android.tools.build:gradle`里，下载对应的版本就可以了，[在这里搜索下载](https://maven.aliyun.com/mvn/search)。

下载gradle的source(源码)和pom(源码的编译需要引入的库)。
![下载source和pom](https://img-blog.csdnimg.cn/20201119174958731.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI2NDEzMjQ5,size_16,color_FFFFFF,t_70#pic_center)
解压source，搜索MockableJarTransform。

```
grep -rn MockableJarTransform *
```
![第一个就是了](https://img-blog.csdnimg.cn/2020111917534281.png#pic_center)
代码比较简单，没几行，gradle的报错肯定就是因为它了，这么少的代码，自己创建java项目测试一下，可能的话修改下android.jar
![简单的代码](https://img-blog.csdnimg.cn/20201119175437967.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI2NDEzMjQ5,size_16,color_FFFFFF,t_70#pic_center)

去pom文件中查找MockableJarGenerator可能引入的库，
![可能引入的库](https://img-blog.csdnimg.cn/20201119175708554.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI2NDEzMjQ5,size_16,color_FFFFFF,t_70#pic_center)

同样办法下载,下载source就够了:
![下载source](https://img-blog.csdnimg.cn/20201119175820607.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI2NDEzMjQ5,size_16,color_FFFFFF,t_70#pic_center)
找到MockableJarGenerator.java,前面导包就是路径。
把文件复制出来，之后要用，创建java项目把文件复制进去，有一堆报错:
![报错](https://img-blog.csdnimg.cn/2020111918015890.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI2NDEzMjQ5,size_16,color_FFFFFF,t_70#pic_center)
去pom文件中找到对应的引用，在idea中添加上。

![添加依赖](https://img-blog.csdnimg.cn/20201119180628807.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI2NDEzMjQ5,size_16,color_FFFFFF,t_70#pic_center)
仿照MockableJarTransform代码创建个测试类:

![测试类](https://img-blog.csdnimg.cn/20201119180906169.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI2NDEzMjQ5,size_16,color_FFFFFF,t_70#pic_center)
运行下就发现报错了，这是肯定的，不报错as也不会编译不过。
![编译报错](https://img-blog.csdnimg.cn/2020111918110281.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI2NDEzMjQ5,size_16,color_FFFFFF,t_70#pic_center)
重点来了，try一下就可以了，把使用不了class文件排除重新生成jar，Android Studio就可以识别了。
![重新生成jar](https://img-blog.csdnimg.cn/20201119181453123.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI2NDEzMjQ5,size_16,color_FFFFFF,t_70#pic_center)
[测试项目地址](https://github.com/Reginer/MockableJarGenerator)