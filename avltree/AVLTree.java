package avltree;

import linkedlist.LinkedList;

import java.util.Comparator;

public class AVLTree<K extends Comparable<K>, V> {

    private TreeNode<K, V> root;

    private Comparator<? super K> comparator;


    public AVLTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }


    public void insert(K k, V v) {
        TreeNode<K, V> node = new TreeNode<K, V>(k, v);
        root = insert(node, root);
    }


    public void remove(K k, V v) {
        TreeNode<K, V> node = new TreeNode<K, V>(k, v);
        root = remove(node, root);
    }

    public LinkedList<K> getInorderElementList() {
        LinkedList<K> list = new LinkedList<>();
        return inorderTraversal(root, list);
    }


    private TreeNode<K, V> insert(TreeNode<K, V> newNode, TreeNode<K, V> currentRoot) {
        if(currentRoot == null) {
            return newNode;
        }

        if (comparator.compare(newNode.key, currentRoot.key) <= 0) {
            currentRoot.left = insert(newNode, currentRoot.left);
        } else {
            currentRoot.right = insert(newNode, currentRoot.right);
        }

        return balance(currentRoot);
    }


    private TreeNode<K, V> remove(TreeNode<K, V> nodeToDelete, TreeNode<K, V> currentRoot) {
         if (nodeToDelete == null) return null;

         if (comparator.compare(nodeToDelete.key, currentRoot.key) < 0) {
             currentRoot.left = remove(nodeToDelete, currentRoot.left);
         } else if (comparator.compare(nodeToDelete.key, currentRoot.key) > 0) {
             currentRoot.right = remove(nodeToDelete, currentRoot.right);
         } else {
             TreeNode<K, V> left = currentRoot.left;
             TreeNode<K, V> right = currentRoot.right;
             currentRoot = null;

             if (right == null) return left;

             TreeNode<K, V> min = findMin(right);
             min.right = removeMin(right);
             min.left = left;

             return balance(min);
         }

         return balance(currentRoot);
    }


    private TreeNode<K, V> balance(TreeNode<K, V> node) {
        if (node == null) {
            return null;
        }

        fixHeight(node);

        if (balanceFactor(node) == 2) {
            if (balanceFactor(node.left) < 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        if (balanceFactor(node) == -2) {
            if (balanceFactor(node.right) > 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        return node;
    }


    private TreeNode<K, V> rotateLeft(TreeNode<K, V> node) {
        TreeNode<K, V> newRoot = node.right;
        TreeNode<K, V> tempChild = newRoot.left;
        node.right = tempChild;
        newRoot.left = node;
        return newRoot;
    }


    private TreeNode<K, V> rotateRight(TreeNode<K, V> node) {
        TreeNode<K, V> newRoot = node.left;
        TreeNode<K, V> tempChild = newRoot.right;
        newRoot.right = node;
        node.left = tempChild;
        return newRoot;
    }


    private int balanceFactor(TreeNode<K, V> node) {
        return getNodeHeight(node.right) - getNodeHeight(node.left);
    }


    private void fixHeight(TreeNode<K, V> node) {
        int leftSubtreeHeight = getNodeHeight(node.left);
        int rightSubtreeHeight = getNodeHeight(node.right);
        node.height = (leftSubtreeHeight > rightSubtreeHeight ? leftSubtreeHeight : rightSubtreeHeight) + 1;
    }

    private int getNodeHeight(TreeNode<K, V> node) {
        return node == null ? 0 : node.height;
    }


    private TreeNode<K,V> removeMin(TreeNode<K, V> currentRoot) {
        return null;
    }

    private TreeNode<K,V> findMin(TreeNode<K, V> currentRoot) {
        return currentRoot.left == null ? currentRoot : findMin(currentRoot.left);
    }


    private LinkedList<K> inorderTraversal(TreeNode<K, V> node, LinkedList<K> list) {
        if (root == null) return list;

        list = inorderTraversal(node, list);
        list.add(node.key);
        list = inorderTraversal(node, list);

        return list;
    }
}

