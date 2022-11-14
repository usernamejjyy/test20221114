<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import ="domain.*" %>
    <%BoardVo bv = (BoardVo)request.getAttribute("bv"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<style>
	td:last-child{
	width:400px;
	}
</style>
</head>
<body>
	<table border=1 style=" height=150px;">
		<tr>
			<td>제목</td><td><%=bv.getSubject() %><p style="float:right">조회수<%=bv.getViewcnt() %></p></td>
		</tr>
		<tr>
			<td>파일 다운로드</td>
			<td><a href="<%=request.getContextPath() %>/board/fileDownload.do?filename=<%=bv.getFilename()%>"> <%=bv.getFilename()%>  </a></td>
		</tr>
		<tr>
			<td>이미지</td>
			<td><img src="<%=request.getContextPath() %>/image/<%=bv.getFilename()%>" width="100px"></td>
		</tr>
		
		
		<tr>
			<td>내용</td><td><%=bv.getContents() %></td>
		</tr>
		<tr>
			<td>작성자</td><td><%=bv.getWriter() %></td>
		</tr>
		<tr>
			<td colspan=2 style="text-align:right;">
			<button onclick="location.href='<%=request.getContextPath() %>/board/boardModify.do?bidx=<%=bv.getBidx()%>'">수정</button>
			<button onclick="location.href='<%=request.getContextPath() %>/board/boardDelete.do?bidx=<%=bv.getBidx()%>'">삭제</button> <!-- 1. 온클릭에 가상경로 넣어줌, 보안상의 이유로
			bidx 넣은 이유, 전체삭제 안하고 키값에 해당하는것만 삭제하려고 -->
			<button onclick="location.href='<%=request.getContextPath() %>/board/boardReply.do?bidx=<%=bv.getBidx()%>&originbidx=<%=bv.getOriginbidx()%>&depth=<%=bv.getDepth()%>&level_=<%=bv.getLevel_()%>'">답변</button>
			<button onclick="location.href='<%=request.getContextPath() %>/board/boardList.do'">목록</button>
			</td>
		</tr>
	</table>
</body>
</html>