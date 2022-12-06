package common.Graphs;

import java.util.EmptyStackException;

public class LinkedStack<T> implements StackInterface<T>, java.io.Serializable

{

    private Node topNode; // references the first node in the chain

    public LinkedStack()

    {

        topNode = null;

    } // end default constructor

    public void push(T newEntry)

    {

        Node newNode = new Node(newEntry, topNode);

        topNode = newNode;

    }

    public T peek()

    {

        T top = null;

        if (topNode != null)

            top = topNode.getData();

        return top;

    }

    public T pop()

    {

        T top = peek();

        if (topNode != null)

            topNode = topNode.getNextNode();

        return top;

    }

    public boolean isEmpty()

    {

        return topNode == null;

    }

    public void clear()

    {

        topNode = null;

    }

    private class Node implements java.io.Serializable

    {

        private T data; // entry in stack

        private Node next; // link to next node

        private Node(T dataPortion)

        {

            data = dataPortion;

            next = null;

        } // end constructor

        private Node(T dataPortion, Node linkPortion)

        {

            data = dataPortion;

            next = linkPortion;

        } // end constructor

        private T getData()

        {

            return data;

        } // end getData

        private void setData(T newData)

        {

            data = newData;

        } // end setData

        private Node getNextNode()

        {

            return next;

        } // end getNextNode

        private void setNextNode(Node nextNode)

        {

            next = nextNode;

        } // end setNextNode

    } // end Node

}