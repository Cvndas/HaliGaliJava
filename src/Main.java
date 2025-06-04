import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static ArrayList<Participant> _cpuPlayers;
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
        Participant player = new Participant();
        GiveParticipantInitialCards(player, playerCount);

        _cpuPlayers = new ArrayList<Participant>(playerCount - 1);


        for (int i = 1; i < playerCount; i++) {
            Participant cpuParticipant = new Participant();
            GiveParticipantInitialCards(cpuParticipant, playerCount);
            _cpuPlayers.add(cpuParticipant);
        }

    }





    private static void GiveParticipantInitialCards(Participant participant, int participantCount) {
        int cardsToGiveToParticipant = CARDS_IN_DECK / participantCount;
        assert (cardsToGiveToParticipant * participantCount <= CARDS_IN_DECK);
        for (int i = 0; i <cardsToGiveToParticipant; i++) {
            HaliCard cardFromDeck = _deck.TakeCardFromDeck();

            // In initial state, there must be enough cards for everyone.
            assert (cardFromDeck != null);
            participant.AddCardToBottom(cardFromDeck);
        }

    }


}
