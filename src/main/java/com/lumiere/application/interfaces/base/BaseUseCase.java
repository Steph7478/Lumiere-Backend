package com.lumiere.application.interfaces.base;

public interface BaseUseCase<I, O> {
    O execute(I input);
}
