public interface Composant {
    void addFils(Composant c);

    void removeFils(Composant c);


    Noeud getParent();

    int getEntier();


}
