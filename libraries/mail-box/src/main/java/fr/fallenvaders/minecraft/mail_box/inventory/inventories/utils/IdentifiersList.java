package fr.fallenvaders.minecraft.mail_box.inventory.inventories.utils;

import fr.fallenvaders.minecraft.mail_box.player_manager.PlayerInfo;
import fr.fallenvaders.minecraft.mail_box.player_manager.PlayerManager;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IdentifiersList {

  private String sender;

  private String server = "";
  //private String faction = "";
  private List<String> precise = new ArrayList<>();
  private boolean calculList = false;
  private List<PlayerInfo> list = new ArrayList<>();
  private List<String> listPreview = new ArrayList<>();
  private boolean calculPreview = true;

  public IdentifiersList(String sender) {
    this.setSender(sender);
  }

  public boolean isEmpty() {
    return this.server.isEmpty() && this.precise.isEmpty();
  }

  public boolean addIdentifier(String str) {
    boolean res = false;
    String identifier = str.replace(" ", "");

    if (identifier.equals("#online") || identifier.equals("#offline") || identifier.equals("#registered")) {

      if (identifier.equals("#registered")) {
        this.server = identifier;

      } else {
        String antonym = identifier.equals("#online") ? "#offline" : "#online";

        if (this.server.equals(antonym)) {
          this.server = "#registered";

        } else {
          this.server = identifier;
        }
      }
      this.calculList = true;
      this.calculPreview = true;
      res = true;

    } else if (identifier.startsWith("#faction")) {
      //TODO factions :shrugging:

    } else {
      if (this.getSender() == null || !identifier.contentEquals(this.getSender())) {
        UUID pUuid = PlayerManager.getInstance().getUUID(identifier);

        if (pUuid != null) {
          if (!this.precise.contains(identifier)) {
            this.precise.add(identifier);
            this.calculList = true;
            this.calculPreview = true;
          }
          res = true;
        }
      }
    }

    return res;
  }

  public void clear() {
    this.server = "";
    //this.faction = "";
    this.precise = new ArrayList<>();
    this.calculList = false;
    this.list = new ArrayList<>();
    this.listPreview = new ArrayList<>();
    this.calculPreview = true;
  }

  public String addAllIdentifiers(List<String> list) {
    String res = null;

    if (!list.isEmpty()) {
      for (int index = 0; index < list.size() && res == null; index++) {
        String identifier = list.get(index).replace(" ", "");
        boolean s = this.addIdentifier(identifier);

        if (!s) {
          res = identifier;
        }
      }
    }

    return res;
  }

  public List<String> getPreviewLore() {
    if (this.calculPreview) {
      List<String> temp = new ArrayList<>();

      if (!this.server.isEmpty()) {
        switch (this.server) {
          case "#registered" -> temp.add(LangManager.getValue("string_registered_players"));
          case "#online" -> temp.add(LangManager.getValue("string_online_players"));
          case "#offline" -> temp.add(LangManager.getValue("string_offline_players"));
        }
      }

      if (!this.precise.isEmpty()) {
        String names = StringUtils.join(this.precise, ", ");

        StringBuilder b = new StringBuilder(names);
        if (b.toString().contains(",")) {
          b.replace(names.lastIndexOf(", "), names.lastIndexOf(", ") + 1, " " + LangManager.getValue("string_string_addition"));
        }
        names = b.toString();

        temp.add(names);
      }

      if (temp.isEmpty()) {
        temp.add(LangManager.getValue("string_no_players"));
      }

      this.calculPreview = false;
      this.listPreview = temp;
    }

    return this.listPreview;
  }

  public String getPreviewString() {
    return this.getPreviewLore().toString().replace("[", "").replace("]", "");
  }

  public List<PlayerInfo> getPlayerList() {
    if (this.calculList) {
      List<PlayerInfo> temp = new ArrayList<>();

      if (!this.server.isEmpty()) {
        switch (this.server) {
          case "#registered" -> temp.addAll(PlayerManager.getInstance().getRegisteredPlayers(this.getSender()));
          case "#online" -> temp.addAll(PlayerManager.getInstance().getOnlinePlayers(this.getSender()));
          case "#offline" -> temp.addAll(PlayerManager.getInstance().getOfflinePlayers(this.getSender()));
        }
      }

      if (!this.precise.isEmpty()) {
        for (String name : this.precise) {
          if (this.getSender() == null || !name.equals(this.getSender())) {
            UUID pUuid = PlayerManager.getInstance().getUUID(name);

            if (pUuid != null) {
              PlayerInfo pi = new PlayerInfo(name, pUuid);

              if (!temp.contains(pi)) {
                temp.add(pi);
              }
            }
          }
        }
      }
      this.calculList = false;
      this.list = temp;
    }

    return this.list;
  }

  private String getSender() {
    return sender;
  }

  private void setSender(String sender) {
    this.sender = sender;
  }

}