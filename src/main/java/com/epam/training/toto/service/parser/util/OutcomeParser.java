package com.epam.training.toto.service.parser.util;

import com.epam.training.toto.domain.Outcome;

public class OutcomeParser implements Parser<Outcome> {

    @Override
    public Outcome parse(final String... input) {
        String value = input[0];
        if (value.endsWith("1")) {
            return Outcome.FIRST_TEAM_VICTORY;
        } else if (value.endsWith("2")) {
            return Outcome.SECOND_TEAM_VICTORY;
        } else {
            return Outcome.DRAW;
        }
    }

}
