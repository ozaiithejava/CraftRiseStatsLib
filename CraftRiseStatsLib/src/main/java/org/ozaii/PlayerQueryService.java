package org.ozaii;


import java.util.Map;

public interface PlayerQueryService {

    /**
     * Belirtilen oyuncu ismiyle ilgili bilgileri sorgular.
     *
     * @param username Oyuncu ismi.
     * @return Oyuncuya ait bilgileri içeren bir Map objesi.
     */
    Map<String, String> sorgula(String username);
}
