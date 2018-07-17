package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import domain.ReplyVO;
import persistence.ReplyDAO;

public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO dao;	
		
	@Override
	public void addReply(ReplyVO vo) throws Exception {
		dao.create(vo);

	}

	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		
		return dao.list(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		dao.update(vo);
	}

	@Override
	public void removeReply(Integer rno) throws Exception {
		dao.delete(rno);
	}

}
