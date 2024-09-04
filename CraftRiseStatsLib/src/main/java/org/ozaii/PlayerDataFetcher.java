package org.ozaii;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerDataFetcher {

    public Map<String, String> fetchPlayerData(String username) {
        Map<String, String> playerData = new HashMap<>();
        try {
            String url = "https://craftrise.com.tr/oyuncu/" + username;
            Document doc = Jsoup.connect(url).get();

            String nick = doc.select("div.profileTitle-generalInformation > p").text().trim();
            Element rankElement = doc.selectFirst("div.rankButton > p");

            // Rank bilgilerini ayıklama
            String[] argsRank = rankElement != null ? rankElement.text().split("\\s+") : new String[]{"", ""};

            // Sosyal medya durumlarını toplama
            Elements socialMediaElements = doc.select("div.rankButton.socialMedia");
            StringBuilder socialMediaBuilder = new StringBuilder();
            for (Element element : socialMediaElements) {
                socialMediaBuilder.append(element.attr("title")).append(" ");
            }

            // Rank durumlarını toplama
            Elements rankElements = doc.select("div.rankButton");
            StringBuilder rankBuilder = new StringBuilder();
            for (Element element : rankElements) {
                rankBuilder.append(element.attr("title")).append(" ");
            }

            // Oyun istatistiklerini toplama
            Elements gameElements = doc.select("div.riseStats > p");
            StringBuilder gameStatsBuilder = new StringBuilder();
            for (Element element : gameElements) {
                gameStatsBuilder.append(element.text()).append(" ");
            }

            String[] argsStatus = socialMediaBuilder.toString().trim().split("\\s+");
            String[] rankData = rankBuilder.toString().replaceAll("<b>", "")
                    .replaceAll("</br>", "")
                    .replaceAll("</b>", "")
                    .replaceAll("<br>", "").trim().split("\\s+");
            String[] games = gameStatsBuilder.toString().replaceAll("win", "win ").trim().split("\\s+");

            playerData.put("username", nick);
            playerData.put("status", argsStatus.length > 0 ? argsStatus[argsStatus.length - 1] : "");
            playerData.put("tag", rankData.length > 0 ? rankData[0] : "");
            playerData.put("rank", rankData.length > 2 ? rankData[1] + " " + rankData[2] : "");
            playerData.put("totalxp", rankData.length > 6 ? rankData[6] : "");
            playerData.put("nextlevelxp", rankData.length > 9 ? rankData[9] : "");
            playerData.put("head", "https://www.craftrise.com.tr/gets/get-head.php?s=256&u=" + username);

            String[] gameKeys = {
                    "herobrine_chamber_point", "herobrinechamber_win",
                    "speedbuilders_point", "speedbuilders_win",
                    "survivalgames_point", "survivalgames_win",
                    "uhcmeetup_point", "uhcmeetup_win",
                    "buildbattle_point", "buildbattle_win",
                    "thebridge_point", "thebridge_win",
                    "bedwars_point", "bedwars_win",
                    "dragonescape_point", "dragonescape_win",
                    "tntrun_point", "tntrun_win",
                    "eggwars_point", "eggwars_win",
                    "turfwars_point", "turfwars_win",
                    "arenapvp_point", "arenapvp_win",
                    "oitc_point", "oitc_win",
                    "bomblobbers_point", "bomblobbers_win",
                    "katilkim_point", "katilkim_win",
                    "skywars_point", "skywars_win"
            };

            for (int i = 0; i < gameKeys.length && i < games.length; i++) {
                playerData.put(gameKeys[i], games[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerData;
    }
}
