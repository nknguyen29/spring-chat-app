package fr.utc.sr03.chatapp.model;

import jakarta.validation.constraints.Pattern;


public class UserSearch {

    private String search;

    @Pattern(regexp = "id|lastName|firstName|email|role|status")
    private String sortBy = "id";

    @Pattern(regexp = "asc|desc")
    private String sortOrder = "asc";

    private Integer page = 0;
    private Integer size = 10;

    @Pattern(regexp = "all|admin|user")
    private String role = "all";

    @Pattern(regexp = "all|locked|unlocked")
    private String status = "all";

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(final String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(final String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Boolean isAdmin() {
        return (role != null && role.equals("admin")) ? Boolean.TRUE
                : (role != null && role.equals("user")) ? Boolean.FALSE
                : null;
    }

    public Boolean isLocked() {
        return (status != null && status.equals("locked")) ? Boolean.TRUE
                : (status != null && status.equals("unlocked")) ? Boolean.FALSE
                : null;
    }

    @Override
    public String toString() {
        return "UserSearch{" +
                "search='" + search + '\'' +
                ", sortBy='" + sortBy + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
