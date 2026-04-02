package fr.epita.assistants.yakamon.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="yakamon")
public class YakamonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) UUID uuid;
    @Column(columnDefinition = "varchar(20)")
    String nickname;
    @Column(name="energy_points")
    Integer energyPoints;

    @ManyToOne
    @JoinColumn(name="yakadex_entry_id")
    YakadexEntryModel yakadexEntry;
}
