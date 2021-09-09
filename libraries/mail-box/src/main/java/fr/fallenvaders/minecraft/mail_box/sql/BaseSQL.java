package fr.fallenvaders.minecraft.mail_box.sql;

import fr.fallenvaders.minecraft.mail_box.data_manager.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BaseSQL<T extends Data> {
  protected SQLConnection sqlConnection;

  protected abstract T onCreate(T obj);

  public T create(T obj) {
    T res = null;

    if (this.sqlConnection.startTransaction()) {
      T temp = this.onCreate(obj);

      if (temp != null) {
        if (this.sqlConnection.commit()) {
          res = temp;
        }
      } else {
        this.sqlConnection.rollBack();
      }
    }

    this.sqlConnection.disconnect();

    return res;
  }

  public List<T> createAll(List<T> list) {
    List<T> res = null;

    if (this.sqlConnection.startTransaction()) {
      List<T> temp = new ArrayList<>();

      for (T obj : list) {
        T TTemp = this.onCreate(obj);

        if (TTemp == null) {
          temp = null;
          break;

        } else {
          temp.add(TTemp);
        }
      }

      if (temp != null) {
        if (this.sqlConnection.commit()) {
          res = temp;
        }
      } else {
        this.sqlConnection.rollBack();
      }
    }
    this.sqlConnection.disconnect();
    return res;
  }

  protected abstract List<T> onFind(UUID uuid);

  public List<T> find(UUID uuid) {
    List<T> res = null;

    List<T> temp = this.onFind(uuid);

    if (temp != null) {
      res = temp;
    }
    this.sqlConnection.disconnect();

    return res;
  }

  protected abstract T onUpdate(Long id, T obj);

  public T update(Long id, T obj) {
    T res = null;

    if (this.sqlConnection.startTransaction()) {
      T temp = this.onUpdate(id, obj);

      if (temp != null) {
        if (this.sqlConnection.commit()) {
          res = temp;
        }
      } else {
        this.sqlConnection.rollBack();
      }
    }
    this.sqlConnection.disconnect();
    return res;
  }

  public List<T> updateAll(List<T> list) {
    List<T> res = null;

    if (this.sqlConnection.startTransaction()) {
      List<T> temp = new ArrayList<>();

      for (T obj : list) {
        T tTemp = this.onUpdate(obj.getId(), obj);

        if (tTemp != null) {
          temp.add(tTemp);

        } else {
          temp = null;
          break;
        }
      }

      if (temp != null) {
        if (this.sqlConnection.commit()) {
          res = temp;
        }
      } else {
        this.sqlConnection.rollBack();
      }
    }
    this.sqlConnection.disconnect();
    return res;
  }

  protected abstract boolean onDelete(T obj);

  public boolean delete(T obj) {
    boolean res = false;

    if (this.sqlConnection.startTransaction()) {

      if (this.onDelete(obj)) {
        if (this.sqlConnection.commit()) {
          res = true;
        }
      } else {
        this.sqlConnection.rollBack();
      }
    }

    this.sqlConnection.disconnect();
    return res;
  }

  public boolean deleteAll(List<T> list) {
    boolean res = false;

    if (this.sqlConnection.startTransaction()) {
      boolean temp = true;

      for (T obj : list) {
        boolean TTemp = this.onDelete(obj);

        if (!TTemp) {
          temp = false;
          break;
        }
      }

      if (temp) {
        if (this.sqlConnection.commit()) {
          res = true;
        }
      } else {
        this.sqlConnection.rollBack();
      }
    }

    this.sqlConnection.disconnect();
    return res;
  }
}
