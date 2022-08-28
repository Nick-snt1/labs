package src.core.model.handlers;

import org.postgresql.util.PSQLException;
import java.sql.*;
import java.util.*;

import src.core.model.elements.*;
import src.util.*;

public class UsersCollectionHandler {
    private Connection conn;

    private Map<String, PreparedStatement> map = new HashMap<>();

    public UsersCollectionHandler(Connection conn) throws Exception {
        this.conn = conn;

        map.put("nextVal",       conn.prepareStatement("SELECT nextval('elementId')"));
        map.put("registration",  conn.prepareStatement("INSERT INTO users VALUES(?, ?)"));
        map.put("authorization", conn.prepareStatement("SELECT password FROM users WHERE login = ?"));
        map.put("ownedElements", conn.prepareStatement("SELECT * FROM collection WHERE owner = ?"));
        map.put("delete",        conn.prepareStatement("DELETE FROM collection WHERE id = ?"));
        map.put("update",
            conn.prepareStatement(
                "UPDATE collection SET (name, x, y, creationDate, realHero, hasToothpick, impactSpeed, " +
                    "soundtrackName, weaponType, mood, car) = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE id = ?"));
        map.put("add",
            conn.prepareStatement(
                "INSERT INTO collection (id, name, x, y, creationDate, realHero, hasToothpick, impactSpeed, " +
                    "soundtrackName, weaponType, mood, car, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
    }

    public synchronized Respond registration(User user) throws Exception {
        try {
            PreparedStatement statement = map.get("registration");
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.execute();
            return new Respond("OK");
        } catch (PSQLException e) {
            return new Respond("User with login " + user.getLogin() + " already exists!");
        }
    }

    public synchronized Respond authorization(User user) throws Exception {
        try {
            PreparedStatement statement = map.get("authorization");
            statement.setString(1, user.getLogin());
            ResultSet result = statement.executeQuery();
            result.next();

            return new Respond(result.getString(1).equals(user.getPassword()) ? "OK" : "Wrong password!");
        } catch (PSQLException e) {
            return new Respond("There are no user with login " + user.getLogin());
        }
    }
    public synchronized User usersClear(User user) throws Exception {
       ResultSet result = getOwnedElements(user);
       try {
           conn.setAutoCommit(false); conn.setSavepoint();
           while (result.next()) execDelete(map.get("delete"), result.getInt("id"));
           conn.commit(); return user;
       } catch (SQLException e) {
           conn.rollback(); return null;
       } finally {
           conn.setAutoCommit(true);
       }
   }

   public synchronized User usersRemoveById(User user, long id) throws Exception {
       ResultSet result = getOwnedElements(user);
       boolean flag = false;
       while (result.next()) {
           if (result.getLong("id") == id) {
               flag = true;
               execDelete(map.get("delete"), id);
               break;
           }
       }
       return flag ? user : null;
   }

   public synchronized HumanBeing usersAdd(HumanBeing h) throws Exception {
       try {
           long id = getNextVal();
           PreparedStatement stmt = map.get("add");
           stmt.setLong(   1, id);
           stmt.setString( 2, h.getName());
           stmt.setInt(    3, h.getCoordinates().getX());
           stmt.setLong(   4, h.getCoordinates().getY());
           stmt.setString( 5, ""+h.getCreationDate());
           stmt.setBoolean(6, h.isRealHero());
           stmt.setObject( 7, h.hasToothpick().isPresent() ? h.hasToothpick().get() : null);
           stmt.setInt(    8, h.getImpactSpeed());
           stmt.setString( 9, h.getSoundtrackName());
           stmt.setString( 10, h.getWeaponType().name());
           stmt.setObject( 11, h.getMood().isPresent() ? h.getMood().get().name() : null);
           stmt.setObject( 12, h.getCar().isPresent() ? h.getCar().get().isCool() : null);
           stmt.setString( 13, h.getOwner().getLogin());

           stmt.execute();
           return h.setId(id);
       } catch (SQLException e) {
           e.printStackTrace();
           return null;
       }
   }
   private synchronized long getNextVal() throws Exception  {
       ResultSet res = map.get("nextVal").executeQuery(); res.next();
       return res.getLong(1);
   }

   public synchronized HumanBeing usersAddIfMin(HumanBeing h) throws Exception {
       ResultSet res = getOwnedElements(h.getOwner());
       long humansValue =  getHumansVal(h), minVal = humansValue + 1;
       while (res.next()) minVal = Math.min(minVal, getResultVal(res));
       return humansValue < minVal ? usersAdd(h) : null;
   }

   public synchronized HumanBeing usersRemoveLower(HumanBeing h) throws Exception {
       ResultSet res = getOwnedElements(h.getOwner());
       long humansValue =  getHumansVal(h);
       try {
           conn.setAutoCommit(false); conn.setSavepoint();
           while (res.next())
               if (getResultVal(res) < humansValue)
                   execDelete(map.get("delete"), res.getInt("id"));
           conn.commit(); return h;
       } catch (SQLException e) {
           conn.rollback(); return null;
       } finally {
           conn.setAutoCommit(true);
       }
   }

   private void execDelete(PreparedStatement stmt, long id) throws Exception {
           stmt.setLong(1, id); stmt.execute();
   }

   public synchronized HumanBeing usersRemoveGreater(HumanBeing h) throws Exception {
       ResultSet res = getOwnedElements(h.getOwner());
       long humansValue =  getHumansVal(h);
       try {
           conn.setAutoCommit(false); conn.setSavepoint();
           while (res.next())
               if (getResultVal(res) > humansValue)
                   execDelete(map.get("delete"), res.getInt("id"));
           conn.commit(); return h;
       } catch (SQLException e) {
           conn.rollback(); return null;
       } finally {
           conn.setAutoCommit(true);
       }
   }

   public synchronized HumanBeing usersUpdate(long id, HumanBeing h) throws Exception {
       ResultSet res = getOwnedElements(h.getOwner());
       boolean flag = false;
       while (res.next()) {
           if (res.getLong("id") == id) {
               flag = true;
               PreparedStatement stmt = map.get("update");
               stmt.setString (1,  h.getName());
               stmt.setInt    (2,  h.getCoordinates().getX());
               stmt.setLong   (3,  h.getCoordinates().getY());
               stmt.setString (4,  ""+h.getCreationDate());
               stmt.setBoolean(5,  h.isRealHero());
               stmt.setObject (6,  h.hasToothpick().isPresent() ? h.hasToothpick().get() : null);
               stmt.setInt    (7,  h.getImpactSpeed());
               stmt.setString (8,  h.getSoundtrackName());
               stmt.setString (9,  h.getWeaponType().name());
               stmt.setObject (10, h.getMood().isPresent() ? h.getMood().get().name() : null);
               stmt.setObject (11, h.getCar().isPresent() ? h.getCar().get().isCool() : null);
               stmt.setLong   (12, id);
               stmt.execute();
               break;
           }
       }
       return flag ? h : null;
   }

   private synchronized ResultSet getOwnedElements(User user) throws Exception {
       PreparedStatement statement = map.get("ownedElements");
       statement.setString(1, user.getLogin());
       return statement.executeQuery();
   }

   public void closeStatements() {
       map.values().iterator().forEachRemaining(x -> {
           try { x.close(); } catch (SQLException e) { e.printStackTrace(); }
       });
   }

   private long getHumansVal(HumanBeing h) {
       return h.getCoordinates().getX() + h.getCoordinates().getY() + h.getImpactSpeed();
   }

   private long getResultVal(ResultSet res) throws Exception {
       return res.getInt("x") + res.getLong("y") + res.getInt("impactSpeed");
   }

   public synchronized boolean checkAuth(User user) throws Exception {
       try {
           PreparedStatement statement = map.get("authorization");
           statement.setString(1, user.getLogin());
           ResultSet result = statement.executeQuery();
           result.next();
           return result.getString(1).equals(user.getPassword());
       } catch (PSQLException e) {
           return false;
       }
   }
}

/*
public class UsersCollectionHandler {
    private Connection conn;

    private Map<String, PreparedStatement> map = new HashMap<>();

    public UsersCollectionHandler(Connection conn) {
        this.conn = conn;

        map.put("nextVal",       conn.prepareStatement("SELECT nextval('elementId')"));
        map.put("registration",  conn.prepareStatement("INSERT INTO users VALUES(?, ?)"));
        map.put("authorization", conn.prepareStatement("SELECT password FROM users WHERE login = ?"));
        map.put("ownedElements", conn.prepareStatement("SELECT * FROM collection WHERE owner = ?"));
        map.put("delete",        conn.prepareStatement("DELETE FROM collection WHERE id = ?"));
        map.put("update",
            conn.prepareStatement(
                "UPDATE collection SET (name, x, y, creationDate, realHero, hasToothpick, impactSpeed, " +
                    "soundtrackName, weaponType, mood, car) = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
        map.put("add",
            conn.prepareStatement(
                "INSERT INTO collection (name, x, y, creationDate, realHero, hasToothpick, impactSpeed, " +
                    "soundtrackName, weaponType, mood, car, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
    }

    public String registration(User user) throws SQLException {
        try {
            PreparedStatement statement = map.get("registration");
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.execute();
            return "OK";
        } catch (PSQLException e) {
            return "User with login " + login + " already exists!";
        }
    }

    public String authorization(User user) throws SQLException {
        try {
            PreparedStatement statement = map.get("authorization");
            statement.setString(1, user.getLogin());
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getString(1).equals(user.getPassword()) ? "OK" : "Wrong password!";
        } catch (PSQLException e) {
            return "There are no user with login " + user.getLogin();
        }
    }
    public boolean usersClear(User user) throws SQLException {
       ResultSet result = getOwnedElements(user);
       try {
           conn.setAutoCommit(false); conn.setSavepoint();
           while (result.next()) execDelete(map.get("delete"), res.getInt("id"));
           conn.commit(); return true;
       } catch (SQLException e) {
           conn.rollback(); return false;
       } finally {
           conn.setAutoCommit(true);
       }
   }

   public boolean usersRemoveById(User user, int id) throws SQLException {
       ResultSet result = getOwnedElements(user);
       boolean flag = false;
       while (result.next()) {
           if (result.getInt("id") == id) {
               flag = true;
               execDelete(map.get("delete"), id);
               break;
           }
       }
       return flag;
   }

   public HumanBeing usersAdd(User user, HumanBeing h) {
       try {
           long id = getNextVal();
           PreparedStatement stmt = map.get("add");
           stmt.setLong(   1, id);
           stmt.setString( 2, h.getName());
           stmt.setInt(    3, h.getCoordinates().getX());
           stmt.setLong(   4, h.getCoordinates().getY());
           stmt.setString( 5, ""+h.getCreationDate());
           stmt.setBoolean(6, h.isRealHero());
           stmt.setBoolean(7, h.hasToothpick().isPresent() ? h.hasToothpick().get() : null);
           stmt.setInt(    8, h.getImpactSpeed());
           stmt.setString( 9, h.getSoundtrackName());
           stmt.setString( 10, h.getWeaponType().name());
           stmt.setString( 11, h.getMood().isPresent() ? h.getMood().get().name() : null);
           stmt.setBoolean(12, h.getCar().isPresent() ? h.getCar().get().isCool() : null);
           stmt.setString( 13, user.getLogin());

           stmt.execute();
           return h.setId(id);
       } catch (SQLException e) {
           e.printStackTrace();
           return null;
       }
   }
   private long getNextVal() {
       ResultSet res = map.get("nextVal").executeQuery();
       res.next();
       return res.getLong(1);
   }

   public HumanBeing usersAddIfMin(User user, HumanBeing h) throws SQLException {
       ResultSet res = getOwnedElements(user);
       long humansValue =  getHumansVal(h), minVal = humansValue + 1;
       while (res.next()) minVal = Math.min(minVal, getResultVal(res));
       return humansValue < minVal ? usersAdd(user, h) : null;
   }

   public boolean usersRemoveLower(User user, HumanBeing h) throws SQLException {
       ResultSet res = getOwnedElements(user);
       long humansValue =  getHumansVal(h);
       try {
           conn.setAutoCommit(false); conn.setSavepoint();
           while (res.next())
               if (getResultVal(res) < humansValue)
                   execDelete(map.get("delete"), res.getInt("id"));
           conn.commit(); return true;
       } catch (SQLException e) {
           conn.rollback(); return false;
       } finally {
           conn.setAutoCommit(true);
       }
   }

   private void execDelete(PreparedStatement stmt, int id) throws SQLException {
           stmt.setInt(1, id); stmt.execute();
   }

   public boolean usersRemoveGreater(User user, HumanBeing h) throws SQLException {
       ResultSet res = getOwnedElements(user);
       long humansValue =  getHumansVal(h);
       try {
           conn.setAutoCommit(false); conn.setSavepoint();
           while (res.next())
               if (getResultVal(res) > humansValue)
                   execDelete(map.get("delete"), res.getInt("id"));
           conn.commit(); return true;
       } catch (SQLException e) {
           conn.rollback(); return false;
       } finally {
           conn.setAutoCommit(true);
       }
   }

   public boolean usersUpdate(User user, int id, HumanBeing h) throws SQLException {
       ResultSet res = getOwnedElements(user);
       boolean flag = false;
       while (res.next()) {
           if (res.getInt("id") == id) {
               flag = true;
               PreparedStatement stmt = map.get("update");
               stmt.setString(1, h.getName());
               stmt.setInt(2, h.getCoordinates().getX());
               stmt.setLong(3, h.getCoordinates().getY());
               stmt.setString(4, ""+h.getCreationDate());
               stmt.setBoolean(5, h.isRealHero());
               stmt.setBoolean(6, h.hasToothpick().isPresent() ? h.hasToothpick().get() : null);
               stmt.setInt(7, h.getImpactSpeed());
               stmt.setString(8, h.getSoundtrackName());
               stmt.setString(9, h.getWeaponType().name());
               stmt.setString(10, h.getMood().isPresent() ? h.getMood().get().name() : null);
               stmt.setBoolean(11, h.getCar().isPresent() ? h.getCar().get().isCool() : null);
               stmt.execute();
               break;
           }
       }
       return flag;
   }

   public void closeStatements() {
       map.values().iterator().forEachRemaining(x -> {
           try { x.close(); } catch (SQLException e) { e.printStackTrace(); }
       });
   }

   private long getHumansVal(HumanBeing h) {
       return h.getCoordinates().getX() + h.getCoordinates().getY() + h.getImpactSpeed();
   }

   private long getResultVal(ResultSet res) throws SQLException {
       return res.getInt("x") + res.getLong("y") + res.getInt("impactSpeed");
   }

   public ResultSet getOwnedElements(User user) throws SQLException {
       PreparedStatement statement = map.get("ownedElements");
       statement.setString(1, login);
       return statement.executeQuery();
   }
}
*/
