package ioc.factory;

import ioc.config.Bean;
import ioc.config.BeanProperty;
import ioc.config.parse.ConfigManager;
import ioc.utils.MyBeanUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 2018/8/17.
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    // 配置信息
    private Map<String, Bean> config;
    // bean容器
    private Map<String, Object> context = new HashMap<>();

    public ClassPathXmlApplicationContext(String path) {
        // 1.读取配置文件获取初始化的bean信息
        config = ConfigManager.getConfig(path);
        // 2.遍历配置，初始化bean
        if (config != null) {
            for (Map.Entry<String, Bean> beanEntry : config.entrySet()) {
                String beanId = beanEntry.getKey();
                Bean bean = beanEntry.getValue();
                Object exitBean = context.get(beanId);
                if (exitBean == null && bean.getScope().equals("singleton")) {
                    // 装载bean
                    Object beanObj = creatBean(bean);
                    context.put(beanId, beanObj);
                }
            }
        }
    }

    private Object creatBean(Bean bean) {
        String className = bean.getClazz();
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("请检查您的Class配置是否正确:" + className);
        }
        Object beanObj = null;
        try {
            // Object o = clazz.getConstructor().newInstance();
            beanObj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("目前仅支持空参构造，请检查你的bean" + className);
        }
        //****** 注入属性********
        List<BeanProperty> properties = bean.getProperties();
        if (properties != null) {
            for (BeanProperty prop : properties) {

                String name = prop.getName();
                String value = prop.getValue();
                String ref = prop.getRef();
                if (value != null) {
                    HashMap<String, String[]> paramMap = new HashMap<>();
                    paramMap.put(name, new String[]{value});
                    try {
                        // 使用BeanUtils工具进行属性注入(基本类型)
                        BeanUtils.populate(beanObj, paramMap);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

                if (ref != null) {
                    Method setMethod = MyBeanUtils.getWriteMethod(beanObj, name);
                    Object exitBean = context.get(ref);
                    if (exitBean == null) {
                        exitBean = creatBean(config.get(ref));
                        if (config.get(ref).getScope().equals("singleton")) {
                            context.put(ref, exitBean);
                        }
                    }

                    try {
                        setMethod.invoke(beanObj, exitBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("您的bean属性" + name + "没有对应的set方法" + className);
                    }
                }



            /*    String parm = null;
                //获取注入方法
                Method setMethod = MyBeanUtils.getWriteMethod(beanObj, name);
                String ref = prop.getRef();

                if ((parm = prop.getValue()) != null) {
                    try {
                        setMethod.invoke(beanObj, parm);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        throw new RuntimeException("您的bean属性" + name + "没有对应的set方法" + className);
                    }
                } else if (ref != null) {
                    Object exitBean = context.get(ref);
                    if (exitBean == null) {
                        exitBean = creatBean(config.get(ref));
                        if (config.get(ref).getScope().equals("singleton")) {
                            context.put(ref, exitBean);
                        }
                    }

                    try {
                        setMethod.invoke(beanObj, exitBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("您的bean属性" + name + "没有对应的set方法" + className);
                    }

                }
*/
            }
        }
        return beanObj;
    }

    @Override
    public Object getBean(String beanId) {
        Object bean = context.get(beanId);
        if (bean == null) {
            bean = creatBean(config.get(beanId));
        }
        return bean;
    }
}
