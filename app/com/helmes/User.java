package com.helmes;

import javax.persistence.*;
import com.google.gson.Gson;

@Entity
@Table(name = "User")
public class User {
    @Id @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "agreeToTerms")
    private Boolean agreeToTerms;

    public User(){}

    public User(String name, Category category, Boolean agreeToTerms) {
        this.name = name;
        this.category = category;
        this.agreeToTerms = agreeToTerms;
    }    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getAgreeToTerms() {
        return agreeToTerms;
    }

    public void setAgreeToTerms(Boolean agreeToTerms) {
        this.agreeToTerms = agreeToTerms;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
