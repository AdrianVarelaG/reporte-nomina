package com.beet.analyzer.invoice.service;

import java.io.FileInputStream;
import java.util.List;

public interface ReadFileService {
  List<FileInputStream> readFilesFromPath(String path, String ext);
}
