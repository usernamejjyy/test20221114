package domain;

public class Criteria {

	private int page;		//������
	private int perPageNum;		//ȭ�鿡 ��µǴ� ����Ʈ ��
	
	public Criteria() {	// ������(Ŭ���� �̸��� ������ �޼ҵ�) 1.��ü ����		2.������� �ʱ�ȭ		// ��������,���Ժ��� �������� �ӵ��� �� ���� ���
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
