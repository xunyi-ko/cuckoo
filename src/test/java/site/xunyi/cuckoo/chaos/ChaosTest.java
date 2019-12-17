/**
 * something just have a try, not test for project
 */
package site.xunyi.cuckoo.chaos;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

/**
 * @author xunyi
 */
public class ChaosTest {
    @Test
    public void testThreadPoolExecutor() {
        ThreadFactory factory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "test" + threadNumber.getAndIncrement());
                
                if (t.isDaemon()) {
                    t.setDaemon(false);
                }
                if (t.getPriority() != Thread.NORM_PRIORITY) {
                    t.setPriority(Thread.NORM_PRIORITY);
                }

                return t;
            }
        };
        // 用这个构造方法，可以给线程命名
        ThreadPoolExecutor pool = new ThreadPoolExecutor(8, 160, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), factory);
    }
    
    @Test
    public void testMath() {
        System.out.println(Math.round(11.5));// 12
        System.out.println(Math.round(-11.5));// -11
    }
}
