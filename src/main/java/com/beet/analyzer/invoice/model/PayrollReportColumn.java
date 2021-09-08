package com.beet.analyzer.invoice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayrollReportColumn {
  RFC("RFC", 0),
  FECHA_PAGO("Fecha pago", 1),
  FECHA_INICIO("Fecha inicio", 2),
  FECHA_FIN("Fecha fin", 3),
  TOTAL_PERCEPCIONES("TotalPercepciones",4),
  TOTAL_DEDUCCIONES("TotalDeducciones", 5),
  TOTAL_OTROS_PAGOS("TotalOtrosPagos", 6),
  TOTAL_SUELDO("TotalSueldos", 7),
  TOTAL_GRAVADO("TotalGravado",8),
  TOTAL_EXENTO("TotalExento", 9),
  TOTAL_OTRAS_DEDUCCIONES("TotalOtrasDeducciones", 10),
  TOTAL_IMPUESTOS_RETENIDOS("TotalImpuestosRetenidos", 11),
  UUID("uuid", 12),
  ARCHIVO("Archivo", 13)
  ;

  public final String label;
  public final int index;
}
