package service;

import javafx.fxml.LoadException;
import myException.LogException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.RepoCategVarsta;
import utils.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class LogInService {
    ConnectionHelper connectionHelper;
    private static final Logger logger= LogManager.getLogger(LogInService.class.getName());

    public LogInService(Properties props) {
        connectionHelper=new ConnectionHelper(props);
    }

    public void logare(String user, String pass) throws LogException {
        try (Connection connection=connectionHelper.getConnection()){
            try (PreparedStatement stat=connection.prepareStatement("select count(*) as nr from logindata where logindata.user=? and pass=?")){
                stat.setString(1,user);
                stat.setString(2,pass);
                try (ResultSet re=stat.executeQuery()){
                    re.next();
                    if (re.getInt("nr")==0)
                        throw new LogException("date invalide");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
