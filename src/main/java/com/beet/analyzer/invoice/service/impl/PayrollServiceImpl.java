package com.beet.analyzer.invoice.service.impl;

import com.beet.analyzer.invoice.model.FileNameInputStream;
import com.beet.analyzer.invoice.model.Payroll;
import com.beet.analyzer.invoice.model.PayrollReport;
import com.beet.analyzer.invoice.service.CsvTransformerService;
import com.beet.analyzer.invoice.service.FileService;
import com.beet.analyzer.invoice.service.PayrollService;
import com.beet.analyzer.invoice.service.PayrollTransformerService;
import com.beet.analyzer.invoice.transformer.PayrollReportTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PayrollServiceImpl implements PayrollService {

  private final String EXTENSION = ".xml";
  private FileService fileService;
  private PayrollTransformerService transformerService;
  private CsvTransformerService csvTransformerService;

  @Autowired
  public PayrollServiceImpl(FileService fileService,
                            PayrollTransformerService transformerService,
                            CsvTransformerService csvTransformerService){
    this.fileService = fileService;
    this.transformerService = transformerService;
    this.csvTransformerService = csvTransformerService;
  }

  @Override
  public void generateReport(String path) {
    List<FileNameInputStream> files = fileService.readFilesFromPath(path,EXTENSION);

    List<Payroll> payrolls = files.stream()
            .map(file -> this.transformerService.toPayroll(file))
            .filter(p -> p != null)
            .sorted(Comparator.comparing(p -> p.getFechaPago()))
            .collect(Collectors.toList());

    PayrollReport payrollReport = new PayrollReport();
    payrolls.stream().forEach( payroll -> {
      payrollReport.addRow(PayrollReportTransformer.toPayrollReportRow(payroll));
    });
    List<String> csvContent = csvTransformerService.convertToCsvFile(payrollReport);

    try {
      this.fileService.writeToFile(csvContent, new File(path , "report.csv"));
    } catch (IOException e) {
      log.error("Error when try to write output file", e);
    }
  }
}
