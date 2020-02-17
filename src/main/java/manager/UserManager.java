package manager;

import db.DBConnectionProvider;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserManager {


    private DBConnectionProvider dbConnectionProvider;

    public UserManager() {
        dbConnectionProvider = DBConnectionProvider.getInstance();
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        String query = "select * from user";
        try {
            Statement statement = dbConnectionProvider.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(User.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User getUserById(int id){
        User user = new User();
        try {
            PreparedStatement statement = dbConnectionProvider.getConnection().prepareStatement("select * from user where id = ?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
