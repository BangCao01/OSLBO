package orangeschool.service;

import java.util.List;

import org.springframework.data.domain.Page;

import orangeschool.model.TextContent;

public interface TextContentService {
    void save(TextContent _content);

    TextContent findByContent(String _content);
    TextContent findByTextID(Integer _id);
    //List<TextContent> findAll();
    Page<TextContent> findAll(Integer _start, Integer _limit);
    void deleteById(Integer _id);
    
}