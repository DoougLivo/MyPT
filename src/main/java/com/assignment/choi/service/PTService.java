package com.assignment.choi.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

@Service
public class PTService {
	String url = "http://localhost:8082";
	
	public Map<String, Object> goUser() {
		url += "/user_PT";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
		System.out.println("status : " + responseEntity.getStatusCode());
		System.out.println("body : " + responseEntity.getBody());
        
        return responseEntity.getBody();
        
        
//		URI uri = UriComponentsBuilder
//      .fromUriString("http://localhost:8082") //http://localhost에 호출
//      .path("/test")
//      .queryParam("depList", "steve")  // query parameter가 필요한 경우 이와 같이 사용
//      .queryParam("age", 10)
//      .encode()
//      .build()
//      .toUri();
	}
	
	
	
}
