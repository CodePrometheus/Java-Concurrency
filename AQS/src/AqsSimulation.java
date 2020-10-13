import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 使用AQS实现一个简单的线程协作器
 *
 * @Author: zzStar
 * @Date: 10-13-2020 08:48
 */
public class AqsSimulation {

    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() {
        sync.acquireShared(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int arg) {
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AqsSimulation aqsSimulation = new AqsSimulation();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "尝试获取latch，失败则等待");
                aqsSimulation.await();
                System.out.println("开闸放行" + Thread.currentThread().getName() + "继续运行");
            }).start();
            Thread.sleep(3000);
            //唤醒
            aqsSimulation.signal();
        }
    }
}
