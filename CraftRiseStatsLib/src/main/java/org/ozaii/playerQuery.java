package org.ozaii;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class playerQuery implements PlayerQueryService {

    @Override
    public Map<String, String> sorgula(String username) {
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
            StringBuilder fre = new StringBuilder();
            for (Element element : socialMediaElements) {
                fre.append(element.attr("title")).append(" ");
            }

            // Rank durumlarını toplama
            Elements rankElements = doc.select("div.rankButton");
            StringBuilder fre2 = new StringBuilder();
            for (Element element : rankElements) {
                fre2.append(element.attr("title")).append(" ");
            }

            // Oyun istatistiklerini toplama
            Elements gameElements = doc.select("div.riseStats > p");
            StringBuilder fre3 = new StringBuilder();
            for (Element element : gameElements) {
                fre3.append(element.text()).append(" ");
            }

            String[] argsStatus = fre.toString().trim().split("\\s+");
            String[] TP = fre2.toString().replaceAll("<b>", "").replaceAll("</br>", "")
                    .replaceAll("</b>", "").replaceAll("<br>", "").trim().split("\\s+");
            String[] games = fre3.toString().replaceAll("win", "win ").trim().split("\\s+");

            playerData.put("username", nick);
            playerData.put("status", argsStatus[argsStatus.length - 1]);
            playerData.put("tag", argsRank.length > 0 ? argsRank[0] : "");
            playerData.put("rank", argsRank.length > 1 ? argsRank[1] + " " + argsRank[2] : "");
            playerData.put("totalxp", TP.length > 6 ? TP[6] : "");
            playerData.put("nextlevelxp", TP.length > 9 ? TP[9] : "");
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
