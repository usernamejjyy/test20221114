package domain;

public class Criteria {

	private int page;		//페이지
	private int perPageNum;		//화면에 출력되는 리스트 수
	
	public Criteria() {	// 생성자(클래스 이름과 동일한 메소드) 1.객체 생성		2.멤버변수 초기화		// 변수선언,대입보다 생성자의 속도가 더 빨라서 사용
		this.page = 1;
		this.perPageNum = 15;
	}
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
	
	
	
	
	
	
}
