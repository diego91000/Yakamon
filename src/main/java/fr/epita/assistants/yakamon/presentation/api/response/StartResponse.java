package fr.epita.assistants.yakamon.presentation.api.response;

import fr.epita.assistants.yakamon.utils.tile.TileType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StartResponse {

        public StartResponse(List<List<TileType>> tiles) {
            this.tiles = tiles;
        }

        List<List<TileType>> tiles;

    }
