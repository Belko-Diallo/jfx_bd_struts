package actions;

import com.opensymphony.xwork2.ActionSupport;

public class Calcul extends ActionSupport {
    private double operande1;
    private double operande2;
    private String operateur;
    private Double res;

    public String execute() throws Exception {
        if (operande2 == 0 && this.operateur.equals("division")) {
            addFieldError("operande2", getText("calcul.divisionPar0"));
            return INPUT;
        }
        switch (operateur) {
            case "addition":
                res = operande1 + operande2;
                break;
            case "soustraction":
                res = operande1 - operande2;
                break;
            case "multiplication":
                res = operande1 * operande2;
                break;
            case "division":
                res = operande1 / operande2;
                break;
        }

        return SUCCESS;
    }

    public double getOperande1() {
        return operande1;
    }

    public void setOperande1(double operande1) {
        this.operande1 = operande1;
    }

    public double getOperande2() {
        return operande2;
    }

    public void setOperande2(double operande2) {
        this.operande2 = operande2;
    }

    public String getOperateur() {
        return operateur;
    }

    public void setOperateur(String operateur) {
        this.operateur = operateur;
    }

    public Double getRes() {
        return res;
    }
}
