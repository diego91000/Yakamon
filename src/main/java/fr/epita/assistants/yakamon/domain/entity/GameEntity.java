package fr.epita.assistants.yakamon.domain.entity;

import fr.epita.assistants.yakamon.utils.tile.TileType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameEntity {

    public GameEntity(List<List<TileType>> tiles) {
        this.tiles = tiles;
    }

    List<List<TileType>> tiles;

}
