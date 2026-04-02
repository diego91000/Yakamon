package fr.epita.assistants.yakamon.converter;

import fr.epita.assistants.yakamon.data.model.YakadexEntryModel;
import fr.epita.assistants.yakamon.domain.entity.GameEntity;
import fr.epita.assistants.yakamon.domain.entity.MoveEntity;
import fr.epita.assistants.yakamon.domain.entity.PlayerEntity;
import fr.epita.assistants.yakamon.domain.entity.YakadexEntity;
import fr.epita.assistants.yakamon.presentation.api.response.*;
import fr.epita.assistants.yakamon.utils.Item;

import java.util.ArrayList;
import java.util.List;

public class EntityToDTOConverter {

    public static StartResponse convertor(GameEntity entity) {
        return new StartResponse(entity.getTiles());
    }
    public static PlayerResponse player(PlayerEntity entity) {
        return new PlayerResponse(entity.getUuid(),
                entity.getName(),
                entity.getPosX(),
                entity.getPosY(),
                entity.getLastCatch(),
                entity.getLastMove(),
                entity.getLastFeed(),
                entity.getLastCollect());
    };

    public static MoveResponse move(MoveEntity entity) {
        return new MoveResponse(entity.getPosX(), entity.getPosY());
    };



    public static InventoryResponse inventory(List<Item> entity) {
        return new InventoryResponse(entity);
    };


    public static YakadexWrapResponse yakadexList(List<YakadexEntity> entity) {

        List<YakadexResponse> responses = new ArrayList<>();

        for (YakadexEntity elm : entity) {
            YakadexResponse response = EntityToDTOConverter.yakadexx(elm);
            responses.add(response);
        }
       return new YakadexWrapResponse(responses);
    }

    public static YakadexResponse yakadexx(YakadexEntity model) {

    YakadexResponse response = new YakadexResponse();

        response.setName(model.getName());
        response.setId(model.getId());
        response.setCaught(model.getCaught());

        if (model.getCaught())
        {
            response.setDescription(model.getDescription());
            response.setFirstType(model.getFirstType());
            response.setEvolveThreshold(model.getEvolveThreshold());
            if(model.getEvolution() != null)
                response.setEvolutionId(model.getEvolution());
            response.setSecondType(model.getSecondType());
        }

        return response;
    }

}
