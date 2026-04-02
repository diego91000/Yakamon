package fr.epita.assistants.yakamon.presentation.api.response;

import fr.epita.assistants.yakamon.utils.Item;
import lombok.Data;

import java.util.List;

@Data
public class InventoryResponse {

    List<Item> items;

    public InventoryResponse(List<Item> items) {
        this.items = items;
    }
}
