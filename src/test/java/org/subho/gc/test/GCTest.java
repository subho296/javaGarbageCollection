package org.subho.gc.test;

import org.junit.Assert;
import org.subho.gc.GC;
import org.subho.gc.test.entity.A;
import org.subho.gc.test.entity.B;
import org.subho.gc.test.entity.C;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GCTest {

    public List<ArrayList<String>> map = new ArrayList<ArrayList<String>>();

    @Test
    public void testGCLifecycle(){

        try {
            A a1 = new A(new B());

            GC.get(a1);
            GC.release(a1);

            GC.gc();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testWithoutFinalize(){
        try {
            C c1 = new C();

            GC.get(c1);
            GC.release(c1);

            GC.gc();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testWithCommonObject(){
        try {
            B b1 = new B();
            A a1 = new A(b1);
            A a2 = new A(b1);

            GC.get(a1);
            GC.get(a2);
            GC.release(a1);

            GC.gc();
        } catch (Exception e) {
            Assert.fail();
        }
    }




}
