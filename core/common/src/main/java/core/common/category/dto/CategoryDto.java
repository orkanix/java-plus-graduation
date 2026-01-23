package core.common.category.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {

    private Long id;

    @Size(max = 50)
    private String name;
}
