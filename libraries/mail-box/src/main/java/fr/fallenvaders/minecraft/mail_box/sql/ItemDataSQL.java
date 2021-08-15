package fr.fallenvaders.minecraft.mail_box.sql;

import fr.fallenvaders.minecraft.mail_box.data_manager.Data;
import fr.fallenvaders.minecraft.mail_box.data_manager.ItemData;
import fr.fallenvaders.minecraft.mail_box.data_manager.factories.DataFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemDataSQL extends BaseSQL<ItemData> {
    private static final String TABLE_NAME = "MailBox_ItemData";

    public ItemDataSQL() {
        super();
        super.sqlConnection = new SQLConnection();

        try {
            PreparedStatement query = this.sqlConnection.getConnection()
                .prepareStatement("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id BIGINT, uuid VARCHAR(255), durationInSeconds BIGINT, itemStack TEXT, PRIMARY KEY(id))");
            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Tente de transformer un ItemStack en String
     */
    private String toBase64(ItemStack itemstack) {
        String res = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(itemstack);
            dataOutput.close();
            res = Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Tente de transformer un String en ItemStack
     */
    private ItemStack fromBase64(String str) {
        ItemStack res = null;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(str));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            res = (ItemStack) dataInput.readObject();

            dataInput.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    /*
     * table name format ?: - [id: int] [uuid: String] [duration: Long] [itemStack:
     * String(?)]
     *
     */

    @Override
    protected ItemData onCreate(ItemData obj) {
        ItemData res = null;
        ItemData temp = obj.clone();
        DataSQL dataSQL = new DataSQL(this.sqlConnection);
        Data data = dataSQL.onCreate(temp);

        if (data != null) {
            temp.setId(data.getId());
            temp.setCreationDate(data.getCreationDate());

            try {
                PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("INSERT INTO " + TABLE_NAME + " (id, uuid, durationInSeconds, itemStack) VALUES(?, ?, ?, ?)");
                query.setLong(1, temp.getId());
                query.setString(2, temp.getOwnerUuid().toString());
                query.setLong(3, temp.getDuration().getSeconds());
                query.setString(4, toBase64(temp.getItem()));

                query.execute();
                query.close();

                res = temp;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return res;
    }

    @Override
    protected List<ItemData> onFind(UUID uuid) {
        List<ItemData> res = null;
        List<ItemData> temp = new ArrayList<>();
        List<Data> dataList = new DataSQL(this.sqlConnection).onFind(uuid);

        if (dataList != null) {
            try {
                PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE uuid = ?");
                query.setString(1, uuid.toString());
                ResultSet resultset = query.executeQuery();

                while (resultset.next()) {
                    ItemStack itemstack = fromBase64(resultset.getString("itemStack"));
                    Duration duration = Duration.ofSeconds(resultset.getLong("durationInSeconds"));
                    Long id = resultset.getLong("id");
                    Data tData = dataList.stream().filter(e -> e.getId().equals(id)).findAny().orElse(new DataFactory());
                    temp.add(new ItemData(tData, itemstack, duration));

                }

                query.close();
                res = temp;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    @Override
    protected ItemData onUpdate(Long id, ItemData obj) {
        ItemData res = null;
        ItemData temp = obj.clone();
        Data uData = new DataSQL(this.sqlConnection).onUpdate(id, obj);

        if (uData != null) {
            try {
                PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("UPDATE " + TABLE_NAME + " SET uuid = ?, itemStack = ?, durationInSeconds = ? WHERE id = ?");
                query.setString(1, obj.getOwnerUuid().toString());
                query.setString(2, toBase64(obj.getItem()));
                query.setLong(3, obj.getDuration().toMillis() / 1000);
                query.setLong(4, obj.getId());
                query.executeUpdate();
                query.close();
                temp.setId(id);

                res = temp;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return res;
    }

    @Override
    protected Boolean onDelete(ItemData obj) {
        boolean res = false;

        if (new DataSQL(this.sqlConnection).onDelete(obj)) {
            try {
                PreparedStatement query = this.sqlConnection.getConnection().prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?");
                query.setLong(1, obj.getId());
                query.execute();
                query.close();
                res = true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;
    }
}
