package com.ktds.admin.common.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * JSP/Servlet 湲곕컲�쓽 Project�뿉�꽌 �뙆�씪�쓣 Upload �븷 �닔 �엳�뒗 Utility
 * commons-fileupload瑜� �궗�슜�븿.
 * @author Minchang Jang (mc.jang@hucloud.co.kr)
 *
 */
public class MultipartHttpServletRequest {

	private HttpServletRequest request;
	private List<FileItem> items;
	
	/**
	 * �씪諛섏쟻�씤 HttpServletReqeust瑜� MultipartHttpServletRequest濡� 蹂��솚�븳�떎.
	 * @param request
	 */
	public MultipartHttpServletRequest(HttpServletRequest request) {
		this.request = request;
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = request.getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8"); 
		
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * request�뿉�꽌 Parameter瑜� 媛��졇�삩�떎.
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		for ( FileItem fileItem : items ) {
			if ( fileItem.getFieldName().equals(name) )  {
				try {
					return fileItem.getString("UTF-8");
				} catch (UnsupportedEncodingException e) {
					return fileItem.getString();
				}
			}
		}
		return null;
	}
	
	/**
	 * request�뿉�꽌 Parameter瑜� 媛��졇�삩�떎.
	 * @param name
	 * @return
	 */
	public List<String> getParameterValue(String name) {
		List<String> values = new ArrayList<String>();
		
		for ( FileItem fileItem : items ) {
			if ( fileItem.getFieldName().equals(name) )  {
				try {
					values.add(fileItem.getString("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					values.add(fileItem.getString());
				}
			}
		}
		return values;
	}
	
	/**
	 * Session�쓣 �뼸�뼱�삩�떎.
	 * @return
	 */
	public HttpSession getSession() {
		return request.getSession();
	}
	
	/**
	 * RequestDispatcher瑜� �뼸�뼱�삩�떎.
	 * @param jspPage
	 * @return
	 */
	public RequestDispatcher getRequestDispatcher(String jspPage) {
		return request.getRequestDispatcher(jspPage);
	}
	
	/**
	 * �뾽濡쒕뱶�븳 File Parameter瑜� �뼸�뼱�삩�떎.
	 * @param name
	 * @return
	 */
	public MultipartFile getFile(String name) {
		MultipartFile file = new MultipartFile();
		
		for ( FileItem fileItem : items ) {
			if ( fileItem.getFieldName().equals(name) )  {
				file.setFileName(fileItem.getName());
				file.setFileSize(fileItem.getSize());
				file.setContentType(fileItem.getContentType());
				file.setFileItem(fileItem);
				
				return file;
			}
		}
		
		return null;
	}
	
	/**
	 * Upload �븳 �뙆�씪�씠 �엫�떆�쟻�쑝濡� ���옣�맆 �겢�옒�뒪
	 * @author Minchang Jang (mc.jang@hucloud.co.kr)
	 *
	 */
	public class MultipartFile {
		private String fileName;
		private long fileSize;
		private String contentType;
		private FileItem fileItem;
		
		/**
		 * �뙆�씪�쓽 �씠由꾩쓣 媛��졇�삩�떎.
		 * @return
		 */
		public String getFileName() {
			return fileName;
		}
		/**
		 * �뙆�씪�쓽 �씠由꾩쓣 �꽕�젙�븳�떎.
		 * @param fileName
		 */
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * �뙆�씪�쓽 �겕湲곕�� 媛��졇�삩�떎.
		 * @return
		 */
		public long getFileSize() {
			return fileSize;
		}
		/**
		 * �뙆�씪�쓽 �겕湲곕�� �꽕�젙�븳�떎.
		 * @param fileSize
		 */
		public void setFileSize(long fileSize) {
			this.fileSize = fileSize;
		}

		/**
		 * �뙆�씪�쓽 Type�쓣 媛��졇�삩�떎.
		 * @return
		 */
		public String getContentType() {
			return contentType;
		}
		/**
		 * �뙆�씪�쓽 Type�쓣 �꽕�젙�븳�떎.
		 * @param contentType
		 */
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		
		/**
		 * Upload �븳 File�씠 �엫�떆�쟻�쑝濡� ���옣�릺�뒗 FileItem(commons-fileupload �쟾�슜)
		 * @param fileItem
		 */
		public void setFileItem(FileItem fileItem) {
			this.fileItem = fileItem;
		}
		
		/**
		 * Upload �븳 �뙆�씪�쓣 吏��젙�븳 �옣�냼�뿉 ���옣�븳�떎.
		 * @param dest
		 * @return
		 */
		public File write(String dest) {
			File file = new File(dest);
			try {
				fileItem.write(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return file;
		}
		
	}
	
}


