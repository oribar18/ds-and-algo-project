public class Queue {
    private DoublyLinkedList<TreeNode> list;
    private Node<TreeNode> tail;

    public Queue(){
        this.list = new DoublyLinkedList<>();
        this.tail = list.getTail();
    }

    public DoublyLinkedList<TreeNode> getList() {
        return list;
    }

    public void setList(DoublyLinkedList<TreeNode> list) {
        this.list = list;
    }

    public Node<TreeNode> getTail() {
        return tail;
    }

    public void setTail(Node<TreeNode> tail) {
        this.tail = tail;
    }

    public void enqueue(TreeNode obj){
        obj.setNodeInList(list.addNode(obj));
        tail = obj.getNodeInList();
    }

    public TreeNode dequeue(){
        if (list.getHead() == null){
            return null;
        }
        TreeNode nodeToReturn = list.getHead().getObj();
        list.deleteNode(list.getHead().getObj());
        if (list.getHead() == null){
            tail = null;
        }
        return nodeToReturn;
    }

}
