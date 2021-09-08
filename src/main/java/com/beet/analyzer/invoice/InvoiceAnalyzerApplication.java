package com.beet.analyzer.invoice;

import com.beet.analyzer.invoice.service.PayrollService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class InvoiceAnalyzerApplication implements CommandLineRunner {

  PayrollService payrollService;

	public static void main(String[] args) {
		//SpringApplication.run(InvoiceAnalizerApplication.class, args);
      new SpringApplicationBuilder(InvoiceAnalyzerApplication.class)
              .web(WebApplicationType.NONE)
              .run(args);
	}

  @Autowired
  public InvoiceAnalyzerApplication(PayrollService payrollService) {
    this.payrollService = payrollService;
  }

  /**
   *
   * @param args
   *     0 - Path to the files like "/Users/cesar.varela/Proyectos/Study/Java/nominas"
   * @throws Exception
   */
  @Override
  public void run(String[] args) throws Exception {

    if(args.length < 1) {
      log.error("Please include de argument for the directory where the xml are!");
      log.error("java -jar reporte-nomina path");
      //throw new IllegalArgumentException("Invalid argument");
    }
    log.info("reading files from {}", args[0] );

    payrollService.generateReport(args[0]);

/*
    File basePath = new File(args[0]);
    File[] files = basePath.listFiles( (File dir, String name) -> name.endsWith(".xml"));

    if(files != null) {
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
      }).sorted(Comparator.comparing(cn -> cn.getFechaPago().getFecha()))
      .collect(Collectors.toList());

      //System.out.println(",,,,,,,PercepcionesTot,,, DeduccionesTot,, Percepcipones,,,Deducciones,,OtrosPagos,, ");
      System.out.println("RFC,Fecha Pago, Fecha Inicio, Fecha Fin, TotalPercepciones, TotalDeducciones, TotalOtrosPagos,"
              + "TotalSueldos, TotalGravado, TotalExento,TotalOtrasDeducciones, TotalImpuestosRetenidos ");
              //", concepto, importeGravado, ImporteExento, Concepto, Importe"

      lcm.forEach(n -> {

        StringBuilder sb = new StringBuilder(n.getEmisor().getRfcPatronOrigen().getvalor() + ",")
                .append(n.getFechaPago().getFecha().toString() + ",")
                .append(n.getFechaInicialPago().getFecha().toString() + ",")
                .append(n.getFechaFinalPago().getFecha().toString() + ",")
                .append(n.getTotalPercepciones().getValor() + ",")
                .append(n.getTotalDeducciones().getValor() + ",");
        if (n.getTotalOtrosPagos() != null)
          sb.append(n.getTotalOtrosPagos().getValor() + ",");
        else
          sb.append(",");
        sb.append(n.getPercepciones().getTotalSueldo().getValor() + ",")
                .append(n.getPercepciones().getTotalGravado().getValor() + ",")
                .append(n.getPercepciones().getTotalExento().getValor() + ",");
        if (n.getDeducciones().getTotalOtrasDeducciones() != null)
          sb.append(n.getDeducciones().getTotalOtrasDeducciones().getValor() + ",");
        else
          sb.append("0.00,");
        if (n.getDeducciones().getTotalImpuestosRetenidos() != null)
          sb.append(n.getDeducciones().getTotalImpuestosRetenidos().getValor() + ",");
        else
          sb.append("0.00,");

        if (n.getOtrosPagos() != null) {

        }
        System.out.println(sb.toString());

        for (int i = 0; i < n.getPercepciones().getPercepciones().size(); i++) {
          ToPercepciones(sb, n.getPercepciones().getPercepciones().get(i));

          if (i < n.getDeducciones().getDeducciones().size()) {
            ToDeducciones(sb, n.getDeducciones().getDeducciones().get(i));
          }
          if (n.getOtrosPagos() != null && i < n.getOtrosPagos().getOtrosPagos().size()) {
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
*/
  }

	
}
