package com.beet.analyzer.invoice.service.impl;

import com.beet.analyzer.invoice.model.FileNameInputStream;
import com.beet.analyzer.invoice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FIleServiceImpl implements FileService {
  @Override
  public List<FileNameInputStream> readFilesFromPath(String path, String ext) {

    File basePath = new File(path);

    if (basePath.isDirectory()) {
      File[] files = basePath.listFiles( (File dir, String name) -> name.endsWith(ext));
      if(files != null) {
        return Arrays.stream(files).map(f -> {
          try {
            return new FileNameInputStream(new FileInputStream(f), f.getName());
          } catch (FileNotFoundException e) {
            log.error("File {} can't be read", f.getName());
            return null;
          }
        }).filter(f -> f!= null).collect(Collectors.toList());
      }else {
        log.error("there are no files in the directory with extension {}", ext);
      }
    } else {
      log.error("the path {} is not a directory", basePath.getPath());
    }

    return null;
  }

  @Override
  public void writeToFile(List<String> content, File file) throws IOException {
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
      for(String line: content) {
        bw.write(line);
        bw.newLine();
      }
    }
  }
}
