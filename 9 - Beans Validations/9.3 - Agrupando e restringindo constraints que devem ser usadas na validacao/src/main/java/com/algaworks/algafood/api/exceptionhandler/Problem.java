package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

   private Integer status;
   private LocalDateTime timestamp;
   private String type;
   private String title;
   private String detail;
   private String userMessage;

   private List<Fields> listFilds;
   @Getter
   @Builder
   public static class Fields {

      private String name;
      private String userMessage;

   }

}
