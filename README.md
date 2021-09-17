Aki - Automated Testing on Mac
=================================

Background
==========

This is a desktop automated testing framework based on accessibility api. At the same time, with the help of the open source framework of JNA, the purpose of calling the mac system-level API is achieved.

Generate jar package
===============
mvn install

Getting started
===============
Requires a system running OS X and Xcode installed. 
Requires system version 10.6 and above
Requires jdk version >= 1.8.0_295

Systemwide accessibility must be enabled. Check the checkbox: System Preferences > Universal Access > Enable access for assistive devices. 

Application element locate tool
===============

Accessibility Inspector：Xcode -> Open Developer Tools

Using Accessibility Inspector can provide a quick way to find these attributes.

Usage
==========
### launch app and initialize an appRef by identifier
 
 `AXUIElementRef app = CallNSWorkspace.initializeAppRef("com.ringcentral.ringcentralformac");`

### find elements

 support text, role(witch is element type).

 For text and roles, the searched elements will be returned in the form of a list, and the index needs to be notified when calling.

    e.g. `app.findElementsByText("Input phone number",0)` & `app.findElementsByRole("AXTextField",2)`

1.Text locate

 The text corresponds to the "Title", "Value" and "Help" in the element attribute

2.Role locate

 The text corresponds to the "Role"

### find element
 support text, role(witch is element type), xpath, identifier.

 for xpath is usually the complete and unique path
 
    e.g. `app.findElementByXpath("AXStandardWindow/AXButton[1]")`

 for identifier is usually the complete and unique path

    e.g. `app.findElementByIdentifier("JoinButton")`








