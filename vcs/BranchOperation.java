package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import utilsvcs.Branch;

import java.util.ArrayList;

public final class BranchOperation extends VcsOperation {
    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * executa operatia: vcs branch nume_branch.
     * creeaza un nou branch, si-l adauga sistemului de versionare.
     * @param vcs sistemul de versionare
     * @return rezultatul comenzii(daca s-a executat cu succes comanda sau nu).
     */
    @Override
    public int execute(Vcs vcs) {
        if (operationArgs.size() == 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        if (vcs.containBranch(operationArgs.get(0))) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        if (vcs.getCurrentBranch().getStaging().getSize() != 0) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }

        vcs.addBranch(new Branch(operationArgs.get(0),
                vcs.getCurrentBranch().getFileSystemSnapshot().cloneFileSystem()));

        return ErrorCodeManager.OK;
    }
}
