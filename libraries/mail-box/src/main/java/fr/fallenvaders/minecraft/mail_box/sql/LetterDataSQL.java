package fr.fallenvaders.minecraft.mail_box.sql;

import fr.fallenvaders.minecraft.mail_box.data_manager.Data;
import fr.fallenvaders.minecraft.mail_box.data_manager.LetterData;
import fr.fallenvaders.minecraft.mail_box.data_manager.LetterType;
import fr.fallenvaders.minecraft.mail_box.data_manager.factories.DataFactory;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class LetterDataSQL extends BaseSQL<LetterData> {
    private final String TABLE_NAME = "MailBox_LetterData";

    public LetterDataSQL() {
        super();
        super.sqlConnection = new SQLConnection();
        Connection conn = this.sqlConnection.getConnection();

        if (conn != null) {
            PreparedStatement query = null;

            try {
                query = conn.prepareStatement("CREATE TABLE IF NOT EXISTS	" + TABLE_NAME
                    + " (id BIGINT, uuid VARCHAR(255), type VARCHAR(255), content TEXT, isRead BOOLEAN DEFAULT '0', PRIMARY KEY(id))");
                query.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (query != null) {
                        query.close();
                    }
                } catch (SQLException ignored) {
                }
            }
        }
    }

    /**
     * Transforme une liste de string en string unique et les séparé par " #-# "
     */
    public String toText(List<String> list) {
        StringBuilder sb = new StringBuilder();
        String res = "";

        if (list != null) {
            for (String page : list) {
                sb.append(String.format("%s#-#", page == null || page.isEmpty() ? " " : page));
            }
            res = sb.toString();

        }

        return res;
    }

    /**
     * Transforme un String en List en utilisant "#-#" comme séparateur
     */
    private List<String> fromText(String str) {
        return Arrays.asList(StringUtils.split(str, "#-#"));
    }

    @Override
    protected LetterData onCreate(LetterData obj) {
        LetterData res = null;
        Connection conn = this.sqlConnection.getConnection();

        if (conn != null) {
            LetterData temp = obj.clone();
            Data data = new DataSQL(this.sqlConnection).onCreate(temp);

            if (data != null) {
                temp.setId(data.getId());
                temp.setCreationDate(data.getCreationDate());
                PreparedStatement query = null;

                try {
                    query = conn.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES(?, ?, ?, ?, ?)");
                    query.setLong(1, temp.getId());
                    query.setString(2, obj.getOwnerUuid().toString());
                    query.setString(3, temp.getLetterType().name());
                    query.setString(4, toText(temp.getContent()));
                    query.setBoolean(5, temp.getIsRead());

                    query.execute();

                    res = temp;

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (query != null) {
                            query.close();
                        }
                    } catch (SQLException ignored) {
                    }
                }
            }
        }
        return res;
    }

    @Override
    protected List<LetterData> onFind(UUID uuid) {
        List<LetterData> res = null;
        Connection conn = this.sqlConnection.getConnection();

        if (conn != null) {
            List<LetterData> temp = new ArrayList<>();
            List<Data> dataList = new DataSQL(this.sqlConnection).onFind(uuid);

            if (dataList != null) {
                PreparedStatement query = null;

                try {
                    query = conn.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE uuid = ?");
                    query.setString(1, uuid.toString());
                    ResultSet resultSet = query.executeQuery();

                    while (resultSet.next()) {
                        LetterType type = LetterType.valueOf(resultSet.getString("type"));
                        List<String> content = fromText(resultSet.getString("content"));
                        boolean isRead = resultSet.getBoolean("isRead");
                        Long id = resultSet.getLong("id");
                        Data tData = dataList.stream().filter(e -> e.getId().equals(id)).findAny().orElse(new DataFactory());
                        temp.add(new LetterData(tData, type, content, isRead));

                    }
                    res = temp;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (query != null) {
                            query.close();
                        }
                    } catch (SQLException ignored) {
                    }
                }
            }
        }
        return res;
    }

    @Override
    protected LetterData onUpdate(Long id, LetterData obj) {
        LetterData res = null;
        Connection conn = this.sqlConnection.getConnection();

        if (conn != null) {
            LetterData temp = obj.clone();
            Data uData = new DataSQL(this.sqlConnection).onUpdate(id, obj);

            if (uData != null) {
                PreparedStatement query = null;

                try {
                    query = conn.prepareStatement("UPDATE " + TABLE_NAME + " SET uuid = ?, content = ?, type = ?, isRead = ? WHERE id = ?");
                    query.setString(1, obj.getOwnerUuid().toString());
                    query.setString(2, toText(obj.getContent()));
                    query.setString(3, obj.getLetterType().name());
                    query.setBoolean(4, obj.getIsRead());
                    query.setLong(5, id);

                    query.executeUpdate();
                    temp.setId(id);

                    res = temp;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (query != null) {
                            query.close();
                        }
                    } catch (SQLException ignored) {
                    }
                }
            }
        }
        return res;
    }

    @Override
    protected boolean onDelete(LetterData obj) {
        boolean res = false;
        Connection conn = this.sqlConnection.getConnection();

        if (conn != null) {
            if (new DataSQL(this.sqlConnection).onDelete(obj)) {
                PreparedStatement query = null;

                try {
                    query = conn.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?");
                    query.setLong(1, obj.getId());
                    query.execute();
                    res = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (query != null) {
                            query.close();
                        }
                    } catch (SQLException ignored) {
                    }
                }
            }
        }
        return res;
    }
}
