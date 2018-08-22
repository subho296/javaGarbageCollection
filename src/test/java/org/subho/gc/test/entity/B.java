package com.agoda.gc.test.entity;

public class B{

    public void finalize(){
        System.out.println("Destructor called for B");
    }

}
