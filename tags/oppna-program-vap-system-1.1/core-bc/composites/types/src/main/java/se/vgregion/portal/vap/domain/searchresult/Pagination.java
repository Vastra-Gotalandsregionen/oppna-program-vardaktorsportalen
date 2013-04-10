package se.vgregion.portal.vap.domain.searchresult;

import java.util.List;

public class Pagination {
    private Integer offset;
    private Integer hitsReturned;
    private Page firstPage;
    private List<Page> pages;
    private Page next;
    private Page previous;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getHitsReturned() {
        return hitsReturned;
    }

    public void setHitsReturned(Integer hitsReturned) {
        this.hitsReturned = hitsReturned;
    }

    public Page getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Page firstPage) {
        this.firstPage = firstPage;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public Page getNext() {
        return next;
    }

    public void setNext(Page next) {
        this.next = next;
    }

    public Page getPrevious() {
        return previous;
    }

    public void setPrevious(Page previous) {
        this.previous = previous;
    }
}
