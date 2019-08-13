package com.example.aop.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
public class Post implements Containable {
    
	@Id
	@Column(nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@Column(name="owner")
//	@JoinColumn(nullable=false,name="id")
	@ManyToOne
    @JoinColumn(name="user_id")
    private User owner;
		
    private String content;

//	@ManyToOne
//	@JoinColumn(name="id") 
//  @ManyToOne(targetEntity = User.class)
    
     
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
