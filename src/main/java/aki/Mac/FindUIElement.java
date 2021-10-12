package aki.Mac;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public interface FindUIElement {
    static UIElementRef findElementByIdentifier(UIElementRef rootElement, String identifier){
        UIElementRef resultElementRef = null;
        Stack<UIElementRef> allNode;
        allNode = new Stack<>();
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            if(ele.get_AXIdentifier().equals(identifier)){
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

    BiFunction<UIElementRef, String, UIElementRef> findElementByIdentifier = FindUIElement::findElementByIdentifier;

    static List<UIElementRef> findElementsByRole(UIElementRef rootElement, String role){
        List<UIElementRef> resElementRefList = new ArrayList<>();
        Stack<UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            if(ele.get_Role().equals(role)){
                resElementRefList.add(ele);
            }
            if (!ele.get_ChildrenElements().isEmpty()) {
                for (UIElementRef childEle : ele.get_ChildrenElements())
                    allNode.push(childEle);
            }
        }
        Collections.reverse(resElementRefList);
        return resElementRefList;
    }

    BiFunction<UIElementRef, String, List<UIElementRef>> findElementsByRole = FindUIElement::findElementsByRole;

    static List<UIElementRef> findElementsByText(UIElementRef rootElement, String text){
       List<UIElementRef> resElementRefList = new ArrayList<>();
       Stack<UIElementRef> allNode= new Stack<>() ;
       allNode.push(rootElement);
       while (!allNode.isEmpty()){
           UIElementRef ele = allNode.pop();
           if(ele.get_Value().equals(text)||ele.get_Title().equals(text)||ele.get_Help().equals(text)){
               resElementRefList.add(ele);
           }
           if (!ele.get_ChildrenElements().isEmpty()) {
               for (UIElementRef childEle : ele.get_ChildrenElements())
                   allNode.push(childEle);
           }
       }
       Collections.reverse(resElementRefList);
       return resElementRefList;
    }

    BiFunction<UIElementRef, String, List<UIElementRef>> findElementsByText = FindUIElement::findElementsByText;


    static List<UIElementRef> findElementsByPartialText(UIElementRef rootElement, String text){
        List<UIElementRef> childElementRefList = new ArrayList<>();
        Stack<UIElementRef> allNode= new Stack<>() ;
        allNode.push(rootElement);
        while (!allNode.isEmpty()){
            UIElementRef ele = allNode.pop();
            if(ele.get_Value().contains(text)||ele.get_Title().contains(text)||ele.get_Help().contains(text)){
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


    static UIElementRef findElementByXpath(UIElementRef element, String xpath){
        String[] nodes = xpath.split("/");
        UIElementRef currentElement =element;
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
