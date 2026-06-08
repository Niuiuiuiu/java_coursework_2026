package model;

/**
 * Represents a system administrator. Extends Person.
 * Admin users have full data-management permissions.
 */
public class Admin extends Person {
    private static final long serialVersionUID = 1L;

    private String role;  // e.g., "SuperAdmin", "Moderator"

    public Admin() {
        super();
    }

    public Admin(String id, String name, String username, String password, String role) {
        super(id, name, username, password);
        this.role = role;
    }

    public String getAdminRole() {
        return role;
    }

    public void setAdminRole(String role) {
        this.role = role;
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public String toString() {
        return String.format("Admin[%s] %s (%s)", getId(), getName(), role);
    }
}
