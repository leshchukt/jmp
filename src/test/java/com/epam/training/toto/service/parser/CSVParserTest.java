package com.epam.training.toto.service.parser;

import com.epam.training.toto.domain.Round;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

import static junit.framework.Assert.*;

public class CSVParserTest {
    private CSVParser testingInstance = new CSVParser();

    @Test
    public void readInfo() {
        Map<LocalDate, Round> actual = testingInstance.readInfo();
        assertFalse(actual.isEmpty());
    }

}