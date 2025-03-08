package com.example;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:sqlite:clipboard_history.db";

    public static String getClipboardContents(){
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String) contents.getTransferData(DataFlavor.stringFlavor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setClipboardContents(String text) {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(text);
            clipboard.setContents(selection, selection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restoreClipboardEntry(int id) {
        String querySQL = "SELECT text FROM clipboard_history WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String text = rs.getString("text");
                setClipboardContents(text);
                System.out.println("✅ Restored to clipboard: " + text);
            } else {
                System.out.println("❌ Entry not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseManager.initializeDatabase(); // Initialize database

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an option: ");
        System.out.println("1️⃣ Save clipboard content");
        System.out.println("2️⃣ View clipboard history");
        System.out.println("3️⃣ Restore clipboard entry");
        System.out.println("4️⃣ Search clipboard history 🔍");
        System.out.print("> ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            String clipboardText = getClipboardContents();
            if (clipboardText != null && !clipboardText.isEmpty()) {
                DatabaseManager.saveClipboardEntry(clipboardText);
                System.out.println("✅ Saved to history: " + clipboardText);
            } else {
                System.out.println("⚠️ Clipboard is empty!");
            }
        } else if (choice == 2) {
            DatabaseManager.getClipboardHistory();
        } else if (choice == 3) {
            DatabaseManager.getClipboardHistory(); // Show history first
            System.out.print("Enter the ID of the entry to restore: ");
            int id = scanner.nextInt();
            restoreClipboardEntry(id);
        } else if (choice == 4) {
            System.out.print("Enter a keyword to search: ");
            String keyword = scanner.nextLine();
            DatabaseManager.searchClipboardHistory(keyword);
        }
        else {
            System.out.println("❌ Invalid choice!");
        }
        scanner.close();
    }
}