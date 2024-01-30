<%@ page import="com.kitri.myservletboard.data.Member" %>
<%@ page import="com.kitri.myservletboard.data.Board" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.kitri.myservletboard.data.Comment" %>
<%@ page import="com.kitri.myservletboard.service.CommentService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<jsp:include page="/view/common/head.jsp">
    <jsp:param name="title" value="게시판 목록"/>
</jsp:include>


<body class="sb-nav-fixed">
<jsp:include page="/view/common/header.jsp"/>

<%
    ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
    Board board = (Board) request.getAttribute("board");
    Member member = (Member) session.getAttribute("member");

%>

<main class="mt-5 pt-5">
    <div class="container-fluid px-4 ">
        <div class="card mb-4 w-50 mx-auto">
            <div>
                <h2 class="mt-3" style="text-align: center;"><b>게시판 상세</b></h2>
                <hr class="mb-0">
            </div>
            <div class="d-flex flex-column" style="height: 500px;">
                <div class="p-2 border-bottom border-dark-subtle d-flex flex-row-reverse">
                    <div class="pd opacity-75 bg-info-subtle border border-dark rounded-pill"><small>조회수
                        : ${board.getViewCount()}</small></div>
                    &nbsp
                    <div class="pd opacity-75 bg-success-subtle border border-dark rounded-pill"><small>댓글수
                        : ${board.getCommentCount()}</small></div>
                    <div class="d-flex flex-row flex-fill">
                        <div class="pd opacity-75 border border-dark rounded-pill">${board.getId()}</div>
                    </div>
                </div>
                <div class="p-2 border-bottom">
                    <b>${board.getTitle()}</b>
                </div>
                <div class="p-2 border-bottom">
                    <b>저자 :</b> ${board.getWriter()}
                </div>
                <div class="p-2 border-bottom">
                    <b>등록일시 :</b> ${board.getCreatedAt()}
                </div>
                <div class="m-3 h-75">
                    <textarea class="h-100 form-control bg-white" id="content" name="content"
                              disabled>${board.getContent()}</textarea>
                </div>
                <div class="d-flex flex-row-reverse mb-3 mr-3">
                    &nbsp
                    &nbsp
                    <%--이 글이 내가 쓴 글이여야지만 보여지게 조건 설정--%>
                    <%
                        if (member != null) {
                            if (board.getMember_id().equals(member.getId())) {
                    %>

                    <a href="/board/delete?id=${board.getId()}" class="btn btn-secondary btn-sm"
                       onclick="return confirm('삭제하시겠습니까?')"><small>삭제하기</small></a>
                    &nbsp
                    <a href="/board/updateForm?id=${board.getId()}" class="btn btn-secondary btn-sm"><small>수정하기</small></a>
                    <%
                            }
                        }
                    %>
                    &nbsp
                    <a href="/board/list" class="btn btn-secondary btn-sm"><small>목록으로</small></a>
                    &nbsp
                </div>
            </div>
        </div>
    </div>
</main>

    <form action="/comment/create" method="post">
        <div class="container-fluid px-4 ">
            <div class="card mb-4 w-50 mx-auto">
                <div>
                    <h6 class="mt-3" style="text-align: center;"><b>댓글</b></h6>
                    <hr class="mb-0">
                </div>


                <%
                    if (member != null) {%>
                <input type="text" id="memberId" name="memberId" value="${member.getId()}" hidden="hidden"/>
                <input type="text" id="boardId" name="boardId" value="${board.getId()}" hidden="hidden"/>

                <tbody class="comment">
                <% for (Comment comment : comments) {%>
                <tr>
                    <th scope="row">
                    </th>
                    <br>
                    <td><b>${member.getName()}</b>
                    </td>
                    <td><%=comment.getContent()%>
                    </td>
                    <td><%=comment.getCreatedAt()%>

                    <td><form action="/comment/delete" method="post">
                    <div class="p-2 border-bottom border-dark-subtle d-flex flex-row-reverse">
                        <button class="btn pd opacity-75 bg-success-subtle border border-dark rounded-pill" onclick="return confirm('삭제하시겠습니까?')">
                            <small>삭제</small>
                            <input type="text" id="deleteId" name="deleteId" value="${comment.getId()}" hidden="hidden"/>
                        </button>
                    </div>
                </form></td>
                </tr>
                <%}%>
                </tbody>


                <div class="m-3 h-30">
                    <div class="p-2 border-bottom">
                        <b>${member.getName()}</b>
                    </div>
                    <textarea class="h-35 form-control bg-white" id="contents" name="contents"
                              placeholder="댓글을 입력해주세요"></textarea>
                </div>
                <div class="p-2 border-bottom border-dark-subtle d-flex flex-row-reverse">
                    <button class="btn pd opacity-75 bg-success-subtle border border-dark rounded-pill"><small>댓글
                        등록하기</small></button>
                </div>
                    <%} else {%>
                <div class="d-flex flex-column" style="height: 100px;">
                    <div class="p-2 border-bottom border-dark-subtle d-flex flex-row-reverse">
                    </div>
                    <div class="m-3 h-15" style="text-align: center">
                        <h6>댓글을 작성하시려면 <a href="/member/loginForm"><b>로그인</b></a>하세요.</h6>
                    </div>
                </div>
                    <%}%>
    </form>

<%--    <form action="/comment/delete" method="post">--%>
<%--        <%--%>
<%--            if (member != null) {%>--%>
<%--        <input type="text" id="memberId" name="memberId" value="${member.getId()}" hidden="hidden"/>--%>
<%--        <input type="text" id="boardId" name="boardId" value="${board.getId()}" hidden="hidden"/>--%>


<%--        <div class="p-2 border-bottom border-dark-subtle d-flex flex-row-reverse">--%>
<%--            <button class="btn pd opacity-75 bg-success-subtle border border-dark rounded-pill" onclick="return confirm('삭제하시겠습니까?')">--%>
<%--                <small>삭제</small>--%>
<%--                <input type="text" id="deleteId" name="deleteId" value="${comment.getId()}" hidden="hidden"/>--%>
<%--            </button>--%>
<%--        </div>--%>
<%--        <%}%>--%>
<%--    </form>--%>

</main>
</body>
<style>
    .pd {
        padding-left: 5px;
        padding-right: 5px;
    }
</style>
</html>