package fr.epita.assistants.yakamon.domain.service;

import fr.epita.assistants.yakamon.converter.ModelToEntityConverter;
import fr.epita.assistants.yakamon.data.model.*;
import fr.epita.assistants.yakamon.data.repository.*;
import fr.epita.assistants.yakamon.domain.entity.GameEntity;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import fr.epita.assistants.yakamon.utils.tile.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class CollectService {

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

    @ConfigProperty(name = "JWS_TICK_DURATION")
    Long TickDuration;
    @ConfigProperty(name = "JWS_COLLECT_DELAY")
    Long MovementDelay;
    @ConfigProperty(name = "JWS_COLLECT_MULTIPLIER")
    Integer Multiplier;

    @Transactional
    public TileType Collect() {

        GameModel map_model = game_repository.findAll().firstResult();
        if (map_model == null) {
            System.out.println("nooooooooooo");
            ErrorCode.MOVE400_ERROR.throwException();
        }
        PlayerModel player = player_repository.findAll().firstResult();

        GameEntity map = ModelToEntityConverter.convert(map_model);
        List<List<TileType>> map3d = map.getTiles();

        if (player.getLastCollect() != null) {

                Long torespect = TickDuration * MovementDelay;
                LocalDateTime lmove = player.getLastCollect();
                lmove = lmove.plus(torespect, ChronoUnit.MILLIS);
                LocalDateTime lactual = LocalDateTime.now();

                if (lmove.isAfter(lactual)) {
                    ErrorCode.MOVE429_ERROR.throwException();
                }
        }

        Collectible collectible =
                map3d.get(player.getPosY()).get(player.getPosX()).getCollectible();



        if (collectible.getCollectibleType() == CollectibleType.YAKAMON) {
            System.out.println("yakamon??");
            ErrorCode.MOVE400_ERROR.throwException();
        }

        char lettre = collectible.getCollectibleInfo().getValue();

        if (lettre == 'N') {
            System.out.println("N??");
            ErrorCode.MOVE400_ERROR.throwException();
        }




        Optional<ItemModel> maybeModel = item_repository.findAll().stream().filter(item -> {
            System.out.println(item.getType().toString());
            return item.getType().toString().equals(collectible.getValue());
        }).findFirst();
        ItemModel newinventory;
        System.out.println("collectible = "+collectible.getValue());
        System.out.println(item_repository.findAll().stream().filter(item -> item.getType().toString().equals(collectible.getValue())).findFirst());

        System.out.println();

        if (maybeModel.isEmpty()) {
            newinventory = new ItemModel();
            newinventory.setType((ItemType) collectible);
            newinventory.setQuantity(1 * Multiplier);
        } else {
            newinventory = maybeModel.get();
            newinventory.setQuantity(newinventory.getQuantity() + (1 * Multiplier));
        }
        item_repository.persist(newinventory);

        StringBuilder map1d = new StringBuilder();

        map3d.get(player.getPosY()).get(player.getPosX()).setCollectible(ItemType.NONE);


        for (int i = 0; i < map3d.size(); i++) {

            List<TileType> innerligne = map3d.get(i);

            for (TileType tile : innerligne) {

                map1d.append("1");
                map1d.append(tile.getTerrainType().getValue());
                map1d.append(tile.getCollectible().getCollectibleInfo().getValue());
            }

            if (i < map3d.size() - 1) {
                map1d.append(";");
            }
        }

        map_model.setMap(map1d.toString());
        game_repository.persist(map_model);

        player.setLastCollect(LocalDateTime.now());
        player_repository.persist(player);

        return map3d.get(player.getPosY()).get(player.getPosX());
    }
}