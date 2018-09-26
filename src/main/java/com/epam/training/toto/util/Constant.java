package com.epam.training.toto.util;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public interface Constant {
    Integer NUMBER_OF_GAMES = 14;
    Integer NUMBER_OF_HITS = 5;
    Integer START_NUMBER_OF_HITS_TO_MAKE_MONEY = 10;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("uk", "UA"));
}
