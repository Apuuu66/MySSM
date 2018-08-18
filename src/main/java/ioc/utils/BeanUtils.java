package ioc.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Created by Kevin on 2018/8/18.
 */
public class BeanUtils {
    public static Method getWriteMethod(Object beanObj, String name) {
        Method method = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            if (pds != null) {
                for (PropertyDescriptor pd : pds) {
                    String pdName = pd.getName();
                    if (pdName.equals(name)) {
                        method = pd.getWriteMethod();
                        break;
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        if (method == null) {
            throw new RuntimeException("请检查" + name + "属性的set方法");
        }
        return method;
    }
}
