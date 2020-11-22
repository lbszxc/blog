package com.lbs.blog.service.Impl;

import com.lbs.blog.Exception.NotFountException;
import com.lbs.blog.dao.BlogRepository;
import com.lbs.blog.entity.Blog;
import com.lbs.blog.entity.Type;
import com.lbs.blog.entity.vo.BlogQuery;
import com.lbs.blog.service.BlogService;
import com.lbs.blog.until.MarkdownUntil;
import com.lbs.blog.until.MyBeanUntil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author Administrator
 * @date 2020/11/16 17:26
 * @description
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {

        Blog blog = blogRepository.findById(id).get();

        if (blog == null){
            throw new NotFountException("该资源不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUntil.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            // 处理动态查询的方法

            /**
             *
             * @param root                  //查询的对象是那个，这里是Blog，root可以获取t_blog表的字段
             * @param criteriaQuery        //查询条件的容器，可以把查询条件criteriaQuery里面
             * @param criteriaBuilder     //设置具体条件的表达式，比如：两个条件相等
             * @return
             */
            @Override
            public Predicate toPredicate(
                    Root<Blog> root,
                    CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle())&&blog.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {

        List<String> years = blogRepository.findGroupYear();
        Map<String,List<Blog>> listMap = new HashMap<>();
        for (String year :years){
            listMap.put(year,blogRepository.findByYear(year));
        }
        return listMap;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {

        Blog b = blogRepository.findById(id).get();

        if(b == null){
            throw new NotFountException("该博客不存在");
        }

        BeanUtils.copyProperties(blog,b, MyBeanUntil.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {

        blogRepository.deleteById(id);

    }
}
