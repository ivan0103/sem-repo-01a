package nl.tudelft.sem.contracts.services;


import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import nl.tudelft.sem.contracts.entities.Contract;
import org.springframework.stereotype.Service;


@Service
public class PdfGeneratorService {

    /**
     * This method generates a PDF of a contract.
     *
     * @param response - the HTTPServlet to attach PDF to.
     * @param contract - the contract we want to generate PDF of.
     */
    public void exportContract(HttpServletResponse response, Contract contract) {

        //Make an A4 document since that's the standard page size.
        try (Document document = new Document(PageSize.A4)) {
            //Attach document in PDF format to response.
            PdfWriter.getInstance(document, response.getOutputStream());

           createPDF(document, contract);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createPDF(Document document, Contract contract){
        //Set normal fonts to use for document.
        Font normalFont = FontFactory.getFont(FontFactory.TIMES);
        normalFont.setSize(12);

        //Declare different paragraphs of text and format them.
        Paragraph title = formatTitle(contract);

        Paragraph intro = formatIntro(contract, normalFont);

        Paragraph detailsHeader = formatDetailsHeader();

        Paragraph contractDetails = formatDetails(contract, normalFont);

        //add the paragraphs to the document and close the document.
        addToDocument(document, title, intro, detailsHeader, contractDetails);
    }

    public void addToDocument(Document document, Paragraph title, Paragraph intro,
                              Paragraph detailsHeader, Paragraph contractDetails) {
        //add the paragraphs to the document and close the document.
        document.open();
        document.add(title);
        document.add(intro);
        document.add(detailsHeader);
        document.add(contractDetails);
    }

    /**
     * Makes the title for PDF contract.
     *
     * @param contract - used to extract information for title.
     * @return Paragraph representing the title.
     */
    public Paragraph formatTitle(Contract contract) {
        //Set fonts to use for title.
        Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        titleFont.setSize(25);

        //format title.
        Paragraph title = new Paragraph("Contract Of Employment", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setExtraParagraphSpace(2);

        return title;
    }

    /**
     * Makes intro for PDF contract.
     *
     * @param contract   - used to extract information for intro.
     * @param normalFont - font to use.
     * @return Paragraph representing the intro
     */
    public Paragraph formatIntro(Contract contract, Font normalFont) {
        Paragraph intro = new Paragraph("This is an agreement made between "
            + contract.getCompanyName()
            + "[username: "
            + contract.getCompanyId()
            + "] and "
            + contract.getFreelancerName()
            + "[NetID: "
            + contract.getFreelancerId()
            + "] made on "
            + contract.getAgreementDate().toString()
            + ". The details of this agreement may be modified(to an extent) in"
            + " the future upon the approval of both parties.", normalFont);
        intro.setAlignment(Paragraph.ALIGN_CENTER);
        intro.setExtraParagraphSpace(5);

        return intro;
    }

    /**
     * Makes a header for details part of PDF contract.
     *
     * @return Paragraph representing the header for details
     */
    public Paragraph formatDetailsHeader() {
        Font enhancedFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        enhancedFont.setSize(13);

        Paragraph detailsHeader = new Paragraph("The details are as followed:", enhancedFont);
        detailsHeader.setAlignment(Paragraph.ALIGN_LEFT);

        return detailsHeader;
    }

    /**
     * Makes details part of PDF contract.
     *
     * @param contract   - used to extract information for intro.
     * @param normalFont - font to use.
     * @return Paragraph representing the details of contract
     */
    public Paragraph formatDetails(Contract contract, Font normalFont) {
        Paragraph contractDetails = new Paragraph("-Start date: "
            + contract.getStartDate().toString()
            + "\n-End date: "
            + contract.getEndDate().toString()
            + "\n-Hours per week: "
            + contract.getHoursPerWeek().toString()
            + "\n-Hourly rate: €"
            + contract.getPayPerWeek(), normalFont);

        contractDetails.setAlignment(Paragraph.ALIGN_LEFT);
        contractDetails.setIndentationRight(5);

        return contractDetails;
    }
}
