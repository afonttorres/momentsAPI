package com.factoria.moments.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="saves")
@NoArgsConstructor
public class Save {

    public Save(User saver, Moment moment){
        this.saver = saver;
        this.moment = moment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "saver_id")
    private User saver;
    @ManyToOne
    @JoinColumn(name = "moment_id")
    private Moment moment;
}
