package com.example.springbootjson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootApplication
public class LearnJsonAndBootApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	public static void main(String[] args) {
		SpringApplication.run(LearnJsonAndBootApplication.class, args);
	}

	@Override
	public void run(String... args) {
		String sql="SELECT * from UserAccount";
		List<UserAccount> list= jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(UserAccount.class));
		list.forEach(System.out :: println);
	}
}
