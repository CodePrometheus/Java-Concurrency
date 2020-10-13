/**
 * 提高代码复用性 —> 装饰者模式
 * 实现无侵入缓存功能
 *
 * @Author: zzStar
 * @Date: 10-13-2020 12:54
 */
public interface Computable<A,V> {
    V compute(A arg) throws Exception;
}
