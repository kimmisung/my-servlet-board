<%@ page import="com.kitri.myservletboard.data.Member" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<header>
    <a class="logo" href="/board/list">
        <span class="material-symbols-outlined">
            home
        </span>
    </a>
    <nav>
        <ul class="nav-items">
            <li><a href="/board/list">게시글목록</a></li>
            <li><a href="/member/joinForm">회원가입</a></li>
            <li><a href="/member/registrationForm">회원정보수정</a></li>
            <%
                Member member = (Member) session.getAttribute("member");
                if (member != null){
                    if (member.getLogin_id() != null) {%>

            <%--네비바에 로그아웃이라고 보이게 구현--%>
            <a href="/member/logout">로그아웃</a>

            <%}} else {%>
            <li><a href="/member/loginForm">로그인</a></li>
            <%}%>
            <nav class="navbar navbar-light">
                <form class="d-flex" action="/board/list">

                    <input name="orderBy" value="${param.orderBy}" hidden="hidden"/>
                    <input name="maxRecordsPerPage" value="${param.maxRecordsPerPage}" hidden="hidden"/>

                    <select name="period">
                        <option value="100 Year" ${param.period == "100 Year" ? "selected" : ""}>전체기간</option>
                        <option value="1 Day" ${param.period == "1 Day" ? "selected" : ""}>1일 전</option>
                        <option value="7 Day" ${param.period == "7 Day" ? "selected" : ""}>1주일 전</option>
                        <option value="1 Month" ${param.period == "1 Month" ? "selected" : ""}>1개월 전</option>
                        <option value="6 Month" ${param.period == "6 Month" ? "selected" : ""}>6개월 전</option>
                        <option value="1 Year" ${param.period == "1 Year" ? "selected" : ""}>1년 전</option>
                    </select>
                    <select name="type">
                        <option value="title" ${param.type =="title" ? "selected" : ""}>제목</option>
                        <option value="writer" ${param.type =="writer" ? "selected" : ""}>작성자</option>
                    </select>
                    <input name="keyword" class="form-control me-2" type="search" placeholder="Search"
                           aria-label="Search" value="${param.keyword}">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </nav>
        </ul>
    </nav>
</header>