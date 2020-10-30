package fr.lygaen.lereseaubot.core.errors;

/**
 * The error when there is an Arg error
 */
public class ArgError extends Throwable {
    /**
     * To recognize and launch
     * @return the matching method
     */
    @Override
    public String getLocalizedMessage() {
        return "onArgError";
    }
}
