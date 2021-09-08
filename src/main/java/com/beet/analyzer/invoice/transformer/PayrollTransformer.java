package com.beet.analyzer.invoice.transformer;

import com.beet.analyzer.invoice.model.Payroll;
import com.beet.model.invoice.model.comprobante.EntidadComprobante;
import com.beet.model.invoice.model.nomina.ComplementoNomina;
import com.beet.model.invoice.model.timbre.TimbreFiscal;


public interface PayrollTransformer {

  static Payroll toPayroll(EntidadComprobante comprobante, ComplementoNomina nomina, TimbreFiscal timbre, String fileName) {
    Payroll ret = null;

    if(comprobante != null) {
      ret = new Payroll();
      if(comprobante.getEmisor() != null && comprobante.getEmisor().getRfc() != null)
        ret.setRfcEmisor(comprobante.getEmisor().getRfc().getvalor());
    }
    if(nomina != null) {
      if(ret == null)
        ret = new Payroll();
      if(nomina.getFechaPago() != null)
        ret.setFechaPago(nomina.getFechaPago().getFecha());
      if(nomina.getFechaInicialPago() != null)
        ret.setFechaInicialPago(nomina.getFechaInicialPago().getFecha());
      if(nomina.getFechaFinalPago() != null)
        ret.setFechaFinalPago(nomina.getFechaFinalPago().getFecha());
      if(nomina.getTotalPercepciones() != null)
        ret.setTotalPercepciones(nomina.getTotalPercepciones().getValor());
      if(nomina.getTotalDeducciones() != null)
        ret.setTotalDeducciones(nomina.getTotalDeducciones().getValor());
      if(nomina.getTotalOtrosPagos() != null)
        ret.setTotalOtrosPagos(nomina.getTotalOtrosPagos().getValor());
      if(nomina.getPercepciones() != null){
        if(nomina.getPercepciones().getTotalSueldo() != null)
          ret.setTotalSueldo(nomina.getPercepciones().getTotalSueldo().getValor());
        if(nomina.getPercepciones().getTotalGravado() != null)
          ret.setTotalGravado(nomina.getPercepciones().getTotalGravado().getValor());
        if(nomina.getPercepciones().getTotalExento() != null)
          ret.setTotalExento(nomina.getPercepciones().getTotalExento().getValor());
      }
      if(nomina.getDeducciones() != null){
        if(nomina.getDeducciones().getTotalOtrasDeducciones() != null)
          ret.setTotalOtrasDeducciones(nomina.getDeducciones().getTotalOtrasDeducciones().getValor());
        if(nomina.getDeducciones().getTotalImpuestosRetenidos() != null)
          ret.setTotalImpuestosRetenidos(nomina.getDeducciones().getTotalImpuestosRetenidos().getValor());
      }
    }
    if(ret != null){
      if(timbre != null && timbre.getUuid() != null)
        ret.setUuid(timbre.getUuid().getvalor());
      ret.setFileName(fileName);
    }

    return ret;
  }
}
