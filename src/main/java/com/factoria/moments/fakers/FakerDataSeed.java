package com.factoria.moments.fakers;

//import aj.org.objectweb.asm.TypeReference;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.Role;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IAuthRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.repositories.IRoleRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class FakerDataSeed {


    private IRoleRepository roleRepository;
    private IAuthRepository authRepository;
    private IMomentsRepository momentsRepository;
    private PasswordEncoder passwordEncoder;


    public FakerDataSeed(IRoleRepository roleRepository, IAuthRepository authRepository, IMomentsRepository momentsRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.authRepository = authRepository;
        this.momentsRepository = momentsRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @PostConstruct
    public void addData(){
        this.createUsers();
        this.createMultipleMoments();
    }

    public void createUsers(){
        Set<Role> userRoles = Set.of(roleRepository.findByName(Role.RoleName.ROLE_USER).get());

        var agnes = new User();
        agnes.setRoles(userRoles);
        agnes.setUsername("afonttorres");
        agnes.setEmail("aft@gmail.com");
        agnes.setPassword(passwordEncoder.encode("password1234"));
        agnes.setName("Agnes");
        agnes.setAvatarUrl("https://images2.alphacoders.com/521/521982.jpg");
        agnes.setBannerUrl("https://images.unsplash.com/photo-1523049673857-eb18f1d7b578?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1075&q=80");
        agnes.setFollowers(5);
        agnes.setFollowing(1000);
        agnes.setDescription("Lol");

        var nil = new User();
        nil.setRoles(userRoles);
        nil.setUsername("nfont4");
        nil.setEmail("nft@gmail.com");
        nil.setPassword(passwordEncoder.encode("password1234"));
        nil.setName("Nil");
        nil.setAvatarUrl("https://img.ecartelera.com/noticias/fotos/52100/52153/1.jpg");
        nil.setBannerUrl("https://imagenes.elpais.com/resizer/0YkWqRvszLt7vbYY69RHI28trHo=/1960x0/cloudfront-eu-central-1.images.arcpublishing.com/prisa/6TIOUTQV4DCNJTPRHFBQQCYQGA.jpg");
        nil.setFollowers(1000);
        nil.setFollowing(1000);
        nil.setDescription("Avui mhe fet uns fideus per dinar... Oh");

        authRepository.saveAll(List.of(agnes, nil));
    }

    public Moment createMoment(String loc, String desc, String img, Long id){
        var moment = new Moment();
        moment.setLocation(loc);
        moment.setDescription(desc);
        moment.setImgUrl(img);
        if(id.equals(2))moment.setCreator(authRepository.findByUsername("afonttorres").get());
        if(!id.equals(2))moment.setCreator(authRepository.findById(id).get());
        return moment;
    }

    public void createMultipleMoments(){
        List<Moment> moments = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<MomentReqDto>> typeReference = new TypeReference<List<MomentReqDto>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
        System.out.println(inputStream);
        try{
            List<MomentReqDto> momentsReq = mapper.readValue(inputStream, typeReference);
            momentsReq.forEach(req -> moments.add(this.createMoment(req.getLocation(), req.getDescription(), req.getImgUrl(), req.getUserId())));
            momentsRepository.saveAll(moments);
            System.out.println("Moments saved!");
        }catch (IOException e){
            System.out.println("Unable to save moments: "+ e.getMessage());
        }
    }




}
