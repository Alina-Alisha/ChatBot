package io.proj3ct.GameTGBot.game;

public class User {

    //TODO:ссылка на историю диалогов

    //TODO: ????? использовать статические методы

    private String Id; // имя/id пользователя
    private GallowsGame gallowsGame;
    private CitiesGame citiesGame;

    private enum State {active, notActive, citiesGame, gallowsGame} // возможные состояния диалога

    private State dialogState;


    public User(String id) {
        this.Id = id;
        dialogState = State.active;
        citiesGame = new CitiesGame();
        gallowsGame = new GallowsGame();
    }

    public String getAnswer(String userMessage, Database database) {
        if (getCitiesGameState() == CitiesGame.returnActiveState()) {// проверка активна ли игра в города
            String answer = citiesGame.getAnswer(userMessage, database);
            if (answer != null) {
                return answer;
            }
        }
        if (getCitiesGameState() == CitiesGame.returnHintState()) { // проверка взял ли игрок подсказку, те активна ли игра в виселицу
            //return getCitiesGameGetAns(userMessage, database);
            if (getGallowsGameState() == GallowsGame.returnActiveState()) {
                String answer = gallowsGame.getAnswer(userMessage, database);
                if (answer != null) {
                    return answer;
                }
                else{
                    return citiesGame.continueCitiesGame();
                }
            }
            else {
                return gallowsGame.startFromCitiesGame(citiesGame.capEndLetter(), database);
            }
        }


        switch (userMessage) {
            case ("start cities game"):
                return startCitiesGame(database);
            case ("end cities game"):
                return endCitiesGame();
            case ("help"):
                return help(); // TODO: help и для городов, и для виселицы
            default:
                return null;
        }

    }

    private String help(){
        if (getCitiesGameState() == CitiesGame.returnActiveState()){
            return citiesGame.help();
        } else if (getCitiesGameState() == CitiesGame.returnHintState()){
            return gallowsGame.help();
        }
        return null;
    }

    public String startCitiesGame(Database database) {

        citiesGame = new CitiesGame(database);
        gallowsGame = new GallowsGame();
        return citiesGame.start();
    }

    public void finishDialog() { // завершает диалог
        dialogState = State.notActive;
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

    public String getCitiesGameGetAns(String message, Database database) { //ф-я возвращает ответ на сообщение пользователя в игре в города
        return citiesGame.getAnswer(message, database);
    }

    /*public GallowsGame.State returnGallowsGameState(String message) {
        return gallowsGame.returnState();
    }

     */


    public String endCitiesGame() { // ф-я завершает игру в города
        citiesGame.finishGame();
        return "Хорошо поиграли, заходи ещё";
    }

    public CitiesGame.State getCitiesGameIsActive() {
        return CitiesGame.returnActiveState();
    }

    public CitiesGame.State getCitiesGameIsHint() {
        return CitiesGame.returnHintState();
    }

    public GallowsGame.State getGallowsGameState() {
        return gallowsGame.getState();
    }

    public GallowsGame.State getGallowsGameIsActive() {
        return GallowsGame.returnActiveState();
    }
}