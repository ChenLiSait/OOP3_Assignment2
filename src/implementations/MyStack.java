package implementations;

import utilities.StackADT;
import utilities.Iterator;
import java.lang.reflect.Array;

/**
 * Array-backed stack implementation using MyArrayList as the underlying storage.
 * Supports standard stack operations and iteration from top to bottom.
 *
 * @param <E> type of elements stored in the stack
 */
public class MyStack<E> implements StackADT<E> {
    /** Underlying list for storing stack elements */
    private MyArrayList<E> data;

    /**
     * Constructs an empty stack with default initial capacity.
     */
    public MyStack() {
        data = new MyArrayList<>();
    }

    /**
     * Pushes the specified element onto the top of this stack.
     *
     * @param toAdd element to be pushed
     * @throws NullPointerException if the element is null
     */
    @Override
    public void push(E toAdd) {
        if (toAdd == null) {
            throw new NullPointerException("Cannot push null onto stack");
        }
        data.add(toAdd);
    }

    /**
     * Removes and returns the element at the top of this stack.
     *
     * @return the popped element
     * @throws java.util.EmptyStackException if this stack is empty
     */
    @Override
    public E pop() {
        if (data.isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return data.remove(data.size() - 1);
    }

    /**
     * Retrieves, but does not remove, the element at the top of this stack.
     *
     * @return the element at the top
     * @throws java.util.EmptyStackException if this stack is empty
     */
    @Override
    public E peek() {
        if (data.isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return data.get(data.size() - 1);
    }

    /**
     * Removes all elements from this stack.
     */
    @Override
    public void clear() {
        data.clear();
    }

    /**
     * Returns true if this stack contains no elements.
     *
     * @return true if stack is empty
     */
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Returns the number of elements in this stack.
     *
     * @return current size of the stack
     */
    @Override
    public int size() {
        return data.size();
    }

    /**
     * Indicates whether this stack has overflowed.
     * Since there is no fixed capacity, this always returns false.
     *
     * @return false always
     */
    @Override
    public boolean stackOverflow() {
        return false;
    }

    /**
     * Returns an array containing all of the elements in this stack
     * in order from top to bottom.
     *
     * @return array of stack elements from top to bottom
     */
    @Override
    public Object[] toArray() {
        int n = size();
        Object[] result = new Object[n];
        for (int i = 0; i < n; i++) {
            result[i] = data.get(n - 1 - i);
        }
        return result;
    }

    /**
     * Returns an array containing all of the elements in this stack
     * in order from top to bottom; the runtime type of the returned array
     * is that of the specified array.
     *
     * @param holder the array into which the elements of the stack are to be stored,
     *               if it is big enough; otherwise, a new array of the same runtime
     *               type is allocated for this purpose.
     * @return an array containing the stack elements
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public E[] toArray(E[] holder) {
        if (holder == null) {
            throw new NullPointerException();
        }
        int n = size();
        E[] arr;
        if (holder.length >= n) {
            arr = holder;
        } else {
            @SuppressWarnings("unchecked")
            Class<? extends E[]> cls = (Class<? extends E[]>) holder.getClass();
            arr = (E[]) Array.newInstance(cls.getComponentType(), n);
        }
        for (int i = 0; i < n; i++) {
            arr[i] = data.get(n - 1 - i);
        }
        if (arr.length > n) {
            arr[n] = null;
        }
        return arr;
    }

    /**
     * Returns true if this stack contains the specified element.
     *
     * @param toFind element whose presence is to be tested
     * @return true if stack contains the element
     */
    @Override
    public boolean contains(E toFind) {
        return data.contains(toFind);
    }

    /**
     * Returns the 1-based position from the top of this stack where the
     * specified element is located. The topmost element is at position 1.
     * Returns -1 if the element is not found.
     *
     * @param toFind element to search for
     * @return position from top, or -1 if not present
     */
    @Override
    public int search(E toFind) {
        int n = data.size();
        for (int i = n - 1; i >= 0; i--) {
            E element = data.get(i);
            if (element == null ? toFind == null : element.equals(toFind)) {
                return n - i;
            }
        }
        return -1;
    }

    /**
     * Returns an iterator over the elements in this stack in top-to-bottom order.
     *
     * @return Iterator over stack elements
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = data.size() - 1;

            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                return data.get(index--);
            }
        };
    }

    /**
     * Compares the specified StackADT with this stack for equality.
     * Returns true if both stacks have the same size and corresponding
     * elements are equal from top to bottom.
     *
     * @param other stack to compare against
     * @return true if stacks are equal by size and element order
     */
    @Override
    public boolean equals(StackADT<E> other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.size() != other.size()) {
            return false;
        }
        Iterator<E> it1 = this.iterator();
        Iterator<E> it2 = other.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            E e1 = it1.next();
            E e2 = it2.next();
            if (e1 == null ? e2 != null : !e1.equals(e2)) {
                return false;
            }
        }
        return true;
    }
}
