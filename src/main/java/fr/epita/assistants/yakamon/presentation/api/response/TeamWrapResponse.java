package fr.epita.assistants.yakamon.presentation.api.response;

import lombok.Data;

import java.util.List;

@Data
public class TeamWrapResponse {
    public TeamWrapResponse(List<TeamResponse> entries) {
        this.yakamons = entries;
    }
    List<TeamResponse> yakamons;
}
