package com.parchenegar.capsule.service.base;


import com.parchenegar.capsule.domain.base.Category;
import com.parchenegar.capsule.repository.base.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService
{
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllById(List categoryIds)
    {
        return categoryRepository.findAllById(categoryIds);
    }

}
