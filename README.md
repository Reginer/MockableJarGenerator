解决为了使用隐藏sdk api, Android Studio 编译报错问题

参考教程
https://blog.csdn.net/qq_26413249/article/details/109819624

需要使用不同 gradle版本的 MockableJarGenerator.java 源码,
源码也在步骤2的同级路径中可以找到

### gradle 4.2.2
1. 用idea打开此项目,编译出class
2. 用MacZip打开 /Users/wujieyuan/.gradle/caches/modules-2/files-2.1/com.android.tools.build/builder/4.2.2/b5b7f34278bb89e8256741e4af463dc9b2fbc409/builder-4.2.2.jar
3. 用编译出的MockableJarGenerator.class 替换包 com.android.builder.testing中的class
4. 重启AS

### gradle 7.3.1
1. 用idea打开此项目,编译出class
2. 用MacZip打开 /Users/wujieyuan/.gradle/caches/modules-2/files-2.1/com.android.tools.build/builder/7.3.1/3e23401ece1637cecbae6be5c02cc18ab7e452e5/builder-7.3.1.jar
3. 用编译出的MockableJarGenerator.class 替换包 com.android.builder.testing中的class
4. 重启AS
