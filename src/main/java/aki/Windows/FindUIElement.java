package aki.Windows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;

public interface FindUIElement {

    static WinUIElementRef findElementByText(WinUIElementRef rootElement, String Text){
        WinUIElementRef resElementRef = null;
        Stack<WinUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            WinUIElementRef ele = allNode.pop();
            if(ele.get_Name().equals(Text)){
                resElementRef = ele;
                break;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements()) {
                    allNode.push(childEle);
                }
            }
        }
        return resElementRef;
    }

    BiFunction<WinUIElementRef, String, WinUIElementRef> findElementByText = FindUIElement::findElementByText;

    static List<WinUIElementRef> findElementsByText(WinUIElementRef rootElement, String Text){
        List<WinUIElementRef> resElementRefList = new ArrayList<>();
        Stack<WinUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            WinUIElementRef ele = allNode.pop();
            if(ele.get_Name().equals(Text)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements()) {
                    allNode.push(childEle);
                }
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }

    BiFunction<WinUIElementRef, String, List<WinUIElementRef>> findElementsByText = FindUIElement::findElementsByText;


    static WinUIElementRef findElementByRole(WinUIElementRef rootElement, String role){
        WinUIElementRef resElementRef=null;
        Stack<WinUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            WinUIElementRef ele = allNode.pop();
            if(ele.get_Role().equals(role)){
                resElementRef=ele;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements()) {
                    allNode.push(childEle);
                }
            }
        }
        return resElementRef;
    }
    BiFunction<WinUIElementRef, String, WinUIElementRef> findElementByRole = FindUIElement::findElementByRole;

    static List<WinUIElementRef> findElementsByRole(WinUIElementRef rootElement, String role){
        List<WinUIElementRef> resElementRefList = new ArrayList<>();
        Stack<WinUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            WinUIElementRef ele = allNode.pop();
            if(ele.get_Role().equals(role)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements()) {
                    allNode.push(childEle);
                }
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }
    BiFunction<WinUIElementRef, String, List<WinUIElementRef>> findElementsByRole = FindUIElement::findElementsByRole;

    static WinUIElementRef findElementByPartialText(WinUIElementRef rootElement, String text){
        WinUIElementRef childElementRef = null;
        Stack<WinUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            WinUIElementRef ele = allNode.pop();
            if(ele.get_Name().contains(text)){
                childElementRef = ele;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return childElementRef;
    }

    BiFunction<WinUIElementRef, String, WinUIElementRef> findElementByPartialText = FindUIElement::findElementByPartialText;

    static List<WinUIElementRef> findElementsByPartialText(WinUIElementRef rootElement, String text){
        List<WinUIElementRef> childElementRefList = new ArrayList<>();
        Stack<WinUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
           WinUIElementRef ele = allNode.pop();
            if(ele.get_Name().contains(text)){
                childElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return childElementRefList;
    }

    BiFunction<WinUIElementRef, String, List<WinUIElementRef>> findElementsByPartialText = FindUIElement::findElementsByPartialText;


    static WinUIElementRef findElementByClassName(WinUIElementRef rootElement, String className){
        WinUIElementRef resultElementRef = null;
        Stack<WinUIElementRef> allNode;
        allNode = new Stack<>();
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            WinUIElementRef ele = allNode.pop();
            String eleClassName = ele.get_ClassName();
            if(eleClassName.equals(className)){
                resultElementRef = ele;
                break;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return resultElementRef;
    }

    BiFunction<WinUIElementRef, String, WinUIElementRef> findElementByClassName = FindUIElement::findElementByClassName;

    static List<WinUIElementRef> findElementsByClassName(WinUIElementRef rootElement, String className){
        List<WinUIElementRef> resElementRefList = new ArrayList<>();
        Stack<WinUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            WinUIElementRef ele = allNode.pop();
            if(ele.get_ClassName().equals(className)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements()) {
                    allNode.push(childEle);
                }
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }
    BiFunction<WinUIElementRef, String, List<WinUIElementRef>> findElementsByClassName = FindUIElement::findElementsByClassName;

    static WinUIElementRef findElementByAutomationId(WinUIElementRef rootElement, String automationId){
        WinUIElementRef resultElementRef = null;
        Stack<WinUIElementRef> allNode;
        allNode = new Stack<>();
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            WinUIElementRef ele = allNode.pop();
            String eleAutomationId = ele.get_AutomationId();
            if(eleAutomationId.equals(automationId)){
                resultElementRef = ele;
                break;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return resultElementRef;
    }

    BiFunction<WinUIElementRef, String, WinUIElementRef> findElementByAutomationId = FindUIElement::findElementByAutomationId;

    static List<WinUIElementRef> findElementsByAutomationId(WinUIElementRef rootElement, String automationId){
        List<WinUIElementRef> resElementRefList = new ArrayList<>();
        Stack<WinUIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            WinUIElementRef ele = allNode.pop();
            if(ele.get_AutomationId().equals(automationId)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements()) {
                    allNode.push(childEle);
                }
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }
    BiFunction<WinUIElementRef, String, List<WinUIElementRef>> findElementsByAutomationId = FindUIElement::findElementsByAutomationId;


    static WinUIElementRef findElementByFullDescription(WinUIElementRef rootElement, String automationId){
        WinUIElementRef resultElementRef = null;
        Stack<WinUIElementRef> allNode;
        allNode = new Stack<>();
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            WinUIElementRef ele = allNode.pop();
            String fullDescription = ele.get_FullDescription();
            if(fullDescription.equals(automationId)){
                resultElementRef = ele;
                break;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (WinUIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return resultElementRef;
    }

    BiFunction<WinUIElementRef, String, WinUIElementRef> findElementByFullDescription = FindUIElement::findElementByFullDescription;

}
