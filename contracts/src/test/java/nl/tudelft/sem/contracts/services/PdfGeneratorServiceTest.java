package nl.tudelft.sem.contracts.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nl.tudelft.sem.contracts.entities.Contract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class PdfGeneratorServiceTest {
    private transient Contract contract;

    private transient PdfGeneratorService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PdfGeneratorService();

        contract = new Contract(
                "1",
                "2",
                "student",
                "company",
                LocalTime.of(6, 0),
                14.5F,
                LocalDate.of(2020, 6, 1),
                LocalDate.of(2020, 9, 1)
        );
    }

    @Disabled
    @Test
    void exportContract() {
    }

    @Disabled
    @Test
    void createPdf() {

    }

    @Test
    void addToDocument() {
        //setup
        Font normalFont = FontFactory.getFont(FontFactory.TIMES);
        normalFont.setSize(12);

        Paragraph title = underTest.formatTitle(contract);
        Paragraph intro = underTest.formatIntro(contract, normalFont);
        Paragraph detailsHeader = underTest.formatDetailsHeader();
        Paragraph contractDetails = underTest.formatDetails(contract, normalFont);

        ArgumentCaptor<Paragraph> paragraphArgumentCaptor =
                ArgumentCaptor.forClass(Paragraph.class);
        //when
        try (Document document = mock(Document.class)) {

            underTest.addToDocument(document, title, intro, detailsHeader, contractDetails);

            //then
            verify(document).open();
            verify(document, times(4)).add(paragraphArgumentCaptor.capture());

            List<Paragraph> capturedParagraphs = paragraphArgumentCaptor.getAllValues();

            assertThat(capturedParagraphs.get(0)).isEqualTo(title);
            assertThat(capturedParagraphs.get(1)).isEqualTo(intro);
            assertThat(capturedParagraphs.get(2)).isEqualTo(detailsHeader);
            assertThat(capturedParagraphs.get(3)).isEqualTo(contractDetails);
        }
    }

    @Test
    void formatTitle() {
        //setup
        Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        titleFont.setSize(25);
        Paragraph expected = new Paragraph("Contract Of Employment", titleFont);

        expected.setAlignment(Paragraph.ALIGN_CENTER);
        expected.setExtraParagraphSpace(2);

        //when
        Paragraph testCase = underTest.formatTitle(contract);

        //then
        assertThat(testCase.getContent()).isEqualTo(expected.getContent());

        assertThat(testCase.getFont().getSize()).isEqualTo(expected.getFont().getSize());
        assertThat(testCase.getFont().getBaseFont()).isEqualTo(
                expected.getFont().getBaseFont());

        assertThat(testCase.getAlignment()).isEqualTo(expected.getAlignment());
        assertThat(testCase.getExtraParagraphSpace()).isEqualTo(
                expected.getExtraParagraphSpace());
    }

    @Test
    void formatIntro() {
        //setup
        Font normalFont = FontFactory.getFont(FontFactory.TIMES);
        normalFont.setSize(12);
        contract.setAgreementDate(LocalDate.of(2022, 12, 2));
        String text = "This is an agreement made between company[username: 2] and "
                + "student[NetID: 1] made on 2022-12-02. The details of this agreement"
                + " may be modified(to an extent) in the future upon the approval of both parties.";
        Paragraph expected = new Paragraph(text, normalFont);

        expected.setAlignment(Paragraph.ALIGN_CENTER);
        expected.setExtraParagraphSpace(5);

        //when
        Paragraph testCase = underTest.formatIntro(contract, normalFont);

        //then
        assertThat(testCase.getContent()).isEqualTo(expected.getContent());

        assertThat(testCase.getFont().getSize()).isEqualTo(expected.getFont().getSize());
        assertThat(testCase.getFont().getBaseFont()).isEqualTo(
                expected.getFont().getBaseFont());

        assertThat(testCase.getAlignment()).isEqualTo(expected.getAlignment());
        assertThat(testCase.getExtraParagraphSpace()).isEqualTo(
                expected.getExtraParagraphSpace());
    }

    @Test
    void formatDetailsHeader() {
        //setup
        Font enhancedFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        enhancedFont.setSize(13);
        Paragraph expected = new Paragraph("The details are as followed:", enhancedFont);

        expected.setAlignment(Paragraph.ALIGN_LEFT);

        //when
        Paragraph testCase = underTest.formatDetailsHeader();

        //then
        assertThat(testCase.getContent()).isEqualTo(expected.getContent());

        assertThat(testCase.getFont().getSize()).isEqualTo(expected.getFont().getSize());
        assertThat(testCase.getFont().getBaseFont()).isEqualTo(
                expected.getFont().getBaseFont());

        assertThat(testCase.getAlignment()).isEqualTo(expected.getAlignment());
        assertThat(testCase.getExtraParagraphSpace()).isEqualTo(
                expected.getExtraParagraphSpace());
    }

    @Test
    void formatDetails() {
        //setup
        Font normalFont = FontFactory.getFont(FontFactory.TIMES);
        normalFont.setSize(12);
        String text = "-Start date: 2020-06-01\n-End date: 2020-09-01\n"
                + "-Hours per week: 06:00\n-Hourly rate: €14.5";
        Paragraph expected = new Paragraph(text, normalFont);

        expected.setAlignment(Paragraph.ALIGN_LEFT);
        expected.setIndentationRight(5);

        //when
        Paragraph testCase = underTest.formatDetails(contract, normalFont);

        //then
        assertThat(testCase.getContent()).isEqualTo(expected.getContent());

        assertThat(testCase.getFont().getSize()).isEqualTo(expected.getFont().getSize());
        assertThat(testCase.getFont().getBaseFont()).isEqualTo(
                expected.getFont().getBaseFont());

        assertThat(testCase.getAlignment()).isEqualTo(expected.getAlignment());
        assertThat(testCase.getIndentationRight()).isEqualTo(
                expected.getIndentationRight());
    }
}