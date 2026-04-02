package fr.epita.assistants.yakamon.data.model;

import fr.epita.assistants.yakamon.utils.ElementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.sisu.Nullable;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="yakadex_entry")
public class YakadexEntryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
    @Column(columnDefinition = "varchar(20)")
    String name;
    Boolean caught;
    @Enumerated(EnumType.STRING)
    @Column(name = "first_type")
    ElementType firstType;
    @Enumerated(EnumType.STRING)
    @Column(name = "second_type")
    ElementType secondType;
    @Column(columnDefinition = "varchar")
    String description;

    @OneToOne
    @JoinColumn(name = "evolution_id")
    YakadexEntryModel evolution;

    @Column(name="evolve_threshold")
    public Integer evolveThreshold;
}


