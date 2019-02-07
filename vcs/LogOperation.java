package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import utilsvcs.Commit;

import java.util.ArrayList;
import java.util.List;

public final class LogOperation extends VcsOperation {
    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * executa operatia: vcs log.
     * @param vcs sistemul de versionare
     * @return rezultatul comenzii(daca s-a executat cu succes comanda sau nu).
     */
    @Override
    public int execute(Vcs vcs) {
        if (operationArgs.size() != 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        List<Commit> commits = vcs.getCurrentBranch().getCommits();

        for (int i = 0; i < commits.size() - 1; ++i) {
            vcs.getOutputWriter().write("Commit id: " + commits.get(i).getCommitId() + "\n");
            vcs.getOutputWriter().write("Message: " + commits.get(i).getMessage() + "\n\n");
        }

        vcs.getOutputWriter().write("Commit id: "
                + commits.get(commits.size() - 1).getCommitId() + "\n");
        vcs.getOutputWriter().write("Message: "
                + commits.get(commits.size() - 1).getMessage() + "\n");
        return ErrorCodeManager.OK;
    }
}
