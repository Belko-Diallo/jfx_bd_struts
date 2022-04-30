import java.util.ArrayList;
import java.util.Collection;

public class Noeud implements Composant {
    private int entier;
    private Collection<Noeud> lesFils;
    private Noeud parent;

    public Noeud(int entier, Noeud parent) {
        this.entier = entier;
        this.parent = parent;
        this.lesFils = new ArrayList<>();
    }

    @Override
    public void addFils(Composant c) {
        if (this.lesFils.size() == 2) {
            throw new IllegalArgumentException();
        } else {
            this.lesFils.add((Noeud) c);
        }
    }

    @Override
    public void removeFils(Composant c) {
    }


    @Override
    public Noeud getParent() {
        return this.parent;
    }

    @Override
    public int getEntier() {
        return this.entier;
    }

    public Collection<Noeud> getLesFils() {
        return this.lesFils;
    }
}
