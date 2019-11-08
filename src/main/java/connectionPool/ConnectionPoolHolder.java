package connectionPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 */
public class ConnectionPoolHolder {
    private static ExecutorService connectionPool = null;

    public static ExecutorService getConnectionPool() {
        if (connectionPool == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (connectionPool == null){
                    connectionPool = Executors.newFixedThreadPool(20);
                }
            }
        }
        return connectionPool;
    }
}
