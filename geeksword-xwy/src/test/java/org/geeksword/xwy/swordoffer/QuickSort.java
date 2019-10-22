package org.geeksword.xwy.swordoffer;

public class QuickSort {

    //快排
    public static void quickSort(int a[], int left, int right) {
        if (left >= right)
            return;
        int i = left;
        int j = right;
        int key = a[left];
        while (i < j) {
            while (i < j && a[j] >= key)
                j--;
            if (i < j) {
                a[i] = a[j];
                i++;
            }
            while (i < j && a[i] < key)
                i++;
            if (i < j) {
                a[j] = a[i];
                j--;
            }
        }
        a[i] = key;
        quickSort(a, left, i - 1);
        quickSort(a, i + 1, right);
    }

    //冒泡
    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return;
    }

    public static void main(String[] args) {
        int arr[] = {8,6,7,5,1,4,3,9};
        //quickSort(arr,arr[0],arr.length);
        sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }

    }
}
