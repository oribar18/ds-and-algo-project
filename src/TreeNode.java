public class TreeNode {
    private Node<TreeNode> nodeInList;
    private GraphNode node;
    private String color;
    private int distance;
    private int discovery;
    private int retraction;
    private TreeNode parent;
    private TreeNode leftChild;
    private TreeNode rightSibling;


    public TreeNode(GraphNode node){
        this.node = node;
        this.nodeInList = null;
        this.color = "white";
        this.distance = Integer.MAX_VALUE;
        this.discovery = 0;
        this.retraction = 0;
        this.parent = null;
        this.leftChild = null;
        this.rightSibling = null;
    }

    public void setNodeInList(Node<TreeNode> nodeInList) {
        this.nodeInList = nodeInList;
    }

    public GraphNode getNode() {
        return node;
    }

    public void setNode(GraphNode node) {
        this.node = node;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDiscovery() {
        return discovery;
    }

    public void setDiscovery(int discovery) {
        this.discovery = discovery;
    }

    public int getRetraction() {
        return retraction;
    }

    public void setRetraction(int retraction) {
        this.retraction = retraction;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightSibling() {
        return rightSibling;
    }

    public void setRightSibling(TreeNode rightSibling) {
        this.rightSibling = rightSibling;
    }

    public Node<TreeNode> getNodeInList() { return this.nodeInList; }

}

