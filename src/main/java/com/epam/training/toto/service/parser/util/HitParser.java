package com.epam.training.toto.service.parser.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;

import javax.annotation.PostConstruct;

import com.epam.training.toto.domain.Hit;

public class HitParser implements Parser<Hit> {

    private DecimalFormat format;

    @Override
    public Hit parse(final String... input) {
        initFormatter();
        return Hit.builder()
                .setNumberOfGames(Integer.parseInt(input[0]))
                .setPrizeForTheGames((format.parse(input[1], new ParsePosition(0))).intValue())
                .build();
    }

    private void initFormatter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        format = new DecimalFormat("###,### UAH", symbols);
    }
}
