package com.erivelton.cloneifood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.erivelton.cloneifood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class CloneifoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloneifoodApiApplication.class, args);
		/*ApplicationContext applicationContext = SpringApplication.run(CloneifoodApiApplication.class, args);
		String[] allBeanNames = applicationContext.getBeanDefinitionNames();
		Arrays.stream(allBeanNames).forEach(System.out::println); // nomes

		System.out.println(allBeanNames.length); // quantidade
		System.out.println(applicationContext.getBeanDefinitionCount()); // quantidade*/
	}

}
