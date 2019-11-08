package data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yuliia Shcherbakova ON 08.11.2019
 * @project parser
 */
public class Counters {
    private static AtomicInteger numberOfRequests = new AtomicInteger(0);
    private static AtomicInteger numberOfProducts = new AtomicInteger(0);

    public static void incrementRequests(){
        numberOfRequests.incrementAndGet();
    }

    public static void incrementProducts(){
        numberOfProducts.incrementAndGet();
    }

    public static AtomicInteger getNumberOfRequests() {
        return numberOfRequests;
    }

    public static AtomicInteger getNumberOfProducts() {
        return numberOfProducts;
    }
}
