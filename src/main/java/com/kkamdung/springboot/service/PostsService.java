package com.kkamdung.springboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkamdung.springboot.domain.posts.Posts;
import com.kkamdung.springboot.domain.posts.PostsRepository;
import com.kkamdung.springboot.web.dto.PostsListResponseDto;
import com.kkamdung.springboot.web.dto.PostsResponseDto;
import com.kkamdung.springboot.web.dto.PostsSaveRequestDto;
import com.kkamdung.springboot.web.dto.PostsUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {
	
	private final PostsRepository postsRepository;
	
	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		return postsRepository.save(requestDto.toEntity()).getId();
	}

	@Transactional
	public Long update(Long id, PostsUpdateRequestDto requestDto) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
		
		posts.update(requestDto.getTitle(), requestDto.getContent());
		
		return id;
	}

	@Transactional(readOnly = true)
	public PostsResponseDto findById(Long id) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
		
		return new PostsResponseDto(posts);
	}
	
	@Transactional(readOnly = true)
	public List<PostsListResponseDto> findAllDesc() {
		return postsRepository.findAllDesc().stream()
				.map(PostsListResponseDto::new)
				.collect(Collectors.toList());
	}

}
