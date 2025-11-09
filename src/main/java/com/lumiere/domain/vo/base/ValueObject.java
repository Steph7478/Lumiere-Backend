package com.lumiere.domain.vo.base;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class ValueObject {
    protected abstract Stream<Object> getAtomicValues();

    protected void validate() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || this.getClass() != obj.getClass())
            return false;

        ValueObject other = (ValueObject) obj;

        return Arrays.equals(this.getAtomicValues().toArray(),
                other.getAtomicValues().toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAtomicValues().toArray());
    }
}