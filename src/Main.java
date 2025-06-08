import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import static java.lang.System.out;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

	private static ArrayList<Participant> _cpuPlayers;
	private static ArrayList<Participant> _aliveParticipants;
	private static Participant _player;
	private static HaliDeck _deck;
	private static Scanner _inputScanner;
	private static int _participantCount;
	public static final int CARDS_IN_DECK = 56;





	public static void main(String[] args) {
		_inputScanner = new Scanner(System.in);

		InitializeGame();


		// ::: Initialization is done.
		PlayGame();
	}





	private static void InitializeGame() {
		_deck = new HaliDeck();
		_aliveParticipants = new ArrayList<>();

		System.out.println("Choose a player count. Options: 2, 3, 4 ");

		boolean playerCountIsValid = false;
		_participantCount = -1;

		while (!playerCountIsValid) {
			_participantCount = _inputScanner.nextInt();
			if (_inputScanner.hasNextLine()) {
				_inputScanner.nextLine();
			}
			playerCountIsValid =
					  (_participantCount == 2) ||
								 (_participantCount == 3) ||
								 (_participantCount == 4);
			if (!playerCountIsValid) {
				out.println("Invalid player count. Options: 2, 3, 4");
			}

		}

		out.println("Starting a " + _participantCount + " player game!");

		// ::: Initializing the participants. The player is participant 0.
		_player = new Participant();
		_aliveParticipants.add(_player);
		GiveParticipantInitialCards(_player, _participantCount);

		_cpuPlayers = new ArrayList<Participant>(_participantCount - 1);


		for (int i = 1; i < _participantCount; i++) {
			Participant cpuParticipant = new Participant();
			GiveParticipantInitialCards(cpuParticipant, _participantCount);
			_cpuPlayers.add(cpuParticipant);
			_aliveParticipants.add(cpuParticipant);
		}

	}





	private static void GiveParticipantInitialCards(Participant participant, int participantCount) {
		int cardsToGiveToParticipant = CARDS_IN_DECK / participantCount;
		assert (cardsToGiveToParticipant * participantCount <= CARDS_IN_DECK);
		for (int i = 0; i < cardsToGiveToParticipant; i++) {
			HaliCard cardFromDeck = _deck.TakeCardFromDeck();

			// In initial state, there must be enough cards for everyone.
			assert (cardFromDeck != null);
			participant.AddCardToBottom(cardFromDeck);
		}
	}





	private static void PlayGame() {
		boolean gameIsDone = false;

		// ::: Player is always 0
		// ::: Whoever starts first is random
		int currentPlayerTurn = new Random().nextInt(_participantCount);

		// TODO: Refresh the terminal, then display UI elements on the top of the screen that show the controls, every
		// round.

		while (!gameIsDone) {

			// ::: The idea
            /*
            Each round consists of two parts:
                1. Waiting for the current player to place a card

                2. If there is a combination for 5 fruits of the same type, wait until someone presses spacebar.
                   If there is no combination for 5 fruits of the same type, wait for 2 seconds, then go to the next round.
             */

			RoundPartOne(currentPlayerTurn);
			PrintCurrentTableCards();
			RoundPartTwo();

			// TODO: Kicking out dead participants should only happen upon a bell smack.
			// This is to ensure that dead players don't have any cards laying around on the table.
			// Draw a diagram to figure out how this should interact with currentPlayerTurn
			// As it stands, this is wrong and bugged.
			KickOutDeadParticipants();

			currentPlayerTurn += 1;
			if (currentPlayerTurn > (_participantCount - 1)) {
				currentPlayerTurn = 0;
			}
		}
	}





	private static void KickOutDeadParticipants() {
		_aliveParticipants.removeIf(p -> !p.HasACard());
	}





	/// Placing a card
	private static void RoundPartOne(int currentPlayerTurn) {
		// ::: Player Turn
		if (currentPlayerTurn == 0) {
			// ::: Wait for player to press enter, then place card.
			System.out.println("Player Turn! (Press enter to place a card.");
			_inputScanner.nextLine();
			_player.PutCardOnTable();
			// TODO: Skip turn if you have no cards.

			System.out.println("Player put " + _player.CardsInFrontOfParticipant.peek().fruitType +
					  " " + _player.CardsInFrontOfParticipant.peek().countType +
					  " on the table.");

		}

		// ::: CPU Turn
		else {
			System.out.println("CPU " + (currentPlayerTurn + 1) + "'s turn!");
			SleepHack(500);

			Participant activeCpu = _cpuPlayers.get(currentPlayerTurn - 1);
			activeCpu.PutCardOnTable();
			HaliCard cpuNewCard = activeCpu.CardsInFrontOfParticipant.peek();
			out.println("Player put " + cpuNewCard.fruitType + " " + cpuNewCard.countType + " on the table.");


		}
	}





	private static void PrintCurrentTableCards() {
		out.println("\n---\nThe Cards on the Table: ");


		// ::: Reading the card data from the player and CPUs.
		HaliCard pCard = null;
		if (!_player.CardsInFrontOfParticipant.isEmpty()) {
			pCard = _player.CardsInFrontOfParticipant.peek();
		}

		if (pCard != null) {
			out.println("Player: " + pCard.fruitType + " " + pCard.countType);
		}

		for (int i = 0; i < _participantCount - 1; i++) {
			HaliCard cCard = null;
			if (!_cpuPlayers.get(i).CardsInFrontOfParticipant.isEmpty()) {
				cCard = _cpuPlayers.get(i).CardsInFrontOfParticipant.peek();
			}
			if (cCard != null) {
				out.println("CPU " + (i + 1) + ": " + cCard.fruitType + " " + cCard.countType);
			}
		}

		out.println("");
	}





	private static void SleepHack(int msSleepTime) {
		try { // Arbitrary delay before CPU places his card
			Thread.sleep(msSleepTime);

		} catch (Exception e) {
			out.println("This code has no business being triggered. The game is broken");
		}
	}





	/// Seeing who will smack the bell first
	private static void RoundPartTwo() {
		boolean exactlyFiveOfFruitArePresent = AreFiveFruitsPresent();


		if (AreFiveFruitsPresent()) {
			// ::: Bell is valid. Waiting until one of the participants smacks the bell.
			out.println("5 fruits were present, but handling that is currently unimplemented");

		} else {
			// ::: Bell is invalid. Waiting for 2 seconds to see if anyone smacks the bell regardless.
			boolean cpuSmacksBell = new Random().nextInt(0, 10) == 0; // 10% chance that a cpu smacks bell.
			if (cpuSmacksBell) {
				// ::: Retroactively deciding which CPU smacked the bell :)
				int bellSmackerIndex = new Random().nextInt(0, _participantCount - 1);
				out.println("Cpu " + (bellSmackerIndex + 1) + " messed up!");
				HandleWrongBellSmack(_cpuPlayers.get(bellSmackerIndex));
			}
		}
	}





	private static void HandleWrongBellSmack(Participant victim) {
		for (Participant nonVictim : _aliveParticipants) {
			if (nonVictim != victim) {
				HaliCard takenCard = victim.RemoveCardFromTop();
				if (takenCard == null) {
					break;
				}
				nonVictim.AddCardToBottom(takenCard);
				out.println("A card was given to another player.");
			}
		}
	}





	public static boolean AreFiveFruitsPresent() {
		// ::: Idea: Go over all alive players, and read their fruits. Check for matches of 5.
		// Since there is such a limited amount of fruits, I hardcode it here. A more dynamic approach with
		// a hashtable would be suited if there was a significantly greater variety of card types.
		int bananaCount = 0;
		int strawberryCount = 0;
		int plumeCount = 0;
		int limeCount = 0;

		for (Participant aliveParticipant : _aliveParticipants) {
			HaliCard tableCard = null;
			if (!aliveParticipant.CardsInFrontOfParticipant.isEmpty()) {
				tableCard = aliveParticipant.CardsInFrontOfParticipant.peek();
			}
			if (tableCard == null) {
				continue;
			}

			switch (tableCard.fruitType) {
				case FruitType.Banana:
					bananaCount += tableCard.count;
					break;
				case FruitType.Lime:
					limeCount += tableCard.count;
					break;
				case FruitType.Strawberry:
					strawberryCount += tableCard.count;
					break;
				case FruitType.Plum:
					plumeCount += tableCard.count;
					break;
			}
		}
		return (
				  bananaCount == 5 ||
							 strawberryCount == 5 ||
							 plumeCount == 5 ||
							 limeCount == 5
		);
	}
}
