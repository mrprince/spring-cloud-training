package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
    @Bean
	CommandLineRunner runner (UserRepository userRepository){
	    return args -> {
            Arrays.asList("tom,jerry,cat".split(",")).forEach(x->userRepository.save(new User(x)));
            userRepository.findAll().forEach(System.out::println);
	    };
    }
}

interface UserRepository extends JpaRepository<User,Long>{


}
@Entity
class User implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	private String name;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("user{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public User(String name) {
        this.name = name;
    }
}
@Controller
class UserController{
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/list")
    public ModelAndView list(){
        return new ModelAndView("user","list",userRepository.findAll());
    }


}