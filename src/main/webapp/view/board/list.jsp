<%@ page import="com.kitri.myservletboard.data.Board" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.kitri.myservletboard.data.Pagination" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    ArrayList<Board> boards = (ArrayList<Board>) request.getAttribute("boards");

    Pagination pagination = (Pagination) request.getAttribute("pagination");
    String type = (String) request.getAttribute("type");
    String keyword = (String) request.getAttribute("keyword");
    String period = (String) request.getAttribute("period");
    String orderBy = (String) request.getAttribute("orderBy");
    String maxRecordsPerPage = (String) request.getAttribute("maxRecordsPerPage");

    String param = "&orderBy=" + orderBy + "&maxRecordsPerPage=" + maxRecordsPerPage;
    if (keyword != null) {
        param += "&type=" + type + "&keyword=" + keyword + "&period=" + period;
    } else {
        keyword = "";
    }

%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="/view/common/head.jsp">
    <jsp:param name="title" value="게시판 목록"/>
</jsp:include>

<body>
<jsp:include page="/view/common/header.jsp">
    <jsp:param name="type" value="<%=type%>"/>
    <jsp:param name="keyword" value="<%=keyword%>"/>
    <jsp:param name="period" value="<%=period%>"/>
    <jsp:param name="orderBy" value="<%=orderBy%>"/>
    <jsp:param name="maxRecordsPerPage" value="<%=maxRecordsPerPage%>"/>
</jsp:include>

<div class="d-flex pt-5 mt-5">
    <div class="flex-fill w-25"></div>
    <h2 class="flex-fill w-50" style="text-align: center;"><b>게시판 목록</b></h2>
    <form class="flex-fill w-25 pr-5 mr-5">
        <input name="period" value="${param.period}" hidden="hidden"/>
        <input name="type" value="${param.type}" hidden="hidden"/>
        <input name="keyword" value="${param.keyword}" hidden="hidden"/>

        <select name="orderBy" onchange="this.form.submit()">
            <option value="latest" <%if(orderBy.equals("latest")){%>selected<%}%>>최신순</option>
            <option value="views" <%if(orderBy.equals("views")){%>selected<%}%>>조회순</option>
            <option value="accuracy" <%if(orderBy.equals("accuracy")){%>selected<%}%>>정확도순</option>
        </select>
        <select name="maxRecordsPerPage" onchange="this.form.submit()">
            <option value="5" <%if(pagination.getMaxRecordsPerPage() == 5){%>selected<%}%>>5개씩 보기</option>
            <option value="10" <%if(pagination.getMaxRecordsPerPage() == 10){%>selected<%}%>>10개씩 보기</option>
            <option value="15" <%if(pagination.getMaxRecordsPerPage() == 15){%>selected<%}%>>15개씩 보기</option>
            <option value="20" <%if(pagination.getMaxRecordsPerPage() == 20){%>selected<%}%>>20개씩 보기</option>
            <option value="30" <%if(pagination.getMaxRecordsPerPage() == 30){%>selected<%}%>>30개씩 보기</option>
            <option value="40" <%if(pagination.getMaxRecordsPerPage() == 40){%>selected<%}%>>40개씩 보기</option>
            <option value="50" <%if(pagination.getMaxRecordsPerPage() == 50){%>selected<%}%>>50개씩 보기</option>
        </select>
    </form>
</div>


<div class="container class=d-flex justify-content-center">
    <div class="p-2 border-primary mb-3">
        <table class="table align-middle table-hover">
            <thead class="table-dark">
            <tr>
                <th scope="col">번호</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">날짜</th>
                <th scope="col">조회수</th>
                <th scope="col">댓글수</th>
            </tr>
            </thead>
            <tbody class="table-group-divider">
            <% for (Board board : boards) {%>
            <tr>
                <th scope="row"><%=board.getId()%>
                </th>
                <td><a href="/board/detail?id=<%=board.getId()%>"><%=board.getTitle()%>
                </a></td>
                <td><%=board.getWriter()%>
                </td>
                <td><%=board.getCreatedAt()%>
                </td>
                <td><%=board.getViewCount()%>
                </td>
                <td><%=board.getCommentCount()%>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
        <div>
            <a href="/board/createForm" role="button" class="btn btn-outline-dark">글쓰기</a>
        </div>
        <div class="d-flex justify-content-center">
            <nav aria-label="Page navigation example">
                <ul class="pagination pagination-sm">

                    <%
                        if (pagination.isHasPrev()) {
                    %>
                    <li class="page-item">
                        <a class="page-link"
                           href="/board/list?page=<%=pagination.getStartPageOnScreen() - 1%><%=param%>" tabindex="-1"
                           aria-disabled="true">Previous</a>
                    </li>
                    <%} else {%>
                    <li class="page-item disabled">
                        <a class="page-link"
                           href="/board/list?page=<%=pagination.getStartPageOnScreen() - 1%><%=param%>" tabindex="-1"
                           aria-disabled="true">Previous</a>
                    </li>
                    <%}%>

                    <%
                        for (int i = pagination.getStartPageOnScreen(); i <= pagination.getEndPageOnScreen(); i++) {
                            if (pagination.getPage() == i) {
                    %>
                    <li class="page-item"><a class="page-link active" href="/board/list?page=<%=i%><%=param%>"><%=i%>
                    </a></li>
                    <%} else {%>
                    <li class="page-item"><a class="page-link" href="/board/list?page=<%=i%><%=param%>"><%=i%>
                    </a></li>
                    <%
                            }
                        }
                    %>


                    <%
                        if (pagination.isHasNext()) {
                    %>
                    <li class="page-item">
                        <a class="page-link" href="/board/list?page=<%=pagination.getEndPageOnScreen() + 1%><%=param%>">Next</a>
                    </li>
                    <%} else {%>
                    <li class="page-item disabled">
                        <a class="page-link" href="/board/list?page=<%=pagination.getEndPageOnScreen() + 1%><%=param%>">Next</a>
                    </li>
                    <%}%>


                </ul>
            </nav>
        </div>
    </div>
</div>
</div>
<div class="p-2">
    <div class="container d-flex justify-content-center">
        <footer>
            <span class="text-muted">&copy; Company's Bootstrap-board</span>
        </footer>
    </div>
</div>


</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
        crossorigin="anonymous">
</script>
</html>