package fr.epita.assistants.yakamon.presentation.api.response;

import fr.epita.assistants.yakamon.utils.tile.ItemType;
import fr.epita.assistants.yakamon.utils.tile.TileType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
public class CollectResponse {

    public CollectResponse(TileType tileType) {
        this.tileType = tileType;
    }
    TileType tileType;
}
