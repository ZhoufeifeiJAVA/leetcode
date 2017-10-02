package zff.array;

import java.util.*;

/**
 * Created by zhoufeifei on 10/1/17.
 */
public class Solution1To10 {
    public int maxArea(int[] height) {
        int maxArea = 0;
        int i = 0, j = height.length - 1;
        while (i < j) {
            maxArea = Math.max(maxArea, Math.min(height[i], height[j]) * (j - i));
            if (height[i] < height[j]) {
                i ++;
            } else {
                j --;
            }
        }
        return maxArea;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        HashSet<List<Integer>> zeros = new HashSet<List<Integer>>();
        for (int first = 0; first < nums.length - 2; first ++) {
            if (nums[first] > 0) {
                break;
            }
            int sum_2and3 = 0 - nums[first];
            int second = first + 1;
            int third = nums.length - 1;
            while (second < third) {
                if (nums[second] + nums[third] < sum_2and3) {
                    second ++;
                } else if (nums[second] + nums[third] > sum_2and3) {
                    third --;
                } else {
                    zeros.add(Arrays.asList(nums[first], nums[second], nums[third]));
                    while(second < third && nums[second] == nums[second + 1]) second ++;
                    while(second < third && nums[third] == nums[third - 1]) third --;
                    second ++;
                    third --;
                }
            }
        }
        return new ArrayList(zeros);
    }

    /*Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

    (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

    You are given a target value to search. If found in the array return its index, otherwise return -1.

    You may assume no duplicate exists in the array.*/
    public int search(int[] nums, int target, int start, int end) {
        if (end < start) return -1;
        int mid = (start + end) / 2;
        if (nums[mid] == target) return mid;
        if (nums[start] < nums[end]) {
            if (nums[mid] < target)
                return search(nums, target, mid + 1, end);
            else
                return search(nums, target, start, mid - 1);
        } else {
            if (nums[mid] >= nums[start])
                if (nums[mid] > target && nums[start] <= target)
                    return search(nums, target, start, mid - 1);
                else
                    return search(nums, target, mid+1, end);
            else
                if (nums[mid] < target && nums[end] >= target)
                    return search(nums, target, mid+1, end);
                else
                    return search(nums, target, start, end-1);
        }
    }

    public int search(int[] nums, int target) {
        return search(nums, target, 0, nums.length-1);
    }

    public static void main(String[] args) {
        int[] nums = {4, 5};
        System.out.println(new Solution1To10().search(nums, 7));
    }
}
