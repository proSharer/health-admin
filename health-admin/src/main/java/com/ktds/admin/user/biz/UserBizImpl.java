package com.ktds.admin.user.biz;

import java.util.ArrayList;
import java.util.List;

import com.ktds.admin.common.web.pager.Pager;
import com.ktds.admin.user.dao.UserDao;
import com.ktds.admin.user.dao.UserDaoImpl;
import com.ktds.admin.user.vo.UserSearchVO;
import com.ktds.admin.user.vo.UserVO;


public class UserBizImpl implements UserBiz{
	
	private UserDao userDao;
	
	public UserBizImpl(){
		userDao = new UserDaoImpl();
	}

	@Override
	public List<UserVO> getAllUsers(UserSearchVO userSearchVO) {
		
		// DB에 USR 테이블의 ROW 수를 카운트 하는 쿼리문의 결과 값을 리턴한다. 
		// List 호출 시에 가져 오는 이유는 전체 DB를 리스트로 보여줄때 제공하는 정보이기 때문.
		int userCount = userDao.selectAllUserCount(userSearchVO);
		
		Pager pager = userSearchVO.getPager();
		pager.setTotalArticleCount(userCount);
		
		if(userCount == 0){
			return new ArrayList<UserVO>();
		}
		
		return userDao.selectAllUser(userSearchVO);
	}

	@Override
	public UserVO getOneUser(UserVO userVO) {
		return userDao.selectOneUser(userVO);
	}

	@Override
	public UserVO getOneUser(String userId) {
		return userDao.selectOneUser(userId);
	}

	@Override
	public boolean registNewUser(UserVO newUserVO) {
		return userDao.insertNewUser(newUserVO) > 0;
	}

	@Override
	public boolean updateUser(UserVO userVO) {
		return userDao.updateUserInfo(userVO) > 0;
	}

	@Override
	public boolean deleteOneUser(String userId) {
		return userDao.deleteOneUser(userId) > 0;
	}

	@Override
	public boolean changeUser(String[] checkedUsers, String beforeAuth, String afterAuth) {
		
		if(checkedUsers != null && checkedUsers.length >0){
			int isSuccess = 0;
			
			for(int i =0; i < checkedUsers.length; i++) {
				isSuccess = userDao.changeUser(checkedUsers[i], beforeAuth, afterAuth);
			}
			return isSuccess > 0;
		}else{
			String checkedUserId = null;
			
			return userDao.changeUser(checkedUserId, beforeAuth, afterAuth) > 0;
		}
		
	}


}
