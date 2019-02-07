package utilsvcs;

import utils.AbstractOperation;

public final class FactoryMessageOperation {
    private static FactoryMessageOperation instance = new FactoryMessageOperation();

    public static FactoryMessageOperation getInstance() {
        return instance;
    }

    private FactoryMessageOperation() {
    }

    public static final String BRANCH_MESSAGE = "On branch: ";
    public static final String STAGED_CHANGED = "Staged changes:";

    /**
     * creeaza un mesaj pe baza tipului operatiei, mesaj utilizat pentru operatia: vcs status.
     * @param operation operatia pe baza careia se construieste mesajul.
     * @return mesajul creat.
     */
    public String create(AbstractOperation operation) {

        switch (operation.getType()) {
            case MAKEDIR:
                return "Created directory " + operation.getOperationArgs()
                        .get(operation.getOperationArgs().size() - 1);
            case TOUCH:
                return "Created file " + operation.getOperationArgs()
                        .get(operation.getOperationArgs().size() - 1);
            case WRITETOFILE:
                return "Added \"" + operation.getOperationArgs()
                        .get(operation.getOperationArgs().size() - 1)
                        + "\" to file " + operation.getOperationArgs()
                        .get(operation.getOperationArgs().size() - 2);
            case REMOVE:
                return "Removed + " + operation.getOperationArgs()
                        .get(operation.getOperationArgs().size() - 1);
            case CHANGEDIR:
                return "Changed directory to " + operation.getOperationArgs()
                        .get(operation.getOperationArgs().size() - 1);
            default:
                return null;
        }
    }
}
