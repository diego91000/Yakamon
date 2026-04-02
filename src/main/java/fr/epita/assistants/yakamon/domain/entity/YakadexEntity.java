package fr.epita.assistants.yakamon.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.epita.assistants.yakamon.utils.ElementType;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class YakadexEntity {
    Integer id;
    String name;
    ElementType firstType;
    ElementType secondType;
    Integer evolveThreshold;
    Integer evolution;
    Boolean caught;
    String description;
}
