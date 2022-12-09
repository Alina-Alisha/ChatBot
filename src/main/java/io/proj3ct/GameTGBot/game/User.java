package io.proj3ct.GameTGBot.game;

public class User {


    private long Id; // имя/id пользователя
    private GallowsGame gallowsGame;
    private CitiesGame citiesGame;

    private enum State {active, notActive, citiesGame, gallowsGame} // возможные состояния диалога

    private State dialogState;


    public User(long id) {
        this.Id = id;
        dialogState = State.active;
        citiesGame = new CitiesGame();
        gallowsGame = new GallowsGame();
    }

    public String getAnswer(String userMessage, Database database) {
        if (getCitiesGameState() == citiesGame.getActiveState()) {// проверка активна ли игра в города
            String answer = citiesGame.getAnswer(userMessage);
            if (answer != null)
                return answer;

        }
        if (getCitiesGameState() == citiesGame.getHintState()) { // проверка взял ли игрок подсказку, те активна ли игра в виселицу
            if (getGallowsGameState() == GallowsGame.returnActiveState()) {
                String answer = gallowsGame.getAnswer(userMessage, database);
                if (answer != null)
                    return answer;
                else {
                    return citiesGame.continueCitiesGame(gallowsGame.getCity());
                }
            } else {
                return gallowsGame.startFromCitiesGame(citiesGame.capEndLetter(), database);
            }
        }


        return switch (userMessage) {
            case ("start cities game") -> startCitiesGame(database);
            case ("end cities game") -> endCitiesGame();
            case ("help") -> help();
            default -> null;
        };

    }

    private String help() {
        if (getCitiesGameState() == citiesGame.getActiveState()) {
            return citiesGame.help();
        } else if (getCitiesGameState() == citiesGame.getHintState()) {
            return gallowsGame.help();
        }
        return null;
    }

    public String startCitiesGame(Database database) {

        citiesGame = new CitiesGame(database);
        gallowsGame = new GallowsGame();
        return citiesGame.start();
    }


    public State getState() { // возвращает заначение состояния диалога
        return dialogState;
    }

    public State getStateNotActive() { // возвращает обЪект State со значенитем State.notActive
        return State.notActive;
    }

    public CitiesGame.State getCitiesGameState() {  // возвращает состояние игры в города
        return citiesGame.returnCitiesGameState();
    }


    public String endCitiesGame() { // ф-я завершает игру в города
        citiesGame.finishGame();
        return "Хорошо поиграли, заходи ещё";
    }


    public GallowsGame.State getGallowsGameState() {
        return gallowsGame.getState();
    }


    /*
    public void finishDialog() { // завершает диалог
        dialogState = State.notActive;
    }
    public CitiesGame.State getCitiesGameIsActive() {
        return CitiesGame.returnActiveState();
    }

    public CitiesGame.State getCitiesGameIsHint() {
        return CitiesGame.returnHintState();
    }

    public GallowsGame.State getGallowsGameIsActive() {
        return GallowsGame.returnActiveState();
    }
    public String getCitiesGameGetAns(String message, Database database) { //ф-я возвращает ответ на сообщение пользователя в игре в города
        return citiesGame.getAnswer(message, database);
    }

    public GallowsGame.State returnGallowsGameState(String message) {
        return gallowsGame.returnState();
    }
     */
}