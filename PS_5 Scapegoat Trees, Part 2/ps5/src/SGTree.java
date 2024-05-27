/**
 * ScapeGoat Tree class
 * <p>
 * This class contains some basic code for implementing a ScapeGoat tree. This version does not include any of the
 * functionality for choosing which node to scapegoat. It includes only code for inserting a node, and the code for
 * rebuilding a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     * <p>
     * This class holds the data for a node in a binary tree.
     * <p>
     * Note: we have made things public here to facilitate problem set grading/testing. In general, making everything
     * public like this is a bad idea!
     */
    public static class TreeNode {
        int key;

        int weight;
        public TreeNode left = null;
        public TreeNode right = null;


        TreeNode(int k) {
            key = k;
            weight = 1;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Counts the number of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node, Child child) {
        // TODO: Implement this
        if (node == null) {
            return 0;
        } else {
            if (child == Child.LEFT) {
                if (node.left == null) {
                    return 0;
                }
                return countNodes(node.left, Child.LEFT) + countNodes(node.left, Child.RIGHT)  + 1;
            } else if (child == Child.RIGHT){
                if (node.right == null) {
                    return 0;
                }
                return countNodes(node.right, Child.LEFT) + countNodes(node.right, Child.RIGHT)  + 1;
            }
        }
        return 0;
    }

    /**
     * Builds an array of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */
    public TreeNode[] enumerateNodes(TreeNode node, Child child) {
        int numOfNodes = countNodes(node, child);
        TreeNode[] array = new TreeNode[numOfNodes];
        int[] index = new int[]{0};
        if (child == Child.RIGHT) {
            helperEnumerate(node.right, index, array);
        } else {
            helperEnumerate(node.left, index, array);
        }
        return array;
    }

    public void helperEnumerate(TreeNode node, int[] index, TreeNode[] list) {
        if (node == null) {
            return;
        } else {
            helperEnumerate(node.left, index, list);
            list[index[0]] = node;
            index[0] += 1;
            helperEnumerate(node.right, index, list);
        }
    }

    /**
     * Builds a tree from the list of nodes Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    public TreeNode buildTree(TreeNode[] nodeList) {
        if (nodeList == null) {
            return null;
        } else {
            return helperBuild(nodeList, 0, nodeList.length-1);
        }
    }

    public TreeNode helperBuild(TreeNode[] nodeList, int begin, int end) {
        if (begin > end) {
            return null;
        }
        int mid = begin + (end-begin)/2;
        TreeNode root = nodeList[mid];
        root.left = helperBuild(nodeList, begin, mid-1);
        int temp1 = 0;
        if (root.left != null) {
            temp1 = root.left.weight;
        }
        root.right = helperBuild(nodeList, mid + 1, end);
        int temp2 = 0;
        if (root.right != null) {
            temp2 = root.right.weight;
        }
        root.weight = temp1 + temp2 + 1;
        return root;
    }

    /**
     * Determines if a node is balanced. If the node is balanced, this should return true. Otherwise, it should return
     * false. A node is unbalanced if either of its children has weight greater than 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public boolean checkBalance(TreeNode node) {
        if (node != null) {
            if (node.left != null) {
                if (node.left.weight > 2*node.weight/3) {
                    return false;
                }
            }
            if (node.right != null) {
                if (node.right.weight > 2*node.weight/3) {
                    return false;
                }
            }
            return true;
        }

        return true;
    }

    /**
     * Rebuilds the specified subtree of a node.
     *
     * @param node  the part of the subtree to rebuild
     * @param child specifies which child is the root of the subtree to rebuild
     */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    /**
     * Inserts a key into the tree.
     *
     * @param key the key to insert
     */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;
        Child[] path = new Child[countNodes(root, Child.RIGHT) + countNodes(root, Child.LEFT) + 1];
        int i = 0;

        while (true) {
            node.weight += 1;
            if (key <= node.key) {
                if (node.left == null) break;
                path[i++] = Child.LEFT;
                node = node.left;
            } else {
                if (node.right == null) break;
                path[i++] = Child.RIGHT;
                node = node.right;
            }
        }
        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }

        TreeNode node2 = root;
        int j = 0;
        while (j < i) {
            if (path[j++] == Child.LEFT) {
                if (checkBalance(node2.left)) {
                    node2 = node2.left;
                } else {
                    rebuild(node2, Child.LEFT);
                    return;
                }
            } else {
                if (checkBalance(node2.right)) {
                    node2 = node2.right;
                } else {
                    rebuild(node2, Child.RIGHT);
                    return;
                }
            }
        }
    }

    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        tree.rebuild(tree.root, Child.RIGHT);

//        for (TreeNode node: tree.enumerateNodes(tree.root, Child.RIGHT)) {
//            System.out.print(node.key);
//            System.out.print(" ");
//            System.out.print(node.weight);
//            System.out.println(" ");
//        }
    }
}
