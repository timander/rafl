package net.timandersen.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "raffle")
public class Raffle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raffleId", updatable = false, nullable = false)
    private Long id;

    @Column
    private String cause;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    //hibernate rocks
    public Raffle() {
    }

    public Raffle(String cause, Date startDate) {
        this.cause = cause;
        this.startDate = startDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public String getCause() {
        return cause;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}