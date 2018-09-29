package com.epam.training.circularbuffer;

import com.epam.training.exception.NotEnoughSpaceException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class CircularBufferTest {
    private CircularBuffer<Integer> testingInstance;

    @Before
    public void setUp() {
        testingInstance = new CircularBuffer<>(5);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotPutInTheFullArray() {
        testingInstance.put(1);
        testingInstance.put(1);
        testingInstance.put(1);
        testingInstance.put(1);
        testingInstance.put(1);
        testingInstance.put(1);
    }

    @Test
    public void shouldGetTheTail() {
        testingInstance.put(1);
        testingInstance.put(2);
        assertEquals(1, (int) testingInstance.get());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotGetFromEmptyBuffer() {
        testingInstance.get();
    }

    @Test
    public void shouldConvertToObjectArray() {
        testingInstance.put(1);
        testingInstance.put(2);
        testingInstance.put(3);
        testingInstance.get();
        Object[] actual = testingInstance.toObjectArray();

        assertEquals(2, actual.length);
        assertEquals(2, actual[0]);
        assertEquals(3, actual[1]);
    }

    @Ignore
    @Test
    public void shouldConvertToArray() {
        testingInstance.put(1);
        testingInstance.put(2);
        testingInstance.put(3);
        testingInstance.get();
        Integer[] actual = testingInstance.toArray();

        assertEquals(2, actual.length);
        assertTrue(actual[0] == 2);
        assertTrue(actual[1] == 3);
    }

    @Test
    public void shouldConvertToList() {
        testingInstance.put(1);
        testingInstance.put(2);
        testingInstance.put(3);
        testingInstance.get();
        List<Integer> actual = testingInstance.asList();

        assertEquals(2, actual.size());
        assertTrue(actual.get(0) == 2);
        assertTrue(actual.get(1) == 3);
    }

    @Test
    public void shouldAddAll() throws NotEnoughSpaceException {
        testingInstance.addAll(Arrays.asList(2, 3, 4, 5));
        assertTrue(2 == testingInstance.get());
    }

    @Test(expected = NotEnoughSpaceException.class)
    public void shouldNotAddAnything() throws NotEnoughSpaceException {
        testingInstance.put(1);
        testingInstance.addAll(Arrays.asList(2, 3, 4, 5, 6));
    }

    @Test
    public void shouldSort() {
        testingInstance.put(2);
        testingInstance.put(1);
        testingInstance.put(4);
        testingInstance.put(3);
        testingInstance.sort(Comparator.naturalOrder());

        assertTrue(1 == testingInstance.get());
        assertTrue(2 == testingInstance.get());
        assertTrue(3 == testingInstance.get());
        assertTrue(4 == testingInstance.get());
    }

    @Test
    public void shouldBeEmpty() {
        assertTrue(testingInstance.isEmpty());
    }

    @Test
    public void shouldNotBeEmpty() {
        testingInstance.put(1);
        assertFalse(testingInstance.isEmpty());
    }

}