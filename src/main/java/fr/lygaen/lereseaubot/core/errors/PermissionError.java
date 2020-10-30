package fr.lygaen.lereseaubot.core.errors;
/**
 * The error when the permission is not good
 */
public class PermissionError extends Throwable {

        /**
         * To recognize and launch
         * @return the matching method
         */
        @Override
        public String getLocalizedMessage() {
            return "onPermissionError";
        }
}
