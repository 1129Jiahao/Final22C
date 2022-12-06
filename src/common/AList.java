package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AList<T> implements ListInterface<T>

{

    private T[] list; // Array of list entries; ignore list[0]
    private int numberOfEntries;
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;

    //default constructor
    public AList() {
        this(DEFAULT_CAPACITY);
    }
    /**
     * The length can be set dynamically in sets
     * @param Capacity
     */
    public AList(int Capacity){
        if(Capacity<DEFAULT_CAPACITY)
            Capacity=DEFAULT_CAPACITY;
        else{
            checkCapacity(Capacity);
        }
        T[] tempList = (T[]) new Object[Capacity + 1];
        list = tempList;
        numberOfEntries = 0;
        initialized = true;
    }

    // Doubles the capacity of the array list if it is full.
// Precondition: checkInitialization has been called.
    private void ensureCapacity()
    {
        int capacity = list.length - 1;
        if (numberOfEntries >= capacity)
        {
            int newCapacity = 2 * capacity;
            checkCapacity(newCapacity); // Is capacity too big?
            list = Arrays.copyOf(list, newCapacity + 1);
        } // end if
    } // end ensureCapacity



    // Makes room for a new entry at newPosition.
// Precondition: 1 <= newPosition <= numberOfEntries + 1;
// numberOfEntries is list's length before addition;
// checkInitialization has been called.
    private void makeRoom(int newPosition)
    {
        assert (newPosition >= 1) && (newPosition <= numberOfEntries + 1);

        int newIndex = newPosition;
        int lastIndex = numberOfEntries;

// Move each entry to next higher index, starting at end of
// list and continuing until the entry at newIndex is moved
        for (int index = lastIndex; index >= newIndex; index--)
            list[index + 1] = list[index];
    } // end makeRoom





    // Shifts entries that are beyond the entry to be removed to the
// next lower position.
// Precondition: 1 <= givenPosition < numberOfEntries;
// numberOfEntries is list's length before removal;
// checkInitialization has been called.

    private void removeGap(int givenPosition)
    {
        assert (givenPosition >= 1) && (givenPosition < numberOfEntries);

        int removedIndex = givenPosition;
        int lastIndex = numberOfEntries;

        for (int index = removedIndex; index < lastIndex; index++)
            list[index] = list[index + 1];
    } // end removeGap





    // Throws an exception if this object is not initialized.
    private void checkInitialization()
    {
        if (!initialized)
            throw new SecurityException ("AList object is not initialized properly.");
    } // end checkInitialization



    // Throws an exception if the client requests a capacity that is too large.
    private void checkCapacity(int capacity)
    {
        if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a list " +
                    "whose capacity exceeds " +
                    "allowed maximum.");
    } // end checkCapacity


    /**
     * Adding data to an array
     * @param newEntry The object to be added as a new entry.
     */
    @Override
    public void add(T newEntry) {
        checkInitialization();
        list[numberOfEntries+1] = newEntry;
        numberOfEntries++;
        ensureCapacity();

    }

    /**
     * Dynamic addition of data
     * @param newPosition An integer that specifies the desired
     * position of the new entry.
     * @param newEntry The object to be added as a new entry.
     */
    @Override
    public void add(int newPosition, T newEntry) {
        checkInitialization();
        if ((newPosition >= 1)
                && (newPosition <= numberOfEntries + 1)) {
            if (newPosition <= numberOfEntries) {
                makeRoom(newPosition);
            }

            list[newPosition] = newEntry;
            numberOfEntries++;
            ensureCapacity();
        } else {
            throw new IndexOutOfBoundsException("Illegal add operation.");
        }

    }

    /**
     * checkInitialization();
     * @param givenPosition An integer that indicates the position of
     * the entry to be removed.
     * @return
     */
    @Override
    public T remove(int givenPosition) {
        checkInitialization();
        if ((givenPosition >= 1)
                && (givenPosition <= numberOfEntries)) {
            T t = list[givenPosition];
            if (givenPosition < numberOfEntries) {
                removeGap(givenPosition);
            }
            numberOfEntries--;

            return t;
        }
        else {
            throw new IndexOutOfBoundsException("Illegal remove operation.");
        }
    }


    /**
     * clear doesn't quite turn the content into null, it just clears the contents.
     */
    @Override
    public void clear() {
        numberOfEntries = 0;
    }


    /**
     *
     * @param givenPosition An integer that indicates the position of the
     * entry to be replaced.
     * @param newEntry The object that will replace the entry at the
     * position givenPosition.
     * @return
     */
    @Override
    public T replace(int givenPosition, T newEntry) {
        checkInitialization();
        if ((givenPosition >= 1)
                && (givenPosition <= numberOfEntries)) {

            T t = list[givenPosition];
            list[givenPosition] = newEntry;
            return t;
        } else
            throw new IndexOutOfBoundsException("Illegal replace operation.");
    }


    /**
     * @param givenPosition An integer that indicates the position of
     * the desired entry.
     * @return
     */
    @Override
    public T getEntry(int givenPosition) {

        checkInitialization();

        if ((givenPosition >= 1)
                && (givenPosition <= numberOfEntries)) {

            return list[givenPosition];
        } else
            throw new IndexOutOfBoundsException("Illegal  getEntry operation.");
    }


    /**
     * Use a for loop to find if there are equal objects
     * @param anEntry The object that is the desired entry.
     * @return
     */
    @Override
    public boolean contains(T anEntry) {
        checkInitialization();
        boolean t = false;
        int index = 1;

        while (!t && (index <= numberOfEntries)) {
            if (anEntry.equals(list[index])) {
                t = true;
            }
            index++;
        }
        return t;
    }

    /**
     * GetLength
     * @return
     */
    @Override
    public int getLength() {
        return numberOfEntries;
    }

    /**
     * isEmpty
     * @return
     */
    @Override
    public boolean isEmpty() {
        if(numberOfEntries==0){
            return true;
        }
        return false;
    }

    /**
     * Converting collections to arrays
     * @return
     */
    @Override
    public T[] toArray() {
        checkInitialization();
        T[] Array = (T[]) new Object[numberOfEntries];
        for(int i=0;i<numberOfEntries;i++){
            Array[i]=list[i+1];
        }
        return Array;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<this.numberOfEntries;i++){
            stringBuffer.append(this.getEntry(i+1));
        }
        return stringBuffer.toString();
    }

    /**
     * 数组转换成集合
     * @return
     */
    public static AList<String> asList(String s){
        try {
            String[] strs=s.split(",");
            List<String> stringList = Arrays.asList(strs);

            AList<String> stringAList = new AList<>();
            for(int i=0;i<strs.length;i++){
                stringAList.add(strs[i]);
            }
            return stringAList;
        }catch (NullPointerException e){
            return new AList<>();
        }
    }


}

