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
