package fr.epita.assistants.yakamon.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static jakarta.ws.rs.core.Response.Status;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    PLAYER_ERROR(Status.BAD_REQUEST, "The game is not running."),
    MOVE400_ERROR(Status.BAD_REQUEST, "Invalid direction or the game is not running."),
    MOVE429_ERROR(Status.TOO_MANY_REQUESTS, "Player has recently moved and must wait before moving again."),
    MOVE404_ERROR(Status.NOT_FOUND, "No pokemon for this id");



    private final Response.Status errorCode;

    private final String errorMessage;

    public WebApplicationException getException() {
        return new WebApplicationException(Response.status(errorCode).entity(new ErrorInfo(errorMessage)).build());
    }

    public void throwException() {
        throw getException();
    }

    public void throwException(String prefix) {
        throw new WebApplicationException(Response.status(errorCode).entity(new ErrorInfo(prefix + ": " + errorMessage)).build());
    }
}
