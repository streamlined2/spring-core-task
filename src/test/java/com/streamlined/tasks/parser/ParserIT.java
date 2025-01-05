package com.streamlined.tasks.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.streamlined.tasks.SpringcoretaskApplication;
import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.exception.ParseException;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-integration-test.properties")
@ContextConfiguration(classes = SpringcoretaskApplication.class)
class ParserIT {

    @Autowired
    private Parser parser;

    private @Value("${source.csv.traineevalid}") String validTraineeSourceFileName;
    private @Value("${source.csv.traineenonvaliduserid}") String nonValidUserIdSourceFileName;
    private @Value("${source.csv.traineenonvalidisactive}") String nonValidIsActiveSourceFileName;
    private @Value("${source.csv.traineenonvaliddate}") String nonValidDateSourceFileName;

    @Test
    void parseShouldReturnMapOfEntities_ifSucceeds() {
        Map<Long, Trainee> expectedTraineeMap = Map
                .ofEntries(
                        Map.entry(1L,
                                new Trainee(1L, "John", "Smith", "John.Smith", "john", true, LocalDate.of(1990, 1, 1),
                                        "USA")),
                        Map.entry(2L,
                                new Trainee(2L, "Jack", "Powell", "Jack.Powell", "jack", true,
                                        LocalDate.of(1990, 2, 15), "UK")),
                        Map.entry(3L,
                                new Trainee(3L, "Robert", "Orwell", "Robert.Orwell", "robert", true,
                                        LocalDate.of(1991, 3, 10), "UK")),
                        Map.entry(4L,
                                new Trainee(4L, "Ingrid", "Kin", "Ingrid.Kin", "ingrid", true,
                                        LocalDate.of(1989, 4, 12), "Norway")),
                        Map.entry(5L, new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "kyle", true,
                                LocalDate.of(1988, 5, 18), "USA")));

        Map<Long, Trainee> traineeMap = parser.parse(Trainee.class, validTraineeSourceFileName);

        assertNotNull(traineeMap);
        assertEquals(5, traineeMap.size());
        assertEquals(expectedTraineeMap.entrySet(), traineeMap.entrySet());
    }

    @Test
    void parseShouldThrowParseException_ifSourceFileContainsNonValidUserId() {
        ParseException exc = assertThrows(ParseException.class,
                () -> parser.parse(Trainee.class, nonValidUserIdSourceFileName));
        assertEquals("Cannot parse input data", exc.getMessage());
        assertTrue(exc.getCause() instanceof RuntimeJsonMappingException);
    }

    @Test
    void parseShouldThrowParseException_ifSourceFileContainsNonValidIsActive() {
        ParseException exc = assertThrows(ParseException.class,
                () -> parser.parse(Trainee.class, nonValidIsActiveSourceFileName));
        assertEquals("Cannot parse input data", exc.getMessage());
        assertTrue(exc.getCause() instanceof RuntimeJsonMappingException);
    }

    @Test
    void parseShouldThrowParseException_ifSourceFileContainsNonValidDate() {
        ParseException exc = assertThrows(ParseException.class,
                () -> parser.parse(Trainee.class, nonValidDateSourceFileName));
        assertEquals("Cannot parse input data", exc.getMessage());
        assertTrue(exc.getCause() instanceof RuntimeJsonMappingException);
    }

}
