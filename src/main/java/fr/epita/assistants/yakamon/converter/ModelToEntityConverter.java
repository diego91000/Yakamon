package fr.epita.assistants.yakamon.converter;

import fr.epita.assistants.yakamon.data.model.GameModel;
import fr.epita.assistants.yakamon.data.model.PlayerModel;
import fr.epita.assistants.yakamon.data.model.YakadexEntryModel;
import fr.epita.assistants.yakamon.domain.entity.GameEntity;
import fr.epita.assistants.yakamon.domain.entity.MoveEntity;
import fr.epita.assistants.yakamon.domain.entity.PlayerEntity;
import fr.epita.assistants.yakamon.domain.entity.YakadexEntity;
import fr.epita.assistants.yakamon.presentation.api.response.MoveResponse;
import fr.epita.assistants.yakamon.utils.tile.*;

import java.util.ArrayList;
import java.util.List;

public class ModelToEntityConverter {

        public static GameEntity convert(GameModel model) {
            String[] string = model.getMap().toString().split(";");
            List<List<TileType>> doublelist = new ArrayList<>();

            for (String row : string) {
                List<TileType> innerlist = new ArrayList<>();

                for (int i = 0; i < row.length(); i += 3) {

                    TerrainType terrainName = TerrainType.getTerrain(row.charAt(i+1));

                    Collectible collectible;
                    collectible  = CollectibleUtils.getCollectible(row.charAt(i+2));

                    for (int j = 0; j < row.charAt(i) - '0'; j++) {
                        innerlist.add(new TileType(terrainName, collectible));
                    }
                }
                doublelist.add(innerlist);
            }
            return new GameEntity(doublelist);
        }


    public static PlayerEntity player(PlayerModel model) {
            return new PlayerEntity(model.getUuid(),
            model.getName(),
                    model.getPosX(),
                    model.getPosY(),
            model.getLastCatch(),
        model.getLastMove(),
        model.getLastFeed(),
        model.getLastCollect());
    }

    public static MoveEntity move(PlayerModel entity) {
        return new MoveEntity(entity.getPosX(), entity.getPosY());
    };




    public static YakadexEntity yakadexEntity(YakadexEntryModel elm) {

        YakadexEntity response = new YakadexEntity();

        response.setId(elm.getId());
        response.setCaught(elm.getCaught());
        response.setName(elm.getName());

        if(elm.getCaught())
        {
            if (elm.getEvolution() != null)
                response.setEvolution(elm.getEvolution().getId());
            response.setFirstType(elm.getFirstType());
            response.setEvolveThreshold(elm.getEvolveThreshold());
            response.setDescription(elm.getDescription());
            response.setSecondType(elm.getSecondType());
        }
        return response;
    }

}
