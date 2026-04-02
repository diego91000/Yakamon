package fr.epita.assistants.yakamon.domain.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveEntity {
    Integer PosX;

    public MoveEntity(Integer posX, Integer posY) {
        this.PosX = posX;
        this.PosY = posY;
    }

    Integer PosY;
}
