package sakuratech.chat.network;

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;

public class ChannelManager {
    private final Map<String, SelectionKey> channelToKeyMap = new HashMap<>();

    public boolean addChannel(String channelId, SelectionKey key) {
        channelToKeyMap.put(channelId, key);
        return false;
    }

    public SelectionKey getKeyForChannel(String channelId) {
        return channelToKeyMap.get(channelId);
    }

    public void removeChannel(String channelId) {
        channelToKeyMap.remove(channelId);
    }
}
