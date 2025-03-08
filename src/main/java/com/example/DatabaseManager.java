package com.example;

import java.sql.*;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:clipboard_history.db";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()){

            // Create table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS clipboard_history ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "text TEXT UNIQUE, "
                    + "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createTableSQL);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void saveClipboardEntry(String text) {
        String insertSQL = "INSERT OR IGNORE INTO clipboard_history (text) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)){

            pstmt.setString(1,text);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void getClipboardHistory() {
        String querySQL = "SELECT id, text, timestamp FROM clipboard_history ORDER BY timestamp DESC LIMIT 10";
        try(Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(querySQL)) {

            System.out.println("📋 Clipboard History:");
            while (rs.next()){
                int id = rs.getInt("id");
                String text = rs.getString("text");
                String timestamp = rs.getString("timestamp");

                System.out.println(id + ". " + text + " (⏳ " + timestamp + ")");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void searchClipboardHistory(String keyword) {
        String querySQL = "SELECT id, text, timestamp FROM clipboard_history WHERE text LIKE ? ORDER BY timestamp DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            System.out.println("🔍 Search Results for '" + keyword + "':");
            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("id");
                String text = rs.getString("text");
                String timestamp = rs.getString("timestamp");

                System.out.println(id + ". " + text + " (⏳ " + timestamp + ")");
            }
            if (!found) {
                System.out.println("❌ No matches found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearClipboardHistory() {
        String deleteSQL = "DELETE FROM clipboard_history";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(deleteSQL);
            System.out.println("🗑️ Clipboard history cleared! (" + rowsAffected + " entries deleted)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteClipboardEntry(int id) {
        String deleteSQL = "DELETE FROM clipboard_history WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("🗑️ Entry " + id + " deleted!");
            } else {
                System.out.println("❌ No entry found with ID: " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
