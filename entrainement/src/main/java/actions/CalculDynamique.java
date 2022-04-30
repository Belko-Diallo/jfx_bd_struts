package actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.inject.Inject;
import modele.CalculatriceDynamiqueDuFutur;

public class CalculDynamique extends ActionSupport {
    private double operande1;
    private double operande2;
    private String operation;
    private CalculatriceDynamiqueDuFutur calculatrice;

    @Inject("calculatrice")
    public void setCalculatrice(CalculatriceDynamiqueDuFutur calculatrice) {
        this.calculatrice = calculatrice;
    }
}
