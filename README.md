# MyWeChat README

## 变量和文件命名

目前用到的变量主要在strings.xml和id.xml。

其中stringsxml中的变量的命名方式为"变量所在Activity/Fragment"+"_"+"变量的特征"，例如"MainActivity_Title""DiscoverFragment_Title"。

而id.xml中变量的命名方式为"变量所在Activity/Fragment"+"_"+"变量的特征"，例如"ChatFragment_TextView"等。

java文件中Fragment和Activity的命名均采用"特征"+"Fragment/Activity"的方式，drawable中xml文件的命名均采用"ic_"+"特征"+"颜色"+"大小"的方式进行命名。



## 代码结构

目前com.example.mywechat下有两个文件夹和2个java文件，两个文件夹分别为data文件夹和ui文件夹。

其中两个java文件分别为MainActivity和ContactActivity，对应MyWeChat的主界面和MyWeChat中“你”的朋友的界面。ContactActivity可以在任何需要调用的位置进行调用。ContactActivity中封装了1个Friend类用来存储朋友的信息。该类声明在了data文件夹中。

在ui文件夹中共有5个子文件夹，分别为chats、contacts、discover、login和me，其中chats、contacts、discover和me四个文件夹对应了MainActivity中的四个Fragment，而login文件夹则为登录界面的文件夹，除此之外，目前data中除了Friend类以外的文件也均为登陆界面所需的文件。



## 注意

目前登陆界面可以随意登录，但是必须在密码中输入大于5的字符串，因此接下来开发时，可以修改manifest中的intent-filter的位置以避免浪费时间。