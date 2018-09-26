package com.epam.training.toto.domain;

import java.time.LocalDate;
import java.util.List;

public class Round {
    private LocalDate date;
    private List<Hit> hits;
    private List<Outcome> outcomes;

    public Round(LocalDate date, List<Hit> hits, List<Outcome> outcomes) {
        this.date = date;
        this.hits = hits;
        this.outcomes = outcomes;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public List<Hit> getHits() {
        return hits;
    }
}
