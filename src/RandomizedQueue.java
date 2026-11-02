import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * A queue that removes items uniformly at random.
 * @param <Item> the type of items stored in this randomized queue
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;
    private static final Random random = new Random();

    /** Construct an empty randomized queue. */
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    /** Return whether the randomized queue is empty. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Return the number of items in the randomized queue. */
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked.")
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    /** Add an item; reject null with IllegalArgumentException. */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item");
        }
        if (size == items.length) {
            resize(2 * items.length);
        }
        items[size++] = item;
    }

    /** Remove and return a uniformly random item; throw NoSuchElementException if empty. */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue underflow");
        }
        int randomIndex = random.nextInt(size);
        Item item = items[randomIndex];

        // Swap chosen item with the last item
        items[randomIndex] = items[size - 1];
        items[size - 1] = null; // Avoid loitering
        size--;

        // Halve capacity when array is one-quarter full
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    /** Return a uniformly random item without removing it; throw NoSuchElementException if empty. */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue underflow");
        }
        int randomIndex = random.nextInt(size);
        return items[randomIndex];
    }

    /** Return an independent iterator in uniformly random order; define next() and remove() exceptions. */
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();


        // TODO: Return an independent iterator that traverses the array items in random order.


    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] copy;
        private int current;

        @SuppressWarnings("unchecked")
        public RandomizedQueueIterator() {
            copy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copy[i] = items[i];
            }
            for (int i = 0; i < size; i++) {
                int r = i + random.nextInt(size - i);
                Item temp = copy[i];
                copy[i] = copy[r];
                copy[r] = temp;
            }
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < copy.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException(" No more items in iteration");
            }
            return copy[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }

    /** Run unit tests for the randomized queue. */
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        System.out.println("Testing isEmpty on new queue: " + rq.isEmpty());
        System.out.println("Testing size on new queue: " + rq.size());

        for (int i = 1; i <= 5; i++) {
            rq.enqueue(i);
        }

        System.out.println("Size after enqueuing 5 items: " + rq.size());
        System.out.println("Random sample without removal: " + rq.sample());

        System.out.print("Independent random iteration 1: ");
        for (int item : rq) {
            System.out.print(item + " ");
        }
        System.out.println();

        System.out.print("Independent random iteration 2: ");
        for (int item : rq) {
            System.out.print(item + " ");
        }
        System.out.println();

        System.out.println("Dequeued item: " + rq.dequeue());
        System.out.println("Size after 1 dequeue: " + rq.size());

        System.out.print("Remaining items in queue: ");
        while (!rq.isEmpty()) {
            System.out.print(rq.dequeue() + " ");
        }
        System.out.println();

        System.out.println("Testing isEmpty after dequeuing all: " + rq.isEmpty());
    }
}
