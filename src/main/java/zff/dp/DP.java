package zff.dp;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.Arrays;
import java.util.Comparator;

import static sun.swing.MenuItemLayoutHelper.max;

/**
 * Created by zhoufeifei on 12/26/17.
 */
public class DP {
    /*
    746. Min Cost Climbing Stairs
     */
    public int minCostClimbingStairs(int[] cost) {
        int len = cost.length;
        if (len == 0) return 0;
        if (len == 1) return cost[0];
        if (len == 2) return Math.min(cost[0], cost[1]);
        int[] minCost = new int[len];
        minCost[0] = 0;
        minCost[1] = 0;
        for (int i = 2; i < len; i++) {
            minCost[i] = Math.min(minCost[i-1] + cost[i-1], minCost[i-2] + cost[i-2]);
        }
        return Math.min(minCost[len-1] + cost[len-1], minCost[len-2] + cost[len-2]);
    }
    /*
    121. Best Time to Buy and Sell Stock
    限定交易次数只能为１次
     */
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len <= 1) return 0;
        int minPrice = prices[0];
        int maxBenifit = 0;
        for (int i = 1; i < len; i++) {
            maxBenifit = Math.max(maxBenifit, prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }
        return maxBenifit;
    }
    /*
    70. Climbing Stairs
    计算到终点共有多少种方式
     */
    public int climbStairs(int n) {
        if (n == 1) return 1;
        int[] methodKinds = new int[n];
        methodKinds[0] = 1;
        methodKinds[1] = 2;
        for (int i = 2; i < n; i++) {
           methodKinds[i] = methodKinds[i-1] + methodKinds[i-2];
        }
        return methodKinds[n-1];
    }

    /*
    198. House Robber
     */
    public int rob(int[] nums) {
        int len = nums.length;
        if (len == 0) return 0;
        int[] noStolen = new int[len];
        int[] stolen = new int[len];
        noStolen[0] = 0;
        stolen[0] = nums[0];
        int maxPre = nums[0];
        for (int i = 1; i < len; i++) {
            noStolen[i] = maxPre;
            stolen[i] = noStolen[i-1] + nums[i];
            maxPre = Math.max(stolen[i], maxPre);
        }
        return maxPre;
    }
    /*
    338. Counting Bits
    求１到ｎ的整数二进制表示中１的个数
     */
    public int[] countBits(int num) {
        if (num == 0) return new int[]{0};
        if (num == 1) return new int[]{0, 1};
        int[] oneNum = new int[num + 1];
        int twoPower = 2;
        oneNum[0] = 0;
        oneNum[1] = 1;
        for (int i = 2; i < num + 1; i++) {
            if (i == twoPower) twoPower *= 2;
            int mapIdx = i - twoPower / 2;
            oneNum[i] = oneNum[mapIdx] + 1;
        }
        return oneNum;
    }
    /*
    647. Palindromic Substrings
    求回文字串的数目
     */
    public int countSubstrings(String s) {
        int len = s.length();
        char[] chars = new char[2 * len + 1];
        for (int i = 0; i < 2 * len + 1; i++) {
            if (i % 2 == 0) chars[i] = '#';
            else chars[i] = s.charAt(i / 2);
        }
        int num = 0;
        for (int i = 0; i < chars.length; i ++) {
            int j = i - 1;
            int maxLen = 0;
            while (j >= 0 && 2 * i - j < chars.length) {
                if (chars[j] == chars[2 * i - j]) maxLen ++;
                else break;
                j --;
            }
            num += maxLen / 2 + 1;
        }
        return num - len - 1;
    }

    /*
    413. Arithmetic Slices
     */
    public int numberOfArithmeticSlices(int[] A) {
        int len = A.length;
        if (len < 3) return 0;
        int[] num = new int[len];
        num[0] = 0;
        num[1] = 0;
        int sumNum = 0;
        for (int i = 2; i < len; i++) {
            if (A[i] - A[i-1] == A[i-1] - A[i-2]) num[i] = num[i-1] + 1;
            else num[i] = 0;
            sumNum += num[i];
        }
        return sumNum;
    }

    /*
    712. Minimum ASCII Delete Sum for Two Strings
    删掉两个字符串中的某些元素，是剩下的字符串完全相等，求最小的代价
     */
    public int minimumDeleteSum(String s1, String s2) {
        if (s1.equals("")) return costOneChar(' ', s2);
        if (s2.equals("")) return costOneChar(' ', s1);
        int len1 = s1.length();
        int len2 = s2.length();
        int[][] dp = new int[len1][len2];
        for (int i = 0; i < len2; i++) dp[0][i] = costOneChar(s1.charAt(0), s2.substring(0, i+1));
        for (int i = 0; i < len1; i++) dp[i][0] = costOneChar(s2.charAt(0), s1.substring(0, i+1));
        for (int i = 1; i < len1; i++) {
            for (int j = 1; j < len2; j++) {
                if (s1.charAt(i) == s2.charAt(j)) dp[i][j] = dp[i-1][j-1];
                else dp[i][j] = Math.min(dp[i-1][j] + s1.charAt(i), dp[i][j-1] + s2.charAt(j));
            }
        }
        return dp[len1-1][len2-1];
    }

    private int costOneChar(char c, String str2) {
        int len = str2.length();
        boolean charEqual = false;
        int cost = 0;
        for (int i = 0; i < len; i++) {
            if (! charEqual && str2.charAt(i) == c) {
                charEqual = true;
                continue;
            } else {
                cost = cost + str2.charAt(i);
            }
        }
        if (charEqual) return cost;
        else return cost + c;
    }

    /*
    646. Maximum Length of Pair Chain
    给定一批数对，从中选择ｋ对，这ｋ对数对表示的空间不重叠（起止坐标也不能重叠），求ｋ的最大值
     */
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        int len = pairs.length;
        if (len == 0) return 0;
        int num = 1;
        int last = pairs[0][1];
        for (int i = 1; i < len; i++) {
            if (pairs[i][0] > last) {
                last = pairs[i][1];
                num ++;
            }
        }
        return num;
    }
    /*
    343. Integer Break
    给定数字ｎ，将其拆成ｋ个正数的和，使得这ｋ个整数的和最大
    */
    public int integerBreak(int n) {
        if (n < 5) {
            if (n == 4) return 4;
            if (n == 3) return 2;
            if (n == 1) return 0;
        }
        int a = n / 3;
        int b = n % 3;
        if (b == 0) return 3 ^ a;  // Math.pow(3, a)
        if (b == 1) return 3 ^ (a - 1) * 4;
        if (b == 2) return 3 ^ a * 2;
        return -1;
    }

    /*
    357. Count Numbers with Unique Digits
    Given a non-negative integer n, count all numbers with unique digits, x, where 0 ≤ x < 10n.
     */
    public int countNumbersWithUniqueDigits(int n) {
        if (n== 0) return 1;
        if (n == 1) return 10;
        int num = 10;
        for (int i = 2; i <= n && i <= 10; i++) {
            int product = 9;
            int t = 9;
            for (int j = 0; j < i - 1; j++) {
                product *= t;
                t --;
            }
            num += product;
        }
        return num;
    }

    /*
    486. Predict the Winner
    Given an array of scores that are non-negative integers. Player 1 picks one of the numbers
    from either end of the array followed by the player 2 and then player 1 and so on. Each time
    a player picks a number, that number will not be available for the next player. This continues
    until all the scores have been chosen. The player with the maximum score wins.
     */
    public boolean PredictTheWinner(int[] nums) {
        int len = nums.length;
        if (len == 0) return false;
        if (len == 1) return true;
        if (len == 2) return true;
        if (scoreDiff(nums, 0, len-1) >= 0) return true;
        else return false;
    }

    public int scoreDiff(int[] nums, int i, int j) {
        if (j - i + 1 == 3) {
            int first = Math.max(nums[i], nums[j]);
            int second = Math.max(Math.min(nums[i], nums[j]), nums[i+1]);
            first += Math.min(Math.min(nums[i], nums[j]), nums[i+1]);
            return first - second;
        }
        int scoreDiff1 = nums[i] - scoreDiff(nums, i+1, j);
        int scoreDiff2 = nums[j] - scoreDiff(nums, i, j-1);
        return Math.max(scoreDiff1, scoreDiff2);
    }

    /*
    650. 2 Keys Keyboard
     */
    public int minSteps(int n) {
        if (n == 1) return 0;
        for (int i = n - 1; i > 1; i--) {
            if (n % i == 0)
                return minSteps(i) + n / i;
        }
        return n;
    }

    /*
    494. Target Sum
    可以转化为子集和为定值的问题
     */
    public int findTargetSumWays(int[] nums, int S) {
        int len = nums.length;
        if (len == 0) return 0;
        int totalSum = 0;
        for (int i = 0; i < len; i++) {
            totalSum += nums[i];
        }
        if ((S + totalSum) % 2 == 1) return 0;
        if (totalSum < S) return 0;
        int subSum = (S + totalSum) / 2;
        // 求子集和为subSum的个数
        int[] dp = new int[subSum + 1];
        dp[0] = 1;
        if (nums[0] < subSum + 1) dp[nums[0]] += 1;
        for (int i = 1; i < len; i++) {
            for (int j = subSum; j >= 0; j--) {
                if (j - nums[i] >= 0)
                    dp[j] = dp[j] + dp[j - nums[i]];
            }
        }
        return dp[subSum];
    }

    /*
    516. Longest Palindromic Subsequence
    Given a string s, find the longest palindromic subsequence's length in s
    找出回文子序列的最大长度，子序列是指删掉部分字符的字符串
     */
    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        if (len == 0) return 0;
        int[][] dp = new int[len][len];
        for (int j = 0; j < len; j++) {
            dp[j][j] = 1;
            for (int i = j-1; i >= 0; i--) {
                if (s.charAt(i) == s.charAt(j))
                    dp[i][j] = dp[i+1][j-1] + 2;
                else
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
            }
        }
        return dp[0][len-1];
    }

    /*
    740. Delete and Earn
    Given an array nums of integers, you can perform operations on the array.
    In each operation, you pick any nums[i] and delete it to earn nums[i] points. After, you must
    delete every element equal to nums[i] - 1 or nums[i] + 1.
    You start with 0 points. Return the maximum number of points you can earn by applying such operations.
     */
    public int deleteAndEarn(int[] nums) {
        int[] val = new int[10001];
        for (int num : nums) {
            val[num] += num;
        }
        int prepreTaken = val[0];
        int preTaken = val[1];
        int taken = 0;
        for (int i = 2; i < 10001; i++) {
            taken = Math.max(prepreTaken + val[i], preTaken);
            prepreTaken = preTaken;
            preTaken = taken;
        }
        return taken;
    }

    /*
    377. Combination Sum IV
    子集和问题, 但是允许选取的数字重复
    我的理解有问题，题目的意思是顺序不同，也算不同，所以要使用version2
     */
    public int combinationSum4(int[] nums, int target) {
        int len = nums.length;
        if (len == 0) return 0;
        int[] dp = new int[target + 1];
        for (int i = 0; i < target + 1; i++) {
            if (i % nums[0] == 0) dp[i] = 1;
        }
        for (int i = 1; i < len; i++) {
            for (int j = target; j >= 0; j--) {
                int jj = j;
                while (jj - nums[i] >= 0){
                    dp[j] = dp[j] + dp[jj - nums[i]];
                    jj -= nums[i];
                }
            }
        }
        return dp[target];
    }

    /*
    377.version2
     */
    public int combinationSum4V2(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i < target + 1; i++) {
            for (int num : nums) {
                if (i >= num) dp[i] += dp[i - num];
            }
        }
        return dp[target];
    }


    public static void main(String[] args) {
        String testFunc = "minSteps";
        if (testFunc == "minSteps") {
            System.out.println(new DP().minSteps(6));
        }
        if (testFunc == "PredictTheWinner") {
            System.out.println(new DP().PredictTheWinner(new int[]{1, 5, 233, 7}));
        }
        if (testFunc == "findLongestChain") {
            int[][] pairs = new int[][]{{-6,9},{1,6},{8,10},{-1,4},{-6,-2},{-9,8},{-5,3},{0,3}};
            int num = new DP().findLongestChain(pairs);
            System.out.println(num);
        }
        if (testFunc == "minimumDeleteSum") {
            String s1 = "delete";
            String s2 = "leet";
            int cost = new DP().minimumDeleteSum(s1, s2);
            System.out.println(cost);
        }
        if (testFunc == "countSubstrings") {
            int num = new DP().countSubstrings("abc");
            System.out.println(num);
        }
        if (testFunc == "maxProfit") {
            System.out.println(new DP().maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        }
    }


}















