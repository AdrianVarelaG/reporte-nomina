package com.beet.analizer.invoice;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InvoiceAnalizerConfig implements WebMvcConfigurer {
  
  @Bean
  public JAXBContext jaxbContext(){
      try {
          return JAXBContext.newInstance("com.beet.model.invoice.xml.model.comprobante:com.beet.model.invoice.xml.model.timbre11:com.beet.model.invoice.xml.model.nomina");
      } catch (JAXBException e) {
          e.printStackTrace();
          return null;
      }
  }

}
