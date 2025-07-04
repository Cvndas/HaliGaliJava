
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayDeque;

public class HaliDeck {
    public HaliDeck() {
        _cards = new ArrayList<>(56);

        InitDeck();
//        System.out.println("Initialized the deck.");
    }





    private ArrayList<HaliCard> _cards;


    public HaliCard TakeCardFromDeck()
    {
        if (!_cards.isEmpty()) {
            return _cards.remove(0);
        }

        return null;
    }



    private void InitDeck() {
        // ::: The Card Distribution, according to my manual count

        //     Banana 1: 5
        //     Banana 2: 3
        //     Banana 3: 3
        //     Banana 4: 2
        //     Banana 5: 1

        //       Lime 1: 5
        //       Lime 2: 2
        //       Lime 3: 2
        //       Lime 4: 2
        //       Lime 5: 1

        //       Plum 1: 5
        //       Plum 2: 4
        //       Plum 3: 4
        //       Plum 4: 2
        //       Plum 5: 1

        // Strawberry 1: 5
        // Strawberry 2: 3
        // Strawberry 3: 3
        // Strawberry 4: 2
        // Strawberry 5: 1

        for (int i = 0; i < 5; i++) {
            _cards.add(new HaliCard(FruitType.Banana, 1));
            _cards.add(new HaliCard(FruitType.Plum, 1));
            _cards.add(new HaliCard(FruitType.Lime, 1));
            _cards.add(new HaliCard(FruitType.Strawberry, 1));
        }

        for (int i = 0; i < 4; i++) {
            _cards.add(new HaliCard(FruitType.Plum, 2));
            _cards.add(new HaliCard(FruitType.Plum, 3));
        }

        for (int i = 0; i < 3; i++) {
            _cards.add(new HaliCard(FruitType.Banana, 2));
            _cards.add(new HaliCard(FruitType.Banana, 3));
            _cards.add(new HaliCard(FruitType.Strawberry, 2));
            _cards.add(new HaliCard(FruitType.Strawberry, 3));
        }

        for (int i = 0; i < 2; i++) {
            _cards.add(new HaliCard(FruitType.Banana, 4));
            _cards.add(new HaliCard(FruitType.Lime, 2));
            _cards.add(new HaliCard(FruitType.Lime, 3));
            _cards.add(new HaliCard(FruitType.Lime, 4));

            _cards.add(new HaliCard(FruitType.Plum, 4));
            _cards.add(new HaliCard(FruitType.Strawberry, 4));
        }

        _cards.add(new HaliCard(FruitType.Banana, 5));
        _cards.add(new HaliCard(FruitType.Lime, 5));
        _cards.add(new HaliCard(FruitType.Plum, 5));
        _cards.add(new HaliCard(FruitType.Strawberry, 5));


        Collections.shuffle(_cards, new Random(System.currentTimeMillis()));

//        for (HaliCard card : _cards) {
//            System.out.println(card.fruitType + ", " + card.countType);
//        }
        assert(_cards.size() == Main.CARDS_IN_DECK);
    }
}
