import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Card Dealer: deal k distinct random cards from a deck of strings read from standard input.
 */
public class Permutation {

    /**
     * Deal exactly k cards uniformly at random from the deck supplied on standard input.
     * Read card names with a Scanner and print each dealt card at most once.
     * @param args command-line arguments; args[0] is the number k of cards to deal
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            return;
        }

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String card = scanner.next();
            queue.enqueue(card);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }

        scanner.close();
    }
}
