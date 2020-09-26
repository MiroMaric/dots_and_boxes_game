package rs.miromaric.dotsandboxes.server.dbbroker.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import rs.miromaric.dotsandboxes.common.domain.GeneralEntity;
import rs.miromaric.dotsandboxes.server.dbbroker.IDatabaseBroker;
import rs.miromaric.dotsandboxes.server.dbbroker.connection.DatabaseConnection;

/**
 *
 * @author miro
 */
public class DatabaseBroker implements IDatabaseBroker {

    @Override
    public List<GeneralEntity> getAllRecord(GeneralEntity entity) throws SQLException {
        List<GeneralEntity> objects = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(entity.getClassName());
        String query = sb.toString();
        System.out.println(query);
        PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            objects.add(entity.getNewRecord(rs));
        }
        return objects;
    }

    @Override
    public Optional<GeneralEntity> findRecord(GeneralEntity entity) throws SQLException {
        String query = "SELECT * FROM " + entity.getClassName() + " WHERE " + entity.getWhereCondition();
        System.out.println(query);
        Statement st = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        boolean signal = rs.next();
        if (signal == true) {
            return Optional.of(entity.getNewRecord(rs));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public List<GeneralEntity> findRecords(GeneralEntity entity, GeneralEntity parent) throws SQLException {
        List<GeneralEntity> objects = new LinkedList<>();
        String query = "SELECT * FROM " + entity.getClassName() + " WHERE " + parent.getWhereCondition();
        System.out.println(query);
        PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            objects.add(entity.getNewRecord(rs));
        }
        return objects;
    }

    @Override
    public Long insertRecord(GeneralEntity entity) throws SQLException {
        String upit = "INSERT INTO "+ entity.getClassName() + " (" + entity.getAtrNames() + ") VALUES (" + entity.getAtrValues() + ")";
        return executeUpdate(upit);
    }

    @Override
    public void deleteRecord(GeneralEntity entity) throws SQLException {
        String upit = "DELETE FROM " + entity.getClassName() + " WHERE " + entity.getWhereCondition();
        executeUpdate(upit);
    }

    @Override
    public void updateRecord(GeneralEntity entity, GeneralEntity entityId) throws SQLException {
        String upit = "UPDATE " + entity.getClassName() + " SET " + entity.setAtrValues() + " WHERE " + entityId.getWhereCondition();
        executeUpdate(upit);

    }

    @Override
    public void updateRecord(GeneralEntity entity)throws SQLException {
        String upit = "UPDATE " + entity.getClassName() + " SET " + entity.setAtrValues() + " WHERE " + entity.getWhereCondition();
        executeUpdate(upit);
    }

    private Long executeUpdate(String query) throws SQLException {
        System.out.println(query);
        try(Statement st = DatabaseConnection.getInstance().getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int rowcount = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (rowcount <= 0) {
                throw new RuntimeException("Update query not executed");
            }
            try(ResultSet rs = st.getGeneratedKeys()){
                rs.next();
                try {
                    return rs.getLong(1);
                } catch(SQLException e) {
                    return -1L;
                }
            }
        }
    }

}