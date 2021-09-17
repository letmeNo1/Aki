package org.aki.Windows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;

public interface FindUIElement {

    static List<UIElementRef> findElementsByText(UIElementRef rootElement, String Text){
        List<UIElementRef> resElementRefList = new ArrayList<>();
        Stack<UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            if(ele.get_Name().equals(Text)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (UIElementRef childEle : ele.get_ChildrenElements()) {
                    allNode.push(childEle);
                }
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }

    BiFunction<UIElementRef, String, List<UIElementRef>> findElementsByText = FindUIElement::findElementsByText;

    static List<UIElementRef> findElementsByRole(UIElementRef rootElement, String role){
        List<UIElementRef> resElementRefList = new ArrayList<>();
        Stack<UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            if(ele.get_Name().equals(role)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (UIElementRef childEle : ele.get_ChildrenElements()) {
                    allNode.push(childEle);
                }
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }
    BiFunction<UIElementRef, String, List<UIElementRef>> findElementsByRole = FindUIElement::findElementsByText;

    static List<UIElementRef> findElementsByPartialText(UIElementRef rootElement, String text){
        List<UIElementRef> childElementRefList = new ArrayList<>();
        Stack<UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
           UIElementRef ele = allNode.pop();
            if(ele.get_Name().contains(text)){
                childElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (UIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return childElementRefList;
    }

    BiFunction<UIElementRef, String, List<UIElementRef>> findElementsByPartialText = FindUIElement::findElementsByPartialText;


    static UIElementRef findElementByAutomationId(UIElementRef rootElement, String automationId){
        UIElementRef resultElementRef = null;
        Stack<UIElementRef> allNode;
        allNode = new Stack<>();
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            String eleAutomationId = ele.get_AutomationId();
            if(eleAutomationId.equals(automationId)){
                resultElementRef = ele;
                break;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (UIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return resultElementRef;
    }

    BiFunction<UIElementRef, String, UIElementRef> findElementByAutomationId = FindUIElement::findElementByAutomationId;

    static UIElementRef findElementByFullDescription(UIElementRef rootElement, String automationId){
        UIElementRef resultElementRef = null;
        Stack<UIElementRef> allNode;
        allNode = new Stack<>();
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            String eleAutomationId = ele.get_FullDescriptionPropertyId();
            if(eleAutomationId.equals(automationId)){
                resultElementRef = ele;
                break;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (UIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return resultElementRef;
    }

    BiFunction<UIElementRef, String, UIElementRef> findElementByFullDescription = FindUIElement::findElementByFullDescription;


}
