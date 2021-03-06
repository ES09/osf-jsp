package com.osf.test.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.osf.test.service.PBoardService2;
import com.osf.test.service.impl.PBoardServiceImpl2;
import com.osf.test.vo.PhotoBoardVO;

public class PBoardServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static String savePath = "D:\\study\\workspace\\osf-jsp\\WebContent\\upload";
	
	private PBoardService2 pbs = new PBoardServiceImpl2();
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String uri = request.getRequestURI();
    	uri = uri.replace("/pboard2/", "");
    	if("list".equals(uri)) {
    		request.setAttribute("pBoardList", pbs.selectPBoardList());
    		RequestDispatcher rd = request.getRequestDispatcher("/views/photo-board2/list.jsp");
    		rd.forward(request, response);
    		return;
    	} else {
    		try {
    			int pbNum = Integer.parseInt(uri);
    			request.setAttribute("pBoard", pbs.selectPBoard(pbNum));
        		RequestDispatcher rd = request.getRequestDispatcher("/views/photo-board2/view.jsp");
        		rd.forward(request, response);
        		return;
    		} catch(NumberFormatException e) {
    			throw new ServletException("상세조회는 번호 입력 후 가능 합니다.");
    		}
    	}
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.replace("/pboard2/", "");
		if("insert".equals(uri)) {
			DiskFileItemFactory dfiFactory = new DiskFileItemFactory(); // tmp디레토리를 얼마나 쓸건지
			String tmpPath = System.getProperty("java.io.tmpdir");
			File tmpFile = new File(tmpPath);
			dfiFactory.setRepository(tmpFile);
			dfiFactory.setSizeThreshold(10*1024*1024); // 이 용량을 넘으면 tmp 파일 쓴다.
			
			ServletFileUpload sfu = new ServletFileUpload(dfiFactory);
			sfu.setSizeMax(20*1024*1024); // size - 파일 하나
			sfu.setFileSizeMax(20*1024*1024); // file size - 전체
			
			try {
				List<FileItem> fileList = sfu.parseRequest(request); // insert의 제목, 내용, 이미지를 넣는 다는 것
				Map<String,String> pBoard = new HashMap<>();
				for(int i=0;i<fileList.size();i++) {
					FileItem fi = fileList.get(i);
					if(fi.isFormField()) {
						pBoard.put(fi.getFieldName(), fi.getString("utf-8"));
					} else {
						String rFileName = fi.getName();
						String extName = rFileName.substring(rFileName.lastIndexOf(".")+1);
						String fileName = System.currentTimeMillis()+"";
						File saveFile = new File(savePath + "\\" + fileName + "." + extName);
						pBoard.put("pb_real_path", rFileName);
						pBoard.put("pb_file_path", "/upload/" + fileName + "." + extName);
						fi.write(saveFile);
					}
				}
				PhotoBoardVO pb = new PhotoBoardVO();
				pb.setPbTitle(pBoard.get("pb_title"));
				pb.setPbContent(pBoard.get("pb_content"));
				pb.setPbRealPath(pBoard.get("pb_real_path"));
				pb.setPbFilePath(pBoard.get("pb_file_path"));
				if(pbs.insertPBoard(pb)==1) {
					request.setAttribute("msg", "성공");
					request.setAttribute("url", "/views/photo-board2/insert.jsp");
					RequestDispatcher rd = request.getRequestDispatcher("/views/result.jsp");
					rd.forward(request, response);
					return;
				} else {
					
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if("update".equals(uri)) {
			
		} else if("delete".equals(uri)) {
			
		} else {
			
		}
	}
	
	public static void main(String[] args) {
		
	}
}
