package com.epam.training.toto.service.parser.util;

public interface Parser<T> {
    T parse(String... input);
}
