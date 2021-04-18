package com.beet.analizer.invoice;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.beet.model.invoice.assembler.comprobante.ComprobanteAssembler;
import com.beet.model.invoice.assembler.nomina.NominaAssembler;
import com.beet.model.invoice.model.Rfc;
import com.beet.model.invoice.model.comprobante.EntidadComprobante;
import com.beet.model.invoice.model.nomina.ComplementoNomina;
import com.beet.model.invoice.model.nomina.Deduccion;
import com.beet.model.invoice.model.nomina.OtroPago;
import com.beet.model.invoice.model.nomina.OtrosPagos;
import com.beet.model.invoice.model.nomina.Percepcion;
import com.beet.model.invoice.xml.model.comprobante.Comprobante;
import com.beet.model.invoice.xml.model.nomina.Nomina;
import com.beet.model.invoice.xml.utils.ComprobanteUtils;

@Slf4j
@SpringBootApplication
public class InvoiceAnalizerApplication implements CommandLineRunner {

  @Autowired
  private JAXBContext context;
	public static void main(String[] args) {
		SpringApplication.run(InvoiceAnalizerApplication.class, args);
		
	}

  @Override
  public void run(String... args) throws Exception {
    File basePath = new File("/home/varela/Documents/Declaracion_2020/Facturas/");
    File[] files = basePath.listFiles( (File dir, String name) -> name.endsWith(".xml"));

    List<ComplementoNomina> lcm = Arrays.stream(files).map(xmlFile -> {
      Unmarshaller jaxbUnMarshaller;
      try {
        log.info("file: {}", xmlFile);
        jaxbUnMarshaller = context.createUnmarshaller();
        Comprobante c = (Comprobante) jaxbUnMarshaller.unmarshal(xmlFile);

        Nomina n = (Nomina) ComprobanteUtils.obtenComplemento(c, Nomina.class);
        //EntidadComprobante ec = ComprobanteAssembler.toEntidadComprobante(c);
        ComplementoNomina cn = NominaAssembler.toComplementoNomina(n);
        cn.getEmisor().setRfcPatronOrigen(new Rfc(c.getEmisor().getRfc()));
        return cn;
      } catch (Exception e) {
        // TODO Auto-generated catch block
        log.error("xmlFile: {}", xmlFile);
        log.error(e.getMessage(), e);
        return null;
      }
    }).collect(Collectors.toList());

    System.out.println(",,,,,,,PercepcionesTot,,, DeduccionesTot,, Percepcipones,,,Deducciones,,OtrosPagos,, ");
    System.out.println("RFC,Fecha Pago, Fecha Inicio, Fecha Fin, TotalPercepciones, TotalDeducciones, TotalOtrosPagos,"
        + "TotalSueldos, TotalGravado, TotalExento,TotalOtrasDeducciones, TotalImpuestosRetenidos, concepto, importeGravado, ImporteExento, Concepto, Importe");
    
    lcm.forEach(n -> {
      
      StringBuilder sb = new StringBuilder(n.getEmisor().getRfcPatronOrigen().getvalor() + ",")
      									  .append(n.getFechaPago().getFecha().toString() + ",")
                                          .append(n.getFechaInicialPago().getFecha().toString() + ",")
                                          .append(n.getFechaFinalPago().getFecha().toString() + ",")
                                          .append( n.getTotalPercepciones().getValor() + ",")
                                          .append( n.getTotalDeducciones().getValor() + "," );
      if(n.getTotalOtrosPagos() != null)
        sb.append(n.getTotalOtrosPagos().getValor() + ",");
      else
        sb.append(",");
      sb.append(n.getPercepciones().getTotalSueldo().getValor() + ",")
        .append(n.getPercepciones().getTotalGravado().getValor() + ",")
        .append(n.getPercepciones().getTotalExento().getValor() + ",");
        if(n.getDeducciones().getTotalOtrasDeducciones() != null )
          sb.append(n.getDeducciones().getTotalOtrasDeducciones().getValor() +",");
        else
          sb.append("0.00,");
        if(n.getDeducciones().getTotalImpuestosRetenidos() != null)
          sb.append(n.getDeducciones().getTotalImpuestosRetenidos().getValor() + ",");
        else
          sb.append("0.00,");
      
      if(n.getOtrosPagos() != null) {
        
      }
      
      
      
      for(int i= 0; i < n.getPercepciones().getPercepciones().size(); i++) {
        ToPercepciones(sb, n.getPercepciones().getPercepciones().get(i) );
        
        if(i < n.getDeducciones().getDeducciones().size()) {
          ToDeducciones(sb, n.getDeducciones().getDeducciones().get(i));
        }
        if(n.getOtrosPagos() != null && i < n.getOtrosPagos().getOtrosPagos().size()) {
          ToOtrosPagos(sb, n.getOtrosPagos().getOtrosPagos().get(i));
        }
        
        System.out.println(sb.toString());
        sb = new StringBuilder(n.getEmisor().getRfcPatronOrigen().getvalor() + ",")
        	.append(n.getFechaPago().getFecha().toString() + ",")
            .append(n.getFechaInicialPago().getFecha().toString() + ",")
            .append(n.getFechaFinalPago().getFecha().toString() + ",");
        sb.append(",,,,,,,,");
      }
      
    });
   

    
  }
  
  private static void ToPercepciones(StringBuilder sb, Percepcion p ) {
    sb.append(p.getConcepto().getvalor() + ",")
      .append(p.getImporteGravado().getValor() + ",")
      .append(p.getImporteExecto().getValor() + ",");
  }

  private static void ToDeducciones(StringBuilder sb, Deduccion d ) {
    sb.append(d.getConcepto().getvalor() +  ",")
      .append(d.getImporte().getValor() + ",");
  }
  
  private static void ToOtrosPagos(StringBuilder sb, OtroPago o) {
    sb.append(o.getConcepto().getvalor() + ",")
      .append(o.getImporte().getValor() + ",");
  }
	
}
