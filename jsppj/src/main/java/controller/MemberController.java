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
	
		request.setCharacterEncoding("UTF-8");   // �Ѱܹ޴� ���� ������(�ѱ�) ������ ���ö�. �ذ� ���. 1.
		response.setContentType("text/html;charset=UTF-8"); //�Ѱܹ޴� ���� ������(�ѱ�) ������ ���ö�. �ذ� ���. 2.
		
		
		String uri = request.getRequestURI(); //��ü ���� �ּ� ��θ� �޴´�.
		String pjName = request.getContextPath(); //������Ʈ �̸� ��θ� �����Ѵ�.
		int jari = pjName.length();				//������Ʈ �̸� �ڸ����� �����Ѵ�.
		String str = uri.substring(jari);      //������Ʈ �̸��� ������ �����ּҸ� �����Ѵ�.
		
		System.out.println("member ������ �ּҴ�?"+str);
		
		if(str.equals("/member/memberInputAction.do")) {			
			
			String memberId = request.getParameter("memberId"); //(�Ѱܹ��� ���� String Ÿ�����θ� ��밡�� �ϴ�.)  
															//request.getParameter("memberId") request��� ��ü�� �ҷ��� getparameter�޼ҵ带 ȣ���ϰ�(��ǲ�ױ� ����)�� ����.
			//1.���� �Ѱܹ޴´�.							//�����͸� �Ѱܹ޴°� ������ ���̱� ������ jsp/java�� ����Ѵ�.    
			String memberPwd = request.getParameter("memberPwd");
			String memberPwd2 = request.getParameter("memberPwd2");
			String memberName = request.getParameter("memberName");
			String memberBirth = request.getParameter("memberBirth");
			String memberGender = request.getParameter("memberGender");
			String memberArea = request.getParameter("memberArea");
			
			//2.ó���Ѵ�.			
			MemberDao md = new MemberDao();
			int value = md.memberInsert(memberId,memberPwd,memberName,memberBirth,memberGender,memberArea);
						
			//3. �̵��Ѵ�.			
			if(value ==1){
				response.sendRedirect(request.getContextPath()+"/index.jsp"); 
			}else{
				response.sendRedirect(request.getContextPath()+"/memberInput.jsp"); 
			}
			}else if(str.equals("/member/memberList.do")) {
				System.out.println("memberList�Դϴ�.");
				
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
