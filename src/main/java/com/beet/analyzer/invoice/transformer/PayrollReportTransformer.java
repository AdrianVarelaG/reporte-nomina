package com.beet.analyzer.invoice.transformer;

import com.beet.analyzer.invoice.model.Payroll;
import com.beet.analyzer.invoice.model.PayrollReportColumn;


public interface PayrollReportTransformer {
  static String[] toPayrollReportRow(Payroll payroll){
    String[] ret = new String[PayrollReportColumn.values().length];

    if(payroll != null) {
      ret[PayrollReportColumn.RFC.index] = payroll.getRfcEmisor();
      if(payroll.getFechaPago() != null)
        ret[PayrollReportColumn.FECHA_PAGO.index] = payroll.getFechaPago().toString();
      if(payroll.getFechaInicialPago() != null)
        ret[PayrollReportColumn.FECHA_INICIO.index] = payroll.getFechaInicialPago().toString();
      if(payroll.getFechaFinalPago() != null)
        ret[PayrollReportColumn.FECHA_FIN.index] = payroll.getFechaFinalPago().toString();
      if(payroll.getTotalPercepciones() != null)
        ret[PayrollReportColumn.TOTAL_PERCEPCIONES.index] = payroll.getTotalPercepciones().toString();
      if(payroll.getTotalDeducciones() != null)
        ret[PayrollReportColumn.TOTAL_DEDUCCIONES.index] = payroll.getTotalDeducciones().toString();
      if(payroll.getTotalOtrosPagos() != null)
        ret[PayrollReportColumn.TOTAL_OTROS_PAGOS.index] = payroll.getTotalOtrosPagos().toString();

      if(payroll.getTotalSueldo() != null)
        ret[PayrollReportColumn.TOTAL_SUELDO.index] = payroll.getTotalSueldo().toString();
      if(payroll.getTotalGravado() != null)
        ret[PayrollReportColumn.TOTAL_GRAVADO.index] = payroll.getTotalGravado().toString();
      if(payroll.getTotalExento() != null)
        ret[PayrollReportColumn.TOTAL_EXENTO.index] = payroll.getTotalExento().toString();

      if(payroll.getTotalOtrasDeducciones() != null)
        ret[PayrollReportColumn.TOTAL_OTRAS_DEDUCCIONES.index] = payroll.getTotalOtrasDeducciones().toString();
      if(payroll.getTotalImpuestosRetenidos() != null)
        ret[PayrollReportColumn.TOTAL_IMPUESTOS_RETENIDOS.index] = payroll.getTotalImpuestosRetenidos().toString();

      if(payroll.getUuid() != null)
        ret[PayrollReportColumn.UUID.index] = payroll.getUuid();
      if(payroll.getFileName() != null)
        ret[PayrollReportColumn.ARCHIVO.index] = payroll.getFileName();

    }
    return ret;
  }
}
