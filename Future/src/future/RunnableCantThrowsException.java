package future;

/**
 * 在run方法中无法抛出checked Exception
 * <p>
 * public interface Runnable {
 * public abstract void run();
 * }
 * 方法里声明异常，返回void
 *
 * @Author: zzStar
 * @Date: 10-13-2020 09:24
 */
public class RunnableCantThrowsException {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            //只能try catch
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}

/*
@FunctionalInterface
public interface Callable<V> {
    */
/**
 * Computes a result, or throws an exception if unable to do so.
 *
 * @return computed result
 * @throws Exception if unable to compute a result
 *//*

    V call() throws Exception;
}
*/
