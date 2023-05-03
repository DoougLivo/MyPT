package com.assignment.choi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.choi.domain.HobbyDto;
import com.assignment.choi.domain.UserDto;
import com.assignment.choi.domain.UserHDto;
import com.assignment.choi.domain.UserHDtoPK;
import com.assignment.choi.service.PTService;

@Controller
public class UserController {
	//@Autowired
	//UserService userService;
	
	@Autowired
	private final PTService ptService;
	
	public UserController(PTService ptService) {
		this.ptService = ptService;
	}
	
	@GetMapping("/")
	String goMainPg() {
		return "index";
	}
	
	// 사용자 포털
	@GetMapping("/user")
	String goUser(Model model) {
		model.addAttribute("getHobbyList", ptService.goUser().get("getHobbyList"));
		model.addAttribute("depList", ptService.goUser().get("depList"));
		return "user/user";
	}
	
	// 사용자 승인 요청
	@ResponseBody
	@PostMapping("/insert_user")
	Map<String, String> insert_user(UserDto dto, UserHDto hDto, String h_code_id) {
		Map<String, String> map = new HashMap<>();
		try {
			// 유저 등록
			ptService.insert(dto);
			// 취미 등록
			if(h_code_id != null) {
				System.out.println("PT controller : "+h_code_id);
				ptService.insert_hobby(dto, hDto, h_code_id);
				System.out.println("취미 등록 됨");
			}
			map.put("msg", "success");
			map.put("ok", "승인 요청 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "fail");
		}
		return map;
	}
	
	// 관리자 포털
	@GetMapping("/admin")
	String getList(Model model, String searchKeyword, String userId) {
		model.addAttribute("list", ptService.adminList(searchKeyword).get("list"));
		model.addAttribute("depList", ptService.depList().get("depList"));
		model.addAttribute("searchKeyword", searchKeyword);
		if(userId != null && userId != "") {  // 상세정보 눌렀을 때
			// 사용자 정보
			model.addAttribute("view", ptService.adminView(userId, searchKeyword).get("view"));
			model.addAttribute("hci", ptService.gethci(userId).get("hci"));
			model.addAttribute("getHobbyList", ptService.hobbyList().get("getHobbyList"));
		}
		return "admin/admin";
	}
	
	// 관리자 포털 상세보기
	@GetMapping("/admin/{userId}")
	String getView(Model model, @PathVariable("userId")String userId, String searchKeyword) {
		model.addAttribute("list", ptService.adminList(searchKeyword).get("list"));
		model.addAttribute("depList", ptService.depList().get("depList"));
		model.addAttribute("hci", ptService.gethci(userId).get("hci"));
		model.addAttribute("getHobbyList", ptService.hobbyList().get("getHobbyList"));
		model.addAttribute("searchKeyword", searchKeyword);
		
		Map<String, Object> resultMap = ptService.adminView(userId, searchKeyword);
		// 사용자 정보
		model.addAttribute("view", resultMap.get("view"));
		return "admin/admin";
	}
	
	// 사용자 정보 수정
	@ResponseBody
	@PostMapping("/admin/update")
	Map<String, String> updateUser(UserDto dto, UserHDto hDto, String h_code_id, UserHDtoPK pk) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 사용자 수정
			ptService.updateUser(dto);
			
			// 취미 삭제
			ptService.deleteUserHobby(pk);
				
			// 취미 수정
			ptService.insert_hobby(dto, hDto, h_code_id);

			map.put("msg", "success");
			map.put("msg2", "저장되었습니다.");
		} catch (Exception e) {
			map.put("msg", "fail");
			e.printStackTrace();
		}
		return map;
	}
		
	// 사용자 삭제
	@ResponseBody
	@PostMapping("/admin/delete")
	Map<String, String> deleteUser(UserDto dto, UserHDtoPK pk, String h_code_id) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(h_code_id != null) {  // 취미가 있으면 
				pk.setH_code_id(h_code_id);
				// 취미 삭제
				ptService.deleteUserHobby(pk);
			}
			// 사용자 삭제
			ptService.deleteUser(dto);
		
		map.put("msg", "success");
		map.put("msg2", "삭제되었습니다.");
		} catch (Exception e) {
			map.put("msg", "fail");
			e.printStackTrace();
		}
		return map;
	}
	
	// 아이디 중복체크
	@ResponseBody
	@PostMapping("/idcheck")
	Map<String, String> idCheck(String userId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			int result = ptService.idCheck(userId);
			System.out.println("중복된 아이디 : "+ result);
			if(result == 0) {
				map.put("msg", "success");
				map.put("use", "사용 가능한 아이디입니다.");
			} else {
				map.put("msg", "fail");
				map.put("use", "이미 사용중인 아이디입니다.");
			}
		} catch (Exception e) {
			map.put("msg", "fail");
			e.printStackTrace();
		}
		return map;
	}
}