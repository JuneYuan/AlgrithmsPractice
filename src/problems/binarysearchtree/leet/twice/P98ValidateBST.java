package problems.binarysearchtree.leet.twice;

import java.util.ArrayDeque;
import java.util.Deque;

public class P98ValidateBST {

    public boolean isValidBST(TreeNode root) {
        if (root == null)   return true;
        return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean helper(TreeNode root, long lb, long ub) {
        if (root == null)   return true;
        if (root.val <= lb || root.val >= ub)   return false;

        return helper(root.left, lb, root.val) && helper(root.right, root.val, ub);
    }

}

class MySln {

    public boolean isValidBST(TreeNode root) {
        return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean helper(TreeNode root, long minVal, long maxVal) {
        if (root == null)   return true;
        if (root.val <= minVal || root.val >= maxVal)   return false;
        if (root.left == null && root.right == null)    return true;

        if (root.left != null && (root.val <= Math.max(root.left.val, minVal) || root.val > maxVal)) {
            return false;
        }
        if (root.right != null && (root.val >= Math.min(root.right.val, maxVal) || root.val < minVal)) {
            return false;
        }
        return helper(root.left, minVal, root.val) && helper(root.right, root.val, maxVal);
    }

}

class Iteration {

    public boolean isValidBST(TreeNode root) {
        Deque<TreeNode> st = new ArrayDeque<>();
        long pre = Long.MIN_VALUE;

        // in-order traverse
        while (root != null || !st.isEmpty()) {
            if (root != null) {
                st.push(root);
                root = root.left;
            } else {
                root = st.pop();
                if (root.val > pre) {
                    pre = root.val;
                } else {
                    return false;
                }
                root = root.right;
            }
        }

        return true;
    }

}
