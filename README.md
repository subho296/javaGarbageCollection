Java Garbage collector

This is a rudimentary implementation of garbage collector. This implementation takes care of identifying unused references,
removing them and calling finalize method if present.

Identification of unused references and finalization of object happens concurrently.

Primarily, divided into three parts -

GC: This is the main entry point of the module and performs following operations -
    a) get(Object) : Add new object to the reference graph
    b) release(Object) : To indicate that object is no more required
    c) gc() : Request to start the garbage collection


GCTask: Primary function of this class is to traverse through graph and identify unused references.
        It also push collected objects(unused references) to finalize reference queue, which is
        taken care by FinalizeTask

FinalizeTask: FinalizeTask concurrently processes(calls finalize) objects pushed to finalize reference queue.

ReferenceQueue: Abstracts blocking queue to maintain objects to be finalized.

Reference: Basic reference implementation. Equivalent to node of graph.

Use cases covered:
         avoiding cyclic references during traversal.
         works for both objects with or without finalize method.
         non blocking implementation of finalize.

Test cases are also included in the project.
