<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="domain.*"%>
<%
    ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");
	PageMaker pm = (PageMaker)request.getAttribute("pm");
    
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<style>
      table {
      	text-align:center;        
      }
      
      th, td {
        padding:  10px 0;
        text-align: center;
      }     
      
      a:link {text-decoration:none;}
</style>
</head>
<body>
	게시판 목록
	<form name="frm" action ="<%=request.getContextPath()%>/board/boardList.do" method="post" >
	<table border=0 style="width:200px; float:right;">
		<tr>
			
			<td>
				<select name="searchType">
					<option value="subject" >제목</option>
					<option value="writer">작성자</option>
				</select>
			</td>
			<td><input type="text" name="keyword" size="10"></td>
			<td><input type="submit" name="submit" value="검색"></td>			
		</tr>
	</table>
	</form>
	<table border=1 style="width:100%">
		<thead>
			<tr>
			<th>게시물번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
			<th>조회수</th>
			</tr> 
			<%for(BoardVo bv:alist){ %>
		</thead>
		<tbody>
			<tr>
			<td><%=bv.getBidx() %></td>
			<td style="text-align:left;">
			<% for(int i=1; i<=bv.getLevel_(); i++){
				out.println("&nbsp;&nbsp;");
				if (i==bv.getLevel_()){
					out.println("ㄴ");
				}
			}%>
						
			<a href="<%=request.getContextPath()%>/board/boardContents.do?bidx=<%=bv.getBidx()%>"><%=bv.getSubject()%></a>
			</td>			
			<td><%=bv.getWriter() %></td>
			<td><%=bv.getWriteday() %></td>
			<td><%=bv.getViewcnt() %></td>
			</tr>
			
			<%} %>
		</tbody>
	</table>
	
	<table border=0 style="width:1200px">
		<tr>
			<td  style="width:800px" ></td>
			<td style="text-aline:right;">
			<%if(pm.isPrev()){ %>
			<a href="<%=request.getContextPath()%>/board/boardList.do?page=<%=pm.getStartPage()-1 %>&searchType=<%=pm.getScri().getSearchType()%>&keyword=<%=pm.getScri().getKeyword()%>">◀</a>
			<%}%>
			</td>
			<td style="text-aline:center; ">
			<%
			for(int i=pm.getStartPage(); i<=pm.getEndPage(); i++){
			%>
			<a href="<%=request.getContextPath()%>/board/boardList.do?page=<%=i %>&searchType=<%=pm.getScri().getSearchType()%>&keyword=<%=pm.getScri().getKeyword()%>"> <%=i %></a>
			<%		
			}
			%>			
			</td>
			<td style="text-aline:left; ">
			<%if(pm.isNext()&&pm.getEndPage()>0) {%>
			<a href="<%=request.getContextPath()%>/board/boardList.do?page=<%=pm.getEndPage()+1 %>&searchType=<%=pm.getScri().getSearchType()%>&keyword=<%=pm.getScri().getKeyword()%>"> ▶</a>
			<%}%>
			</td>
		</tr>	
	</table>
	
	<a href="<%=request.getContextPath() %>/board/boardWrite.do">글쓰기</a>
</body>
</html>