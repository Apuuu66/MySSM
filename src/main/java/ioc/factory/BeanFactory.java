package ioc.factory;

/**
 * Created by Kevin on 2018/8/17.
 */
public interface BeanFactory {
    Object getBean(String beanId);
}
