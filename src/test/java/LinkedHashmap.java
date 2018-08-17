import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Kevin on 2018/8/16.
 */
public class LinkedHashmap {
    public static void main(String[] args) {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(16, 0.75f);
        for (int i = 0; i < 10; i++) {
            map.put(i, i);
        }

        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        map.get(3);
        System.out.println();
        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
