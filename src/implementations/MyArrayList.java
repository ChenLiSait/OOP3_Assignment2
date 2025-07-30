package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.NoSuchElementException;
import java.util.Arrays;

public class MyArrayList<E> implements ListADT<E> {
    // === Fields ===
	private static final long serialVersionUID = 1L;
    private E[] data; // Internal array to store elements
    private int size; // Current number of elements in the list
    private static final int DEFAULT_CAPACITY = 10; // Default starting capacity

    // === Constructor ===
    /**
     * Initializes an empty MyArrayList with a default capacity.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    // === Private Helper Method ===
    /**
     * Ensures the internal array has enough capacity.
     * If the required capacity exceeds the current size, the array will grow
     * (usually doubled).
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(data.length * 2, minCapacity);
            data = Arrays.copyOf(data, newCapacity);
        }
    }

    // === ListADT Implementations ===
    /**
     * Returns the number of elements currently in the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Clears all elements from the list.
     * Resets the list to empty state.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    /**
     * Adds an element at a specific index and shifts subsequent elements.
     *
     * @param index index to insert at
     * @param toAdd the element to add
     * @throws NullPointerException      if the element is null
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null)
            throw new NullPointerException();
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = toAdd;
        size++;
        return true;
    }

    /**
     * Adds an element at the end of the list.
     *
     * @param toAdd the element to add
     * @throws NullPointerException if the element is null
     */
    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null)
            throw new NullPointerException();
        ensureCapacity(size + 1);
        data[size++] = toAdd;
        return true;
    }

    /**
     * Adds all elements from another ListADT into this list.
     *
     * @param toAdd the list of elements to add
     * @throws NullPointerException if the provided list is null
     */
    @Override
    public boolean addAll(ListADT<? extends E> toAdd) {
        if (toAdd == null)
            throw new NullPointerException();
        boolean added = false;
        for (Iterator<? extends E> it = toAdd.iterator(); it.hasNext();) {
            add(it.next());
            added = true;
        }
        return added;
    }

    /**
     * Retrieves the element at the given index.
     *
     * @param index position of the element to get
     * @return the element at the index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return data[index];
    }

    /**
     * Removes and returns the element at the given index.
     * Shifts remaining elements to fill the gap.
     *
     * @param index index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        E removed = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[--size] = null;
        return removed;
    }

    /**
     * Removes the first occurrence of the specified element.
     *
     * @param toRemove the element to remove
     * @return the removed element if found, or null if not found
     * @throws NullPointerException if the element is null
     */
    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null)
            throw new NullPointerException();
        for (int i = 0; i < size; i++) {
            if (toRemove.equals(data[i])) {
                return remove(i);
            }
        }
        return null;
    }

    /**
     * Replaces the element at a specific index and returns the old element.
     *
     * @param index    the index to replace
     * @param toChange the new element
     * @return the old element that was replaced
     * @throws NullPointerException      if the new element is null
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null)
            throw new NullPointerException();
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        E old = data[index];
        data[index] = toChange;
        return old;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if no elements are in the list, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the list contains a specific element.
     *
     * @param toFind the element to check for
     * @return true if found, false otherwise
     * @throws NullPointerException if the element is null
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null)
            throw new NullPointerException();
        for (int i = 0; i < size; i++) {
            if (toFind.equals(data[i]))
                return true;
        }
        return false;
    }

    /**
     * Converts the list to an array of type E.
     * If the provided array is too small, a new array is created.
     *
     * @param toHold the destination array
     * @return an array containing all list elements
     * @throws NullPointerException if the provided array is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null)
            throw new NullPointerException();
        if (toHold.length < size) {
            return Arrays.copyOf(data, size, (Class<? extends E[]>) toHold.getClass());
        }
        System.arraycopy(data, 0, toHold, 0, size);
        if (toHold.length > size) {
            toHold[size] = null;
        }
        return toHold;
    }

    /**
     * Converts the list to a new Object array.
     *
     * @return an Object array with all elements
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    /**
     * Returns an iterator to traverse the list from start to end.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public E next() throws NoSuchElementException {
                if (!hasNext())
                    throw new NoSuchElementException();
                return data[cursor++];
            }
        };
    }
}
