package data.access;

import connection.ConnectionFactory;
import model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO{
    public Bill findById(long id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Bill WHERE id = ?";
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);

            resultSet = statement.executeQuery();
            resultSet.next();

            int price = resultSet.getInt(2);

            return new Bill(id, price);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(connection);
            ConnectionFactory.closeStatement(statement);
            ConnectionFactory.closeResultSet(resultSet);
        }

        return null;
    }

    public List<Bill> findAll(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM Bill";
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while(resultSet.next()){
                long id = resultSet.getLong(1);
                int price = resultSet.getInt(2);

                bills.add(new Bill(id, price));
            }

            return bills;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(connection);
            ConnectionFactory.closeStatement(statement);
            ConnectionFactory.closeResultSet(resultSet);
        }

        return null;
    }

    public void insert(Bill bill){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO Bill (id, price) VALUES ('" +
                    bill.id() +
                    "', '" +
                    bill.price() +
                    "')";

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(connection);
            ConnectionFactory.closeStatement(statement);
        }
    }
}
