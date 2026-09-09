package com.algafood.client.model;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Problem {

    private Integer status;
    private OffsetDateTime offsetDateTime;
    private String userMessage;
    private List<Object> objects = new ArrayList<>();

    @Data
    public static class Object{
        private String name;
        private String userMessage;
    }


}
