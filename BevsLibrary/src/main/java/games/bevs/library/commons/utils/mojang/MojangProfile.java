package games.bevs.library.commons.utils.mojang;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MojangProfile {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String uuid;

    @SerializedName("properties")
    private List<Property> properties;

    public MojangProfile(String name, String uuid) {
        this(name, uuid, null);
    }

    public MojangProfile(String name, String uuid, List<Property> properties) {
        this.name = name;
        this.uuid = uuid;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return MojangUtil.convertUuid(uuid);
    }

    public List<Property> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public Property getSkinData() {
        return getProperty("textures");
    }

    public Property getProperty(String name) {
        return properties.stream().filter(property -> property.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public boolean isPartial() {
        return properties == null;
    }

    @Override
    public String toString() {
        return "MojangProfile{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", properties=" + properties +
                '}';
    }
}
