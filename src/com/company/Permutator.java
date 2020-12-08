package com.company;

//this class code is a modified version of
//chenchen98's code on geeksforgeeks.org titled
//"Iterative approach to print all permutations of an Array"
//last updated on "17-06-2019" (dd/mm/year)
public class Permutator {
    private final int[] array;
    private int[] indexes;
    private int increase;

    public Permutator(int[] array) {
        this.array = array;
        this.increase = -1;
        this.indexes = new int[this.array.length];
    }

    public int[] getFirst() {
        indexes = new int[array.length];
        for (int i = 0; i < indexes.length; i++)
            indexes[i] = i;
        increase = 0;
        return this.output();
    }

    public boolean hasNext() {
        return increase != (indexes.length - 1);
    }

    public int[] getNext() {
        if (increase == 0) {
            swapElements(increase, increase + 1);
            increase++;
            while (increase < indexes.length - 1
                    && indexes[increase] > indexes[increase + 1]) {
                increase++;
            }
        } else {
            if (indexes[increase+1] > indexes[0])
                swapElements(increase + 1, 0);
            else {
                int start = 0;
                int end = increase;
                int mid = (start + end) / 2;
                int tVal = indexes[increase + 1];
                while (!(indexes[mid] < tVal && indexes[mid - 1] > tVal)) {
                    if (indexes[mid] < tVal)
                        end = mid - 1;
                    else
                        start = mid + 1;
                    mid = (start + end) / 2;
                }
                swapElements(increase + 1, mid);
            }
            for (int i = 0; i <= increase / 2; i++)
                swapElements(i, increase - i);

            increase = 0;
        }
        return output();
    }

    private int[] output() {
        int[] result = new int[indexes.length];
        for (int i = 0; i < indexes.length; i++)
            result[i] = array[indexes[i]];
        return result;
    }

    private void swapElements(int index1, int index2) {
        int tmp = indexes[index1];
        indexes[index1] = indexes[index2];
        indexes[index2] = tmp;
    }
}
