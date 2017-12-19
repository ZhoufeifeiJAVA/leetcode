package zff.divcon;

/**
 * Created by zhoufeifei on 12/15/17.
 */
public class DivideCon {
    /*
    240. Search a 2D Matrix II
    Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following
    properties:

    Integers in each row are sorted in ascending from left to right.
    Integers in each column are sorted in ascending from top to bottom.

    For example,

    Consider the following matrix:

    [
      [1,   4,  7, 11, 15],
      [2,   5,  8, 12, 19],
      [3,   6,  9, 16, 22],
      [10, 13, 14, 17, 24],
      [18, 21, 23, 26, 30]
    ]

    Given target = 5, return true.

    Given target = 20, return false.
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int rowLen = matrix.length;
        if (rowLen == 0) return false;
        int colLen = matrix[0].length;
        int col = 0;
        int row = rowLen - 1;
        while (row >= 0 && col < colLen) {
            if (matrix[row][col] == target) return true;
            if (matrix[row][col] < target) col ++;
            else row --;
        }
        return false;
    }
    /*
    215. Kth Largest Element in an Array
     */
    public int findKthLargest(int[] nums, int k) {
        return findKthLargest(nums, 0, nums.length - 1, k);
    }
    public int findKthLargest(int[] nums, int startIdx, int endIdx, int k) {
        int p = findPivot(nums, startIdx, endIdx);
        if (endIdx - p == k-1) return nums[p];
        else if (endIdx - p > k-1) return findKthLargest(nums, p+1, endIdx, k);
        else return findKthLargest(nums, startIdx, p-1, k-(endIdx-p+1));
    }
    private int findPivot(int[] nums, int startIdx, int endIdx) {
        if (nums.length == 1) return 0;
        int pivot = nums[startIdx];
        int p = startIdx;
        int q = startIdx;
        while (q < endIdx) {
            q ++;
            if (nums[q] <= pivot) {
                exchange(nums, p+1, q);
                exchange(nums, p, p+1);
                p ++;
            }
        }
        return p;
    }
    private void exchange(int[] nums, int p, int q) {
        int tmp = nums[p];
        nums[p] = nums[q];
        nums[q] = tmp;
    }

    /*
    169. Majority Element
    Given an array of size n, find the majority element. The majority element is the element that
    appears more than ⌊ n/2 ⌋ times.

    You may assume that the array is non-empty and the majority element always exist in the array.
     */
    private int[] findPivots(int[] nums, int startIdx, int endIdx) {
        if (nums.length == 1) return new int[]{0, 0};
        int pivot = nums[startIdx];
        int p1 = startIdx;
        int p2 = startIdx;
        int q = startIdx;
        while (q < endIdx) {
            q ++;
            if (nums[q] == pivot) {
                exchange(nums, p1+1, q);
                p1 ++;
            } else if (nums[q] < pivot) {
                exchange(nums, p2, q);
                exchange(nums, p1+1, q);
                p1 ++;
                p2 ++;
            }
        }
        return new int[]{p2, p1};
    }
    private int majorityElement(int[] nums, int startIdx, int endIdx) {
        int[] pivots = findPivots(nums, startIdx, endIdx);
        // note the special case
        if (pivots[0] - startIdx == nums.length / 2 && endIdx - pivots[1] == nums.length / 2) {
            boolean leftFlag = true;
            boolean rightFlag = true;
            for (int i = 0; i < nums.length / 2 - 1; i++) {
                if (nums[startIdx + i] != nums[startIdx + i + 1]) leftFlag = false;
                if (nums[endIdx - i] != nums[endIdx -i - 1]) rightFlag = false;
            }
            if (leftFlag) return nums[startIdx];
            if (rightFlag) return nums[endIdx];
        }
        if (pivots[1] - pivots[0] + 1 > nums.length / 2) return nums[pivots[0]];
        if (endIdx - pivots[1] >= nums.length / 2) return majorityElement(nums, pivots[1]+1, endIdx);
        else return majorityElement(nums, startIdx, pivots[0]-1);
    }

    public int majorityElement(int[] nums) {
        return majorityElement(nums, 0, nums.length-1);
    }

    /*
    53. Maximum Subarray
    Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
    For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
    the contiguous subarray [4,-1,2,1] has the largest sum = 6.
     */
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) return nums[0];
        int minSum = Math.min(nums[0], 0);
        int maxSum = nums[0];
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum += nums[i];
            maxSum = Math.max(maxSum, sum - minSum);
            minSum = Math.min(minSum, sum);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        String testFunc = "majorityElement";
        if (testFunc == "majorityElement") {
            int[] nums = new int[]{6,5,5};
            int majorNum = new DivideCon().majorityElement(nums);
            System.out.println(majorNum);
        }
        if (testFunc == "findKthLargest") {
            int[] nums = new int[]{1, 2};
            new DivideCon().findKthLargest(nums, 1);
            System.out.println("hello");
        }
    }
}
