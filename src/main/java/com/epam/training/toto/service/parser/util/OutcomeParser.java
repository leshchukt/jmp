package com.epam.training.toto.service.parser.util;

import com.epam.training.toto.domain.Outcome;

public class OutcomeParser {
    private static OutcomeParser instance = new OutcomeParser();

    private OutcomeParser() {
    }

    public static OutcomeParser getInstance() {
        return instance;
    }

    public Outcome parse(String input) {
        String value = input;
        if (value.endsWith("1")) {
            return Outcome.FIRST_TEAM_VICTORY;
        } else if (value.endsWith("2")) {
            return Outcome.SECOND_TEAM_VICTORY;
        }
        return Outcome.DRAW;
    }

}
