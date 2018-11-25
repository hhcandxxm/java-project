package com.hhc.concurrent.lock;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReenTrantLockMain {



    public static void main(String args[]) {
        Lock lock = new ReentrantLock();
        /**
         * Acquires the lock if it is not held by another thread and returns
         *      * immediately, setting the lock hold count to one.
         *
         */
        MyThread myThread = new MyThread(lock);
        System.out.println("当前线程：" + Thread.currentThread() + "状态" + Thread.currentThread().getState());
        System.out.println("子线程状态：" + myThread.getState());

        lock.lock();
//        lock.lock();
//        lock.lock();
//
//
//        System.out.println("锁的数量：" + ((ReentrantLock) lock).getHoldCount());
//
        lock.unlock();
//        System.out.println("锁的数量：" + ((ReentrantLock) lock).getHoldCount());



        myThread.start();
        while (true) {

        }
//        System.out.println("子线程状态：" + myThread.getState());
////        try {
////            Thread.sleep(5000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//        for (int i = 0; i < 200; i++) {
//            System.out.println("For子线程状态：" + myThread.getState());
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        lock.unlock();
//        System.out.println("子线程状态：" + myThread.getState());
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("子线程状态：" + myThread.getState());



    }

    public static class MyThread extends Thread {

        final private Lock lock;

        public MyThread(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            System.out.println("开始获取锁线程：" + Thread.currentThread() + "当前时间：" + new Date());

            lock.lock();
            System.out.println("获取锁线程：" + Thread.currentThread() + "当前时间：" + new Date());
            try {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("锁线程：" + Thread.currentThread() + "当前时间：" + new Date() + "线程状态" + Thread.currentThread().getState());

            } finally {
                System.out.println("开始释放锁线程：" + Thread.currentThread() + "当前时间：" + new Date());
                lock.unlock();
                System.out.println("释放锁线程：" + Thread.currentThread() + "当前时间：" + new Date());

            }
        }
    }
}
