package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dbconn.Dbconn;
import domain.MemberVo;

public class MemberDao {
	
	private Connection conn;
	private PreparedStatement pstmt;
	
	public MemberDao () {
		Dbconn dbconn = new Dbconn();
		this.conn = dbconn.getConnect();
	}
	
	public int memberInsert(String memberId, String memberPwd, String memberName,String memberBirth,
				String memberGender,String memberArea){
				String sql = "insert into member0817(midx,memberId,memberPwd,memberName," //sql�� �μ�Ʈ�ϴ� ����
				 +"memberBirth,memberGender,memberArea)" 
				 +" values(midx_seq.nextval,?,?,?,?,?,?)"; //setString�� �ε��� ��ȣ�� ���� ����.
	
	int value = 0;
	try{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memberId);      //�������̸� setInt
		pstmt.setString(2, memberPwd);
		pstmt.setString(3, memberName);
		pstmt.setString(4, memberBirth);
		pstmt.setString(5, memberGender);
		pstmt.setString(6, memberArea);
		value = pstmt.executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
	}			
		return value;
	}
	
	public ArrayList<MemberVo> memberList(){
			ArrayList<MemberVo> alist = new ArrayList<MemberVo>();
			String sql ="select midx,memberId,memberName,memberBirth,memberGender,memberArea from member0817 order by midx desc"; //order by ���� desc=��������
			ResultSet rs = null;
			PreparedStatement pstmt = null;
	
	try{
	pstmt = conn.prepareStatement(sql);
	rs = pstmt.executeQuery();   //ResultSet �� �����͸� �Űܴ�� ���� 
	
		while(rs.next()){
			MemberVo mv = new MemberVo();
			mv.setMidx(rs.getInt("midx"));
			mv.setMemberId(rs.getString("memberId"));
			mv.setMemberName(rs.getString("memberName"));
			mv.setMemberBirth(rs.getString("memberBirth"));
			mv.setMemberGender(rs.getString("memberGender"));
			mv.setMemberArea(rs.getString("memberArea"));
			alist.add(mv);
		}
		
		
	
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try{
		rs.close();
		pstmt.close();
		conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return alist;
	}
	

}
