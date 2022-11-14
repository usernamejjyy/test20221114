package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FrontController")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//�ѱ۱�������
		request.setCharacterEncoding("UTF-8");   // �Ѱܹ޴� ���� ������(�ѱ�) ������ ���ö�. �ذ� ���. 1.
		response.setContentType("text/html;charset=UTF-8"); //�Ѱܹ޴� ���� ������(�ѱ�) ������ ���ö�. �ذ� ���. 2.
		
		
		String uri = request.getRequestURI(); //��ü ���� �ּ� ��θ� �޴´�.
		String pjName = request.getContextPath(); //������Ʈ �̸� ��θ� �����Ѵ�.
		int jari = pjName.length();				//������Ʈ �̸� �ڸ����� �����Ѵ�.
		String str = uri.substring(jari);      //������Ʈ �̸��� ������ �����ּҸ� �����Ѵ�.
		
		//��) /board/boardWrite.do
		System.out.println("FrontController ������ �ּҴ�?"+str);
		
		String[] fileObj = str.split("/");	//split() : Ư�����ڸ� �������� ���ڿ��� �迭�� ����
		if (fileObj[1].equals("board")) { //ù��° ���� board�̸�
			BoardController bc = new BoardController();
			bc.doGet(request, response);	//Boardcontroller���� ����
		
		}else if (fileObj[1].equals("member")) {
			MemberController mc = new MemberController();
			mc.doGet(request,response);
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
