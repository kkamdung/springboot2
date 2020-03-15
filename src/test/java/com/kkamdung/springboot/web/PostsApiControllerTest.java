package com.kkamdung.springboot.web;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkamdung.springboot.domain.posts.Posts;
import com.kkamdung.springboot.domain.posts.PostsRepository;
import com.kkamdung.springboot.web.dto.PostsSaveRequestDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class PostsApiControllerTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private PostsRepository postsRepository;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@After
	public void tearDown() {
		postsRepository.deleteAll();
	}
	
	@Test
	public void posts_등록된다() throws Exception {
		// given
		String title = "title";
		String content = "content";
		String author = "kkamdung@test.com";
		
		PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
				.title(title)
				.content(content)
				.author(author)
				.build();
		
		String url = "http://localhost:" + port + "/api/v1/posts";
		
		// when
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
		
		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);
		
		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);
		assertThat(all.get(0).getAuthor()).isEqualTo(author);
	}

}
