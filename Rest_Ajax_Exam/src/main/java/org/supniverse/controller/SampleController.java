package org.supniverse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supniverse.domain.SampleVO;

// RestController를 선언하면 모든 뷰 처리가 JSP가 아니라 것을 의 
// ResponseBody를 선언하지않아도 동일하게 적용
// 자동으로 JSON으로 처리된다.
@RestController 
@RequestMapping("/sample")
public class SampleController {
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}
	
	@GetMapping("/sendVO")
	public SampleVO sendVO() {
		SampleVO vo = new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setMno(123);
		
		return vo;
	}		
	
	@GetMapping("/sendList")
	public Map<Integer, SampleVO> sendList() {
		Map<Integer, SampleVO> map = new HashMap<>();
		
		for(int i = 0; i < 10; i++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("길동");
			vo.setLastName("홍");
			vo.setMno(i);
			map.put(i, vo);
		}
		
		return map;
	}
	
	@GetMapping("/sendErrorAuth")
	public ResponseEntity<Void> sendListAuth() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/sendErrorNot")
	public ResponseEntity<List<SampleVO>> sendListNot() {
		List<SampleVO> list = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("길동");
			vo.setLastName("홍");
			vo.setMno(i);
			list.add(vo);
		}
		
		return new ResponseEntity<>(list,HttpStatus.NOT_FOUND); 		
	}
}
