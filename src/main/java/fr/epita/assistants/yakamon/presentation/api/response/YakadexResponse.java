package fr.epita.assistants.yakamon.presentation.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.epita.assistants.yakamon.data.model.YakadexEntryModel;
import fr.epita.assistants.yakamon.data.model.YakamonModel;
import fr.epita.assistants.yakamon.utils.ElementType;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class YakadexResponse {
    Integer id;
    String name;
    ElementType firstType;
    ElementType secondType;
    Integer evolveThreshold;
    Integer evolutionId;
    Boolean caught;
    String description;
}