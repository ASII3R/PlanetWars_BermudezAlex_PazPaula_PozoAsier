import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/planetwars";
    private static final String USER = "root"; 
    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Inserta un registro en Planet_stats
    public static void insertPlanetStats(
        int planet_id, String name, int resource_metal_amount, int resource_deuterion_amount,
        int technology_defense_level, int technology_attack_level, int battle_counter,
        int missile_launcher_remaining, int ion_cannon_remaining, int plasma_cannon_remaining,
        int light_hunter_remaining, int heavy_hunter_remaining, int battleship_remaining, int armored_ship_remaining
    ) {
        String sql = "INSERT INTO Planet_stats (planet_id, name, resource_metal_amount, resource_deuterion_amount, technology_defense_level, technology_attack_level, battle_counter, missile_launcher_remaining, ion_cannon_remaining, plasma_cannon_remaining, light_hunter_remaining, heavy_hunter_remaining, battleship_remaining, armored_ship_remaining) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, planet_id);
            stmt.setString(2, name);
            stmt.setInt(3, resource_metal_amount);
            stmt.setInt(4, resource_deuterion_amount);
            stmt.setInt(5, technology_defense_level);
            stmt.setInt(6, technology_attack_level);
            stmt.setInt(7, battle_counter);
            stmt.setInt(8, missile_launcher_remaining);
            stmt.setInt(9, ion_cannon_remaining);
            stmt.setInt(10, plasma_cannon_remaining);
            stmt.setInt(11, light_hunter_remaining);
            stmt.setInt(12, heavy_hunter_remaining);
            stmt.setInt(13, battleship_remaining);
            stmt.setInt(14, armored_ship_remaining);
            stmt.executeUpdate();
            System.out.println("Insert en Planet_stats realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert en Battle_stats
    public static void insertBattleStats(int planet_id, int num_battle, int resource_metal_acquired, int resource_deuterion_acquired) {
        String sql = "INSERT INTO Battle_stats (planet_id, num_battle, resource_metal_acquired, resource_deuterion_acquired) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, planet_id);
            stmt.setInt(2, num_battle);
            stmt.setInt(3, resource_metal_acquired);
            stmt.setInt(4, resource_deuterion_acquired);
            stmt.executeUpdate();
            System.out.println("Insert en Battle_stats realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert en Battle_log
    public static void insertBattleLog(int planet_id, int num_battle, int num_line, String log_entry) {
        String sql = "INSERT INTO Battle_log (planet_id, num_battle, num_line, log_entry) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, planet_id);
            stmt.setInt(2, num_battle);
            stmt.setInt(3, num_line);
            stmt.setString(4, log_entry);
            stmt.executeUpdate();
            System.out.println("Insert en Battle_log realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert en Planet_battle_defense
    public static void insertPlanetBattleDefense(int planet_id, int num_battle, int missile_launcher_built, int missile_launcher_destroyed, int ion_cannon_built, int ion_cannon_destroyed, int plasma_cannon_built, int plasma_cannon_destroyed) {
        String sql = "INSERT INTO Planet_battle_defense (planet_id, num_battle, missile_launcher_built, missile_launcher_destroyed, ion_cannon_built, ion_cannon_destroyed, plasma_cannon_built, plasma_cannon_destroyed) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, planet_id);
            stmt.setInt(2, num_battle);
            stmt.setInt(3, missile_launcher_built);
            stmt.setInt(4, missile_launcher_destroyed);
            stmt.setInt(5, ion_cannon_built);
            stmt.setInt(6, ion_cannon_destroyed);
            stmt.setInt(7, plasma_cannon_built);
            stmt.setInt(8, plasma_cannon_destroyed);
            stmt.executeUpdate();
            System.out.println("Insert en Planet_battle_defense realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert en Planet_battle_army
    public static void insertPlanetBattleArmy(int planet_id, int num_battle, int light_hunter_destroyed, int heavy_hunter_destroyed, int battle_ship_destroyed, int armored_ship_destroyed) {
        String sql = "INSERT INTO Planet_battle_army (planet_id, num_battle, light_hunter_destroyed, heavy_hunter_destroyed, battleship_destroyed, armored_ship_destroyed) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, planet_id);
            stmt.setInt(2, num_battle);
            stmt.setInt(3, light_hunter_destroyed);
            stmt.setInt(4, heavy_hunter_destroyed);
            stmt.setInt(5, battle_ship_destroyed);
            stmt.setInt(6, armored_ship_destroyed);
            stmt.executeUpdate();
            System.out.println("Insert en Planet_battle_army realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Insert en Enemy_army
    public static void insertEnemyArmy(int planet_id, int num_battle, int light_hunter_destroyed, int heavy_hunter_destroyed, int battle_ship_destroyed, int armored_ship_destroyed) {
        String sql = "INSERT INTO Enemy_army (planet_id, num_battle, light_hunter_destroyed, heavy_hunter_destroyed, battleship_destroyed, armored_ship_destroyed) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, planet_id);
            stmt.setInt(2, num_battle);
            stmt.setInt(3, light_hunter_destroyed);
            stmt.setInt(4, heavy_hunter_destroyed);
            stmt.setInt(5, battle_ship_destroyed);
            stmt.setInt(6, armored_ship_destroyed);
            stmt.executeUpdate();
            System.out.println("Insert en Enemy_army realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
    //UPDATES 
    // Update Planet_stats
    public static void updatePlanetStats(
        int planet_id, String name, int resource_metal_amount, int resource_deuterion_amount,
        int technology_defense_level, int technology_attack_level, int battle_counter,
        int missile_launcher_remaining, int ion_cannon_remaining, int plasma_cannon_remaining,
        int light_hunter_remaining, int heavy_hunter_remaining, int battleship_remaining, int armored_ship_remaining
    ) {
        String sql = "UPDATE Planet_stats SET name=?, resource_metal_amount=?, resource_deuterion_amount=?, technology_defense_level=?, technology_attack_level=?, battle_counter=?, missile_launcher_remaining=?, ion_cannon_remaining=?, plasma_cannon_remaining=?, light_hunter_remaining=?, heavy_hunter_remaining=?, battleship_remaining=?, armored_ship_remaining=? WHERE planet_id=?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, resource_metal_amount);
            stmt.setInt(3, resource_deuterion_amount);
            stmt.setInt(4, technology_defense_level);
            stmt.setInt(5, technology_attack_level);
            stmt.setInt(6, battle_counter);
            stmt.setInt(7, missile_launcher_remaining);
            stmt.setInt(8, ion_cannon_remaining);
            stmt.setInt(9, plasma_cannon_remaining);
            stmt.setInt(10, light_hunter_remaining);
            stmt.setInt(11, heavy_hunter_remaining);
            stmt.setInt(12, battleship_remaining);
            stmt.setInt(13, armored_ship_remaining);
            stmt.setInt(14, planet_id);
            stmt.executeUpdate();
            System.out.println("Update en Planet_stats realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Battle_stats
    public static void updateBattleStats(int planet_id, int num_battle, int resource_metal_acquired, int resource_deuterion_acquired) {
        String sql = "UPDATE Battle_stats SET resource_metal_acquired=?, resource_deuterion_acquired=? WHERE planet_id=? AND num_battle=?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, resource_metal_acquired);
            stmt.setInt(2, resource_deuterion_acquired);
            stmt.setInt(3, planet_id);
            stmt.setInt(4, num_battle);
            stmt.executeUpdate();
            System.out.println("Update en Battle_stats realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Battle_log
    public static void updateBattleLog(int planet_id, int num_battle, int num_line, String log_entry) {
        String sql = "UPDATE Battle_log SET log_entry=? WHERE planet_id=? AND num_battle=? AND num_line=?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, log_entry);
            stmt.setInt(2, planet_id);
            stmt.setInt(3, num_battle);
            stmt.setInt(4, num_line);
            stmt.executeUpdate();
            System.out.println("Update en Battle_log realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Planet_battle_defense
    public static void updatePlanetBattleDefense(int planet_id, int num_battle, int missile_launcher_built, int missile_launcher_destroyed, int ion_cannon_built, int ion_cannon_destroyed, int plasma_cannon_built, int plasma_cannon_destroyed) {
        String sql = "UPDATE Planet_battle_defense SET missile_launcher_built=?, missile_launcher_destroyed=?, ion_cannon_built=?, ion_cannon_destroyed=?, plasma_cannon_built=?, plasma_cannon_destroyed=? WHERE planet_id=? AND num_battle=?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, missile_launcher_built);
            stmt.setInt(2, missile_launcher_destroyed);
            stmt.setInt(3, ion_cannon_built);
            stmt.setInt(4, ion_cannon_destroyed);
            stmt.setInt(5, plasma_cannon_built);
            stmt.setInt(6, plasma_cannon_destroyed);
            stmt.setInt(7, planet_id);
            stmt.setInt(8, num_battle);
            stmt.executeUpdate();
            System.out.println("Update en Planet_battle_defense realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Planet_battle_army
    public static void updatePlanetBattleArmy(int planet_id, int num_battle, int light_hunter_destroyed, int heavy_hunter_destroyed, int battle_ship_destroyed, int armored_ship_destroyed) {
        String sql = "UPDATE Planet_battle_army SET light_hunter_destroyed=?, heavy_hunter_destroyed=?, battleship_destroyed=?, armored_ship_destroyed=? WHERE planet_id=? AND num_battle=?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, light_hunter_destroyed);
            stmt.setInt(2, heavy_hunter_destroyed);
            stmt.setInt(3, battle_ship_destroyed);
            stmt.setInt(4, armored_ship_destroyed);
            stmt.setInt(5, planet_id);
            stmt.setInt(6, num_battle);
            stmt.executeUpdate();
            System.out.println("Update en Planet_battle_army realizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Enemy_army
    public static void updateEnemyArmy(int planet_id, int num_battle, int light_hunter_destroyed, int heavy_hunter_destroyed, int battle_ship_destroyed, int armored_ship_destroyed) {
            String sql = "UPDATE Enemy_army SET light_hunter_destroyed=?, heavy_hunter_destroyed=?, battleship_destroyed=?, armored_ship_destroyed=? WHERE planet_id=? AND num_battle=?";
            try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, light_hunter_destroyed);
                stmt.setInt(2, heavy_hunter_destroyed);
                stmt.setInt(3, battle_ship_destroyed);
                stmt.setInt(4, armored_ship_destroyed);
                stmt.setInt(5, planet_id);
                stmt.setInt(6, num_battle);
                stmt.executeUpdate();
                System.out.println("Update en Enemy_army realizado correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }