package implementations;

public class MyDLLNode<E> {
    E element;
    MyDLLNode<E> next;
    MyDLLNode<E> prev;

    public MyDLLNode(E element) {
        this.element = element;
        this.next = null;
        this.prev = null;
    }
}
