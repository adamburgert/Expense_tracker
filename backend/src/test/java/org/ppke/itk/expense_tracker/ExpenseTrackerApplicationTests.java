package org.ppke.itk.expense_tracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ppke.itk.expense_tracker.controllers.ExpenseController;
import org.ppke.itk.expense_tracker.domain.Expense;
import org.ppke.itk.expense_tracker.services.ExpenseService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExpenseTrackerApplicationTests {

	private MockMvc mockMvc;

	@Mock
	private ExpenseService expenseService;

	@InjectMocks
	private ExpenseController expenseController;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testGetAllExpenses() throws Exception {
		Expense expense1 = new Expense();
		expense1.setId(1L);
		expense1.setDescription("Test Expense 1");

		Expense expense2 = new Expense();
		expense2.setId(2L);
		expense2.setDescription("Test Expense 2");

		List<Expense> expenses = Arrays.asList(expense1, expense2);

		when(expenseService.getAllExpenses()).thenReturn(expenses);

		mockMvc.perform(get("/api/expenses"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].description").value("Test Expense 1"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].description").value("Test Expense 2"));

		verify(expenseService, times(1)).getAllExpenses();
	}

	@Test
	public void testGetExpenseById() throws Exception {
		Expense expense = new Expense();
		expense.setId(1L);
		expense.setDescription("Test Expense");

		when(expenseService.getExpenseById(1L)).thenReturn(Optional.of(expense));

		mockMvc.perform(get("/api/expenses/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.description").value("Test Expense"));

		verify(expenseService, times(1)).getExpenseById(1L);
	}

	@Test
	public void testCreateExpense() throws Exception {
		Expense expense = new Expense();
		expense.setDescription("New Expense");
		expense.setAmount(1);
		expense.setPrice(100.0);

		Expense createdExpense = new Expense();
		createdExpense.setId(1L);
		createdExpense.setDescription("New Expense");
		createdExpense.setAmount(1);
		createdExpense.setPrice(100.0);

		when(expenseService.createExpense(any(Expense.class))).thenReturn(createdExpense);

		mockMvc.perform(post("/api/expenses")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(expense)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.description").value("New Expense"));

		verify(expenseService, times(1)).createExpense(any(Expense.class));
	}

	// Add tests for other endpoints (update, delete, etc.) following the same pattern.
	// Example for delete:
	@Test
	public void testDeleteExpense() throws Exception {
		doNothing().when(expenseService).deleteExpense(1L);

		mockMvc.perform(delete("/api/expenses/1"))
				.andExpect(status().isNoContent());

		verify(expenseService, times(1)).deleteExpense(1L);
	}


}