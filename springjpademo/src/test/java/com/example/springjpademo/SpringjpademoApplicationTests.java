package com.example.springjpademo;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringjpademoApplicationTests {
  @Autowired private UserRepository userRepository;
  private User user;

  @Before
  public void create() {
  }
  
  @Test
  void contextLoads() {
	  user = new User("John Smith", "john.smith");
	  user = userRepository.save(user);
	  assertThat(user.getCreated()).isNotNull();
	  assertThat(user.getModified()).isNotNull();
	  assertThat(user.getCreatedBy()).isEqualTo("Mr. Auditor Victor");
	  assertThat(user.getModifiedBy()).isEqualTo("Mr. Auditor Victor");
  }
}
