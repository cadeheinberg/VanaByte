//import org.bukkit.entity.Player;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;//SET THE DEFAULT VALUES FOR EACH STAT HERE
//public void addScore(Player player) {
//    String insertStatement = "INSERT INTO " + tableName + "(";
//    for (int i = 0; i < column.length; i++) {
//        if (i == column.length - 1) {
//            insertStatement = insertStatement + column[i] + ")";
//        } else {
//            insertStatement = insertStatement + column[i] + ",";
//        }
//    }
//    insertStatement = insertStatement + " VALUES (";
//    for (int i = 0; i < column.length; i++) {
//        if (i == column.length - 1) {
//            insertStatement = insertStatement + "?)";
//        } else {
//            insertStatement = insertStatement + "?,";
//        }
//    }
//    PreparedStatement statement;
//    try {
//        statement = connection.prepareStatement(insertStatement);
//        statement.setString(1, player.getUniqueId().toString());
//        statement.setString(2, player.getName());
//        statement.setInt(3, 500);
//        statement.setInt(4, 1);
//        statement.setInt(5, 0);
//        statement.setInt(6, 0);
//        statement.setInt(7, 0);
//        statement.setInt(8, 0);
//        statement.setInt(9, 0);
//        //KITS
//        statement.setInt(10, 1);
//        statement.setInt(11, 1);
//        statement.setInt(12, 0);
//        statement.setInt(13, 0);
//        statement.setInt(14, 0);
//        statement.setInt(15, 0);
//        statement.setInt(16, 0);
//        statement.executeUpdate();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}
//
//
//public void deletePlayerFromTable(Player player) {
//    PreparedStatement statement;
//    try {
//        statement = connection
//                .prepareStatement("DELETE FROM " + tableName + " WHERE " + column[0] + " = ?");
//        statement.setString(1, player.getUniqueId().toString());
//        statement.execute();
//        player.sendMessage("You have been cleared from kitpvp stats");
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}
//public int getStat(Player player, String stat) {
//    int getter = -1;
//    PreparedStatement statement;
//    try {
//        statement = connection
//                .prepareStatement("SELECT " + stat + " FROM " + tableName + " WHERE " + column[0] + " = ?");
//        statement.setString(1, player.getUniqueId().toString());
//        ResultSet result = statement.executeQuery();
//        while (result.next()) {
//            getter = result.getInt(stat);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return getter;
//}
//
//public void setStat(String uuid, String stat, int setter) {
//    PreparedStatement statement;
//    try {
//        statement = connection.prepareStatement(
//                "UPDATE " + tableName + " SET " + stat + " = ? WHERE " + column[0] + " = ?");
//        statement.setString(2, uuid);
//        statement.setInt(1, setter);
//        statement.executeUpdate();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}
//
//public void updateName(Player player, String stat, String setter) {
//    PreparedStatement statement;
//    try {
//        statement = connection.prepareStatement(
//                "UPDATE " + tableName + " SET " + stat + " = ? WHERE " + column[0] + " = ?");
//        statement.setString(2, player.getUniqueId().toString());
//        statement.setString(1, setter);
//        statement.executeUpdate();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}
//
//public void incOfflineStat(String name, String stat, int setter) {
//    // GET THE AMOUNT OF STAT
//    int getter = 0;
//    {
//        PreparedStatement statement;
//        try {
//            statement = connection.prepareStatement(
//                    "SELECT " + stat + " FROM " + tableName + " WHERE " + column[1] + " = ?");
//            statement.setString(1, name);
//            ResultSet result = statement.executeQuery();
//            while (result.next()) {
//                getter = result.getInt(stat);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // INCREASE THE AMOUNT OF STAT
//    {
//        PreparedStatement statement;
//        try {
//            statement = connection.prepareStatement(
//                    "UPDATE " + tableName + " SET " + stat + " = ? WHERE " + column[1] + " = ?");
//            statement.setString(2, name);
//            statement.setInt(1, setter + getter);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//public boolean playerExists(Player player) {
//    PreparedStatement statement;
//    try {
//        statement = connection.prepareStatement(
//                "SELECT " + column[2] + " FROM " + tableName + " WHERE " + column[0] + " = ?");
//        statement.setString(1, player.getUniqueId().toString());
//        ResultSet results = statement.executeQuery();
//        if (results.next()) {
//            return true;
//        }
//        return false;
//    } catch (SQLException e) {
//        e.printStackTrace();
//        return false;
//    }
//}