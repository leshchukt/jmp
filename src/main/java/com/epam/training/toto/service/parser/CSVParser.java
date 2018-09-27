
package com.epam.training.toto.service.parser;

import com.epam.training.toto.domain.Round;
import com.epam.training.toto.util.exception.DataCanNotBeReadException;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CSVParser {
    public static final String INPUT_DATA_PATH = "/toto.csv";

    public Map<LocalDate, Round> readData() throws DataCanNotBeReadException {
        try (CSVReader reader =
                     new CSVReader(new FileReader(CSVParser.class.getResource(INPUT_DATA_PATH).getFile()), ';')) {
            Map<LocalDate, Round> data = new HashMap<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                final Round round = RoundParser.getInstance().parse(line);
                data.put(round.getDate(), round);
            }
            return data;
        } catch (IOException e) {
            throw new DataCanNotBeReadException(e);
        }
    }
}
