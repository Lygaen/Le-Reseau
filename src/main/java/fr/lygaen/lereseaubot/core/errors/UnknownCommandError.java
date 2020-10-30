package fr.lygaen.lereseaubot.core.errors;

/**
 * The error when the command used is not known
 */
public class UnknownCommandError extends Throwable {
    /**
     * To recognize and launch
     * @return the matching method
     */
    @Override
    public String getLocalizedMessage() {
        return "onUnknownCommandError";
    }

}
