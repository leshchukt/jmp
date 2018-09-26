package com.epam.training.toto.service.parser;

import com.epam.training.toto.domain.Hit;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;

public class HitParser {
    private static HitParser instance = new HitParser();
    private DecimalFormat format;

    private HitParser() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        format = new DecimalFormat("###,### UAH", symbols);
    }

    public static HitParser getInstance() {
        return instance;
    }

    public Hit parse(String... input) {
        return new Hit(format.parse(input[1], new ParsePosition(0)).intValue());
    }
}
