package com.epam.training.toto;

import com.epam.training.toto.service.TotoService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TotoService service = new TotoService(scanner);

            service.initData();

            service.printTheLargestPrize();
            service.printDistributionOfTheResults();
            service.printHitsAndAmountForTheWager();

        }
    }
}
