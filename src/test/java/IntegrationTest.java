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

public class IntegrationTest {

    private Service service;

    private void setUpFile(String realFile, String testingFile, String backupFile) throws Exception
    {
        Path source = Paths.get(realFile);
        Files.copy(source, source.resolveSibling(backupFile), StandardCopyOption.REPLACE_EXISTING);

        Path source2 = Paths.get(testingFile);
        Files.copy(source2, source2.resolveSibling(realFile), StandardCopyOption.REPLACE_EXISTING);
    }


    @BeforeEach
    public void setUp() throws Exception {

        setUpFile("studenti.xml", "studenti_testing.xml", "studenti_backup.xml");
        setUpFile("teme.xml", "teme_testing.xml", "teme_backup.xml");
        setUpFile("note.xml", "note_testing.xml", "note_backup.xml");

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }


    @Test
    public void testAddStudent()
    {
        assertEquals(1, service.saveStudent("3", "Petrica", 500), "If the id is unique the student should be added");
    }

    @Test
    public void testAddTema()
    {
        assertEquals(1, service.saveTema("5", "Desc1", 5, 2), "If the description is NOT empty the assignment should be added");
    }

    @Test //String idStudent, String idTema, double valNota, int predata, String feedback
    public void testAddNota()
    {
        assertEquals(1, service.saveNota("1", "3", 9.5, 9, "Good"));
    }

    @Test //String idStudent, String idTema, double valNota, int predata, String feedback
    public void testIntegration()
    {
        service.saveStudent("3", "Petrica", 500);
        service.saveTema("5", "Desc1", 5, 2);

        assertEquals(1, service.saveNota("3", "5", 9.5, 5, "Good"));
    }


    private void tearDownFile(String realFile, String backupFile) throws Exception
    {
        Path source = Paths.get(backupFile);
        Files.copy(source, source.resolveSibling(realFile), StandardCopyOption.REPLACE_EXISTING);

        Files.delete(source.resolveSibling(backupFile));
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        tearDownFile("studenti.xml", "studenti_backup.xml");
        tearDownFile("note.xml", "note_backup.xml");
        tearDownFile("teme.xml", "teme_backup.xml");
    }

}
