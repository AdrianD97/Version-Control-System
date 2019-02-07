package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import utilsvcs.Commit;

import java.util.ArrayList;

public final class CommitOperation extends VcsOperation {
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * executa operatia: vcs commit -m message.
     * creeaza un nou commit pe care il adauga la lista de commit-uri ale branch-ului curent.
     * seteaza commit-ul curent al branch-ului curent.
     * goleste staging-ul brank-ului curent.
     * seteaza sistemul de fisiere al branch-ului curent cu o copie a sistemului de fisiere al
     * staging-ului.
     * @param vcs sietemul de versionare
     * @return rezultatul comenzii(daca s-a executat cu succes comanda sau nu).
     */
    @Override
    public int execute(Vcs vcs) {
        if (operationArgs.size() != 2) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        if (!operationArgs.get(0).equals("-m")) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        if (vcs.getCurrentBranch().getStaging().getSize() == 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        Commit commit = new Commit(vcs.getCurrentBranch().getCurrentCommit(), operationArgs.get(1),
                vcs.getCurrentBranch().getStaging().getFileSystemSnapshot().cloneFileSystem());
        vcs.getCurrentBranch().addCommit(commit);
        vcs.getCurrentBranch().setCurrentCommit(commit);
        vcs.getCurrentBranch().setFileSystemSnapshot(vcs.getCurrentBranch()
                .getStaging().getFileSystemSnapshot().cloneFileSystem());
        vcs.getCurrentBranch().getStaging().free();

        return ErrorCodeManager.OK;
    }
}
