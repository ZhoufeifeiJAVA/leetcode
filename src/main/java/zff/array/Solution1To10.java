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

    /*
    34. Search for a Range

    Given an array of integers sorted in ascending order, find the starting and ending position of a given target value.

    Your algorithm's runtime complexity must be in the order of O(log n).

    If the target is not found in the array, return [-1, -1].

    For example,
    Given [5, 7, 7, 8, 8, 10] and target value 8,
    return [3, 4].
     */
    public int[] searchRange(int[] nums, int target) {
        int[] position = {-1, -1};
        int i = 0, j = nums.length - 1, k;
        while (i <= j) {
            k = (i + j) / 2;
            if (nums[k] == target && (k-1 < 0 || nums[k-1] < target)) {
                position[0] = k;
                break;
            }
            if (nums[k] >= target)
                j = k - 1;
            else
                i = k + 1;
        }
        i = 0;
        j = nums.length - 1;
        while (i <= j) {
            k = (i + j) / 2;
            if (nums[k] == target && (k+1 > nums.length - 1 || nums[k+1] > target)) {
                position[1]= k;
                break;
            }
            if (nums[k] <= target)
                i = k + 1;
            else
                j = k - 1;
        }
        return position;
    }

    /*
    You are given an n x n 2D matrix representing an image.

    Rotate the image by 90 degrees (clockwise).

    Note:
    You have to rotate the image in-place, which means you have to modify the input 2D matrix directly.
    DO NOT allocate another 2D matrix and do the rotation.
    */
    public void rotate(int[][] matrix) {
        int len = matrix.length;
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                if (i<=j && i+j<len-1) {
                    int tmp = matrix[i][j];
                    matrix[i][j] = matrix[len-1-j][i];
                    matrix[len-1-j][i] = matrix[len-1-i][len-1-j];
                    matrix[len-1-i][len-1-j] = matrix[j][len-1-i];
                    matrix[j][len-1-i] = tmp;
                }
            }
        }
    }
    /*
    54. Spiral Matrix
    Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int len = matrix.length;
        ArrayList<Integer> spiraledData = new ArrayList<Integer>();
        int len_row = matrix.length;
        if (len_row == 0) {
            return spiraledData;
        }
        int len_col = matrix[0].length;
        int num_iteration = Math.min(len_row-1, len_col-1) / 2 + 1;
        int start_i = 0;
        int start_j = 0;
        int len_i = len_row - 1;  // 分成四次迭代,这样实际每次的长度应该是边长-1;
        int len_j = len_col - 1;
        while (num_iteration > 0) {
            if (len_i == 0 && len_j == 0) {
                spiraledData.add(matrix[start_i][start_j]);
            }
            if (len_i !=0 && len_j != 0) {
                for (int i=0; i<len_j; i++) {
                    spiraledData.add(matrix[start_i][start_j+i]);
                }
                for (int i=0; i<len_i; i++) {
                    spiraledData.add(matrix[start_i+i][start_j+len_j]);
                }
                for (int i=0; i<len_j; i++) {
                    spiraledData.add(matrix[start_i+len_i][start_j+len_j-i]);
                }
                for (int i=0; i<len_i; i++) {
                    spiraledData.add(matrix[start_i+len_i-i][start_j]);
                }
            }
            if (len_i !=0 && len_j == 0) {
                for(int i=0; i<=len_i; i++) {
                    spiraledData.add(matrix[start_i+i][start_j]);
                }
            }
            if (len_i ==0 && len_j != 0) {
                for(int i=0; i<=len_j; i++) {
                    spiraledData.add(matrix[start_i][start_j+i]);
                }
            }
            start_i ++;
            start_j ++;
            len_i -= 2;
            len_j -= 2;
            num_iteration --;
        }
        return spiraledData;
    }
    /*
    55. Jump Game
     Given an array of non-negative integers, you are initially positioned at the first index of the array.

    Each element in the array represents your maximum jump length at that position.

    Determine if you are able to reach the last index.

    For example:
    A = [2,3,1,1,4], return true.

    A = [3,2,1,0,4], return false
    */

    public boolean canJump(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return true;
        }
        int indexAccessStart = 0;
        int indexAccessEnd = 0;
        int firstIdxCantAccess = 1;
        while (indexAccessStart <= indexAccessEnd) {
            if (nums[indexAccessStart] + indexAccessStart + 1 > firstIdxCantAccess) {
                firstIdxCantAccess = nums[indexAccessStart] + indexAccessStart + 1;
            }
            indexAccessStart ++;
            indexAccessEnd = firstIdxCantAccess - 1;
            if (firstIdxCantAccess > len - 1) {
                break;
            }
        }
        if (firstIdxCantAccess > len - 1) {
            return true;
        } else {
            return false;
        }
    }

    public List<Interval> merge (List < Interval > intervals){
        Collections.sort(intervals, new IntervalComparator());
        ArrayList<Interval> intervalsMerged = new ArrayList<Interval>();
        for (Interval interval : intervals) {
            if (intervalsMerged.isEmpty()) {
                intervalsMerged.add(interval);
            }
            int size = intervalsMerged.size();
            Interval lastInterval = intervalsMerged.get(size-1);
            if (lastInterval.end >= interval.start && lastInterval.end < interval.end) {
                lastInterval.end = interval.end;
            } else if (lastInterval.end < interval.start) {
                intervalsMerged.add(interval);
            }
        }
        return intervalsMerged;
    }

    public static void main(String[] args) {
        ArrayList<Interval> intervals = new ArrayList<Interval>();
        intervals.add(new Interval(8, 10));
        intervals.add(new Interval(15, 18));
        intervals.add(new Interval(1, 3));
        intervals.add(new Interval(2, 6));
        List<Interval> intervalsMerged = new Solution1To10().merge(intervals);
        for (Interval interval : intervalsMerged) {
            System.out.println(interval.start + ", " + interval.end);
        }
    }
}

class Interval {
    int start;
    int end;

    Interval() {
        start = 0;
        end = 0;
    }

    Interval(int s, int e) {
        start = s;
        end = e;
    }
}

class IntervalComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        Interval i1 = (Interval)o1;
        Interval i2 = (Interval)o2;
        return i1.start - i2.start;
    }
}


















