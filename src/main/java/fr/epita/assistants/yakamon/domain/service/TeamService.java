package fr.epita.assistants.yakamon.domain.service;

import fr.epita.assistants.yakamon.data.model.GameModel;
import fr.epita.assistants.yakamon.data.model.ItemModel;
import fr.epita.assistants.yakamon.data.model.YakamonModel;
import fr.epita.assistants.yakamon.data.repository.GameRepository;
import fr.epita.assistants.yakamon.data.repository.ItemRepository;
import fr.epita.assistants.yakamon.data.repository.YakadexEntryRepository;
import fr.epita.assistants.yakamon.data.repository.YakamonRepository;
import fr.epita.assistants.yakamon.domain.entity.YakadexEntity;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import fr.epita.assistants.yakamon.utils.Item;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TeamService {

        @Inject
        YakamonRepository yakamon_repository;


        @Inject
        GameRepository game_repository;


        @Transactional
        public List<YakamonModel> TeamService(){

            GameModel map_model = game_repository.findAll().firstResult();
            if(map_model == null){
                ErrorCode.MOVE400_ERROR.throwException();
            }

            List<YakamonModel> inventory = yakamon_repository.listAll();

            return inventory;
        }


    }
