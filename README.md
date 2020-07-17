*其它语言版本: [English](README.md),[简体中文](README.zh-cn.md).*

### TinyPngPlugin
`TinyPngPlugin`是一个[TinyPng](https://tinypng.com/)的Gradle插件，它能够批量地压缩你项目中的图片

### 获得Tiny的API Key
在使用该插件前， 你需要先获得一个Tiny的API Key。 首先跳转到[Tiny Developers Page](https://tinypng.com/developers)，然后输入你的姓名和邮箱来获得这个Key

*注意: 一个Key每个月可以免费压缩500张图片，超过500张后就需要付费后才能继续使用*

### 使用教程
首先在根目录中的`build.gradle`文件中添加`TinyPngPlugin`的依赖：

 	dependencies {
    	classpath 'com.lemon.compress:tinypng-plugin:1.0.3'
	}

然后在app目录中的`build.gradle`文件中应用该插件，并配置`tinyinfo`：

 	apply plugin: 'tinypng'

 	tinyInfo {
    	resourceDir = [
			// 你的资源目录
            "app/src/main/res",
            "lib/src/main/res"
    	]
        resourcePattern = [
        	// 你的资源文件夹
        	"drawable[a-z-]*",
            "mipmap[a-z-]*"
        ]
        whiteList = [
        	// 在这里添加文件白名单，支持正则表达式
        ]
        apiKey = 'your tiny API key'
    }

使用`Android Studio`的同学，可以在`tinypng`目录中找到相关的构建任务。或者也可以直接在终端中运行`./gradlew tinyPng`命令来执行任务

`TinyPngPlugin`会将压缩结果保存到`compressed-resource.json`这个文件中，下次再运行任务时会自动跳过那些已经被压缩过的文件

NOTE: 相比原项目主要修复了 compressed-resource.json 中的路径问题，解决了被重复压缩问题。

### 致谢
[TinyPngPlugin](https://github.com/waynell/TinyPngPlugin)