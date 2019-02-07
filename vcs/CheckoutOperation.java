package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import utilsvcs.Commit;

import java.util.ArrayList;

public final class CheckoutOperation extends VcsOperation {
    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * executa operatia: vcs checkout [-c] nume_branck/id_commit.
     * schimba branch-ul curent sau commit-ul curent, in functie de lista de argumente a operatiei.
     * @param vcs sistemul de versionare
     * @return rezultatul comenzii(daca s-a executat cu succes comanda sau nu).
     */
    @Override
    public int execute(Vcs vcs) {
        if (operationArgs.size() == 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        if (vcs.getCurrentBranch().getStaging().getSize() != 0) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }

        if (operationArgs.get(0).equals("-c")) {
            return checkoutForCommit(vcs);
        } else {
            return checkoutForBranch(vcs);
        }
    }

    /**
     * verifica daca branch-ul cautat exista in sistemul de versionare.
     * daca exista seteaza acest branch ca branch-ul curent al sistemului de versionare.
     * @param vcs sisetmul de versionare.
     * @return rezultatul comenzii(daca s-a reusit schimbarea branch-ului)
     */
    private int checkoutForBranch(Vcs vcs) {
        if (!vcs.containBranch(operationArgs.get(0))) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        vcs.setCurrentBranch(vcs.getBranchWithName(operationArgs.get(0)));
        return ErrorCodeManager.OK;
    }

    /**
     * verifica daca commit-ul cautat exista in branch-ul curent al sistemului de versionare.
     * daca exista, schimba commit-ul curent al branch-ului curent cu acest commit,
     * si sterge toate celelalte commit-uri ulterioare acestui commit.
     * seteaza sistemul de fisiere al branch-ului curent si al staging-ului de pe branch-ul
     * curent cu o copie a sistemului de pe commit-ul care va deveni commit curent.
     * @param vcs sistemul de versionare
     * @return rezultatul comenzii(daca s-a reusit schimbarea commit-ului)
     */
    private int checkoutForCommit(Vcs vcs) {
        if (!vcs.getCurrentBranch().hasCommit(Integer.parseInt(operationArgs.get(1)))) {
            return ErrorCodeManager.VCS_BAD_PATH_CODE;
        }

        Commit commit = vcs.getCurrentBranch().getCurrentCommit();
        int id = vcs.getCurrentBranch().getCurrentCommit().getCommitId();
        while (id != Integer.parseInt(operationArgs.get(1))) {
            id = commit.getParent().getCommitId();
            vcs.getCurrentBranch().delete(commit);
            commit = vcs.getCurrentBranch().getCommitWithId(id);
        }

        vcs.getCurrentBranch().setCurrentCommit(commit);
        vcs.getCurrentBranch().getStaging()
                .setFileSystemSnapshot(commit.getFileSystemSnapshot().cloneFileSystem());
        vcs.getCurrentBranch()
                .setFileSystemSnapshot(commit.getFileSystemSnapshot().cloneFileSystem());
        return ErrorCodeManager.OK;
    }

}
