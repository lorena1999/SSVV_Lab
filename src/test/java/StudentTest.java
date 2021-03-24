import console.UI;
import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertEquals;

// hmm.. eu zic sa faci alta clasa sa vezi daca merge altfel. si tot adaugi pe ea
// ok
public class StudentTest {

    private Service service;

    @BeforeEach
    public void setUp() throws Exception {
        Path source = Paths.get("studenti.xml");
        Files.copy(source, source.resolveSibling("studenti_backup.xml"), StandardCopyOption.REPLACE_EXISTING);

        Path source2 = Paths.get("studenti_testing.xml");
        Files.copy(source2, source2.resolveSibling("studenti.xml"), StandardCopyOption.REPLACE_EXISTING);

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);

    }

    @Test
    public void testIdUnique()
    {
        assertEquals(1, service.saveStudent("3", "Petrica", 500), "If the id is unique the student should be added");
    }

    @Test
    public void testIdNotUnique()
    {
        assertEquals(0, service.saveStudent("4", "Petrica", 501), "If the id is NOT unique the student should not be added");
    }

    @Test
    public void testIdNotEmpty()
    {
        assertEquals(1, service.saveStudent("5", "Petrica", 502), "If the id is NOT empty the student should be added");
    }

    @Test
    public void testIdEmpty()
    {
        assertEquals(0, service.saveStudent("", "Petrica", 503), "If the id is empty the student should NOT be added");
    }

    @Test
    public void testNameNotEmpty()
    {
        assertEquals(1, service.saveStudent("7", "Petrica", 504), "If the name is NOT empty the student should be added");
    }

    @Test
    public void testNameEmpty()
    {
        assertEquals(0, service.saveStudent("8", "", 505), "If the name is empty the student should NOT be added");
    }

    @Test
    public void testGroupMinValueMinus1()
    {
        assertEquals(0, service.saveStudent("12", "Petrica", 109), "If the group is lower than min value, the student should NOT be added");
    }

    @Test
    public void testGroupMinValue()
    {
        assertEquals(1, service.saveStudent("13", "Petrica", 110), "If the group is equal to min value, the student should be added");
    }

    @Test
    public void testGroupMinValuePlus1()
    {
        assertEquals(1, service.saveStudent("14", "Petrica", 111), "If the group is higher than min value, the student should be added");
    }

    @Test
    public void testGroupMaxValueMinus1()
    {
        assertEquals(1, service.saveStudent("15", "Petrica", 937), "If the group is lower than max value, the student should be added");
    }

    @Test
    public void testGroupMaxValue()
    {
        assertEquals(1, service.saveStudent("16", "Petrica", 938), "If the group is equal to max value, the student should be added");
    }

    @Test
    public void testGroupMaxValuePlus1()
    {
        assertEquals(0, service.saveStudent("17", "Petrica", 939), "If the group is higher than max value, the student should NOT be added");
    }


//    @AfterEach
//    public void tearDown() throws Exception
//    {
//        Path source = Paths.get("studenti_backup.xml");
//        Files.copy(source, source.resolveSibling("studenti.xml"), StandardCopyOption.REPLACE_EXISTING);
//
//        Files.delete(source.resolveSibling("studenti_backup.xml"));
//
//    }


}
