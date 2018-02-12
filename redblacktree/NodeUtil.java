package redblacktree;


public class NodeUtil {

    public static <K, V> RBNode<K, V> createBlackNode(K key, V value) {
        RBNode<K, V> node = new RBNode<K, V>(key, value);
        node.color = RBNode.Color.BLACK;
        node.left = createNilNode(node);
        node.right = createNilNode(node);
        return node;
    }

    public static <K, V> RBNode<K, V> createRedNode(RBNode<K, V> parent, K key, V value) {
        RBNode<K, V> node = new RBNode<K, V>(key, value);
        node.color = RBNode.Color.RED;
        node.parent = parent;
        node.left = createNilNode(node);
        node.right = createNilNode(node);
        return node;
    }

    public static <K, V> RBNode<K, V> createNilNode(RBNode<K, V> parent) {
        RBNode<K, V> node = new RBNode<>();
        node.color = RBNode.Color.BLACK;
        node.parent = parent;
        node.isNil = true;
        return node;
    }

}
