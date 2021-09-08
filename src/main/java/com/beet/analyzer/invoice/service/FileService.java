package com.beet.analyzer.invoice.service;

import com.beet.analyzer.invoice.model.FileNameInputStream;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
  List<FileNameInputStream> readFilesFromPath(String path, String ext);
  void writeToFile(List<String> content, File file) throws IOException;
}
