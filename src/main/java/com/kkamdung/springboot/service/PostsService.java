package com.kkamdung.springboot.service;

import org.springframework.stereotype.Service;

import com.kkamdung.springboot.domain.posts.PostsRepository;
import com.kkamdung.springboot.web.dto.PostsSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {
	
	private final PostsRepository postsRepository;
	
	public Long save(PostsSaveRequestDto requestDto) {
		return postsRepository.save(requestDto.toEntity()).getId();
	}

}
