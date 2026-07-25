package be.jeffcheasey88.codetaskfollower.dto;

public record PlayerPermissionDto(
    int id,
    String username,
    String type,
    Boolean read,
    Boolean add,
    Boolean update,
    Boolean delete,
    Boolean admin
) {
    public PlayerPermissionDto(
        String type,
        int id,
        Boolean read,
        Boolean add,
        Boolean update,
        Boolean delete,
        Boolean admin
    ) {
        this(id, null, type, read, add, update, delete, admin);
    }

    public PlayerPermissionDto(
        int id,
        String username,
        Boolean read,
        Boolean add,
        Boolean update,
        Boolean delete,
        Boolean admin
    ) {
        this(id, username, null, read, add, update, delete, admin);
    }
}
