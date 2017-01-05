package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class WebUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebUiApplication.class, args);
    }
}

@RestController
class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/message")
    public String message() {
        return userService.message();
    }

    @GetMapping("/names")
    public String names() {
        return userService.getAllUses().getContent().stream().map(User::getName).collect(Collectors.joining(","));
    }
}

@FeignClient("user-service")
interface UserService {
    @RequestMapping(method = RequestMethod.GET, value = "/message")
    String message();

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    PagedResources<User> getAllUses();

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    Resource<User> getUser(@PathVariable("id") long id);
}

@Data
class User{
    private Long id;
    private String name;
}

