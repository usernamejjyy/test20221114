package domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PageMaker {

	private int displayPageNum = 10;		//		< 1 2 3 4 5 6 7 8 9 10 >
	private int startPage;		//����¡�� ������
	private int endPage;		//����¡�� ������ ����
	private int totalCount;		//��ü �Խù� ��
	
	private boolean prev;		//������ư
	private boolean next;		//������ư
	
	private SearchCriteria scri;		//������ ��ü

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
		calcData();	// ��ü �Խù� ���� �����ö� �׿� ���� ����¡�޼ҵ� (����)
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
	
	public void calcData() { // calcData �޼ҵ� ���� ( ������ ��ư�� ����)
		//���������� ��ȣ
		//�������� ��ȣ
		//prev next ��ư�� �����ش�
		
		//�������� ��ȣ
		endPage = (int)(Math.ceil(scri.getPage()/(double)displayPageNum)*displayPageNum); // getPage:����������(n) 	//		n/10�� �ǰԲ� => ���� �Ҽ����� �ǰ� double�� ���
								//Math.ceil : �ø�ó��		// n/10 �ø� ó�� => �ø�ó���ؼ� ���� ������*displayPageNum(10) = endPage 
		//���������� ��ȣ
		startPage = (endPage - displayPageNum)+1;
		//��ü ������ ��
		int tempEndPage = (int)(Math.ceil(totalCount/(double)scri.getPerPageNum())); //	"��ü�Խù��� /ȭ��Խù���"�� �ø�ó���� ������ = �ʿ��� ������ ������(tempEndPage) 

		if (endPage > tempEndPage) { //endPage �� tempEndPage ���� ũ��
			endPage = tempEndPage; 	//����
		}													//ex) 42������(tempEndpage) �� �������� 50������(endPage)���� ��Ÿ���� �ʰ� 42���������� ��Ÿ����
		//������ư
		prev = (startPage ==1 ? false:true);	// if)	 startPage �� 1�̸� false => ������ư(prev)�� �ȳ�Ÿ���� 
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
