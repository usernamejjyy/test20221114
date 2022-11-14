package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbconn.Dbconn;
import domain.BoardVo;
import domain.Criteria;
import domain.MemberVo;
import domain.SearchCriteria;

public class BoardDao {
	
	private Connection conn;
	private PreparedStatement pstmt;
	
	public BoardDao () {
		Dbconn dbconn = new Dbconn();
		this.conn = dbconn.getConnect();
	}
	
	
	
	public ArrayList<BoardVo> boardList(SearchCriteria scri){
		
		String str="";
		if(scri.getSearchType().equals("subject")) { //������ ������ �Ѿ����
			str="and subject like ?";
		}else {
			str="and writer like ?";
		}
		
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();
		
//		String sql ="select * from board0817 where delYn = 'N' order by originbidx desc, depth asc"; 	//order by ���� desc=��������
		
		//����¡�ϴ� ������ �����
		String sql ="SELECT b.* FROM ("
				+ "	SELECT ROWNUM AS rnum, A.* FROM ("
				+ "	SELECT * FROM board0817 WHERE delyn='N' "+ str+ " ORDER BY ORIGINBIDX desc, DEPTH ASC"
				+ "	)A"
				+ ")B WHERE b.rnum BETWEEN ? AND ?";
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;

	try{
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%"+scri.getKeyword()+"%");
		pstmt.setInt(2, (scri.getPage()-1)*scri.getPerPageNum()+1);
		pstmt.setInt(3, scri.getPage()*scri.getPerPageNum());
		rs = pstmt.executeQuery();   //ResultSet �� �����͸� �Űܴ�� ���� 

	while(rs.next()){
		BoardVo bv = new BoardVo(); //��ü�� �����Ͽ� �Űܴ�´�.
		bv.setBidx(rs.getInt("bidx"));
		bv.setSubject(rs.getString("subject"));
		bv.setWriter(rs.getString("writer"));
		bv.setWriteday(rs.getString("writeday"));
		bv.setViewcnt(rs.getInt("viewcnt"));
		bv.setLevel_(rs.getInt("level_"));
		
		
		alist.add(bv); //�� ������ ��ü(bv)�� ArrayList alist �߰��Ѵ�.
		}
	
	

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
			rs.close();
			pstmt.close();
			//conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
		return alist;
		}
	
		public int boardInsert(String subject,String contents,String writer,String ip,int midx,String filename) {
			
			int value =0;
			String sql = "insert into board0817(bidx,originbidx,depth,level_,subject,contents,writer,writeday,ip,midx,filename)"      //���ڿ��� ������(sql)�ۼ���.
						+"values(bidx_seq.nextval,bidx_seq.nextval,0,0,?,?,?,sysdate,?,?,?)";
			
			try {
				pstmt = conn.prepareStatement(sql);       //����ȭ �ϴ� ����
				pstmt.setString(1, subject);             
				pstmt.setString(2, contents);
				pstmt.setString(3, writer);
				pstmt.setString(4, ip);
				pstmt.setInt(5,midx );
				pstmt.setString(6, filename);
				
				value = pstmt.executeUpdate();       //������ �����ϱ� ���� ����
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return value;
		}
		public BoardVo boardSelectOne(int bidx) {
			BoardVo bv = null;
			ResultSet rs = null;
			
			String sql = "select * from board0817 where bidx = ?";
			String sql2 = "update board0817 set viewcnt = viewcnt+1 where bidx = ?";	//viewcnt ��ȸ�� �۵���Ű�� ����
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bidx);
				rs = pstmt.executeQuery();
				
			if(rs.next()) { //Ŀ���� �̵��ϰ� ���� �����Ͱ� �����Ѵٸ� true ��ȯ
				bv = new BoardVo();
				bv.setBidx(rs.getInt("bidx")); //rs�� �ִ� ���� bv�� �Ű� ��´�.
				bv.setSubject(rs.getString("subject"));
				bv.setContents(rs.getString("contents"));
				bv.setWriter(rs.getString("writer"));
				bv.setWriteday(rs.getString("writeday"));
				bv.setOriginbidx(rs.getInt("originbidx"));
				bv.setDepth(rs.getInt("depth"));
				bv.setLevel_(rs.getInt("level_"));
				bv.setViewcnt(rs.getInt("viewcnt"));
				bv.setFilename(rs.getString("filename"));
			}
			//��ȸ��
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, bidx);
			pstmt.executeUpdate();
			
			
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return bv;
		}
	
	public int boardUpdate (String subject,String contents,String writer,int bidx) {
			
			int value =0;
		//	String sql = "insert into board0817(bidx,subject,contents,writer,writeday,ip,midx)"      //���ڿ��� ������(sql)�ۼ���.
		//				+"values(bidx_seq.nextval,?,?,?,sysdate,?,?)";
			
			String sql="update board0817 set subject=?, contents=?, writer=? where bidx=?";
			
			try {
				pstmt = conn.prepareStatement(sql);       //����ȭ �ϴ� ����
				pstmt.setString(1, subject);             
				pstmt.setString(2, contents);
				pstmt.setString(3, writer);
				pstmt.setInt(4,bidx );				
				value = pstmt.executeUpdate();       //������ �����ϱ� ���� ����
				
			} catch (SQLException e) {				
				e.printStackTrace();
			}
			return value;
		}
	
	
	
	// �����ϱ� �ۼ�	
	public int boardDelete(int bidx) {
		
		int value =0;

		String sql= "UPDATE board0817 SET delYn='Y', writeday=SYSDATE WHERE bidx =?";
		
		try {
			pstmt = conn.prepareStatement(sql);       //����ȭ �ϴ� ���� prepareStatement
			pstmt.setInt(1,bidx);				
			value = pstmt.executeUpdate();       //������ �����ϱ� ���� ����
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return value;
	}
		
	//�亯�ϱ�
	public int boardReply(BoardVo bv) {
		int value=0;
		
		String sql = "update board0817 set depth=depth+1 where originbidx=? and depth>?";
		String sql2 ="insert into board0817(bidx,originbidx,depth,level_,subject,contents,writer,writeday,midx) "+ 
							"values(bidx_seq.nextval,?,?,?,?,?,?,sysdate,?)";
		
		try {
			//Ʈ����� ����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bv.getOriginbidx());
			pstmt.setInt(2, bv.getDepth());
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, bv.getOriginbidx());
			pstmt.setInt(2, bv.getDepth()+1);
			pstmt.setInt(3, bv.getLevel_()+1);
			pstmt.setString(4, bv.getSubject());
			pstmt.setString(5, bv.getContents());
			pstmt.setString(6, bv.getWriter());
			pstmt.setInt(7, bv.getMidx());
			value = pstmt.executeUpdate();
			
			//Ʈ����� �ϰ�����
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}		
		return value;
	}
	
	public int boardTotal(SearchCriteria scri) {
		
		String str="";
		if(scri.getSearchType().equals("subject")) {
			str="and subject like ?";
		}else {
			str="and writer like ?";
		}
		
		
		int cnt = 0;
		String sql="select count(*) as cnt from board0817 where delyn ='N' "+str;
		ResultSet rs= null;
		try {
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			rs= pstmt.executeQuery(); //������ ������ rs�� �޴´�
			
			if(rs.next()) {
				cnt = rs.getInt("cnt");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}		
		return cnt;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}