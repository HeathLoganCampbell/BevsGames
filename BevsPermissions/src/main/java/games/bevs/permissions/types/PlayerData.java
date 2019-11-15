package games.bevs.permissions.types;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.mongodb.morphia.annotations.Indexed;


import java.util.List;
import java.util.UUID;

@Data
@ToString
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity(value = "PlayerData", noClassnameStored = true)
public class PlayerData
{
    @Id
    @NonNull
    private UUID uniqueId;

    @Indexed
    private String username;

    private String rank;

    @Embedded
    private List<PlayerRank> ranks;
}
