package fr.epita.assistants.yakamon.domain.service;

import fr.epita.assistants.yakamon.converter.ModelToEntityConverter;
import fr.epita.assistants.yakamon.data.model.GameModel;
import fr.epita.assistants.yakamon.data.model.ItemModel;
import fr.epita.assistants.yakamon.data.model.PlayerModel;
import fr.epita.assistants.yakamon.data.model.YakadexEntryModel;
import fr.epita.assistants.yakamon.data.repository.GameRepository;
import fr.epita.assistants.yakamon.data.repository.ItemRepository;
import fr.epita.assistants.yakamon.data.repository.PlayerRepository;
import fr.epita.assistants.yakamon.data.repository.YakadexEntryRepository;
import fr.epita.assistants.yakamon.domain.entity.GameEntity;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import fr.epita.assistants.yakamon.utils.tile.ItemType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@ApplicationScoped
public class GameService {

    @Inject
    PlayerRepository player_repository;

    @Inject
    ItemRepository item_repository;

    @Inject
    GameRepository Game_repository;

    @Inject
    YakadexEntryRepository pokedex_repository;

    @Transactional
    public GameEntity StartService(String mapPath, String playerName){


        pokedex_repository.update("caught = false");
        List<YakadexEntryModel> list = pokedex_repository.listAll();
        pokedex_repository.persist(list);

        if(!Files.exists(Paths.get(mapPath)))
        {
            ErrorCode.MOVE400_ERROR.throwException();
        }

        PlayerModel player = new PlayerModel();
        player_repository.deleteAll();

        player.setName(playerName);
        player.setPosX(0);
        player.setPosY(0);
        player.setLastMove(null);
        player.setLastFeed(null);
        player.setLastCollect(null);
        player.setLastCatch(null);
        player_repository.persist(player);

        ItemModel item = new ItemModel();
        item_repository.deleteAll();
        item.setType(ItemType.YAKABALL);
        item.setQuantity(5);
        item_repository.persistAndFlush(item);


        String map_string = "";
        try {
            List<String> lines= Files.readAllLines(Paths.get(mapPath));

            map_string = String.join(";", lines);
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier map", e);
        }

        GameModel mapmodel = new GameModel();
        Game_repository.deleteAll();
        mapmodel.setMap(map_string);
        Game_repository.persist(mapmodel);

        GameEntity myGameEntity = ModelToEntityConverter.convert(mapmodel);

        return myGameEntity;


    }

}
