public class GraphEdge {
    private Node<GraphEdge> nodeOutEdgeList;
    private Node<GraphEdge> nodeInEdgeList;
    private GraphNode from;
    private GraphNode to;

    public GraphEdge(GraphNode from, GraphNode to, Node<GraphEdge> nodeOutEdgeList, Node<GraphEdge> nodeInEdgeList){
        this.from = from;
        this.to = to;
        this.nodeOutEdgeList = nodeOutEdgeList;
        this.nodeInEdgeList = nodeInEdgeList;
    }

    public Node<GraphEdge> getNodeOutEdgeList(){ return this.nodeOutEdgeList; }

    public void setNodeOutEdgeList(Node<GraphEdge> nodeOutEdgeList) {
        this.nodeOutEdgeList = nodeOutEdgeList;
    }

    public Node<GraphEdge> getNodeInEdgeList(){ return this.nodeInEdgeList; }

    public void setNodeInEdgeList(Node<GraphEdge> nodeInEdgeList) {
        this.nodeInEdgeList = nodeInEdgeList;
    }

    public GraphNode getFrom() {
        return from;
    }

    public void setFrom(GraphNode from) {
        this.from = from;
    }

    public GraphNode getTo() {
        return to;
    }

    public void setTo(GraphNode to) {
        this.to = to;
    }

}
