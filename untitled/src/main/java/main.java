import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        ArrayList<Integer> parcours = new ArrayList<>();
        Noeud noeud = new Noeud(0, null);
        parcours.add(parcoursProfondeur(noeud));
        ArrayList<Integer> parcours2 = new ArrayList<>();
        parcours2.addAll(parcoursLargeur(noeud));
    }

    public static int parcoursProfondeur(Noeud noeud) {
        if (noeud.getLesFils().isEmpty()) {
            return noeud.getEntier();
        }
        for (Noeud fils : noeud.getLesFils()) {
            return parcoursProfondeur(fils);
        }
        return noeud.getEntier();

    }

    public static ArrayList parcoursLargeur(Noeud noeud) {
        ArrayList<Integer> list = new ArrayList();
        list.add(noeud.getEntier());
        for (Noeud n : noeud.getLesFils()) {
            list.add(noeud.getEntier());
        }
        return list;
    }
}
