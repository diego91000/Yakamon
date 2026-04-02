package fr.epita.assistants.yakamon.presentation.api.response;

import lombok.Data;

@Data
public class MoveResponse {
    Integer posX;
    Integer posY;

    public MoveResponse(Integer posX, Integer posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
