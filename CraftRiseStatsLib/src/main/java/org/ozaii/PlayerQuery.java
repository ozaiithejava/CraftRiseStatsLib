package org.ozaii;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Map;

public class PlayerQuery implements PlayerQueryService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private final PlayerDataFetcher dataFetcher = new PlayerDataFetcher();

    @Override
    public Future<Map<String, String>> getPlayerData(String username) {
        return executorService.submit(() -> dataFetcher.fetchPlayerData(username));
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
