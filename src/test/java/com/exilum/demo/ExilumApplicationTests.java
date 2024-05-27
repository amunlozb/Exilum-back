package com.exilum.demo;

import com.exilum.demo.model.Scarab;
import com.exilum.demo.repository.ScarabRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExilumApplicationTests {

    @Autowired
    private ScarabRepository scarabRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testDatabaseConnection() {
        Scarab testScarab = new Scarab(22L, "Scarab of the Generals", "Legion", 3, 19.99, "https://test.com");
        scarabRepository.save(testScarab);

        // Perform a simple database query
        List<Scarab> scarabs = scarabRepository.findAll();

        // Log the scarabs to verify they are being saved
        System.out.println("Scarabs in the database: " + scarabs);

        // Assert that the fetched list is not null or empty
        assertNotNull(scarabs);
        assertFalse(scarabs.isEmpty());

        // Assert that the testScarab is in the list
        boolean found = scarabs.stream().anyMatch(s -> s.getName().equals("Scarab of the Generals"));
        assertTrue(found, "Test scarab should be found in the database");
    }
}
