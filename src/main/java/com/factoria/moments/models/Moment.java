package com.factoria.moments.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private int likes;
    private int saves;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "moment")
    private List<Comment> comments = new ArrayList<>();
    @JsonSerialize
    public int commentsCount (){
        return this.comments.size();
    }

    @OneToMany(mappedBy = "moment")
    private List<Like> favs = new ArrayList<>();

    public void addLike(Like like){
        if(!like.getMoment().equals(this)) return;
       favs.add(like);
    }
    public int likesCount() {
        return favs.size();
    }

    public boolean isFaved(User user) {
        var favLover = favs.stream().filter(Fav -> Fav.getLover() == (user)).findFirst();
        if(favLover.isEmpty()) return false;
        return true;
    }
}
