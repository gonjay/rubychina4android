## 开源地址
  https://github.com/gonjay/rubychina4android

## 下载

  [Google Play下载](https://play.google.com/store/apps/details?id=org.rubychina.app)

  [开发版下载 1.0.5](https://github.com/gonjay/rubychina4android/blob/master/app/build/apk/app-debug-unaligned.apk?raw=true)
  ![](https://raw.github.com/gonjay/rubychina4android/master/shots/unsigned-apk.png)

  [发布版下载 1.0.5](https://github.com/gonjay/rubychina4android/blob/master/app/build/apk/app-debug-unaligned.apk?raw=true)
  ![](https://raw.github.com/gonjay/rubychina4android/master/shots/signed-apk.png)

## 特性：

 * 安卓原生的侧滑抽屉
 * 下拉刷新
 * 主页帖子列表下滑到头后自动获取新的内容
 * 浏览帖子详情并支持Markdown，左右侧滑分别展示帖子内容、回复列表、回复编辑（预览）
 * 登录、退出功能

## 为什么要开发安卓版本

 * 热爱Ruby-China社区
 * 我希望用自己掌握的技术在安卓上面提供优秀的体验
 * 探索HTML5与native的结合
 * 一套更快更好的与Rails结合的移动端开发技术

## 预览

![](http://rubychina.qiniudn.com/Screenshot_2014-02-12-12-33-06.png?imageView2/1/w/300/h/550)

![](http://rubychina.qiniudn.com/media-20140204%20(1).png?imageView2/1/w/300/h/550) ![](http://rubychina.qiniudn.com/device-2014-02-10-232412.png?imageView2/1/w/300/h/550)

![](http://rubychina.qiniudn.com/media-20140204.png?imageView2/1/w/300/h/550) ![](http://rubychina.qiniudn.com/device-2014-02-07-135606.png?imageView2/1/w/300/h/550)

![](http://rubychina.qiniudn.com/media-20140204%20(2).png?imageView2/1/w/300/h/550) ![](http://rubychina.qiniudn.com/media-20140204%20(3).png?imageView2/1/w/300/h/550)

## Tips

 练习之作，所以客户端还不是很完善。关于如何让iOS支持Markdown，我觉得完全可以使用一个UIWebView来实现，可以参考这个[index.html](https://github.com/gonjay/rubychina4android/blob/master/app/src/main/assets/index.html)
 然后这么调用
```
NSString body_html = Topic.body_html;
[self.webView stringByEvaluatingJavaScriptFromString:(@\"$('#display').html(%@);\", body_html)];
```
 当然也欢迎会Android的朋友贡献代码咯，不过你得学会使用AndroidStudio，从Eclipse过来的童鞋可能得花点时间适应一下，不过确实是很有效率的开发工具

## 开发

  fork和like是最能证明这个开源APP的价值了，所以对[repo](https://github.com/gonjay/rubychina4android)fork或者是like吧，越多我越有动力把这个app完善好!当然也期待你的contribute