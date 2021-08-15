package fr.fallenvaders.minecraft.mail_box.sql;

import fr.fallenvaders.minecraft.mail_box.data_manager.Data;
import fr.fallenvaders.minecraft.mail_box.data_manager.factories.DataFactory;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataSQL extends BaseSQL<Data> {
    protected final String TABLE_NAME = "MailBox_Data";

    public DataSQL(SQLConnection sqlConnection) {
        super.sqlConnection = sqlConnection;
        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS	" + TABLE_NAME
                + " (id BIGINT NOT NULL AUTO_INCREMENT, uuid VARCHAR(255), author VARCHAR(255), object VARCHAR(255), creationDate TIMESTAMP, PRIMARY KEY(id))");
            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Data onCreate(Data obj) {
        Data res = null;
        Data temp = obj.clone();
        temp.setCreationDate(Timestamp.from(Instant.now()));

        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("INSERT INTO " + TABLE_NAME + " (uuid, author, object, creationDate) VALUES(?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            query.setString(1, temp.getOwnerUuid().toString());
            query.setString(2, temp.getAuthor());
            query.setString(3, temp.getObject());
            query.setTimestamp(4, temp.getCreationDate());

            query.execute();

            ResultSet tableKeys = query.getGeneratedKeys();
            if (tableKeys.next()) {
                temp.setId(tableKeys.getLong(1));
            }

            query.close();
            res = temp;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    protected List<Data> onFind(UUID uuid) {
        List<Data> res = null;
        List<Data> temp = new ArrayList<>();

        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE uuid = ?");
            query.setString(1, uuid.toString());
            ResultSet resultset = query.executeQuery();

            while (resultset.next()) {
                Long id = resultset.getLong("id");
                String author = resultset.getString("author");
                String object = resultset.getString("object");
                Timestamp creationDate = resultset.getTimestamp("creationDate");

                temp.add(new DataFactory(id, uuid, author, object, creationDate));
            }
            query.close();
            res = temp;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    protected Data onUpdate(Long id, Data obj) {
        Data res = null;
        Data temp = obj.clone();

        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("UPDATE " + TABLE_NAME + " SET uuid = ?, author = ?, object = ?, creationDate = ? WHERE id = ?");
            query.setString(1, obj.getOwnerUuid().toString());
            query.setString(2, obj.getAuthor());
            query.setString(3, obj.getObject());
            query.setTimestamp(4, obj.getCreationDate());
            query.setLong(5, id);

            query.executeUpdate();
            query.close();

            temp.setId(id);
            res = temp;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    protected Boolean onDelete(Data obj) {
        boolean res = false;

        try {
            PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?");
            query.setLong(1, obj.getId());
            query.execute();
            query.close();
            res = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

}
