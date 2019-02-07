package utilsvcs;

import filesystem.FileSystemSnapshot;

import java.util.LinkedList;
import java.util.List;

public final class Staging {
    private FileSystemSnapshot fileSystemSnapshot;
    private List<String> content;

    /**
     * constructor = creeaza un staging.
     * @param stagingFileSystemSnapshot sistemul de fisiere al staging-ului ce va fi creat
     */
    public Staging(final FileSystemSnapshot stagingFileSystemSnapshot) {
        fileSystemSnapshot = stagingFileSystemSnapshot;
        content = new LinkedList<>();
    }

    /**
     * @return numarul de measaje(corespunzator numarului de operatii date pe branch-ul
     * curent), mesaje care au fost generate de clasa FactoryMessageOperation.
     */
    public int getSize() {
        return content.size();
    }

    /**
     * @return mesajele corespunzatoare operatiilor date pe branch-ul curent
     */
    public List<String> getContent() {
        return content;
    }

    /**
     * @return sistemul de fisiere al staging-ului
     */
    public FileSystemSnapshot getFileSystemSnapshot() {
        return fileSystemSnapshot;
    }

    /**
     * seteaza sistemul de fisiere al staging-ului.
     * @param fileSystemSnapshot noua valoare a sistemului de fisiere
     */
    public void setFileSystemSnapshot(FileSystemSnapshot fileSystemSnapshot) {
        this.fileSystemSnapshot = fileSystemSnapshot;
    }

    /**
     * goleste staging-ul.
     */
    public void free() {
        content.clear();
    }

    /**
     * adauga un mesaj la lista de mesaje <=> s-a executat o operatie pe branch.
     * @param message mesajul ce trebuie adaugat
     */
    public void add(final String message) {
        content.add(message);
    }
}
