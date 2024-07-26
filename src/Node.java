public class Node <T> {
    private T obj;
    private Node prev;
    private Node next;

    public Node(T obj){
        this.obj = obj;
        this.next = null;
        this.prev = null;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
