package com.epam.training.toto;

import com.epam.training.toto.service.TotoService;

public class App {
    public static void main(String[] args) {
        TotoService service = new TotoService();

        service.printTheLargestPrize();
        service.printDistributionOfTheResults();
        service.printHitsAndAmountForTheWager();
    }
}
