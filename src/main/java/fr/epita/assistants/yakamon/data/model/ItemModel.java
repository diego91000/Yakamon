package fr.epita.assistants.yakamon.data.model;

import fr.epita.assistants.yakamon.utils.Item;
import fr.epita.assistants.yakamon.utils.tile.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="item")
public class ItemModel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
        ItemType type;
        Integer quantity;
}
