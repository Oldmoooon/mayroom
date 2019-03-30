package name.guyue.backend.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author hujia
 * @date 2019-03-30
 */
public class TypeUtil {
    private static class MapParameterizedType implements ParameterizedType {
        private Type key;
        private Type value;

        private MapParameterizedType(Type key, Type value) {
            this.key = key;
            this.value = value;
        }

        @Override public Type[] getActualTypeArguments() {
            return new Type[] {key, value};
        }

        @Override public Type getRawType() {
            return Map.class;
        }

        @Override public Type getOwnerType() {
            return null;
        }
    }

    private static class ListParameterizedType implements ParameterizedType {
        private Type type;

        private ListParameterizedType(Type type) {
            this.type = type;
        }

        @Override public Type[] getActualTypeArguments() {
            return new Type[] {type};
        }

        @Override public Type getRawType() {
            return ArrayList.class;
        }

        @Override public Type getOwnerType() {
            return null;
        }
    }

    public static Type map(Class<?> clz1, Class<?> clz2) {
        return new MapParameterizedType(clz1, clz2);
    }

    public static  Type list(Class<?> clz) {
        return new ListParameterizedType(clz);
    }
}
