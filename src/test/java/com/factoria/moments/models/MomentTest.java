package com.factoria.moments.models;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MomentTest {

    @Test
    void shouldHaveACommentsCounter(){
        //GIVEN
        var moment = new Moment();
        var comment = new Comment();
        //SYSTEM UNDER TEST
        var sut = moment.commentsCount();
        //THEN
        assertThat(sut, equalTo(0));
        //WHEN
    }
}