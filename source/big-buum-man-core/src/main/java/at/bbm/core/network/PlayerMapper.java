package at.bbm.core.network;

import at.bbm.core.GlobalProperties;
import at.bbm.core.objects.players.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMapper {

    public static final Logger LOGGER = LogManager.getLogger(PlayerMapper.class.getName());
    private static final Map<String, Player> playersByUUID = new HashMap<>();

    public static synchronized Player getPlayer(final String paramUUID) {
        return playersByUUID.get(paramUUID);
    }

    public static synchronized String addPlayer(final String paramID, final String paramName) {
        if (!GlobalProperties.UNREGISTERED_UUID.equals(paramID) && null != playersByUUID.get(paramID) && null != paramID) {
            final Player player = playersByUUID.get(paramID);
            LOGGER.info("Controller for player \"{}\" already connected with id {}", player.getName(), paramID);
            return paramID;
        }
        String uuid = UUID.randomUUID().toString();
        while (null != playersByUUID.get(uuid)) {
            uuid = UUID.randomUUID().toString();
        }
        playersByUUID.put(uuid, new Player(paramName));
        return uuid;
    }

    public static void removePlayer(final String paramUUID) {
        playersByUUID.remove(paramUUID);
    }
}
