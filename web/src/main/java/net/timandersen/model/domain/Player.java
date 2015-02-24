package net.timandersen.model.domain;

import net.timandersen.enums.Gender;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "player")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerId", updatable = false, nullable = false)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @Type(type = "net.timandersen.enums.GenderEnumUserType")
    private Gender gender;

    @Column
    private String email;

    @Column
    private int tickets;

    public Player() {
    }

    public Player(String firstName, String lastName, Gender gender, String email, int tickets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.tickets = tickets;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public int getTickets() {
        return tickets;
    }
}