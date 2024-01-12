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
            <li><a href="/view/member/join.jsp">회원가입</a></li>
            <li><a href="/view/member/registration.jsp">회원정보수정</a></li>
            <li><a href="/view/member/login.jsp">로그인</a></li>
            <nav class="navbar navbar-light">
                <form class="d-flex" action="/board/list">
                        <select name="type">
                            <option value="title" ${param.type =="title" ? "selected" : ""}>제목</option>
                            <option value="writer" ${param.type =="writer" ? "selected" : ""}>작성자</option>
                        </select>
                    <input name="keyword" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" value="${param.keyword}">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </nav>
        </ul>
    </nav>
</header>
