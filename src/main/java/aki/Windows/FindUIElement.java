package aki.Windows;

import aki.OpenCV.CallOpenCV;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface FindUIElement {

    static UIElementRef findElementByText(UIElementRef rootElement, String Text){
        UIElementRef resElementRef = null;
        Stack<UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            if(ele.get_Name().equals(Text)){
                resElementRef = ele;
                break;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (UIElementRef childEle : ele.get_ChildrenElements()) {
                    allNode.push(childEle);
                }
            }
        }
        return resElementRef;
    }

    BiFunction<UIElementRef, String, UIElementRef> findElementByText = FindUIElement::findElementByText;

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


    static UIElementRef findElementByRole(UIElementRef rootElement, String role){
        UIElementRef resElementRef=null;
        Stack<UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            if(ele.get_Name().equals(role)){
                resElementRef=ele;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (UIElementRef childEle : ele.get_ChildrenElements()) {
                    allNode.push(childEle);
                }
            }
        }
        return resElementRef;
    }
    BiFunction<UIElementRef, String, UIElementRef> findElementByRole = FindUIElement::findElementByRole;

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
    BiFunction<UIElementRef, String, List<UIElementRef>> findElementsByRole = FindUIElement::findElementsByRole;

    static UIElementRef findElementByPartialText(UIElementRef rootElement, String text){
        UIElementRef childElementRef = null;
        Stack<UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            if(ele.get_Name().contains(text)){
                childElementRef = ele;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (UIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return childElementRef;
    }

    BiFunction<UIElementRef, String, UIElementRef> findElementByPartialText = FindUIElement::findElementByPartialText;

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

    static List<UIElementRef> findElementsByAutomationId(UIElementRef rootElement, String automationId){
        List<UIElementRef> resElementRefList = new ArrayList<>();
        Stack<UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            if(ele.get_Name().equals(automationId)){
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
    BiFunction<UIElementRef, String, List<UIElementRef>> findElementsByAutomationId = FindUIElement::findElementsByAutomationId;


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
