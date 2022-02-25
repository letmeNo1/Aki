Aki - Automated Testing on desktop
=================================
-   [English](#Background)
-   [中文版](#背景)

Background
==========

This is a desktop automated testing framework based on accessibility api. At the same time, with the help of the open source framework of JNA, the purpose of calling the Mac and Windows system-level API is achieved.

Maven dependency
===============
```
<dependency>
    <groupId>io.github.letmeno1</groupId>
    <artifactId>aki</artifactId>
    <version>1.0.7</version>
</dependency>
```

Generate jar package
===============
mvn install

Getting started
===============
Requires jdk version >= 1.8.0_295

**For Mac**

Requires a system running OS X and Xcode installed. 
Requires system version 10.6 and above


Systemwide accessibility must be enabled. Check the checkbox: System Preferences > Security and privacy
> Universal Access > Enable access for assistive devices. 

**For Windows**

Download opencv_java451.dll and move it to your jdk path（C:\Program Files\Java\jdk1.8.0_202\bin）

Requires system version >= Windows 7

Applicaion element locate tool
===============
**For Mac**

Accessibility Inspector：Xcode -> Open Developer Tools

Using `Accessibility Inspector` can provide a quick way to find these attributes.

**For Windows**

download the exe file `inspect.exe` from here

Using `inspect.exe` can provide a quick way to find these attributes.


Usage
==========

### launch app and initialize an UIElementRef by identifier(Mac) or file execute path(Windows)
 
 ***For Mac：*** `UIElementRef app = Operation.initializeAppRefForMac("com.apple.calculator");`


 ***For Windows：***  `UIElementRef app = Operation.initializeAppRefForWin("C:\\WINDOWS\\System32\\calc.exe");`

App window itself is an UIElementRef object, And every elements are an UIElementRef object yet.
You can call the find or click method through UIElementRef

Mouse event

UIElementRef object support click, double click, long click, hover

e.g.  `app.findElementsByText("Input phone number",0).click()` or `app.findElementsByText("Input phone number",0).doubleClick()`

Input event 

UIElementRef object support type and clear

e.g. `app.findElementsByText("Input phone number",0).type("188888")` or `app.findElementsByText("Input phone number",0).clear()`


### find elements

 ***For Mac：*** 
  
     support text, role(which is element type), identifier, xpath.

     For text and roles, the searched elements will be returned in the form of a list, and the index needs to be notified when calling.

     e.g. `app.findElementsByText("Input phone number",0)` & `app.findElementsByRole("AXTextField",2)`

     1.By text

      The text corresponds to the "Title", "Value" and "Help" in the element attribute

     2.By Role

     The role corresponds to the "Role"
     
     3.By Xpath

     This is a way to find through the path of the element
 
     e.g. `app.findElementByXpath("AXStandardWindow/AXButton[1]")`
    
     4.By identifier 

      The identifier corresponds to the "identifier"

     e.g. `app.findElementByIdentifier("JoinButton")`
     
 ***For Windows：***
 
     support text(name), role, automationId, fullDescription.
     
     For text, role and fullDescription, the searched elements will be returned in the form of a list, and the index needs to be notified when calling.

     e.g. `app.findElementsByText("Input phone number",0)` & `app.findElementsByRole("AXTextField",2)`
     
      1.By text

      The text corresponds to the "Name" in the element attribute

     2.By Role

     The role corresponds to the "Role"
     
     e.g. `app.findElementsByRole(app.findElementsByText("client",0))
    
     4.By automationId 

      The automationId corresponds to the "automationId"

     e.g. `app.findElementByAutomationId("num3button")`
     
 ***For Common：***
      1.By image
      
      Use images for element positioning
      
      e.g. `app.findElementLocationByImage(imageFolderPath + "001.png")`

    
### Operation

 Support Mouse events, Combination keyboard events, take screenshot and kill app



背景
==========

Aki是一个基于Accessibility Api实现的跨平台(Mac/Windows）桌面端自动化测试框架，借助开源框架JNA实现了对系统底层Api的访问。

Maven 依赖
===============
```
<dependency>
    <groupId>io.github.letmeno1</groupId>
    <artifactId>aki</artifactId>
    <version>1.0.3</version>
</dependency>
```

生成Jar包
===============
mvn install

入门
===============
需要 jdk version >= 1.8.0_295

**对于 Mac**

需要安装Xcode
需要系统版本高于10.6

需要在在隐私权限中开启Xcode对辅助设备访问的权限。系统偏好设置 > 安全性与隐私 > 通用访问 > 启用辅助设备访问。

**对于 Windows**

下载opencv_java451.dll，并放在你jdk安装目录下（C:\Program Files\Java\jdk1.8.0_202\bin）

需要版本高于Windows 7

元素定位工具
===============
**对于 Mac**

Accessibility Inspector：Xcode -> 打开 Developer Tools

使用“Accessibility Inspector”可以查找到App对应的元素属性

**对于 Windows**

在主页里下载 inspect.exe

使用“inspect.exe”可以查找到App对应的元素属性


使用
==========

### 启动应用程序并通过identifier（Mac）或文件执行路径（Windows）初始化一个 UIElementRef对象
 
 ***对于 Mac：*** `UIElementRef app = Operation.initializeAppRefForMac("com.apple.calculator");`


 ***对于 Windows：***  `UIElementRef app = Operation.initializeAppRefForWin("C:\\WINDOWS\\System32\\calc.exe");`

App 窗口本身就是一个 UIElementRef 对象，而每个元素也都是一个 UIElementRef 对象。你可以通过UIElementRef来调用各种查找或者是点击的方法

鼠标事件

UIElementRef 对象支持单击、双击、长按、悬停

例如:  `app.findElementsByText("Input phone number",0).click()` 或 `app.findElementsByText("Input phone number",0).doubleClick()`

输入事件

UIElementRef 对象支持输入和清除

例如:  `app.findElementsByText("Input phone number",0).type("188888")` 或 `app.findElementsByText("Input phone number",0).clear()`


### 查找元素

 ***对于 Mac：*** 
  
     支持文本、role（元素类型）、identifier、xpath。
     
     对于文本和role，搜索到的元素会以列表的形式返回，调用时需要加入索引。

     例如 `app.findElementsByText("Input phone number",0)` 和 `app.findElementsByRole("AXTextField",2)`

     1.通过文本

     文本对应元素属性中的“Title”、“Value”和“Help”

     2.通过Role

     Role对应元素属性中的“Role”
     
     3.通过Xpath

     这是一种通过元素的路径查找的方法(待改进)
 
     例如 `app.findElementByXpath("AXStandardWindow/AXButton[1]")`
    
     4.通过identifier

     identifier对应元素属性中的“identifier”

     例如 `app.findElementByIdentifier("JoinButton")`
     
 ***对于 Windows：***
 
     支持 text(name)、role、automationId、fullDescription。
     
     对于text、role和fullDescription，搜索到的元素会以列表的形式返回，调用时需要加入索引。

     例如`app.findElementsByText("Input phone number",0)` 和 `app.findElementsByRole("AXTextField",2)`
     
     1.通过文本

      文本对应于元素属性中的“Name”

     2.通过Role

      Role对应于元素属性中的“Role”
     
      例如 `app.findElementsByRole(app.findElementsByText("client",0))
    
     4.通过automationId 

      automationId对应于元素属性中的"automationId"

      例如 `app.findElementByAutomationId ("num3button")`
      
  ***通用定位方式：***
      1.图像定位
      
      e.g. `app.findElementLocationByImage(imageFolderPath + "001.png")`
    
### 通用操作

 支持鼠标事件、组合键盘事件、截图和结束进程



    












