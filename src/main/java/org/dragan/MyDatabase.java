package org.dragan;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    Dotenv dotenv = Dotenv.load();
    private static volatile MyDatabase instance;

    private final String url = "jdbc:postgresql://localhost/" + dotenv.get("DB_NAME");
    private final String user = dotenv.get("DB_USER");
    private final String password = dotenv.get("DB_PASSWORD");
    private final Connection connection;

    public MyDatabase() throws SQLException {
        this.connection = connect();
    }

    public static MyDatabase getInstance() throws SQLException {
        MyDatabase result = instance;
        if (result != null) {
            return result;
        }
        synchronized (MyDatabase.class) {
            if (instance == null) {
                instance = new MyDatabase();
            }
            return instance;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to the PostgreSQL server successfully.");

        return conn;
    }

    public int ajouter(Personne p) throws SQLException {
        var instance = MyDatabase.getInstance();
        var conn = instance.getConnection();

        String stm;
        if (p.getId() != null) {
            stm = String.format(
                    "INSERT INTO personnes (id, nom, genre) VALUES (%d, '%s','%s');",
                    p.getId(),
                    p.getNom(),
                    p.getGenre()
            );
        } else {
            stm = String.format(
                    "INSERT INTO personnes (nom, genre) VALUES ('%s', '%s');",
                    p.getNom(),
                    p.getGenre()
            );
        }


        var statement = conn.prepareStatement(stm);
        return statement.executeUpdate();
    }
}
