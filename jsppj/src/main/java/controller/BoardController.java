package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import domain.BoardVo;
import domain.Criteria;
import domain.PageMaker;
import domain.SearchCriteria;
import service.BoardDao;
import service.MemberDao;

@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("넘어오나요?");
		
		//한글깨짐방지
		request.setCharacterEncoding("UTF-8");   // 넘겨받는 값의 내용이(한글) 깨져서 나올때. 해결 방법. 1.
		response.setContentType("text/html;charset=UTF-8"); //넘겨받는 값의 내용이(한글) 깨져서 나올때. 해결 방법. 2.
		
		//파일
		//String filePath="D:\\dev0817\\eclipse-workspace\\uploadFiles";
		String filePath="D:\\dev0817\\eclipse-workspace\\jsppj\\src\\main\\webapp\\image\\";
		int fileSize = 15*1024*1024; //15M
		String fileEncoding = "UTF-8";
		
		String uri = request.getRequestURI(); //전체 가상 주소 경로를 받는다.
		String pjName = request.getContextPath(); //프로젝트 이름 경로를 추출한다.
		int jari = pjName.length();				//프로젝트 이름 자릿수를 추출한다.
		String str = uri.substring(jari);      //프로젝트 이름을 제외한 가상주소를 추출한다.
		
		System.out.println("Board 가상경로 주소는?"+str);
		
		//보드리스트 만들기
		if(str.equals("/board/boardList.do")) {
		//1.페이지 번호를 넘겨준다
		String page = request.getParameter("page");
		if(page ==null) {
			page ="1";
		}
		
		String searchType = request.getParameter("searchType"); //검색기능 선택창
		if(searchType ==null) {
			searchType="subject";
		}
		String keyword = request.getParameter("keyword"); //검색기능 키워드입력창
		if(keyword ==null) {
			keyword ="";
		}
		
		//페이징의 기준이 되는 객체를 생성하여 설정값을 정의한다
		SearchCriteria scri = new SearchCriteria();	//객체 생성
		scri.setPage( Integer.parseInt(page) );	//넘어온 현재 페이지번호를 담는다
		scri.setSearchType(searchType);
		scri.setKeyword(keyword);
		
		
		BoardDao bd = new BoardDao();	//객체 생성
		ArrayList<BoardVo> alist = bd.boardList(scri);	//cri 객체를 매개변수로 넘긴다
		int cnt = bd.boardTotal(scri);	//전체 게시물 수를 구한다
		
		//네비게이션에 필요한 설정값을 담는 객체를 생성하여  cri객체와 전체개수를 담는다
		PageMaker pm = new PageMaker();
		pm.setScri(scri);
		pm.setTotalCount(cnt);
		
		request.setAttribute("alist", alist);
		request.setAttribute("pm", pm);
		
		String path= "/boardList.jsp";  
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}else if(str.equals("/board/boardWrite.do")) {
		
		String path= "/boardWrite.jsp";  
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
	}else if(str.equals("/board/boardWriteAction.do")) {
		
		DefaultFileRenamePolicy reNamePolicy = new DefaultFileRenamePolicy(); //파일이름중복정책
		
		MultipartRequest multiRequest = 
				new MultipartRequest(request, filePath,fileSize,fileEncoding,reNamePolicy);
		
		//입력값을 넘겨준다
		String subject = multiRequest.getParameter("subject");
		String contents = multiRequest.getParameter("contents");
		String writer = multiRequest.getParameter("writer");
		
		//열거자에 저장될 파일이름을 담는다
		Enumeration files = multiRequest.getFileNames();
		//열거자에 담긴 각 파일을 옮겨 담는다
		String file = (String)files.nextElement();
		//저장되는 이름을 추출
		String fileName = multiRequest.getFilesystemName(file);
		//원래 파일 이름 추출
		String orginFileName = multiRequest.getOriginalFileName(file);		
				
		//ip를 추출한다
			String ip =	InetAddress.getLocalHost().getHostAddress();          //ip를 추출하는 클래스.메소드().메소드();
		
		int midx = 1; //midx는 sql에서 있는 값을 써야해서 그중에 하나를 썼음(예시로)
			
		BoardDao bd = new BoardDao();
		int value = bd.boardInsert(subject,contents,writer,ip,midx,fileName);
		
		if(value == 1) { //입력이 성공하면 출력되는 값은 1.  실패하면 출력되는 값은 0 이기 때문에 이 조건은 성공했을 때 조건임.
			
		String path = request.getContextPath()+"/board/boardList.do"; //send로 이동시 외부경로 주소씀
		response.sendRedirect(path);
		}else { //입력 실패
			String path = request.getContextPath()+"/board/boardWrite.do"; //send로 이동시 외부경로 주소씀
			response.sendRedirect(path);
		}
		}else if(str.equals("/board/boardContents.do")) {
			String bidx = request.getParameter("bidx");
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(Integer.parseInt(bidx));
			
			request.setAttribute("bv",bv);
			
			
			String path= "/boardContents.jsp";  
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
			
		}else if(str.equals("/board/boardModify.do")) {
			
			String bidx = request.getParameter("bidx");
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(Integer.parseInt(bidx));
			
			request.setAttribute("bv",bv);
									
			String path= "/boardModify.jsp";  
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		}else if (str.equals("/board/boardModifyAction.do")) {
			
			String bidx = request.getParameter("bidx");
			String subject = request.getParameter("subject");
			String contents = request.getParameter("contents");
			String writer = request.getParameter("writer");
			
			BoardDao bd = new BoardDao();
			int value = bd.boardUpdate(subject, contents,writer, Integer.parseInt(bidx));
			
			if(value == 1) {  //수정성공시				
				String path = request.getContextPath()+"/board/boardContents.do?bidx="+bidx;
				response.sendRedirect(path);
				}else { //수정 실패시
					String path = request.getContextPath()+"/board/boardModify.do?bidx"+bidx;
					response.sendRedirect(path);
				}
		}
		
		//삭제하기 작성
		else if (str.equals("/board/boardDelete.do")) { 
			//가상경로로 넘어옴
			String bidx = request.getParameter("bidx");
			
			//request객체에 bidx이름으로 bidx값을 담아서 jsp 화면에 공유한다
			  request.setAttribute("bidx",bidx);

//			BoardDao bd = new BoardDao();
//			BoardVo bv = bd.boardSelectOne(Integer.parseInt(bidx));
//			
//			request.setAttribute("bv",bv);
			  
			 // 내부이동 공유
			//삭제화면을 보여줌 ( : 포워드방식)
			String path = "/boardDelete.jsp"; //
			RequestDispatcher rd = request.getRequestDispatcher(path); //내부적으로 페이지를 이동시키는 RequestDispatcher 클래스
			rd.forward(request, response); //forward할때 쓰는 일종의 공식, request로 담아서 response시킴, 내부 이동
		}else if (str.equals("/board/boardDeleteAction.do")) {
			String bidx = request.getParameter("bidx");
			
			BoardDao bd = new BoardDao();
			int value = bd.boardDelete( Integer.parseInt(bidx)   );
				
				if(value == 1) {  //삭제성공시				
					String path = request.getContextPath()+"/board/boardList.do";
					response.sendRedirect(path);
					}else { //삭제 실패시
						String path = request.getContextPath()+"/board/boardContents.do?bidx="+bidx;
						response.sendRedirect(path);
					}
			
				//답변하기
		}else if (str.equals("/board/boardReply.do")) {
			String bidx = request.getParameter("bidx");
			int bidx2 = Integer.parseInt(bidx); // 숫자형 변환
			String originbidx = request.getParameter("originbidx");
			int originbidx2 = Integer.parseInt(originbidx); 			
			String depth = request.getParameter("depth");
			int depth2 = Integer.parseInt(depth); 			
			String level_ = request.getParameter("level_");
			int level_2 = Integer.parseInt(level_); 
			
			BoardVo bv = new BoardVo();
			bv.setBidx(bidx2);
			bv.setOriginbidx(originbidx2);
			bv.setDepth(depth2);
			bv.setLevel_(level_2);
			
			request.setAttribute("bv", bv);
			
			
			String path = "/boardReply.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response); 
			
		}else if (str.equals("/board/boardReplyAction.do")) { //equals라는 메소드 
			//매개변수 7개를 Action할때 값을 변수에 담고 넘겨준다
			String bidx = request.getParameter("bidx");
			int bidx2 = Integer.parseInt(bidx); // String으로 받아온 값들을 숫자형으로 변환하여 담는다
			String originbidx = request.getParameter("originbidx");
			int originbidx2 = Integer.parseInt(originbidx); 			
			String depth = request.getParameter("depth");
			int depth2 = Integer.parseInt(depth); 			
			String level_ = request.getParameter("level_");
			int level_2 = Integer.parseInt(level_); 
			
			String subject = request.getParameter("subject");
			String contents = request.getParameter("contents");
			String writer = request.getParameter("writer");
			
			BoardVo bv = new BoardVo(); //객체 생성
			bv.setBidx(bidx2);	//생성한 객체에 값들을 담는다
			bv.setOriginbidx(originbidx2);
			bv.setDepth(depth2);
			bv.setLevel_(level_2);
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setMidx(1);
			
			
			BoardDao bd = new BoardDao();
			int value = bd.boardReply(bv); //위에서 값을 담은 변수를 bd 객체에 담아 value에 대입
			
			
			
			
			if(value == 1) {  //답변성공시								Action이니까 sendRedirect로 보내는거
				String path = request.getContextPath()+"/board/boardList.do";  //send로 이동시 외부경로 주소씀
				response.sendRedirect(path);
				}else { //답변 실패시
					String path = request.getContextPath()+"/board/boardContents.do?bidx="+bidx;  //send로 이동시 외부경로 주소씀
					response.sendRedirect(path);
				}
			
		// 파일 다운로드
		}else if(str.equals("/board/fileDownload.do")) {
			//파일이름을 넘겨받기
			String filename = request.getParameter("filename");
			//파일의 전체경로
			String fullFilePath = filePath + "/"+ filename;
			
			//실제경로로 인식한다
			Path source = Paths.get(fullFilePath); //실제경로를 가져와서 소스를 패스화 시킴 ???
			String mimeType = Files.probeContentType(source); //서버가 인식하는 타입 : mimeType	 //파일형식 추출
			
			response.setContentType(mimeType); //response객체에 파일형식을 담고
			
			//한글 이름은 깨지지 않게
			String filenameEncoding = new String(filename.getBytes("UTF-8"));
			//헤더정보에 한글 이름담기 ???
			response.setHeader("Content-Disposition", "attachment;fileNAME="+filenameEncoding);
			//해당위치에 있는 파일을 읽어들인다
			FileInputStream fileInputStream = new FileInputStream(fullFilePath);
			//읽어들인 파일을 쓰기
			ServletOutputStream so = response.getOutputStream();
			
			//4바이트 크기로
			byte[] b = new byte[4096];
			int read = 0;
			while ((read = fileInputStream.read(b,0,b.length)) != -1) {
				so.write(b, 0, read);				//읽어들인 파일을 쓰기복사
			}
			
			so.flush();
			so.close();
			
			
			
		}
		
			
}
	
	
	
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
