package src;

import src.errors.InvalidInput;

@FunctionalInterface
public interface Validator<T> {
    void validate(T t) throws InvalidInput;
}