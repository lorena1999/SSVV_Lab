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
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemaTest {

    private Service service;

    @BeforeEach
    public void setUp() throws Exception {
        Path source = Paths.get("teme.xml");
        Files.copy(source, source.resolveSibling("teme_backup.xml"), StandardCopyOption.REPLACE_EXISTING);

        Path source2 = Paths.get("teme_testing.xml");
        Files.copy(source2, source2.resolveSibling("teme.xml"), StandardCopyOption.REPLACE_EXISTING);

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }


    @Test
    public void testDescriptionEmpty()
    {
        assertEquals(0, service.saveTema("2", "", 5, 2), "If the description is empty the assignment should NOT be added");
    }

    @Test
    public void testDescriptionNotEmpty()
    {
        assertEquals(1, service.saveTema("2", "Desc1", 5, 2), "If the description is NOT empty the assignment should be added");
    }



    @AfterEach
    public void tearDown() throws Exception
    {
        Path source = Paths.get("teme_backup.xml");
        Files.copy(source, source.resolveSibling("teme.xml"), StandardCopyOption.REPLACE_EXISTING);

        Files.delete(source.resolveSibling("teme_backup.xml"));

    }

}
