package com.example.inflearnrestfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        final List<User> users = service.findAll();


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "password");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        final MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

//     GET /admin/users/1 or /admin/users/10 -> String -> Convert -> int
//    @GetMapping("v1/users/{id}")
//    @GetMapping(value = "/users/{id}", params = "version=1")
//    @GetMapping(value = "/users/{id}", headers="X-API-VERSION=1")
    @GetMapping(value = "/user/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        final User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        final MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

//    @GetMapping("v2/users/{id}")
//    @GetMapping(value = "/users/{id}", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers="X-API-VERSION=2")
    @GetMapping(value = "/user/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        final User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> UserV2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
        final MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }
}