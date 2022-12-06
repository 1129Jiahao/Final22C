package common;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashedDictionary<K,V> implements DictionaryInterface<K,V>, Serializable
{
    // The dictionary:
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 5;
    private static final int MAX_CAPACITY = 10000;

    // The hash table:
    private TableEntry<K, V>[] hashTable;
    private static final int MAX_SIZE = 2 * MAX_CAPACITY;
    private boolean initialized = false;
    private static final double MAX_LOAD_FACTOR = 0.5;
    private int probes;

    public HashedDictionary()
    {
        this(DEFAULT_CAPACITY);
    }
    public HashedDictionary(int initialCapacity)
    {
        initialCapacity = checkCapacity(initialCapacity);
        this.numberOfEntries = 0;
        int tableSize = getNextPrime(initialCapacity);
        checkSize(tableSize);

        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        TableEntry<K, V>[] temp = (TableEntry<K, V>[])new TableEntry[tableSize];
        this.hashTable = temp;

        this.initialized = true;
        this.probes = 0;
    }

    //display the hash table for illustration and testing

    public void displayHashTable()
    {
        System.out.println("The size of hash table is: " + this.hashTable.length);
        System.out.println("In displayHashTable - implement me");

        // TODO Project1 #1
        for (int i = 0; i < this.hashTable.length; i++) {
            if(this.hashTable[i] == null){
                //System.out.println("Index " + i + "null");
                System.out.printf("%-5d" + "null " ,i);
            }
            else if(this.hashTable[i].isRemoved()){
                //System.out.println("Index " + i +"notIn");
                System.out.printf("%-5d" + "notIN " ,i);
            }
            else{
                System.out.println("Index " + i +" Key " + this.hashTable[i].key + " Value: " + this.hashTable[i].value);
                //System.out.printf("%-5d" + "Key:" ,i);
            }

        }


        System.out.println();
    }

    // Throws an exception if this object is not initialized.
    private void checkInitialization()
    {
        if (!this.initialized)
            throw new SecurityException ("HashedDictionary object is not initialized properly.");
    }

    private int checkCapacity(int capacity)
    {
        if (capacity < DEFAULT_CAPACITY)
            capacity = DEFAULT_CAPACITY;
        else if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a dictionary " +
                    "whose capacity is larger than " +
                    MAX_CAPACITY);
        return capacity;
    }

    // Throws an exception if the hash table becomes too large.
    private void checkSize(int size)
    {
        if (size > MAX_SIZE)
            throw new IllegalStateException("Dictionary has become too large.");
    }

    public int getNumberOfProbes()
    {
        return this.probes;
    }

    public V add(K key, V value)
    {
        checkInitialization();
        if ((key == null) || (value == null))
            throw new IllegalArgumentException("Cannot add null to a dictionary.");
        else
        {
            V oldValue;

            int index = getHashIndex(key);
            index = probe(index, key);

            // Assertion: index is within legal range for hashTable
            assert (index >= 0) && (index < this.hashTable.length);

            if ( (this.hashTable[index] == null) || this.hashTable[index].isRemoved())
            {
                this.hashTable[index] = new TableEntry<>(key, value);
                this.numberOfEntries++;
                oldValue = null;
            }
            else
            {
                oldValue = this.hashTable[index].getValue();
                this.hashTable[index].setValue(value);
            }

            if (isHashTableTooFull())
                enlargeHashTable();

            return oldValue;
        }
    }

    public V remove(K key)
    {
        checkInitialization();
        V removedValue = null;

        int index = getHashIndex(key);
        index = locate(index, key);

        if (index != -1)
        {
            removedValue = this.hashTable[index].getValue();
            this.hashTable[index].setToRemoved();
            this.numberOfEntries--;
        }

        return removedValue;
    }
    public V getValue(K key)
    {
        checkInitialization();
        V result = null;

        int index = getHashIndex(key);
        index = locate(index, key);

        if (index != -1)
            result = this.hashTable[index].getValue();
        return result;
    }

    public boolean contains(K key)
    {
        return getValue(key) != null;
    }

    public boolean isEmpty()
    {
        return this.numberOfEntries == 0;
    }

    public int getSize()
    {
        return this.numberOfEntries;
    }

    public final void clear()
    {
        checkInitialization();
        for (int index = 0; index < this.hashTable.length; index++)
            hashTable[index] = null;

        this.numberOfEntries = 0;
    }

    public Iterator<K> getKeyIterator()
    {
        return new KeyIterator();
    }

    public Iterator<V> getValueIterator()
    {
        return new ValueIterator();
    }

    private int getHashIndex(K key)
    {
        int hashIndex = key.hashCode() % this.hashTable.length;

        if (hashIndex < 0)
        {
            hashIndex = hashIndex + this.hashTable.length;
        }

        return hashIndex;
    }
    private int getHashIndexIncrement(K key)
    {
        // TODO Project 2 Part 2
        int previousPrime = getPreviousPrime(this.hashTable.length);
        final int DEFAULT_PRIME = 7;
        int step = (previousPrime - key.hashCode() % previousPrime);

        while(step < 0){
            step+=hashTable.length;
        }
        if(step == 0){
            step = DEFAULT_PRIME;
        }

        //;System.out.println("getHashIndexIncrement method - IMPLEMENT ME");
        //System.out.println("hash index increment is " + step);
        return step;
    }
    // Precondition: checkInitialization has been called.
    private int probe(int index, K key)
    {
        // TODO Project 2 Part 2

        boolean found = false;
        int removedStateIndex = -1;

        int step = getHashIndexIncrement(key);

        while ( !found && (this.hashTable[index] != null) )
        {
            if (this.hashTable[index].isIn())
            {
                if (key.equals(this.hashTable[index].getKey()))
                    found = true; // Key found
                else {
                    index = (index + step) % this.hashTable.length;
                    this.probes++;
                }
            }
            else
            {

                // Save index of first location in removed state
                if (removedStateIndex == -1)
                    removedStateIndex = index;

                index = (index + step) % this.hashTable.length;
                this.probes++;
            }
        }
        // Assertion: Either key or null is found at hashTable[index]

        if (found || (removedStateIndex == -1) )
            return index;
        else
            return removedStateIndex;
    }

    // Precondition: checkInitialization has been called.
    private int locate(int index, K key)
    {

        boolean found = false;
        int step = getHashIndexIncrement(key);

        while ( !found && (this.hashTable[index] != null) )
        {
            if ( this.hashTable[index].isIn() && key.equals(this.hashTable[index].getKey()) )
                found = true;
            else
            {

                index = (index + step) % this.hashTable.length;
                this.probes++;
            }
        }

        int result = -1;
        if (found)
            result = index;

        return result;
    }

    // Increases the size of the hash table to a prime >= twice its old size.
    private void enlargeHashTable()
    {
        TableEntry<K, V>[] oldTable = this.hashTable;
        int oldSize = this.hashTable.length;
        int newSize = getNextPrime(oldSize + oldSize);
        checkSize(newSize);

        @SuppressWarnings("unchecked")
        TableEntry<K, V>[] tempTable = (TableEntry<K, V>[])new TableEntry[newSize];
        this.hashTable = tempTable;
        this.numberOfEntries = 0;

        for (int index = 0; index < oldSize; index++)
        {
            if ( (oldTable[index] != null) && oldTable[index].isIn() )
                add(oldTable[index].getKey(), oldTable[index].getValue());
        }
    }

    // Returns true if lambda > MAX_LOAD_FACTOR for hash table;

    private boolean isHashTableTooFull()
    {
        return this.numberOfEntries > MAX_LOAD_FACTOR * this.hashTable.length;
    }

    private int getPreviousPrime(int integer)
    {
        if (integer % 2 == 0)
        {
            integer--;
        }

        while(!isPrime(integer))
        {
            integer = integer - 2;
        }

        return integer;
    }

    // Returns a prime integer that is >= the given integer.
    private int getNextPrime(int integer)
    {
        if (integer % 2 == 0)
        {
            integer++;
        }

        while (!isPrime(integer))
        {
            integer = integer + 2;
        }

        return integer;
    }

    // Returns true if the given intege is prime.
    private boolean isPrime(int integer)
    {
        boolean result;
        boolean done = false;

        if ( (integer == 1) || (integer % 2 == 0) )
        {
            result = false;
        }

        else if ( (integer == 2) || (integer == 3) )
        {
            result = true;
        }

        else
        {
            assert (integer % 2 != 0) && (integer >= 5);

            // a prime is odd and not divisible by every odd integer up to its square root
            result = true;
            for (int divisor = 3; !done && (divisor * divisor <= integer); divisor = divisor + 2)
            {
                if (integer % divisor == 0)
                {
                    result = false;
                    done = true;
                }
            }
        }

        return result;
    }

    private class KeyIterator implements Iterator<K>
    {
        private int currentIndex;
        private int numberLeft;

        private KeyIterator()
        {
            this.currentIndex = 0;
            this.numberLeft = numberOfEntries;
        }
        public boolean hasNext()
        {
            return this.numberLeft > 0;
        }

        public K next()
        {
            K result = null;

            if (hasNext())
            {
                while ( (hashTable[this.currentIndex] == null) || hashTable[this.currentIndex].isRemoved() )
                {
                    this.currentIndex++;
                }

                result = hashTable[this.currentIndex].getKey();
                this.numberLeft--;
                this.currentIndex++;
            }
            else
                throw new NoSuchElementException();

            return result;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    private class ValueIterator implements Iterator<V>
    {
        private int currentIndex;
        private int numberLeft;

        private ValueIterator()
        {
            this.currentIndex = 0;
            this.numberLeft = numberOfEntries;
        }
        public boolean hasNext()
        {
            return this.numberLeft > 0;
        }

        public V next()
        {
            V result = null;

            if (hasNext())
            {
                while ( (hashTable[this.currentIndex] == null) || hashTable[this.currentIndex].isRemoved() )
                {
                    this.currentIndex++;
                }

                result = hashTable[this.currentIndex].getValue();
                this.numberLeft--;
                this.currentIndex++;
            }
            else
                throw new NoSuchElementException();

            return result;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
    private static class TableEntry<S, T> implements Serializable
    {
        private S key;
        private T value;
        private States state;
        private enum States {CURRENT, REMOVED}

        private TableEntry(S searchKey, T dataValue)
        {
            this.key = searchKey;
            this.value = dataValue;
            this.state = States.CURRENT;
        }

        private S getKey()
        {
            return this.key;
        }

        private T getValue()
        {
            return this.value;
        }

        private void setValue(T newValue)
        {
            this.value = newValue;
        }

        // Returns true if this entry is currently in the hash table.
        private boolean isIn()
        {
            return this.state == States.CURRENT;
        }

        // Returns true if this entry has been removed from the hash table.
        private boolean isRemoved()
        {
            return this.state == States.REMOVED;
        }

        // Sets the state of this entry to removed.
        private void setToRemoved()
        {
            this.key = null;
            this.value = null;
            this.state = States.REMOVED;
        }

        // Sets the state of this entry to current.
        private void setToIn()
        {
            this.state = States.CURRENT;
        }
    }
}
