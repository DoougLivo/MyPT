package com.assignment.choi.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.assignment.choi.domain.DepDto;

@Service
public class PTService {
	
	public ResponseEntity<DepDto> goUser() {
		URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8082") //http://localhost에 호출
                .path("/user_PT")
//                .queryParam("depList", "steve")  // query parameter가 필요한 경우 이와 같이 사용
//                .queryParam("age", 10)
                .encode()
                .build()
                .toUri();
		
		DepDto dDto = new DepDto();
		List<DepDto> dlist = new ArrayList<DepDto>();
		
		RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DepDto> responseEntity = restTemplate.getForEntity(uri, DepDto.class);
        return responseEntity;
		
	}
	
	
	
}
