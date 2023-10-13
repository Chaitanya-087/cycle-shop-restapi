package com.api.cycleshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.api.cycleshop.entity.Cycle;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CycleshopApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestCycleRepository testCycleRepository;

	private String baseURL = "http://localhost";
	private static RestTemplate restTemplate;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();

	}

	@BeforeEach
	public void setUp() {
		baseURL = baseURL.concat(":").concat(port + "").concat("/api/cycles");
	}

	@Test
	@Sql(statements = "INSERT INTO cycles (brand,stock, num_borrowed, price) VALUES ('TestBrand', 10, 0, 1000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM cycles WHERE brand = 'TestBrand'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testGetAllCycles() {
		List<Cycle> cycles = restTemplate.getForObject(baseURL, List.class);
		assertEquals(1, cycles.size());
		assertEquals(1, testCycleRepository.findAll().size());
	}

}
