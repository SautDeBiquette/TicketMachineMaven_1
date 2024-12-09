package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}


	@Test
		// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	void insertNotEnoughMoneyNoPrint() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(PRICE -1);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
        assertFalse(machine.printTicket(), "Le billet est imprimé même si le prix n'est pas suffisant");
	}

	@Test
		// S4 : on imprime le ticket si le montant inséré est suffisant
	void insertEnoughMoneyPrint() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(PRICE);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertTrue(machine.printTicket(), "Le billet n'est pas imprimé alors que le montant est suffisant");
	}

	@Test
		// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void balanceUpToDateAfterPrint() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(PRICE);
		int balance = machine.getBalance();
		machine.printTicket();
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(balance - PRICE, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void balanceUpToDateONLYAfterPrint() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(PRICE);
		int total = machine.getTotal();
		machine.printTicket();
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(total + PRICE, machine.getTotal(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		// S7 : refund() rend correctement la monnaie
	void refundSendBackMoney() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(PRICE);
		int balance = machine.getBalance();
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(balance , machine.refund(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		// S8 : refund() remet la balance à zéro
	void refundSetBalanceZero() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(PRICE);
		machine.refund();
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(0 , machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		// S9 :  on ne peut pas insérerun montant négatif
	void cannontInsertNegative() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		try {
			machine.insertMoney(-PRICE);
			// Si on arrive ici, c'est qu'on n'a pas eu d'exception -> le test doit échouer
			fail("On devrait avoir une exception pour une tentative d'entrée d'argent négative");
			} catch (IllegalArgumentException e) {
			// Si on arrive ici c'est normal, le test est réussi
			}
	}

	@Test
		// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void cannotCreateNegativePriceTickets() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On essaye de créer une machine avec un ticket negatif l'argent
		try {
			machine = new TicketMachine(-PRICE);
			// Si on arrive ici, c'est qu'on n'a pas eu d'exception -> le test doit échouer
			fail("On devrait avoir une exception pour un prix de ticket négatif");
		} catch (IllegalArgumentException e) {
			// Si on arrive ici c'est normal, le test est réussi
		}
	}

}
