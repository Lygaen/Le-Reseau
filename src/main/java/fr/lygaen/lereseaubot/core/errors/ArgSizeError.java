package fr.lygaen.lereseaubot.core.errors;

/**
 * The error when the size is not correct
 */
public class ArgSizeError extends Throwable {
        /**
         * To recognize and launch
         * @return the matching method
         */
        @Override
        public String getLocalizedMessage() {
                return "onArgSizeError";
        }
}
