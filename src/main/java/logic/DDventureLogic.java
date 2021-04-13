package logic;


import javafx.util.Pair;

import java.io.File;
import java.util.*;

public class DDventureLogic implements ILogic{

    private static DDventureLogic instance = null;

    private Game game;
    private Map tempMap;
    private Character tempCharacter;

    private boolean moveMode;
    private ArrayList<Pair<Integer, Integer>> cellsToMove;
    private int turnNumber;

    private final static int DEFAULT_COLUMNS = 16;
    private final static int DEFAULT_ROWS = 9;

    private DDventureLogic() {
        // game's directory sets up
        File gameDirectory = new File(System.getProperty("user.home") + "/Documents/DDventure");
        if(!gameDirectory.exists()) {
            gameDirectory.mkdir();
            new File(gameDirectory.getAbsolutePath() + "/saved_game").mkdir();
            new File(gameDirectory.getAbsolutePath() + "/saved_map").mkdir();
            new File(gameDirectory.getAbsolutePath() + "/saved_character").mkdir();
        }
        game = new Game();
        moveMode = false;
        turnNumber = 0;
    }

    public static ILogic getInstance() {
        if(instance == null)
            instance = new DDventureLogic();
        return instance;
    }


    @Override
    public boolean createTeam(String teamName, String teamColor) {
        for(int i = 0; i < game.getTeams().size(); i++) {
            Team t = game.getTeams().get(i);
            if(teamName.equals(t.getName()) || teamColor.equals(t.getColor())) {
                return false;
            }
        }
        game.getTeams().add(new Team(teamName, teamColor));
        return true;
    }

    @Override
    public void createCharacterInGame(String name, int pf, int ca, int speed, int initiative, String avatar, String teamName, String weaponName, int damageDice, int tpcModifier) {
        Team characterTeam = null;
        for(int i = 0; i < game.getTeams().size(); i++) {
            Team t = game.getTeams().get(i);
            if(t.getName().equals(teamName)) {
                characterTeam = t;
            }
        }
        CharacterInGame characterInGame = new CharacterInGame(name, pf, ca, speed, initiative, avatar, characterTeam,
                new Weapon(weaponName, damageDice, tpcModifier));
        game.getCharactersInGame().add(characterInGame);
        characterTeam.addToTeam(characterInGame);
    }

    @Override
    public void createCharacter(String name, int pf, int ca, int speed, int initiative, String avatar, String weaponName, int damageDice, int tpcModifier) {
        tempCharacter = new Character(name, pf, ca, speed, initiative, avatar,
                new Weapon(weaponName,damageDice, tpcModifier)
        );
    }

    @Override
    public void deleteCharactersInGame() {
        game.getCharactersInGame().clear();
    }

    @Override
    public void deleteTeams() {
        game.getTeams().clear();
    }

    @Override
    public String[] getTeamNames() {
        int n = game.getTeams().size();
        String[] teamNames = new String[n];
        for(int i = 0; i < n; i++)
            teamNames[i] = game.getTeams().get(i).getName();
        return teamNames;
    }

