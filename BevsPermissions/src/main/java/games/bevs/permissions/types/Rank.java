package games.bevs.permissions.types;

import com.google.common.collect.Lists;
import lombok.*;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

/**
 * This will be cached by the server,
 * since every player will recieve one of the few
 * set ranks.
 *
 *
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(value = "Ranks", noClassnameStored = true)
public class Rank
{
    @Id
    @NonNull
    private String nameId;
    private String displayName;
    private String tag;

    private List<String> permission;

    public Rank(String nameId, String displayName, String tag)
    {
        this(nameId, displayName, tag, Lists.newLinkedList());
    }
}
