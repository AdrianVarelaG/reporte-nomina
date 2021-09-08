package com.beet.analyzer.invoice.service.impl;

import com.beet.analyzer.invoice.model.PayrollReport;
import com.beet.analyzer.invoice.service.CsvTransformerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CsvTransformerServiceImpl implements CsvTransformerService {

  private static final String COMMA = ",";
  private static final String DEFAULT_SEPARATOR = COMMA;
  private static final String DOUBLE_QUOTES = "\"";
  private static final String EMBEDDED_DOUBLE_QUOTES = "\"\"";
  private static final String NEW_LINE_UNIX = "\n";
  private static final String NEW_LINE_WINDOWS = "\r\n";

  @Override
  public List<String> convertToCsvFile(PayrollReport report) {

    return report.getFullReport().stream()
            .map(this::convertToCsvFormat)
            .collect(Collectors.toList());
  }

  public String convertToCsvFormat(final String[] line) {
    return convertToCsvFormat(line, DEFAULT_SEPARATOR);
  }

  public String convertToCsvFormat(final String[] line, final String separator) {
    return convertToCsvFormat(line, separator, false);
  }

  // if quote = true, all fields are enclosed in double quotes
  public String convertToCsvFormat(
          final String[] line,
          final String separator,
          final boolean quote) {

    return Stream.of(line)                              // convert String[] to stream
            .map(l -> formatCsvField(l, quote))         // format CSV field
            .collect(Collectors.joining(separator));    // join with a separator

  }

  private String formatCsvField(final String field, final boolean quote) {

    String result = field;

    if (result == null){
      result = DOUBLE_QUOTES + DOUBLE_QUOTES;
    } else if (result.contains(COMMA)
            || result.contains(DOUBLE_QUOTES)
            || result.contains(NEW_LINE_UNIX)
            || result.contains(NEW_LINE_WINDOWS)) {

      // if field contains double quotes, replace it with two double quotes \"\"
      result = result.replace(DOUBLE_QUOTES, EMBEDDED_DOUBLE_QUOTES);

      // must wrap by or enclosed with double quotes
      result = DOUBLE_QUOTES + result + DOUBLE_QUOTES;

    } else {
      // should all fields enclosed in double quotes
      if (quote) {
        result = DOUBLE_QUOTES + result + DOUBLE_QUOTES;
      }
    }

    return result;

  }

}
