package com.agoda.gc.test.entity;

public class A{

    private B b;

    public A(B b) {
        this.b = b;
    }

    public void finalize(){
        System.out.println("Destructor called for A");
    }
}
