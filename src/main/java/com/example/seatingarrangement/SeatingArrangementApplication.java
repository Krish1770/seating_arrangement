package com.example.seatingarrangement;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoAuditing
public class SeatingArrangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeatingArrangementApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper()
	{
           return new ModelMapper();
	}

}