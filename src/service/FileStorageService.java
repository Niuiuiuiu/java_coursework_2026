package service;

import java.io.*;

/**
 * Handles saving and loading system data to/from files.
 * Uses Java serialization because all model classes and
 * GameDataManager already implement Serializable.
 *
 * File format: binary (.dat), stored in the project root
 * by default as "gamedata.dat".
 */
public class FileStorageService {

    private String filePath;

    public FileStorageService(String filePath) {
        this.filePath = filePath;
    }

    public FileStorageService() {
        this("gamedata.dat");
    }

    /**
     * Save the entire GameDataManager to a file.
     * Returns true if successful, false if an I/O error occurred.
     */
    public boolean save(GameDataManager data) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            out.writeObject(data);
            System.out.println("✅ Data saved to " + filePath);
            return true;
        } catch (IOException e) {
            System.out.println("❌ Failed to save data: " + e.getMessage());
            return false;
        }
    }

    /**
     * Load the GameDataManager from a file.
     * Returns the loaded data, or null if the file doesn't exist
     * or an I/O error occurred.
     */
    public GameDataManager load() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("ℹ No saved data found. Using initial dataset.");
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(filePath))) {
            GameDataManager data = (GameDataManager) in.readObject();
            System.out.println("✅ Data loaded from " + filePath);
            return data;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Failed to load data: " + e.getMessage());
            return null;
        }
    }

    /**
     * Check whether a saved data file exists.
     */
    public boolean hasSavedData() {
        return new File(filePath).exists();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
