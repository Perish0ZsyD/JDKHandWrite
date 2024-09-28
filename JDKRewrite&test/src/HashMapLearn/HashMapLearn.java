package HashMapLearn;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * for test
 *
 * @author Siyuan
 * @Date 2024/9/28 10:03
 */
public class HashMapLearn {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>();
        // 键不能重复，值可以重复
        map.put("san", "张三");
        map.put("si", "李四");
        map.put("wu", "王五");
        map.put("wang", "老王");
        map.put("wang", "老外2"); // 老王被覆盖
        map.put("lao", "老王");
        System.out.println("————直接输出hashmap:————");
        System.out.println(map);
        /**
         * 遍历HashMap
         */
        // 1.获取Map中的所有键
        System.out.println("————foreach获取Map中所有的键:————");
        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println(key + " ");;
        }
        System.out.println(); // 换行
        // 2.获取Map中所有值
        System.out.println("————foreach获取Map中所有的值:————");
        Collection<String> values = map.values();
        for (String value : values) {
            System.out.println(value + " ");
        }
        System.out.println(); // 换行
        // 3.得到key的值同时得到key对应的值
        System.out.println("————得到key的值的同时得到key所对应的值:————");
        Set<String> keys2 = map.keySet();
        for (String key : keys2) {
            System.out.println(key + ": " + map.get(key) + "  ");
        }

        /**
         * put(key, value)操作会把键值对映射到Entry这个静态内部对象中，把Entry对象再添加到数组中
         * 调用Entry中的getKey()和getValue()方法获取键值对
         */
        Set<java.util.Map.Entry<String, String>> entrys = map.entrySet();
        for (java.util.Map.Entry<String, String> entry : entrys) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        /**
         * HashMap其他常用方法
         */
        System.out.println("————HashMap其他用法:————");
        System.out.println("after map.size()："+map.size());
        System.out.println("after map.isEmpty()："+map.isEmpty());
        System.out.println(map.remove("san"));
        System.out.println("after map.remove()："+map);
        System.out.println("after map.get(si)："+map.get("si"));
        System.out.println("after map.containsKey(si)："+map.containsKey("si"));
        System.out.println("after containsValue(李四)："+map.containsValue("李四"));
        System.out.println(map.replace("si", "李四2"));
        System.out.println("after map.replace(si, 李四2):"+map);
    }
}
