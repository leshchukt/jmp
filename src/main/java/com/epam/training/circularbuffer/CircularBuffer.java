package com.epam.training.circularbuffer;

import com.epam.training.exception.NotEnoughSpaceException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CircularBuffer<T> {
    private Object[] data;
    private int initialCapacity;
    private int headPosition = 0;
    private int tailPosition = 0;
    private int size = 0;

    public CircularBuffer(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.data = new Object[initialCapacity];
    }

    public void put(T t) {
        if (isFull()) {
            throw new RuntimeException("The buffer is full!");
        }
        data[headPosition] = t;
        size++;
        headPosition = nextIndexInACircle(headPosition);
    }

    public T get() {
        if (tailPosition == headPosition && !isFull()) {
            throw new RuntimeException("The buffer is empty!");
        }
        T t = (T) data[tailPosition];
        tailPosition = nextIndexInACircle(tailPosition);
        size--;
        return t;
    }

    public Object[] toObjectArray(){
        if (isEmpty()) {
            return new Object[0];
        }
        Object[] array = new Object[size];
        int indexOfData = tailPosition;
        for (int newIndex = 0; newIndex < size; newIndex++){
            array[newIndex] = data[indexOfData];
            indexOfData = nextIndexInACircle(indexOfData);
        }
        return array;
    }

    public T[] toArray() {
        return null;
    }

    public List<T> asList() {
        return (List<T>) Arrays.asList(toObjectArray());
    }

    public void addAll(List<? extends T> toAdd) throws NotEnoughSpaceException{
        if (toAdd.size() > initialCapacity - size) {
            throw new NotEnoughSpaceException("Not enough space in the buffer to add the list!");
        }
        for (T t : toAdd) {
            put(t);
        }
    }

    public void sort(Comparator<? super T> comparator) {
        T[] filledData = (T[]) Arrays.copyOf(data, size);
        Arrays.sort(filledData, comparator);
        for (int index = 0; index < size; index++) {
            data[index] = filledData[index];
        }
        tailPosition = 0;
        headPosition = size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return headPosition == tailPosition && !isEmpty();
    }

    private int nextIndexInACircle(int position) {
        if (position == initialCapacity - 1) {
            return 0;
        }
        return position + 1;
    }
}
