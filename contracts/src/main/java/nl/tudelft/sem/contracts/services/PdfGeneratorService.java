package nl.tudelft.sem.contracts.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import nl.tudelft.sem.contracts.entities.Contract;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PdfGeneratorService {
    public void exportContract(HttpServletResponse response, Contract contract) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        titleFont.setSize(25);

        Font normalFont = FontFactory.getFont(FontFactory.TIMES);
        normalFont.setSize(12);

        Font enhancedFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        enhancedFont.setSize(13);


        Paragraph title = new Paragraph("Contract Of Employment", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setExtraParagraphSpace(2);

        Paragraph intro = new Paragraph("This is an agreement made between " +
                contract.getCompanyName() +
                "[username: " +
                contract.getCompanyId() +
                "] and " +
                contract.getFreelancerName() +
                "[NetID: " +
                contract.getFreelancerId() +
                "] made on " +
                contract.getAgreementDate().toString() +
                ". The details of this agreement may be modified(to an extent) in" +
                " the future upon the approval of both parties.", normalFont);
        intro.setAlignment(Paragraph.ALIGN_CENTER);
        intro.setExtraParagraphSpace(5);

        Paragraph detailsHeader = new Paragraph("The details are as followed:",enhancedFont);
        detailsHeader.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph contractDetails = new Paragraph("-Start date: " +
                contract.getStartDate().toString() +
                "\n-End date: " +
                contract.getEndDate().toString() +
                "\n-Hours per week: " +
                contract.getHoursPerWeek().toString() +
                "\n-Hourly rate: " +
                contract.getPayPerWeek(), normalFont);

        contractDetails.setAlignment(Paragraph.ALIGN_LEFT);
        contractDetails.setIndentationRight(5);

        document.add(title);
        document.add(intro);
        document.add(detailsHeader);
        document.add(contractDetails);
        document.close();
    }
}
