package util.config.parse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import util.config.Bean;
import util.config.BeanProperty;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 2018/8/17.
 */
public class ConfigManager {

    private volatile static Map<String, Bean> map = null;

    private ConfigManager() {

    }

    public static Map<String, Bean> getConfig(String path) {
        if (map == null) {
            synchronized (ConfigManager.class) {
                if (map == null) {
                    map = getConfig(path, true);
                }
            }
        }
        return map;
    }


    // 读取配置文件→获得读取结果
    private static Map<String, Bean> getConfig(String path, boolean flag) {
        map = new HashMap<>();
        // 1.创建解析器
        SAXReader reader = new SAXReader();
        // 2.加载配置→document对象
        String currentPath = ConfigManager.class.getResource("/").getPath();
        // System.out.println(currentPath);
        Document doc = null;
        try {
            doc = reader.read(new File(currentPath + path));
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("请检查xml配置是否正确！！！");
        }
        // 3.定义xpath表达式，取出所有bean元素
        String xpath = "//bean";
        // 4.对bean元素进行遍历
        List<Element> list = doc.selectNodes(xpath);
        if (list != null) {
            // 将所有bean元素的name和class封装到bean对象
            for (Element element : list) {
                Bean bean = new Bean();
                String id = element.attributeValue("id");
                String className = element.attributeValue("class");
                bean.setId(id);
                bean.setClazz(className);
                // 将bean元素下的property子元素的name，value，ref封装到property对象
                List<Element> children = element.elements("property");
                if (children != null) {
                    for (Element child : children) {
                        BeanProperty prop = new BeanProperty();
                        String name = child.attributeValue("name");
                        String value = child.attributeValue("value");
                        String ref = child.attributeValue("ref");
                        prop.setName(name);
                        prop.setValue(value);
                        prop.setRef(ref);
                        // 将property封装到bean
                        bean.getProperties().add(prop);
                        // 将bean封装到map
                        map.put(id, bean);
                    }
                }
            }
        }
        return map;
    }
}
