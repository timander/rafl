package net.timandersen.model.domain;

import net.timandersen.enums.Gender;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


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

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    @Cascade(value = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Ticket> tickets = new ArrayList<Ticket>();

    public Player() {
    }

    public Player(String firstName, String lastName, Gender gender, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void buyTicketFor(Raffle raffle) {
        tickets.add(new Ticket(this, raffle));
    }
}