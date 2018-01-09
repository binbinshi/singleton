package com.kobin.singleton.sort;

/**
 * 快速排序
 * 思路：每次插入之前，和之前插入的所有值进行比对，并将比对的值放入特定的位置
 * 插入的值和之前插入的所有值进行比对，并将
 * Created by @author shibinbin on 2017/12/12.
 */

public class QuickSort {

   public static int[] quickSort(int[] sortInt){

       for (int i = 0; i < sortInt.length; i++) {
           int j = 0;
           int temp = sortInt[i];
           for (j =i - 1; j >= 0 && temp < sortInt[j]; j--) {
               sortInt[j+1] = sortInt[j];
           }
           sortInt[j+1] = temp;
       }
       return sortInt;
   }

    public static void main(String[] args) {
        int[] sortInt = {2,5,1,3,10,30,8,6,6,3,4,2,1};
        System.out.println(getString(sortInt));
        int[] sortedInt = quickSort(sortInt);
        System.out.println(getString(sortedInt));
    }

    private static String  getString(int[] ints){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ints.length ; i++) {
            sb.append(ints[i]).append(",");
        }
        return sb.toString();
    }
}
