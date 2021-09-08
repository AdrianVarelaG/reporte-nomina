package com.beet.analyzer.invoice.service.impl;

import com.beet.analyzer.invoice.model.FileNameInputStream;
import com.beet.analyzer.invoice.model.Payroll;
import com.beet.analyzer.invoice.service.PayrollTransformerService;
import com.beet.analyzer.invoice.transformer.PayrollTransformer;
import com.beet.model.invoice.assembler.comprobante.ComprobanteAssembler;
import com.beet.model.invoice.assembler.nomina.NominaAssembler;
import com.beet.model.invoice.assembler.timbre.TimbreFiscalAssembler;
import com.beet.model.invoice.exception.ValidacionExcepcion;
import com.beet.model.invoice.model.comprobante.EntidadComprobante;
import com.beet.model.invoice.model.nomina.ComplementoNomina;
import com.beet.model.invoice.model.timbre.TimbreFiscal;
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
public class PayrollTransformerServiceImpl implements PayrollTransformerService {

  private JAXBContext context;

  @Autowired
  public PayrollTransformerServiceImpl(JAXBContext context) {
    this.context = context;
  }

  @Override
  public Payroll toPayroll(FileNameInputStream fileNameInput) {
    log.info("Processing file {}", fileNameInput.getName());
    Payroll payroll = null;
    try {
      Unmarshaller jaxbUnMarshaller;
      jaxbUnMarshaller = context.createUnmarshaller();
      Comprobante comprobante = (Comprobante) jaxbUnMarshaller.unmarshal(fileNameInput.getFileInputStream());
      Nomina nomina = (Nomina) ComprobanteUtils.obtenComplemento(comprobante, Nomina.class);
      TimbreFiscalDigital timbreFiscalDigital = (TimbreFiscalDigital) ComprobanteUtils.obtenComplemento(comprobante, TimbreFiscalDigital.class);

      EntidadComprobante entidadComprobante = ComprobanteAssembler.toEntidadComprobante(comprobante);
      ComplementoNomina complementoNomina = NominaAssembler.toComplementoNomina(nomina);
      TimbreFiscal timbreFiscal = TimbreFiscalAssembler.toTimbreFiscal(timbreFiscalDigital);

      payroll = PayrollTransformer.toPayroll(entidadComprobante,complementoNomina,timbreFiscal, fileNameInput.getName());

    }catch(JAXBException | ValidacionExcepcion exception){
      log.error("Error when process file: {} ", fileNameInput.getName());
    }finally {
      return payroll;
    }
  }
}
