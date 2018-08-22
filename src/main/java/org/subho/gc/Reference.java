package com.agoda.gc;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Reference {

    private Object object;
    private Set<Reference> references;

    public Reference(Object object) {
        this.object = object;
        references = new HashSet<>();
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Set<Reference> getReferences() {
        return references;
    }

    public void addReference(Reference reference) {
        this.references.add(reference);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return Objects.equals(object, reference.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object);
    }
}
