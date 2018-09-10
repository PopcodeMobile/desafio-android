package br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites;


public class StarWarsFavoritesResponseDTO {

    public String status;
    public String message;
    public String error;
    public String error_message;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (status != null) {
            stringBuilder = stringBuilder.append(status).append(" ");
        }
        if (message != null) {
            stringBuilder = stringBuilder.append(message).append(" ");
        }
        if (error != null) {
            stringBuilder = stringBuilder.append(error).append(" ");
        }
        if (error_message != null) {
            stringBuilder = stringBuilder.append(error_message).append(" ");
        }

        return stringBuilder.toString();
    }
}
