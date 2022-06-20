package com.factoria.moments.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="moments")
@NoArgsConstructor
@AllArgsConstructor
public class Moment {
    private String imgUrl;
    private String description;
    private String title;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
