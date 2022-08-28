package src.core.model.handlers;

import java.sql.*;
import java.util.*;
import java.time.ZonedDateTime;

import src.util.User;
import src.core.model.elements.*;

public class DatabaseHandler {
    private final Connection conn;

    public DatabaseHandler(String url, String login, String password) throws SQLException { // возможно стоит обработать?
        this.conn = DriverManager.getConnection(url, login, password);
        createTables();
    }

    public Connection getConnection() { return conn; }

    public void close() throws SQLException {
        conn.close();
    }

    public PriorityQueue<HumanBeing> readFromDB(PriorityQueue<HumanBeing> queue) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM collection");
            while (result.next()) queue.add(readHumanBeing(result));
            return queue;
        }
    }

    private HumanBeing readHumanBeing(ResultSet line) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("SELECT password FROM users WHERE login = ?");
        stm.setString(1, line.getString("owner"));
        ResultSet res = stm.executeQuery();
        res.next();
        return new HumanBeing(
            line.getLong("id"),
            line.getString("name"),
            new Coordinates(line.getInt("x"), Long.valueOf(line.getLong("y"))),
            ZonedDateTime.parse(line.getString("creationDate")),
            line.getBoolean("realHero"),
            line.getString("hasToothpick") == null ? null : Boolean.valueOf(line.getBoolean("hasToothpick")),
            line.getInt("impactSpeed"),
            line.getString("soundtrackName"),
            WeaponType.valueOf(line.getString("weaponType")),
            line.getString("mood") == null ? null : Mood.valueOf(line.getString("mood")),
            line.getString("car")  == null ? null : new Car(line.getBoolean("car")),
            new User(line.getString("owner"), res.getString(1)));
    }

    private void createTables() throws SQLException {
        String elementId = "CREATE SEQUENCE IF NOT EXISTS elementId START 1";

        String userTable = "CREATE TABLE IF NOT EXISTS users (" +
                "login    VARCHAR(32)  PRIMARY KEY, " +
                "password VARCHAR(128) NOT NULL)";

        String collectionTable = "CREATE TABLE IF NOT EXISTS collection (" +
                "id             BIGINT    PRIMARY KEY," +
                "name           VARCHAR(100) NOT NULL," +
                "x              INTEGER      NOT NULL," +
                "y              BIGINT       NOT NUll," +
                "creationDate   VARCHAR(128) NOT NULL," +
                "realHero       BOOLEAN      NOT NULL," +
                "hasToothpick   BOOLEAN          NULL," +
                "impactSpeed    INTEGER      NOT NULL," +
                "soundtrackName TEXT         NOT NULL," +
                "weaponType     VARCHAR(11)  NOT NULL," +
                "mood           VARCHAR(7)       NULL, " +
                "car            BOOLEAN          NUll, " +
                "owner          VARCHAR(32)  REFERENCES users(login) NOT NULL" +
                ")";
        try (Statement statement = conn.createStatement()) {
            statement.execute(elementId);
            statement.execute(userTable);
            statement.execute(collectionTable);
        }
    }
}
