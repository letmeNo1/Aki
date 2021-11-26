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
    <version>1.0.3</version>
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

Systemwide accessibility must be enabled. Check the checkbox: System Preferences > Universal Access > Enable access for assistive devices. 

**For Windows**

Requires system version >= Windows 7

Application element locate tool
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

Mouse event

UIElementRef object support click, double click, long click, hover

e.g.  `app.findElementsByText("Input phone number",0).click()` or `app.findElementsByText("Input phone number",0).doubleClick()`

Input event 

UIElementRef object support type and clear

e.g. `app.findElementsByText("Input phone number",0).type("188888")` or `app.findElementsByText("Input phone number",0).clear()`


### find elements

 ***For Mac：*** 
  
     support text, role(witch is element type), identifier, xpath.

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

      The identifier corresponds to the "identifier"

     e.g. `app.findElementByIdentifier("num3button")`
    
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

需要在在隐私权限中开启Xcode对辅助设备访问的权限。系统偏好设置 > 通用访问 > 启用辅助设备访问。

**对于 Windows**

Requires system version >= Windows 7

Application element locate tool
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

Mouse event

UIElementRef object support click, double click, long click, hover

e.g.  `app.findElementsByText("Input phone number",0).click()` or `app.findElementsByText("Input phone number",0).doubleClick()`

Input event 

UIElementRef object support type and clear

e.g. `app.findElementsByText("Input phone number",0).type("188888")` or `app.findElementsByText("Input phone number",0).clear()`


### find elements

 ***For Mac：*** 
  
     support text, role(witch is element type), identifier, xpath.

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

      The identifier corresponds to the "identifier"

     e.g. `app.findElementByIdentifier("num3button")`
    
### Operation

 Support Mouse events, Combination keyboard events, take screenshot and kill app



    












