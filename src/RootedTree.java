import java.io.DataOutputStream;
import java.io.IOException;

public class RootedTree {
    private TreeNode root;
    private int counter = 0;

    public RootedTree() {
        this.root = null;
    }

    public void printByLayer(DataOutputStream out) {
        try {
            if (root == null){
                return;
            }
            out.writeBytes(String.valueOf(root.getNode().getKey()));
            Queue sons = new Queue();
            Queue grandSons = new Queue();
            TreeNode child = root.getLeftChild();
            if (child != null){
                out.writeBytes(System.lineSeparator());
            }
            while (child != null) {
                sons.enqueue(child);
                child = child.getRightSibling();
            }
            while (sons.getTail() != null) {
                TreeNode father = sons.dequeue();
                out.writeBytes(String.valueOf(father.getNode().getKey()));
                TreeNode grandSon = father.getLeftChild();
                while (grandSon != null) {
                    grandSons.enqueue(grandSon);
                    grandSon = grandSon.getRightSibling();
                }
                if (sons.getTail() == null) {
                    if (grandSons.getTail() != null){
                        out.writeBytes(System.lineSeparator());
                    }
                    while (grandSons.getTail() != null) {
                        TreeNode son = grandSons.dequeue();
                        sons.enqueue(son);
                    }
                } else {
                    out.writeBytes(",");
                }
            }
        }
        catch (IOException exception){
            return;
        }
    }

    public void preorderPrint(DataOutputStream out){
        TreeNode tempRoot = root;
        int numOfNodes = countNodes(tempRoot);
        preOrder(tempRoot, numOfNodes, out);
        counter = 0;
    }

    public void preOrder(TreeNode tempRoot, int numOfNodes, DataOutputStream out){
        try {
            if (tempRoot == null) {
                return;
            }
            counter++;
            out.writeBytes(String.valueOf(tempRoot.getNode().getKey()));
            if (counter != numOfNodes) {
                out.writeBytes(",");
            }
            preOrder(tempRoot.getLeftChild(), numOfNodes, out);
            preOrder(tempRoot.getRightSibling(), numOfNodes, out);
        } catch (IOException exception){
            return;
        }
    }

    public int countNodes(TreeNode tempRoot){
        if(tempRoot == null){
            return 0;
        }
        return 1 + countNodes(tempRoot.getLeftChild()) + countNodes(tempRoot.getRightSibling());
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
}


