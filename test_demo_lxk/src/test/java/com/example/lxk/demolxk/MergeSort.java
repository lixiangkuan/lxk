package com.example.lxk.demolxk;

public class MergeSort {

    //　private　static　long　sum　=　0;

    /**
     * 　*　<pre>
     * 　*　二路归并
     * 　*　原理：将两个有序表合并和一个有序表
     * 　*　</pre>
     * 　*
     * 　*　@param　a
     * 　*　@param　s
     * 　*　第一个有序表的起始下标
     * 　*　@param　m
     * 　*　第二个有序表的起始下标
     * 　*　@param　t
     * 　*　第二个有序表的结束小标
     * 　*
     */
    private static void merge(int[] a, int s, int m, int t) {
        int[] tmp = new int[t - s + 1];
        int i = s, j = m, k = 0;
        while (i < m && j <= t) {
            if (a[i] <= a[j]) {
                tmp[k] = a[i];
                k++;
                i++;
            } else {
                tmp[k] = a[j];
                j++;
                k++;
            }
        }
        while (i < m) {
            tmp[k] = a[i];
            i++;
            k++;
        }
        while (j <= t) {
            tmp[k] = a[j];
            j++;
            k++;
        }
        System.arraycopy(tmp, 0, a, s, tmp.length);
    }

    /**
     * 　*
     * 　*　@param　a
     * 　*　@param　s
     * 　*　@param　len
     * 　*　每次归并的有序集合的长度
     */
    public static void mergeSort(int[] a, int s, int len) {
        int size = a.length;
        int mid = size / (len << 1);
        int c = size & ((len << 1) - 1);
        //　-------归并到只剩一个有序集合的时候结束算法-------//
        if (mid == 0)
            return;
        //　------进行一趟归并排序-------//
        for (int i = 0; i < mid; ++i) {
            s = i * 2 * len;
            merge(a, s, s + len, (len << 1) + s - 1);
        }
        //　-------将剩下的数和倒数一个有序集合归并-------//
        if (c != 0)
            merge(a, size - c - 2 * len, size - c, size - 1);
        //　-------递归执行下一趟归并排序------//
        mergeSort(a, 0, 2 * len);
    }

    /**
     *插入排序
     *@paramarr
     *@return
     */
    private static int[] insertSort(int[]arr){
        if(arr == null || arr.length < 2){
            return arr;
        }
        for(int i=1;i<arr.length;i++){
            for(int j=i;j>0;j--){
                if(arr[j]<arr[j-1]){
                    //TODO:
                    int temp=arr[j];
                    arr[j]=arr[j-1];
                    arr[j-1]=temp;
                }else{
                    //接下来是无用功
                    break;
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] a = new int[]{4, 3, 6, 1, 2, 5,7,8,9,24,425,66,643,6432,6643,65475,754,54,2,543,33};
        mergeSort(a, 0, 1);
        for (int i = 0; i < a.length; ++i) {
            System.out.print(a[i] + "　");
        }
        insertSort(a);
        for (int i = 0; i < a.length; ++i) {
            System.out.print(a[i] + "　");
        }
    }
}
