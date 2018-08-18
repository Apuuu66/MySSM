package ioc.factory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 2018/8/18.
 */
public class ClassPathXmlApplicationContextTest {

    @Test
    public void getBean() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        Object a = context.getBean("a");
        System.out.println(a);
    }
}