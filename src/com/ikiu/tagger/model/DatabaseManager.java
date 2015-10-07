package com.ikiu.tagger.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class DatabaseManager {
    private static final String englishTokenTable = "EnglishTokens";
    private static final String persianTokenTable = "PersianTokens";

    public final static int ENGLISH = 0;
    public final static int PERSIAN = 1;

    public Connection createConnection() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
        System.out.println("Opened database successfully");
        return c;
    }

    public void createEnglishTokenTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS " + englishTokenTable +
                    " (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " TYPE           CHAR(50)    NOT NULL," +
                    " MEANING        INTEGER," +
                    " ISGENERATED    INTEGER," +
                    " WORD           TEXT     NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
        System.out.println("Table created successfully");
    }

    public void createPersianTokenTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS " + persianTokenTable +
                    " (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " TYPE           CHAR(50)    NOT NULL," +
                    " ISGENERATED    INTEGER," +
                    " WORD           TEXT     NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public int insertLanguageToken(int language, Token token) {
        if (language == ENGLISH) {
            return insertEnglishTokenTable(token);
        }
        return insertPersianTokenTable(token);

    }

    private int insertPersianTokenTable(Token token) {
        Connection c = null;
        Statement stmt = null;
        int result = 0;
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "INSERT INTO " + persianTokenTable +
                    " (TYPE,WORD) VALUES('" + token.type + "','" + token.word + "')";
            if(stmt.executeUpdate(sql)>0)
            {
                ResultSet resultSet = stmt.executeQuery("SELECT MAX(id) as id FROM " + persianTokenTable + "");
                while (resultSet.next())
                {
                    result = resultSet.getInt("id");
                    System.out.println(result);
                }
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return result;
    }

    private int insertEnglishTokenTable(Token token) {
        Connection c = null;
        Statement stmt = null;
        int result = 0;
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "INSERT INTO " + englishTokenTable +
                    " (TYPE,WORD) VALUES('" + token.type + "','" + token.word + "')";
            if(stmt.executeUpdate(sql)>0)
            {
                ResultSet resultSet = stmt.executeQuery("SELECT MAX(id) as id FROM " + englishTokenTable + "");
                while (resultSet.next())
                {
                    result = resultSet.getInt("id");
                    System.out.println(result);
                }
            }

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return result;
    }

    private int deleteEnglishToken(int id)
    {
        Connection c = null;
        Statement stmt = null;
        int result = 0;
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "DELETE FROM " + englishTokenTable +
                    " WHERE ID=" +String.valueOf(id);
            result = stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("result="+result);
        return result;
    }

    public int deleteLanguageToken(int language,int id)
    {
        if(language ==ENGLISH)
            return deleteEnglishToken(id);
        else
            return deletePersianToken(id);
    }

    private int deletePersianToken(int id)
    {
        Connection c = null;
        Statement stmt = null;
        int result = 0;
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "DELETE FROM " + persianTokenTable +
                    " WHERE ID=" +String.valueOf(id);
            result = stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return result;
    }

    public Vector<Token> getEnglishTokens() {
        Connection c = null;
        Statement stmt = null;
        Vector<Token> tokens = new Vector<>();
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "SELECT * FROM " + englishTokenTable;
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                Token token = new Token();
                token.setId(resultSet.getInt("ID"));
                token.setMeaning(resultSet.getInt("MEANING"));
                token.setWord(resultSet.getString("WORD"));
                token.setType(resultSet.getString("TYPE"));
                int generated = resultSet.getInt("ISGENERATED");
                boolean isGenerated = false;
                if (generated > 0)
                    isGenerated = true;

                token.setIsGenerated(isGenerated);
                tokens.add(token);
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return tokens;
    }

    public Vector<Token> getPersianTokens() {
        Connection c = null;
        Statement stmt = null;
        Vector<Token> tokens = new Vector<>();
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "SELECT * FROM " + persianTokenTable;
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                Token token = new Token();
                token.setId(resultSet.getInt("ID"));
                token.setMeaning(resultSet.getInt("MEANING"));
                token.setWord(resultSet.getString("WORD"));
                token.setType(resultSet.getString("TYPE"));
                int generated = resultSet.getInt("ISGENERATED");
                boolean isGenerated = false;
                if (generated > 0)
                    isGenerated = true;

                token.setIsGenerated(isGenerated);
                tokens.add(token);
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return tokens;
    }

    public static class Token {
        private String word;
        private String type;
        private int id;
        private int meaning;
        private boolean isGenerated;
        //------

        public Token() {
        }

        public Token(String word, String type, int id, int meaning, boolean isGenerated) {
            this.word = word;
            this.type = type;
            this.id = id;
            this.meaning = meaning;
            this.isGenerated = isGenerated;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMeaning() {
            return meaning;
        }

        public void setMeaning(int meaning) {
            this.meaning = meaning;
        }

        public boolean isGenerated() {
            return isGenerated;
        }

        public void setIsGenerated(boolean isGenerated) {
            this.isGenerated = isGenerated;
        }


    }
}