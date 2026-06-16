package service;

/**
 * Interface for objects that can be searched by ID or name.
 * Implementing classes provide entity-specific lookup logic
 * and return formatted results as Strings.
 *
 * Used by: SearchService
 */
public interface Searchable {

    /**
     * Search for an entity by its unique ID.
     * Returns a formatted description string, or null if not found.
     */
    String searchById(String id);

    /**
     * Search for an entity by its name (case-insensitive).
     * Returns a formatted description string, or null if not found.
     */
    String searchByName(String name);
}
