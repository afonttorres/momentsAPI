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
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class FakeMomentsRepository implements IMomentsRepository {
    private List<Moment> getMomentList(){
        Moment moment1 = new Moment("nil","la garriga, catalunya","beer","doing fine","https://www.publicdomainpictures.net/pictures/30000/nahled/sunset-15.jpg", "https://images2.alphacoders.com/521/521982.jpg", 89L, 21L, 5L,  1L);
        Moment moment2 = new Moment("esther","barcelona, catalunya","shopping day","lorem ipsum","https://www.publicdomainpictures.net/pictures/30000/nahled/sunset-15.jpg", "https://images2.alphacoders.com/521/521982.jpg", 89L, 21L, 5L,  2L);
        Moment moment3 = new Moment("agnès","girona, catalunya","destroyer monster","gripau sleeping","https://www.publicdomainpictures.net/pictures/30000/nahled/sunset-15.jpg", "https://images2.alphacoders.com/521/521982.jpg", 89L, 21L, 5L,  3L);
        Moment moment4 = new Moment("stephen","london, uk","rainy day","lorem ipsum","https://www.publicdomainpictures.net/pictures/30000/nahled/sunset-15.jpg", "https://images2.alphacoders.com/521/521982.jpg", 89L, 21L, 5L,  4L);
        List<Moment> moments = List.of(moment1, moment2, moment3, moment4);
        return moments;
    }

    private List<String> getSearchFields(String search, Moment moment){
        List<String> resFields = new ArrayList<>();
        Field[] fields = moment.getClass().getDeclaredFields();
        for(Field f: fields){
            var fieldType = f.getType().getTypeName();
            var searchType = search.getClass().getTypeName();
            if(Objects.equals(fieldType, searchType)) resFields.add(f.getName());
        }
        return resFields;
    }
    private String capitalize(String data){
        String str = data.substring(0, 1).toUpperCase() + data.substring(1);
        return str;
    }
    private List<Method> getSearchMethods(List<String> fields, Moment moment){
        List<Method> methods = List.of(moment.getClass().getDeclaredMethods());
        List<Method> resMethods = new ArrayList<>();
        for(String f: fields){
            String name = capitalize(f);
            for(Method m : methods){
                if(m.getName().contains("get"+name)) resMethods.add(m);
            }
        }
        return resMethods;
    }
    private List<String> getSearchData(List<Method> methods, Moment moment) throws InvocationTargetException, IllegalAccessException {
        List<String> searchData = new ArrayList<>();
        for(Method m : methods){
            m.setAccessible(true);
            searchData.add(m.invoke(moment).toString());
        }
        return searchData;
    }
    private boolean checkIfContainsSearch(List<String> data,String search) throws InvocationTargetException, IllegalAccessException {
        for(String d: data){if(d.contains(search.toLowerCase()))return true;}
        return false;
    }
    private void search(List<Moment> moments, String search, Moment moment) throws InvocationTargetException, IllegalAccessException {
        var fields = getSearchFields(search, moment);
        var methods = getSearchMethods(fields, moment);
        var data = getSearchData(methods, moment);
        var doesContain = checkIfContainsSearch(data, search);
        if (doesContain) moments.add(moment);
    }
    @Override
    public List<Moment> getMoments(){
        return getMomentList();
    }

    @Override
    public Moment getMomentById(Long id){
        var moments = getMomentList();
        Moment moment = moments.stream()
                .filter(Moment -> Moment.getId() == id)
                .collect(Collectors.toList()).get(0);
        return moment;
    }
    @Override
    public List<Moment> getMomentBySearch(String search) {
        var data = search.toLowerCase();
        var moments = getMomentList();

        //1
        /*
        List<Moment> searchCollection = moments.stream().filter(
                        Moment ->
                                Moment.getDescription().contains(data) ||
                                Moment.getTitle().contains(data) ||
                                Moment.getUser().contains(data) ||
                                Moment.getLocation().contains(data)
                        )
                .collect(Collectors.toList());

        moments.forEach(Moment -> getSearchFields(search, Moment));
        return searchCollection;*/
        //2
        List<Moment> searchList = new ArrayList<>();
        moments.forEach(Moment -> {
            try {
                search(searchList, search, Moment);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return searchList;
    }

}
