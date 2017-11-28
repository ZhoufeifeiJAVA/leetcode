package zff.hashtable;

import java.util.*;

/**
 * Created by zhoufeifei on 11/21/17.
 */
public class HashTables {
    /*
    Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.
    If the fractional part is repeating, enclose the repeating part in parentheses.
    For example,
    Given numerator = 1, denominator = 2, return "0.5".
    Given numerator = 2, denominator = 1, return "2".
    Given numerator = 2, denominator = 3, return "0.(6)".
     */
    public String fractionToDecimal(int numerator, int denominator) {
        long num = (long) numerator;
        long den = (long) denominator;
        String decimal = "";
        // 别忘了判断正负
        if (num * den < 0) {
            decimal += '-';
        }
        num = Math.abs(num);
        den = Math.abs(den);
        HashMap<Long, Long> hm = new HashMap<Long, Long>();
        long rest = 0;
        if (num >= den) {
            decimal += num / den;
            rest = num % den;
        } else {
            decimal += "0";
            rest = num;
        }
        if (rest != 0)
            decimal += ".";
        long idxAfterDot = 0;
        while (rest != 0) {
            idxAfterDot ++;
            hm.put(rest, idxAfterDot - 1);
            rest *= 10;
            long quotient = rest / den;
            rest %= den;
            decimal += quotient;
            if (hm.containsKey(rest)) {
                int len = decimal.length();
                int repeatIdx = (int)(len - 1 - (idxAfterDot - hm.get(rest) - 1));
                decimal = decimal.substring(0, repeatIdx) + "(" + decimal.substring(repeatIdx, len) + ")";
                return decimal;
            }
        }
        return decimal;
    }

    /*
    3. Longest Substring Without Repeating Characters
    Given a string, find the length of the longest substring without repeating characters.
    Examples:
    Given "abcabcbb", the answer is "abc", which the length is 3.
    Given "bbbbb", the answer is "b", with the length of 1.
    Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a
    subsequence and not a substring.
    */
    public int lengthOfLongestSubstring(String s) {
        char[] data = s.toCharArray();
        int len = data.length;
        int[] minIdx = new int[len];
        HashMap<Character, Integer> ht = new HashMap<Character, Integer>();
        for (int i = len - 1; i >= 0; i--) {
            if (ht.containsKey(data[i])) {
                minIdx[i] = ht.get(data[i]);
            } else {
                minIdx[i] = len;
            }
            ht.put(data[i], i);
        }
        int maxLen = 0;
        int lastIdx;
        for (int i = 0; i < len; i++) {
            int maxLenFromI = 1;
            lastIdx = minIdx[i];
            for (int j = i + 1; j < lastIdx; j++) {
                if (minIdx[j] < lastIdx) {
                    lastIdx = minIdx[j];
                }
                maxLenFromI ++;
            }
            if (maxLenFromI > maxLen)
                maxLen = maxLenFromI;
        }
        return maxLen;
    }

    public int lengthOfLongestSubstringV2(String s) {
        char[] data = s.toCharArray();
        int len = data.length;
        HashMap<Character, Integer> ht = new HashMap<Character, Integer>();
        int left = 0;
        int maxLen = 0;
        for (int i = 0; i < len; i++) {
            if (ht.containsKey(data[i]) && left <= ht.get(data[i])) {
                maxLen = Math.max(maxLen, i - left);
                left = ht.get(data[i]) + 1;
            }
            ht.put(data[i], i);
        }
        maxLen = Math.max(maxLen, len - left);
        return maxLen;
    }

