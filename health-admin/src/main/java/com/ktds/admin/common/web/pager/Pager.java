package com.ktds.admin.common.web.pager;

public abstract class Pager {
	
	public static final boolean ORACLE = true;
	public static final boolean OTHER = false;
	
	private int totalArticleCount;

	protected int printArticle;
	int printPage;

	protected int startArticleNumber;
	protected int endArticleNumber;

	int totalPage;
	int totalGroup;

	int nowGroupNumber;

	int groupStartPage;

	int nextGroupPageNumber;
	int prevGroupPageNumber;

	protected int pageNo;
	
	/**
	 * Paging 媛앹껜瑜� �뼸�뼱�삩�떎.
	 * �븳 �럹�씠吏��떦 蹂댁뿬吏��뒗 寃뚯떆湲� �닔 10媛�
	 * �븳 �럹�씠吏��떦 蹂댁뿬吏��뒗 �럹�씠吏� �닔 10媛�
	 * 濡� 湲곕낯 �꽕�젙�맖.
	 */
	public Pager() {
		this.printArticle = 10;
		this.printPage = 10;
	}
	
	public Pager(int printArticle, int printPage) {
		this.printArticle = printArticle;
		this.printPage = printPage;
	}
	
	public void setPageNumber(int pageNumber) {
		setPageNumber(pageNumber + "");
	}
	
	/**
	 * �슂泥��맂 �럹�씠吏��쓽 踰덊샇瑜� �뼸�뼱�삩�떎.
	 * 1 �럹�씠吏��쓽 寃쎌슦 0�씠 �엯�젰�맂�떎.
	 * �븘臾닿쾬�룄 �엯�젰�븯吏� �븡�븯�떎硫� 0�쑝濡� 湲곕낯 �꽕�젙�맂�떎.
	 * @param pageNumber
	 */
	public void setPageNumber(String pageNumber) {
		this.pageNo = 0;
		try {
			this.pageNo = Integer.parseInt(pageNumber);
		} catch (NumberFormatException nfe) {
			this.pageNo = 0;
		}

		computeArticleNumbers();
		
		this.nowGroupNumber = this.pageNo / this.printPage;
		this.groupStartPage = (this.nowGroupNumber * this.printPage) + 1;

		this.nextGroupPageNumber = this.groupStartPage + this.printPage - 1;
		this.prevGroupPageNumber = this.groupStartPage - this.printPage - 1;
	}
	
	protected abstract void computeArticleNumbers();
	
	/**
	 * 議고쉶�븯�젮�뒗 議곌굔(Query)�쓽 珥� 寃뚯떆臾� �닔瑜� �젙�쓽�븳�떎.
	 * @param count
	 */
	public void setTotalArticleCount(int count) {
		this.totalArticleCount = count;

		this.totalPage = (int) Math.ceil((double) this.totalArticleCount
				/ this.printArticle);
		this.totalGroup = (int) Math.ceil((double) this.totalPage
				/ this.printPage);
	}
	
	/**
	 * 議고쉶�븯�젮�뒗 議곌굔(Query)�쓽 珥� 寃뚯떆臾� �닔瑜� 媛��졇�삩�떎.
	 * @return
	 */
	public int getTotalArticleCount() {
		return this.totalArticleCount;
	}

	/**
	 * Query�뿉�꽌 �궗�슜�맆 �깘�깋 �떆�옉 踰덊샇 
	 * Oracle�쓽 寃쎌슦 rownum�쓽 �떆�옉 踰덊샇
	 * @return
	 */
	public int getStartArticleNumber() {
		return this.startArticleNumber;
	}
	
	/**
	 * Query�뿉�꽌 �궗�슜�맆 �깘�깋 �떆�옉 踰덊샇瑜� �젙�쓽�븳�떎.
	 * @param startArticleNumber
	 */
	public void setStartArticleNumber(int startArticleNumber) {
		this.startArticleNumber = startArticleNumber;
	}
	
	/**
	 * Query�뿉�꽌 �궗�슜�맆 �깘�깋 �걹 踰덊샇瑜� �젙�쓽�븳�떎.
	 * @param endArticleNumber
	 */
	public abstract void setEndArticleNumber(int endArticleNumber);

	/**
	 * Query�뿉�꽌 �궗�슜�맆 �깘�깋 留덉�留� 踰덊샇
	 * Oracle�쓽 寃쎌슦 rownum�쓽 留덉�留� 踰덊샇
	 * @return
	 */
	public abstract int getEndArticleNumber();
	
}
