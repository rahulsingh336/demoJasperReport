package com.example.demoJasperReport.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demoJasperReport.model.Employee;
import com.example.demoJasperReport.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/api")
public class EmployeeController {

    final Logger log = LoggerFactory.getLogger(this.getClass());
    final ModelAndView model = new ModelAndView();

    // @Autowired annotation provides the automatic dependency injection.
    @Autowired
    EmployeeService eservice;

    // Method to display the index page of the application.
    @GetMapping(value= "/welcome")
    public ModelAndView index() {
        log.info("Showing the welcome page.");
        model.setViewName("welcome");
        return model;
    }

    // Method to create the pdf report via jasper framework.
    @GetMapping(value = "/view")
    public void viewReport(ModelAndView model, HttpServletResponse response) throws IOException, JRException {
        log.info("Preparing the pdf report via jasper.");
      /*  try {
            createPdfReport(eservice.findAll());
            log.info("File successfully saved at the given path.");
        } catch (final Exception e) {
            log.error("Some error has occurred while preparing the employee pdf report.");
            e.printStackTrace();
        }*/
        JasperPrint jasperPrint = null;

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"users.pdf\""));

        OutputStream out = response.getOutputStream();
        jasperPrint = createPdfReport(eservice.findAll());
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
     /*   // Returning the view name as the index page for ease.
        model.setViewName("welcome");
        return model;*/
    }

    // Method to create the pdf file using the employee list datasource.
    private JasperPrint createPdfReport(final List<Employee> employees) throws JRException {
        // Fetching the .jrxml file from the resources folder.
        final InputStream stream = this.getClass().getResourceAsStream("/report.jrxml");

        // Compile the Jasper report from .jrxml to .japser
        final JasperReport report = JasperCompileManager.compileReport(stream);

        // Fetching the employees from the data source.
        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

        // Adding the additional parameters to the pdf.
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "rs");

        // Filling the report with the employee data and additional parameters information.
       return JasperFillManager.fillReport(report, parameters, source);

     /*   // Users can change as per their project requrirements or can take it as request input requirement.
        // For simplicity, this tutorial will automatically place the file under the "c:" drive.
        // If users want to download the pdf file on the browser, then they need to use the "Content-Disposition" technique.
        final String filePath = "\\";
        // Export the report to a PDF file.
        JasperExportManager.exportReportToPdfFile(print, filePath + "Employee_report.pdf");*/
    }
}
