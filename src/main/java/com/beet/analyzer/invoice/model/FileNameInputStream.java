package com.beet.analyzer.invoice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.FileInputStream;
@Getter
@AllArgsConstructor
public class FileNameInputStream {
  private final FileInputStream fileInputStream;
  private final String name;
}
