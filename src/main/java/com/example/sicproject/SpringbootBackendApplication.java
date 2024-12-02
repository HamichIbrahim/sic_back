package com.example.sicproject;


import com.example.sicproject.model.ChatRoom;
import com.example.sicproject.model.Message;
import com.example.sicproject.model.Resive;
import com.example.sicproject.model.User;
import com.example.sicproject.repository.MessageRepo;
import com.example.sicproject.repository.ResiveRepo;
import com.example.sicproject.repository.RoomRepo;
import com.example.sicproject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class

SpringbootBackendApplication implements CommandLineRunner {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoomRepo roomRepo;
	@Autowired

	private MessageRepo messageRepo;
	@Autowired
	private ResiveRepo resiveRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
