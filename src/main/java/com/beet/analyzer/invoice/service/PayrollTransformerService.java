package com.beet.analyzer.invoice.service;

import com.beet.analyzer.invoice.model.FileNameInputStream;
import com.beet.analyzer.invoice.model.Payroll;


public interface PayrollTransformerService {

  Payroll toPayroll(FileNameInputStream fileNameInput);
}
