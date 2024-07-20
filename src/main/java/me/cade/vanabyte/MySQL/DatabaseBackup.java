package me.cade.vanabyte.MySQL;

import me.cade.vanabyte.MySQL.HiddenPersonal.DatabaseAccess;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseBackup {

    protected static void backupDatabase() {
        String host = DatabaseAccess.getHost();
        String port = "" + DatabaseAccess.getPort();
        String database = DatabaseAccess.getDatabase();
        String user = DatabaseAccess.getUsername();
        String password = DatabaseAccess.getPassword();

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "START_BACKUP_" + timestamp + ".sql";
        String backupPath = "/home/sevenkits/database_backups/startup_backups/" + fileName;

        // Construct the mysqldump command
        String command = String.format("mysqldump -h%s -P%s -u%s -p%s %s -r %s",
                host, port, user, password, database, backupPath);

        VanaByte.sendConsoleMessageGood("DatabaseBackup", "backup starting");

        try {
            // Execute the command
            Process process = Runtime.getRuntime().exec(command);

            // Capture and log any errors
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    VanaByte.sendConsoleMessageWarning("DatabaseBackup", "warning/error: " + line);
                    Bukkit.getConsoleSender().sendMessage("[MyPlugin] " + line);
                }
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                VanaByte.sendConsoleMessageGood("DatabaseBackup", "backup successful");
            } else {
                VanaByte.sendConsoleMessageBad("DatabaseBackup", "backup failed 1, exit code: " + exitCode);
            }
        } catch (Exception e) {
            VanaByte.sendConsoleMessageBad("DatabaseBackup", "backup failed 2, exit code: " + e.getMessage());
        }
    }

}
