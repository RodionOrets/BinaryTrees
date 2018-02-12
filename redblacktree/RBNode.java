package redblacktree;


public class RBNode<K, V> {

    public enum Color {
        RED, BLACK;
    }

    K key;
    V value;

    RBNode<K, V> left;
    RBNode<K, V> right;
    RBNode<K, V> parent;

    Color color;
    boolean isNil;

    RBNode() {}

    RBNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
