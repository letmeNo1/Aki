package aki.Electon;


import aki.Mac.UIElementRef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public interface FindUIElement {
    static aki.Mac.UIElementRef findElementByIdentifier(aki.Mac.UIElementRef rootElement, String identifier){
        aki.Mac.UIElementRef resultElementRef = null;
        Stack<aki.Mac.UIElementRef> allNode;
        allNode = new Stack<>();
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            aki.Mac.UIElementRef ele = allNode.pop();
            if(ele.get_AXIdentifier().equals(identifier)){
                resultElementRef = ele;
                break;
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (aki.Mac.UIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return resultElementRef;
    }

    BiFunction<aki.Mac.UIElementRef, String, aki.Mac.UIElementRef> findElementByIdentifier = FindUIElement::findElementByIdentifier;

    static List<aki.Mac.UIElementRef> findElementsByRole(aki.Mac.UIElementRef rootElement, String role){
        List<aki.Mac.UIElementRef> resElementRefList = new ArrayList<>();
        Stack<aki.Mac.UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            aki.Mac.UIElementRef ele = allNode.pop();
            if(ele.get_Role().equals(role)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (aki.Mac.UIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }

    BiFunction<aki.Mac.UIElementRef, String, List<aki.Mac.UIElementRef>> findElementsByRole = FindUIElement::findElementsByRole;

    static List<aki.Mac.UIElementRef> findElementsBySubRole(aki.Mac.UIElementRef rootElement, String role){
        List<aki.Mac.UIElementRef> resElementRefList = new ArrayList<>();
        Stack<aki.Mac.UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            aki.Mac.UIElementRef ele = allNode.pop();
            if(ele.get_SubRole().equals(role)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (aki.Mac.UIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }

    BiFunction<aki.Mac.UIElementRef, String, List<aki.Mac.UIElementRef>> findElementsBySubRole = FindUIElement::findElementsBySubRole;

    static List<aki.Mac.UIElementRef> findElementsByText(aki.Mac.UIElementRef rootElement, String text){
       List<aki.Mac.UIElementRef> resElementRefList = new ArrayList<>();
       Stack<aki.Mac.UIElementRef> allNode= new Stack<>() ;
       allNode.push(rootElement);
       while (!allNode.isEmpty()){
           aki.Mac.UIElementRef ele = allNode.pop();
           if(ele.get_Value().equals(text)||ele.get_Title().equals(text)||ele.get_Help().equals(text)){
               resElementRefList.add(ele);
           }
           if (!ele.get_ChildrenElements().isEmpty()) {
               for (aki.Mac.UIElementRef childEle : ele.get_ChildrenElements())
                   allNode.push(childEle);
           }
       }
       Collections.reverse(resElementRefList);
       return resElementRefList;
    }

    BiFunction<aki.Mac.UIElementRef, String, List<aki.Mac.UIElementRef>> findElementsByText = FindUIElement::findElementsByText;


    static List<aki.Mac.UIElementRef> findElementsByPartialText(aki.Mac.UIElementRef rootElement, String text){
        List<aki.Mac.UIElementRef> childElementRefList = new ArrayList<>();
        Stack<aki.Mac.UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            aki.Mac.UIElementRef ele = allNode.pop();
            if(ele.get_Value().contains(text)||ele.get_Title().contains(text)||ele.get_Help().contains(text)){
                childElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (aki.Mac.UIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        return childElementRefList;
    }

    BiFunction<aki.Mac.UIElementRef, String, List<aki.Mac.UIElementRef>> findElementsByPartialText = FindUIElement::findElementsByPartialText;


    static aki.Mac.UIElementRef findElementByXpath(aki.Mac.UIElementRef element, String xpath){
        String[] nodes = xpath.split("/");
        aki.Mac.UIElementRef currentElement =element;
        for(String node:nodes){
            if(node.equals("**")){
                   /*
                    Get index =0 to locate the main window;
                    AXApplication -> [AXWindow,AXMenu]
                     */
                currentElement =element.get_ChildrenElements().get(0);
                while (true){
                    assert currentElement != null;
                    if(currentElement.get_ChildrenElements().size()>1){
                        break;
                    }else {
                        currentElement = currentElement.get_ChildrenElements().get(0);
                    }
                }
            }else{
                assert currentElement != null;
                if(node.contains("[")){
                    int index = Integer.parseInt(node.replaceAll("\\D", ""));
                    String role = node.split("\\[")[0];
                    currentElement = currentElement.get_ChildrenElements().stream().filter(e -> e.get_Role().equals(role)).collect(Collectors.toList()).get(index-1);
                }
                else{
                    currentElement = currentElement.get_ChildrenElements().stream().filter(e -> e.get_Role().equals(node)).findFirst().orElse(null);
                }
            }
        }
        return currentElement;
    }

    BiFunction<UIElementRef, String, UIElementRef> findElementByXpath = FindUIElement::findElementByXpath;
}
