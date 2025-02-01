package API.utils;

import lombok.Getter;

@Getter
public enum Endpoints {
    // Define constants with endpoint URLs
    CREATE_USERS("/users"),
    GET_USER("/users/"),
    UPDATE_USER("/users/");

    private final String value;

    Endpoints(String value) {
        this.value = value;
    }

    // Helper method to append an ID only for GET_USER and UPDATE_USER
    public String withId(int id) {
        if (this == GET_USER || this == UPDATE_USER) {
            return this.value + id; // Append the ID to the endpoint
        }
        throw new UnsupportedOperationException(this.name() + " does not support appending an ID.");
    }
}
