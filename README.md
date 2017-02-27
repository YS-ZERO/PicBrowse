# PicBrowse

模仿"美食杰app"中的一个View做的，代码有些乱，我会慢慢整理的。。。

<img src="/example.gif" width="256" height="512"/>

---

在你的 `build.gradle`:

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

```
dependencies {
    compile 'com.github.yszero:YSUtils:v1.0.1'
}
```

设置View必须要用到的方法：
- setTitles()   测试标题
- setOnPicPageOptionListener() 设置监听事件
- addImageSrcBit() 设置图片
