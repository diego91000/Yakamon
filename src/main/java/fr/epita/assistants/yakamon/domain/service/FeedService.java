package fr.epita.assistants.yakamon.domain.service;

import fr.epita.assistants.yakamon.data.model.GameModel;
import fr.epita.assistants.yakamon.data.model.ItemModel;
import fr.epita.assistants.yakamon.data.model.PlayerModel;
import fr.epita.assistants.yakamon.data.model.YakamonModel;
import fr.epita.assistants.yakamon.data.repository.*;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import fr.epita.assistants.yakamon.utils.tile.ItemType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class FeedService {
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
    @ConfigProperty(name = "JWS_FEED_DELAY")
    Long MovementDelay;

    @Transactional
    public YakamonModel Feed(UUID uid, Integer number) {

        GameModel map_model = game_repository.findAll().firstResult();
        if (map_model == null) {
            System.out.println("nooooooooooo");
            ErrorCode.MOVE400_ERROR.throwException();
        }

        PlayerModel player = player_repository.findAll().firstResult();
        if (player.getLastFeed() != null) {

            Long torespect = TickDuration * MovementDelay;
            LocalDateTime lfeed = player.getLastFeed();
            lfeed = lfeed.plus(torespect, ChronoUnit.MILLIS);
            LocalDateTime lactual = LocalDateTime.now();

            if (lfeed.isAfter(lactual)) {
                ErrorCode.MOVE429_ERROR.throwException();
            }
        }


        Optional<YakamonModel> maybeYakamon = yakamon_repository.findAll().stream().filter(elm -> elm.getUuid().equals(uid)).findFirst();
        if(maybeYakamon.isEmpty())
        {
            System.out.println("coucou");
            ErrorCode.MOVE404_ERROR.throwException();
        }

        Optional<ItemModel> MaybeInventory = item_repository.findAll().stream().filter(item -> item.getType().equals(ItemType.SCROOGE)).findFirst();
        if(MaybeInventory.isEmpty() || MaybeInventory.get().getQuantity() - number < 0)
        {
            System.out.println("no item");
            ErrorCode.MOVE400_ERROR.throwException();
        }
        player.setLastFeed(LocalDateTime.now());
        player_repository.persist(player);

        ItemModel inventory = MaybeInventory.get();
        inventory.setQuantity(inventory.getQuantity() - number);
        item_repository.persist(inventory);


        YakamonModel pokemon = maybeYakamon.get();
        pokemon.setEnergyPoints(pokemon.getEnergyPoints() + number);
        yakamon_repository.persist(pokemon);

        return pokemon;

    }

}

