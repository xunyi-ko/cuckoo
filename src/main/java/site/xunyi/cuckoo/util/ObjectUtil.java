/**
 * 
 */
package site.xunyi.cuckoo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

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
    
    public static <T> T parseObject(JSONObject data, Class<T> clazz) {
        Constructor<T> constructor = null;
        T newInstance = null;
        try {
            constructor = clazz.getConstructor();
            newInstance = constructor.newInstance();
            
            for(Entry<String, Object> param : data.entrySet()) {
                try {
                    Field field = clazz.getField(param.getKey());
                    field.set(newInstance, param.getValue());
                } catch (NoSuchFieldException e) {
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return newInstance;
    }
}
