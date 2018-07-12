package org.supniverse.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.supniverse.domain.PageMaker;
import org.supniverse.domain.SearchCriteria;
import org.supniverse.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class searchBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(searchBoardController.class);
	
	@Inject
	private BoardService service;
	
	@GetMapping("/list")
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		logger.info(cri.toString());
		
//		model.addAttribute("list", service.listCriteria(cri));
		model.addAttribute("list", service.listSearchCriteria(cri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
//		pageMaker.setTotalCount(service.listCountCriteria(cri));
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("pageMaker",pageMaker);
		
	}

}
