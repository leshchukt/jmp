
package com.epam.training.toto.service.parser;

import com.epam.training.toto.domain.Round;
import com.epam.training.toto.service.parser.util.Parser;
import com.epam.training.toto.service.parser.util.RoundParser;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CSVParser {
    public static final String INPUT_DATA_PATH = "/toto.csv";

    private Parser<Round> roundParser = new RoundParser();

    public Map<LocalDate, Round> readInfo() {
        try (CSVReader reader =
                     new CSVReader(new FileReader(CSVParser.class.getResource(INPUT_DATA_PATH).getFile()), ';')) {
            Map<LocalDate, Round> info = new HashMap<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                final Round round = roundParser.parse(line);
                info.put(round.getDate(), round);
            }
            return info;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
