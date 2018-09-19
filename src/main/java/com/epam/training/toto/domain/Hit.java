package com.epam.training.toto.domain;

public class Hit {
    private int count;
    private int numberOfGames;
    private int prizeForTheGames;

    private Hit() {}

    public void setCount(int count) {
        this.count = count;
    }

    public static Builder builder() {
        return new Hit().new Builder();
    }

    public class Builder {
        private Builder() {}

        public Builder setNumberOfGames(int numberOfGames) {
            Hit.this.numberOfGames = numberOfGames;
            return this;
        }

        public Builder setPrizeForTheGames(int prizeForTheGames) {
            Hit.this.prizeForTheGames = prizeForTheGames;
            return this;
        }

        public Hit build() {
            return Hit.this;
        }
    }
}
