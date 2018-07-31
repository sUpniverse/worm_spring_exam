package org.supniverse.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supniverse.domain.BoardVO;
import org.supniverse.domain.Criteria;
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
	
	@GetMapping("/register")
	public void registerGET(BoardVO board, Model model) throws Exception {
		logger.info("register get......");	
	}
	
	@PostMapping("/register")
	public String registerPOST(BoardVO board, RedirectAttributes rttr) throws Exception {
		logger.info("register post.....");
		logger.info(board.toString());
		
		service.regist(board);
		
		rttr.addFlashAttribute("msg", "success");
		
//		return "/board/success";
		return "redirect:/sboard/list";
	}
	
	
	@GetMapping("/readPage")
	public void read(@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		model.addAttribute("board",service.read(bno));
	}
	
	@PostMapping("/removePage")
	public String remove(@RequestParam("bno") int bno, SearchCriteria cri,RedirectAttributes rttr) throws Exception {
		service.remove(bno);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addFlashAttribute("msg","SUCCESS");
		
		return "redirect:/sboard/list";		
	}	
	
	
	@GetMapping("/modifyPage")
	public void modifyGET(@RequestParam("bno")int bno,@ModelAttribute("cri") SearchCriteria cri ,Model model) throws Exception {
		model.addAttribute("board",service.read(bno));	
	}
	
	@PostMapping("/modifyPage")
	public String modifyPOST(BoardVO board,SearchCriteria cri ,RedirectAttributes rttr) throws Exception {
		logger.info("mod post.................");
		
		service.modify(board);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		rttr.addFlashAttribute("msg","success");
		
		return "redirect:/sboard/list";
	}	

}
