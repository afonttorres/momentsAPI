package com.factoria.moments.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToMany(mappedBy = "moment")
    private List<Comment> comments = new ArrayList<>();
    @JsonSerialize
    public int commentsCount (){
        return this.comments.size();
    }

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToMany(mappedBy = "moment")
    private List<Like> likes = new ArrayList<>();

    public void toggleLike(Like like){
        if(!like.getMoment().equals(this)) return;
        var found = likes.stream().filter(Like -> Like.getLiker() == like.getLiker()).findAny();
        if (found.isPresent()) {
            likes.remove(found.get());
            return;
        }
        likes.add(like);
    }
    public int likesCount() {
        return likes.size();
    }
    public boolean isLiked(User user) {
        var liker = likes.stream().filter(Fav -> Fav.getLiker() == user).findAny();
        if(liker.isEmpty()) {
            return false;
        }
        return true;
    }

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToMany(mappedBy = "moment")
    private List<Save> saves = new ArrayList<>();
    public void toggleSave(Save save){
        if(!save.getMoment().equals(this)) return;
        var found = saves.stream().filter(Save-> Save.getSaver() == save.getSaver()).findAny();
        if(found.isPresent()){
            saves.remove(found.get());
            return;
        }
        saves.add(save);
    }
    public int savesCount(){return saves.size();}

    public boolean isSaved(User user){
        var saver = saves.stream().filter(Save->Save.getSaver() == user).findFirst();
        if(saver.isEmpty()) {
            return false;
        }
        return true;
    }
}
