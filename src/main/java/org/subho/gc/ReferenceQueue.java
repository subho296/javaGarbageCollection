package org.subho.gc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ReferenceQueue<T> {

    private BlockingQueue<T> queue = new ArrayBlockingQueue<>(1024);

    public void add(T t){
        synchronized (queue) {
            queue.offer(t);
            queue.notifyAll();
        }
    }

    public T remove(){
        try {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    System.out.println("Inside Wait");
                    queue.wait();
                }
                return queue.remove();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}