    @Override
    public void createMap(double size) {
        int columns = (int) Math.round(DEFAULT_COLUMNS*size);
        int rows = (int) Math.round(DEFAULT_ROWS*size);
        Cell[][] tempMapCells = new Cell[columns][rows];
        for(int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                tempMapCells[i][j] = new Cell(Map.TERRAIN_MAP.get((int) Math.ceil(Math.random() * 3.0)));
            }
        }
        tempMap = new Map(tempMapCells);
        //TODO: implementare controllo Dijkstra
    }

    @Override
    public Map getTempMap() {
        return tempMap;
    }

    public Character getTempCharacter() {
        return tempCharacter;
    }

    @Override
    public void setGameMap() {
        game.setMap(tempMap);
    }

    @Override
    public Map getGameMap() {
        return game.getMap();
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public boolean saveCharacter(String fileName) {
        return XMLSaveAndLoadManager.saveCharacter(fileName, tempCharacter);
    }

    @Override
    public boolean loadCharacter(String fileName) {
        Character character = XMLSaveAndLoadManager.loadCharacter(fileName);
        if(character != null) {
            tempCharacter = character;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean saveMap(String fileName) {
        return XMLSaveAndLoadManager.saveMap(fileName, tempMap);
    }

    @Override
    public boolean loadMap(String fileName) {
        Map map = XMLSaveAndLoadManager.loadMap(fileName);
        if(map != null) {
            tempMap = map;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void searchCellsToMove(CharacterInGame characterInGame) {
        cellsToMove = new ArrayList<>();
        int xSource = characterInGame.getCoordinataX();
        int ySource = characterInGame.getCoordinataY();
        int movementLeft = characterInGame.speed;
        Map mapGameCells = game.getMap();
        if(movementLeft - mapGameCells.getCell(xSource, ySource).getCrossingCost() >= 0) {
            cellsToMove.add(new Pair<>(xSource, ySource));
            movementLeft -= mapGameCells.getCell(xSource, ySource).getCrossingCost();
            searchCellsToMoveUpRic(cellsToMove, movementLeft, xSource, ySource - 1);
            searchCellsToMoveDownRic(cellsToMove, movementLeft, xSource, ySource + 1);
            searchCellsToMoveLeftRic(cellsToMove, movementLeft, xSource - 1, ySource);
            searchCellsToMoveRightRic(cellsToMove, movementLeft, xSource + 1, ySource);
        }
        cellsToMove = removeDuplicates(cellsToMove);
    }

    private void searchCellsToMoveUpRic(ArrayList<Pair<Integer, Integer>> cellsToMove, int movementLeft, int iSource, int jSource) {
        Map mapGameCells = game.getMap();
        if((iSource >= 0 && iSource < mapGameCells.getColumns()) && (jSource >= 0 && jSource < mapGameCells.getRows())) {
            if(movementLeft - mapGameCells.getCell(iSource, jSource).getCrossingCost() >= 0) {
                cellsToMove.add(new Pair<>(iSource, jSource));
                movementLeft -= mapGameCells.getCell(iSource, jSource).getCrossingCost();
                searchCellsToMoveLeftRic(cellsToMove, movementLeft, iSource - 1, jSource);
                searchCellsToMoveRightRic(cellsToMove, movementLeft, iSource + 1, jSource);
                searchCellsToMoveUpRic(cellsToMove, movementLeft, iSource, jSource - 1);
            }
        }
    }

    private void searchCellsToMoveLeftRic(ArrayList<Pair<Integer, Integer>> cellsToMove, int movementLeft, int iSource, int jSource) {
        Map mapGameCells = game.getMap();
        if((iSource >= 0 && iSource < mapGameCells.getColumns()) && (jSource >= 0 && jSource < mapGameCells.getRows())) {
            if(movementLeft - mapGameCells.getCell(iSource, jSource).getCrossingCost() >= 0) {
                cellsToMove.add(new Pair<>(iSource, jSource));
                movementLeft -= mapGameCells.getCell(iSource, jSource).getCrossingCost();
                searchCellsToMoveLeftRic(cellsToMove, movementLeft, iSource - 1, jSource);
                searchCellsToMoveDownRic(cellsToMove, movementLeft, iSource, jSource + 1);
                searchCellsToMoveUpRic(cellsToMove, movementLeft, iSource, jSource - 1);
            }
        }
    }

    private void searchCellsToMoveRightRic(ArrayList<Pair<Integer, Integer>> cellsToMove, int movementLeft, int iSource, int jSource) {
        Map mapGameCells = game.getMap();
        if((iSource >= 0 && iSource < mapGameCells.getColumns()) && (jSource >= 0 && jSource < mapGameCells.getRows())) {
            if(movementLeft - mapGameCells.getCell(iSource, jSource).getCrossingCost() >= 0) {
                cellsToMove.add(new Pair<>(iSource, jSource));
                movementLeft -= mapGameCells.getCell(iSource, jSource).getCrossingCost();
                searchCellsToMoveRightRic(cellsToMove, movementLeft, iSource + 1, jSource);
                searchCellsToMoveDownRic(cellsToMove, movementLeft, iSource, jSource + 1);
                searchCellsToMoveUpRic(cellsToMove, movementLeft, iSource, jSource - 1);
            }
        }
    }

    private void searchCellsToMoveDownRic(ArrayList<Pair<Integer, Integer>> cellsToMove, int movementLeft, int iSource, int jSource) {
        Map mapGameCells = game.getMap();
        if((iSource >= 0 && iSource < mapGameCells.getColumns()) && (jSource >= 0 && jSource < mapGameCells.getRows())) {
            if(movementLeft - mapGameCells.getCell(iSource, jSource).getCrossingCost() >= 0) {
                cellsToMove.add(new Pair<>(iSource, jSource));
                movementLeft -= mapGameCells.getCell(iSource, jSource).getCrossingCost();
                searchCellsToMoveRightRic(cellsToMove, movementLeft, iSource + 1, jSource);
                searchCellsToMoveDownRic(cellsToMove, movementLeft, iSource, jSource + 1);
                searchCellsToMoveLeftRic(cellsToMove, movementLeft, iSource - 1, jSource);
            }
        }
    }

    private ArrayList<Pair<Integer, Integer>> removeDuplicates(ArrayList<Pair<Integer, Integer>> list) {
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    @Override
    public void generateRandomPositions() {
        Map map = game.getMap();
        game.getCharactersInGame().forEach( characterInGame -> {
            boolean flag = true;
            while(flag) {
                int i = (int) Math.floor(Math.random()*map.getColumns());
                int j = (int) Math.floor(Math.random()*map.getRows());
                if(!map.getCell(i, j).isOccupied() && map.getCell(i, j).getCrossingCost() < Integer.MAX_VALUE) {
                    map.getCell(characterInGame.getCoordinataX(), characterInGame.getCoordinataY()).setOccupied(false);
                    map.getCell(i, j).setOccupied(true);
                    characterInGame.setCoordinataX(i);
                    characterInGame.setCoordinataY(j);
                    flag = false;
                }
            }
        });
    }

    @Override
    public boolean isInMoveMode() {
        return moveMode;
    }
    
    @Override
    public void disableMoveMode() {
        moveMode = false;
    }
    
    @Override
    public void enableMoveMode() {
        moveMode = true;
    }

    @Override
    public Pair<Integer, Integer>[] getCellsToMove() {
        Pair<Integer, Integer>[] array = new Pair[cellsToMove.size()];
      return cellsToMove.toArray(array);
    }

    @Override
    public CharacterInGame getTurnCharacterInGame() {
        return game.getCharactersInGame().get(turnNumber);
    }

    @Override
    public boolean moveCharacterInGame(CharacterInGame characterInGame, int iTarget, int jTarget) {
        boolean flag = false;
        for(int i = 0; i < cellsToMove.size() && !flag; i++) {
            Pair<Integer, Integer> cell = cellsToMove.get(i);
            if(cell.getKey().intValue() == iTarget && cell.getValue().intValue() == jTarget) {
                if(!game.getMap().getCell(iTarget, jTarget).isOccupied())  {
                    game.getMap().getCell(characterInGame.getCoordinataX(), characterInGame.getCoordinataY()).setOccupied(false);
                    characterInGame.setCoordinataX(iTarget);
                    characterInGame.setCoordinataY(jTarget);
                    game.getMap().getCell(iTarget, jTarget).setOccupied(true);
                    flag = true;
                }
            }
        }
        return flag;
    }

    public ArrayList<CharacterInGame> characterTurnOrder(){

        for (int i = 0; i < game.getCharactersInGame().size(); i++){
            game.getCharactersInGame().get(i).setInitiative((int) (Math.random()*20 +1 + game.getCharactersInGame().get(i).getInitiativeModifier()));
        }

        Comparator<CharacterInGame> initiativeComparator = Comparator.comparing(CharacterInGame::getInitiative).reversed();

        Collections.sort(game.getCharactersInGame(), initiativeComparator);
        return game.getCharactersInGame();
    }
}
