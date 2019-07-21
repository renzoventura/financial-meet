package com.financialmeet.service;


public interface ApplicationSubTypeService {
  Iterable<String> getAllApplicationSubTypeTitle();
  Iterable<String> getAllApplicationSubTypeTitleByParent(String parentApplicationTitle);

}
