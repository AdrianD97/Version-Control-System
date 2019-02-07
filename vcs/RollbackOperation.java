package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class RollbackOperation extends VcsOperation {
    public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * executa operatia: vcs rollback.
     * goleste staging-ul branch-ului curent al vcs-ului.
     * seteaza sistemul de fisiere al staging-ului,
     * cu o copie a sistemului de fisere al branch-ului curent.
     * @param vcs sistemul de versionare
     * @return rezultatul comenzii(daca s-a executat cu succes comanda sau nu).
     */
    @Override
    public int execute(Vcs vcs) {
        if (operationArgs.size() != 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        vcs.getCurrentBranch().getStaging().free();
        vcs.getCurrentBranch().getStaging().setFileSystemSnapshot(
                vcs.getCurrentBranch().getFileSystemSnapshot().cloneFileSystem());
        return ErrorCodeManager.OK;
    }
}
