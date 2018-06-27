package org.supniverse.persistence;

import org.supniverse.domain.MemberVO;

public interface MemberDAO {
	
	public String getTime();
	
	public void insertMember(MemberVO vo);
}
	