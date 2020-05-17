package com.antRain.webserver.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebContext {
    private List<ServeletBean> entitys;
    private List<Mapping> mappings;
    private Map<String,String> entityMap=new HashMap<>();
    private Map<String,String> mapperMap=new HashMap<>();
   
    public WebContext(List<ServeletBean> entitys, List<Mapping> mappings) {
        this.entitys = entitys;
        this.mappings = mappings;
        for(ServeletBean serveletBean:entitys){
            entityMap.put(serveletBean.getName(), serveletBean.getClz());
        }
        for(Mapping mapping:mappings){
            for(String pattern:mapping.getPatterns()){
                mapperMap.put(pattern, mapping.getName());
            }
        }
    }
    
    //找到url对应的class路径位置
    public String getClz(String pattern){
        String name = mapperMap.get(pattern);
        return entityMap.get(name);
    }
}