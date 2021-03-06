package com.example.blog.service.impl;

import com.example.blog.NotFoundException;
import com.example.blog.dao.TagRepository;
import com.example.blog.entity.Tag;
import com.example.blog.entity.Type;
import com.example.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1=tagRepository.findById(id).get();
        if(tag1==null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag1,tag);
        return tagRepository.save(tag1);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        return null;
    }

//    @Override
//    public List<Tag> listTagTop(Integer size) {
//        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
//        Pageable pageable=new PageRequest(0,size,sort);
//        return tagRepository.findTop(pageable);
//    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids){
        List<Long> list=new ArrayList<>();
        if(!"".equals(ids)&&ids!=null){
            String[] idarray=ids.split(",");
            for (int i=0;i<idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
