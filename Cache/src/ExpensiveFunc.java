import java.util.concurrent.TimeUnit;

/**
 * 耗时计算的实现类
 *
 * @Author: zzStar
 * @Date: 10-13-2020 13:01
 */
public class ExpensiveFunc implements Computable<String, Integer> {
    @Override
    public Integer compute(String arg) throws Exception {
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName() + "calculating...");
        return Integer.valueOf(arg);
    }
}
