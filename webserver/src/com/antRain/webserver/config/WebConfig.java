package com.antRain.webserver.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.antRain.webserver.Impl.Servlet;
import com.antRain.webserver.bean.Mapping;
import com.antRain.webserver.bean.ServeletBean;
import com.antRain.webserver.bean.WebContext;

public class WebConfig {
    private static WebContext webContext;
    static{
        try {
        // SAX解析
        // 1.获取解析工厂
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        // 2.从解析工厂获取解析器
        final SAXParser parser = factory.newSAXParser();
        // 3.加载文档注册处理器（编写处理器)
        final WebHandler handler = new WebHandler();
        // 4.解析
        parser.parse(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("com/antRain/webserver/web.xml"),
                handler);
        // 5.获取数据
        // List<ServeletBean> serveletBeans = handler.getEntitys();
        // List<Mapping> mappings = handler.getMappings();
        webContext = new WebContext(handler.getEntitys(), handler.getMappings());
        } catch (Exception e) {
            System.out.println("配置文件解析错误");
        }
    }
    
    /**
     * 通过url从配置文件获取对应的servelet所在的包
     * @param url
     * @return
     */
    public static Servlet getServlet(String url){
        String className = webContext.getClz(url);
        try {
            Class  clz = Class.forName(className);
            return (Servlet)clz.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}

class WebHandler extends DefaultHandler {
    private List<ServeletBean> entitys;
    private List<Mapping> mappings;
    private ServeletBean serveletBean;
    private Mapping mapping;
    private String tag;
    private boolean isMapping = false;

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        if (null != qName) {
            if (qName.equals("servlet")) {
                this.entitys.add(this.serveletBean);
            } else if (qName.equals("servlet-mapping")) {
                this.mappings.add(this.mapping);
            }
        }
        tag = null;
    }

    @Override
    public void startDocument() throws SAXException {
        this.entitys = new ArrayList<>();
        this.mappings = new ArrayList<>();
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
            throws SAXException {
        if (null != qName) {
            tag = qName;
            if (tag.equals("servlet")) {
                this.serveletBean = new ServeletBean();
                this.isMapping = false;
            } else if (tag.equals("servlet-mapping")) {
                this.mapping = new Mapping();
                this.isMapping = true;
            }
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        final String contents = new String(ch, start, length).trim();
        if (null != tag) {
            if (isMapping) {
                if (tag.equals("servlet-name")) {
                    this.mapping.setName(contents);
                } else if (tag.equals("url-pattern")) {
                    this.mapping.addPattern(contents);
                }
            } else {
                if (tag.equals("servlet-name")) {
                    this.serveletBean.setName(contents);
                } else if (tag.equals("servlet-class")) {
                    this.serveletBean.setClz(contents);
                }
            }
        }
    }

    public List<ServeletBean> getEntitys() {
        return entitys;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

}