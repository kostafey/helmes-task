package com.helmes.form;

public class CategoryForm {
    private Integer categoryId;
    private String name;
    private Integer parentId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId > 0 ? parentId : null;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
