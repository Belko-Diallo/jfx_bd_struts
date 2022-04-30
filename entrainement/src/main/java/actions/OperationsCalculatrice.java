package actions;

import com.opensymphony.xwork2.ActionSupport;
import modele.CalculatriceDynamiqueDuFutur;
import modele.CalculatriceDynamiqueDuFuturImpl;

import java.util.Collection;

public class OperationsCalculatrice extends ActionSupport {
    private CalculatriceDynamiqueDuFutur calculatriceDynamiqueDuFutur = new CalculatriceDynamiqueDuFuturImpl();
    /*private Collection<String> lesOperation;*/

    public Collection<String> getLesOperations() {
        return this.calculatriceDynamiqueDuFutur.getOperations();
    }
    /*
    public String execute()throws Exception{
        this.lesOperation = this.calculatriceDynamiqueDuFutur.getOperations();
        return SUCCESS;
    }*/
}
