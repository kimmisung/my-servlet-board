<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인 폼</title>
</head>
<body>
<%
    String id = (String) session.getAttribute("id"); //세션은 오브젝트를 반환하기때문에 string으로 형변환
    if (id != null)

    {%>

    <h2>안녕하세요! <%=id%>님 접속중입니다.</h2>
    <a href="/ex/logout">로그아웃</a>

    <%} else {%>
    <%--실패했을 경우--%>
    <form method="POST" action="/ex/login">
        <label for="id">아이디: </label>
        <input type="text" name="id" id="id">
        <br>
        <label for="pw">비밀번호: </label>
        <input type="password" name="pw" id="pw">
        <br>
        <input type="submit" value="로그인">
    </form>
    <div>
        <%--리퀘스트 객체에 있는 값을 접근할 때 requestScope를 사용--%>
        ${requestScope.loginFailed ? "로그인이 실패하였습니다. 아이디 혹은 비밀번호를 정확하게 입력해주세요." : ""}
    </div>
    <%}%>
</body>

<%--자바스크립트, 문구가 2초동안 뜨고 사라지게--%>
<script>
    setTimeout(() => {
        document.querySelector(".notification").hidden = true;
    }, 2000);
</script>

</html>
