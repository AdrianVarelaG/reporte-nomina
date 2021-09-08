package com.beet.analyzer.invoice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Configuration
public class InvoiceAnalyzerConfig implements WebMvcConfigurer {
  
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
