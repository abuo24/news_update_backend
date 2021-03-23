package com.news.update.service;

import com.news.update.entity.Messages;
import com.news.update.entity.News;
import com.news.update.entity.Tags;
import com.news.update.repository.NewsRepository;
import com.news.update.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public Tags getOne(String id) {
        Tags tags = tagsRepository.findById(id).get();
        if (tags == null) {
            return null;
        }
        return tags;
    }

    @Override
    public boolean create(Tags tags) {
        try {
            Tags tags1 = new Tags();
            tags1.setTagRu(tags.getTagRu());
            tags1.setTagUz(tags.getTagUz());
            if (tagsRepository.save(tags1) != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Tags> getAll() {
        try {
            List<Tags> tagsList = tagsRepository.findAll();
            Tags newsResponse = null;
            List<Tags> tagsArrayList = new ArrayList<>();
            for (int i = 0; i < tagsList.size(); i++) {
                newsResponse = new Tags(tagsList.get(i).getId(), tagsList.get(i).getTagUz(),tagsList.get(i).getTagRu() );
                tagsArrayList.add(newsResponse);
            }
            return tagsArrayList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Tags> getAllByTagsId(List<String> list) {
        try {
            List<Tags> tagsList = tagsRepository.findAllById(list);
            return tagsList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        try {
            Tags tag = tagsRepository.findById(id).get();
            if (tag != null) {
                List<News> newsList = newsRepository.findAllByTagsId(tag.getId());
                newsList.forEach(item -> {
                    List<Tags> tagsList = new ArrayList<>(item.getTags());
                    tagsList.remove(tag);
                    item.setTags(tagsList);
                    newsRepository.save(item);
                });
                tagsRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
