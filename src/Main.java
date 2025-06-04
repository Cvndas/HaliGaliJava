import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import static java.lang.System.out;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static ArrayList<Participant> _cpuPlayers;
    private static Participant _player;
    private static HaliDeck _deck;


    public static final int CARDS_IN_DECK = 56;





    public static void main(String[] args) {
        _deck = new HaliDeck();

        System.out.println("Choose a player count. Options: 2, 3, 4 ");
        Scanner scanner = new Scanner(System.in);

        boolean validPlayerCount = false;
        int playerCount = -1;

        while (!validPlayerCount) {
            playerCount = scanner.nextInt();
            validPlayerCount =
                    (playerCount == 2) ||
                            (playerCount == 3) ||
                            (playerCount == 4);
            if (!validPlayerCount) {
                out.println("Invalid player count. Options: 2, 3, 4");
            }

        }

        out.println("Starting a " + playerCount + " player game!");

        // ::: Initializing the participants. The player is participant 0.
        _player = new Participant();
        GiveParticipantInitialCards(_player, playerCount);

        _cpuPlayers = new ArrayList<Participant>(playerCount - 1);


        for (int i = 1; i < playerCount; i++) {
            Participant cpuParticipant = new Participant();
            GiveParticipantInitialCards(cpuParticipant, playerCount);
            _cpuPlayers.add(cpuParticipant);
        }


        // ::: Initialization is done.
        PlayGame(playerCount);
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





    private static void PlayGame(int participantCount) {
        boolean gameIsDone = false;

        // ::: Player is always 0
        // ::: Whoever starts first is random
        int currentPlayerTurn = new Random().nextInt(participantCount);

        while (!gameIsDone) {

            // ::: The idea
            /*
            Each round consists of two parts:
                1. Waiting for the current player to place a card

                2. If there is a combination for 5 fruits of the same type, wait until someone presses spacebar.
                   If there is no combination for 5 fruits of the same type, wait for 2 seconds, then go to the next round.
             */

            RoundPartOne(currentPlayerTurn);
            RoundPartTwo();
        }
    }

    /// Placing a card
    private static void RoundPartOne(int currentPlayerTurn)
    {
        // ::: Player Turn
        if (currentPlayerTurn == 0) {

        }

        // ::: CPU Turn
        else {

        }
    }


    /// Seeing who will smack the bell first
    private static void RoundPartTwo()
    {

    }


}
