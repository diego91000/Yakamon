package fr.epita.assistants.yakamon.domain.service;

import com.ethlo.time.DateTime;
import fr.epita.assistants.yakamon.converter.ModelToEntityConverter;
import fr.epita.assistants.yakamon.data.model.GameModel;
import fr.epita.assistants.yakamon.data.model.PlayerModel;
import fr.epita.assistants.yakamon.data.repository.GameRepository;
import fr.epita.assistants.yakamon.data.repository.PlayerRepository;
import fr.epita.assistants.yakamon.domain.entity.GameEntity;
import fr.epita.assistants.yakamon.domain.entity.MoveEntity;
import fr.epita.assistants.yakamon.domain.entity.PlayerEntity;
import fr.epita.assistants.yakamon.utils.Direction;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import fr.epita.assistants.yakamon.utils.tile.TerrainType;
import fr.epita.assistants.yakamon.utils.tile.TileType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.server.runtime.kotlin.ApplicationCoroutineScope;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class MoveService {

    @Inject
    PlayerRepository player_repository;

    @Inject
    GameRepository game_repository;

    @ConfigProperty(name = "JWS_TICK_DURATION")
    Long TickDuration;
    @ConfigProperty(name = "JWS_MOVEMENT_DELAY")
    Long MovementDelay;
    @Inject
    ApplicationCoroutineScope applicationCoroutineScope;

    @Transactional
    public MoveEntity Move(Direction element) {

        GameModel map_model = game_repository.findAll().firstResult();
        if (map_model == null) {
            ErrorCode.MOVE400_ERROR.throwException();
        }
        GameEntity map = ModelToEntityConverter.convert(map_model);

        PlayerModel player_model = player_repository.findAll().firstResult();
        PlayerEntity player = ModelToEntityConverter.player(player_model);

        List<List<TileType>> map3d = map.getTiles();
        if (map3d.isEmpty()) {
            ErrorCode.MOVE429_ERROR.throwException();
        }

        if (player.getLastMove() != null) {

            Long torespect = TickDuration * MovementDelay;
            LocalDateTime lmove = player.getLastMove();
            lmove = lmove.plus(torespect, ChronoUnit.MILLIS);
            LocalDateTime lactual = LocalDateTime.now();

            if (lmove.isAfter(lactual)) {
                ErrorCode.MOVE429_ERROR.throwException();
            }
        }

            if (element == Direction.UP) {
                if (player.getPosY() == 0 || !map3d.get(player.getPosY() - 1).get(player.getPosX()).getTerrainType().isWalkable()) {
                    ErrorCode.MOVE400_ERROR.throwException();
                } else {
                    player_model.setLastMove(LocalDateTime.now());
                    player_model.setPosY(player.getPosY() - 1);
                    player_repository.persist(player_model);
                }
            } else if (element == Direction.DOWN) {
                try {
                    if (player.getPosY() > map3d.toArray().length || !map3d.get(player.getPosY() + 1).get(player.getPosX()).getTerrainType().isWalkable()) {
                        ErrorCode.MOVE400_ERROR.throwException();
                    } else {
                        player_model.setLastMove(LocalDateTime.now());
                        player_model.setPosY(player.getPosY() + 1);
                        player_repository.persist(player_model);
                    }
                } catch (Exception e) {
                    ErrorCode.MOVE400_ERROR.throwException();
                }
            } else if (element == Direction.LEFT) {
                if (player.getPosX() == 0 || !map3d.get(player.getPosY()).get(player.getPosX() - 1).getTerrainType().isWalkable()) {
                    ErrorCode.MOVE400_ERROR.throwException();
                } else {
                    player_model.setLastMove(LocalDateTime.now());
                    player_model.setPosX(player.getPosX() - 1);
                    player_repository.persist(player_model);
                }
            } else if (element == Direction.RIGHT) {

                try {
                    if (player.getPosX() > map3d.get(player.getPosY()).toArray().length || !map3d.get(player.getPosY()).get(player.getPosX() + 1).getTerrainType().isWalkable()) {
                        ErrorCode.MOVE400_ERROR.throwException();
                    } else {
                        player_model.setLastMove(LocalDateTime.now());
                        player_model.setPosX(player.getPosX() + 1);
                        player_repository.persist(player_model);
                    }


                } catch (Exception e) {
                    ErrorCode.MOVE400_ERROR.throwException();
                }


            }

            return ModelToEntityConverter.move(player_model);
        }
    }