    /*
     138. Copy List with Random Pointer
     A linked list is given such that each node contains an additional random pointer which could point to any node in
     the list or null.
     Return a deep copy of the list.
     */

    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null)
            return null;
        HashMap<RandomListNode, Integer> ht = new HashMap<RandomListNode, Integer>();
        HashMap<Integer, RandomListNode> copyHt = new HashMap<Integer, RandomListNode>();
        RandomListNode copyListHead = new RandomListNode(head.label);
        RandomListNode node = head;
        RandomListNode copyNode = copyListHead;
        ht.put(head, 0);
        copyHt.put(0, copyListHead);
        int i = 1;
        while (node.next != null) {
            copyNode.next = new RandomListNode(node.next.label);
            ht.put(node.next, i);
            copyHt.put(i, copyNode.next);
            i ++;
            node = node.next;
            copyNode = copyNode.next;
        }
        node = head;
        copyNode = copyListHead;
        while (node.next != null) {
            if (node.random != null) {
                int targetIdx = ht.get(node.random);
                copyNode.random = copyHt.get(targetIdx);
            }
            node = node.next;
            copyNode = copyNode.next;
        }
        return copyListHead;
    }

    public RandomListNode copyRandomListV2(RandomListNode head) {
        if (head == null) return null;

        Map<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();

        // loop 1. copy all the nodes
        RandomListNode node = head;
        while (node != null) {
            map.put(node, new RandomListNode(node.label));
            node = node.next;
        }

        // loop 2. assign next and random pointers
        node = head;
        while (node != null) {
            map.get(node).next = map.get(node.next);
            map.get(node).random = map.get(node.random);
            node = node.next;
        }

        return map.get(head);
    }

    /*
    49. group anagrams
    Given an array of strings, group anagrams together.
    For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"],
    Return:
    [
      ["ate", "eat","tea"],
      ["nat","tan"],
      ["bat"]
    ]
    Note: All inputs will be in lower-case.
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String keyStr = String.valueOf(ca);
            if (!map.containsKey(keyStr)) map.put(keyStr, new ArrayList<String>());
            map.get(keyStr).add(s);
        }
        return new ArrayList<List<String>>(map.values());
    }

    /*
    36.Valid Sudoku
    Determine if a Sudoku is valid, according to: Sudoku Puzzles - The Rules.
    The Sudoku board could be partially filled, where empty cells are filled
    with the character '.'.
     */
    public boolean isValidSudoku(char[][] board) {
        int numRow = board.length;
        int numCol = board[0].length;
        HashSet<Character> set = new HashSet<Character>();
        // check rows
        for (int row = 0; row < numRow; row++) {
            char[] rowData = board[row];
            set.clear();
            for (char element : rowData) {
                if (element != '.' && set.contains(element)) {
                    return false;
                }
                set.add(element);
            }
        }
        // check columns
        for (int col = 0; col < numCol; col++) {
            char[] colData = new char[numRow];
            // get colData
            for (int i = 0; i < colData.length; i++) {
                colData[i] = board[i][col];
            }
            set.clear();
            for (char element : colData) {
                if (element != '.' && set.contains(element)) {
                    return false;
                }
                set.add(element);
            }
        }
        // check 3 * 3
        int num3Row = numRow / 3;
        int num3Col = numCol / 3;
        for (int i = 0; i < num3Row; i++) {
            for (int j = 0; j < num3Col; j++) {
                set.clear();
                for (int i1 = 3 * i; i1 < 3 * (i + 1); i1++) {
                    for (int j1 = 3 * j; j1 < 3 * (j + 1); j1++) {
                        char element = board[i1][j1];
                        if (element != '.' && set.contains(element)) {
                            return false;
                        }
                        set.add(element);
                    }
                }
            }
        }
        return true;
    }

    /*
    Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l) there are such that A[i] + B[j]
    + C[k] + D[l] is zero.
    To make problem a bit easier, all A, B, C, D have same length of N where 0 ≤ N ≤ 500. All integers are in the range
    of -228 to 228 - 1 and the result is guaranteed to be at most 231 - 1.
    */
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int len = A.length;
        int num = 0;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                int sum = A[i] + B[j];
                if (map.containsKey(sum)) {
                    map.put(sum, map.get(sum)+1);
                } else {
                    map.put(sum, 1);
                }
            }
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                int sum = -(C[i] + D[j]);
                if (map.containsKey(sum)) {
                   num += map.get(sum);
                }
            }
        }
        return num;
    }
    /*
    347. Top K Frequent Elements
    Given a non-empty array of integers, return the k most frequent elements.
    For example,
    Given [1,1,1,2,2,3] and k = 2, return [1,2].
    Note:
    You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
    Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
     */
    public List<Integer> topKFrequent(int[] nums, int k) {
        Arrays.sort(nums);
        TreeMap<Integer, List<Integer>> treeMap = new TreeMap<Integer, List<Integer>>();
        int continueLen = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                continueLen ++;
            } else {
                if (treeMap.containsKey(continueLen)) {
                    treeMap.get(continueLen).add(nums[i]);
                } else {
                    List<Integer> l = new ArrayList<Integer>();
                    l.add(nums[i]);
                    treeMap.put(continueLen, l);
                }
                continueLen = 1;
            }
        }
        if (treeMap.containsKey(continueLen)) {
            treeMap.get(continueLen).add(nums[nums.length - 1]);
        } else {
            List<Integer> l = new ArrayList<Integer>();
            l.add(nums[nums.length - 1]);
            treeMap.put(continueLen, l);
        }
        List<Integer> mostKFreq = new ArrayList<Integer>();
        Object[] keys = treeMap.keySet().toArray();
        for (int i = keys.length - 1; i > -1; i--) {
            Object key = keys[i];
            List<Integer> l = treeMap.get(key);
            for (int data : l) {
                mostKFreq.add(data);
                k--;
                if (k == 0) {
                    return mostKFreq;
                }
            }
        }
        return mostKFreq;
    }

    public static void main(String[] args) {
        String testFunc = "topKFrequent";
        if (testFunc == "topKFrequent") {
            int[] nums = {5, 6, 1,1,1,2,2,3};
            System.out.println(new HashTables().topKFrequent(nums, 2));
        }
        if (testFunc == "fourSumCount") {
            int[] a = new int[]{1, 2};
            int[] b = new int[]{-2, -1};
            int[] c = new int[]{-1, 2};
            int[] d = new int[]{0, 2};
            int num = new HashTables().fourSumCount(a, b, c, d);
            System.out.println(num);
        }
        if (testFunc == "isValidSudoku") {
            char[][] board = new char[][]{{'.','8','7','6','5','4','3','2','1'},{'2','.','.','.','.','.','.','.','.'},{'3','.','.','.','.','.','.','.','.'},{'4','.','.','.','.','.','.','.','.'},{'5','.','.','.','.','.','.','.','.'},{'6','.','.','.','.','.','.','.','.'},{'7','.','.','.','.','.','.','.','.'},{'8','.','.','.','.','.','.','.','.'},{'9','.','.','.','.','.','.','.','.'}};
            new HashTables().isValidSudoku(board);
            
        }
        if (testFunc == "lengthOfLongestSubstring") {
            int maxLen = new HashTables().lengthOfLongestSubstringV2("abbacd");
            System.out.println(maxLen);
        }
        if (testFunc == "fractionToDecimal") {
            String decimal = new HashTables().fractionToDecimal(1, -2147483648);
            System.out.println(decimal);
        }
        if (testFunc == "copyRandomList") {
            RandomListNode head = new RandomListNode(0);
            head.next = new RandomListNode(1);
            head.next.next = new RandomListNode(2);
            head.next .next.next= new RandomListNode(3);
            head.next .next.next.next= new RandomListNode(4);
            head.random = head.next.next;
            head.next.random = head.next.next.next;
            head.next.next.random = head.next.next.next.next;
            head.next.next.next.random = head.next.next.next.next.next;
            head.next.next.next.next.random = null;
            RandomListNode copyListNode = new HashTables().copyRandomList(head);
            System.out.println("use debug to test");
        }
    }
}


class RandomListNode {
    int label;
    RandomListNode next, random;
    RandomListNode(int x) { this.label = x; }
}

























