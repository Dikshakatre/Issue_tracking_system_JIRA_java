package data;

import java.util.UUID;

public abstract class Entity {
    private UUID id;
    String name;

    Entity(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    Entity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
