package com.beet.analyzer.invoice.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Payroll {
  private String rfcEmisor;
  private LocalDate fechaPago;
  private LocalDate fechaInicialPago;
  private LocalDate fechaFinalPago;
  private BigDecimal totalPercepciones;
  private BigDecimal totalDeducciones;
  private BigDecimal totalOtrosPagos;
  private BigDecimal totalSueldo;
  private BigDecimal totalGravado;
  private BigDecimal totalExento;
  private BigDecimal totalOtrasDeducciones;
  private BigDecimal totalImpuestosRetenidos;

  private String uuid;
  private String fileName;
}
