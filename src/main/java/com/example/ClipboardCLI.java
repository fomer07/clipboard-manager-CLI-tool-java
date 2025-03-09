package com.example;


import static com.example.DatabaseManager.restoreClipboardEntry;

public class ClipboardCLI {

    public static void main(String[] args) {

        DatabaseManager.initializeDatabase();

        if (args.length == 0) {
            System.out.println("❌ No command provided! Usage:");
            System.out.println("  clipboard-cli save <text>");
            System.out.println("  clipboard-cli view");
            System.out.println("  clipboard-cli delete <id>");
            System.out.println("  clipboard-cli clear" );
            System.out.println("  clipboard-cli restore <id>");
            return;
        }

        String command = args[0];

        switch (command){
            case "save":
                if (args.length > 1) {
                    String clipboardText = args[1];
                    DatabaseManager.saveClipboardEntry(clipboardText);
                    System.out.println("✅ Saved clipboard text: " + clipboardText);
                }else {
                    System.out.println("⚠️ Please provide the text to save.");
                }
                break;


            case "view":
                DatabaseManager.getClipboardHistory();
                break;

            case "delete":
                if (args.length > 1) {
                    int id = Integer.parseInt(args[1]);
                    DatabaseManager.deleteClipboardEntry(id);
                } else {
                    System.out.println("⚠️ Please provide the ID of the entry to delete.");
                }
                break;

            case "clear":
                DatabaseManager.clearClipboardHistory();
                break;

            case "restore":
                if (args.length > 1) {
                    int id = Integer.parseInt(args[1]);
                    restoreClipboardEntry(id);
                } else {
                    System.out.println("⚠️ Please provide the ID of the entry to restore.");
                }
                break;

            default:
                System.out.println("❌ Unknown command: " + command);
                break;
        }
    }
}