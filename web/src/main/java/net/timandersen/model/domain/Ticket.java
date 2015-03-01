package net.timandersen.model.domain;

import org.apache.commons.lang.math.RandomUtils;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketId", updatable = false, nullable = false)
    private Long id;

    @Column
    private long number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playerId", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raffleId", nullable = false)
    private Raffle raffle;

    public Ticket() {
    }

    public Ticket(Player player, Raffle raffle) {
        number = RandomUtils.nextLong();
        this.player = player;
        this.raffle = raffle;
    }

    public long getNumber() {
        return number;
    }

    public Player getPlayer() {
        return player;
    }

    public Raffle getRaffle() {
        return raffle;
    }

}
