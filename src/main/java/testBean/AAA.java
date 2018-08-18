package testBean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Kevin on 2018/8/18.
 */
public class AAA {
    private String name;
    private Integer age;
    private BBB bbb;

    public AAA() {
        System.out.println("A被创建了");
    }

    public BBB getBbb() {
        return bbb;
    }

    public void setBbb(BBB bbb) {
        this.bbb = bbb;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
