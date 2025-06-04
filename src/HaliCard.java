public class HaliCard {

    public HaliCard(FruitType fruitType, CountType countType) {
        this.fruitType = fruitType;
        this.countType = countType;

        this.count = countType.ordinal() + 1;
//        this.count = switch (countType) {
//            case CountType.One -> 1;
//            case CountType.Two -> 2;
//            case CountType.Three -> 3;
//            case CountType.Four -> 4;
//            case CountType.Five -> 5;
//        };
    }





    FruitType fruitType;
    CountType countType;
    int count;
}

