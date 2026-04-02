package fr.epita.assistants.yakamon.domain.service;

import fr.epita.assistants.yakamon.data.model.GameModel;
import fr.epita.assistants.yakamon.data.model.ItemModel;
import fr.epita.assistants.yakamon.data.model.PlayerModel;
import fr.epita.assistants.yakamon.data.repository.GameRepository;
import fr.epita.assistants.yakamon.data.repository.PlayerRepository;
import fr.epita.assistants.yakamon.utils.tile.ItemType;
import jakarta.inject.Inject;
import fr.epita.assistants.yakamon.converter.ModelToEntityConverter;
import fr.epita.assistants.yakamon.data.model.YakadexEntryModel;
import fr.epita.assistants.yakamon.data.repository.YakadexEntryRepository;
import fr.epita.assistants.yakamon.domain.entity.YakadexEntity;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class YakadexService {


    @Inject
    YakadexEntryRepository yakadex_repository;

    @Inject
    GameRepository game_repository;


    @Inject
    PlayerRepository player_repository;

    public List<YakadexEntity> getYakadex(Boolean onlyMissing) {

        GameModel map_model = game_repository.findAll().firstResult();
        if (map_model == null) {
            ErrorCode.MOVE400_ERROR.throwException();
        }





        List<YakadexEntryModel> ModelList = yakadex_repository.listAll();

        if (onlyMissing) {
            List<YakadexEntryModel> all = new ArrayList<>();

            for (YakadexEntryModel elm : ModelList) {
                if (! elm.getCaught()) {
                    all.add(elm);
                }
            }
            ModelList = all;
        }


        List<YakadexEntity> responses = new ArrayList<>();

        for (YakadexEntryModel model : ModelList) {
            YakadexEntity response = ModelToEntityConverter.yakadexEntity(model);
            responses.add(response);
        }

        return responses;
    }

    public YakadexEntity getById(Integer id) {

        Optional<YakadexEntryModel> model = yakadex_repository.findAll().stream().filter(elm -> elm.getId() == id).findFirst();
        if (model.isEmpty())
        {
            ErrorCode.MOVE404_ERROR.throwException();
        }
        return ModelToEntityConverter.yakadexEntity(model.get());
    }
}

