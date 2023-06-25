package com.gestorreservas;

import com.gestorreservas.model.CategoryView;
import com.gestorreservas.model.ResourceView;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class ResourceFilters {
    private LocalTime startTime;
    private LocalTime endTime;

    private List<CategoryView> categories;
    private CategoryView selectedCategory;
    private Predicate<ResourceView> sameCategory = r -> selectedCategory != null ? selectedCategory.equals(r.getCategory()) : true;

    private List<Integer> capacityValues = Arrays.asList(1, 5, 10, 15, 20, 25, 30);
    private Integer selectedCapacity;
    private Predicate<ResourceView> sameCapacity = r -> Objects.isNull(selectedCapacity) ? true : r.getCapacity() >= selectedCapacity;

    private String keyword;
    private Predicate<ResourceView> containsKeyword = r -> StringUtils.isNotBlank(keyword) ? r.getName().toLowerCase().contains(keyword.toLowerCase()) : true;


    public ResourceFilters(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.categories = Stream.of(CategoryView.values()).collect(Collectors.toList());
    }

    public List<ResourceView> applyFilters(List<ResourceView> resources) {
        Predicate<ResourceView> predFinal = r -> sameCapacity
                .and(sameCategory)
                .and(containsKeyword)
                .test(r);
        return resources.stream().filter(predFinal).collect(Collectors.toList());
    }

    public void resetFilters() {
        this.selectedCategory = null;
        this.selectedCapacity = null;
        this.keyword = null;
    }

}
