package ioc.config.parse;

import ioc.config.Bean;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Kevin on 2018/8/17.
 */
public class ConfigManagerTest {

    @Test
    public void getConfig() {
        Map<String, Bean> config = ConfigManager.getConfig("application.xml");
        System.out.println(config);
    }
}