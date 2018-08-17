package util.config.parse;

import org.junit.Test;
import util.config.Bean;

import java.util.Map;

import static org.junit.Assert.*;

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