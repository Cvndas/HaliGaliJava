import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import static java.lang.System.out;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

	private static ArrayList<Participant> _cpuPlayers;
	private static ArrayList<Participant> _aliveParticipants;
	private static ArrayList<Participant> _allParticipants;
	private static Participant _player;
	private static HaliDeck _deck;
	private static Scanner _inputScanner;
	private static int _participantCount;
	public static final int CARDS_IN_DECK = 56;
	private static final Object bellLock = new Object();
	private static volatile boolean userSmackedBell = false;
	private static volatile boolean bellAlreadyHandled = false;

	public static void main(String[] args) {
		_inputScanner = new Scanner(System.in);
		InitializeGame();
		PlayGame(_player);
	}

	private static void InitializeGame() {
		_deck = new HaliDeck();
		_aliveParticipants = new ArrayList<>();
		_allParticipants = new ArrayList<>();

		System.out.println("Choose a player count. Options: 2, 3, 4 ");

		boolean playerCountIsValid = false;
		_participantCount = -1;

		while (!playerCountIsValid) {
			_participantCount = _inputScanner.nextInt();
			if (_inputScanner.hasNextLine()) {
				_inputScanner.nextLine();
			}
			playerCountIsValid = (_participantCount == 2) ||
					(_participantCount == 3) ||
					(_participantCount == 4);
			if (!playerCountIsValid) {
				out.println("Invalid player count. Options: 2, 3, 4");
			}

		}
		out.println("Starting a " + _participantCount + " player game!");
		//
		String playerName = providePlayerName(_inputScanner);
		_player = new Participant(playerName);
		_aliveParticipants.add(_player);
		_allParticipants.add(_player);
		GiveParticipantInitialCards(_player, _participantCount);

		ArrayList<String> cpuNames = generateCpuNames(_participantCount - 1);
		_cpuPlayers = new ArrayList<Participant>(_participantCount - 1);

		for (int i = 0; i < cpuNames.size(); i++) {
			Participant cpuParticipant = new Participant(cpuNames.get(i));
			GiveParticipantInitialCards(cpuParticipant, _participantCount);
			_cpuPlayers.add(cpuParticipant);
			_aliveParticipants.add(cpuParticipant);
			_allParticipants.add(cpuParticipant);
		}
	}

	public static ArrayList<String> generateCpuNames(int cpuCount) {
		ArrayList<String> names = new ArrayList<>();
		String[] possibleNames = {"BotMax", "Botana", "NeuroBot", "HaliBot", "GaliBot"};
		Random rand = new Random();

		for (int i = 0; i < cpuCount; i++) {
			String name = possibleNames[rand.nextInt(possibleNames.length)];
			while (names.contains(name)) {
				name = possibleNames[rand.nextInt(possibleNames.length)];
			}
			names.add(name);
		}
		return names;
	}

	public static String providePlayerName(Scanner scanner) {
		System.out.println("Enter your name:");
		return scanner.nextLine();
	}

	private static void GiveParticipantInitialCards(Participant participant, int participantCount) {
		int cardsToGiveToParticipant = CARDS_IN_DECK / participantCount;
		assert (cardsToGiveToParticipant * participantCount <= CARDS_IN_DECK);
		for (int i = 0; i < cardsToGiveToParticipant; i++) {
			HaliCard cardFromDeck = _deck.TakeCardFromDeck();

			// In initial state, there must be enough cards for everyone.
			assert (cardFromDeck != null);
			participant.AddCard_ToBottomOfHands(cardFromDeck);
		}
	}

	private static void PlayGame(Participant player) {
		boolean gameIsDone = false;

		// ::: Player is always 0
		// ::: Whoever starts first is random
		int currentPlayerTurn = new Random().nextInt(_participantCount);

		// TODO: Refresh the terminal, then display UI elements on the top of the screen
		// that show the controls, every
		// round.

		while (!gameIsDone) {

			if (_aliveParticipants.size() == 1) {
				gameIsDone = true;
				Participant winner = _aliveParticipants.get(0);
				// System.out.println(winner.name + "Wins!");
				PlayEndScreen(winner);
			}

			// ::: The idea
			/*
			 * Each round consists of two parts:
			 * 1. Waiting for the current player to place a card
			 * 
			 * 2. If there is a combination for 5 fruits of the same type, wait until
			 * someone presses spacebar.
			 * If there is no combination for 5 fruits of the same type, wait for 2 seconds,
			 * then go to the next round.
			 */

			waitForPlayerCard(currentPlayerTurn, player);
			clearUserInput();
			PrintCurrentTableCards();
			waitForBell();

			// TODO: Kicking out dead participants should only happen upon a bell smack.
			// This is to ensure that dead players don't have any cards laying around on the
			// table.
			// Draw a diagram to figure out how this should interact with currentPlayerTurn
			// As it stands, this is wrong and bugged.

			currentPlayerTurn += 1;
			if (currentPlayerTurn >= _aliveParticipants.size()) {
				currentPlayerTurn = 0;
			}
		}
	}

	// To be called when a player smacks the bell and there are precisely 5 fruits
	// of the same type on the table.
	private static void GrabAllTableCards(Participant winner) {
		for (Participant p : _aliveParticipants) {
			while (!p.TableCards.isEmpty()) {
				HaliCard card = p.TableCards.pop();
				winner.AddCard_ToBottomOfHands(card);
			}
		}

		System.out.println(winner.name + " grabbed all the table cards!");

		KickOutDeadParticipants();
	}

	// Players can only die when someone has
	private static void KickOutDeadParticipants() {
		ArrayList<Participant> eliminated = new ArrayList<>();

		for (Participant p : _aliveParticipants) {
			if (!p.HasACard()) {
				eliminated.add(p);
			}
		}

		_aliveParticipants.removeAll(eliminated);
		_cpuPlayers.removeIf(cpu -> !cpu.HasACard());

		for (Participant p : eliminated) {
			System.out.println(p.name + " has been eliminated.");
		}
	}

	private static void PlayEndScreen(Participant winner) {
		System.out.println("\n===============================");
		System.out.println("         GAME OVER!");
		System.out.println("===============================");
		System.out.println("Winner: " + winner.name);

		GetStatistics();

		System.out.println("Would you like to play again? (y/n)");
		String answer = _inputScanner.nextLine().trim().toLowerCase();
		if (answer.equals("y")) {
			InitializeGame();
			PlayGame(_player);
		} else {
			System.out.println("Goodbye!");
			System.exit(0);
		}
	}
	
	private static void GetStatistics() {
			System.out.println("\n--- Game Statistics ---");

			for (Participant p : _allParticipants) {
				System.out.println("Player: " + p.name);
				System.out.println("  Correct Bell Presses: " + p.correctBellCount);
				System.out.println("  Max Inventory Size: " + p.maxInventorySize);
				System.out.println();
			}
		}
	
	private static void waitForPlayerCard(int currentPlayerTurn, Participant player) {
		Participant participant = _aliveParticipants.get(currentPlayerTurn);

		if (participant.getHandCardSize() == 0) {
			System.out.println(participant.name + " has no cards and skips this turn.");
			return;
		}

		// Oyuncu kendi sırasıysa
		if (participant == player) {
			System.out.print("Your Turn! (Press enter to place a card.)");
			SleepHack(1000);
			_inputScanner.nextLine();

			participant.PutCardOnTable();
			HaliCard card = participant.TableCards.peek();
			System.out.println(participant.name + " put " + card.fruitType + " " + card.count + " on the table.");
		} 
		// CPU sırasıysa
		else {
			System.out.println(participant.name + "'s turn!");
			SleepHack(1000);
			participant.PutCardOnTable();
			HaliCard card = participant.TableCards.peek();
			out.println(participant.name + " put " + card.fruitType + " " + card.count + " on the table.");
		}
	}



	private static void PrintCurrentTableCards() {
		out.println("\n---The Cards on the Table---");

		// ::: Reading the card data from the player and CPUs.
		HaliCard pCard = null;
		if (!_player.TableCards.isEmpty()) {
			pCard = _player.TableCards.peek();
		}

		if (pCard != null) {
			out.println(_player.name + ": " + pCard.fruitType + " " + pCard.count);
		}

		// for (int i = 0; i < _participantCount - 1; i++) {
		for (int i = 0; i < _cpuPlayers.size(); i++) {
			HaliCard cCard = null;
			if (!_cpuPlayers.get(i).TableCards.isEmpty()) {
				cCard = _cpuPlayers.get(i).TableCards.peek();
			}
			if (cCard != null) {
				out.println(_cpuPlayers.get(i).name + ": " + cCard.fruitType + " " + cCard.count);
			}
		}

		out.println("---\n");
	}

	private static void SleepHack(int msSleepTime) {
		try {
			Thread.sleep(msSleepTime);

		} catch (Exception e) {
			out.println("This code has no business being triggered. The game is broken");
		}
	}

	/// Seeing who will smack the bell first within a 3 second window.
	private static void waitForBell() {
		boolean exactlyFiveOfFruitArePresent = AreFiveFruitsPresent();

		// TODO: Multithreading, or some other solution, to solve the problem of
		// the player needing to be able to press Enter to smack the bell,
		// while at any time during the 3 second window, the CPUs can do it too.

		if (AreFiveFruitsPresent()) {
			// ::: Bell is valid. Waiting until one of the participants smacks the bell.
			if (AreFiveFruitsPresent()) {
				userSmackedBell = false;
				bellAlreadyHandled = false;
				ProcessUserBellSmacking();
				ProcessCPUBellSmacking();
				SleepHack(3000);
			}
		}
		else
		{
			// ::: Give user some time to smack invalidly
			long startTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - startTime < 1000) {
				try {
					if (System.in.available() > 0) {
						synchronized (_inputScanner) {
							_inputScanner.nextLine();
							System.out.println("You smacked the bell incorrectly!");
							HandleWrongBellSmack(_player);
							return;
						}
					}
				} catch (IOException e) {
					System.out.println("Input check failed.");
				}
				SleepHack(50);
			}
			// ::: Bell is invalid. Computing a chance that a CPU smacks the bell on
			// accident anyway.
			boolean cpuSmacksBell = new Random().nextInt(0, 10) == 0; // 10% chance that a cpu smacks bell.
			if (cpuSmacksBell) {
				// ::: Retroactively deciding which CPU smacked the bell :)
				if (_cpuPlayers.size() > 0) {
					int bellSmackerIndex = new Random().nextInt(_cpuPlayers.size());
					Participant cpuWhoMessedUp = _cpuPlayers.get(bellSmackerIndex);
					out.println(cpuWhoMessedUp.name + " messed up!");
					HandleWrongBellSmack(cpuWhoMessedUp);
				}
			}
		}

		// TODO: Remove this while implementing the function properly.
		SleepHack(3000);
	}

	private static void HandleWrongBellSmack(Participant victim) {
		for (Participant nonVictim : _aliveParticipants) {
			if (nonVictim != victim) {
				HaliCard takenCard = victim.RemoveCard_FromHandTop();
				if (takenCard == null) {
					break;
				}
				nonVictim.AddCard_ToBottomOfHands(takenCard);
				out.println("A card was given to another player.");
			}
		}
		KickOutDeadParticipants();
	}

	public static boolean AreFiveFruitsPresent() {
		// ::: Idea: Go over all alive players, and read their fruits. Check for matches
		// of 5.
		// Since there is such a limited amount of fruits, I hardcode it here. A more
		// dynamic approach with
		// a hashtable would be suited if there was a significantly greater variety of
		// card types.
		int bananaCount = 0;
		int strawberryCount = 0;
		int plumeCount = 0;
		int limeCount = 0;

		for (Participant aliveParticipant : _aliveParticipants) {
			HaliCard tableCard = null;
			if (!aliveParticipant.TableCards.isEmpty()) {
				tableCard = aliveParticipant.TableCards.peek();
			}
			if (tableCard == null) {
				continue;
			}

			switch (tableCard.fruitType) {
				case Banana:
					bananaCount += tableCard.count;
					break;
				case Lime:
					limeCount += tableCard.count;
					break;
				case Strawberry:
					strawberryCount += tableCard.count;
					break;
				case Plum:
					plumeCount += tableCard.count;
					break;
			}
		}
		return (bananaCount == 5 ||
				strawberryCount == 5 ||
				plumeCount == 5 ||
				limeCount == 5);
	}

	private static void clearUserInput() {
		try {
			while (System.in.available() > 0) {
				out.println("Before eating next line");
				_inputScanner.nextLine();
				out.println("After eating next line");
			}
		} catch (Exception e) {
			out.println("Checking if there was userinput threw exception: " + e.getMessage());
		}
	}

	private static void ProcessUserBellSmacking() {
		Thread userThread = new Thread(() -> {
			long startTime = System.currentTimeMillis();
			System.out.println("Smack the bell! (press Enter)");
			while (System.currentTimeMillis() - startTime < 3000 && !bellAlreadyHandled) {
				try {
					if (System.in.available() > 0) {
						synchronized (_inputScanner) {
							_inputScanner.nextLine();
							synchronized (bellLock) {
								if (!bellAlreadyHandled) {
									userSmackedBell = true;
									bellAlreadyHandled = true;
									HandleCorrectBellSmack(_player);
								}
							}
							break;
						}
					}
				} catch (IOException e) {
					System.out.println("Error checking input availability");
				}
			}
		});
		userThread.start();
	}

	private static void ProcessCPUBellSmacking() {
		Thread cpuThread = new Thread(() -> {
			try {
				Thread.sleep(new Random().nextInt(3000));
			} catch (InterruptedException e) {
				return;
			}
			// 50 % chance it will smack
			if (new Random().nextBoolean()) {
				synchronized (bellLock) {
					if (!bellAlreadyHandled) {
						Participant cpuWhoSmacked = _cpuPlayers.get(new Random().nextInt(_cpuPlayers.size()));
						System.out.println("CPU smacked the bell!");
						bellAlreadyHandled = true;
						HandleCorrectBellSmack(cpuWhoSmacked);
					}
				}
			} else {
				System.out.println("CPU messed up hope you didnt.");
			}
		});
		cpuThread.start();
	}

	private static void HandleCorrectBellSmack(Participant winner) {
		System.out.println(winner.name + " smacked the bell correctly!");
		winner.correctBellCount++;
		GrabAllTableCards(winner);
	}
}
