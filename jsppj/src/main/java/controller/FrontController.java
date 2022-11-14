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
		
		//한글깨짐방지
		request.setCharacterEncoding("UTF-8");   // 넘겨받는 값의 내용이(한글) 깨져서 나올때. 해결 방법. 1.
		response.setContentType("text/html;charset=UTF-8"); //넘겨받는 값의 내용이(한글) 깨져서 나올때. 해결 방법. 2.
		
		
		String uri = request.getRequestURI(); //전체 가상 주소 경로를 받는다.
		String pjName = request.getContextPath(); //프로젝트 이름 경로를 추출한다.
		int jari = pjName.length();				//프로젝트 이름 자릿수를 추출한다.
		String str = uri.substring(jari);      //프로젝트 이름을 제외한 가상주소를 추출한다.
		
		//예) /board/boardWrite.do
		System.out.println("FrontController 가상경로 주소는?"+str);
		
		String[] fileObj = str.split("/");	//split() : 특정문자를 기준으로 문자열을 배열에 저장
		if (fileObj[1].equals("board")) { //첫번째 값이 board이면
			BoardController bc = new BoardController();
			bc.doGet(request, response);	//Boardcontroller에서 반응
		
		}else if (fileObj[1].equals("member")) {
			MemberController mc = new MemberController();
			mc.doGet(request,response);
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
