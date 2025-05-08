package PlayersEnemies;

import java.util.ArrayList;
import java.util.List;

public class Party {
    private List<Recruit> recruits;
    private static final int MAX_PARTY_SIZE = 3;

    public Party() {
        recruits = new ArrayList<>();
    }

    public boolean addRecruit(Recruit recruit) {
        if (recruits.size() < MAX_PARTY_SIZE) {
            recruits.add(recruit);
            recruit.setInParty(true);
            return true;
        }
        return false; // Отряд полон
    }

    public void removeRecruit(Recruit recruit) {
        recruits.remove(recruit);
        recruit.setInParty(false);
    }

    public List<Recruit> getRecruits() {
        return recruits;
    }

    public void printParty() {
        if (recruits.isEmpty()) {
            System.out.println("Ваш отряд пуст.");
        } else {
            System.out.println("Ваш отряд:");
            for (Recruit recruit : recruits) {
                System.out.println(recruit);
            }
        }
    }
}