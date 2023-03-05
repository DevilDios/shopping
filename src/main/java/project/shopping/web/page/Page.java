package project.shopping.web.page;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 페이지 DTO
 */
@Getter @Setter
public class Page {
    private int previous;
    private int next;
    private int lastPage;
    private Integer nowPage;

    List<Integer> pages = new ArrayList<>();;

    public void pageInit(int previous, int next, int lastPage, int nowPage) {
        this.previous = previous;
        this.next = next;
        this.lastPage = lastPage;
        this.nowPage = nowPage;
    }

}
