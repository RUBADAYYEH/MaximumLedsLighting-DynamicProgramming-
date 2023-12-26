package com.example.maxledlightingproject;

import java.util.LinkedList;
import java.util.stream.Stream;

public class CustomHashSet {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private LinkedList<Integer>[] nodes;
    private int size;

    public CustomHashSet() {
        this(DEFAULT_CAPACITY);
    }

    public CustomHashSet(int initialCapacity) {
        nodes = new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            nodes[i] = new LinkedList<>();
        }
        size = 0;
    }

    public void add(Integer element) {
        if (!contains(element)) {
            int index = getIndex(element);
            nodes[index].add(element);
            size++;

            // Check if rehashing is needed
            if ((double) size / nodes.length > LOAD_FACTOR) {
                rehash();
            }
        }
    }

    public boolean contains(Integer element) {
        int index = getIndex(element);
        return nodes[index].contains(element);
    }

    public void remove(Integer element) {
        int index = getIndex(element);
        nodes[index].remove(element);
        size--;
    }

    public int size() {
        return size;
    }

    private int getIndex(Integer element) {
        int hashCode = element.hashCode();
        return Math.abs(hashCode) % nodes.length;
    }

    private void rehash() {
        LinkedList<Integer>[] oldNodes = nodes;
        nodes = new LinkedList[2 * oldNodes.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new LinkedList<>();
        }
        size = 0;

        for (LinkedList<Integer> node : oldNodes) {
            for (Integer element : node) {
                add(element);
            }
        }
    }
    public void clear() {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].clear();
        }
        size = 0;
    }

    public Stream<Integer> stream() {
        return Stream.of(nodes)
                .flatMap(LinkedList::stream);
    }
}
