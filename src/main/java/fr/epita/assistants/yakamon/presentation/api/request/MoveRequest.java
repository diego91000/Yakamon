package fr.epita.assistants.yakamon.presentation.api.request;

import fr.epita.assistants.yakamon.utils.Direction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class MoveRequest {

    @Enumerated(EnumType.STRING)
    public Direction direction;

    public MoveRequest(Direction direction) {
        this.direction = direction;
    }
}