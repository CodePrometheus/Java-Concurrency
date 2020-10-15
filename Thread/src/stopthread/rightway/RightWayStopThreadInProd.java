package stopthread.rightway;

/**
 * 生产实践中，停止线程的最佳方法
 * -> catch了InterruptedException之后优先选择 ->在方法签名中抛出异常
 * 那么在run()就会强制try/catch，且只有try这个方法，因为run方法为顶层函数，无法抛出checked Exception
 *
 * @Author: zzStar
 * @Date: 10-14-2020 17:26
 */
public class RightWayStopThreadInProd implements Runnable {

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("gogogo");
            try {
                throwInMethod();
            } catch (InterruptedException e) {
                System.out.println("正确响应处理中断请求");
                e.printStackTrace();
            }
        }
    }

    private void throwInMethod() throws InterruptedException {
        //这里抛出
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
