package com.epam.training.toto.service.parser.util;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoundParser implements Parser<Round> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private Parser<Hit> hitParser = new HitParser();
    private Parser<Outcome> outcomeParser = new OutcomeParser();

    @Override
    public Round parse(final String... input) {
        return Round.builder()
                .setYear(Integer.parseInt(input[0]))
                .setWeek(Integer.parseInt(input[1]))
                .setRound(parseWeekNumber(input[2]))
                .setDate(retrieveDateOfRound(input))
                .setHits(getHits(input))
                .setOutcomes(getOutcomes(input))
                .build();
    }

    private int parseWeekNumber(final String input) {
        if (input.equals("-")) {
            return 1;
        } else {
            return Integer.parseInt(input);
        }
    }

    private LocalDate retrieveDateOfRound(final String[] input) {
        if (input[3].isEmpty()) {
            return LocalDate.ofYearDay(Integer.parseInt(input[0]), (Integer.parseInt(input[1]) - 1) * 7 + 1);
        } else {
            return LocalDate.parse(input[3], formatter);
        }
    }

    private List<Hit> getHits(final String[] input) {
        List<Hit> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Hit hit = hitParser.parse(input[4 + 2 * i], input[5 + 2 * i]);
            hit.setCount(14 - i);
            result.add(hit);
        }
        return result;
    }

    private List<Outcome> getOutcomes(final String[] input) {
        List<Outcome> result = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            result.add(outcomeParser.parse(input[14 + i]));
        }
        return result;
    }

}
