package com.beet.analyzer.invoice.service;

import com.beet.analyzer.invoice.model.PayrollReport;

import java.util.List;

public interface CsvTransformerService {

  List<String> convertToCsvFile(PayrollReport report);

}
