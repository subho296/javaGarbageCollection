package org.subho.gc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FinalizeTask<T> implements Runnable {

    private ReferenceQueue<T> referenceQueue;

    public FinalizeTask(ReferenceQueue<T> referenceQueue){
        this.referenceQueue = referenceQueue;
    }

    @Override
    public void run() {
        System.out.println("Inside Finalize Task");

        while (true){
            T object = referenceQueue.remove();
            try {
                Method finalize = object.getClass().getDeclaredMethod("finalize");

                if (finalize == null)
                    continue;

                finalize.invoke(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
