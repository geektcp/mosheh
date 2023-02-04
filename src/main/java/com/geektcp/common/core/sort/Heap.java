package com.geektcp.common.core.sort;

/**
 * @author geektcp on 2019/9/23.
 */
public class Heap<T extends Comparable<T>> {

    private T[] heapArray;
    private int seq = 0;

    public Heap(int maxSeq) {
        this.heapArray = (T[]) new Comparable[maxSeq + 1];
    }

    public boolean isEmpty() {
        return seq == 0;
    }

    public int size() {
        return seq;
    }

    private boolean less(int i, int j) {
        return heapArray[i].compareTo(heapArray[j]) < 0;
    }

    private void swap(int i, int j) {
        T t = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = t;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            swap(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= seq) {
            int j = 2 * k;
            if (j < seq && less(j, j + 1))
                j++;
            if (!less(k, j))
                break;
            swap(k, j);
            k = j;
        }
    }

    public void insert(T v) {
        heapArray[++seq] = v;
        swim(seq);
    }

    public T delMax() {
        T max = heapArray[1];
        swap(1, seq--);
        heapArray[seq + 1] = null;
        sink(1);
        return max;
    }
}
