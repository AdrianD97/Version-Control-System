package utilsvcs;

import filesystem.FileSystemSnapshot;
import utils.IDGenerator;

public final class Commit {
    private Commit parent;
    private int commitId;
    private String message;
    private FileSystemSnapshot fileSystemSnapshot;

    /**
     * constructor = creeaz un commit.
     * @param commitParent parintele commit-ului
     * @param commitMessage mesajul comitu-lui
     * @param commitFileSystemSnapshot sistemul de fisere al commit-ului
     */
    public Commit(final Commit commitParent, final String commitMessage,
                  final FileSystemSnapshot commitFileSystemSnapshot) {
        message = commitMessage;
        parent = commitParent;
        commitId = IDGenerator.generateCommitID();
        fileSystemSnapshot = commitFileSystemSnapshot;
    }

    /**
     * @return id-ul commit-ului
     */
    public int getCommitId() {
        return commitId;
    }

    /**
     * @return mesajul commit-ului
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return parintele commit-ului
     */
    public Commit getParent() {
        return parent;
    }

    /**
     * @return sistemul de fisiere al commit-ului
     */
    public FileSystemSnapshot getFileSystemSnapshot() {
        return fileSystemSnapshot;
    }
}
