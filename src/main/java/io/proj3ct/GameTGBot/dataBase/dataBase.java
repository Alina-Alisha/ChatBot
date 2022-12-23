package io.proj3ct.GameTGBot.dataBase;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.sql.*;

public class dataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/dbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public dataBase(){
    }

    public void writeDataInTable(long chatId, String text){
        Connection connection;
        try{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute("delete from users");
            String query = "INSERT INTO users(id, dialogHistory) VALUES (?,?)";
            PreparedStatement state = connection.prepareStatement( query  );
            state.setLong( 1,chatId );
            state.setString( 2,text );
            state.execute();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
