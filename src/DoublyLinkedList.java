public class DoublyLinkedList <T> {
    private Node<T> head;
    private Node<T> tail;


    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void setTail(Node<T> tail) {
        this.tail = tail;
    }

    public Node<T> addNode(T obj) {
        Node<T> new_node = new Node<>(obj);
        if (head == null) {
            head = tail = new_node;
            head.setPrev(null);
            tail.setNext(null);
        } else {
            tail.setNext(new_node);
            new_node.setPrev(tail);
            tail = new_node;
            tail.setNext(null);
        }
        return new_node;
    }

    public void deleteNode(GraphNode obj) {
        if (obj.getNodeInList().getPrev() != null){
            obj.getNodeInList().getPrev().setNext(obj.getNodeInList().getNext());
        } else {
            if (head == tail){
                head = tail = obj.getNodeInList().getNext();
            }
            else {
                head = obj.getNodeInList().getNext();
            }
        }
        if  (obj.getNodeInList().getNext() != null) {
             obj.getNodeInList().getNext().setPrev(obj.getNodeInList().getPrev());
        }
        else{
            tail = obj.getNodeInList().getPrev();
        }
        obj.setNodeInList(null);
    }

    public void deleteNode(GraphEdge obj){
        if (obj.getNodeInEdgeList().getPrev() != null){
            obj.getNodeInEdgeList().getPrev().setNext(obj.getNodeInEdgeList().getNext());
        } else {
            if (obj.getTo().getInEdges().head == obj.getTo().getInEdges().tail){
                obj.getTo().getInEdges().tail = obj.getTo().getInEdges().head = obj.getNodeInEdgeList().getNext();
            }
            obj.getTo().getInEdges().head = obj.getNodeInEdgeList().getNext();
        }
        if  (obj.getNodeInEdgeList().getNext() != null) {
            obj.getNodeInEdgeList().getNext().setPrev(obj.getNodeInEdgeList().getPrev());
        }
        else {
            obj.getTo().getInEdges().tail = obj.getNodeInEdgeList().getPrev();
        }
        obj.setNodeInEdgeList(null);
        if (obj.getNodeOutEdgeList().getPrev() != null){
            obj.getNodeOutEdgeList().getPrev().setNext(obj.getNodeOutEdgeList().getNext());
        } else {
            if (obj.getFrom().getOutEdges().head == obj.getFrom().getOutEdges().tail){
                obj.getFrom().getOutEdges().tail = obj.getFrom().getOutEdges().head = obj.getNodeOutEdgeList().getNext();
            }
            obj.getFrom().getOutEdges().head = obj.getNodeOutEdgeList().getNext();
        }
        if  (obj.getNodeOutEdgeList().getNext() != null) {
            obj.getNodeOutEdgeList().getNext().setPrev(obj.getNodeOutEdgeList().getPrev());
        }
        else {
            obj.getFrom().getOutEdges().tail = obj.getNodeOutEdgeList().getPrev();
        }
        obj.setNodeOutEdgeList(null);
    }

    public void deleteNode(TreeNode obj) {
        if (obj.getNodeInList().getPrev() != null){
            obj.getNodeInList().getPrev().setNext(obj.getNodeInList().getNext());
        } else {
                head = obj.getNodeInList().getNext();
            }
        if  (obj.getNodeInList().getNext() != null) {
            obj.getNodeInList().getNext().setPrev(obj.getNodeInList().getPrev());
        }
        obj.getNodeInList().setNext(null);
        obj.getNodeInList().setPrev(null);
    }
}