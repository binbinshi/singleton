package com.kobin.singleton.singleton;

/**
 * Created by @author shibinbin on 2017/12/8.
 */

public class NormalSingleton {

    private NormalSingleton instance = null;

    public NormalSingleton getInstance(){
        if (instance == null) {
           instance = new NormalSingleton();
        }
        return instance;
    }


    public static void main(String[] args) {
        int[] result = new int[2];
        result[0]=1;
        result[1]=2;
        System.out.println(result.length);

    }
    public int[] twoSum(int[] nums, int target) {
        int[] result = {};
        for(int i = 0; i < nums.length; i++){
            int first = nums[i];
            for(int j = nums.length - 1; j > i; j-- ){
                int second = nums[j];
                if((i != j) && (first + second == target)){
                    result[0]=i;
                    result[1]=j;
                }
            }
        }
        return result;
    }

}
