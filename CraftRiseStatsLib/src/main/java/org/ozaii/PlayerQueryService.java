package org.ozaii;

import java.util.Map;
import java.util.concurrent.Future;

public interface PlayerQueryService {
    Future<Map<String, String>> getPlayerData(String username);
}
