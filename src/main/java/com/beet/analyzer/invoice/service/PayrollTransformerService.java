package com.beet.analyzer.invoice.service;

import com.beet.analyzer.invoice.model.FileNameInputStream;
import com.beet.model.invoice.xml.model.comprobante.Comprobante;

public interface ComprobanteTransformerService {

  Comprobante toComprobante(FileNameInputStream fileNameInput);
}
