package com.osf.test.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private List<Map<String,String>> getTestList(){
		List<Map<String,String>> testList = new ArrayList<>();
		for(int i=1;i<=3;i++) {
			Map<String,String> test = new HashMap<>();
			test.put("name", "이름" + i);
			test.put("age", i+"");
			testList.add(test);
		}
		return testList;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		if("/test/c".equals(uri)) {
			request.setAttribute("test", getTestList()); 
		}
		RequestDispatcher rd = request.getRequestDispatcher("/views" + uri + ".jsp");
		List<String> list = new ArrayList<>();
		list.add("정말");
		list.add("너무");
		request.setAttribute("list", list);
		Map<String,String> map = new HashMap<>();
		map.put("name", "채희주");
		map.put("특기", "사기");
		map.put("직업", "사기꾼");
		request.setAttribute("map", map);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
