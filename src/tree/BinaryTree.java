package tree;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 二叉搜索树实现
 *
 * @author 微信公众号：轻松编程。扫描resources中的二维码关注。
 */
public class BinaryTree {

    // 树的根节点
    private BinaryNode root;
    // 栈
    private static Stack<BinaryTree> treeStack = new Stack<>();

    public BinaryTree(BinaryNode root) {
        this.root = root;
    }

    /**
     * 后序遍历该树
     */
    public void postOrderTraversal() {
        // 为了输出更明显，打印中文，如果出现乱码请修改idea的编码格式或者修改为英文
        System.out.print("后序遍历结果为 ：");
        this.postOrderTraversal(this.root);
        // 增加换行
        System.out.println();
    }

    /**
     * 后序遍历
     */
    private void postOrderTraversal(BinaryNode t) {
        if (t != null) {
            // 递归遍历左子树、右子树、根
            postOrderTraversal(t.left);
            postOrderTraversal(t.right);
            System.out.print(t.element);
        }
    }

    /**
     * 先序遍历该树
     */
    private void firstOrderTraversal() {
        // 为了输出更明显，打印中文，如果出现乱码请修改idea的编码格式或者修改为英文
        System.out.print("先序遍历结果为 ：");
        firstOrderTraversal(this.root);
        System.out.println();
    }

    /**
     * 先序遍历
     */
    private void firstOrderTraversal(BinaryNode t) {
        if (t != null) {
            System.out.print(t.element);
            firstOrderTraversal(t.left);
            firstOrderTraversal(t.right);
        }
    }

    /**
     * 中序遍历该树
     */
    private void inOrderTraversal() {
        // 为了输出更明显，打印中文，如果出现乱码请修改idea的编码格式或者修改为英文
        System.out.print("中序遍历结果为 ：");
        inOrderTraversal(this.root);
        System.out.println();
    }

    /**
     * 中序遍历
     */
    private void inOrderTraversal(BinaryNode t) {
        if (t != null) {
            inOrderTraversal(t.left);
            System.out.print(t.element);
            inOrderTraversal(t.right);
        }
    }

    /**
     * 按层遍历该树
     */
    private void levelTraversal() {
        treeStack.push(this);
        // 为了输出更明显，打印中文，如果出现乱码请修改idea的编码格式或者修改为英文
        System.out.println("按层打印输出 ： ");
        printLevelTree();
    }

    /**
     * 按层遍历
     */
    public void printLevelTree() {
        LinkedList<BinaryTree> binaryTrees = new LinkedList<>();
        if (treeStack.isEmpty()) {
            return;
        }
        while (!treeStack.isEmpty()) {
            BinaryTree tree = treeStack.pop();
            System.out.print(tree.root.element);
            binaryTrees.addFirst(tree);
        }
        System.out.println();
        for (BinaryTree binaryTree : binaryTrees) {

            if (binaryTree.root.right != null) {
                treeStack.push(new BinaryTree(binaryTree.root.right));
            }
            if (binaryTree.root.left != null) {
                treeStack.push(new BinaryTree(binaryTree.root.left));
            }
        }
        printLevelTree();
    }

    /**
     * 后缀表达式转换为树
     *
     * @param expression 表达式
     * @return 表达式树
     */
    public static BinaryTree expressionToTree(String expression) {
        int length = expression.length();
        for (int i = 0; i < length; i++) {
            char c = expression.charAt(i);
            int ascii = (int) c;
            if (ascii >= 65 && ascii <= 122) {
                treeStack.push(new BinaryTree(new BinaryNode<>(c)));
            } else {
                BinaryNode root = new BinaryNode(c);
                BinaryTree rightTree = treeStack.pop();
                BinaryTree leftTree = treeStack.pop();
                root.right = rightTree.getRoot();
                root.left = leftTree.getRoot();
                BinaryTree binaryTree = new BinaryTree(root);
                treeStack.push(binaryTree);
            }
        }
        return treeStack.pop();

    }

    public static void main(String[] args) {
        // 表达式转为二叉树
        BinaryTree binaryTree = expressionToTree("ab+cde+**");
        // 先序遍历打印二叉树
        binaryTree.firstOrderTraversal();
        // 中序遍历打印二叉树
        binaryTree.inOrderTraversal();
        // 后序遍历打印二叉树
        binaryTree.postOrderTraversal();
        // 按层遍历打印二叉树，方便观察树结构
        binaryTree.levelTraversal();
    }

    // 树节点类
    private static class BinaryNode<E> {
        // 节点的值
        E element;
        // 左子树
        BinaryNode<E> left;
        // 右子树
        BinaryNode<E> right;

        BinaryNode(E element) {
            this(element, null, null);
        }

        BinaryNode(E element, BinaryNode<E> left, BinaryNode<E> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    public BinaryNode getRoot() {
        return root;
    }

}
