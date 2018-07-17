package persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import domain.ReplyVO;

public class ReplyDAOImpl implements ReplyDAO {

	@Autowired
	private SqlSession session;
	
	private static String namespace = "org.supniverse.mapper.ReplyMapper";
	
	@Override
	public List<ReplyVO> list(Integer bno) throws Exception {
		return session.selectList(namespace+".list",bno);
	}

	@Override
	public void create(ReplyVO vo) throws Exception {
		session.insert(namespace+".create",vo);
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		session.update(namespace+".update",vo);
	}

	@Override
	public void delete(Integer rno) throws Exception {
		session.delete(namespace+".delete",rno);
	}

}
