package com.ktds.admin.authorization.biz;

import java.util.List;

import com.ktds.admin.authorization.vo.AuthorizationSearchVO;
import com.ktds.admin.authorization.vo.AuthorizationVO;




public interface AuthorizationBiz {
	
	public List<AuthorizationVO> getAllAuthorizations(AuthorizationSearchVO authorizationSearchVO);

}
