/**
 * 
 */
package site.xunyi.cuckoo.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xunyi
 */
public class ObjectUtil {
    /**
     * 根据name列表复制一个对象
     * names列表为空时，复制所有参数
     * 
     * 必须有无参构造方法
     * 并且不是内部类
     * @param object
     * @param names
     * @return
     */
    public static <T> T clone(T object, Iterable<String> names){
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) object.getClass();
        Constructor<T> constructor = null;
        
        try {
            constructor = clazz.getConstructor();
            T newInstance = constructor.newInstance();
            if(names != null) {
                for(String name : names) {
                    try {
                        Field field = clazz.getDeclaredField(name);
                        
                        field.setAccessible(true);
                        field.set(newInstance, field.get(object));
                    }catch(NoSuchFieldException e) {
                        System.out.println(e.getMessage() + "dose not exist");
                    }catch(IllegalAccessException e) {
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }else {
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields) {
                    try {
                        field.setAccessible(true);
                        field.set(newInstance, field.get(object));
                    }catch(IllegalAccessException e) {
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return newInstance;
        }catch(NoSuchMethodException e) {
            return null;
        }catch(Exception e) {
            return null;
        }
    }
    
    public static <T> List<T> clone(Iterable<T> objects, Iterable<String> names){
        List<T> res = new ArrayList<>();
        if(objects == null) {
            return res;
        }
        
        for(T object : objects) {
            res.add(clone(object, names));
        }
        return res;
    }
}
