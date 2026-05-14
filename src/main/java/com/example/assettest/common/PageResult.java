package com.example.assettest.common;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 * Generic page wrapper.
 *
 * @param <T> row item type
 */
@Data
@Schema(description = "Paged result wrapper")
public class PageResult<T> {
    @Schema(description = "Current page number")
    private int pageNum;

    @Schema(description = "Number of rows per page")
    private int pageSize;

    @Schema(description = "Total row count")
    private long total;

    @Schema(description = "Page rows")
    private List<T> records;
}
