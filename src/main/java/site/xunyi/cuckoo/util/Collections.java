package site.xunyi.cuckoo.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Collections {
    /**
     * 遍历Collection
     */
    public static <T, R> List<R> forEach(Collection<T> list, Function<T, R> f){
        return forEach(list, f, Collectors.toList());
    }
    public static <T, R, C> C forEach(Collection<T> list, Function<T, R> f, Collector<R, ?, C> c){
        return list == null ? null : list.stream().filter(e -> e != null)
                .map(f).collect(c);
    }
}
