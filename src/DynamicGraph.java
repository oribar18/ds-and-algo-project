public class DynamicGraph {
    private DoublyLinkedList<GraphNode> nodesList;
    private DoublyLinkedList<GraphNode> retractionList;
    private int time = 0;

    public DynamicGraph() {
        this.nodesList = new DoublyLinkedList<GraphNode>();
    }

    public GraphNode insertNode(int nodeKey) {
        GraphNode new_node = new GraphNode(nodeKey, nodesList.addNode(null));
        new_node.getNodeInList().setObj(new_node);
        return new_node;
    }

    public void deleteNode(GraphNode node) {
        if (node.getOutDegree() != 0 || node.getInDegree() != 0) {
            return;
        }
        nodesList.deleteNode(node);
    }

        public GraphEdge insertEdge (GraphNode from, GraphNode to){
            GraphEdge new_edge = new
                    GraphEdge(from, to, from.getOutEdges().addNode(null), to.getInEdges().addNode(null));
            new_edge.getNodeOutEdgeList().setObj(new_edge);
            new_edge.getNodeInEdgeList().setObj(new_edge);
            return new_edge;
        }

        public void deleteEdge (GraphEdge edge){
            edge.getFrom().getOutEdges().deleteNode(edge);
            edge.setNodeInEdgeList(null);
            edge.setNodeOutEdgeList(null);
            edge.setFrom(null);
            edge.setTo(null);

        }

        public RootedTree bfs(GraphNode source){
            RootedTree bfsTree = new RootedTree();
            Queue queue = new Queue();
            bfsInitialization(source, queue);
            bfsTree.setRoot(source.getBfsNode());
            while (queue.getTail() != null) {
                TreeNode currentNode = queue.dequeue();
                if (currentNode.getNode().getOutEdges().getTail() != null) {
                    Node<GraphEdge> neighbor = currentNode.getNode().getOutEdges().getTail();
                    boolean firstChild = true;
                    while (neighbor != null) {
                        if (neighbor.getObj().getTo().getBfsNode().getColor() == "white") {
                            neighbor.getObj().getTo().getBfsNode().setColor("grey");
                            neighbor.getObj().getTo().getBfsNode().setDistance(currentNode.getDistance() + 1);
                            neighbor.getObj().getTo().getBfsNode().setParent(currentNode);
                            queue.enqueue(neighbor.getObj().getTo().getBfsNode());
                            if (firstChild) {
                                currentNode.setLeftChild(neighbor.getObj().getTo().getBfsNode());
                                firstChild = false;
                            } else {
                                TreeNode needSibling = currentNode.getLeftChild();
                                while (needSibling.getRightSibling() != null) {
                                    needSibling = needSibling.getRightSibling();
                                }
                                needSibling.setRightSibling(neighbor.getObj().getTo().getBfsNode());
                            }
                        }
                        neighbor = neighbor.getPrev();
                    }
                }
                currentNode.setColor("black");
            }
            return bfsTree;
        }

        public void bfsInitialization(GraphNode source,Queue queue){
            Node<GraphNode> node = nodesList.getHead();
            while (node != null){
                TreeNode treeNode = new TreeNode(node.getObj());
                node.getObj().setBfsNode(treeNode);
                if (node.getNext() != null){
                    node = node.getNext();
                }
               else {break;}
                if (node == source.getNodeInList()){
                    if (node.getNext() != null) {
                        node = node.getNext();
                    }
                }
            }
            TreeNode root = new TreeNode(source);
            source.setBfsNode(root);
            root.setColor("grey");
            root.setDistance(0);
            queue.enqueue(root);
        }

        public RootedTree scc() {
            RootedTree sccTree = new RootedTree();
            TreeNode virtualNode = new TreeNode(new GraphNode(0));
            sccTree.setRoot(virtualNode);
            retractionList = new DoublyLinkedList<>();
            dfs(nodesList, false);
            DoublyLinkedList<RootedTree> forest = dfs(retractionList, true);
            if (forest.getHead().getObj() != null) {
                Node<RootedTree> subTree = forest.getHead();
                TreeNode child = subTree.getObj().getRoot();
                sccTree.getRoot().setLeftChild(child);
                Node<RootedTree> nextSubTree = subTree.getNext();
                while (nextSubTree != null) {
                    child.setRightSibling(nextSubTree.getObj().getRoot());
                    child = nextSubTree.getObj().getRoot();
                    nextSubTree = nextSubTree.getNext();
                }
            }
            return sccTree;
        }

        public DoublyLinkedList<RootedTree> dfs(DoublyLinkedList<GraphNode> listOfNodes, boolean transpose){
            DoublyLinkedList<RootedTree> forest = new DoublyLinkedList<>();
            Node<GraphNode> node = listOfNodes.getTail();
            while (node != null){
                TreeNode treeNode = new TreeNode(node.getObj());
                node.getObj().setDfsNode(treeNode);
                if (node.getPrev() != null){
                    node = node.getPrev();
                }
                else {break;}
            }
            node = listOfNodes.getTail();
            while (node != null){
                if (node.getObj().getDfsNode().getColor() == "white"){
                    RootedTree dfsTree = new RootedTree();
                    dfsTree.setRoot(node.getObj().getDfsNode());
                    dfsVisit(node.getObj(), transpose);
                    forest.addNode(dfsTree);
                }
                node = node.getPrev();
            }
            time = 0;
            return forest;
    }

    public void dfsVisit(GraphNode node, boolean transpose) {
        time = time + 1;
        TreeNode currentNode = node.getDfsNode();
        currentNode.setDiscovery(time);
        currentNode.setColor("grey");
        if (transpose == false){
            if (currentNode.getNode().getOutEdges().getTail() != null) {
                Node<GraphEdge> neighbor = currentNode.getNode().getOutEdges().getTail();
                boolean firstChild = true;
                while (neighbor != null) {
                    if (firstChild) {
                        if (neighbor.getObj().getTo().getDfsNode().getColor() == "white") {
                            currentNode.setLeftChild(neighbor.getObj().getTo().getDfsNode());
                            neighbor.getObj().getTo().getDfsNode().setParent(currentNode);
                            firstChild = false;
                            dfsVisit(neighbor.getObj().getTo(), transpose);
                        }
                    }
                    else{
                        if (neighbor.getObj().getTo().getDfsNode().getColor() == "white") {
                            TreeNode needSibling = currentNode.getLeftChild();
                            while (needSibling.getRightSibling() != null){
                                needSibling = needSibling.getRightSibling();
                            }
                            needSibling.setRightSibling(neighbor.getObj().getTo().getDfsNode());
                            neighbor.getObj().getTo().getDfsNode().setParent(currentNode);
                            dfsVisit(neighbor.getObj().getTo(), transpose);
                        }
                    }
                    neighbor = neighbor.getPrev();
                    }
                }
            }
        else{
            if (currentNode.getNode().getInEdges().getTail() != null) {
                Node<GraphEdge> neighbor = currentNode.getNode().getInEdges().getTail();
                boolean firstChild = true;
                while (neighbor != null) {
                    if (firstChild) {
                        if (neighbor.getObj().getFrom().getDfsNode().getColor() == "white") {
                            currentNode.setLeftChild(neighbor.getObj().getFrom().getDfsNode());
                            neighbor.getObj().getFrom().getDfsNode().setParent(currentNode);
                            firstChild = false;
                            dfsVisit(neighbor.getObj().getFrom(), transpose);
                        }
                    }
                    else{
                        if (neighbor.getObj().getFrom().getDfsNode().getColor() == "white"){
                            TreeNode needSibling = currentNode.getLeftChild();
                            while (needSibling.getRightSibling() != null){
                                needSibling = needSibling.getRightSibling();
                            }
                            needSibling.setRightSibling(neighbor.getObj().getFrom().getDfsNode());
                            neighbor.getObj().getFrom().getDfsNode().setParent(currentNode);
                            dfsVisit(neighbor.getObj().getFrom(), transpose);
                        }
                    }
                    neighbor = neighbor.getPrev();
                    }
                }
            }
        currentNode.setColor("black");
        time = time + 1;
        currentNode.setRetraction(time);
        if (transpose == false){
            retractionList.addNode(currentNode.getNode());
        }
    }
}







