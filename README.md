# Java-Concurrency
Java并发编程

视频讲解[B站](https://www.bilibili.com/video/BV1Ev411k7AA)

## 线程
     创建线程的两种方法
     正确启动线程
     正确停止线程
     线程六大生命周期
     Thread和Object中的重要方法
     线程的属性
     未捕获异常的处理
     线程安全问题
     ThreadPool
     ThreadLocal
     线程核心知识总结
     

## 锁
     乐观与悲观锁
     公平与非公平锁
     可重入锁与非可重入锁：以ReentrantLock为例
     共享锁和排他锁：以ReentrantReadWriterLock读写锁为例
     自旋锁和阻塞锁
     可中断锁
     锁优化
     
     
## 死锁
    死锁的发生
    定位死锁
    哲学家进餐问题与解决
    避免死锁
    活锁问题
    饥饿


## Synchronized
    作用用法
    性质
    原理
    缺陷


## 原子类
    基本类型原子类
        AtomicInteger
        AtomicLong
        AtomicBoolean
     
    数组类型原子类
        AtomicIntegerArray
        AtomicLongArray
        AtomicReferenceArray
           
    引用类型原子类
        AtomicReference
        AtomicStampedReference
        AtomicMarkableReference
        
    升级类型原子类
        AtomicIntegerFieldUpdater
        AtomicLongFieldUpdater
        AtomicReferenceFieldUpdater
        
    Adder累加器
        LongAdder
        DoubleAdder
        
    Accumulator累加器
        LongAccumulator
        DoubleAccumulator  
        

## CAS
    原理
    应用场景
    Java中如何利用CAS实现原子操作
    缺点
    

## 不可变性
    final的作用与用法
    不可变性
    栈封闭

## 并发容器
    ConcurrentHashMap
    CopyOnWriteArrayList
    并发队列
        阻塞队列
        非阻塞队列
    

## 控制并发流程
    各个线程之间相互协调
    CountDownLatch(一等多，多等一)
    Semaphore(控制并发总量)
    Condition
    CyclicBarrier


## AQS
    state
    控制线程抢锁和配合的FIFO队列
    期望协作工具类去实现的获取/释放等重要方法
        
     
## Future    
    Future接口
    主要方法
    FutureTask
    注意点
    

## JMM
    内存模型
    重排序
    可见性
    原子性
    常见问题
