package com.factoria.moments.models;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MomentTest {

    @Test
    void shouldHaveACommentsCounter(){
        var moment = new Moment();
        var comment = new Comment();
        var sut = moment.commentsCount();
        assertThat(sut, equalTo(0));
    }

    @Test
    void shouldHAveALikesCounter(){
        var moment = new Moment();
        var user = new User();
        moment.setId(1L);
        user.setId(1L);
        var like = new Like(user, moment);
        moment.toggleLike(like);
        int sut = moment.likesCount();
        assertThat(sut, equalTo(1));
    }

    @Test
    void momentShouldntLetAddLikeIfMomentDoesNotMatch(){
        var moment1 = new Moment();
        var moment2 = new Moment();
        var user = new User();
        moment1.setId(1L);
        moment2.setId(2L);
        user.setId(1L);
        var like = new Like(user, moment1);
        moment2.toggleLike(like);
        var sut = moment2.likesCount();
        assertThat(sut, equalTo(0));
    }

    @Test
    void momentShouldKnowIfUserLikesMoment(){
        var moment = new Moment();
        var latinLover = new User();
        var like = new Like(latinLover, moment);
        moment.toggleLike(like);
        var sut = moment.isLiked(latinLover);
        assertThat(sut, equalTo(true));
    }

    @Test
    void loverShouldBeContainedInFavListToIsLikeToAppear(){
        var moment = new Moment();
        var latinLover = new User();
        var notLatinLover = new User();
        var like = new Like(latinLover, moment);
        moment.toggleLike(like);
        var sut = moment.isLiked(notLatinLover);
        assertThat(sut, equalTo(false));
    }

    @Test
    void loverCantLikeMomentIfAlreadyLiked(){
        var moment = new Moment();
        var latinLover = new User();
        var like1 = new Like(latinLover, moment);
        var like2= new Like(latinLover, moment);
        moment.toggleLike(like1);
        moment.toggleLike(like2);
        var sut = moment.likesCount();
        var liked = moment.isLiked(latinLover);
        assertThat(sut, equalTo(0));
        assertThat(liked, equalTo(false));
    }
}