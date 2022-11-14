<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>

</head>
<body>

<a href="./">'헬로월드'로 이동합니다</a>
<a href="/jsppj/First">서블릿 헬로월드로 이동합니다</a>

<a href="<%=request.getContextPath()%>/member/memberList.do">회원정보 리스트 가기</a>
<a href="<%=request.getContextPath()%>/member/memberInput.do">회원가입 하기</a>
<a href="<%=request.getContextPath()%>/board/boardList.do">게시판 목록가기</a>
</body>
</html>