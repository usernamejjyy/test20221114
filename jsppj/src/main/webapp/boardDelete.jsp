<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String bidx = (String)request.getAttribute("bidx"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제화면</title>
</head>
<body>
정말로 삭제하시겠습니까?
<button onclick="location.href='<%=request.getContextPath() %>/board/boardDeleteAction.do?bidx=<%=bidx%>'">삭제</button>
</body>
</html>