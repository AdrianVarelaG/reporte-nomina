package com.beet.analyzer.invoice.model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
public class PayrollReport {
  private static String[] headers;
  private List<String[]> rows = new ArrayList<>();

  static {
    headers = new String[PayrollReportColumn.values().length];
    for (PayrollReportColumn column: PayrollReportColumn.values()) {
      headers[column.getIndex()] = column.getLabel();
    }
  }

  public void addRow(String[] row) {
    this.rows.add(row);
  }

  public List<String[]> getFullReport(){
    List<String[]> report = this.rows.stream().collect(Collectors.toList());
    report.add(0,headers);
    return report;
  }
}
