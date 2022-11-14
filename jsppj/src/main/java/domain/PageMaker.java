package domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PageMaker {

	private int displayPageNum = 10;		//		< 1 2 3 4 5 6 7 8 9 10 >
	private int startPage;		//페이징의 시작점
	private int endPage;		//페이징의 현재의 끝점
	private int totalCount;		//전체 게시물 수
	
	private boolean prev;		//이전버튼
	private boolean next;		//다음버튼
	
	private SearchCriteria scri;		//페이지 객체

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount; 
		calcData();	// 전체 게시물 수를 가져올때 그에 따른 페이징메소드 (계산식)
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public SearchCriteria getScri() {
		return scri;
	}

	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}
	
	public void calcData() { // calcData 메소드 생성 ( 페이지 버튼에 관한)
		//시작페이지 번호
		//끝페이지 번호
		//prev next 버튼을 보여준다
		
		//끝페이지 번호
		endPage = (int)(Math.ceil(scri.getPage()/(double)displayPageNum)*displayPageNum); // getPage:현재페이지(n) 	//		n/10이 되게끔 => 값이 소수점이 되게 double형 사용
								//Math.ceil : 올림처리		// n/10 올림 처리 => 올림처리해서 나온 정수값*displayPageNum(10) = endPage 
		//시작페이지 번호
		startPage = (endPage - displayPageNum)+1;
		//전체 페이지 수
		int tempEndPage = (int)(Math.ceil(totalCount/(double)scri.getPerPageNum())); //	"전체게시물수 /화면게시물수"의 올림처리한 정수값 = 필요한 마지막 페이지(tempEndPage) 

		if (endPage > tempEndPage) { //endPage 가 tempEndPage 보다 크면
			endPage = tempEndPage; 	//대입
		}													//ex) 42페이지(tempEndpage) 로 끝났을때 50페이지(endPage)까지 나타내지 않고 42페이지까지 나타낸다
		//이전버튼
		prev = (startPage ==1 ? false:true);	// if)	 startPage 가 1이면 false => 이전버튼(prev)이 안나타난다 
		next = (endPage*scri.getPerPageNum() >= totalCount ? false:true);
	}
	
	public String encoding(String keyword) {
		String str="";		
		try {
			str = URLEncoder.encode(keyword,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}			
		return str;
	}
	
	
	
	
	
	
	
	
}
