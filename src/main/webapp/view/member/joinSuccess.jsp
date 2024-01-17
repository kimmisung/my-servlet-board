<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<jsp:include page="/view/common/head.jsp">
    <jsp:param name="title" value="게시판 목록"/>
</jsp:include>


<body>
<jsp:include page="/view/common/header.jsp"/>



<div class="container">
    <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
            <h4 class="mb-3"><b>회원 가입에 성공하셨습니다.</b></h4>
            <hr>
            <h6 style="text-align: center"><b>로그인 후 사용해주세요</b></h6>
            <br>
            <form class="validation-form" novalidate method="post" action="/member/loginForm">
                <button class="btn btn-secondary btn-block" type="submit">로그인하러 가기</button>
            </form>
        </div>
    </div>
    <div class="p-2">
        <div class="footer">
            <footer>
                <span class="text-muted d-flex justify-content-center">Copyright &copy; 2024 Bootstrap board</span>
            </footer>
        </div>
    </div>
</div>
<script>
    window.addEventListener('load', () => {
        const forms = document.getElementsByClassName('validation-form');

        Array.prototype.filter.call(forms, (form) => {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }

                form.classList.add('was-validated');
            }, false);
        });
    }, false);
</script>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>

</html>