package at.bbm.core.server;

import at.bbm.core.objects.players.Player;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class PlayerMapper {

    private final static Map<byte[], Player> players = new HashMap<>();

    public static synchronized Player getPlayer(final InetAddress paramInetAddress) {
        return players.get(paramInetAddress.getAddress());
    }

    public static synchronized void addPlayer(final InetAddress paramInetAddress, final String paramName) {
        final byte[] address = paramInetAddress.getAddress();
        if (null == players.get(address)) {
            players.put(address, new Player());
        }
    }
}
