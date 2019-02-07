package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import utilsvcs.FactoryMessageOperation;

import java.util.ArrayList;
import java.util.List;

public final class StatusOperation extends VcsOperation {
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * executa operatia: vcs status.
     * afiseaza mesajele din staging, mesaje corespunzatoare operatiilor efectuate pe
     * branch-ul curent al vcs-ului, in sistemul de output al vcs-ului.
     * @param vcs sistemul de versionare
     * @return rezultatul comenzii(daca s-a executat cu succes comanda sau nu).
     */
    @Override
    public int execute(Vcs vcs) {
        if (operationArgs.size() != 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        List<String> list = vcs.getCurrentBranch().getStaging().getContent();

        vcs.getOutputWriter().write(FactoryMessageOperation.BRANCH_MESSAGE
                + vcs.getCurrentBranch().getName() + "\n");
        vcs.getOutputWriter().write(FactoryMessageOperation.STAGED_CHANGED + "\n");
        for (String element: list) {
            vcs.getOutputWriter().write("\t" + element + "\n");
        }


        return ErrorCodeManager.OK;
    }
}
