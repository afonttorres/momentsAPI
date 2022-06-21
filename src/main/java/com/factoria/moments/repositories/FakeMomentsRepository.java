package com.factoria.moments.repositories;

import com.factoria.moments.models.Moment;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class FakeMomentsRepository {

    public List<Moment> findAll(){
        return getMomentList();
    }

    private List<Moment> getMomentList(){
        Moment moment1 = new Moment("url", "des", "tile", 1L);
        Moment moment2 = new Moment("url", "des", "tile", 2L);
        Moment moment3 = new Moment("url", "des", "tile", 3L);
        Moment moment4 = new Moment("url", "des", "tile", 4L);
        List<Moment> moments = List.of(moment1, moment2, moment3, moment4);
        return moments;
    }

}
