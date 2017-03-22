package com.ktds.admin.user.dao;

import java.util.List;

import com.ktds.admin.user.vo.UserSearchVO;
import com.ktds.admin.user.vo.UserVO;


public interface UserDao {
	
	public int insertNewUser(UserVO newUserVO);
	public List<UserVO> selectAllUser(UserSearchVO userSearchVO);
	
	/**
	 * 관리자 페이지에서 한명의 회원 정보를 보기 위한 메소드
	 * @param userId
	 * @return
	 */
	public UserVO selectOneUser(String userId);
	
	/**
	 * 로그인을 위한 메소드
	 * @param userVO
	 * @return
	 */
	public UserVO selectOneUser(UserVO userVO);
	
	public int updateUserInfo(UserVO userVO);
	public int deleteOneUser(String userId);
	public int selectAllUserCount(UserSearchVO userSearchVO);
	public int changeUser(String checkedUserId, String beforeAuthId, String afterAuthId);

}
