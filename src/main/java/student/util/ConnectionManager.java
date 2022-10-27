package student.util;




import org.apache.log4j.Logger;
import student.exception.DaoException;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;




public final class ConnectionManager {

    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.username";
    private static final String URL_KEY = "db.url";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final Integer DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;
    static Logger log = Logger.getLogger(ConnectionManager.class.getName());



    static {
        initConnectionPool();
    }
    private ConnectionManager(){

    }

    private static void initConnectionPool() {
       String poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
       int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
       pool = new ArrayBlockingQueue<>(size);
       for (int i = 0; i < size; i++){
           Connection connection;
           try {
               connection = open();
           } catch (DaoException e) {
               throw new RuntimeException(e);
           }
           Connection finalConnection = connection;
           Connection proxyConnection = (Connection)Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                   new Class[]{Connection.class},
                   (proxy, method, args) -> method.getName().equals("close")
                           ? pool.add((Connection) proxy)
                           : method.invoke(finalConnection, args));
           pool.add(proxyConnection);
       }
    }

    public static Connection get(){
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection open() throws DaoException {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException sqlException) {

            log.error("DB-connection failed");
            throw new DaoException(sqlException);

        }
    }

}
