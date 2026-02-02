package core.category.service;

import core.common.category.dto.CategoryDto;
import core.common.category.dto.CategoryRequestDto;
import core.common.event.client.EventClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.category.mapper.CategoryMapper;
import core.category.model.Category;
import core.category.repository.CategoryRepository;
import core.common.exception.ConflictException;
import core.common.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final EventClient eventClient;
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    // Admin API:
    @Override
    @Transactional
    public CategoryDto add(CategoryRequestDto newDto) {
        log.debug("Метод add(); categoryRequestDto: {}", newDto);

        this.validateCategoryNameExists(newDto.getName());

        Category category = categoryMapper.toEntity(newDto);
        category.setName(newDto.getName());
        category = categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto update(Long categoryId, CategoryRequestDto updDto) {
        log.debug("Метод update(); categoryId: {}, dto: {}", categoryId, updDto);

        this.validateCategoryNameExists(updDto.getName(), categoryId);

        Category category = this.findCategoryById(categoryId);
        category.setName(updDto.getName());
        category = categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public void delete(Long categoryId) {
        log.debug("Метод delete(); categoryId: {}", categoryId);

        this.validateCategoryExists(categoryId);

        boolean hasEvents = eventClient.existsByCategoryId(categoryId);
        if (hasEvents) {
            throw new ConflictException("Category с id=" + categoryId + " используется");
        }

        categoryRepository.deleteById(categoryId);
    }

    // Public API:
    @Override
    public CategoryDto findById(Long categoryId) {
        log.debug("Метод findById(); categoryId: {}", categoryId);

        Category category = this.findCategoryById(categoryId);

        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> findAllById(int from, int size) {
        log.debug("Метод findAllById(); from: {}, size: {}", from, size);

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        List<Category> categories = categoryRepository.findAll(pageable).getContent();

        return categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findCategoriesByIds(List<Long> categoryIds) {
        log.debug("Метод findCategoriesByIds(); categoryIds: {}", categoryIds);

        return categoryRepository.findAllById(categoryIds).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    private void validateCategoryNameExists(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Category name={} уже существует", name);
        }
    }

    private void validateCategoryNameExists(String name, Long categoryId) {
        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(name, categoryId)) {
            throw new ConflictException("Category name={} уже существует", name, categoryId);
        }
    }

    private void validateCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category id={} не найдена", categoryId);
        }
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category id={} не найдена", categoryId));
    }
}