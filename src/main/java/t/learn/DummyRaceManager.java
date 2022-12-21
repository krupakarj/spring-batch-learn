package t.learn;

import java.util.List;

public class DummyRaceManager implements RaceManager {

    public Animal getWinner(List<Animal> animals) {
        return animals==null || animals.size()==0 ? null: animals.get(0);
    }
}