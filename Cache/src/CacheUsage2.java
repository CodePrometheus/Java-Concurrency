import java.util.HashMap;

/**
 * 装饰者模式解耦
 *
 * @Author: zzStar
 * @Date: 10-13-2020 13:03
 */
public class CacheUsage2<A, V> implements Computable<A, V> {

    private final HashMap<A, V> cache = new HashMap<>();

    private final Computable<A, V> computable;

    public CacheUsage2(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public synchronized V compute(A arg) throws Exception {
        System.out.println("cache");
        V result = cache.get(arg);
        if (result == null) {
            result = computable.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        CacheUsage2<String, Integer> usage2 = new CacheUsage2<>(new ExpensiveFunc());
        Integer result = usage2.compute("1333");
        System.out.println("result = " + result);
        result = usage2.compute("1333");
        System.out.println("result = " + result);

    }
}
