package org.geeksword.xwy.JavaLearn;

import java.util.LinkedList;
import java.util.Queue;

public class MyTree {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BinaryOrderTree<Integer> tree = new BinaryOrderTree<Integer>();
        tree.insertTreeNode(2);
        tree.insertTreeNode(1);
        tree.insertTreeNode(0);
        tree.insertTreeNode(9);
        tree.insertTreeNode(6);
        tree.insertTreeNode(7);
        tree.insertTreeNode(5);
        tree.insertTreeNode(8);
        tree.insertTreeNode(3);
        tree.insertTreeNode(4);
        System.out.print("前序遍历（递归）:");
        tree.preOrderTraverse(tree.getRoot());
        System.out.println();
        System.out.print("中序遍历（递归）:");
        tree.midOrderTraverse(tree.getRoot());
        System.out.println();
        System.out.print("后序遍历（递归）:");
        tree.postOrderTraverse(tree.getRoot());
        System.out.println();
        System.out.print("前序遍历（非递归）:");
        tree.preOrderTraverseNo(tree.getRoot());
        System.out.println();
        System.out.print("中序遍历（非递归）:");
        tree.midOrderTraverseNo(tree.getRoot());
        System.out.println();
        System.out.print("后序遍历（非递归）:");
        tree.postOrderTraverseNo(tree.getRoot());
        System.out.println();
        System.out.print("广度优先遍历:");
        tree.breadthFirstTraverse(tree.getRoot());
    }

}

//首先定义节点类
class TreeNode<E extends Comparable<E>> {
    E value;
    TreeNode<E> left;
    TreeNode<E> right;

    TreeNode(E value) {
        this.value = value;
        left = null;
        right = null;
    }
}

//通过前序插入节点，构造二叉排序树
class BinaryOrderTree<E extends Comparable<E>> {
    private TreeNode<E> root;

    BinaryOrderTree() {
        root = null;
    }

    public void insertTreeNode(E value) {
        if (root == null) {
            root = new TreeNode<E>(value);
            return;
        }
        TreeNode<E> currentNode = root;
        while (true) {
            if (value.compareTo(currentNode.value) > 0) {
                if (currentNode.right == null) {
                    currentNode.right = new TreeNode<E>(value);
                    break;
                }
                currentNode = currentNode.right;
            } else {
                if (currentNode.left == null) {
                    currentNode.left = new TreeNode<E>(value);
                    break;
                }
                currentNode = currentNode.left;
            }
        }
    }

    public TreeNode<E> getRoot() {
        return root;
    }

    //前序遍历（递归）
    public void preOrderTraverse(TreeNode<E> node) {
        System.out.print(node.value + " ");
        if (node.left != null)
            preOrderTraverse(node.left);
        if (node.right != null)
            preOrderTraverse(node.right);
    }

    //中序遍历（递归）
    public void midOrderTraverse(TreeNode<E> node) {
        if (node.left != null)
            midOrderTraverse(node.left);
        System.out.print(node.value + " ");
        if (node.right != null)
            midOrderTraverse(node.right);
    }

    //后序遍历（递归）
    public void postOrderTraverse(TreeNode<E> node) {
        if (node.left != null)
            postOrderTraverse(node.left);
        if (node.right != null)
            postOrderTraverse(node.right);
        System.out.print(node.value + " ");
    }

    //前序遍历（非递归）
    public void preOrderTraverseNo(TreeNode<E> root) {
        LinkedList<TreeNode<E>> list = new LinkedList<TreeNode<E>>();
        TreeNode<E> currentNode = null;
        list.push(root);
        while (!list.isEmpty()) {
            currentNode = list.pop();
            System.out.print(currentNode.value + " ");
            if (currentNode.right != null)
                list.push(currentNode.right);
            if (currentNode.left != null)
                list.push(currentNode.left);
        }
    }

    public void midOrderTraverseNo(TreeNode<E> root) {
        LinkedList<TreeNode<E>> list = new LinkedList<TreeNode<E>>();
        TreeNode<E> currentNode = root;
        while (currentNode != null || !list.isEmpty()) {
            while (currentNode != null) {
                list.push(currentNode);
                currentNode = currentNode.left;
            }
            currentNode = list.pop();
            System.out.print(currentNode.value + " ");
            currentNode = currentNode.right;
        }
    }

    public void postOrderTraverseNo(TreeNode<E> root) {
        LinkedList<TreeNode<E>> list = new LinkedList<TreeNode<E>>();
        TreeNode<E> currentNode = root;
        TreeNode<E> rightNode = null;
        while (currentNode != null || !list.isEmpty()) {
            while (currentNode != null) {
                list.push(currentNode);
                currentNode = currentNode.left;
            }
            currentNode = list.pop();
            while (currentNode.right == null || currentNode.right == rightNode) {
                System.out.print(currentNode.value + " ");
                rightNode = currentNode;
                if (list.isEmpty()) {
                    return; //root以输出，则遍历结束
                }
                currentNode = list.pop();
            }
            list.push(currentNode); //还有右结点没有遍历
            currentNode = currentNode.right;
        }
    }

    //广度优先遍历，使用队列
    public void breadthFirstTraverse(TreeNode<E> root) {
        Queue<TreeNode<E>> queue = new LinkedList<TreeNode<E>>();
        TreeNode<E> currentNode = null;
        queue.offer(root);
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            System.out.print(currentNode.value + " ");
            if (currentNode.left != null)
                queue.offer(currentNode.left);
            if (currentNode.right != null)
                queue.offer(currentNode.right);
        }
    }
}
