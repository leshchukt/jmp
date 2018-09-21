package com.epam.training.toto.service.parser.util;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoundParser {
    public static final Integer NUMBER_OF_GAMES = 14;
    public static final Integer NUMBER_OF_HITS = 5;

    public static RoundParser instance = new RoundParser();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private HitParser hitParser = HitParser.getInstance();
    private OutcomeParser outcomeParser = OutcomeParser.getInstance();

    private RoundParser() {
    }

    public static RoundParser getInstance() {
        return instance;
    }

    /**
     * Method parses input data
     *
     * @param input a row of data with all information about the round from the source file
     * @return Round parsed from input data
     */
    public Round parse(String... input) {
        return new Round(Integer.parseInt(input[0]),
                Integer.parseInt(input[1]),
                parseWeekNumber(input[2]),
                parseDateOfRound(input),
                getHits(input),
                getOutcomes(input)
        );
    }

    private int parseWeekNumber(String input) {
        return "-".equals(input) ? 1 : Integer.parseInt(input);
    }

    private LocalDate parseDateOfRound(String[] input) {
        return input[3].isEmpty() ?
                LocalDate.ofYearDay(Integer.parseInt(input[0]), (Integer.parseInt(input[1]) - 1) * 7 + 1) :
                LocalDate.parse(input[3], formatter);
    }

    private List<Hit> getHits(String[] input) {
        List<Hit> result = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_HITS; i++) {
            Hit hit = hitParser.parse(input[4 + 2 * i], input[5 + 2 * i]);
            hit.setCount(NUMBER_OF_GAMES - i);
            result.add(hit);
        }
        return result;
    }

    private List<Outcome> getOutcomes(String[] input) {
        List<Outcome> result = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            result.add(outcomeParser.parse(input[14 + i]));
        }
        return result;
    }
}
