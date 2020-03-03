package utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;

public class ConnectionHelper {
    private Connection connection=null;
    private Properties myProperties;

    public ConnectionHelper(Properties properties) {
        this.myProperties= properties;
    }

    public Connection getConnection(){
        try {
            if (connection==null || connection.isClosed())
                connection=getNewConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    private Connection getNewConnection() {
        String user = myProperties.getProperty("user");
        String password = myProperties.getProperty("password");
        try {
            Class.forName(myProperties.getProperty("mySql.jdbcDriver")).getConstructor().newInstance();
            connection = DriverManager.getConnection(myProperties.getProperty("mySql.url"), user, password);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }


}
