package zff.greedy;

import zff.divcon.DivideCon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by zhoufeifei on 12/20/17.
 */
public class Greedy {
    /*
    122. Best Time to Buy and Sell Stock II
    Say you have an array for which the ith element is the price of a given stock on day i.
    Design an algorithm to find the maximum profit. You may complete as many transactions as you like
    (ie, buy one and sell one share of the stock multiple times). However, you may not engage in
    multiple transactions at the same time (ie, you must sell the stock before you buy again).
     */
    public int maxProfit(int[] prices) {
        int len = prices.length;
        int buy = 0;
        int sell = 0;
        if (len == 1 || len == 0) return 0;
        if (prices[0] < prices[1]) buy += prices[0];
        for (int i = 1; i < len - 1; i++) {
            if (prices[i-1] >= prices[i] && prices[i] < prices[i+1]) buy += prices[i];
            if (prices[i-1] < prices[i] && prices[i] >= prices[i+1]) sell += prices[i];
        }
        if (prices[len-2] < prices[len-1]) sell += prices[len-1];
        return sell - buy;
    }

    /*
    455. Assign Cookies
    Assume you are an awesome parent and want to give your children some cookies. But, you should give
    each child at most one cookie. Each child i has a greed factor gi, which is the minimum size of a
    cookie that the child will be content with; and each cookie j has a size sj. If sj >= gi, we can
    assign the cookie j to the child i, and the child i will be content. Your goal is to maximize the
    number of your content children and output the maximum number.
    Note:
    You may assume the greed factor is always positive.
    You cannot assign more than one cookie to one child.
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int sIdx = 0;
        int satisfiedNum = 0;
        for (int i = 0; i < g.length; i++) {
            while (sIdx < s.length && s[sIdx] < g[i]) {
                sIdx ++;
            }
            if (sIdx > s.length - 1) break;
            if (s[sIdx] >= g[i]) {
                sIdx ++;
                satisfiedNum ++;
            }
        }
        return satisfiedNum;
    }
    /*
    406. Queue Reconstruction by Height
    Suppose you have a random list of people standing in a queue. Each person is described
    by a pair of integers (h, k), where h is the height of the person and k is the number
    of people in front of this person who have a height greater than or equal to h. Write
    an algorithm to reconstruct the queue.
    Note:
    The number of people is less than 1,100.
    Input:
    [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
    Output:
    [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
     */
    public int[][] reconstructQueue(int[][] people) {
        Pair[] pairs = new Pair[people.length];
        for (int i = 0; i < people.length; i++) {
            pairs[i] = new Pair(people[i][0], people[i][1]);
        }
        Arrays.sort(pairs);
        // adjust according to second data
        for (int i = 1; i < pairs.length; i++) {
            int adjustIdx = pairs[i].lastData;
            Pair tmp = pairs[i];
            for (int j = i; j > adjustIdx; j--) {
                pairs[j] = pairs[j - 1];
            }
            pairs[adjustIdx] = tmp;
        }
        for (int i = 0; i < people.length; i++) {
            people[i][0] = pairs[i].firstData;
            people[i][1] = pairs[i].lastData;
        }
        return people;
    }

    private class Pair implements Comparable{
        int firstData, lastData;
        public Pair(int a, int b) {
            this.firstData = a;
            this.lastData = b;
        }
        public int compareTo(Object o) {
            Pair p2 = (Pair)o;
            int d1 = -this.firstData + p2.firstData;
            if (d1 != 0) return d1;
            else return this.lastData - p2.lastData;
        }
    }

    /*
    392. Is Subsequence
    Given a string s and a string t, check if s is subsequence of t.
    You may assume that there is only lower case English letters in both s and t.
    t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).
    A subsequence of a string is a new string which is formed from the original string by
    deleting some (can be none) of the characters without disturbing the relative positions
    of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).
    Example 1:
    s = "abc", t = "ahbgdc"
    Return true.
    Example 2:
    s = "axc", t = "ahbgdc"
    Return false.
    Follow up:
    If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one
    to see if T has its subsequence. In this scenario, how would you change your code?
    */
    public boolean isSubsequence(String s, String t) {
        char[] schars = s.toCharArray();
        char[] tchars = t.toCharArray();
        return isSubsequence(schars, 0, schars.length-1, tchars, 0, tchars.length-1);
    }

