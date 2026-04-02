package fr.epita.assistants.yakamon.presentation.api.response;

import fr.epita.assistants.yakamon.data.model.YakadexEntryModel;
import fr.epita.assistants.yakamon.utils.tile.TileType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
public class FeedResponse {
    UUID uuid;
    String nickname;
    Integer energyPoints;
    Integer yakadexId;

    public FeedResponse(UUID uuid, String nickname, Integer energyPoints, Integer yakadexId) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.energyPoints = energyPoints;
        this.yakadexId = yakadexId;
    }
}


