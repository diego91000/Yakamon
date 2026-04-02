package fr.epita.assistants.yakamon.domain.service;

import fr.epita.assistants.yakamon.converter.EntityToDTOConverter;
import fr.epita.assistants.yakamon.data.model.GameModel;
import fr.epita.assistants.yakamon.data.model.ItemModel;
import fr.epita.assistants.yakamon.data.repository.GameRepository;
import fr.epita.assistants.yakamon.data.repository.ItemRepository;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import fr.epita.assistants.yakamon.utils.Item;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InventoryService {

    @Inject
    ItemRepository item_repository;


    @Inject
    GameRepository game_repository;


    @Transactional
    public List<Item> InventoryService(){

        GameModel map_model = game_repository.findAll().firstResult();
        if(map_model == null){
            ErrorCode.MOVE400_ERROR.throwException();
        }

        List<Item> inv = new ArrayList<>();
        List<ItemModel> inventory = item_repository.listAll();

        inventory.forEach(elm ->
                inv.add(new Item(elm.getType(), elm.getQuantity())));
        return inv;
    }

}
