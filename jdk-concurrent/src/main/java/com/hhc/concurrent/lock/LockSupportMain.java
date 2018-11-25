package com.hhc.concurrent.lock;

import java.util.Date;
import java.util.concurrent.locks.LockSupport;

/**
 * class java.util.concurrent.locks.LockSupport
 * 参考文档
 * https://yq.aliyun.com/articles/493552
 *
 * 特点：可以唤醒指定线程
 */
public class LockSupportMain {



    public static void main(String args[]) {
        Object object = new Object();
        //LockSupport的简单使用
        MyThread myThread1 = new MyThread(object);
        myThread1.setName("myThread-1");
        MyThread myThread2 = new MyThread(object);
        myThread2.setName("myThread-2");
        System.out.println("myThread1状态：" + myThread1.getState());
        System.out.println("myThread2状态：" + myThread2.getState());

        LockSupport.unpark(myThread1);
        myThread1.start();
        myThread2.start();
        System.out.println("myThread1状态：" + myThread1.getState());
        System.out.println("myThread2状态：" + myThread2.getState());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("myThread1状态：" + myThread1.getState());
        System.out.println("myThread2状态：" + myThread2.getState());

        System.out.println("myThread1-Blocker：" + LockSupport.getBlocker(myThread1));

        LockSupport.unpark(myThread1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("myThread1状态：" + myThread1.getState());
        System.out.println("myThread2状态：" + myThread2.getState());
        LockSupport.unpark(myThread2);
        System.out.println("myThread1状态：" + myThread1.getState());
        System.out.println("myThread2状态：" + myThread2.getState());

        System.out.println("=========================================================================");
        //LockSupport的定时使用
        MyThreadTime myThreadTime = new MyThreadTime(object);
        myThreadTime.setName("myThread-Time");
        System.out.println("myThreadTime状态：" + myThreadTime.getState());
        myThreadTime.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("myThreadTime状态：" + myThreadTime.getState());
//        myThreadTime.interrupt();
    }

    public static class MyThread extends Thread {

        final private Object object;

        public MyThread(Object object) {
            this.object = object;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread() + "开始获取park," + "当前时间：" + new Date());
            LockSupport.park(object);
            System.out.println(Thread.currentThread() + "已获取park：" + "当前时间：" + new Date());
            try {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "获取后park：" + "线程状态" + Thread.currentThread().getState() + "当前时间：" + new Date());

            } finally {
                System.out.println(Thread.currentThread() + "获取后park：" + "线程状态" + Thread.currentThread().getState() + "当前时间：" + new Date());

            }
        }
    }


    public static class MyThreadTime extends Thread {

        final private Object object;

        public MyThreadTime(Object object) {
            this.object = object;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread() + "开始获取park," + "当前时间：" + new Date());
            LockSupport.parkNanos(object, 10000000000L);
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread() + "被打断" + "当前时间：" + new Date());
            }
            System.out.println(Thread.currentThread() + "已获取park：" + "当前时间：" + new Date());
            try {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "获取后park：" + "线程状态" + Thread.currentThread().getState() + "当前时间：" + new Date());

            } finally {
                System.out.println(Thread.currentThread() + "获取后park：" + "线程状态" + Thread.currentThread().getState() + "当前时间：" + new Date());

            }
        }
    }
}
