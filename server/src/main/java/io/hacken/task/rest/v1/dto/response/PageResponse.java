package io.hacken.task.rest.v1.dto.response;

import java.util.Collection;

public record PageResponse<T>(Collection<T> collection, int currentPage, int totalPages) {
}
