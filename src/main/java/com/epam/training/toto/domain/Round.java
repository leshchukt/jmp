package com.epam.training.toto.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Round {
    private Integer year;
    private Integer week;
    private Integer round;
    private LocalDate date;
    private List<Hit> hits;
    private List<Outcome> outcomes;

    private Round() {}

    public LocalDate getDate() {
        return date;
    }

    public static Builder builder() {
        return new Round().new Builder();
    }

    public class Builder {
        private Builder() {}

        public Builder setYear(Integer year) {
            Round.this.year = year;
            return this;
        }

        public Builder setWeek(Integer week) {
            Round.this.week = week;
            return this;
        }

        public Builder setRound(Integer round) {
            Round.this.round = round;
            return this;
        }

        public Builder setDate(LocalDate date) {
            Round.this.date = date;
            return this;
        }

        public Builder setHits(List<Hit> hits) {
            Round.this.hits = hits;
            return this;
        }

        public Builder setOutcomes(List<Outcome> outcomes) {
            Round.this.outcomes = outcomes;
            return this;
        }

        public Round build() {
            return Round.this;
        }
    }
}
