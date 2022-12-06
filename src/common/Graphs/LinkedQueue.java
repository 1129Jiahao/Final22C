package common.Graphs;

public final class LinkedQueue<T> implements QueueInterface<T>
{
    private Node head, tail;

    public LinkedQueue() {
        head = tail = null;
    }

    /**
     * enqueue
     * @param newEntry  An object to be added.
     */
    @Override
    public void enqueue(T newEntry) {
        Node n = new Node(newEntry);

        if (isEmpty()) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
    }

    /**
     * dequeue
     * @return
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyQueueException("queue is empty");
        }
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return data;
    }

    /**
     * getFront
     * @return
     */
    @Override
    public T getFront() {

        if (isEmpty()) {
            throw new EmptyQueueException("queue is empty");
        }
        return head.data;
    }

    /**
     * isEmpty
     * @return
     */
    @Override
    public boolean isEmpty() {
        return head == null;

    }

    /**
     * clear
     */
    @Override
    public void clear() {
        head = tail = null;

    }

    class Node{
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
        }
    } // end Node
} // end Linkedqueue

