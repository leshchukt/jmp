package com.epam.training.toto.service.parser;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;
import com.epam.training.toto.util.Constant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoundParser {
    public static RoundParser instance = new RoundParser();

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
    public Round parse(String[] input) {
        return new Round(
                parseDateOfRound(input),
                getHits(input),
                getOutcomes(input)
        );
    }

    private LocalDate parseDateOfRound(String[] input) {
        return input[3].isEmpty() ?
                LocalDate.ofYearDay(Integer.parseInt(input[0]), (Integer.parseInt(input[1]) - 1) * 7 + 1) :
                LocalDate.parse(input[3], Constant.formatter);
    }

    private List<Hit> getHits(String[] input) {
        List<Hit> result = new ArrayList<>();
        for (int i = 0; i < Constant.NUMBER_OF_HITS; i++) {
            Hit hit = hitParser.parse(input[4 + 2 * i], input[5 + 2 * i]);
            result.add(hit);
        }
        return result;
    }

    private List<Outcome> getOutcomes(String[] input) {
        List<Outcome> result = new ArrayList<>();
        for (int i = 0; i < Constant.NUMBER_OF_GAMES; i++) {
            result.add(outcomeParser.parse(input[14 + i]));
        }
        return result;
    }
}
