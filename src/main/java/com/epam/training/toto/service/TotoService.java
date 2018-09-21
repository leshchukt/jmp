package com.epam.training.toto.service;

import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;
import com.epam.training.toto.service.parser.CSVParser;
import com.epam.training.toto.service.parser.util.OutcomeParser;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TotoService {
    public static final Integer NUMBER_OF_GAMES = 14;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("uk", "UA"));

    private Map<LocalDate, Round> data;

    public TotoService() {
        try {
            data = new CSVParser().readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer getTheLargestPrize() {
        List<Integer> allPrizes = data.values()
                .stream()
                .map(round -> round.getHits())
                .flatMap(Collection::stream)
                .map(hit -> hit.getPrizeForTheGames())
                .collect(Collectors.toList());

        return Collections.max(allPrizes);
    }

    private List<Map<Outcome, Long>> getDistributionOfResultsForEachRound() {
        List<Map<Outcome, Long>> distributionForEachRound = new ArrayList<>(2 * data.size());
        data.values()
                .forEach(round -> distributionForEachRound.add(round.getOutcomes()
                        .stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                );
        return distributionForEachRound;
    }

    public void printTheLargestPrize() {
        System.out.println("======== The largest prize: " +
                numberFormat.format(getTheLargestPrize()) +
                " ========");
    }

    public void printDistributionOfTheResults() {
        System.out.println("\n======== The distribution of the results of each round ========");
        getDistributionOfResultsForEachRound()
                .forEach(round -> System.out.println("team #1 won: " +
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
        return String.format("%.2f ", amountOfVictories / (double) NUMBER_OF_GAMES) + "%";
    }

    public void printHitsAndAmountForTheWager() {
        System.out.println("\n======== The hits and amount for the wager ========");
        Optional<Round> round;
        do {
            LocalDate dateToSearch = readDateToSearch();
            round = getRoundByDate(dateToSearch);
        } while (!round.isPresent());

        List<Outcome> wager = readWager();
        int hitCounter = 0;
        int prize = 0;
        for (int gameCounter = 0; gameCounter < NUMBER_OF_GAMES; gameCounter++) {
            if (round.get().getOutcomes().get(gameCounter) == wager.get(gameCounter)) {
                hitCounter++;
            }
        }
        if (hitCounter > 9) {
            prize = round.get().getHits().get(NUMBER_OF_GAMES % hitCounter).getPrizeForTheGames();
        }
        System.out.println("Result: hits: " + hitCounter + ", amount: " + numberFormat.format(prize));
    }

    private List<Outcome> readWager() {
        List<Outcome> outcomes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean isInputCorrect;
        do {
            System.out.print("Enter outcomes (1/2/X): ");
            String input = scanner.next();
            char[] enteredOutcomes = input.toCharArray();
            for (int gameCounter = 0; gameCounter < NUMBER_OF_GAMES; gameCounter++) {
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
        Scanner scanner = new Scanner(System.in);
        LocalDate dateToSearch = LocalDate.now();
        boolean isDateCorrect;
        do {
            System.out.print("Enter date: ");
            try {
                dateToSearch = LocalDate.parse(scanner.next(), formatter);
                isDateCorrect = true;
            } catch (DateTimeParseException e) {
                isDateCorrect = false;
                System.out.println("Please enter the valid date to search in format: yyyy.MM.dd.");
            }
        } while (!isDateCorrect);
        return dateToSearch;
    }
}
