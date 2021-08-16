package fr.fallenvaders.minecraft.mail_box.data_manager;

import fr.fallenvaders.minecraft.mail_box.sql.ItemDataSQL;
import fr.fallenvaders.minecraft.mail_box.sql.LetterDataSQL;
import fr.fallenvaders.minecraft.mail_box.utils.ItemStackBuilder;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import fr.fallenvaders.minecraft.mail_box.utils.MessageLevel;
import fr.fallenvaders.minecraft.mail_box.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MailBoxController {
    private static final String NOT_ENOUGHT_SPACE = LangManager.getValue("string_not_enought_space");

    private static DataHolder getHolderFromDataBase(UUID uuid) {
        DataHolder res = new DataHolder(uuid, new ArrayList<>());
        ItemDataSQL itemDataSQL = new ItemDataSQL();
        List<ItemData> itemDataList = itemDataSQL.find(uuid);
        List<Data> temp = new ArrayList<>();

        if (itemDataList != null) {
            List<ItemData> outOfDateItems = itemDataList.stream().filter(ItemData::isOutOfDate).toList();
            if (outOfDateItems.size() > 0) {
                itemDataSQL.deleteAll(outOfDateItems);
            }
            itemDataList.removeAll(outOfDateItems);

            temp.addAll(itemDataList);
        }

        LetterDataSQL letterDataSQL = new LetterDataSQL();
        List<LetterData> letterDataList = letterDataSQL.find(uuid);

        if (letterDataList != null) {
            temp.addAll(letterDataList);
        }

        res.setDataList(temp);

        return res;
    }

    public static void load(UUID uuid) {
        DataManager.getCache().put(uuid, getHolderFromDataBase(uuid));
    }

    public static void unload(UUID uuid) {
        DataManager.getCache().remove(uuid);
    }

    public static void initialize() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            load(player.getUniqueId());
        }
    }

    public static DataHolder getDataHolder(UUID uuid) {
        DataHolder res = DataManager.getDataHolder(uuid);

        if (res == null) {
            res = getHolderFromDataBase(uuid);
        }
        return res;
    }

    /*
     *
     * Letters
     *
     */

    public static boolean sendLetter(Player player, LetterData letterData) {
        boolean res = false;
        LetterDataSQL letterDataSQL = new LetterDataSQL();
        LetterData temp = letterDataSQL.create(letterData);

        if (temp != null) {
            DataHolder holder = DataManager.getDataHolder(temp.getOwnerUuid());

            if (holder != null) {
                holder.addData(temp);
            }

            // notification
            Player recipient = Bukkit.getPlayer(letterData.getOwnerUuid());
            if (recipient != null) {
                MessageUtils.sendMessage(recipient, MessageLevel.NOTIFICATION, LangManager.getValue("string_receive_letter_notification", letterData.getAuthor()));

            }
            res = true;
        } else {
            MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
            player.closeInventory();
        }

        return res;
    }

    public static boolean sendLetters(Player player, List<LetterData> letters) {
        boolean res = false;

        if (letters != null && !letters.isEmpty()) {
            if (letters.size() > 1) {
                LetterDataSQL letterDataSQL = new LetterDataSQL();
                List<LetterData> sent = letterDataSQL.createAll(letters);

                if (sent != null) {
                    for (LetterData letter : sent) {
                        DataHolder holder = DataManager.getDataHolder(letter.getOwnerUuid());

                        if (holder != null) {
                            holder.addData(letter);
                        }

                        // notification
                        Player recipient = Bukkit.getPlayer(letter.getOwnerUuid());

                        if (recipient != null) {
                            MessageUtils.sendMessage(recipient, MessageLevel.NOTIFICATION, LangManager.getValue("string_receive_letter_notification", letter.getAuthor()));
                        }
                    }
                    res = true;

                } else {
                    MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
                    player.closeInventory();
                }

            } else {
                res = sendLetter(player, letters.get(0));
            }
        }
        return res;
    }

    public static void readLetter(Player player, LetterData letterData) {
        player.openBook(getBookView(letterData));

        if (letterData.getOwnerUuid().equals(player.getUniqueId())) {
            LetterDataSQL letterDataSQL = new LetterDataSQL();

            if (letterDataSQL.update(letterData.getId(), letterData) != null) {
                letterData.setIsRead(true);
            } else {
                MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
            }
        }
    }

    private static ItemStack getBookView(LetterData letterData) {
        StringBuilder letterHead = new StringBuilder();
        letterHead.append(String.format("%s%s:%s %s\n", ChatColor.BOLD, LangManager.getValue("string_author"), ChatColor.RESET, letterData.getAuthor()));

        SimpleDateFormat sdf = new SimpleDateFormat(LangManager.getValue("string_date_format"));

        letterHead.append(String.format("%s%s:%s %s\n", ChatColor.BOLD, LangManager.getValue("string_reception_date"), ChatColor.RESET, sdf.format(letterData.getCreationDate())));
        letterHead.append(String.format("%s%s:%s %s\n", ChatColor.BOLD, LangManager.getValue("string_object"), ChatColor.RESET, letterData.getObject()));

        ItemStack book = new ItemStackBuilder(Material.WRITTEN_BOOK).build();
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setAuthor(letterData.getAuthor());
        List<String> pages = new ArrayList<>();
        pages.add(letterHead.toString());
        pages.addAll(letterData.getContent());

        bookMeta.setPages(pages);
        bookMeta.setTitle(letterData.getObject());

        book.setItemMeta(bookMeta);

        return book;
    }

    public static boolean deleteLetter(Player player, DataHolder holder, LetterData letterData) {
        boolean res = false;
        LetterDataSQL letterDataSQL = new LetterDataSQL();

        if (letterDataSQL.delete(letterData)) {
            holder.removeData(letterData.getId());
            res = true;

        } else {
            MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
            player.closeInventory();
        }

        return res;
    }

    public static boolean deleteLetters(Player player, DataHolder holder, List<LetterData> dataList) {
        boolean res = false;
        LetterDataSQL letterDataSQL = new LetterDataSQL();

        if (letterDataSQL.deleteAll(dataList)) {
            holder.removeDatas(dataList.stream().map(LetterData::getId).collect(Collectors.toList()));
            res = true;

        } else {
            MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
            player.closeInventory();
        }

        return res;
    }

    /*
     *
     * Items
     */

    public static boolean sendItem(Player player, ItemData itemData) {
        boolean res = false;
        ItemDataSQL itemDataSQL = new ItemDataSQL();
        ItemData temp = itemDataSQL.create(itemData);

        if (temp != null) {
            DataHolder holder = DataManager.getDataHolder(temp.getOwnerUuid());

            if (holder != null) {
                holder.addData(temp);
            }

            Player recipient = Bukkit.getPlayer(itemData.getOwnerUuid());
            if (recipient != null) {
                MessageUtils.sendMessage(recipient, MessageLevel.NOTIFICATION, LangManager.getValue("string_receive_item_notification", itemData.getAuthor()));
            }

            res = true;

        } else {
            MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
            player.closeInventory();
        }

        return res;

    }

    public static boolean sendItems(Player player, List<ItemData> items) {
        boolean res = false;

        if (items != null && !items.isEmpty()) {
            if (items.size() > 1) {
                ItemDataSQL itemDataSQL = new ItemDataSQL();
                List<ItemData> sent = itemDataSQL.createAll(items);

                if (sent != null) {
                    for (ItemData item : sent) {
                        DataHolder holder = DataManager.getDataHolder(item.getOwnerUuid());

                        if (holder != null) {
                            holder.addData(item);
                        }

                        // notification
                        Player recipient = Bukkit.getPlayer(item.getOwnerUuid());
                        if (recipient != null) {
                            MessageUtils
                                .sendMessage(
                                    recipient,
                                    MessageLevel.NOTIFICATION,
                                    LangManager.getValue("string_receive_item_notification", item.getAuthor())
                                );
                        }
                    }
                    res = true;

                } else {
                    MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
                    player.closeInventory();
                }

            } else {
                res = sendItem(player, items.get(0));
            }
        }

        return res;
    }

    public static boolean deleteItem(Player player, DataHolder holder, ItemData itemData) {
        boolean res = false;

        if (holder.getData(itemData.getId()) != null) {
            ItemDataSQL itemDataSQL = new ItemDataSQL();

            if (itemDataSQL.delete(itemData)) {
                holder.removeData(itemData.getId());
                res = true;

            } else {
                MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
                player.closeInventory();
            }
        } else {
            res = true; // si l'objet existe plus, n'envoie pas de message d 'erreur
        }
        return res;
    }

    public static boolean deleteItems(Player player, DataHolder holder, List<ItemData> dataList) {
        boolean res = false;

        ItemDataSQL itemDataSQL = new ItemDataSQL();

        if (itemDataSQL.deleteAll(dataList)) {
            holder.removeDatas(dataList.stream().map(ItemData::getId).collect(Collectors.toList()));
            res = true;

        } else {
            MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
            player.closeInventory();
        }

        return res;
    }

    public static boolean recoverItem(Player player, DataHolder holder, ItemData itemData) {
        boolean success = false;

        if (player.getInventory().firstEmpty() >= 0) {
            boolean t = deleteItem(player, holder, itemData);

            if (t) {
                player.getInventory().addItem(itemData.getItem());
                success = true;

            } else {
                MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_error_player"));
                player.closeInventory();
            }

        } else {
            MessageUtils.sendMessage(player, MessageLevel.ERROR, NOT_ENOUGHT_SPACE);
        }

        return success;
    }

    /*
     * generic
     */

    public static boolean deleteData(Player player, DataHolder holder, Data data) {
        boolean res = false;

        if (data instanceof ItemData) {
            res = deleteItem(player, holder, (ItemData) data);

        } else if (data instanceof LetterData) {
            res = deleteLetter(player, holder, (LetterData) data);
        }

        return res;
    }

    public static boolean deleteDatas(Player player, DataHolder holder, List<Data> dataList) {
        boolean res = true;
        List<ItemData> itemDataList = new ArrayList<>();
        List<LetterData> letterDataList = new ArrayList<>();

        for (Data data : dataList) {
            if (data instanceof ItemData) {
                itemDataList.add((ItemData) data);

            } else if (data instanceof LetterData) {
                letterDataList.add((LetterData) data);

            }
        }

        if (!itemDataList.isEmpty()) {
            res = deleteItems(player, holder, itemDataList);
        }

        if (res && !letterDataList.isEmpty()) {
            res = deleteLetters(player, holder, letterDataList);
        }

        return res;
    }
}
