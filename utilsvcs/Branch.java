package utilsvcs;

import filesystem.FileSystemSnapshot;

import java.util.LinkedList;
import java.util.List;

public final class Branch {
    private String name;
    private Commit currentCommit;
    private List<Commit> commits;
    private FileSystemSnapshot fileSystemSnapshot;
    private Staging staging;

    /**
     * constructor = creeaza un branch.
     * @param branchName numele branch-ului
     * @param branchFileSystemSnapshot sistemul de fisiere al branch-ului
     * de asemenea, instatiaza un obiect de tip Staging => stagingul branch-ului,
     * acest staging are si el un sistem de fisiere=copie a sistemului de fisere al branch-ului.
     */
    public Branch(final String branchName,
                  final FileSystemSnapshot branchFileSystemSnapshot) {
        name = branchName;
        fileSystemSnapshot = branchFileSystemSnapshot;
        commits = new LinkedList<>();
        currentCommit = null;
        staging = new Staging(this.fileSystemSnapshot.cloneFileSystem());
    }

    /**
     * adauga un nou commit la lista de commit-uri.
     * @param commit commit-ul care va fi adaugat la lista de
     *               commit-uri ale branch-ului curent.
     */
    public void addCommit(final Commit commit) {
        commits.add(commit);
    }

    /**
     * @return referinta catre staging-ul branch-ului curent.
     */
    public Staging getStaging() {
        return staging;
    }

    /**
     * @return numele branch-ului
     */
    public String getName() {
        return name;
    }

    /**
     * @return commit-ul curent al branch-ului
     */
    public Commit getCurrentCommit() {
        return currentCommit;
    }

    /**
     * seteaza comitul curent.
     * @param newCurrentCommit noul commit curent al branch-ului
     */
    public void setCurrentCommit(Commit newCurrentCommit) {
        this.currentCommit = newCurrentCommit;
    }

    /**
     * @return sistemul de fisiere al branch-ului
     */
    public FileSystemSnapshot getFileSystemSnapshot() {
        return fileSystemSnapshot;
    }

    /**
     * seteaza sistemul de fisiere al branch-ului.
     * @param newFileSystemSnapshot noul sistem de fisiere
     */
    public void setFileSystemSnapshot(FileSystemSnapshot newFileSystemSnapshot) {
        this.fileSystemSnapshot = newFileSystemSnapshot;
    }

    /**
     * verifica daca branch-ul contine un anumit commit.
     * @param commitId id-ul commit-ului cautat
     * @return true daca branch-ul contine commit-ul cautata, false altfel.
     */
    public boolean hasCommit(final int commitId) {
        for (Commit commit: commits) {
            if (commit.getCommitId() == commitId) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return lista de commit-uri ale branch-ului
     */
    public List<Commit> getCommits() {
        return commits;
    }

    /**
     * @param commitId id-ul commit-ului cautat
     * @return commit-ul cu id-ul "commitId"
     */
    public Commit getCommitWithId(final int commitId) {
        for (Commit commit: commits) {
            if (commit.getCommitId() == commitId) {
                return commit;
            }
        }

        return null;
    }

    /**
     * sterge commit-ul din lista de commit-uri ale branch-ului.
     * @param commit commit-ul care se doreste a fi sters
     */
    public void delete(final Commit commit) {
        commits.remove(commit);
    }
}
