package com.epam.training.toto.domain;

public class Hit {
    private int count;
    private int numberOfGames;
    private Integer prizeForTheGames;

    public Hit(int numberOfGames, int prizeForTheGames) {
        this.numberOfGames = numberOfGames;
        this.prizeForTheGames = prizeForTheGames;
    }

    public Integer getPrizeForTheGames() {
        return prizeForTheGames;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
