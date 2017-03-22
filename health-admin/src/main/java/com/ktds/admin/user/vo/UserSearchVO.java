package com.ktds.admin.user.vo;

import com.ktds.admin.common.web.pager.Pager;
import com.ktds.admin.common.web.pager.PagerFactory;

public class UserSearchVO {
	
	private Pager pager;

	public Pager getPager() {
		if( pager == null) {
			pager = PagerFactory.getPager(Pager.ORACLE,50,10);
		}
		
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	
	
	

}
