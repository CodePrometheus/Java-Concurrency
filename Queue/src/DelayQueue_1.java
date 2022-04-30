import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Starry
 * @Date: 04-30-2022 20:21
 */
public class DelayQueue_1 {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DelayQueue<Order> orders = new DelayQueue<>();
        System.out.println(df.format(new Date()) + " 订单 A 加入队列");
        // 生成订单A并加入队列
        Order order_A = new Order("Starry", "testA_" + random.nextInt(100), 10, TimeUnit.SECONDS);
        orders.offer(order_A);
        Thread.sleep(5000);
        // 等待5s生成订单B并加入队列
        System.out.println(df.format(new Date()) + " 订单 B 加入队列");
        Order order_B = new Order("Jack Ma", "testB_" + random.nextInt(100), 10, TimeUnit.SECONDS);
        orders.offer(order_B);
        Thread.sleep(5000);
        // 再等待5s生成订单C并加入队列
        System.out.println(df.format(new Date()) + " 订单 C 加入队列");
        Order order_C = new Order("Pony", "testC_" + random.nextInt(100), 10, TimeUnit.SECONDS);
        orders.offer(order_C);
        while (!orders.isEmpty()) {
            // 若没有超时的元素，take()会阻塞该线程
            Order order = orders.take();
            System.out.println("订单因为未支付：" + order + " 在 " + df.format(new Date()) + "被取出！！");
        }
    }

}

class Order implements Delayed {

    private String user;
    private String orderId;
    private long orderTime;

    public Order(String user, String orderId, long orderTime, TimeUnit timeUnit) {
        this.user = user;
        this.orderId = orderId;
        this.orderTime = System.currentTimeMillis() + (orderTime > 0 ? timeUnit.toMillis(orderTime) : 0);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return orderTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        Order order = (Order) o;
        long res = this.orderTime - order.getOrderTime();
        return res <= 0 ? -1 : 1;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user='" + user + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderTime=" + orderTime +
                '}';
    }
}
