package fr.epita.assistants.yakamon.presentation.api.response;

import lombok.Data;

import java.util.UUID;
@Data
public class CatchResponse {
    UUID uuid;
    String nickname;
    Integer energyPoints;
    Integer yakadexId;

    public CatchResponse(UUID uuid, String nickname, Integer energyPoints, Integer id) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.energyPoints = energyPoints;
        this.yakadexId = id;
    }
}
