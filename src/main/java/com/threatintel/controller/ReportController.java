package com.threatintel.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.threatintel.entity.AttackLog;
import com.threatintel.entity.Report;
import com.threatintel.repository.AttackLogRepository;
import com.threatintel.repository.ReportRepository;
import com.threatintel.service.EmailService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class ReportController {

    private final ReportRepository reportRepository;
    private final AttackLogRepository attackLogRepository;
    private final EmailService emailService;

    public ReportController(
            ReportRepository reportRepository,
            AttackLogRepository attackLogRepository,
            EmailService emailService) {

        this.reportRepository = reportRepository;
        this.attackLogRepository = attackLogRepository;
        this.emailService = emailService;
    }

    // ==========================================
    // Reports Page
    // ==========================================

    @GetMapping("/reports")
    public String reports(Model model) {

        // Create sample reports only when table is empty
        if (reportRepository.count() == 0) {

            // Report 1
            Report r1 = new Report();

            r1.setReportName("Daily Threat Report");
            r1.setReportType("PDF");
            r1.setFileName("Daily_Threat_Report.pdf");
            r1.setFilePath("reports/Daily_Threat_Report.pdf");
            r1.setFileSize(0L);
            r1.setGeneratedBy("Admin");
            r1.setStatus("Completed");
            r1.setGeneratedDate(LocalDateTime.now());

            reportRepository.save(r1);

            // Report 2
            Report r2 = new Report();

            r2.setReportName("Weekly Attack Summary");
            r2.setReportType("Excel");
            r2.setFileName("Weekly_Attack_Summary.xlsx");
            r2.setFilePath("reports/Weekly_Attack_Summary.xlsx");
            r2.setFileSize(0L);
            r2.setGeneratedBy("Admin");
            r2.setStatus("Completed");
            r2.setGeneratedDate(LocalDateTime.now());

            reportRepository.save(r2);

            // Report 3
            Report r3 = new Report();

            r3.setReportName("Honeypot Logs");
            r3.setReportType("PDF");
            r3.setFileName("Honeypot_Logs.pdf");
            r3.setFilePath("reports/Honeypot_Logs.pdf");
            r3.setFileSize(0L);
            r3.setGeneratedBy("System");
            r3.setStatus("Completed");
            r3.setGeneratedDate(LocalDateTime.now());

            reportRepository.save(r3);
        }

        // Send reports to page
        model.addAttribute(
                "reports",
                reportRepository.findAll()
        );

        model.addAttribute(
                "totalReports",
                reportRepository.count()
        );

        return "admin/reports";
    }


    // ==========================================
    // PDF Download
    // ==========================================

    @GetMapping("/reports/pdf")
    public void downloadPdf(
            HttpServletResponse response) throws Exception {

        String fileName = "Threat_Report.pdf";

        // Save report history
        Report report = new Report();

        report.setReportName(
                "Threat_Report_" + System.currentTimeMillis()
        );

        report.setReportType("PDF");
        report.setFileName(fileName);
        report.setFilePath("reports/" + fileName);
        report.setFileSize(0L);
        report.setGeneratedBy("Admin");
        report.setStatus("Completed");
        report.setGeneratedDate(LocalDateTime.now());

        reportRepository.save(report);


        // Response settings
        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + fileName
        );


        // Get attack logs
        List<AttackLog> attacks =
                attackLogRepository.findAllByOrderByTimestampDesc();


        // Create PDF
        Document document = new Document();

        PdfWriter.getInstance(
                document,
                response.getOutputStream()
        );

        document.open();


        // ==========================================
        // Title
        // ==========================================

        document.add(
                new Paragraph(
                        "Threat Intelligence Report",
                        FontFactory.getFont(
                                FontFactory.HELVETICA_BOLD,
                                20
                        )
                )
        );

        document.add(new Paragraph(" "));


        // Generated date
        document.add(
                new Paragraph(
                        "Generated On : "
                                + LocalDateTime.now()
                )
        );

        document.add(new Paragraph(" "));


        // Project name
        document.add(
                new Paragraph(
                        "AI Based Threat Intelligence & Honeypot System"
                )
        );

        document.add(new Paragraph(" "));


        // Total attacks
        document.add(
                new Paragraph(
                        "Total Attacks : " + attacks.size()
                )
        );

        document.add(new Paragraph(" "));


        // ==========================================
        // Attack Log Table
        // ==========================================

        PdfPTable table = new PdfPTable(7);

        table.setWidthPercentage(100);

        // Header
        table.addCell("ID");
        table.addCell("IP Address");
        table.addCell("Country");
        table.addCell("City");
        table.addCell("Attack Type");
        table.addCell("Risk");
        table.addCell("Timestamp");


        // Data
        for (AttackLog attack : attacks) {

            table.addCell(
                    attack.getId() != null
                            ? String.valueOf(attack.getId())
                            : ""
            );

            table.addCell(
                    attack.getIpAddress() != null
                            ? attack.getIpAddress()
                            : ""
            );

            table.addCell(
                    attack.getCountry() != null
                            ? attack.getCountry()
                            : ""
            );

            table.addCell(
                    attack.getCity() != null
                            ? attack.getCity()
                            : ""
            );

            table.addCell(
                    attack.getAttackType() != null
                            ? attack.getAttackType()
                            : ""
            );

            table.addCell(
                    attack.getRiskLevel() != null
                            ? attack.getRiskLevel()
                            : ""
            );

            table.addCell(
                    attack.getTimestamp() != null
                            ? attack.getTimestamp().toString()
                            : ""
            );
        }


        document.add(table);

        document.add(new Paragraph(" "));

        document.add(
                new Paragraph(
                        "Report Status : Completed"
                )
        );

        document.add(new Paragraph(" "));

        document.add(
                new Paragraph(
                        "Thank You"
                )
        );


        document.close();
    }


    // ==========================================
    // Excel Download
    // ==========================================

    @GetMapping("/reports/excel")
    public void downloadExcel(
            HttpServletResponse response) throws IOException {

        String fileName = "Threat_Report.xlsx";


        // Save report history
        Report report = new Report();

        report.setReportName(
                "Threat_Report_" + System.currentTimeMillis()
        );

        report.setReportType("Excel");
        report.setFileName(fileName);
        report.setFilePath("reports/" + fileName);
        report.setFileSize(0L);
        report.setGeneratedBy("Admin");
        report.setStatus("Completed");
        report.setGeneratedDate(LocalDateTime.now());

        reportRepository.save(report);


        // Response settings
        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + fileName
        );


        // ==========================================
        // Create Excel
        // ==========================================

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Attack Logs");


        // ==========================================
        // Header
        // ==========================================

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("IP Address");
        header.createCell(2).setCellValue("Country");
        header.createCell(3).setCellValue("City");
        header.createCell(4).setCellValue("Region");
        header.createCell(5).setCellValue("Attack Type");
        header.createCell(6).setCellValue("Risk Level");
        header.createCell(7).setCellValue("Username");
        header.createCell(8).setCellValue("Request Method");
        header.createCell(9).setCellValue("Request URI");
        header.createCell(10).setCellValue("Browser");
        header.createCell(11).setCellValue("Operating System");
        header.createCell(12).setCellValue("Device Type");
        header.createCell(13).setCellValue("Timestamp");


        // ==========================================
        // Get Attack Logs
        // ==========================================

        List<AttackLog> attacks =
                attackLogRepository.findAllByOrderByTimestampDesc();


        int rowCount = 1;


        // ==========================================
        // Data
        // ==========================================

        for (AttackLog attack : attacks) {

            Row row = sheet.createRow(rowCount++);


            row.createCell(0).setCellValue(
                    attack.getId() != null
                            ? attack.getId()
                            : 0
            );


            row.createCell(1).setCellValue(
                    attack.getIpAddress() != null
                            ? attack.getIpAddress()
                            : ""
            );


            row.createCell(2).setCellValue(
                    attack.getCountry() != null
                            ? attack.getCountry()
                            : ""
            );


            row.createCell(3).setCellValue(
                    attack.getCity() != null
                            ? attack.getCity()
                            : ""
            );


            row.createCell(4).setCellValue(
                    attack.getRegion() != null
                            ? attack.getRegion()
                            : ""
            );


            row.createCell(5).setCellValue(
                    attack.getAttackType() != null
                            ? attack.getAttackType()
                            : ""
            );


            row.createCell(6).setCellValue(
                    attack.getRiskLevel() != null
                            ? attack.getRiskLevel()
                            : ""
            );


            row.createCell(7).setCellValue(
                    attack.getUsername() != null
                            ? attack.getUsername()
                            : ""
            );


            row.createCell(8).setCellValue(
                    attack.getRequestMethod() != null
                            ? attack.getRequestMethod()
                            : ""
            );


            row.createCell(9).setCellValue(
                    attack.getRequestUri() != null
                            ? attack.getRequestUri()
                            : ""
            );


            row.createCell(10).setCellValue(
                    attack.getBrowser() != null
                            ? attack.getBrowser()
                            : ""
            );


            row.createCell(11).setCellValue(
                    attack.getOperatingSystem() != null
                            ? attack.getOperatingSystem()
                            : ""
            );


            row.createCell(12).setCellValue(
                    attack.getDeviceType() != null
                            ? attack.getDeviceType()
                            : ""
            );


            row.createCell(13).setCellValue(
                    attack.getTimestamp() != null
                            ? attack.getTimestamp().toString()
                            : ""
            );
        }


        // ==========================================
        // Auto-size columns
        // ==========================================

        for (int i = 0; i < 14; i++) {
            sheet.autoSizeColumn(i);
        }


        // ==========================================
        // Write Excel
        // ==========================================

        workbook.write(
                response.getOutputStream()
        );

        workbook.close();
    }


    // ==========================================
    // Email Report
    // ==========================================

    @GetMapping("/reports/email")
    public String emailReportPage() {

        try {

            emailService.sendAlert(
                    "vishalrathodr@gmail.com",
                    "Monthly Report",
                    "System",
                    "INFO"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "redirect:/admin/reports?email=sent";
    }
}

