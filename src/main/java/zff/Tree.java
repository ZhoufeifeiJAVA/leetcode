package zff;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhoufeifei on 11/28/17.
 */
public class Tree {
    /*
    230. Kth Smallest Element in a BST
    Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
    Note:
    You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
    Follow up:
    What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently?
    How would you optimize the kthSmallest routine?
    Credits:
    Special thanks to @ts for adding this problem and creating all test cases.
     */
    public int kthSmallest(TreeNode root, int k) {
        List<Integer> l = new ArrayList<Integer>();
        k = values(root, l, k);
        if (k == 0)
            return l.get(0);
        else
            return -1;
    }

    private int values(TreeNode root, List<Integer> l, int k) {  // 返回值为更新之后的k
        if (root == null)
            return k;
        k = values(root.left, l, k);
        if (k == 0)
            return 0;
        k --;
        if (k == 0){
            l.add(root.val);
            return k;
        }
        return values(root.right, l, k);
    }

    /*
    337. House Robber III
    The thief has found himself a new place for his thievery again. There is only one entrance to this area, called the
    "root." Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that
    "all houses in this place forms a binary tree". It will automatically contact the police if two directly-linked
    houses were broken into on the same night.
    Determine the maximum amount of money the thief can rob tonight without alerting the police.
    任意两个被偷的节点不能直接相连
     */
    /*
    下面这种自己写的递归1变４,4^n,代价太大了，时间超过了
     */
    public int rob(TreeNode root) {
        int[] values = rob2(root);
        return Math.max(values[0], values[1]);
    }
    public int rob(TreeNode root, boolean rootCanRob) {
        if (root == null)
            return 0;
        int valueRob = 0;
        int leftValueRoot = rob(root.left, false);
        int rightValueRoot = rob(root.right, false);
        int leftValueNoRoot = rob(root.left, true);
        int rightValueNoRoot = rob(root.right, true);
        if (rootCanRob) {
            valueRob = Math.max(valueRob, root.val + leftValueRoot + rightValueRoot);
            valueRob = Math.max(valueRob, leftValueNoRoot + rightValueNoRoot);
        } else {
            valueRob = Math.max(valueRob, leftValueNoRoot + rightValueNoRoot);
        }
        return valueRob;
    }

    /*
    想办法变成１变２的
     */
    public int[] rob2(TreeNode root) {
        if (root == null)
            return new int[]{0, 0};
        int[] leftValue = rob2(root.left);
        int[] rightValue = rob2(root.right);
        int[] rootAndNoRoot = new int[2];
        rootAndNoRoot[0] = leftValue[1] + rightValue[1] + root.val;
        rootAndNoRoot[1] = Math.max(leftValue[0], leftValue[1]) + Math.max(rightValue[0], rightValue[1]);
        return rootAndNoRoot;
    }

