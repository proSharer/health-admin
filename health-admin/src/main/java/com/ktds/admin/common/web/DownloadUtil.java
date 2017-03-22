package com.ktds.admin.common.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * �뙆�씪 �떎�슫濡쒕뱶瑜� �쑀�슜�븯寃� �븿.
 * Internet Explorer, Mozilia 紐⑤몢 �샇�솚
 * @author Minchang Jang (mc.jang@hucloud.co.kr)
 *
 */
public class DownloadUtil {

	private String uploadPath;
	
	/**
	 * �뙆�씪�씠 �뾽濡쒕뱶 �릺�뼱 �엳�뒗 寃쎈줈瑜� 媛��졇�샂.
	 * @return
	 */
	public String getUploadPath() {
		return uploadPath;
	}
	/**
	 * �뙆�씪�씠 �뾽濡쒕뱶 �릺�뼱 �엳�뒗 寃쎈줈瑜� 吏��젙.
	 * @param uploadPath
	 */
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
	private static DownloadUtil downloadUtil;
	
	private DownloadUtil() {}
	
	/**
	 * DownloadUtil �씤�뒪�꽩�뒪瑜� 媛��졇�샂.
	 * @param filePath �떎�슫濡쒕뱶 �븯�젮�뒗 �뙆�씪�씠 �쐞移섑븳 �꽌踰꾩긽�쓽 臾쇰━�쟻�씤 �젅�� 寃쎈줈
	 * @return
	 */
	public static DownloadUtil getInstance(String filePath) {
		
		if ( downloadUtil == null ) {
			downloadUtil = new DownloadUtil();
		}
		
		downloadUtil.setUploadPath(filePath);
		
		return downloadUtil;
	}
	
	/**
	 * �뙆�씪�쓣 �떎�슫濡쒕뱶 �븿.
	 * @param request
	 * @param response
	 * @param realFileName �꽌踰꾩뿉 議댁옱�븯�뒗 臾쇰━�쟻�씤 �뙆�씪�쓽 �씠由�
	 * @param displayFileName �떎�슫濡쒕뱶 �븷 �븣 �궗�슜�옄�뿉寃� 蹂댁뿬吏� �뙆�씪�쓽 �씠由�
	 * @throws UnsupportedEncodingException
	 */
	public void download(HttpServletRequest request,
						HttpServletResponse response,
						String realFileName,
						String displayFileName) throws UnsupportedEncodingException {
		
		File downloadFile = new File(this.getUploadPath() + File.separator + realFileName);
		
		response.setContentType("application/download; charset=utf-8");
		response.setContentLength( (int) downloadFile.length());
		
		// �궗�슜�옄�쓽 釉뚮씪�슦�졇 �젙蹂대�� 媛��졇�삩�떎.
		String userAgent = request.getHeader("User-Agent");
		// �궗�슜�옄�쓽 釉뚮씪�슦��媛� MicroSoft Internet Explorer �씤吏� �솗�씤�븳�떎.
		boolean internetExplorer = userAgent.indexOf("MSIE") > -1;
		if( !internetExplorer ) {
			internetExplorer = userAgent.indexOf("Gecko") > -1;
		}
		
		// �떎�슫濡쒕뱶�븷 �뙆�씪�쓽 �씠由꾩쓣 釉뚮씪�슦�졇蹂꾨줈 媛��졇�삩�떎.
		String fileName = new String(displayFileName.getBytes(), "UTF-8");
		if ( internetExplorer ) {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		}
		else {
			// File�쓽 �씠由꾩쓣 UTF-8 ���엯�뿉�꽌 ISO-8859-1 ���엯�쑝濡� 蹂�寃쏀븳�떎.
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		
		// 釉뚮씪�슦�졇媛� 諛쏆쓣 �뙆�씪�쓽 �씠由꾩쓣 response�뿉 �벑濡앺븳�떎.
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + fileName + "\";");
		// 釉뚮씪�슦�졇媛� �떎�슫濡쒕뱶 諛쏆� �썑 Binary �뙆�씪濡� �깮�꽦�븯�씪怨� 蹂대궦�떎.
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		FileInputStream fin = null;
		FileChannel inputChannel = null;
		WritableByteChannel outputChannel = null;
		
		try {
			fin = new FileInputStream(downloadFile);
			inputChannel = fin.getChannel();

			outputChannel = Channels.newChannel(response.getOutputStream());
			inputChannel.transferTo(0, fin.available(), outputChannel);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			try {
				if (outputChannel.isOpen())
					outputChannel.close();
			} catch (Exception e) {}
			try {
				if (inputChannel.isOpen())
					inputChannel.close();
			} catch (Exception e) {}
			try {
				if (fin != null)
					fin.close();
			} catch (Exception e) {}
		}
	}
	
}
