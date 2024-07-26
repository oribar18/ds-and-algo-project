public class GraphNode {
    private int key;
    private Node<GraphNode> nodeInList;
    private DoublyLinkedList<GraphEdge> inEdges;
    private DoublyLinkedList<GraphEdge> outEdges;
    private TreeNode bfsNode;
    private TreeNode dfsNode;

    public GraphNode(int key){
        this.key = key;
        this.nodeInList = null;
        this.inEdges = new DoublyLinkedList<GraphEdge>();
        this.outEdges = new DoublyLinkedList<GraphEdge>();
        this.bfsNode = null;
        this.dfsNode = null;
    }

    public GraphNode(int key, Node<GraphNode> node){
        this.key = key;
        this.nodeInList = node;
        this.inEdges = new DoublyLinkedList<GraphEdge>();
        this.outEdges = new DoublyLinkedList<GraphEdge>();
        this.bfsNode = null;
        this.dfsNode = null;
    }

    public int getOutDegree(){
        int numOfEdges = 0;
        if (outEdges.getHead() == null){
            return numOfEdges;
        }
        Node<GraphEdge> ptr = outEdges.getHead();
        while (ptr!=null){
            numOfEdges++;
            ptr = ptr.getNext();
        }
        return numOfEdges;
    }

    public int getInDegree(){
        int numOfEdges = 0;
        if (inEdges.getHead() == null){
            return numOfEdges;
        }
        Node<GraphEdge> ptr = inEdges.getHead();
        while (ptr!=null){
            numOfEdges++;
            ptr = ptr.getNext();
        }
        return numOfEdges;
    }

    public int getKey(){
        return this.key;
    }

    public Node<GraphNode> getNodeInList() { return this.nodeInList; }

    public void setKey(int key) {
        this.key = key;
    }

    public void setNodeInList(Node<GraphNode> nodeInList) {
        this.nodeInList = nodeInList;
    }

    public DoublyLinkedList<GraphEdge> getInEdges() {
        return inEdges;
    }

    public void setInEdges(DoublyLinkedList<GraphEdge> inEdges) {
        this.inEdges = inEdges;
    }

    public DoublyLinkedList<GraphEdge> getOutEdges() {
        return outEdges;
    }

    public void setOutEdges(DoublyLinkedList<GraphEdge> outEdges) {
        this.outEdges = outEdges;
    }

    public TreeNode getBfsNode() {
        return bfsNode;
    }

    public void setBfsNode(TreeNode bfsNode) {
        this.bfsNode = bfsNode;
    }

    public TreeNode getDfsNode() {
        return dfsNode;
    }

    public void setDfsNode(TreeNode dfsNode) {
        this.dfsNode = dfsNode;
    }


}
