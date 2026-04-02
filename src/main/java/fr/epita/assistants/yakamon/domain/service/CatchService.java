package fr.epita.assistants.yakamon.domain.service;


import fr.epita.assistants.yakamon.converter.ModelToEntityConverter;
import fr.epita.assistants.yakamon.data.model.*;
import fr.epita.assistants.yakamon.data.repository.*;
import fr.epita.assistants.yakamon.domain.entity.GameEntity;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import fr.epita.assistants.yakamon.utils.tile.ItemType;
import fr.epita.assistants.yakamon.utils.tile.TileType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class CatchService {


    @Inject
    PlayerRepository player_repository;

    @Inject
    GameRepository game_repository;

    @Inject
    YakadexEntryRepository yakadex_repository;

    @Inject
    YakamonRepository yakamon_repository;

    @Inject
    ItemRepository item_repository;

    @ConfigProperty(name= "JWS_TICK_DURATION") Integer TickDuration;
    @ConfigProperty(name= "JWS_CATCH_DELAY") Integer MovementDelay;

    @Transactional
    public YakamonModel Catch() {

        GameModel map_model = game_repository.findAll().firstResult();
        if(map_model == null){
            ErrorCode.MOVE400_ERROR.throwException();
        }

        var yakamonNumberCheck = yakamon_repository.findAll();
        if (yakamonNumberCheck.stream().count() >= 3)
        {
            ErrorCode.MOVE400_ERROR.throwException();
        }





        GameEntity map = ModelToEntityConverter.convert(map_model);
        List<List<TileType>> map3d = map.getTiles();

        PlayerModel player = player_repository.findAll().firstResult();
        String name = map3d.get(player.getPosY()).get(player.getPosX()).getCollectible().getValue();

        if (player.getLastCatch() != null) {

            Integer torespect = TickDuration * MovementDelay;
            LocalDateTime lmove = player.getLastCatch();
            lmove = lmove.plus(torespect, TimeUnit.MILLISECONDS.toChronoUnit());
            LocalDateTime lactual = LocalDateTime.now();

            if (lmove.isAfter(lactual)) {
                ErrorCode.MOVE429_ERROR.throwException();
            }
        }

        Optional<ItemModel> maybeModel = item_repository.findAll().stream().filter(item -> item.getType().equals(ItemType.YAKABALL)).findFirst();
        if (maybeModel.isEmpty() || maybeModel.get().getQuantity() < 1)
        {
            ErrorCode.MOVE400_ERROR.throwException();
        }

        var item_model = maybeModel.get();
        item_model.setQuantity(item_model.getQuantity() - 1);
        item_repository.persist(item_model);

        Optional<YakadexEntryModel> maybePokedex = yakadex_repository.findAll().stream().filter(item -> item.getName().toLowerCase().equals(name.toLowerCase())).findFirst();
        if(maybePokedex.isEmpty())
        {
            System.out.println("no pokemon");
            ErrorCode.MOVE400_ERROR.throwException();
        }
        YakadexEntryModel pokedex = maybePokedex.get();
        pokedex.setCaught(true);
        yakadex_repository.persist(pokedex);

        PlayerModel player_model = player_repository.findAll().firstResult();
        player_model.setLastCatch(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        player_repository.persist(player_model);



        YakamonModel pokemon = new YakamonModel();
        pokemon.setNickname(pokedex.getName());
        pokemon.setYakadexEntry(pokedex);
        pokemon.setEnergyPoints(0);
        yakamon_repository.persist(pokemon);

        map3d.get(player.getPosY()).get(player.getPosX()).setCollectible(ItemType.NONE);
       StringBuilder map1d = new StringBuilder();

        for (int i = 0; i < map3d.size(); i++) {
            List<TileType> innerligne = map3d.get(i);
            for (TileType tile : innerligne) {

                map1d.append("1");
                map1d.append(tile.getTerrainType().getValue());
                map1d.append(tile.getCollectible().getCollectibleInfo().getValue());
            }
            if (i < map3d.size() - 1) {
                map1d.append(";");}
        }

        map_model.setMap(map1d.toString());
        game_repository.persist(map_model);

        if (map3d.isEmpty())
        {
            ErrorCode.MOVE429_ERROR.throwException();
        }
       return pokemon;
    }
}
