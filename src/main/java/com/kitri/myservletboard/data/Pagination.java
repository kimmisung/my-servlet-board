package com.kitri.myservletboard.data;

public class Pagination {

    //페이지정보
    private int page; //현재 페이지
    private int maxRecordsPerPage = 10; //한페이지에 보여지는 게시물 수
    private int maxPagesOnScreen = 5; //아래 보여지는 페이지 수(1~5)
    private int startIndex = 0; //DB 게시글이 몇 번째부터 시작하는지
    private int totalRecords = 0; //총 저장된 레코드 수

    private int totalSearchRecords = 0; //검색된 레코드 수


    private boolean hasNext = false;  //페이지네이션의 next 여부 : true면 활성화
    private boolean hasPrev = false; //페이지네이션의 previous 여부 : true면 활성화
    private int startPageOnScreen = 1;
    private int endPageOnScreen = this.maxPagesOnScreen;


    public Pagination(int page, int maxRecordsPerPage) {
        this.page = page;
        this.maxRecordsPerPage = maxRecordsPerPage;
    }

    public void calcPagination(){
        //페이지네이션 정보 계산 메세지
        //시작페이지 - 끝페이지를 구할 때 -> 현재 page를 기준으로 구하기
        //1,2,3,4,5... -> start = 1, end = ?
        //6,7,8,9,10.. -> start = 6, end = ?
        //this.startPageOnScreen = 현재 페이지가 어디 있냐에 따라 결정
        //1~5페이지를 /5 했을 때 -> 0~1 올림 시 1,
        //6~10페이지를 /5 했을 때 ->  1~2 올림 시 2,
        //11~15페이지를 /5 했을 때 -> 2~3 올림 시 3
        //ceil을 줄 때, 연산 과정에 double을 명시해주어야 한다
        int totalPages = ((int) (Math.ceil((double) this.totalRecords / this.maxRecordsPerPage)));

        this.startPageOnScreen
                = ((int) (Math.ceil((double) this.page / maxPagesOnScreen)) - 1) * this.maxPagesOnScreen + 1;

        //1,5
        this.endPageOnScreen = this.startPageOnScreen + this.maxPagesOnScreen - 1;
        // totalPages에 따라 달라짐
        if (this.endPageOnScreen > totalPages) {
            this.endPageOnScreen = totalPages;
        }
        System.out.println(this.startPageOnScreen);
        System.out.println(this.endPageOnScreen);

        //next페이지가 있는지 여부
        if (this.endPageOnScreen < totalPages) {
            this.hasNext = true; //활성화
        }
        //previous페이지가 있는지
        if (this.startPageOnScreen > this.maxPagesOnScreen) {
            this.hasPrev = true; //활성화
        }
//        default 값이 false이기 때문에 else값은 따로 안주어도 됨
    }

    public Pagination() {
    }


    public Pagination(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMaxRecordsPerPage() {
        return maxRecordsPerPage;
    }

    public void setMaxRecordsPerPage(int maxRecordsPerPage) {
        this.maxRecordsPerPage = maxRecordsPerPage;
    }

    public int getMaxPagesOnScreen() {
        return maxPagesOnScreen;
    }

    public void setMaxPagesOnScreen(int maxPagesOnScreen) {
        this.maxPagesOnScreen = maxPagesOnScreen;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public int getStartPageOnScreen() {
        return startPageOnScreen;
    }

    public void setStartPageOnScreen(int startPageOnScreen) {
        this.startPageOnScreen = startPageOnScreen;
    }

    public int getEndPageOnScreen() {
        return endPageOnScreen;
    }


    public void setEndPageOnScreen(int endPageOnScreen) {
        this.endPageOnScreen = endPageOnScreen;
    }

    public int getTotalSearchRecords() {
        return totalSearchRecords;
    }

    public void setTotalSearchRecords(int totalSearchRecords) {
        this.totalSearchRecords = totalSearchRecords;
    }
}

