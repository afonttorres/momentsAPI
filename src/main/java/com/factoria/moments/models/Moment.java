package com.factoria.moments.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="moments")
@NoArgsConstructor
@AllArgsConstructor
public class Moment {
    private String imgUrl;
    private String description;
    private String location;
    private boolean isLiked = false;
    private boolean isSaved = false;
    private int likes = 48;
    private int saves = 2;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "moment")
    private List<Comment> comments = new ArrayList<>();
    @JsonSerialize
    public int commentsCount (){
        return this.comments.size();
    }

}
