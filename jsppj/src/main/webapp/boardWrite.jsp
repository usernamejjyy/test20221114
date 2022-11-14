<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script>
	function check(){
		var fm = document.frm;
		
		if(fm.subject.value==""){     
			alert("제목을 입력하세요.");
			fm.subject.focus();
			return;
		}else if(fm.contents.value==""){
			alert("내용을 입력하세요.");
			fm.contents.focus();
			return;
		}else if(fm.writer.value==""){
			alert("작성자를 입력하세요.");
			fm.writer.focus();
			return;
		}
		fm.action = "<%=request.getContextPath()%>/board/boardWriteAction.do";
		fm.method = "post";						//fm에 메소드를 넣음.
		fm.enctype = "multipart/form-data"; //?파일 형식 추가
		fm.submit();
		return;
	}
	
</script>
</head>
<body>
게시판 글쓰기
<form name="frm">
	<table border="1" style="width:440px;">
		<tr>
			<td>제목</td>
			<td><input type="text" name="subject"></td>
		</tr>
		<tr>
			<td>내용</td>
			<td><textarea name="contents" cols="50" rows="5"></textarea></td>
		</tr>
		<tr>
			<td>작성자</td>
			<td><input type="text" name="writer"></td>
		</tr>
		<tr>
			<td>파일첨부</td>
			<td><input type='file' name='filename'></td>			
		</tr>
		
		
		<tr><td colspan=2>
				<input type="button" name="btn" value="확인" onclick="check();">
				<input type="reset" name="rst" value="취소">
			</td></tr>
	</table>
</form>
</body>
</html>