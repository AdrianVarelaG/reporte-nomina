package com.beet.analyzer.invoice.service.impl;

import com.beet.analyzer.invoice.model.FileNameInputStream;
import com.beet.analyzer.invoice.service.ComprobanteTransformerService;
import com.beet.model.invoice.xml.model.comprobante.Comprobante;
import com.beet.model.invoice.xml.model.nomina.Nomina;
import com.beet.model.invoice.xml.model.timbre11.TimbreFiscalDigital;
import com.beet.model.invoice.xml.utils.ComprobanteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Slf4j
@Service
public class ComprobanteTransformerServiceImpl implements ComprobanteTransformerService {

  private JAXBContext context;

  @Autowired
  public ComprobanteTransformerServiceImpl(JAXBContext context) {
    this.context = context;
  }

  @Override
  public Comprobante toComprobante(FileNameInputStream fileNameInput) {
    log.info("Processing file {}", fileNameInput.getName());
    Comprobante c = null;
    try {
      Unmarshaller jaxbUnMarshaller;
      jaxbUnMarshaller = context.createUnmarshaller();
      c = (Comprobante) jaxbUnMarshaller.unmarshal(fileNameInput.getFileInputStream());
      Nomina n = (Nomina) ComprobanteUtils.obtenComplemento(c, Nomina.class);
      TimbreFiscalDigital timbreFiscal = (TimbreFiscalDigital) ComprobanteUtils.obtenComplemento(c, TimbreFiscalDigital.class);

    }catch(JAXBException jaxbException){
      log.error("Error when process file: {} ", fileNameInput.getName());
    }finally {
      return c;
    }
  }
}
