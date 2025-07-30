package implementations;

import utilities.QueueADT;
import utilities.Iterator;
import exceptions.EmptyQueueException;

/**
 * MyQueue implementation using MyDLL as the underlying storage.
 * Elements are enqueued at the tail and dequeued from the head (FIFO order).
 *
 * @param <E> type of elements stored in the queue
 */
public class MyQueue<E> implements QueueADT<E> {
    /** Underlying doubly-linked list for queue storage */
    private MyDLL<E> data;

    /**
     * Constructs an empty queue.
     */
    public MyQueue() {
        data = new MyDLL<>();
    }

    /**
     * Inserts the specified element at the tail of this queue.
     *
     * @param toAdd element to enqueue
     * @throws NullPointerException if the element is null
     */
    @Override
    public void enqueue(E toAdd) {
        if (toAdd == null) {
            throw new NullPointerException("Cannot enqueue null into queue");
        }
        // Add to the end of the list to maintain FIFO order
        data.add(toAdd);
    }

    /**
     * Removes and returns the element at the head of this queue.
     *
     * @return dequeued element
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public E dequeue() throws EmptyQueueException {
        if (data.isEmpty()) {
            throw new EmptyQueueException();
        }
        // Remove from the front of the list
        return data.remove(0);
    }

    /**
     * Retrieves, but does not remove, the head of this queue.
     *
     * @return head element
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public E peek() throws EmptyQueueException {
        if (data.isEmpty()) {
            throw new EmptyQueueException();
        }
        return data.get(0);
    }

    /**
     * Removes all elements from this queue.
     */
    @Override
    public void dequeueAll() {
        data.clear();
    }

    /**
     * Returns true if this queue contains no elements.
     *
     * @return true if queue is empty
     */
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Returns the number of elements in this queue.
     *
     * @return current size of the queue
     */
    @Override
    public int size() {
        return data.size();
    }

    /**
     * Indicates whether this queue has reached a fixed capacity.
     * Since there is no fixed capacity, this always returns false.
     *
     * @return false always
     */
    @Override
    public boolean isFull() {
        return false;
    }

    /**
     * Returns true if this queue contains the specified element.
     *
     * @param toFind element whose presence is to be tested
     * @return true if queue contains the element
     * @throws NullPointerException if the element is null
     */
    @Override
    public boolean contains(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null in queue");
        }
        return data.contains(toFind);
    }

    /**
     * Returns the 1-based position from the head of this queue where
     * the specified element is located. The head element is at position 1.
     * Returns -1 if the element is not found.
     *
     * @param toFind element to search for
     * @return position from head, or -1 if not present
     * @throws NullPointerException if the element is null
     */
    @Override
    public int search(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null in queue");
        }
        // Iterate through list to find matching element
        for (int i = 0; i < data.size(); i++) {
            E element = data.get(i);
            if (element == null ? toFind == null : element.equals(toFind)) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Returns an iterator over the elements in this queue in FIFO order.
     *
     * @return iterator from head to tail
     */
    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }

    /**
     * Compares the specified QueueADT with this queue for equality.
     * Returns true if both queues have the same size and corresponding
     * elements are equal in the same order.
     *
     * @param other queue to compare against
     * @return true if queues are equal by size and element order
     */
    @Override
    public boolean equals(QueueADT<E> other) {
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

    /**
     * Returns an array containing all of the elements in this queue
     * in FIFO order.
     *
     * @return array of queue elements from head to tail
     */
    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    /**
     * Returns an array containing all of the elements in this queue
     * in FIFO order; the runtime type of the returned array is that of
     * the specified array.
     *
     * @param holder the array into which the elements of the queue are to be stored,
     *               if it is big enough; otherwise, a new array of the same runtime
     *               type is allocated for this purpose.
     * @return an array containing the queue elements
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public E[] toArray(E[] holder) {
        return data.toArray(holder);
    }
}
