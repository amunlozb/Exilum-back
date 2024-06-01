package com.exilum.demo;

import com.exilum.demo.model.Scarab;
import com.exilum.demo.repository.ScarabRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExilumApplicationTests {

    @Autowired
    private ScarabRepository scarabRepository;
}
