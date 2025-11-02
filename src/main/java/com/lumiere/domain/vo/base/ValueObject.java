package com.lumiere.domain.vo.base;

public abstract class ValueObject<T> {

    protected final T value;

    protected ValueObject(T value) {
        this.value = value;
        validate();
    }

    protected abstract void validate();

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ValueObject))
            return false;
        ValueObject<?> other = (ValueObject<?>) obj;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
