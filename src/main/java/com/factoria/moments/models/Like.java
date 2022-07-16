package com.factoria.moments.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="likes")
@NoArgsConstructor
public class Like {
    public Like(User liker, Moment moment) {
        this.liker = liker;
        this.moment = moment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lover_id")
    private User liker;
    @ManyToOne
    @JoinColumn(name = "moment_id")
    private Moment moment;
}