    /*
    102. Binary Tree Level Order Traversal
    Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
    For example:
    Given binary tree [3,9,20,null,null,15,7],
    given:
         3
        / \
        9  20
        /   \
        15   7
    return its level order traversal as:
    [
      [3],
      [9,20],
      [15,7]
    ]
    */
    //这里用队列来存会更好一些
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<TreeNode>> trees = new ArrayList<List<TreeNode>>();
        List<List<Integer>> treeList = new ArrayList<List<Integer>>();
        if (root == null)
            return treeList;
        List<TreeNode> rootList = new ArrayList<TreeNode>();
        rootList.add(root);
        trees.add(rootList);
        int i = 0;
        while (true) {
            List<TreeNode> l = trees.get(i);
            List<TreeNode> nextL = new ArrayList<TreeNode>();
            List<Integer> valueList = new ArrayList<Integer>();
            boolean allIsNull = true;
            Iterator it = l.iterator();
            while (it.hasNext()) {
                TreeNode node = (TreeNode) it.next();
                valueList.add(node.val);
                TreeNode left = node.left;
                TreeNode right = node.right;
                if (left != null) {
                    allIsNull = false;
                    nextL.add(left);
                }
                if (right != null) {
                    allIsNull = false;
                    nextL.add(right);
                }
            }
            trees.add(nextL);
            treeList.add(valueList);
            if (allIsNull) {
                break;
            }
            i ++;
        }
        return treeList;
    }
    /*
    114. Flatten Binary Tree to Linked List
    Given a binary tree, flatten it to a linked list in-place.
    For example,
    Given
             1
            / \
           2   5
          / \   \
         3   4   6
    The flattened tree should look like:
       1
        \
         2
          \
           3
            \
             4
              \
               5
                \
                 6

     */
    public void flatten(TreeNode root) {
        flattenHelper(root);
        System.out.println("hello");
    }
    private TreeNode[] flattenHelper(TreeNode root) {
        if (root == null)
            return new TreeNode[]{null, null};
        TreeNode[] lefts = flattenHelper(root.left);
        TreeNode[] rights = flattenHelper(root.right);
        if (lefts[0] == null) {
            root.right = rights[0];
            root.left = null;
        } else {
            lefts[1].right = rights[0];
            root.right = lefts[0];
            root.left = null;
        }
        TreeNode last = root;
        while (last.right != null) {
            last = last.right;
        }
        return new TreeNode[]{root, last};
    }

    /*
    236. Lowest Common Ancestor of a Binary Tree
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // inorder
        ArrayList<TreeNode> l = (ArrayList<TreeNode>) inOrder(root);
        int pIdx = l.indexOf(p);
        int qIdx = l.indexOf(q);
        // if pIdx > qIdx exchange
        if (pIdx > qIdx) {
            int tmp = pIdx;
            pIdx = qIdx;
            qIdx = tmp;
        }
        HashSet<TreeNode> set = new HashSet<TreeNode>();
        // [pIdx, qIdx]
        for (int i = pIdx; i <= qIdx; i++) {
            set.add(l.get(i));
        }
        // preorder
        return preOrderFind(root, set);
    }

    /*
    98. Validate Binary Search Tree
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        if (!isValidBST(root.left) || !isValidBST(root.right)) return false;
        if ((root.left == null || maxTree(root.left).val < root.val) &&
                (root.right == null || minTree(root.right).val > root.val)) return true;
        else return false;
    }

    private TreeNode maxTree(TreeNode root) {
        if (root == null) return null;
        while(root.right != null) root = root.right;
        return root;
    }

    private TreeNode minTree(TreeNode root) {
        if (root == null) return null;
        while(root.left != null) root = root.left;
        return root;
    }


    private TreeNode preOrderFind(TreeNode root, HashSet set) {
        if (root != null) {
            if (set.contains(root)) return root;
            TreeNode findInLeft = preOrderFind(root.left, set);
            TreeNode findInRight = preOrderFind(root.right, set);
            if (findInLeft != null) return findInLeft;
            if (findInRight != null) return findInRight;
        }
        return null;
    }
    public List<TreeNode> inOrder(TreeNode root) {
        List<TreeNode> l = new ArrayList<TreeNode>();
        inOrder(root, l);
        return l;
    }
    private void inOrder(TreeNode root, List<TreeNode> l) {
        if (root != null) {
            inOrder(root.left, l);
            l.add(root);
            inOrder(root.right, l);
        } else {
            return;
        }
    }

    public static void main(String[] args) {
        TreeNode tree6 = new TreeNode(6);
        TreeNode tree5 = new TreeNode(5);
        TreeNode tree4 = new TreeNode(4);
        TreeNode tree3 = new TreeNode(3);
        TreeNode tree8 = new TreeNode(8);
        TreeNode tree7 = new TreeNode(7);
        TreeNode tree9 = new TreeNode(9);
        tree6.left = tree4;
        tree6.right = tree8;
        //tree4.left = tree3;
        tree4.right = tree5;
        tree8.left = tree7;
        tree8.right = tree9;
        String testFunc = "lowestCommonAncestor";
        if (testFunc == "lowestCommonAncestor") {
            TreeNode node = new Tree().lowestCommonAncestor(tree4, tree5, tree4);
            System.out.println("Successful!");
        }
        if (testFunc == "flatten") {
            new Tree().flatten(tree6);
            System.out.println("hello");
        }
        if (testFunc =="levelOrder") {
            List<List<Integer>> l = new Tree().levelOrder(tree6);
            System.out.println(l);
        }
        if (testFunc == "kthSmallest") {
            int value = new Tree().kthSmallest(tree6, 6);
            System.out.println(value);
        }
        if (testFunc == "rob") {
            int value = new Tree().rob(tree6);
            System.out.println(value);
        }
    }

}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
 }