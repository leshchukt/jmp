package com.epam.training.toto.service;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;
import com.epam.training.toto.service.parser.CSVParser;
import com.epam.training.toto.service.parser.OutcomeParser;
import com.epam.training.toto.util.Constant;
import com.epam.training.toto.util.exception.DataCanNotBeReadException;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TotoService is a service to process the input data and get statistical information about wagers.
 * Before using this class initData() must be called in order to read data to operate with from the source file.
 */
public class TotoService {
    private CSVParser parser;
    private Scanner scanner;
    private Map<LocalDate, Round> data;

    private static final PrintStream console = System.out;
    private static final PrintStream error = System.err;

    public TotoService(Scanner scanner) {
        this.parser = new CSVParser();
        this.scanner = scanner;
    }

    public void initData() {
        try {
            data = parser.readData();
        } catch (DataCanNotBeReadException e) {
            error.println(e.getMessage());
            System.exit(1);
        }
    }

    private Integer getTheLargestPrize() {
        return data.values()
                .stream()
                .map(Round::getHits)
                .flatMap(Collection::stream)
                .map(Hit::getPrizeForTheGames)
                .max(Comparator.naturalOrder())
                .get();
    }

    private List<Map<Outcome, Long>> getDistributionOfResultsForEachRound() {
        List<Map<Outcome, Long>> distributionForEachRound = new ArrayList<>();
        data.values()
                .forEach(round -> distributionForEachRound.add(round.getOutcomes()
                        .stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                );
        return distributionForEachRound;
    }

    public void printTheLargestPrize() {
        console.println(String.format("======== The largest prize: %s ========",
                Constant.numberFormat.format(getTheLargestPrize())));
    }

    public void printDistributionOfTheResults() {
        console.println("\n======== The distribution of the results of each round ========");
        getDistributionOfResultsForEachRound()
                .forEach(round -> console.println("team #1 won: " +
                        formatDistribution(round.get(Outcome.FIRST_TEAM_VICTORY)) +
                        ", team #2 won: " +
                        formatDistribution(round.get(Outcome.SECOND_TEAM_VICTORY)) +
                        ", draw: " +
                        formatDistribution(round.get(Outcome.DRAW)))
                );
    }

    private String formatDistribution(Long amountOfVictories) {
        if (amountOfVictories == null) {
            return "0%";
        }
        return String.format("%.2f ", amountOfVictories / (double) Constant.NUMBER_OF_GAMES) + "%";
    }

    public void printHitsAndAmountForTheWager() {
        console.println("\n======== The hits and amount for the wager ========");
        Optional<Round> round;
        do {
            LocalDate dateToSearch = readDateToSearch();
            round = getRoundByDate(dateToSearch);
        } while (!round.isPresent());

        List<Outcome> wager = readWager();
        int hitCounter = 0;
        int prize = 0;
        for (int gameCounter = 0; gameCounter < Constant.NUMBER_OF_GAMES; gameCounter++) {
            if (round.get().getOutcomes().get(gameCounter) == wager.get(gameCounter)) {
                hitCounter++;
            }
        }
        if (hitCounter > Constant.START_NUMBER_OF_HITS_TO_MAKE_MONEY - 1) {
            prize = round.get().getHits().get(Constant.NUMBER_OF_GAMES % hitCounter).getPrizeForTheGames();
        }
        console.println(String.format("Result: hits: %d, amount: %s", hitCounter, Constant.numberFormat.format(prize)));
    }

    private List<Outcome> readWager() {
        List<Outcome> outcomes = new ArrayList<>();
        boolean isInputCorrect;
        do {
            console.print("Enter outcomes (1/2/X): ");
            String input = scanner.next();
            char[] enteredOutcomes = input.toCharArray();
            for (int gameCounter = 0; gameCounter < Constant.NUMBER_OF_GAMES; gameCounter++) {
                outcomes.add(OutcomeParser.getInstance().parse(String.valueOf(enteredOutcomes[gameCounter])));
            }
            String verifiedInput = input.replaceAll("1", "")
                    .replaceAll("2", "")
                    .replaceAll("X", "");
            isInputCorrect = verifiedInput.isEmpty();
        } while (!isInputCorrect);

        return outcomes;
    }

    private Optional<Round> getRoundByDate(LocalDate dateToSearch) {
        if (data.keySet().contains(dateToSearch)) {
            return Optional.of(data.get(dateToSearch));
        }
        return Optional.empty();
    }

    private LocalDate readDateToSearch() {
        LocalDate dateToSearch = LocalDate.now();
        boolean isDateCorrect;
        do {
            console.print("Enter date: ");
            try {
                String input = scanner.next();
                dateToSearch = LocalDate.parse(input, Constant.formatter);
                isDateCorrect = true;
            } catch (DateTimeParseException e) {
                isDateCorrect = false;
                error.println("Please enter the valid date to search in format: yyyy.MM.dd.");
            }
        } while (!isDateCorrect);

        return dateToSearch;
    }
}