    public boolean isSubsequence(char[] s, int sStart, int sEnd, char[] t, int tStart, int tEnd) {
        int sLen = sEnd - sStart + 1;
        int tLen = tEnd - tStart + 1;
        if (sLen == 0) return true;
        if (tLen == 0) return false;
        while (tStart <= tEnd && t[tStart] != s[sStart]) tStart ++;
        if (tStart > tEnd) return false;
        return isSubsequence(s, sStart+1, sEnd, t, tStart+1, tEnd);
    }

    /*
    452. Minimum Number of Arrows to Burst Balloons
    There are a number of spherical balloons spread in two-dimensional space. For each balloon,
    provided input is the start and end coordinates of the horizontal diameter. Since it's horizontal,
    y-coordinates don't matter and hence the x-coordinates of start and end of the diameter suffice.
    Start is always smaller than end. There will be at most 104 balloons.
    An arrow can be shot up exactly vertically from different points along the x-axis. A balloon
    with xstart and xend bursts by an arrow shot at x if xstart ≤ x ≤ xend. There is no limit to
    the number of arrows that can be shot. An arrow once shot keeps travelling up infinitely.
    The problem is to find the minimum number of arrows that must be shot to burst all balloons.
    Input:[[10,16], [2,8], [1,6], [7,12]]
    Output:
    2
    */
    public int findMinArrowShots(int[][] points) {
        ArrayList<Pair> pairs = new ArrayList<Pair>();
        for (int i = 0; i < points.length; i ++) pairs.add(new Pair(points[i][0], points[i][1]));
        Collections.sort(pairs, new Comparator<Pair>() {
            public int compare(Pair o1, Pair o2) {
                if (o1.firstData == o2.firstData) return o1.lastData - o2.lastData;
                return o1.firstData - o2.firstData;
            }
        });
        if (points.length == 0) return 0;
        int minArrowNum = 1;
        int minEndValue = pairs.get(0).lastData;
        for (Pair pair : pairs) {
            if (pair.firstData > minEndValue) {
                minArrowNum++;
                minEndValue = pair.lastData;
            } else {
                minEndValue = Math.min(minEndValue, pair.lastData);
            }
        }
        return minArrowNum;
    }
    public int findMinArrowShotsV2(int[][] points) {
        Arrays.sort(points, new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) return o1[1] - o2[1];
                else return o1[0] - o2[0];
            }
        });
        if (points.length == 0) return 0;
        int minArrowNum = 1;
        int minEndValue = points[0][1];
        for (int i = 0; i < points.length; i++) {
            if (points[i][0] > minEndValue) {
                minArrowNum++;
                minEndValue = points[i][1];
            } else {
                minEndValue = Math.min(minEndValue, points[i][1]);
            }
        }
        return minArrowNum;
    }

    /*
    621. Task Scheduler
    Given a char array representing tasks CPU need to do. It contains capital letters A to Z where
    different letters represent different tasks.Tasks could be done without original order. Each task
    could be done in one interval. For each interval, CPU could finish one task or just be idle.
    However, there is a non-negative cooling interval n that means between two same tasks, there must
    be at least n intervals that CPU are doing different tasks or just be idle.
    You need to return the least number of intervals the CPU will take to finish all the given tasks.
    Example 1:
    Input: tasks = ["A","A","A","B","B","B"], n = 2
    Output: 8
    Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
    Note:
    The number of tasks is in the range [1, 10000].
    The integer n is in the range [0, 100].
     */
    public int leastInterval(char[] tasks, int n) {
        int len = tasks.length;
        int[] taskUnDone = new int[26];
        for (int i = 0; i < len; i++) {
            taskUnDone[tasks[i] - 'A'] ++;
        }
        int[] taskFreeze = new int[26];
        int interNeed = 0;
        int maxVal, maxIdx;
        while (true) {
            maxIdx = maxIdx(taskUnDone, taskFreeze);
            boolean stop = true;
            for (int i = 0; i < taskUnDone.length; i++) {
                if (taskUnDone[i] > 0) stop = false;
            }
            if (stop) break;
            if (maxIdx == -1) {
                interNeed ++;
                arraySubOne(taskFreeze);
                continue;
            }
            taskUnDone[maxIdx] --;
            interNeed ++;
            arraySubOne(taskFreeze);
            taskFreeze[maxIdx] = n;
        }
        return interNeed;
    }

    private int maxIdx(int[] nums, int[] mask) {
        int maxVal = -1;
        int maxIdx = -1;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (mask[i] == 0 && nums[i] > maxVal && nums[i] > 0) {
                maxVal = nums[i];
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    private void arraySubOne(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) nums[i] --;
        }
    }
    /*
    714. Best Time to Buy and Sell Stock with Transaction Fee
    只是在卖的时候收费，买的时候不收费
    Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
    Output: 8
    Explanation: The maximum profit can be achieved by:
    Buying at prices[0] = 1
    Selling at prices[3] = 8
    Buying at prices[4] = 4
    Selling at prices[5] = 9
    The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
     */
    public int maxProfit(int[] prices, int fee) {
        int len = prices.length;
        int[] hold = new int[len];
        int[] none = new int[len];
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                hold[i] = -prices[i];
                none[i] = 0;
            } else {
                hold[i] = Math.max(none[i-1] - prices[i], hold[i-1]);
                none[i] = Math.max(hold[i-1] + prices[i] - fee, none[i-1]);
            }
        }
        return Math.max(hold[len-1], none[len-1]);
    }
    /*
    435. Non-overlapping Intervals
    Given a collection of intervals, find the minimum number of intervals you need to remove to make
    the rest of the intervals non-overlapping.
    Note:
    You may assume the interval's end point is always bigger than its start point.
    Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.
    */
    public int eraseOverlapIntervals(Interval[] intervals) {
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval o1, Interval o2) {
                if (o1.start == o2.start) return o2.end - o1.end;
                return o1.start - o2.start;
            }
        });
        int removeInter = 0;
        int len = intervals.length;
        if (len == 0) return 0;
        Interval currentInter = intervals[0];
        for (int i = 1; i < len; i++) {
            if (currentInter.end > intervals[i].start) {
                removeInter++;
                if (currentInter.end > intervals[i].end)
                    currentInter = intervals[i];
            } else {
                currentInter = intervals[i];
            }
        }
        return removeInter;
    }

    private class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    /*
    738. Monotone Increasing Digits
    Given a non-negative integer N, find the largest number that is less than or equal
    to N with monotone increasing digits.
    (Recall that an integer has monotone increasing digits if and only if each pair of
    adjacent digits x and y satisfy x <= y.)
     */
    public int monotoneIncreasingDigits(int N) {
        String str = N + "";
        char[] chars = str.toCharArray();
        int len = chars.length;
        int[] digits = new int[len];
        for (int i = 0; i < len; i++) {
            digits[i] = Integer.parseInt(chars[i] + "");
        }
        if (len == 1) return N;
        int p1 = 0;
        int p2 = 0;
        for (int i = 1; i < len; i++) {
            if (digits[i - 1] < digits[i]) {
                p2 = i;
                p1 = i;
            } else if (digits[i-1] == digits[i]) {
                p2 ++;
            } else break;
        }
        if (p2 == len - 1) return N;
        digits[p1] --;
        for (int i = p1 + 1; i < len; i++) {
            digits[i] = 9;
        }
        str = "";
        for (int i = 0; i < len; i++) {
            str += digits[i];
        }
        return Integer.parseInt(str);
    }
    /*
    376. Wiggle Subsequence
    A sequence of numbers is called a wiggle sequence if the differences between successive numbers strictly alternate
    between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence
    with fewer than two elements is trivially a wiggle sequence.
    For example, [1,7,4,9,2,5] is a wiggle sequence because the differences (6,-3,5,-7,3) are alternately positive and
    negative. In contrast, [1,4,7,2,5] and [1,7,4,5,5] are not wiggle sequences, the first because its first two differences
    are positive and the second because its last difference is zero.
    Given a sequence of integers, return the length of the longest subsequence that is a wiggle sequence. A
    subsequence is obtained by deleting some number of elements (eventually, also zero) from the original sequence,
    leaving the remaining elements in their original order.
    Examples:
    Input: [1,7,4,9,2,5]
    Output: 6
    The entire sequence is a wiggle sequence.
    Input: [1,17,5,10,13,15,10,5,16,8]
    Output: 7
    There are several subsequences that achieve this length. One is [1,17,10,13,10,16,8].
    Input: [1,2,3,4,5,6,7,8,9]
    Output: 2
    Follow up:
    Can you do it in O(n) time?
     */
    public int wiggleMaxLength(int[] nums) {
        int len = nums.length;
        int[] pos = new int[len];  // pos[i]表示以第i个元素结尾构成的wiggle，保证其长度最大，并且最后一个间隙是正的
        int[] neg = new int[len];  // pos[i]表示以第i个元素结尾构成的wiggle，保证其长度最大，并且最后一个间隙是负的
        for (int i = 0; i < len; i++) {
            pos[i] = 1;
            neg[i] = 1;  // 最小长度是1
        }
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    pos[i] = Math.max(pos[i], neg[j] + 1);
                } else if (nums[i] < nums[j]) {
                    neg[i] = Math.max(neg[i], pos[j] + 1);
                }
            }
        }
        int maxLen = 0;
        for (int i = 0; i < len; i++) {
            maxLen = Math.max(maxLen, neg[i]);
            maxLen = Math.max(maxLen, pos[i]);
        }
        return maxLen;
    }

    /*
    659. Split Array into Consecutive Subsequences
    You are given an integer array sorted in ascending order (may contain duplicates), you need to
    split them into several subsequences, where each subsequences consist of at least 3 consecutive
    integers. Return whether you can make such a split.
    Input: [1,2,3,3,4,5]
    Output: True
    Explanation:
    You can split them into two consecutive subsequences :
    1, 2, 3
    3, 4, 5
    Input: [1,2,3,3,4,4,5,5]
    Output: True
    Explanation:
    You can split them into two consecutive subsequences :
    1, 2, 3, 4, 5
    3, 4, 5
    */
    public boolean isPossible(int[] nums) {
        int len = nums.length;
        ArrayList<Integer> preEndList = new ArrayList<Integer>();
        ArrayList<Integer> endList = new ArrayList<Integer>();
        if (len <= 2) return false;
        int endData = nums[0];
        endList.add(1);
        int preEndData = endData - 2; // ensure endData and preEndData not diff 1
        int minLen;
        for (int i = 1; i < len; i++) {
            if (nums[i] != nums[i - 1]) {
                minLen = removeMin(preEndList);
                if (minLen != 0 && minLen < 3) return false;
                preEndList.clear();
                preEndList = (ArrayList<Integer>) endList.clone();
                endList.clear();
                preEndData = endData;
                endData = nums[i];
            }
            if (nums[i] == preEndData + 1) {
                minLen = removeMin(preEndList);
                endList.add(minLen + 1);
            } else {
                endList.add(1);
            }
        }
        minLen = removeMin(preEndList);
        if (minLen != 0 && minLen < 3) return false;
        minLen = removeMin(endList);
        if (minLen != 0 && minLen < 3) return false;
        return true;
    }

    private int removeMin(ArrayList<Integer> list) {
        if (list.isEmpty()) return 0;
        int minIdx = 0;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < list.get(minIdx))
                minIdx = i;
        }
        int min = list.get(minIdx);
        list.remove(minIdx);
        return min;
    }
    /*
    402. Remove K Digits
    Given a non-negative integer num represented as a string, remove k digits from the number so
    that the new number is the smallest possible.
    Note:
    The length of num is less than 10002 and will be ≥ k.
    The given num does not contain any leading zero.
     */
    public String removeKdigits(String num, int k) {
        int len = num.length();
        if (len <= k) return 0 + "";
        char[] chars = num.toCharArray();
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i = 0; i < len; i++) {
            nums.add(Integer.parseInt(chars[i]+""));
        }
        for (int i = 0; i < len - 1; i++) {
            if (k == 0) break;
            if (nums.get(i) > nums.get(i + 1)) {
                nums.remove(i);
                k --;
                if (i == 0)
                    i --;
                else
                    i -= 2;
                len --;
            }
        }
        for (int i = 0; i < k; i++) {
            nums.remove(nums.size() - 1);
        }
        String str = "";
        boolean zeroLeading = true;
        for (int i = 0; i < nums.size(); i++){
            if (nums.get(i) != 0) zeroLeading = false;
            if (!zeroLeading) str += nums.get(i);
        }
        return str;
    }

    public static void main(String[] args) {

        String testFunc = "removeKdigits";
        if (testFunc == "removeKdigits") {
            String str = "1432219";
            String result = new Greedy().removeKdigits(str, 3);
            System.out.println(result);
        }
        if (testFunc == "isPossible") {
            int[] nums = new int[]{1,2,3,4,4,5};
            boolean isPossible = new Greedy().isPossible(nums);
            System.out.println(isPossible);
        }
        if (testFunc == "leastInterval") {
            char[] tasks = new char[]{'A','A','A','B','B','B'};
            int interNeeded = new Greedy().leastInterval(tasks, 2);
            System.out.println(interNeeded);
        }
        if (testFunc == "reconstructQueue") {
            int[][] nums = new int[][]{{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}};
            int[][] adjustedNums = new Greedy().reconstructQueue(nums);
            System.out.println(adjustedNums);
        }
    }
}
