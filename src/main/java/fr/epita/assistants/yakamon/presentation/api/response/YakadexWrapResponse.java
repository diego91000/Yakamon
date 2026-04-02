package fr.epita.assistants.yakamon.presentation.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class YakadexWrapResponse {
    public YakadexWrapResponse(List<YakadexResponse> entries) {
        this.entries = entries;
    }
    List<YakadexResponse> entries;
}
