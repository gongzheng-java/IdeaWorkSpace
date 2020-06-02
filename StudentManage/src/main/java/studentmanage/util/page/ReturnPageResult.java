package studentmanage.util.page;

import java.util.List;

/**
 * 分页查询返回的结果
 *
 * @param <T>
 */
public class ReturnPageResult<T> {

    /**
     * 实体内容
     */
    private List<T> records;
    /**
     * 总页数
     */
    private long totalPage;
    /**
     * 总行数（分页前）
     */
    private long totalRecord;
    /**
     * 当前页码（第几页；从1开始而不是0）
     */
    private int pageNumber;
    /**
     * 每页行数
     */
    private int pageSize;

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
