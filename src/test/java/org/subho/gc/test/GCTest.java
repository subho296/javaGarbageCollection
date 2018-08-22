package com.agoda.gc.test;

import com.agoda.gc.GC;
import com.agoda.gc.test.entity.A;
import com.agoda.gc.test.entity.B;
import com.agoda.gc.test.entity.C;
import org.junit.Test;

public class GCTest {

    @Test
    public void testGCLifecycle(){
        A a1 = new A(new B());

        GC.get(a1);
        GC.release(a1);

        GC.gc();
    }

    @Test
    public void testWithoutFinalize(){
        C c1 = new C();

        GC.get(c1);
        GC.release(c1);

        GC.gc();
    }

    @Test
    public void testWithCommonObject(){
        B b1 = new B();
        A a1 = new A(b1);
        A a2 = new A(b1);

        GC.get(a1);
        GC.get(a2);
        GC.release(a1);

        GC.gc();
    }




}
