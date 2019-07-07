package com.financialmeet.service;


public interface ApplicationSubTypeService {

  Iterable<String> getAllApplicationSubTypeTitleByParent(String parentApplicationTitle);

}
