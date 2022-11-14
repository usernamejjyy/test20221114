package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberDao;

@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");   // 넘겨받는 값의 내용이(한글) 깨져서 나올때. 해결 방법. 1.
		response.setContentType("text/html;charset=UTF-8"); //넘겨받는 값의 내용이(한글) 깨져서 나올때. 해결 방법. 2.
		
		
		String uri = request.getRequestURI(); //전체 가상 주소 경로를 받는다.
		String pjName = request.getContextPath(); //프로젝트 이름 경로를 추출한다.
		int jari = pjName.length();				//프로젝트 이름 자릿수를 추출한다.
		String str = uri.substring(jari);      //프로젝트 이름을 제외한 가상주소를 추출한다.
		
		System.out.println("member 가상경로 주소는?"+str);
		
		if(str.equals("/member/memberInputAction.do")) {			
			
			String memberId = request.getParameter("memberId"); //(넘겨받을 때는 String 타입으로만 사용가능 하다.)  
															//request.getParameter("memberId") request라는 객체를 불러서 getparameter메소드를 호출하고(인풋테그 네임)을 쓴다.
			//1.값을 넘겨받는다.							//데이터를 넘겨받는건 동적인 것이기 때문에 jsp/java를 사용한다.    
			String memberPwd = request.getParameter("memberPwd");
			String memberPwd2 = request.getParameter("memberPwd2");
			String memberName = request.getParameter("memberName");
			String memberBirth = request.getParameter("memberBirth");
			String memberGender = request.getParameter("memberGender");
			String memberArea = request.getParameter("memberArea");
			
			//2.처리한다.			
			MemberDao md = new MemberDao();
			int value = md.memberInsert(memberId,memberPwd,memberName,memberBirth,memberGender,memberArea);
						
			//3. 이동한다.			
			if(value ==1){
				response.sendRedirect(request.getContextPath()+"/index.jsp"); 
			}else{
				response.sendRedirect(request.getContextPath()+"/memberInput.jsp"); 
			}
			}else if(str.equals("/member/memberList.do")) {
				System.out.println("memberList입니다.");
				
				String path= "/memberList.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(path);
				rd.forward(request,response);
				
			}else if(str.equals("/member/memberInput.do")) {
				String path= "/memberInput.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(path);
				rd.forward(request, response);                              
			}
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
