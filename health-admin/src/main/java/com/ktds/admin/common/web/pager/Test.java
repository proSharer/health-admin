package com.ktds.admin.common.web.pager;

public class Test {

	public static void main(String[] args) {
		
		Pager pager = PagerFactory.getPager(Pager.ORACLE);
		pager.setPageNumber(17);
		pager.setTotalArticleCount(220);
		
		PageExplorer pageExplorer1 = new ListPageExplorer(pager);
		String page1 = pageExplorer1.getPagingList("pageNo", "[@]", "�씠�쟾", "�떎�쓬", "form");
		System.out.println(page1);
		
		PageExplorer pageExplorer2 = new ClassicPageExplorer(pager);
		String page2 = pageExplorer2.getPagingList("pageNo", "[@]", "�씠�쟾", "�떎�쓬", "form");
		System.out.println(page2);
		
	}
	
}
