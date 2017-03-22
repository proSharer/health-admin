package com.ktds.admin.user.service;

import java.util.List;
import java.util.Map;

import com.ktds.admin.user.vo.UserSearchVO;
import com.ktds.admin.user.vo.UserVO;


public interface UserService {
	
	public List<UserVO> getAllUsers(UserSearchVO userSearchVO);
	
	public UserVO getOneUser(UserVO userVO);
	
	public UserVO getOneUser(String userId);
	
	public boolean registNewUser(UserVO newUserVO);

	public boolean updateUser(UserVO userVO);
	
	public boolean deleteOneUser(String userId);

	public Map<String, Object> getOneUserWithAuthorizations(String userId);
	
	public boolean changeUser(String[] checkedUsers, String beforeAuth, String afterAuth);

}
