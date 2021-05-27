package com.lbs.blog.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @date 2020/11/14 20:38
 * @description
 **/
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue                                 //Id设置为自增
    private Long id;                               //ID

    private String title;                         //标题
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;                      //内容
    private String firstPicture;                //首图
    private String flag;                        //标记
    private Integer views;                      //浏览次数
    private boolean appreciation;             //赞赏是否开启
    private boolean shareStatement;          //转载声明是否开启
    private boolean comment;                 //评论是否开启
    private boolean published;              //是否发布
    private boolean recommend;             //是否推荐

    @Temporal(TemporalType.TIMESTAMP)           //对应数据库的时间类型
    private Date createTime;               //创建时间

    @Temporal(TemporalType.TIMESTAMP)          //对应数据库的时间类型
    private Date updateTime;              //更新时间

    private String description;         //博客描述

    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @Transient              //不存入数据库
    private String tagIds;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void init(){
        this.tagIds = tagToIds(this.getTags());
    }

    private String tagToIds(List<Tag> tags){
        if (!tags.isEmpty()){
            StringBuffer stringBuffer = new StringBuffer();
            Boolean flag = false;
            for (Tag tag : tags){
                if (flag){
                    stringBuffer.append(",");
                }else {
                    flag = true;
                }
                stringBuffer.append(tag.getId());
            }
            return stringBuffer.toString();
        }else {
            return tagIds;
        }

    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", comment=" + comment +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", tagIds='" + tagIds + '\'' +
                ", comments=" + comments +
                '}';
    }
}
