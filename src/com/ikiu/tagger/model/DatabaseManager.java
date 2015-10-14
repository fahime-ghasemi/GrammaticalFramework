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

    public int insertLanguageToken(int language, TokenTableRow tokenTableRow) {
        if (language == ENGLISH) {
            return insertEnglishToken(tokenTableRow);
        }
        return insertPersianToken(tokenTableRow);

    }

    private int insertPersianToken(TokenTableRow tokenTableRow) {
        Connection c = null;
        Statement stmt = null;
        int result = 0;
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "INSERT INTO " + persianTokenTable +
                    " (TYPE,WORD) VALUES('" + tokenTableRow.type + "','" + tokenTableRow.word + "')";
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

    private int insertEnglishToken(TokenTableRow tokenTableRow) {
        Connection c = null;
        Statement stmt = null;
        int result = 0;
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "INSERT INTO " + englishTokenTable +
                    " (TYPE,WORD) VALUES('" + tokenTableRow.type + "','" + tokenTableRow.word + "')";
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

    public int updateLanguageToken(TokenTableRow tokenTableRow, int language)
    {
        if(language ==ENGLISH )
            return updateEnglishToken(tokenTableRow);
        return updatePersianToken(tokenTableRow);
    }
    private int updateEnglishToken(TokenTableRow tokenTableRow)
    {
        Connection c = null;
        Statement stmt = null;
        int result = 0;
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "UPDATE " + englishTokenTable +
                    " SET WORD='"+tokenTableRow.getWord()+"',TYPE='"+tokenTableRow.getType()+"',MEANING="+tokenTableRow.getMeaning()
                    +" WHERE ID="+tokenTableRow.getId();
            result = stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return result;
    }
    private int updatePersianToken(TokenTableRow tokenTableRow)
    {
        Connection c = null;
        Statement stmt = null;
        int result = 0;
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "UPDATE " + persianTokenTable +
                    " SET WORD='"+tokenTableRow.getWord()+"',TYPE='"+tokenTableRow.getType()
                    +"' WHERE ID="+tokenTableRow.getId();
            result = stmt.executeUpdate(sql);

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

    public Vector<TokenTableRow> getEnglishTokens() {
        Connection c = null;
        Statement stmt = null;
        Vector<TokenTableRow> tokenTableRows = new Vector<TokenTableRow>();
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "SELECT * FROM " + englishTokenTable;
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                TokenTableRow tokenTableRow = new TokenTableRow();
                tokenTableRow.setId(resultSet.getInt("ID"));
                tokenTableRow.setMeaning(resultSet.getInt("MEANING"));
                tokenTableRow.setWord(resultSet.getString("WORD"));
                tokenTableRow.setType(resultSet.getString("TYPE"));
                int generated = resultSet.getInt("ISGENERATED");
                boolean isGenerated = false;
                if (generated > 0)
                    isGenerated = true;

                tokenTableRow.setIsGenerated(isGenerated);
                tokenTableRows.add(tokenTableRow);
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return tokenTableRows;
    }

    public Vector<TokenTableRow> getPersianTokens() {
        Connection c = null;
        Statement stmt = null;
        Vector<TokenTableRow> tokenTableRows = new Vector<TokenTableRow>();
        try {
            c = createConnection();
            stmt = c.createStatement();
            String sql = "SELECT * FROM " + persianTokenTable;
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                TokenTableRow tokenTableRow = new TokenTableRow();
                tokenTableRow.setId(resultSet.getInt("ID"));
                tokenTableRow.setMeaning(resultSet.getInt("MEANING"));
                tokenTableRow.setWord(resultSet.getString("WORD"));
                tokenTableRow.setType(resultSet.getString("TYPE"));
                int generated = resultSet.getInt("ISGENERATED");
                boolean isGenerated = false;
                if (generated > 0)
                    isGenerated = true;

                tokenTableRow.setIsGenerated(isGenerated);
                tokenTableRows.add(tokenTableRow);
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return tokenTableRows;
    }

    public static class TokenTableRow {
        private String word;
        private String type;
        private int id;
        private int meaning;
        private boolean isGenerated;
        private int editedCells;
        //------

        public TokenTableRow() {
        }

        public TokenTableRow(String word, String type, int id, int meaning, boolean isGenerated) {
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

        public int getEditedCells() {
            return editedCells;
        }

        public void setEditedCells(int editedCells) {
            this.editedCells = editedCells;
        }
    }
}