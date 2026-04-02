package fr.epita.assistants.yakamon.presentation.api.request;

import jakarta.ws.rs.core.Response;

public class StartRequest {
    public String mapPath;
    public final String playerName;

    public StartRequest(String mapPath, String playerName) {
        this.mapPath = mapPath;
        this.playerName = playerName;
    }
}
