package com.assignment.choi.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.assignment.choi.domain.HobbyDto;
import com.assignment.choi.domain.UserDto;
import com.assignment.choi.domain.UserHDto;
import com.assignment.choi.domain.UserHDtoPK;

@Service
public class PTService {
	String url = "http://localhost:8082";
	RestTemplate restTemplate = new RestTemplate();
	
	public Map<String, Object> goUser() {
		String uri = "/user_PT";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ResponseEntity<Map> responseEntity = restTemplate.exchange(url+uri, HttpMethod.GET, null, new ParameterizedTypeReference<Map>() {});
		System.out.println("status : " + responseEntity.getStatusCode());
		System.out.println("body : " + responseEntity.getBody());
        
        return responseEntity.getBody();
	}
	
	public void insert(UserDto dto) {
		String uri = "/insert_user_PT";
		restTemplate.postForObject(url+uri, dto, UserDto.class);
	}
	
	public void insert_hobby(UserDto dto, UserHDto hDto, String h_code_id) {
		UserHDto new_hDto = new UserHDto();
		HobbyDto hobbyDto = new HobbyDto();
		String uri = "/insert_userHobby_PT";
		// hDto 에 아이디 담아서 보내기
		hDto.setUserDto(dto);
		// hDto 에 취미코드 하나씩 담아서 보내기
		if(h_code_id.contains(",")) {
			String[] hic = h_code_id.split(",");
			for(int i=0; i<hic.length; i++) {
				// 임시 변수
				System.out.println("취미코드"+ (i+1) +": "+hic[i]);
				hobbyDto.setH_code_id(hic[i]);
				hDto.setHobbyDto(hobbyDto);
				
				System.out.println("new_hDto : "+new_hDto);
				restTemplate.postForObject(url+uri, hDto, UserHDto.class);
			}
		} else {
			hobbyDto.setH_code_id(h_code_id);
			hDto.setHobbyDto(hobbyDto);
			System.out.println("PT 서비스 hDto : "+new_hDto);
			restTemplate.postForObject(url+uri, hDto, UserHDto.class);
		}
	}
	
	public int idCheck(String userId) {
		String uri = "/idcheck_PT";
		int result = restTemplate.postForObject(url+uri, userId, int.class);
		return result;
	}
	
	
	public Map<String, Object> adminList(String searchKeyword) {
		System.out.println("User List");
		String uri;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(searchKeyword != null) {
			uri = url + "/admin_PT?searchKeyword="+searchKeyword;
		} else {
			uri = url + "/admin_PT";
		}
		ResponseEntity<Map> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Map>() {});
		System.out.println("status : " + responseEntity.getStatusCode());
		System.out.println("body : " + responseEntity.getBody());
		return responseEntity.getBody();
	}
	
	public Map<String, Object> depList() {
		System.out.println("Dep List");
		String uri="/depList_PT";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ResponseEntity<Map> responseEntity = restTemplate.exchange(url+uri, HttpMethod.GET, null, new ParameterizedTypeReference<Map>() {});
		System.out.println("status : " + responseEntity.getStatusCode());
		System.out.println("body : " + responseEntity.getBody());
		return responseEntity.getBody();
	}
	
	public Map<String, Object> hobbyList() {
		System.out.println("Hobby List");
		String uri="/hobbyList_PT";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ResponseEntity<Map> responseEntity = restTemplate.exchange(url+uri, HttpMethod.GET, null, new ParameterizedTypeReference<Map>() {});
		System.out.println("status : " + responseEntity.getStatusCode());
		System.out.println("body : " + responseEntity.getBody());
		return responseEntity.getBody();
	}
	
	public Map<String, Object> gethci(String userId) {
		System.out.println("hci");
		String uri="/hci_PT?userId="+userId;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ResponseEntity<Map> responseEntity = restTemplate.exchange(url+uri, HttpMethod.GET, null, new ParameterizedTypeReference<Map>() {});
		System.out.println("status : " + responseEntity.getStatusCode());
		System.out.println("body : " + responseEntity.getBody());
		return responseEntity.getBody();
	}
	
	
	public Map<String, Object> adminView(String userId, String searchKeyword) {
		String uri = "/admin/"+userId;
		ResponseEntity<Map> responseEntity = restTemplate.exchange(url+uri, HttpMethod.GET, null, new ParameterizedTypeReference<Map>() {});
		System.out.println("status : " + responseEntity.getStatusCode());
		System.out.println("body : " + responseEntity.getBody());
		return responseEntity.getBody();
	}
	
	
	public void deleteUser(UserDto dto) {
		String uri = "/admin/delete_PT";
		restTemplate.postForObject(url+uri, dto, UserDto.class);
	}
	
	public void deleteUserHobby(UserHDtoPK pk) {
		String uri = "/admin/deleteHobby_PT";
		restTemplate.postForObject(url+uri, pk, UserHDtoPK.class);
	}
	
	public void updateUser(UserDto dto) {
		String uri = "/admin/update_PT";
		restTemplate.postForObject(url+uri, dto, UserDto.class);
	}
	
	public void updateHobby(UserHDtoPK pk) {
		String uri = "/admin/updateHobby_PT";
		restTemplate.postForObject(url+uri, pk, UserDto.class);
	}
}