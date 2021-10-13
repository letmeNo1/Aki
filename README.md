Aki - Automated Testing on desktop
=================================

Background
==========

This is a desktop automated testing framework based on accessibility api. At the same time, with the help of the open source framework of JNA, the purpose of calling the Mac and Windows system-level API is achieved.

Maven dependency
===============
```
    <key>Java</key>
    <dict>
        <key>StartOnMainThread</key>
        <true/>
    </dict>
```

Generate jar package
===============
mvn install

Getting started
===============
**For Mac**

Requires a system running OS X and Xcode installed. 
Requires system version 10.6 and above
Requires jdk version >= 1.8.0_295

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


 ***For Windows：***  `UIElementRef app = Operation.initializeAppRefForWin("Your app launch path");`


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

    dd












