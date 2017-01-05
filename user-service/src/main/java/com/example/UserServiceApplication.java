package com.example;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}

@Entity
@Data
class User implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	private String name;
}
@RepositoryRestResource
interface UserRepository extends JpaRepository<User,Long>{
    @RequestMapping("all")
    List<User> findAll();
}


@RestController
@RefreshScope
class MessageController {
	@Value("${message}")
	private String message;
	@GetMapping("/message")
	public String message(){
		return message;
	}
}
