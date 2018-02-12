package redblacktree;

import java.util.Comparator;

import static redblacktree.RBNode.Color.BLACK;
import static redblacktree.RBNode.Color.RED;

public class RBTree<K extends Comparable<K>, V> {

    private RBNode<K, V> root;

    private Comparator<? super K> comparator;

    public RBTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    public void insert(K key, V value) {
        root = insert(null, root, key, value);
    }

    private RBNode<K, V> insert(RBNode<K, V> parent, RBNode<K, V> currentRoot, K key, V value) {
        if (currentRoot == null || currentRoot.isNil) {
            if (parent == null) {
                return NodeUtil.createBlackNode(key, value);
            } else {
                return NodeUtil.createRedNode(parent, key, value);
            }
        }

        int compareResult = comparator.compare(key, root.key);
        boolean wentToLeft;
        if (compareResult < 0) {
            RBNode<K, V> left = insert(currentRoot, currentRoot.left, key, value);
            if (left == currentRoot.parent) { return left; }
            root.left = left;
            wentToLeft = true;
        } else if (compareResult > 0) {
            RBNode<K, V> right = insert(currentRoot, currentRoot.left, key, value);
            if (right == currentRoot.parent) { return right; }
            currentRoot.right = right;
            wentToLeft = false;
        } else {
            throw new IllegalArgumentException("Duplicate key");
        }

        if (wentToLeft) {
            checkLeftRedRedConflict(currentRoot);
        } else {
            checkRightRedRedConflict(currentRoot);
        }

        return root;
    }

    private void delete(RBNode<K, V> root, K key) {
        if (root == null || root.isNil) {
            return;
        }

        int compareResult = comparator.compare(key, root.key);
        if (compareResult < 0) {
            delete(root.left, key);
        } else if (compareResult > 0) {
            delete(root.right, key);
        } else {

        }
    }

    private void checkLeftRedRedConflict(RBNode<K, V> root) {
        if (root.color == RED && root.left.color == RED) {
            RBNode<K, V> sibling = sibling(root);
            if (sibling == null || sibling.color == BLACK) {
                if (isLeftChild(root)) {
                    rotateRight(root, true);
                } else {
                    rotateRight(root.left, false);
                    root = root.parent;
                    rotateLeft(root, true);
                }
            } else {
                fixColors(root, sibling);
            }
        }
    }

    private void checkRightRedRedConflict(RBNode<K, V> root) {
        if (root.color == RED && root.right.color == RED) {
            RBNode<K, V> sibling = sibling(root);
            if (sibling == null || sibling.color == BLACK) {
                if (!isLeftChild(root)) {
                    rotateLeft(root, true);
                } else {
                    rotateLeft(root.right, false);
                    root = root.parent;
                    rotateRight(root, true);
                }
            } else {
                fixColors(root, sibling);
            }
        }
    }


    private void rotateRight(RBNode<K, V> root, boolean changeColors) {
        RBNode<K, V> parent = root.parent;
        root.parent = parent.parent;
        if (parent.parent != null) {
            if (parent.parent.right == parent) {
                parent.parent.right = root;
            } else {
                parent.parent.left = root;
            }
        }

        RBNode<K, V> right = root.right;
        root.right = parent;
        parent.parent = root;
        parent.left = right;
        if(right != null) {
            right.parent = parent;
        }

        if (changeColors) {
            root.color = BLACK;
            parent.color = RED;
        }
    }

    private void rotateLeft(RBNode<K, V> root, boolean changeColors) {
        RBNode<K, V> parent = root.parent;
        root.parent = parent.parent;
        if(parent.parent != null) {
            if(parent.parent.right == parent) {
                parent.parent.right = root;
            } else {
                parent.parent.left = root;
            }
        }
        RBNode<K, V> left = root.left;
        root.left = parent;
        parent.parent = root;
        parent.right = left;
        if(left != null) {
            left.parent = parent;
        }
        if(changeColors) {
            root.color = BLACK;
            parent.color = RED;
        }
    }

    private RBNode<K, V> sibling(RBNode<K, V> node) {
        if (isLeftChild(node)) {
            return findRightSibling(node);
        }

        if (isRightChild(node)) {
            return findLeftSibling(node);
        }

        return null;
    }


    private boolean isLeftChild(RBNode<K, V> node) {
        return node.parent.left == node;
    }

    private boolean isRightChild(RBNode<K, V> node) {
        return node.parent.right == node;
    }

    private RBNode<K, V> findLeftSibling(RBNode<K, V> node) {
        return node.parent.left.isNil ? null : node.parent.left;
    }

    private RBNode<K, V> findRightSibling(RBNode<K, V> node) {
        return node.parent.right.isNil ? null : node.parent.right;
    }

    private void fixColors(RBNode<K, V> root, RBNode<K, V> rootSibling) {
        root.color = BLACK;
        rootSibling.color = BLACK;
        if (root.parent.parent != null) {
            root.parent.color = RED;
        }
    }
}
