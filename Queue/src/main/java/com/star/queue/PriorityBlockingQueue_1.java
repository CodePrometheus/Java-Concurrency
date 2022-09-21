package com.star.queue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author: Starry
 * @Date: 04-30-2022 19:52
 */
public class PriorityBlockingQueue_1 {

    public static void main(String[] args) {
        PriorityBlockingQueue<Patient> pbq = new PriorityBlockingQueue<>(10);
        for (int i = 0; i < 3; i++) {
            Patient patent = new Patient("Patent" + i, 20 + i);
            pbq.offer(patent);
        }
        Patient oldMan = new Patient("OldMan", 88);
        pbq.offer(oldMan);

        Patient patient;
        do {
            patient = pbq.poll();
            if (patient != null) {
                System.out.println(patient.name + "挂号成功！");
            }
        } while (patient != null);
    }

    static class Patient implements Comparable<Patient> {
        private String name;
        private Integer age;
        private long waitingTime;

        public Patient(String name, Integer age) {
            this.name = name;
            this.age = age;
            this.waitingTime = System.nanoTime();
        }

        @Override
        public int compareTo(Patient o) {
            if (age >= 80) {
                return -1;
            } else if (o.age >= 80) {
                return 1;
            }
            return waitingTime < o.waitingTime ? -1 : 1;
        }
    }

}
