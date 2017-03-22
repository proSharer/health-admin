package com.ktds.admin.user.vo;

import com.ktds.admin.authorization.vo.AuthorizationVO;

public class UserVO {

	private int index;

	public String userId;
	public String userName;
	public String userPassword;
	public String authorizationId;

	private AuthorizationVO authorizationVO;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getAuthorizationId() {
		return authorizationId;
	}

	public void setAuthorizationId(String authorizationId) {
		this.authorizationId = authorizationId;
	}

	public AuthorizationVO getAuthorizationVO() {

		if (authorizationVO == null) {
			authorizationVO = new AuthorizationVO();
		}
		return authorizationVO;
	}

	public void setAuthorizationVO(AuthorizationVO authorizationVO) {
		this.authorizationVO = authorizationVO;
	}

	public String getAuthrizationId() {
		return authorizationId;
	}

	public void setAuthrizationId(String authrizationId) {
		this.authorizationId = authrizationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
