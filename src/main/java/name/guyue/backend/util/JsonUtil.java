package name.guyue.backend.util;

import com.google.gson.Gson;
import java.util.Map;

/**
 * @author hujia
 * @date 2019-04-20
 */
public class JsonUtil {
    /** 把fields的内容合并到t对象中 */
    public static <T> T merge(T t, Map<String, Object> fields, Class<T> tClass) {
        Gson gson = new Gson();
        var json = gson.toJson(t);
        Map<String, Object> map = gson.fromJson(json, TypeUtil.map(String.class, Object.class));
        map.putAll(fields);
        json = gson.toJson(map);
        return gson.fromJson(json, tClass);
    }
}
