import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

import static java.lang.System.out;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

	public static ArrayList<Participant> _allCpuParticipants;

	// Variable containing ALL alive participants, including the player
	public static ArrayList<Participant> _aliveParticipants;

	// Variable storing only the CPU participants who are alive, excluding the
	// player.
	public static ArrayList<Participant> _aliveCpuParticipants;
	public static ArrayList<Participant> _deadParticipants;
	public static ArrayList<Participant> _allParticipants;
	public static Participant _player;
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
		_aliveCpuParticipants = new ArrayList<>();
		_deadParticipants = new ArrayList<>();
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
		_allCpuParticipants = new ArrayList<Participant>(_participantCount - 1);

		for (int i = 0; i < cpuNames.size(); i++) {
			Participant cpuParticipant = new Participant(cpuNames.get(i));
			GiveParticipantInitialCards(cpuParticipant, _participantCount);
			_allCpuParticipants.add(cpuParticipant);
			_aliveParticipants.add(cpuParticipant);
			_aliveCpuParticipants.add(cpuParticipant);
			_allParticipants.add(cpuParticipant);
		}
		// out.println("length of cpuplayers upon init: "+ _allCpuParticipants.size());
	}

	public static ArrayList<String> generateCpuNames(int cpuCount) {
		ArrayList<String> names = new ArrayList<>();
		String[] possibleNames = { "BotMax", "Botana", "NeuroBot", "HaliBot", "GaliBot" };
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
		String name = "";
		while (true) {

			name = scanner.nextLine();
			if (name.isEmpty()) {
				out.println("Name is too short.");
				continue;
			}
			break;
		}
		return name;
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
			out.println("\n======================");

			waitForPlayerCard(currentPlayerTurn, player);
			clearUserInput();
			PrintCurrentTableCards();
			waitForBell();

			currentPlayerTurn = ProgressTurnIndex(currentPlayerTurn);

		}
	}

	// Returns the new currentPlayerTurn value, to be used in the next iteration of
	// the game loop.
	private static int ProgressTurnIndex(int currentPlayerTurn) {
		// ::: Progresing the turn.
		boolean validTurnSet = false;
		while (!validTurnSet) {
			currentPlayerTurn += 1;
			if (currentPlayerTurn > _allCpuParticipants.size()) {
				currentPlayerTurn = 0;
			}

			boolean deadPlayerWasSet = (currentPlayerTurn == 0 && _deadParticipants.contains(_player));
			if (deadPlayerWasSet) {
				currentPlayerTurn += 1;
			}

			boolean deadCpuWasSet = (currentPlayerTurn != 0
					&& _deadParticipants.contains(_allCpuParticipants.get(currentPlayerTurn - 1)));
			if (!deadCpuWasSet) {
				validTurnSet = true;
			} else {
				out.println("A dead cpu was set, trying again. index was" + currentPlayerTurn);
			}
		}

		return currentPlayerTurn;
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

		// ::: Removing participants from _aliveParticipants
		for (Participant p : _aliveParticipants) {
			if (!p.HasACard()) {
				eliminated.add(p);
			}
		}
		_aliveParticipants.removeAll(eliminated);
		_aliveCpuParticipants.removeAll(eliminated);
		_deadParticipants.addAll(eliminated);

		// // ::: Removing the newly deceased CPUs from the _cpuPlayers list.
		// _cpuPlayers.removeIf(cpu -> !cpu.HasACard());

		for (Participant p : eliminated) {
			System.out.println(p.name + " has been eliminated.");
		}
	}

	private static void PlayEndScreen(Participant winner) {
		System.out.println("\n===============================");
		System.out.println("         GAME OVER!");
		System.out.println("===============================");
		System.out.println("Winner: " + winner.name);

		PrintStatistics();

		System.out.println("Would you like to play again? (y/n)");
		String answer = _inputScanner.nextLine().trim().toLowerCase();
		if (answer.equals("y")) {
			ResetGame();
			InitializeGame();
			PlayGame(_player);
		} else {
			System.out.println("Goodbye!");
			System.exit(0);
		}
	}

	private static void PrintStatistics() {
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
		for (int i = 0; i < _allCpuParticipants.size(); i++) {
			HaliCard cCard = null;
			if (!_allCpuParticipants.get(i).TableCards.isEmpty()) {
				cCard = _allCpuParticipants.get(i).TableCards.peek();
			}
			if (cCard != null) {
				out.println(_allCpuParticipants.get(i).name + ": " + cCard.fruitType + " " + cCard.count);
			}
		}

		out.println("----------------------------");
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

		userSmackedBell = false;
		bellAlreadyHandled = false;
		clearUserInput();
		boolean fiveFruitsArePresent = AreFiveFruitsPresent(_aliveParticipants);
		Thread userBellSmackThread = ProcessUserBellSmacking(fiveFruitsArePresent);
		Thread cpuBellSmackThread = ProcessCPUBellSmacking(fiveFruitsArePresent);

		// ::: After 3 seconds, join the bell smacking threads.
		SleepHack(3000);
		try {
			userBellSmackThread.join();
			cpuBellSmackThread.join();
		} catch (Exception e) {
			out.println("Failed to join thread: " + e.getMessage());
		}
	}

	private static void HandleWrongBellSmack(Participant victim) {
		out.println(victim.name + " miscounted!");
		for (Participant nonVictim : _aliveParticipants) {
			if (nonVictim != victim) {
				HaliCard takenCard = victim.RemoveCard_FromHandTop();
				if (takenCard == null) {
					break;
				}
				nonVictim.AddCard_ToBottomOfHands(takenCard);
				out.println(victim.name + " gave a card to " + nonVictim.name);
			}
		}
		out.println(victim.name + " now has " + victim.getHandCardSize() + " cards.");
		KickOutDeadParticipants();
	}


	public static boolean AreFiveFruitsPresent(ArrayList<Participant> aliveParticipants) {

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

		for (Participant aliveParticipant : aliveParticipants) {
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
				_inputScanner.nextLine();
			}
		} catch (Exception e) {
			out.println("Checking if there was userinput threw exception: " + e.getMessage());
		}
	}

	public static Thread ProcessUserBellSmacking(boolean fiveFruitsArePresent) {
		Thread userThread = new Thread(() -> {
			long startTime = System.currentTimeMillis();
			System.out.println("!!!COUNT 5 FRUITS? SMACK THE BELL!!! (press Enter)");
			while (System.currentTimeMillis() - startTime < 3000 && !bellAlreadyHandled) {
				if (_deadParticipants.contains(_player)) {
					break;
				}
				try {
					if (System.in.available() > 0) {
						// synchronized (_inputScanner) {
						_inputScanner.nextLine();
						synchronized (bellLock) {
							if (!bellAlreadyHandled) {
								userSmackedBell = true;
								bellAlreadyHandled = true;
								if (fiveFruitsArePresent) {
									HandleCorrectBellSmack(_player);
								} else {
									HandleWrongBellSmack(_player);
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

		return userThread;
	}

	public static Thread ProcessCPUBellSmacking(boolean fiveFruitsArePresent) {
		Thread cpuThread = new Thread(() -> {
			SleepHack(new Random().nextInt(1000, 3000));

			// 50 % chance it will smack
			if (new Random().nextBoolean()) {
				synchronized (bellLock) {
					if (!bellAlreadyHandled) {
						Participant cpuWhoSmacked = _aliveCpuParticipants
								.get(new Random().nextInt(_aliveCpuParticipants.size()));
						System.out.println(cpuWhoSmacked.name + " smacked the bell!");
						assert (!_deadParticipants.contains(cpuWhoSmacked));
						bellAlreadyHandled = true;
						if (fiveFruitsArePresent) {
							HandleCorrectBellSmack(cpuWhoSmacked);
						} else {
							HandleWrongBellSmack(cpuWhoSmacked);
						}
					}
				}
			}
			// else {
			//
			// System.out.println("CPU messed up hope you didnt.");
			// }
		});
		cpuThread.start();
		return cpuThread;
	}

	public static int HandleCorrectBellSmack(Participant winner) {
		System.out.println(winner.name + " smacked the bell successfully!");
		winner.correctBellCount++;
		GrabAllTableCards(winner);
		out.println(winner.name + " now has " + winner.getHandCardSize() + " cards.");
		return winner.correctBellCount;
	}

	private static void ResetGame() {
		_allCpuParticipants.clear();
		_aliveParticipants.clear();
		_deadParticipants.clear();

		// _deck = new HaliDeck();
		// _player = new Participant();
		// _aliveParticipants.add(_player);
		//
		// GiveParticipantInitialCards(_player, _participantCount);
		//
		// for (int i = 1; i < _participantCount; i++) {
		// Participant cpu = new Participant();
		// GiveParticipantInitialCards(cpu, _participantCount);
		// _cpuPlayers.add(cpu);
		// _aliveParticipants.add(cpu);
		// }
		//
		System.out.println("\nGame has been reset!\n");
	}

}
