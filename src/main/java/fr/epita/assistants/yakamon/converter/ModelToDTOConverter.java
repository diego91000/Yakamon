package fr.epita.assistants.yakamon.converter;

import fr.epita.assistants.yakamon.data.model.GameModel;
import fr.epita.assistants.yakamon.data.model.ItemModel;
import fr.epita.assistants.yakamon.data.model.YakadexEntryModel;
import fr.epita.assistants.yakamon.data.model.YakamonModel;
import fr.epita.assistants.yakamon.domain.entity.GameEntity;
import fr.epita.assistants.yakamon.domain.entity.PlayerEntity;
import fr.epita.assistants.yakamon.presentation.api.response.*;
import fr.epita.assistants.yakamon.utils.tile.TileType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModelToDTOConverter {

    public static CatchResponse catchh(YakamonModel pokemon) { return new CatchResponse(pokemon.getUuid(),
            pokemon.getNickname(),
            pokemon.getEnergyPoints(),
            pokemon.getYakadexEntry().getId());
    };

    public static CollectResponse collect(TileType tile) {
        return new CollectResponse(tile);

    }

    public static FeedResponse feed(YakamonModel pokemon) {
        return new FeedResponse(pokemon.getUuid(),
                pokemon.getNickname(),
                pokemon.getEnergyPoints(),
                pokemon.getYakadexEntry().getId());

    }
    public static TeamWrapResponse team(List<YakamonModel> pokemons) {

        List<TeamResponse> response = new ArrayList<>();
        pokemons.forEach(elm -> response.add(new TeamResponse( elm.getUuid(), elm.getNickname(), elm.getEnergyPoints(), elm.getYakadexEntry().getId())));
        return new TeamWrapResponse(response);
    }
}
