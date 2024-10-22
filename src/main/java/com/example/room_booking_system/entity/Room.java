package com.example.room_booking_system.entity;


import javax.persistence.*;

@Entity
@Table
public class Room {
    @Id
    @SequenceGenerator(

            name = "room_sequence",
            sequenceName = "room_sequence",
            allocationSize = 1
    )

    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "room_sequence")
    private Long id;
    private String name;

    public Room() {
    }

    public Room(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
