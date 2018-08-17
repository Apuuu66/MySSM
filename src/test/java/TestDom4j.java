import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * Created by Kevin on 2018/8/16.
 */
public class TestDom4j {
    public static void parserNode(Element ele) {

        System.out.println(ele.getName() + ":" + ele.getText().trim());
        //从Users根节点开始遍历，像【属性=值】的形式存为一个Attribute对象存储在List集合中
        List<Attribute> attrList = ele.attributes();
        for (Attribute attr : attrList) {
            //每循环一次，解析此节点的一个【属性=值】，没有则输出空
            String name = attr.getName();
            String value = attr.getValue();
            System.out.println(name + "=" + value);
        }

        List<Element> eleList = ele.elements();
        //递归遍历父节点下的所有子节点
        for (Element e : eleList) {
            parserNode(e);
        }
    }

    public static void main(String[] args) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        String currentPath = TestDom4j.class.getResource("/").getPath();
        try {
            document = saxReader.read(new File(currentPath+"ElementNode.xml"));
            Element ele = document.getRootElement();
            parserNode(ele);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
