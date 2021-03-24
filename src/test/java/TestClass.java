import domain.Nota;
import domain.Student;
import domain.Tema;
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

public class TestClass {

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

}
