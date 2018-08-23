package org.subho.gc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class GC {

    private static Reference root = new Reference(new Object());
    private static Map<Integer, Reference> allReferences = new HashMap<>();
    private static ExecutorService gcExecutor = Executors.newSingleThreadExecutor();
    private static ExecutorService finalizeExecutor = Executors.newSingleThreadExecutor();
    private static ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
    private static Set<Integer> releaseObjects = new HashSet<>();

    private GC() {}


    static {
        FinalizeTask<Object> finalizeTask = new FinalizeTask<>(referenceQueue);
        finalizeExecutor.submit(finalizeTask);
    }


    public static Reference get(Object object){
        return createReferences(object, 0);
    }

    private static Reference createReferences(Object object, int count) {

        if (object == null)
            return null;

        int hashCode = System.identityHashCode(object);
        Reference reference = allReferences.get(hashCode);
        if (reference == null)
            reference = new Reference(object);

        if (count == 0)
            root.addReference(reference);

        for (Field field : object.getClass().getDeclaredFields()){
            if (field instanceof Object){
                try {
                    field.setAccessible(true);
                    reference.addReference(createReferences(field.get(object), ++count));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return reference;
    }

    public static synchronized void release(Object object){
        if (object == null)
            return;
        releaseObjects.add(System.identityHashCode(object));
    }

    public static synchronized void gc(){
        gcExecutor.submit(new GCTask(root, new HashSet<Integer>(releaseObjects), referenceQueue));
        releaseObjects.clear();
    }

}
