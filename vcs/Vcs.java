package vcs;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.OutputWriter;
import utils.Visitor;
import utilsvcs.Branch;
import utilsvcs.Commit;

import java.util.LinkedList;
import java.util.List;

public final class Vcs implements Visitor {
    private final OutputWriter outputWriter;
    private List<Branch> branches;
    private Branch currentBranch;

    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
        branches = new LinkedList<>();
    }

    /**
     * creeaza branch-ul master.
     * creeaza un prim commit.
     */
    public void init() {
        FileSystemSnapshot initialFileSystemSnapshot = new FileSystemSnapshot(outputWriter);
        currentBranch = new Branch("master", initialFileSystemSnapshot);
        branches.add(currentBranch);
        Commit commit = new Commit(null, "First commit",
                initialFileSystemSnapshot.cloneFileSystem());
        currentBranch.addCommit(commit);
        currentBranch.setCurrentCommit(commit);
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.getCurrentBranch()
                .getStaging().getFileSystemSnapshot());
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {
        return  vcsOperation.execute(this);
    }

    /**
     * @return sistemul de output al vcs-ului
     */
    public OutputWriter getOutputWriter() {
        return outputWriter;
    }

    /**
     * @return branch-ul curent al vcs-ului
     */
    public Branch getCurrentBranch() {
        return currentBranch;
    }

    /**
     * seteaza branch-ul curent al vcs-ului.
     * @param newCurrentBranch noul branch
     */
    public void setCurrentBranch(Branch newCurrentBranch) {
        this.currentBranch = newCurrentBranch;
    }

    /**
     * verifica daca vcs-ul contine un branch cu numele branchName.
     * @param branchName numele branch-ului cautat
     * @return true daca branch-ul cautat exista, false altfel
     */
    public boolean containBranch(final String branchName) {
        for (Branch branch: branches) {
            if (branch.getName().equals(branchName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * adauga un nou branch la lista de branch-uri ale vcs-ului.
     * @param branch branch-ul care se doreste a fi adaugat
     */
    public void addBranch(final Branch branch) {
        branches.add(branch);
    }

    /**
     * returneaza branch-ul cu numele name.
     * @param name numele branch-ului cautat
     * @return branch-ul cautat
     */
    public Branch getBranchWithName(final String name) {
        for (Branch branch: branches) {
            if (branch.getName().equals(name)) {
                return branch;
            }
        }

        return null;
    }
}
