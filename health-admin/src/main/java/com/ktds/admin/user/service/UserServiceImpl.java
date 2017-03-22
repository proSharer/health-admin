package com.ktds.admin.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktds.admin.authorization.biz.AuthorizationBiz;
import com.ktds.admin.authorization.biz.AuthorizationBizImpl;
import com.ktds.admin.authorization.vo.AuthorizationSearchVO;
import com.ktds.admin.user.biz.UserBiz;
import com.ktds.admin.user.biz.UserBizImpl;
import com.ktds.admin.user.vo.UserSearchVO;
import com.ktds.admin.user.vo.UserVO;


public class UserServiceImpl implements UserService {

	private UserBiz userBiz;
	private AuthorizationBiz authorizationBiz;

	public UserServiceImpl() {
		userBiz = new UserBizImpl();
		authorizationBiz = new AuthorizationBizImpl();
	}

	@Override
	public List<UserVO> getAllUsers(UserSearchVO userSearchVO) {
		return userBiz.getAllUsers(userSearchVO);
	}

	@Override
	public UserVO getOneUser(UserVO userVO) {
		return userBiz.getOneUser(userVO);
	}

	@Override
	public UserVO getOneUser(String userId) {
		return userBiz.getOneUser(userId);
	}

	@Override
	public boolean registNewUser(UserVO newUserVO) {
		return userBiz.registNewUser(newUserVO);
	}

	@Override
	public boolean updateUser(UserVO userVO) {

		UserVO tempUserVO = getOneUser(userVO.getUserId());

		// 권한 정보를 수정했다면..
		if (userVO.getAuthrizationId() != null && userVO.getAuthrizationId().length() > 0) {
			tempUserVO.setAuthrizationId(userVO.getAuthrizationId());
		}

		if (userVO.getUserPassword() != null && userVO.getUserPassword().length() > 0) {
			tempUserVO.setUserPassword(userVO.getUserPassword());
		}

		return userBiz.updateUser(tempUserVO);
	}

	@Override
	public boolean deleteOneUser(String userId) {
		return userBiz.deleteOneUser(userId);
	}

	@Override
	public Map<String, Object> getOneUserWithAuthorizations(String userId) {

		// 페이징. 페이지 넘버 1부터.
		AuthorizationSearchVO authorizationSearchVO = new AuthorizationSearchVO();
		authorizationSearchVO.getPager().setPageNumber(0);

		Map<String, Object> user = new HashMap<String, Object>();
		user.put("user", userBiz.getOneUser(userId));
		user.put("authorizations", authorizationBiz.getAllAuthorizations(authorizationSearchVO));

		return user;
	}

	@Override
	public boolean changeUser(String[] checkedUsers, String beforeAuth, String afterAuth) {
		return userBiz.changeUser(checkedUsers, beforeAuth, afterAuth);
	}

	

}
