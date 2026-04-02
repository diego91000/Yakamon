package fr.epita.assistants.yakamon.presentation.api.response;

import lombok.Data;

import java.util.UUID;

@Data
public class TeamResponse {
    UUID uuid;
    String nickname;
    Integer energyPoints;
    Integer yakadexId;

    public TeamResponse(UUID uuid, String nickname, Integer energyPoints, Integer yakadexEntry) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.energyPoints = energyPoints;
        this.yakadexId = yakadexEntry;
    }
}

