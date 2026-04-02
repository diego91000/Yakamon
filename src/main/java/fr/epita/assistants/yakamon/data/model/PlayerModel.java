package fr.epita.assistants.yakamon.data.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.rmi.server.UID;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@ApplicationScoped
@Table(name="player")
public  class PlayerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) UUID uuid;
    @Column(columnDefinition = "varchar(20)")
    String name;

    @Column(name="pos_x")
    Integer posX;
    @Column(name="pos_y")
    Integer posY;
    @Column(name="last_move")
    LocalDateTime lastMove;
    @Column(name="last_catch")
    LocalDateTime lastCatch;
    @Column(name="last_collect")
    LocalDateTime lastCollect;
    @Column(name="last_feed")
    LocalDateTime lastFeed;
}

