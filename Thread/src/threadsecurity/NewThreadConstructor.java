package threadsecurity;

import java.util.HashMap;
import java.util.Map;

/**
 * 构造函数中新建线程
 *
 * @Author: zzStar
 * @Date: 10-19-2020 12:48
 */
public class NewThreadConstructor {

    private Map<String, String> states;


    // 构造函数中新建线程
    public NewThreadConstructor() {
        new Thread(() -> {
            states = new HashMap<>();
            states.put("1", "a");
            states.put("2", "b");
            states.put("3", "c");
        }).start();
    }

    public Map<String, String> getStates() {
        return states;
    }

    public static void main(String[] args) throws InterruptedException {
        NewThreadConstructor newThreadConstructor = new NewThreadConstructor();
        // java.lang.NullPointerException
        // 初始化的工作在另外一个线程还没执行完毕
        // 时间不同，结果不同
        Thread.sleep(1000);
        System.out.println(newThreadConstructor.getStates().get("1"));

    }
}
