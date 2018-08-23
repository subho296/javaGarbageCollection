package org.subho.gc;

import java.util.HashSet;
import java.util.Set;

public class GCTask implements Runnable{

    private Reference root;
    private Set<Integer> releaseObjects;
    private ReferenceQueue referenceQueue;

    public GCTask(Reference root, Set<Integer> releaseObjects, ReferenceQueue referenceQueue) {
        this.root = root;
        this.releaseObjects = releaseObjects;
        this.referenceQueue = referenceQueue;
    }

    @Override
    public void run() {
        System.out.println("Starting GC Task thread");
        Set<Integer> markSet = new HashSet<>();
        markReference(root, markSet);
        removeReference(root, markSet);
    }

    private Reference removeReference(Reference root, Set<Integer> markSet) {
        Object obj = root.getObject();

        int identityHashCode = System.identityHashCode(obj);

        Set<Reference> deleteReferences = new HashSet<>();
        for (Reference reference : root.getReferences()){
            if (removeReference(reference, markSet) == null)
                deleteReferences.add(reference);
        }

        addToReferenceQueue(deleteReferences);

        root.getReferences().removeAll(deleteReferences);

        if (markSet.contains(identityHashCode))
            return root;
        return null;
    }

    private void addToReferenceQueue(Set<Reference> deleteReferences) {
        for (Reference reference : deleteReferences){
            try {
                if (reference.getObject().getClass().getDeclaredMethod("finalize") == null)
                    continue;
                referenceQueue.add(reference.getObject());
            } catch (NoSuchMethodException e) {
                System.out.println("Finalize method does not exist");
            }
        }
    }

    private void markReference(Reference root, Set<Integer> markSet) {

        Object obj = root.getObject();

        int identityHashCode = System.identityHashCode(obj);
        if (releaseObjects.contains(identityHashCode))
            return;
        else if (! markSet.add(identityHashCode))
            return;


        for (Reference reference : root.getReferences()){
            markReference(reference, markSet);
        }
    }
}
