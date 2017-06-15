# Walle <img alt="Walle is an android library" src="https://www.cleveroad.com/public/comercial/label-android.svg" height="19"> [![minSdk for Walle](https://img.shields.io/badge/minSdk-14-green.svg)](#)

## This is Walle - Recyclerview.Adapter's helper


## Setup and usage
### installation

with gradle
```GRADLE
compile 'com.github.donniesky:Walle:0.1.0'
```

Assuming you have installed jitpack provider:
```GRADLE
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

or just import `Walle` module to your project.

## Features
Proxy Pattern.

* No need change anything with your target adapter;
* Not destory target adapter position;
* Support dynamic add & remove;
* No dependencies code build order

### Usage

```JAVA
Walle wrapper = Walle.newBuilder()
                .wrapperAdapter(adapter)
                .autoLoadMore(false)
                .headerViewRes(R.layout.view_header)
                .emptyViewRes(R.layout.view_empty)
                .footerViewRes(R.layout.view_footer)
                .loadMoreViewRes(R.layout.view_loadmore)
                .build();
```

### Thanks
* baseAdapter [hongyangAndroid/baseAdapter](https://github.com/hongyangAndroid/baseAdapter)
* BaseRecyclerViewAdapterHelper [CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
* 更优雅的方式添加 Header 与 Footer [优雅的添加Header和Footer等](http://www.woaitqs.cc/android/2017/04/11/new-way-to-add-header-and-footer)
