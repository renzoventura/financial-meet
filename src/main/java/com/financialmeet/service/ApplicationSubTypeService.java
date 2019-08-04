package com.financialmeet.service;


public interface ApplicationSubTypeService {
  Iterable<String> getAllApplicationSubTypeCode();
  Iterable<String> getAllApplicationSubTypeTitleByParent(String parentApplicationTitle);

}
