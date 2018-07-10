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
import org.supniverse.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
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
		return "redirect:/board/listAll";
	}
	
	@GetMapping("/listAll")
	public void listAll(Model model) throws Exception {
		logger.info("show all list....................");
		model.addAttribute("list",service.listAll());		
	}
	
	@GetMapping("/listPage")
	public void listPage(Model model,Criteria cri) throws Exception {
		logger.info("show all list....................");
		model.addAttribute("list",service.listCriteria(cri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCountCriteria(cri));		
		model.addAttribute("pageMaker", pageMaker);
		
	}
	
	@GetMapping("/read")
	public void read(@RequestParam("bno") Integer bno, Model model) throws Exception {
		model.addAttribute("board",service.read(bno));
	}
	
	@GetMapping("/readPage")
	public void read(@RequestParam("bno") int bno,@ModelAttribute("cri") Criteria cri ,Model model) throws Exception {
		model.addAttribute("board",service.read(bno));
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Integer bno, RedirectAttributes rttr) throws Exception {
		service.remove(bno);
		rttr.addFlashAttribute("msg","SUCCESS");
		return "redirect:/board/listAll";		
	}
	
	//Remove에서 @ModelAttribute를 사용하지 않은 이유 : redirect로 Model로 담은 객체가 쓸모 없기 떄문에.
	@PostMapping("/removePage")
	public String remove(@RequestParam("bno") int bno, Criteria cri,RedirectAttributes rttr) throws Exception {
		service.remove(bno);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addFlashAttribute("msg","SUCCESS");
		
		return "redirect:/board/listPage";		
	}
	
	
	@GetMapping("/modify")
	public void modifyGET(@RequestParam("bno")int bno, Model model) throws Exception {
		model.addAttribute("board",service.read(bno));	
	}
	
	@GetMapping("/modifyPage")
	public void modifyGET(@RequestParam("bno")int bno,@ModelAttribute("cri") Criteria cri ,Model model) throws Exception {
		model.addAttribute("board",service.read(bno));	
	}
	
	@PostMapping("/modify")
	public String modifyPOST(BoardVO board, RedirectAttributes rttr) throws Exception {
		logger.info("mod post.................");
		
		service.modify(board);
		rttr.addFlashAttribute("msg","success");
		return "redirect:/board/listAll";
	}
	
	@PostMapping("/modifyPage")
	public String modifyPOST(BoardVO board,Criteria cri ,RedirectAttributes rttr) throws Exception {
		logger.info("mod post.................");
		
		service.modify(board);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addFlashAttribute("msg","success");
		
		return "redirect:/board/listPage";
	}	
	
	
}
