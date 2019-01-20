package tree;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 二叉搜索树实现。
 * 该类只是表明实现逻辑的测试类，因此尽可能保证该类的独立性，所以该类中会出现重复代码（相较于其他测试类，例如BinaryTree）。
 *
 * @author 微信公众号：轻松编程。扫描resources中的二维码关注。
 */
public class BinarySearchTree<E extends Comparable<? super E>> {
    /**
     * 树的根.
     */
    private BinaryNode<E> root;
    // 按层打印时使用
    private Stack<BinarySearchTree<E>> treeStack = new Stack<>();

    /**
     * 构造函数.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * 构造函数.
     */
    public BinarySearchTree(BinaryNode<E> root) {
        this.root = root;
    }

    /**
     * 向该树中插入节点，如果重复则忽略.递归实现.
     *
     * @param x 待插入节点的值.
     */
    public void insert(E x) {
        root = insert(x, root);
    }

    /**
     * 向指定树中插入节点，如果重复则忽略.递归实现.
     *
     * @param x 待插入节点的值.
     * @param t 指定树的根节点.
     * @return 插入后生成的新树的根节点.
     */
    private BinaryNode<E> insert(E x, BinaryNode<E> t) {
        if (t == null) {
            return new BinaryNode<>(x, null, null);
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = insert(x, t.left);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right);
        } else {
            ;  // Duplicate; do nothing
        }
        return t;
    }

    /**
     * 向该树中插入节点，如果重复则忽略.非递归实现.
     *
     * @param x 待插入节点的值.
     */
    public void insertNormal(E x) {
        root = insertNormal(x, root);
    }

    /**
     * 向指定树中插入节点，如果重复则忽略.非递归实现.
     *
     * @param x 待插入节点的值.
     * @param t 指定树的根节点
     * @return 插入后生成的新树的根节点
     */
    private BinaryNode<E> insertNormal(E x, BinaryNode<E> t) {

        if (t == null) {
            return new BinaryNode<>(x, null, null);
        }
        BinaryNode<E> parent = t;
        BinaryNode<E> child = t;
        while (child != null) {
            int compareResult = x.compareTo(child.element);
            if (compareResult < 0) {
                if ((child = parent.left) == null) {
                    parent.left = new BinaryNode<>(x, null, null);
                }
            } else if (compareResult > 0) {
                if ((child = parent.right) == null) {
                    parent.right = new BinaryNode<>(x, null, null);
                }
            } else {
                ;
            }
            parent = child;
        }

        return t;
    }

    /**
     * 删除该树中的节点. 如果删除的节点不存在则不作任何操作.递归实现.
     *
     * @param x 待删除节点的值.
     */
    public void remove(E x) {
        root = remove(x, root);
    }

    /**
     * 删除指定树中的节点.如果删除的节点不存在则不作任何操作.递归实现.
     *
     * @param x 待删除节点的值.
     * @param t 指定树的根.
     * @return 删除节点后的新树的根节点.
     */
    private BinaryNode<E> remove(E x, BinaryNode<E> t) {
        if (t == null) {
            return t;   // Item not found; do nothing
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (compareResult > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) // Two children
        {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    /**
     * 删除该树中的节点. 如果删除的节点不存在则不作任何操作.非递归实现.
     *
     * @param x 待删除节点的值.
     */
    public void removeNormal(E x) {
        root = removeNormal(x, root);
    }

    /**
     * 删除指定树中的节点.如果删除的节点不存在则不作任何操作.非递归实现.
     *
     * @param x 待删除节点的值.
     * @param t 指定树的根.
     * @return 删除节点后的新树的根节点.
     */
    private BinaryNode<E> removeNormal(E x, BinaryNode<E> t) {
        if (t == null) {
            return t;   // Item not found; do nothing
        }

        BinaryNode<E> tmp = t;
        while (tmp != null) {
            int compareResult = x.compareTo(tmp.element);

            if (compareResult < 0) {
                tmp = tmp.left;
            } else if (compareResult > 0) {
                tmp = tmp.right;
            } else if (tmp.left != null && tmp.right != null) // Two children
            {
                BinaryNode<E> l = tmp;
                tmp = tmp.right;
                BinaryNode<E> p = tmp;
                while (tmp.left != null) {
                    p = tmp;
                    tmp = tmp.left;
                }
                l.element = tmp.element;
                p.left = tmp.right;
                tmp = null;


            } else {
                tmp = (tmp.left != null) ? tmp.left : tmp.right;
            }
        }
        return t;
    }

    /**
     * 在该树中搜索最小的节点，递归实现.
     *
     * @return 最小节点的值.
     */
    public E findMin() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        return findMin(root).element;
    }

    /**
     * 在指定树中搜索最小节点，递归实现.
     *
     * @param t 指定树的根.
     * @return 最小节点.
     */
    private BinaryNode<E> findMin(BinaryNode<E> t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        }
        return findMin(t.left);
    }

    /**
     * 在该树中搜索最小的节点，非递归实现.
     *
     * @return 最小节点的值.
     */
    public E findMinNormal() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        return findMinNormal(root).element;
    }

    /**
     * 在指定树中搜索最小节点，非递归实现.
     *
     * @param t 指定树的根.
     * @return 最小节点.
     */
    private BinaryNode<E> findMinNormal(BinaryNode<E> t) {
        if (t != null) {
            while (t.left != null) {
                t = t.left;
            }
        }

        return t;
    }

    /**
     * 在该树中搜索最大的节点，递归实现.
     *
     * @return 最大的节点的值.
     */
    public E findMax() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        return findMax(root).element;
    }

    /**
     * 在指定树中搜索最大节点，递归实现.
     *
     * @param t 指定树的根节点.
     * @return 最大节点.
     */
    private BinaryNode<E> findMax(BinaryNode<E> t) {
        if (t == null) {
            return null;
        } else if (t.right == null) {
            return t;
        }
        return findMin(t.right);
    }

    /**
     * 在该树中搜索最大的节点，非递归实现.
     *
     * @return 最大的节点的值.
     */
    public E findMaxNormal() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        return findMaxNormal(root).element;
    }

    /**
     * 在指定树中搜索最大节点，非递归实现.
     *
     * @param t 指定树的根节点.
     * @return 最大节点.
     */
    private BinaryNode<E> findMaxNormal(BinaryNode<E> t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }

        return t;
    }

    /**
     * 该树中是否包含某节点，递归实现.
     *
     * @param x 节点的值.
     * @return 包含返回true，否则返回false.
     */
    public boolean contains(E x) {
        return contains(x, root);
    }

    /**
     * 指定树中是否包含某节点，递归实现.
     *
     * @param x 节点的值.
     * @param t 指定树的根节点.
     * @return 包含返回true，否则返回false.
     */
    private boolean contains(E x, BinaryNode<E> t) {
        if (t == null) {
            return false;
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            return contains(x, t.left);
        } else if (compareResult > 0) {
            return contains(x, t.right);
        } else {
            return true;    // Match
        }
    }

    /**
     * 该树中是否包含某节点，非递归实现.
     *
     * @param x 节点的值.
     * @return 包含返回true，否则返回false.
     */
    public boolean containsNormal(E x) {
        return containsNormal(x, root);
    }

    /**
     * 指定树中是否包含某节点，非递归实现.
     *
     * @param x 节点的值.
     * @param t 指定树的根节点.
     * @return 包含返回true，否则返回false.
     */
    private boolean containsNormal(E x, BinaryNode<E> t) {
        if (t == null) {
            return false;
        }
        BinaryNode<E> tmp = t;
        while (tmp != null) {
            int compareResult = x.compareTo(tmp.element);

            if (compareResult < 0) {
                tmp = tmp.left;
            } else if (compareResult > 0) {
                tmp = tmp.right;
            } else {
                return true;    // Match
            }
        }
        return false;
    }

    /**
     * 清空树.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * 树是否为空.
     *
     * @return 为空true, 否则false.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 中序打印树.
     */
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            printTree(root);
        }
    }

    /**
     * 中序打印树.
     *
     * @param t 树的根.
     */
    private void printTree(BinaryNode<E> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * 计算树的高.
     *
     * @param t 树的根.
     */
    private int height(BinaryNode<E> t) {
        if (t == null) {
            return -1;
        } else {
            return 1 + Math.max(height(t.left), height(t.right));
        }
    }

    /**
     * 树节点类.二叉树的节点都是一样的，使其成为内部类主要是为了测试方便，以及各个测试类文件之间的独立。
     * 每个类文件都可以拷贝到其他地方运行。
     */
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
        LinkedList<BinarySearchTree<E>> binaryTrees = new LinkedList<>();
        if (treeStack.isEmpty()) {
            return;
        }
        while (!treeStack.isEmpty()) {
            BinarySearchTree<E> tree = treeStack.pop();
            System.out.print(tree.root.element + " ");
            binaryTrees.addFirst(tree);
        }
        System.out.println();
        for (BinarySearchTree<E> binarySearchTree : binaryTrees) {
            if (binarySearchTree.root.right != null) {
                treeStack.push(new BinarySearchTree<>(binarySearchTree.root.right));
            }
            if (binarySearchTree.root.left != null) {
                treeStack.push(new BinarySearchTree<>(binarySearchTree.root.left));
            }
        }
        printLevelTree();
    }

    // 测试主函数
    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        int[] ary = new int[]{6, 2, 9, 1, 5, 8, 4};
        for (int i : ary) {
            t.insert(i);
        }

        System.out.print("搜索二叉树，");
		t.levelTraversal();

        System.out.print("插入3后，");
        t.insert(3);
        t.levelTraversal();

        t.remove(2);
        System.out.print("删除2后，");
        t.levelTraversal();

        System.out.println("树中最小节点的值(递归)："+t.findMin());
        System.out.println("树中最小节点的值(非递归)："+t.findMaxNormal());
        System.out.println("树中最大节点的值(递归)："+t.findMax());
        System.out.println("树中最大节点的值(非递归)："+t.findMaxNormal());
        System.out.println("树中是否包含4(递归)："+t.contains(4));
        System.out.println("树中是否包含4(非递归)："+t.contains(4));
    }
}
