package service;

/**
 * Interface for objects that support authentication.
 * Any class implementing this interface must provide
 * credential checking and user identity methods.
 *
 * Used by: Person (and its subclasses Player, Admin)
 */
public interface Authenticatable {

    /**
     * Check whether the given password matches the user's stored password.
     */
    boolean checkPassword(String password);

    /**
     * Return the username used for login.
     */
    String getUsername();

    /**
     * Return the role name (e.g., "Player" or "Admin").
     */
    String getRole();
}
