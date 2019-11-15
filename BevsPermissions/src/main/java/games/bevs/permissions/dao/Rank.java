package games.bevs.permissions.dao;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
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
public class Rank
{
    private String nameId;
    private String displayName;
    private String tag;

    private List<String> permission;

    public Rank(String nameId, String displayName, String tag)
    {
        this(nameId, displayName, tag, Lists.newLinkedList());
    }
}
