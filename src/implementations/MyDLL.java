package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.NoSuchElementException;

/**
 * A doubly linked list implementation of the ListADT interface.
 * Supports insertion, removal, and traversal in both directions.
 *
 * @param <E> the type of elements stored in the list
 */
@SuppressWarnings("unchecked")
public class MyDLL<E> implements ListADT<E> {

    private MyDLLNode<E> head;
    private MyDLLNode<E> tail;
    private int size;

    /**
     * Constructs an empty doubly linked list.
     */
    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all elements from the list.
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Inserts an element at a specific index.
     * 
     * @param index position where the element should be inserted
     * @param toAdd element to insert
     */
    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null)
            throw new NullPointerException();
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

        if (size == 0) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            MyDLLNode<E> current = getNode(index);
            MyDLLNode<E> previous = current.prev;
            previous.next = newNode;
            newNode.prev = previous;
            newNode.next = current;
            current.prev = newNode;
        }

        size++;
        return true;
    }

    /**
     * Adds an element to the end of the list.
     */
    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null)
            throw new NullPointerException();
        return add(size, toAdd);
    }

    /**
     * Adds all elements from another list.
     */
    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null)
            throw new NullPointerException();
        Iterator<? extends E> it = toAdd.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
        return true;
    }

    /**
     * Retrieves the element at a given index.
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        return getNode(index).element;
    }

    /**
     * Removes and returns the element at a given index.
     */
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        MyDLLNode<E> removed;

        if (size == 1) {
            removed = head;
            head = tail = null;
        } else if (index == 0) {
            removed = head;
            head = head.next;
            head.prev = null;
        } else if (index == size - 1) {
            removed = tail;
            tail = tail.prev;
            tail.next = null;
        } else {
            removed = getNode(index);
            removed.prev.next = removed.next;
            removed.next.prev = removed.prev;
        }

        size--;
        return removed.element;
    }

    /**
     * Removes the first occurrence of a given element from the list.
     */
    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null)
            throw new NullPointerException();
        MyDLLNode<E> current = head;
        while (current != null) {
            if (toRemove.equals(current.element)) {
                if (current == head) {
                    return remove(0);
                } else if (current == tail) {
                    return remove(size - 1);
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    size--;
                    return current.element;
                }
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Replaces the element at a specific index.
     */
    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null)
            throw new NullPointerException();
        MyDLLNode<E> node = getNode(index);
        E old = node.element;
        node.element = toChange;
        return old;
    }

    /**
     * Checks if the list is empty.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if a given element exists in the list.
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null)
            throw new NullPointerException();
        MyDLLNode<E> current = head;
        while (current != null) {
            if (toFind.equals(current.element))
                return true;
            current = current.next;
        }
        return false;
    }

    /**
     * Returns an array containing all list elements.
     */
    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null)
            throw new NullPointerException();
        if (toHold.length < size) {
            toHold = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
        }
        MyDLLNode<E> current = head;
        int i = 0;
        while (current != null) {
            toHold[i++] = current.element;
            current = current.next;
        }
        if (toHold.length > size) {
            toHold[size] = null;
        }
        return toHold;
    }

    /**
     * Returns an Object[] containing all list elements.
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        MyDLLNode<E> current = head;
        int i = 0;
        while (current != null) {
            array[i++] = current.element;
            current = current.next;
        }
        return array;
    }

    /**
     * Returns an iterator for this list.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private MyDLLNode<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() throws NoSuchElementException {
                if (current == null)
                    throw new NoSuchElementException();
                E element = current.element;
                current = current.next;
                return element;
            }
        };
    }

    /**
     * Helper method to get the node at a given index.
     */
    private MyDLLNode<E> getNode(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        MyDLLNode<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++)
                current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--)
                current = current.prev;
        }
        return current;
    }
}
