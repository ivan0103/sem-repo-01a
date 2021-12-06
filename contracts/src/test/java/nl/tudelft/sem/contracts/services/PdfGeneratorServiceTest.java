package nl.tudelft.sem.contracts.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import java.time.LocalDate;
import java.time.LocalTime;
import nl.tudelft.sem.contracts.entities.Contract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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

    @Test
    void formatTitle() {
        //setup
        Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        titleFont.setSize(25);
        Paragraph expected = new Paragraph("Contract Of Employment", titleFont);

        //when
        Paragraph testCase = underTest.formatTitle(contract);

        //then
        assertThat(testCase.getContent()).isEqualTo(expected.getContent());
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

        //when
        Paragraph testCase = underTest.formatIntro(contract, normalFont);

        //then
        assertThat(testCase.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    void formatDetailsHeader() {
        //setup
        Font enhancedFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        enhancedFont.setSize(13);
        Paragraph expected = new Paragraph("The details are as followed:", enhancedFont);

        //when
        Paragraph testCase = underTest.formatDetailsHeader();

        //then
        assertThat(testCase.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    void formatDetails() {
        //setup
        Font normalFont = FontFactory.getFont(FontFactory.TIMES);
        normalFont.setSize(12);
        String text = "-Start date: 2020-06-01\n-End date: 2020-09-01\n"
                + "-Hours per week: 06:00\n-Hourly rate: €14.5";
        Paragraph expected = new Paragraph(text, normalFont);

        //when
        Paragraph testCase = underTest.formatDetails(contract, normalFont);

        //then
        assertThat(testCase.getContent()).isEqualTo(expected.getContent());
    }
}