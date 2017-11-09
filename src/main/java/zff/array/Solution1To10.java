package zff.array;

import java.lang.reflect.Array;
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

    /*
    62. Unique Paths
    A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

    The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

    How many possible unique paths are there?

    这道题目主要陷阱在于要注意不能用阶乘，阶乘太容易越界，超过整数的范围了
     */
    public int uniquePaths(int m, int n) {
        return combData(m+n-2, m-1);
    }
    public int combData(int m, int n) {
        int[][] comDataArr = new int[m+1][m+1];
        for (int i=0; i<=m; i++) {
//         　这个地方还可以改进，因为第ｉ行只对第i+1行有帮助,第i+1行也只需要第i行的数据，不需要第i+1行本身的数据
            for (int j=0; j<=i; j++) {
                if (j == 0 || j == i) {
                    comDataArr[i][j] = 1;
                } else {
                    comDataArr[i][j] = comDataArr[i-1][j-1] + comDataArr[i-1][j];
                }
            }
        }
        return comDataArr[m][n];
    }

    /*
    73. Set Matrix Zeroes
    Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in place.
     */
    public void setZeroes(int[][] matrix) {
        int row_len = matrix[0].length;
        int col_len = matrix.length;
        boolean[] row_zeros = new boolean[row_len];
        boolean[] col_zeros = new boolean[col_len];
        /*
        我的解法的空间复杂度为O(m+n),如果要求空间复杂度为O(1),不能新建数组,可以考虑将第一行第一列作为flag
         */
        for (int i=0; i<col_len; i++) {
            for (int j=0; j<row_len; j++) {
                if (matrix[i][j] == 0) {
                    row_zeros[j] = true;
                    col_zeros[i] = true;
                }
            }
        }
        for (int i=0; i<col_len; i++) {
            for (int j=0; j<row_len; j++) {
                if (row_zeros[j] == true || col_zeros[i] == true) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    /*
    75. Sort Colors
    Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent,
    with the colors in the order red, white and blue.

    Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
     */
    public void sortColors(int[] nums) {
        sortColors2Value(nums, 0);
        sortColors2Value(nums, 1);
    }
    public void sortColors2Value(int[] nums, int threshold) {
        int start = 0;
        int end = nums.length - 1;
        int tmp;
        while (start < end) {
            while(start < end && nums[start] <= threshold)
                start ++;
            while(start < end && nums[end] > threshold)
                end --;
            tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
        }
    }

    /*
     Given a set of distinct integers, nums, return all possible subsets (the power set).

    Note: The solution set must not contain duplicate subsets.

    For example,
    If nums = [1,2,3], a solution is:
     */
    public List<List<Integer>> subsets(int[] nums) {
        return subsets(nums, nums.length);
    }
    public List<List<Integer>> subsets(int[] nums, int len) {
        if (len == 0) {
            ArrayList<List<Integer>> al = new ArrayList<List<Integer>>();
            al.add(new ArrayList<Integer>());
            return al;
        }
        List<List<Integer>> shorterList = subsets(nums, len-1);
        for(int i=0; i<Math.pow(2, len-1); i++) {
            ArrayList<Integer> newList = new ArrayList<Integer>();
            Iterator<Integer> iterator = shorterList.get(i).iterator();
            while (iterator.hasNext()) {
                newList.add(iterator.next());
            }
            newList.add(nums[len-1]);
            shorterList.add(newList);
        }
        return shorterList;
    }
    /*
    79. Word Search
    Given a 2D board and a word, find if the word exists in the grid.

    The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.

    For example,
    Given board =

        [
        ['A','B','C','E'],
        ['S','F','C','S'],
        ['A','D','E','E']
        ]

    word = "ABCCED", -> returns true,
    word = "SEE", -> returns true,
    word = "ABCB", -> returns false.

    */

    /*
    这种使用递归的方式时间超过了预期(85 / 87 test cases passed).,我的做法类似于广度优先,需要使用深度优先算法
     */
    public boolean exist(char[][] board, String word) {
        List<List<Index2D>> l = exist(board, word, word.length());
        if (l.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<List<Index2D>> exist(char[][] board, String word, int len) {
        int row = board.length;
        int col = board[0].length;
        char[] wordArray = word.toCharArray();
        ArrayList<List<Index2D>> al = new ArrayList<List<Index2D>>();
        if (len == 1) {
            for (int i=0; i<row; i++) {
                for (int j=0; j<col; j++) {
                    if (board[i][j] == wordArray[0]) {
                        ArrayList<Index2D> l = new ArrayList<Index2D>();
                        l.add(new Index2D(i, j));
                        al.add(l);
                    }
                }
            }
            return al;
        }
        List<List<Index2D>> alOneShorter = exist(board, word, len-1);
        Iterator iterator = alOneShorter.iterator();
        while (iterator.hasNext()) {
            List<Index2D> l = (List<Index2D>)iterator.next();
            Index2D last = l.get(l.size() - 1);
            int rowLast = last.row;
            int colLast = last.col;
            // 考察l的最后一个元素的前后左右
            if (rowLast + 1 <= row - 1 && board[rowLast+1][colLast] == wordArray[len-1]) {
                Iterator<Index2D> iteratorL = l.iterator();
                boolean indexUsed = false;
                while (iteratorL.hasNext()) {
                    Index2D oldIndex = iteratorL.next();
                    if (oldIndex.row == rowLast + 1 && oldIndex.col == colLast) {
                        indexUsed = true;
                    }
                }
                if (!indexUsed) {
                    int justToBeutifulCode = 0;
                    Index2D newIndex = new Index2D(rowLast+1, colLast);
                    // 复制l　再添加
                    ArrayList<Index2D> newL = new ArrayList<Index2D>();
                    for (int i=0; i<l.size(); i++) {
                        newL.add(l.get(i));
                    }
                    newL.add(newIndex);
                    al.add(newL);
                }
            }
            if (rowLast - 1 >= 0 && board[rowLast-1][colLast] == wordArray[len-1]) {
                Iterator<Index2D> iteratorL = l.iterator();
                boolean indexUsed = false;
                while (iteratorL.hasNext()) {
                    Index2D oldIndex = iteratorL.next();
                    if (oldIndex.row == rowLast - 1 && oldIndex.col == colLast) {
                        indexUsed = true;
                    }
                }
                if (!indexUsed) {
                    int justToBeutifulCode = 1;
                    Index2D newIndex = new Index2D(rowLast-1, colLast);
                    // 复制l　再添加
                    ArrayList<Index2D> newL = new ArrayList<Index2D>();
                    for (int i=0; i<l.size(); i++) {
                        newL.add(l.get(i));
                    }
                    newL.add(newIndex);
                    al.add(newL);
                }
            }
            if (colLast - 1 >= 0 && board[rowLast][colLast-1] == wordArray[len-1]) {
                Iterator<Index2D> iteratorL = l.iterator();
                boolean indexUsed = false;
                while (iteratorL.hasNext()) {
                    Index2D oldIndex = iteratorL.next();
                    if (oldIndex.row == rowLast && oldIndex.col == colLast - 1) {
                        indexUsed = true;
                    }
                }
                if (!indexUsed) {
                    Index2D newIndex = new Index2D(rowLast, colLast-1);
                    // 复制l　再添加
                    ArrayList<Index2D> newL = new ArrayList<Index2D>();
                    for (int i=0; i<l.size(); i++) {
                        newL.add(l.get(i));
                    }
                    newL.add(newIndex);
                    al.add(newL);
                }

            }
            if (colLast + 1 <= col - 1 && board[rowLast][colLast+1] == wordArray[len-1]) {
                Iterator<Index2D> iteratorL = l.iterator();
                boolean indexUsed = false;
                while (iteratorL.hasNext()) {
                    Index2D oldIndex = iteratorL.next();
                    if (oldIndex.row == rowLast && oldIndex.col == colLast + 1) {
                        indexUsed = true;
                    }
                }
                if (!indexUsed) {
                    Index2D newIndex = new Index2D(rowLast, colLast+1);
                    // 复制l　再添加
                    ArrayList<Index2D> newL = new ArrayList<Index2D>();
                    for (int i=0; i<l.size(); i++) {
                        newL.add(l.get(i));
                    }
                    newL.add(newIndex);
                    al.add(newL);
                }
            }
        }
        return al;
    }

    /*
    105. Construct Binary Tree from Preorder and Inorder Traversal
    Given preorder and inorder traversal of a tree, construct the binary tree.
    Note:
    You may assume that duplicates do not exist in the tree.
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, inorder, 0, preorder.length-1, 0, inorder.length-1);
    }

    public int indexOf(int[] arr, int target) {
        for (int i=0; i<arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
    public TreeNode buildTree(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {
        if (preStart > preEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preStart]);
        int rootIndexInorder = indexOf(inorder, root.val);
        root.left = buildTree(preorder, inorder, preStart+1, preStart+(rootIndexInorder-inStart), inStart, rootIndexInorder-1);
        root.right = buildTree(preorder, inorder, preStart+(rootIndexInorder-inStart)+1, preEnd, rootIndexInorder+1, inEnd);
        return root;
    }

    /*
    152. Maximum Product Subarray
     Find the contiguous subarray within an array (containing at least one number) which has the largest product.


    For example, given the array [2,3,-2,4],
    the contiguous subarray [2,3] has the largest product = 6.
     */
    public int maxProduct(int[] nums) {
        int start = 0;
        int len = nums.length;
        TreeSet<Integer> maxs = new TreeSet<Integer>();
        int lastZeroIdx = -1;
        for (int i=0; i<len; i++) {
            if (nums[i] == 0) {
                maxs.add(0);
                if (i-1 >= start) {
                    maxs.add(maxNonZerosProduct(nums, start, i-1));
                }
                start = i + 1;
                lastZeroIdx = i;
            }
        }
        if (lastZeroIdx != -1 && lastZeroIdx < len - 1) {
            maxs.add(maxNonZerosProduct(nums, lastZeroIdx+1,len-1));
        }
        if (lastZeroIdx == -1) {
            maxs.add(maxNonZerosProduct(nums, 0, len-1));
        }
        return maxs.last();
    }

    public int maxNonZerosProduct(int[] nums, int start, int end) {
        if (end == start) {
            return nums[start];
        }
        int negCnt = 0;
        int firstNegIdx = -1;
        int lastNegIdx = -1;
        int max;
        for (int i=start; i<=end; i++) {
            if (nums[i] < 0) {
                negCnt ++;
                if (firstNegIdx == -1) {
                    firstNegIdx = i;
                }
                lastNegIdx = i;
            }
        }
        if (negCnt % 2 == 0) {
            max = 1;
            for (int i = start; i <= end; i++) {
                max = max * nums[i];
            }
            return max;
        } else {
            int leftmax = 1;
            int rightmax = 1;
            for (int i=start; i<=end; i++) {
                if (i < lastNegIdx) {
                    leftmax = leftmax * nums[i];
                }
                if (i > firstNegIdx) {
                    rightmax = rightmax * nums[i];
                }
            }
            max = Math.max(leftmax, rightmax);
        }
        return max;
    }
    /*
    这是grandyang的解法
    是使用动态规划来处理的，具体请看笔记本上的笔记
     */
    int maxProductV2(int[] nums) {
        int len = nums.length;
        int minPre = nums[0];
        int maxPre = nums[0];
        int max = nums[0];
        for (int i=1; i<len; i++) {
            int minPreNew = Math.min(Math.min(minPre * nums[i], maxPre * nums[i]), nums[i]);
            int maxPreNew = Math.max(Math.max(minPre * nums[i], maxPre * nums[i]), nums[i]);
            minPre = minPreNew;
            maxPre = maxPreNew;
            max = Math.max(max, maxPre);
        }
        return max;
    }

    /*
    162. Find Peak Element
    A peak element is an element that is greater than its neighbors.

    Given an input array where num[i] ≠ num[i+1], find a peak element and return its index.

    The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.

    You may imagine that num[-1] = num[n] = -∞.

    For example, in array [1, 2, 3, 1], 3 is a peak element and your function should return the index number 2.

    click to show spoilers.
     */
    public int findPeakElement(int[] nums) {
        int len = nums.length;
        boolean preSmaller = true;
        for (int i=0; i<len-1; i++) {
            if (nums[i] > nums[i + 1]) {
                if (preSmaller) {
                    return i;
                }
                preSmaller = false;
            } else {
                preSmaller = true;
            }
        }
        if (preSmaller) {
            return len-1;
        }
        return -1;
    }
    /*
    238. Product of Array Except Self
    Given an array of n integers where n > 1, nums, return an array output such that output[i] is equal to the product of all the elements of
    nums except nums[i].

    Solve it without division and in O(n).

    For example, given [1,2,3,4], return [24,12,8,6].

    Follow up:
    Could you solve it with constant space complexity? (Note: The output array does not count as extra space for the purpose of space complexity analysis.)
    */
    /*
    我的这个解法包含了除法，它题目的要求是不包含除法
     */
    public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        int zeroCnt = 0;
        int zeroIdx = -1;
        int nonZeroProduct = 1;
        for (int i=0; i<len; i++) {
            if (nums[i] == 0) {
                zeroCnt++;
                zeroIdx = i;
            } else {
                nonZeroProduct *= nums[i];
            }
        }
        for (int i=0; i<len; i++) {
            if (zeroCnt > 1) {
                nums[i] = 0;
            } else if (zeroCnt == 1) {
                if (i == zeroIdx) {
                    nums[i] = nonZeroProduct;
                } else {
                    nums[i] = 0;
                }
            } else {
                nums[i] = nonZeroProduct / nums[i];
            }
        }
        return nums;
    }

    public int[] productExceptSelfV2(int[] nums) {
        int len = nums.length;
        int[] product = new int[len];
        product[len-1] = nums[len-1];
        for (int i=len-2; i>=0; i--) {
            product[i] = product[i+1] * nums[i];
        }
        int preProduct = 1;
        for (int i=0; i<len-1; i++) {
            product[i] = preProduct * product[i+1];
            preProduct *= nums[i];
        }
        product[len-1] = preProduct;
        return product;
    }

    public static void main(String[] args) {

       int[] maxProduct = new Solution1To10().productExceptSelfV2(new int[]{1, 2, 3, 7, 2});
       System.out.println(maxProduct);

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

class Index2D {
    public int row;
    public int col;
    public boolean visited;
    Index2D(int row, int col, boolean visited) {
        this.row = row;
        this.col = col;
        this.visited = visited;
    }
    Index2D(int row, int col) {
        this.row = row;
        this.col = col;
        this.visited = false;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
 }
















