package fr.epita.assistants.yakamon_testsuite;

import fr.epita.assistants.yakamon.converter.EntityToDTOConverter;
import fr.epita.assistants.yakamon.converter.ModelToDTOConverter;
import fr.epita.assistants.yakamon.converter.ModelToEntityConverter;
import fr.epita.assistants.yakamon.data.model.*;
import fr.epita.assistants.yakamon.data.repository.GameRepository;
import fr.epita.assistants.yakamon.data.repository.ItemRepository;
import fr.epita.assistants.yakamon.data.repository.PlayerRepository;
import fr.epita.assistants.yakamon.data.repository.YakamonRepository;
import fr.epita.assistants.yakamon.domain.entity.GameEntity;
import fr.epita.assistants.yakamon.domain.entity.MoveEntity;
import fr.epita.assistants.yakamon.domain.entity.PlayerEntity;
import fr.epita.assistants.yakamon.domain.entity.YakadexEntity;
import fr.epita.assistants.yakamon.domain.service.*;
import fr.epita.assistants.yakamon.presentation.api.response.CatchResponse;
import fr.epita.assistants.yakamon.presentation.api.response.MoveResponse;
import fr.epita.assistants.yakamon.presentation.api.response.StartResponse;
import fr.epita.assistants.yakamon.utils.Direction;
import fr.epita.assistants.yakamon.utils.tile.ItemType;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ServiceTest {


    @Inject
    PlayerRepository player_repository;

    @Inject
    GameRepository game_repository;

    @Inject
    ItemRepository item_repository;

    @Inject
    YakamonRepository yakamon_repository;

    @Inject
    YakadexService yakadex_service;

    @Inject
    PlayerService player_service;

    @Inject
    TeamService team_service;

    @Inject
    CatchService catch_service;

    @Inject
    MoveService move_service;

    void init(){
        PlayerModel player = new PlayerModel();
        player_repository.deleteAll();

        player.setName("diego");
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


        GameModel map_model = new GameModel();
        map_model.setMap("3Sn;3Sn;3Sn");
        game_repository.persist(map_model);
    }
    @Test
    @Transactional
    void CorerTest() {

        init();
        PlayerEntity moveentity = player_service.Player();

        assertEquals(0, moveentity.getPosY());
        assertEquals(0, moveentity.getPosX());
        assertEquals("diego", moveentity.getName());
        assertNull(moveentity.getLastMove());
    }




    @Test
    @Transactional
    void SimpleMoveSide() {

        init();
        MoveEntity moveentity = move_service.Move(Direction.RIGHT);
        assertEquals(1, moveentity.getPosX());
    }

    @Test
    @Transactional
    void DoubleMoveSide() {

        init();
        move_service.Move(Direction.RIGHT);
        MoveEntity moveentity = move_service.Move(Direction.RIGHT);
        assertEquals(2, moveentity.getPosX());
    }

    @Test
    @Transactional
    void SimpleMoveDOWN() {

        init();
        MoveEntity moveentity = move_service.Move(Direction.DOWN);
        assertEquals(1, moveentity.getPosY());
    }

    @Test
    @Transactional
    void DoubleMoveDOWN() {
        init();
        move_service.Move(Direction.DOWN);
        MoveEntity moveentity = move_service.Move(Direction.DOWN);
        assertEquals(2, moveentity.getPosY());
    }

    @Test
    @Transactional
    void MoveOutOfTheMapError() {

        init();
        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            MoveEntity moveentity = move_service.Move(Direction.LEFT);
        assertEquals(0, moveentity.getPosX());
    });
        String expectedMessage = "HTTP 400 Bad Request";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    @Transactional
    void NoStartMapError() {

        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            MoveEntity moveentity = move_service.Move(Direction.LEFT);
            assertEquals(0, moveentity.getPosX());
        });
        String expectedMessage = "HTTP 400 Bad Request";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    @Transactional
    void PlayerTest() {

        init();
        PlayerEntity moveentity = player_service.Player();

        assertEquals(0, moveentity.getPosY());
        assertEquals(0, moveentity.getPosX());
        assertEquals("diego", moveentity.getName());
        assertNull(moveentity.getLastMove());
    }




    @Test
    @Transactional
    void ConvertorTest() {

        init();
        MoveEntity moveentity = move_service.Move(Direction.RIGHT);

        MoveResponse emtity = EntityToDTOConverter.move(moveentity);

        assertNotNull(emtity);
        assertEquals(1, emtity.getPosX());
    }


    @Test
    @Transactional
    void TeamTestService() {

        init();
        YakamonModel catchh = catch_service.Catch();
        yakamon_repository.persist(catchh);

        List<YakamonModel> team = team_service.TeamService();

        assertNotNull(team.get(0));
        assertEquals("Nicotali", team.get(0).getNickname());

    }



    @Test
    @Transactional
    void pokedexTestService() {

        init();

        List<YakadexEntity> catchh = yakadex_service.getYakadex(true);
        assertNotNull(catchh.get(0));
    }

    @Test
    @Transactional
    void ModelToResponseConvertor() {

        init();
        YakamonModel catchh = catch_service.Catch();
        CatchResponse voila = ModelToDTOConverter.catchh(catchh);
        assertNotNull(voila);
        assertEquals("Nicotali", voila.getNickname());
    }

    @Test
    @Transactional
    void ModelToEntityConvertor() {

        init();
        PlayerEntity moveentity = player_service.Player();
        PlayerModel model = new PlayerModel();
        var resp = ModelToEntityConverter.player(model);
        assertNotNull(resp);
    }
    @Test
    @Transactional
    void TeamTestServiceNULL() {

        init();
        YakamonModel catchh = catch_service.Catch();
        yakamon_repository.persist(catchh);

        List<YakamonModel> team = team_service.TeamService();
        assertNotNull(team.get(0));
        assertEquals("Nicotali", team.get(0).getNickname());

    }


        @Test
    @Transactional
    void MoveOutOfTheMapp() {

        init();
        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            MoveEntity moveentity = move_service.Move(Direction.UP);
            assertEquals(0, moveentity.getPosX());
        });
        String expectedMessage = "HTTP 400 Bad Request";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @Transactional
    void ConvertorTest2() {

        init();
        MoveEntity moveentity = move_service.Move(Direction.RIGHT);

        MoveResponse emtity = EntityToDTOConverter.move(moveentity);

        assertNotNull(emtity);
        assertEquals(1, emtity.getPosX());
        assertEquals(0, emtity.getPosY());
    }
/*
    @Test
    @Transactional
    void NoStartMapError() {

        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            MoveEntity moveentity = move_service.Move(Direction.LEFT);
            assertEquals(0, moveentity.getPosX());
        });
        String expectedMessage = "HTTP 400 Bad Request";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }


   @Test
    @Transactional
    void deleteTest() {

        init();
        MoveEntity moveentity = move_service.Move(Direction.RIGHT);

        MoveResponse emtity = EntityToDTOConverter.move(moveentity);

        assertNotNull(emtity);
        assertEquals(1, emtity.getPosX());


    @Test
    @Transactional
    void PlayerTest() {

        init();
        PlayerEntity moveentity = player_service.Player();

        assertEquals(0, moveentity.getPosY());
        assertEquals(0, moveentity.getPosX());
        assertEquals("diego", moveentity.getName());
        assertNull(moveentity.getLastMove());
    }


 }

    @Test
    @Transactional
    void RenameService() {

        init();
        YakamonModel catchh = catch_service.Catch();
        yakamon_repository.persist(catchh);

        List<YakamonModel> team = team_service.TeamService();

        assertNotNull(team.get(0));
        assertEquals("Nicotali", team.get(0).getNickname());

    }

*/
}
