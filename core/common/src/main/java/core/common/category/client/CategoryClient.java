package core.common.category.client;

import core.common.category.dto.CategoryDto;
import core.common.category.dto.CategoryRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "main-service", contextId = "category-service")
public interface CategoryClient {

    String ADMIN_PREFIX = "/admin/categories";
    String PUBLIC_PREFIX = "/categories";

    @PostMapping(ADMIN_PREFIX)
    CategoryDto addCategory(@RequestBody CategoryRequestDto newDto);

    @DeleteMapping(ADMIN_PREFIX + "/{catId}")
    void deleteCategory(@PathVariable Long catId);

    @PatchMapping(ADMIN_PREFIX + "/{catId}")
    CategoryDto updateCategory(@PathVariable Long catId,
                               @RequestBody CategoryRequestDto updDto);

    @GetMapping(PUBLIC_PREFIX)
    List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") int from,
                                    @RequestParam(defaultValue = "10") int size);

    @GetMapping(PUBLIC_PREFIX + "/{catId}")
    CategoryDto getCategory(@PathVariable Long catId);

    @PostMapping(PUBLIC_PREFIX + "/getIds")
    List<CategoryDto> getCategoriesByIds(@RequestBody List<Long> categoryIds);
}
