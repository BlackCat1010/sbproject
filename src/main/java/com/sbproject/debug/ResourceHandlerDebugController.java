package com.sbproject.debug;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

@RestController
public class ResourceHandlerDebugController {

    @Autowired
    private ApplicationContext context;

    @GetMapping("/debug/resources")
    public String showStaticResourceMappings() {
        StringBuilder sb = new StringBuilder("<h2>Static Resource Handlers</h2><ul>");

        Map<String, HandlerMapping> handlerMappings = context.getBeansOfType(HandlerMapping.class);

        for (HandlerMapping mapping : handlerMappings.values()) {
            if (mapping instanceof SimpleUrlHandlerMapping simpleMapping) {
                Map<String,?> urlMap = simpleMapping.getUrlMap();

                for (Map.Entry<String, ?> entry : urlMap.entrySet()) {
                    String pattern = entry.getKey();
                    Object handler = entry.getValue();

                    if (handler instanceof ResourceHttpRequestHandler resourceHandler) {
                        sb.append("<li><b>Pattern:</b> ").append(pattern)
                          .append("<br><b>Locations:</b><ul>");
                        for (var location : resourceHandler.getLocations()) {
                            sb.append("<li>").append(location).append("</li>");
                        }
                        sb.append("</ul></li><br>");
                    }
                }
            }
        }


        sb.append("</ul>");
        return sb.toString();
    }
}
