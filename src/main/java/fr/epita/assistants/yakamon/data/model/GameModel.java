package fr.epita.assistants.yakamon.data.model;

import fr.epita.assistants.yakamon.utils.tile.ItemType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="game")
@ApplicationScoped
public class GameModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(columnDefinition = "TEXT")
    String map;

}
