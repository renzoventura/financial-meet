package com.financialmeet.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public final class PaginationUtil {

  private PaginationUtil() {

  }

  public static Direction getDirection(String sortDirectionString){
    Direction sortDirection;

    switch(sortDirectionString.toUpperCase()) {
      case "ASC":
        sortDirection = Direction.ASC;
        break;
      case "DESC":
        sortDirection = Direction.DESC;
        break;
      default:
        sortDirection = Direction.ASC;
        break;
    }
    return sortDirection;
  }

  public static Pageable createPageRequest(int page, int size, Sort.Direction direction, String orderProperty) {
    return PageRequest.of(page - 1, size, direction, orderProperty);
  }

  public static Pageable createPageRequest(int page, int size) {
    return PageRequest.of(page - 1, size );
  }

}
